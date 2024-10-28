package com.training.sftestNG;

import static org.testng.Assert.assertEquals;

import java.awt.Window;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

/*
 * Launch and Login  to SalesForce Application
Click on Home Tab
Click on the FirstName LastName link in the home page
 */

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.training.base.SalesForceBase;

public class Test_RandomScenarios extends SalesForceBase{
	
	@BeforeMethod
	public void setupBeforeMethod() {
		SalesForceBase app = new SalesForceBase();
		app.loginToSalesForce("chrome", false);
		maximizeBrowserWindow();
		app.clickHomeTab();
		addWait(3);
		
		
		
	}

	@Test
	public void TC33_verifyUserFirstAndLastName() {
		WebElement userNameEle = driver.findElement(By.xpath("//h1/a"));
		String userNameTxt = extractText(userNameEle, "User Name");
		assertEquals(userNameTxt, "Shivakumari abc");
		
	}
	
	
	/*
	 * Click on Home Tab
Click on the FirstName LastName link at the left had side of the home page
Click on the 'Edit Profile' icon near Contact
Click on the 'About' tab in the popup.
	 */
	@Test
	public void TC34_verifyEditedLastName() {
//		WebElement userNameEle = driver.findElement(By.xpath("//h1/a"));
//		String userNameTxt = extractText(userNameEle, "User Name");
//		clickElement(userNameEle, "User Name");
//		addWait(3);
		
		// Select "My profile" option from user menu
		WebElement myProfileEle = driver.findElement(By.linkText("Profile"));
		mouseOverElement(myProfileEle, "My Profile");
		clickElement(myProfileEle, "My Profile");
		
		addWait(4);
		
		// Click on edit profile button to edit contact information
		WebElement editProfileEle = driver.findElement(By.xpath("//a[@class='contactInfoLaunch editLink']"));
		clickElement(editProfileEle, "Edit profile icon");
		addWait(4);
		
		
		driver.switchTo().frame("contactInfoContentId");
		
		WebElement aboutTabEle = driver.findElement(By.cssSelector("#aboutTab"));
		clickElement(aboutTabEle, "About Tab");
		WebElement lastNameEle = driver.findElement(By.cssSelector("#lastName"));
		clearText(lastNameEle, "last name");
		enterText(lastNameEle, "Abcd", "Last Name text field");
		WebElement saveAllBtn = driver.findElement(By.cssSelector("input[value='Save All']"));
		clickElement(saveAllBtn, "Save All");
		
		driver.switchTo().defaultContent();
		// clickHomeTab();
		addWait(3);
		
		WebElement updatedUserNameEle = driver.findElement(By.cssSelector("#tailBreadcrumbNode"));
		String updatedName = extractText(updatedUserNameEle, "Updated User Name");
		assertEquals(updatedName, "Shivakumari Abcd");
		
		WebElement DropDownUserNameEle = driver.findElement(By.id("userNavLabel"));
		String DropDownUserName = extractText(DropDownUserNameEle, "DropDown UserName");
		assertEquals(DropDownUserName, "Shivakumari Abcd");
		WebElement newUserNameEle = driver.findElement(By.cssSelector(".userBlock"));
		String newUserName = extractText(newUserNameEle, "User Name");
		assertEquals(newUserName, "Shivakumari Abcd");
	
	}
	
	/*
	 * Click on the all tab '+'
Click on the 'Customize My Tabs' button on the right hand side of the page.
Select any tab from the 'Selected Tabs' section and click Remove button.
Click on Save button
Click on User menu 'FirstName LastName' menu button and click Logout.
Launch and Login  to SalesForce Application
	 */
	@Test
	public void TC35_verifyTabCustomization() {
		WebElement allTabsEle = driver.findElement(By.cssSelector(".allTabsArrow"));
		clickElement(allTabsEle, "All tabs");
		
		WebElement customizeTabEle = driver.findElement(By.cssSelector(".btnImportant"));
		clickElement(customizeTabEle, "Customize my tabs");
		
		WebElement selectedTabs = driver.findElement(By.id("duel_select_1"));
		Select sel = new Select(selectedTabs);
		List<WebElement> optionsBeforeRemove = sel.getOptions();
		selectDropDownOption(selectedTabs, 1, "Selected Tabs");
		WebElement selectedOption = sel.getFirstSelectedOption();
		System.out.println("selectedOption"+selectedOption.getText());
		WebElement removeBtn = driver.findElement(By.className("leftArrowIcon"));
		clickElement(removeBtn, "Remove");
		
		WebElement saveBtn = driver.findElement(By.name("save"));
		clickElement(saveBtn, "Save");
		
		WebElement staticDropDown = driver.findElement(By.id("userNavButton"));
		clickElement(staticDropDown,"user dropdown menu");
		
		// get the drop down items and click Logout
		List<WebElement> elems =  driver.findElements(By.xpath("//div[@id='userNav-menuItems']/a"));
		dropDownElementSelection(elems, "Logout", "Logout");
		
		String username = getUserName();
		String pswd = getPswd();
		
		WebElement userNameEle = driver.findElement(By.id("username"));
		enterText(userNameEle,username, "username text field");
		
		
		WebElement pswdEle = driver.findElement(By.id("password"));
		enterText(pswdEle, pswd, "Password text field");
		
		WebElement loginEle = driver.findElement(By.id("Login"));
		clickElement(loginEle, "Login");
		
		addWait(5);
		
		List<WebElement> tabBarElems = driver.findElements(By.xpath("//ul[@id='tabBar']/li"));
		boolean bool =  true;
		for(WebElement elem : tabBarElems) {
			if((elem.getText()).equals(selectedOption.getText())) {
				bool = false;
				break;
			}
		}
		
		assertEquals(bool, true);
		
		
	}
	
	/*
	 * Click on Home Tab
Click on the current date link
Click on '8:00PM' link
Click on 'Subject Combo' icon next to Subject field.
Click 'Other' from Combobox
Click on the 'End' time field
Select '9:00 PM' from the dropdown.
Click Save button
	 */
	@Test
	public void TC36_BlockingAnEventInCalendar() {
		WebElement dateLink = driver.findElement(By.cssSelector(".pageDescription a"));
		clickElement(dateLink, "Current Date Link");
		
		JavascriptExecutor scroll = (JavascriptExecutor) driver;
		scroll.executeScript("window.scrollBy(0,800);", "Scroll to 8PM");
		
		WebElement eightPMLink = driver.findElement(By.linkText("8:00 PM"));
		clickElement(eightPMLink,"8:00 PM link");
		
		addWait(5);
		
		
		WebElement subjectComboIconEle = driver.findElement(By.cssSelector("a[title='Combo (New Window)']"));
		//mouseOverElement(subjectComboIconEle, "combo icon");
		clickElement(subjectComboIconEle, "Subject Combo Icon");
		
		Set<String> windowHandles = driver.getWindowHandles();
		String currentWinHandle = driver.getWindowHandle();
		
		for(String handle : windowHandles) {
			if(!handle.equals(currentWinHandle)) {
				driver.switchTo().window(handle);
				break;
			}
		}
		WebElement otherLink = driver.findElement(By.linkText("Other"));
		clickElement(otherLink, "Other Link");
		addWait(2);
		
		driver.switchTo().window(currentWinHandle);
		
		WebElement endDateTimeEle = driver.findElement(By.name("EndDateTime"));
		clickElement(endDateTimeEle, "End Date ");
		
		List<WebElement> endTimeList = driver.findElements(By.cssSelector("div[id=simpleTimePicker] div"));
		for(WebElement endTime : endTimeList) {
			if (endTime.getText().equals("9:00 PM")) {
				clickElement(endTime, "9:00 PM selected");
				break;
			}
		}
		
		WebElement saveEle = driver.findElement(By.cssSelector("#topButtonRow input[name='save']"));
		clickElement(saveEle, "save");
		
		
		WebElement eventBlockEle = driver.findElement(By.cssSelector(".eventBlockDnD"));
		mouseOverElement(eventBlockEle, "blocked event(Other)");
		
		WebElement otherEleLink = driver.findElement(By.linkText("Other"));
		String OtherLinkText =	extractText(otherEleLink, "Other Link");
		assertEquals(OtherLinkText, "Other");
		addWait(3);
		
	}
	
	
	/*
	 * Click on Home Tab
Click on the current date link
Click on '4:00PM' link
Click on 'Subject Combo' icon next to Subject field.
Click 'Other' from Combobox
Click on the 'End' time field
Select '7:00 PM' from the dropdown.
Check the'Create Recurring Series of Events' under the 'Recurrence' field
Select 'Weekly' radiobutton
Click on the 'End Date' field and select 2 weeks from then.
Click Save button
Click 'Month View' icon
	 */
	@Test
	public void TC37_blockEventWithWeeklyRecurrence() {
		WebElement dateLink = driver.findElement(By.cssSelector(".pageDescription a"));
		clickElement(dateLink, "Current Date Link");
		
		
		JavascriptExecutor scroll = (JavascriptExecutor) driver;
		scroll.executeScript("window.scrollBy(0,800);", "Scroll to 8PM");
		
		WebElement eightPMLink = driver.findElement(By.linkText("8:00 PM"));
		clickElement(eightPMLink,"4:00 PM link");
		
		addWait(5);
		
		WebElement subjectComboIconEle = driver.findElement(By.cssSelector("a[title='Combo (New Window)']"));
		//mouseOverElement(subjectComboIconEle, "combo icon");
		clickElement(subjectComboIconEle, "Subject Combo Icon");
		
		Set<String> windowHandles = driver.getWindowHandles();
		String currentWinHandle = driver.getWindowHandle();
		
		for(String handle : windowHandles) {
			if(!handle.equals(currentWinHandle)) {
				driver.switchTo().window(handle);
				break;
			}
		}
		
		WebElement otherLink = driver.findElement(By.linkText("Other"));
		clickElement(otherLink, "Other Link");
		addWait(2);
		
		driver.switchTo().window(currentWinHandle);
		
		WebElement endDateTimeEle = driver.findElement(By.name("EndDateTime"));
		clickElement(endDateTimeEle, "End Date ");
		
		List<WebElement> endTimeList = driver.findElements(By.cssSelector("div[id=simpleTimePicker] div"));
		for(WebElement endTime : endTimeList) {
			if (endTime.getText().equals("7:00 PM")) {
				clickElement(endTime, "7:00 PM selected");
				break;
			}
		}
		
		WebElement recurrenceCheckBox = driver.findElement(By.cssSelector("#IsRecurrence"));
		clickElement(recurrenceCheckBox, "Reccurence CheckBox");
		
		WebElement weeklyRadioBtn = driver.findElement(By.cssSelector("#rectypeftw"));
		clickElement(weeklyRadioBtn, "Weekly Radio");
		
	}
	
	
	public void getDate() {
		WebElement dateEle = driver.findElement(By.cssSelector("#RecurrenceStartDateTime"));
		String dateTxt = extractText(dateEle, "Date");
		String[] dateMonthYear = dateTxt.split("/");
		System.out.println(dateMonthYear.toString());
		
	}
	
	
}
