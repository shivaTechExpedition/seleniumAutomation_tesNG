package com.training.sftestNG;

import static org.testng.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.training.base.SalesForceBase;
import com.training.utilities.CleanUpUtitlity;



@Listeners(com.training.utilities.SalesForceListenersUtility.class)
public class Test_Accounts extends SalesForceBase{
	
	@BeforeMethod
	public void setupBeforeMethod() {
		SalesForceBase app = new SalesForceBase();
		app.loginToSalesForce("chrome", false);
		maximizeBrowserWindow();
		app.clickAccountsLink();
	}
	
	@Test
	public void TC10_CreateAccount() {
		WebElement newEle = driver.findElement(By.name("new"));
		clickElement(newEle, "New tab");
		
		addWait(3);
		
		String accountName = "Sidvin";
		
		WebElement accoutNameTextEle = driver.findElement(By.id("acc2"));
		enterText(accoutNameTextEle,accountName,"Account Name");
		
		WebElement typeDropDown = driver.findElement(By.id("acc6"));
		
		
		Select sel = new Select(typeDropDown);
		sel.selectByValue("Technology Partner");
		
		WebElement customerPriority = driver.findElement(By.id("00Nbm000004Ld2U"));
		Select selObj = new Select(customerPriority);
		selObj.selectByVisibleText("High");
		
		WebElement saveBtn = driver.findElement(By.name("save"));
		clickElement(saveBtn, "Save");
		
		String newAccountTitle = getWebPageTitle();
		
		mylog.info("New account title: " + newAccountTitle);
		
		boolean bool = validatePartialText(newAccountTitle, accountName);
		
		assertEquals(bool, true);
	
	}
	
	@Test
	public void TC11_CreateNewView() {
		WebElement createNewViewLink = driver.findElement(By.linkText("Create New View"));
		clickElement(createNewViewLink, "Create New View Link");
		
		String accountName = generateRandomString();
		
		
		WebElement viewNameEle = driver.findElement(By.id("fname"));
		enterText(viewNameEle, accountName, "View Name TextField");
		
		WebElement uniqueViewNameEle = driver.findElement(By.id("devname"));
		enterText(uniqueViewNameEle, "", "Unique View Name TextField");
		
		WebElement saveBtn = driver.findElement(By.name("save"));
		clickElement(saveBtn, "Save");
		
		addWait(4);
		
		WebElement viewDropDown = driver.findElement(By.name("fcf"));
		Select sel = new Select(viewDropDown);
		
		List<WebElement> viewMenuOptions =  sel.getOptions();
		boolean bool = false;
		for(WebElement option : viewMenuOptions) {
			
			if(option.getText().equals(accountName)) {
				bool = true;
				break;
			}
		}
		assertEquals(bool, true);
		
	}
	
	@Test
	public void TC12_EditView() {
		
		//Edit link click
		WebElement editEle = driver.findElement(By.linkText("Edit"));
		clickElement(editEle, "Edit view");
		
		// change the view name to new view name
		WebElement viewNameEle = driver.findElement(By.id("fname"));
		String viewName = extractText(viewNameEle, "view name text field");
		clearText(viewNameEle, "View Name");
		String newViewName = generateRandomString();
		enterText(viewNameEle, newViewName, "View Name: New View Name");
		
		// Change the unique view name to new view name
		WebElement uniqueViewNameEle = driver.findElement(By.id("devname"));
		clearText(uniqueViewNameEle, "unique view");
		enterText(uniqueViewNameEle,newViewName, "Unique View Name TextField");
		
		
		WebElement fieldDDELe = driver.findElement(By.id("fcol1"));
		selectDropDownOption(fieldDDELe, "ACCOUNT.NAME", "field: Account Name ");
		
		
		
		WebElement operatorDDEle = driver.findElement(By.id("fop1"));
		selectByVisibleText(operatorDDEle, "contains", "operator: contains");
		
		
		WebElement valueEle = driver.findElement(By.id("fval1"));
		enterText(valueEle, "a", "Value text field");
		
		WebElement availableFieldDDEle = driver.findElement(By.id("colselector_select_0"));
		Select sel = new Select(availableFieldDDEle);
		List<WebElement> list = sel.getOptions();
		Boolean elementPresence = false;
		for(WebElement ele : list) {
			if(ele.getText() == "Last Activity") {
				selectDropDownOption(availableFieldDDEle, "ACCOUNT.LAST_ACTIVITY", "Available field : Last Activity ");
				elementPresence = true;
				break;
			}
				
		}
		if(!elementPresence) {
			CleanUpUtitlity obj = new CleanUpUtitlity();
			obj.removeEditViewSelectedFields("Last Activity");
			selectDropDownOption(availableFieldDDEle, "ACCOUNT.LAST_ACTIVITY", "Available field : Last Activity ");
		}
		
		WebElement addBtn = driver.findElement(By.cssSelector("#colselector_select_0_right"));
		clickElement(addBtn, "Add");
		
		
		WebElement saveBtn = driver.findElement(By.name("save"));
		clickElement(saveBtn, "Save");
		
		Select viewDDELe = new Select(driver.findElement(By.name("fcf")));
		List<WebElement>selectedOptions = viewDDELe.getAllSelectedOptions();
		for(WebElement option : selectedOptions) {
			if(option.getText() == newViewName) {
				mylog.info("Last Activity : new View created with view name: "+ newViewName);
				mylog.info("Test Case 12 : Pass");
			}
			else {
				mylog.error("Test Case 12 : Failed");
			}
			break;
			
 		}

	}
	
	@Test
	public void TC13_MergeAccount() {
		
		//create accounts that will be merged
		String name = generateRandomString();
		for(int i = 0; i < 2; i++) {
			clickAccountsLink();
			createAccount(name);
		}
		
		// Move to merge accounts link and click on the link
		WebElement mergeAccountEle = driver.findElement(By.linkText("Merge Accounts"));
		mouseOverElement(mergeAccountEle, "Merge Accounts link");
		clickElement(mergeAccountEle, "Merge Account");
		
		WebElement findAccountsEle = driver.findElement(By.cssSelector("input[id='srch']"));
		enterText(findAccountsEle, name, "Find Accounts text field");
		
		WebElement findAccountBtn = driver.findElement(By.name("srchbutton"));
		clickElement(findAccountBtn, "Find Accounts");
		
		WebElement footerNextBtn = driver.findElement(By.xpath("//div[@class='pbWizardFooter']/div/input[@value=' Next ']"));
		clickElement(footerNextBtn, "Next");
		
		WebElement headerMergeBtn = driver.findElement(By.cssSelector("div[class='pbWizardHeader'] div input[value=' Merge ']"));
		clickElement(headerMergeBtn, "Merge");
		addWait(4);
		
		String alertText = handleAlert();
		boolean bool = validatePartialText(alertText, "These records will be merged");
		assertEquals(bool, true);
	
	}
	
	@Test
	public void TC14_CreateAccountReport() {
		
		
		WebElement reportEle = driver.findElement(By.linkText("Accounts with last activity > 30 days"));
		mouseOverElement(reportEle, "Account with last activity > 30 days Link");
		clickElement(reportEle, "Account with last activity");
		
		addWait(3);
		
		//validate : in Unsaved Reports page
		WebElement unsavedReport = driver.findElement(By.className("pageDescription"));
		boolean bool = validatePartialText(unsavedReport.getText(),"Unsaved Report");
		if(bool) {
			mylog.info("Unsaved Reports page display successful");
		}
		else {
			mylog.info("Fail to display Unsaved Reports page");
		}
		
		WebElement DateField = driver.findElement(By.id("ext-gen148"));
		clickElement(DateField, "Date field dropdown");
		
		List<WebElement> DateFieldDDEles = driver.findElements(By.xpath("//div[@id='ext-gen273']/div"));
		for(WebElement elem : DateFieldDDEles) {
			if(elem.getText().equals("Created Date")){
				clickElement(elem,"'Created Date' Dropdown option");
				break;
			}
		}
		
		WebElement fromDateEle = driver.findElement(By.id("ext-gen152"));
		clickElement(fromDateEle, "From Date Picker");
		
		WebElement fromDateTodayEle = driver.findElement(By.id("ext-gen293"));
		clickElement(fromDateTodayEle, "From Date: Today");
		
		WebElement ToDateEle = driver.findElement(By.id("ext-gen154"));
		clickElement(ToDateEle, "To Date Picker");
		
		WebElement ToDateTodayEle = driver.findElement(By.id("ext-gen308"));
		clickElement(ToDateTodayEle, "To Date: Today");
		
		WebElement reportRunBtn = driver.findElement(By.id("ext-gen63"));
		clickElement(reportRunBtn, "Report Run");
		
		addWait(4);
		
		WebElement reportTxt = driver.findElement(By.xpath("//h1[@class='noSecondHeader pageType']"));
		
		boolean reportTxtBool = validateText("Account List Report",extractText(reportTxt, "Account List Report"));
		
		WebElement reportGenerationStatus = driver.findElement(By.cssSelector("div[class='progressIndicator'] div[id='status']"));
		
		assertEquals(reportTxtBool, true);
		assertEquals(extractText(reportGenerationStatus, "Report Generation Status"), "Complete");
		
	
	}
	
	
}
