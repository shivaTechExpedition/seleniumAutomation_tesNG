package com.training.base;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.io.Files;


import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {
	
	protected  static WebDriver driver = null;
	protected  static Logger mylog = LogManager.getLogger(BaseTest.class);

	
	
	// launch browser based on the browserName 
	public WebDriver launchBrowser(String browserName) {
		
		switch (browserName.toLowerCase()) {
		case "chrome":
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
			break;
			
		case "firefox":
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
			break;
		case "edge":
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
			break;
		case "ie":
			WebDriverManager.iedriver().setup();
			driver = new InternetExplorerDriver();
			break;

		default:
			mylog.error("Invalid browser name.");
			break;
		}
		return driver;
	}
	
	
	
	// open the webpage with the provided url string
	public  void getUrl(String url) {
		driver.get(url);
	}
	
	
	
	public  void takeScreenShot(String filepath) {
		// String path = Constants.SCREENSHOTS_PATH + CommonUtils.getStringDateAndTimeStamp()+".png";
		TakesScreenshot screen = (TakesScreenshot) driver;
		File src = screen.getScreenshotAs(OutputType.FILE);
		File dst = new File(filepath);
		try {
			Files.copy(src, dst);
			mylog.info("screen captured");
		}
		catch (IOException e) {
			mylog.error(e.getMessage());
			mylog.error("Not able to capture the screenshot");
			
		}
		
	}
	
//  close driver instance
	public  void closeDriverInstance() {
		mylog.info("Closing driver instance.");
		driver.close();
	}
	
	

	

	
	public void maximizeBrowserWindow() {
		mylog.info("Maximising the browser window.");
		driver.manage().window().maximize();
	}
	
	
	// verify if text box present, if present, send keys to the text box
	public  void enterText(WebElement ele,String data,String objectName) {
		if(ele.isDisplayed()) {
			ele.clear();
			ele.sendKeys(data);
			mylog.info("data is entered in the "+ objectName);
		}
		else{
			mylog.error(objectName+" textbox is not diplayed");
		}
	}
	
	
	// verify if text box present, if present, clear the text box
	public  void clearText(WebElement ele,String objectName) {
		if(ele.isDisplayed()) {
			ele.clear();
			mylog.info("data is cleared in the "+ objectName);
		}
		else{
			mylog.error(objectName+" textbox is not diplayed");
		}
	}
		
	// verify if button present, if present, click the button
	public  void clickElement(WebElement ele,String objectName) {
		if(ele.isDisplayed()) {
			ele.click();
			mylog.info(objectName+" button is clicked");
		}
		else{
			mylog.error(objectName+" button is not diplayed");
		}
	}
	
	// add wait
	public  WebDriverWait addWait(int seconds) {
		WebDriverWait wait = new WebDriverWait(driver, seconds);
		
		return wait;
	}
	
	
	
	// Extract text
	public  String extractText(WebElement ele , String objectName) {
		if(ele.isDisplayed()) {
			mylog.info(objectName + " text extracted.");
			return ele.getText();
		}
		mylog.error(objectName + " is not displayed");
		return null;
	}
	
	// compare and validate extracted text with the actual text
	public  boolean validateText(String actualText, String extractedText) {
		mylog.info("Comparing extracted text with the actual text");
		return actualText.equals(extractedText);
		
	}
	
	// validate the extracted text with partial text of the actual text
	public  boolean validatePartialText(String extractedText, String partialText) {
		mylog.info("Comparing extracted text with the partial text");
		return extractedText.contains(partialText);
		
	}
	
	// select dropdown using Select class : select by visible text
	public  List<WebElement> selectByVisibleText(WebElement ele, String visibleText, String objName) {
		List<WebElement> selectedOp = new ArrayList<WebElement>();
		if(ele.isDisplayed()) {
			mylog.info(objName + " dropdown option selected.");
			Select operatorDD = new Select(ele);
			operatorDD.selectByVisibleText(visibleText);
			selectedOp = operatorDD.getAllSelectedOptions();
		}
		else {
			mylog.error(objName + " dropdown option is not displayed.");
		}
		return selectedOp;
	}
	
	// selected option
	public  WebElement getSelectedOption(WebElement ele) {
		Select sel = new Select(ele);
		return sel.getFirstSelectedOption();
	}
	
	// select dropdown using Select class : select by value
	public  void selectDropDownOption(WebElement ele, String value, String objName) {
		
		if(ele.isDisplayed()) {
			mylog.info(objName + " dropdown option selected.");
			Select operatorDD = new Select(ele);
			operatorDD.selectByValue(value);
		}
		else {
			mylog.error(objName + " dropdown option is not displayed.");
		}
	}
	
	// select dropdown using Select class : select by index
		public  void selectDropDownOption(WebElement ele, int index, String objName) {
			
			if(ele.isDisplayed()) {
				mylog.info(objName + " dropdown option selected.");
				Select operatorDD = new Select(ele);
				operatorDD.selectByIndex(index);
			}
			else {
				mylog.error(objName + " dropdown option is not displayed.");
			}
		}
		
	
	

	// verify if element is selected, if not selected, perform click operation to select the webelement
	public  void selectElement(WebElement ele,String objectName) {
		if(!ele.isSelected()) {
			ele.click();
			mylog.info(objectName+" button is selected");
		}
		else{
			mylog.info(objectName+" button is already selected");
		}
	}
	
	public  String getWebPageTitle() {
		return driver.getTitle();
	}
	

	// dropdrown menu particular element click out of a list of menu dropdown elements
	public  void dropDownElementSelection(List<WebElement> elems, String dropDownOptionText, String optionName) {
			// mylog.info( list.size());
			for(WebElement ele : elems) {
				if(ele.getText().equals(dropDownOptionText)) {
					ele.click();
					break;
				}
			}
			mylog.info(optionName + " option in the drop down menu clicked");
			addWait(2);
	}
	
	// get current url of the website
	public  String getCurrentUrlOfWebPage() {
		return driver.getCurrentUrl();
		
	}
	
	//switch to frame -> frameElement
	public  void switchToFrameElement(WebElement ele) {
		mylog.info("Switching to frame ");
		driver.switchTo().frame(ele);
		addWait(4);
	}
	
	//switch to frame -> by frame id
	public  void switchToFrameById(String id) {
		mylog.info("Swithcing to frame");
		driver.switchTo().frame(id);
		addWait(4);
	}
	
	//switch to frame -> by frame name
	public  void switchToFrameByName(String name) {
		mylog.info("Swithcing to frame");
		driver.switchTo().frame(name);
		addWait(4);
	}
	
	//switch to main window from frame
	public  void switchToDefault(String objName) {
		mylog.info("Swithcing to main window from " + objName);
		driver.switchTo().defaultContent();
		addWait(2);
	}
	
	public  void dragAndDropByOffset(WebElement ele, int x, int y) {
		Actions act  = new Actions(driver);
		act.moveToElement(ele).build().perform();
		act.dragAndDropBy(ele, x, y).build().perform();
		
	}
	
	// get all the window handles
	public  Set<String> getAllWindowHandles() {
		mylog.info("Get all the window handles");
		return driver.getWindowHandles();
	}
	
	//mouse over an element
	public  void mouseOverElement(WebElement ele, String objName) {
		Actions act = new Actions(driver);
		mylog.info("mouse over to " + objName);
		act.moveToElement(ele).build().perform();
	}
	
	
	
	// get current window handle
	public  String getCurrentWindowHandle() {
		mylog.info("Get current window handle");
		return driver.getWindowHandle();
	}
	
	public void switchWindow(String switchToWindow) {
		mylog.info("Get all the window handles");
		Set<String> handles = getAllWindowHandles();
		
		mylog.info("Select "+ switchToWindow + " window.");
		for(String handle : handles) {
			if(!(handle).equals(switchToWindow)) {
				driver.switchTo().window(handle);
				break;
			}
		}
	}
	
	public boolean verifyPresenceOfDDOption(WebElement Dropdown, String option) {
		Select sel = new Select(Dropdown);
		List<WebElement> list = sel.getOptions();
		Boolean elementPresence = false;
		for(WebElement ele : list) {
			if(ele.getText() == "Last Activity") {
				selectDropDownOption(Dropdown, option, "Available field : "+ option);
				elementPresence = true;
				break;
			}
				
		}
		return elementPresence;
	}
		
	// handling confirm alert
	public  String handleAlert() {
		mylog.info("Switch to alert popup");
		Alert alertPopup = driver.switchTo().alert();
		mylog.info("extract the alert text");
		String alertText = alertPopup.getText();
		mylog.info("Click ok in the alert popup");
		alertPopup.accept();
		return alertText;
	}
	
	
	
	// close multiple browser instances
	public  void closeAllWindows() {
		mylog.info("Closing all the windows.");
		driver.quit();
	}
			


}
