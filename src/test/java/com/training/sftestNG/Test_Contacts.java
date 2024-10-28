package com.training.sftestNG;

import static org.testng.Assert.assertEquals;

import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.training.base.SalesForceBase;



public class Test_Contacts extends SalesForceBase{
	
	@BeforeMethod
	public void setupBeforeMethod() {
		// Launch and Login 
		
		// Click Contact Tab
		loginToSalesForce("chrome", false);
		clickContacts();
	}
	
	@Test
	public void TC25_CreateNewContact() {
		WebElement allTabsEle = driver.findElement(By.xpath("//a/img[@class='allTabsArrow']"));
		clickElement(allTabsEle, "all tabs icon");
	
		addWait(2);
		
		WebElement contactsEle  = driver.findElement(By.cssSelector("a[class='listRelatedObject contactBlock title']"));
		mouseOverElement(contactsEle, "Contacts");
		clickElement(contactsEle, "Contacts");
		
		WebElement newEle = driver.findElement(By.cssSelector("input[name='new']"));
		mouseOverElement(newEle, "New");
		clickElement(newEle, "New");
		
		String lastName = generateRandomString();
		WebElement lastNameEle = driver.findElement(By.cssSelector("input[id='name_lastcon2']"));
		enterText(lastNameEle, lastName, "Last Name Text Field");
		
		WebElement acctNameLookUpEle = driver.findElement(By.cssSelector("img[alt='Account Name Lookup (New Window)']"));
		clickElement(acctNameLookUpEle, "acctNameLookUp");
		
		addWait(3);
		
		String mainWindowHandle = driver.getWindowHandle();
		
		Set<String> handles = driver.getWindowHandles();
		
		for(String handle : handles) {
			if(!(handle).equals(mainWindowHandle)) {
				driver.switchTo().window(handle);
			}
		}
		
		addWait(3);
		WebElement searchTextEle = driver.findElement(By.xpath("//label[@for='lksrch']"));
		enterText(searchTextEle, "Si", "Search Text field");
		
		WebElement goBtn = driver.findElement(By.name("go"));
		clickElement(goBtn, "Go");
		
		addWait(2);
		
		WebElement accountName  = driver.findElement(By.linkText("Si"));
		mouseOverElement(accountName, "Account Name");
		clickElement(accountName, "Account Name");
		
		driver.switchTo().defaultContent();
		
		WebElement saveBtn = driver.findElement(By.xpath("//td[@id='topButtonRow']/input[@name='save']"));
		clickElement(saveBtn, "Save");
		

	}

	
	
		
	@Test
	public void TC26_CreateNewViewInContacts(){
		
		// Click Create new View
		WebElement createNewViewEle = driver.findElement(By.linkText("Create New View"));
		clickElement(createNewViewEle, "Create New View");
		
		// Enter View Name
		WebElement viewNameEle = driver.findElement(By.id("fname"));
		String viewName = generateRandomString();
		enterText(viewNameEle, viewName, "View Name text field");
		
		// Enter View Unique Name
		WebElement uniqueNameEle = driver.findElement(By.id("devname"));
		clickElement(uniqueNameEle, "Unique Name text field");
		
		// Click on Save
		WebElement saveBtn = driver.findElement(By.xpath("//div[@class='pbHeader']/table/tbody/tr/td/input[@class='btn primary']"));
		clickElement(saveBtn, "Save");
		
		// Validate the presence of a newly created view in the dropdown
		String pageTitle = getWebPageTitle();
		if(pageTitle.contains("Contacts")) {
			System.out.println("In Contacts page");
		}
		
		else {
			System.out.println("Not in contacts page, TestCase 26 Failed");
				
			}
		WebElement dropdownEle = driver.findElement(By.className("title"));
		Select sel = new Select(dropdownEle);
		List<WebElement> ddList = sel.getAllSelectedOptions();
		System.out.println(ddList.size());
		for(WebElement ele : ddList) {
			if(ele.getText().equals(viewName)){
				System.out.println(ele.getText());
				System.out.println("Successfully created new view; TC 26 Pass");
			}
			else
				System.out.println("Not able to create new view; TC 26 Fail");
		}
			
	}
	
	@Test
	public void TC27_RecentlyCreatedContacts() {
		
		// Select recently created 
			

		}
	
	
	@Test
	public void TC28_CheckMyContactsView() {
		
		// Select 'My Contacts'
		WebElement myContactsDD = driver.findElement(By.id("fcf"));
		Select sel = new Select(myContactsDD);
		sel.selectByVisibleText("My Contacts");
		
		WebElement selectedEle = sel.getFirstSelectedOption();
		if(selectedEle.getText().equals("My Contacts")) {
			System.out.println("My Contacts option selected from the dropdown - TC28 Pass");
		}
		else {
			System.out.println("Not able to select the 'My Contacts' option from the dropdown list - TC29 Fail");
		}

	}
	
	
	@Test
	public void TC29_ViewAContact(){
		
		// Click on a <contact name> under the Name field in the Recent Contact Frame
		Select sel = new Select(driver.findElement(By.id("fcf")));
		if(sel.getFirstSelectedOption().getText().equals("My Contacts")) {
			System.out.println("In Contacts Page: My Contacts");
		}
		else {
			System.out.println("Not in Contacts Page: TC29 failed");
			closeDriverInstance();
		}
		
		List<WebElement> tableNameColumnEle = driver.findElements(By.xpath("//table[@class='list']/tbody/tr/th[1]"));
		WebElement randomContactName = getRandomElement(tableNameColumnEle);
		String randomContact = randomContactName.getText();
		System.out.println("random contact"+ randomContact);
		WebElement randomContactNameEle = driver.findElement(By.linkText(randomContactName.getText()));
		mouseOverElement(randomContactNameEle, "Random Contact Name");
		clickElement(randomContactNameEle, "Random Contact Name");
		addWait(5);
		
		WebElement selectedContactNameEle = driver.findElement(By.cssSelector("div[class='textBlock']"));
		addWait(4);
		String extractedTxt = extractText(selectedContactNameEle, "Selected Contact Name");
		System.out.println(extractedTxt);
		String[] stringArray = extractedTxt.split(" ");
		String newString ="";
		for(int i = 1; i < stringArray.length; i++) {
			newString += stringArray[i]+ " ";
			
		}
		System.out.println(newString);
		
		if(newString.strip().equals(randomContact)){
			System.out.println("Contact: '" + randomContactName.getText()+"' is selected, TC29 Pass");
		}
		else
			System.out.println("TC29 FAIL, not able to select a Contact");
	}
	
	@Test
	public void TC30_ErrorMessageWhileCreatingNewContactView() { 

			// WebElement pageTextEle = driver.findElement(By.cssSelector(".pageDescription"));
			addWait(4);
			
			WebElement createNewViewLink = driver.findElement(By.linkText("Create New View"));
			
			clickElement(createNewViewLink, "Create New View Link");
			
			addWait(3);
			//wait.until(ExpectedConditions.textToBePresentInElement(pageTextEle, " Create New View"));
			
			WebElement viewUniqueNameEle = driver.findElement(By.cssSelector("#devname"));
			
			enterText(viewUniqueNameEle, "EFGH", "View Unique Name");
			
			WebElement save = driver.findElement(By.cssSelector(".pbHeader td[class='pbButtonb'] input[name='save']"));
			clickElement(save, "Save");
			
			WebElement errorText = driver.findElement(By.cssSelector("div[class='errorMsg']"));
			String errorTxt = errorText.getText();
			assertEquals(errorTxt, "Error: You must enter a value");
			
	}
	
	
	@Test
	public void TC31_CreateNewViewCancelCheck() {
		
		WebElement createNewViewLink = driver.findElement(By.linkText("Create New View"));
		
		clickElement(createNewViewLink, "Create New View Link");
		addWait(3);
		//wait.until(ExpectedConditions.textToBePresentInElement(pageTextEle, " Create New View"));
		
		WebElement viewNameEle = driver.findElement(By.cssSelector("#fname"));
		
		enterText(viewNameEle, "ABCD", "View Name");
		
		
		//wait.until(ExpectedConditions.textToBePresentInElement(pageTextEle, " Create New View"));
		
		WebElement viewUniqueNameEle = driver.findElement(By.cssSelector("#devname"));
		clickElement(viewUniqueNameEle, "View Unique Element");
		addWait(2);
		clearText(viewUniqueNameEle, "View Unique Name");
		
		enterText(viewUniqueNameEle, "EFGH", "View Unique Name");
		
		WebElement cancelBtn = driver.findElement(By.cssSelector(".pbHeader td[class='pbButtonb'] input[name='cancel']"));
		clickElement(cancelBtn, "Cancel");
		
		// validation : check the view list for the view Name
		// if a new View is already present, you need to get the date and time of the view created and validate
		WebElement viewList = driver.findElement(By.name("fcf"));
		Select sel = new Select(viewList);
		List<WebElement> viewElements = sel.getOptions();
		for(WebElement ele : viewElements)
			System.out.println(ele.getText());
		for(WebElement elem : viewElements) {
			if(elem.getText() == "ABCD") {
				System.out.println("Test case failed, new View ABCD created even after cancel button pressed");
				return;
			}
		}
		System.out.println("Test case pass, new View ABCD not created after cancel button pressed");
	
	}
	
	@Test
	public void TC32_CheckSaveAndNewBtnContacts() {
			
			WebElement newEle = driver.findElement(By.name("new"));
			clickElement(newEle, "New");
			
			WebElement lastNameEle = driver.findElement(By.cssSelector("#name_lastcon2"));
			enterText(lastNameEle, "Indian", "LastName text field");
			
			WebElement accountNameEle = driver.findElement(By.cssSelector("#con4"));
			enterText(accountNameEle, "Global Media", "Account name text field");
			
			WebElement lookUpIconEle = driver.findElement(By.cssSelector("#con4_lkwgt"));
			clickElement(lookUpIconEle, "Look up Icon");
			
			addWait(2);
			String mainWinHandle = driver.getWindowHandle();
			
			Set<String> windowHandles = driver.getWindowHandles();
			
//			WebElement saveBtn = driver.findElement(By.cssSelector("#topButtonRow input[name='save_new']"));
//			clickElement(saveBtn, "Save and New ");
			for(String handle : windowHandles) {
				if(!handle.equals(mainWinHandle)) {
					driver.switchTo().window(handle);
					System.out.println( driver.getTitle());
					break;
				}
			}
			
//			// New Contacts not created
//			WebElement errorMsgEle = driver.findElement(By.cssSelector("#errorDiv_ep"));
//			System.out.println("Error message : " + errorMsgEle.getText());
			
		}

}
