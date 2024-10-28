package com.training.sftestNG;

import static org.testng.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.training.base.SalesForceBase;
import com.training.utilities.Constants;



@Listeners(com.training.utilities.SalesForceListenersUtility.class)
public class Test_UserDropDown extends SalesForceBase {
	
	@BeforeMethod
	public void setupBeforeMethod() {
	SalesForceBase app = new SalesForceBase();
	app.loginToSalesForce("chrome", false);
	maximizeBrowserWindow();
	}
	
	@Test
	public void TC05_SelectUserMenuDropDown(){
		
		// actual user dropdown menu list
		List<String> actualUserDropDownList = new ArrayList<String>();
		actualUserDropDownList.addAll(Arrays.asList("My Profile", "My Settings", "Developer Console", "Switch to Lightning Experience", "Logout"));
		mylog.info("actual user DropDown Menu " + actualUserDropDownList);
		
		addWait(3);
		// click on the user element to see the dropdown menu
		WebElement staticDropDown = driver.findElement(By.cssSelector("#userNavButton"));
		clickElement(staticDropDown,"user dropdown menu");
		
		// get all the dropdown menu elements and extract text
		List<WebElement> elems =  driver.findElements(By.xpath("//div[@id='userNav-menuItems']/a"));
		
		List<String> userDropDownMenu = new ArrayList<String>();
		for(WebElement elem : elems) {
			String txt = extractText(elem, "drop down element");
			userDropDownMenu.add(txt);
		}
		
		mylog.info("userDropDownMenu " + userDropDownMenu);
		
		// validate the extracted text list with the actual dropdown menu list
		assertEquals(userDropDownMenu.size(),actualUserDropDownList.size());
		assertEquals(userDropDownMenu, actualUserDropDownList);
	
		
	}
		
	@Test
	public void TC06_MyProfileOption(){
		addWait(3);
		// Select user menu for <username> drop down
		WebElement staticDropDown = driver.findElement(By.cssSelector("#userNavButton"));
		clickElement(staticDropDown,"user dropdown menu");
		
		// get all the dropdown menu elements 
		List<WebElement> elems =  driver.findElements(By.xpath("//div[@id='userNav-menuItems']/a"));	
		
		// Select "My profile" option from user menu
		dropDownElementSelection(elems,"My Profile", "My profile option" );
		
		// Click on edit profile button to edit contact information
		WebElement editProfileEle = driver.findElement(By.xpath("//a[@class='contactInfoLaunch editLink']/img"));
		clickElement(editProfileEle, "Edit profile icon");
		addWait(4);
		
		// Edit profile window validation
		
		
	
		WebElement frameEle = driver.findElement(By.id("contactInfoContentId"));
		switchToFrameElement(frameEle);
		
		// switch to the visible window
		// String editWindowTitle = getWebPageTitle();
		
		//mylog.info("Control is in Edit Window: " + editWindowTitle);
		
		// addWait(4);
		
		// Click on About tab
		WebElement aboutTabEle = driver.findElement(By.cssSelector("li[id='aboutTab']"));
		clickElement(aboutTabEle,"About");
		
		// enter last name text field
		WebElement lastNameEle = driver.findElement(By.cssSelector("input[id='lastName']"));
		enterText(lastNameEle, "abc", "last name text field");
		
		// click on save all button
		WebElement saveAllButtonEle = driver.findElement(By.cssSelector("input[value='Save All']"));
		clickElement(saveAllButtonEle, "save all");
		
		switchToDefault("Window");
		
		// validation of the change in last name of the user
		
		
		// Click on post link
		WebElement postLinkEle = driver.findElement(By.linkText("Post"));
		clickElement(postLinkEle, "Post");
		

		WebElement frameElement = driver.findElement(By.xpath("//iframe[@class='cke_wysiwyg_frame cke_reset']"));
		switchToFrameElement(frameElement);
		
		//WebElement textArea = driver.findElement(By.cssSelector("div[id='cke_43_contents']"));
		WebElement textArea = driver.findElement(By.xpath("//body[@role='textbox']"));
		enterText(textArea,"Goooooood Nightttttt", "Frame Text Area");
		driver.switchTo().defaultContent();
		
		WebElement shareButtonEle = driver.findElement(By.cssSelector("input[id=publishersharebutton]"));
		clickElement(shareButtonEle,"Share");
		addWait(4);
		
		// Click on file link
		WebElement fileLinkEle = driver.findElement(By.linkText("File"));
		clickElement(fileLinkEle, "File");
		
		WebElement uploadFileFromCompEle = driver.findElement(By.id("chatterUploadFileAction"));
		clickElement(uploadFileFromCompEle, "upload file from computer");
		
		WebElement chooseFileEle = driver.findElement(By.id("chatterFile"));
		addWait(2);
		enterText(chooseFileEle,Constants.UPLOAD_PIC ,"choose file field ");
		clickElement(shareButtonEle, "Share");
		addWait(2);
		
		// Click on Add photo link
		WebElement AddPhotoLinkEle = driver.findElement(By.linkText("Add Photo"));
		mouseOverElement(AddPhotoLinkEle, "Add photo Link");
		clickElement(AddPhotoLinkEle, "Add Photo");
		
		switchToFrameById("uploadPhotoContentId");
		
		
		WebElement chooseFile = driver.findElement(By.cssSelector("input[class='fileInput']"));
		enterText(chooseFile,Constants.UPLOAD_PROFILE_PIC,"choose file field");
		WebElement saveBtn = driver.findElement(By.name("j_id0:uploadFileForm:uploadBtn"));
		clickElement(saveBtn, "Save");
		
		addWait(4);
		
		
		
		WebElement imgCropEle = driver.findElement(By.className("imgCrop_selArea"));
		dragAndDropByOffset(imgCropEle, 55, 0);
		
		WebElement imgSaveBtn = driver.findElement(By.id("j_id0:j_id7:save"));
		clickElement(imgSaveBtn, "Save image");
		addWait(4);
		
		switchToDefault("window");
				
	}
	
	@Test
	public void TC07_MySettingsOption() {

		// click on the user element
		WebElement staticDropDown = driver.findElement(By.id("userNavButton"));
		clickElement(staticDropDown, "user dropdown menu");

		// get all the user dropdown options
		List<WebElement> elems = driver.findElements(By.xpath("//div[@id='userNav-menuItems']/a"));
		dropDownElementSelection(elems, "My Settings", "My Settings Option");

		// Click on personal link and select Login History link.
		WebElement personalEle = driver.findElement(By.id("PersonalInfo_font"));
		clickElement(personalEle, "Personal tab");

		WebElement loginHistoryEle = driver.findElement(By.id("LoginHistory_font"));
		Actions act = new Actions(driver);
		act.moveToElement(loginHistoryEle).build().perform();
		clickElement(loginHistoryEle, "Login History Option");

		// Validate login history by extracting the title of the webpage

		// Click on Display & Layout link
		WebElement displayAndLayOutEle = driver.findElement(By.id("DisplayAndLayout"));
		clickElement(displayAndLayOutEle, "Display and Layout tab");

	}
		
	@Test
	public void TC08_DeveloperConsole() {
		
		// click on the user drop down menu
		WebElement staticDropDown = driver.findElement(By.id("userNavButton"));
		clickElement(staticDropDown,"user dropdown menu");
		
		// Select the Developer console option from the menu
		List<WebElement> elems =  driver.findElements(By.xpath("//div[@id='userNav-menuItems']/a"));
		dropDownElementSelection(elems, "Developer Console", "Developer Console");
		addWait(5);
		
		
		String mainWindowHandle = getCurrentWindowHandle();
		
		
		Set<String> handles = getAllWindowHandles();
		
		for(String handle : handles) {
			if(!(handle).equals(mainWindowHandle)) {
				driver.switchTo().window(handle);
			}
		}
		
		// extract the Developer console window title to validate Developer console window is open
		String webPageTitle = getWebPageTitle();
		
		mylog.info("WebPage Title: " + webPageTitle);
		
		switchToDefault("Developer console Window");
		
		Boolean bool = validateText("Developer Console", webPageTitle);
		assertEquals(bool, true);
		
		
		// closeAllWindows();
		
	
	}
	
	@Test
	public void TC09_LogoutOption() {
		
		// click the user drop down
		WebElement staticDropDown = driver.findElement(By.id("userNavButton"));
		clickElement(staticDropDown,"user dropdown menu");
		
		// get the drop down items and click Logout
		List<WebElement> elems =  driver.findElements(By.xpath("//div[@id='userNav-menuItems']/a"));
		dropDownElementSelection(elems, "Logout", "Logout");
		
		// validate using the title of the the webpage : after logout title should "Login | Salesforce"
		String webPageTitle = getWebPageTitle();
		
		mylog.info("Web page title: " + webPageTitle);
		boolean bool = validatePartialText(webPageTitle, "Login");
		
		assertEquals(bool, true);

		
	}
		
	

}
