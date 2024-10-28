package com.training.base;

import java.util.List;
import java.util.Random;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterMethod;

import com.training.utilities.PropertiesUtilClass;

import net.bytebuddy.utility.RandomString;

public class SalesForceBase extends BaseTest {
	
	String dirPath = System.getProperty("user.dir");
	String filePath =  dirPath+"/src/test/resources/env.properties";
	
	@AfterMethod
	public void tearDownAfterMethod() {
		closeDriverInstance();
	}
	
	

	
	// get the username from the .properties file
	public String getUserName() {
		System.out.println("Fetch username from properties file");
		return PropertiesUtilClass.readDataFromPropertyFile(filePath, "username");
	}
	
	// get the password from the .properties file
	public String getPswd() {
		System.out.println("Fetch password from properties file");
		return PropertiesUtilClass.readDataFromPropertyFile(filePath, "pswd");
	}
	
	public String getUser() {
		System.out.println("user account name: ");
		return PropertiesUtilClass.readDataFromPropertyFile(filePath, "user");
	}
	
	//login to the salesforce app
	public void loginToSalesForce(String browserName, boolean rememberMe) {
		
		String username = getUserName();
		String pswd = getPswd();
		
		launchBrowser(browserName);
		
		getUrl("https://login.salesforce.com");
		
		WebElement userNameEle = driver.findElement(By.id("username"));
		enterText(userNameEle, username, "username text field");
		
		
		WebElement pswdEle = driver.findElement(By.id("password"));
		enterText(pswdEle, pswd, "Password text field");
		
		if(rememberMe) {
			WebElement rememberMeCheckBoxEle = driver.findElement(By.id("rememberUn"));
			clickElement(rememberMeCheckBoxEle, "Remember Me Checkbox");
			
		}
		
		WebElement loginEle = driver.findElement(By.id("Login"));
		clickElement(loginEle, "Login");
		
		addWait(5);
		
		System.out.println("login to salesforce app successful!");
		
		
	}
	
	public boolean loginToSalesForce(String browserName, String username, String password) {
		
		launchBrowser(browserName);
		
		getUrl("https://login.salesforce.com");
		
		WebElement userNameEle = driver.findElement(By.id("username"));
		enterText(userNameEle, username, "username text field");
		
		
		WebElement pswdEle = driver.findElement(By.id("password"));
		enterText(pswdEle, password, "Password text field");
		
		WebElement loginEle = driver.findElement(By.id("Login"));
		clickElement(loginEle, "Login");
		
		addWait(5);
		
		String  homepageTitle = getWebPageTitle();
		
		System.out.println("homepageTitle: "+ homepageTitle);
		
		boolean bool = validatePartialText(homepageTitle, "Home Page");
		
		if(bool)
			System.out.println("\"login successful. Homepage is displayed\"");
		else
			System.out.println("Failed to login, please try again with valid username and password.");
		return bool;
}
	
	public void deletePostFileLink(boolean post, boolean file, boolean link) {
		
	}
	
	public String generateRandomString() {
		return  RandomString.make();

	}
	
	public void clickAccountsLink() {
		WebElement allTabsEle = driver.findElement(By.xpath("//a/img[@class=\"allTabsArrow\"]"));
		clickElement(allTabsEle, "all tabs icon");
		addWait(2);
		
		WebElement accountLink = driver.findElement(By.linkText("Accounts"));
		mouseOverElement(accountLink, "Account link");
		clickElement(accountLink, "Accounts link");
	}
	
	public void clickHomeTab() {
		WebElement homeTabEle = driver.findElement(By.cssSelector("#home_Tab"));
		clickElement(homeTabEle, "Home Tab");
	}
	
	public void createAccount(String name) {
		
		WebElement newEle = driver.findElement(By.name("new"));
		clickElement(newEle, "New tab");
		
		addWait(3);
		
		String accountName = name;
		
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
	}
	
	public void clickOppurtunities() {
		WebElement allTabsEle = driver.findElement(By.xpath("//a/img[@class=\"allTabsArrow\"]"));
		clickElement(allTabsEle, "all tabs icon");
		addWait(2);
		
		WebElement leadsEle = driver.findElement(By.linkText("Leads"));
		mouseOverElement(leadsEle, "Oppurtunities");
		clickElement(leadsEle,"Leads");
		addWait(3);
		
	}
	
	public void clickLeads() {
		WebElement allTabsEle = driver.findElement(By.xpath("//a/img[@class=\"allTabsArrow\"]"));
		clickElement(allTabsEle, "all tabs icon");
		addWait(2);
		
		WebElement oppEle = driver.findElement(By.linkText("Leads"));
		mouseOverElement(oppEle, "Leads");
		clickElement(oppEle,"Leads");
		addWait(3);
	}
	
	public void clickContacts() {
		
		WebElement allTabsEle = driver.findElement(By.xpath("//a/img[@class='allTabsArrow']"));
		clickElement(allTabsEle, "all tabs icon");
	
		addWait(2);
		
		WebElement contactsEle  = driver.findElement(By.cssSelector("a[class='listRelatedObject contactBlock title']"));
		//javascript executor injection to 
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("return window.scrollTo(0,800);" );
		mouseOverElement(contactsEle, "Contacts");
		clickElement(contactsEle, "Contacts");
		
		addWait(2);
	}
	
	
	public void CreateNewContact() {
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

	
	public WebElement getRandomElement(List<WebElement> list)
    {
        Random rand = new Random();
        return list.get(rand.nextInt(list.size()));
    }

}
