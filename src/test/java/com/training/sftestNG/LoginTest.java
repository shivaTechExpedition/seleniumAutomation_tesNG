package com.training.sftestNG;

import static org.testng.Assert.assertEquals;

import java.lang.reflect.Method;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.training.base.SalesForceBase;



@Listeners(com.training.utilities.SalesForceListenersUtility.class)
public class LoginTest extends SalesForceBase {
	
	
	@Test
	public void TC01_LoginErrorMessage() {
		
		launchBrowser("chrome");
		getUrl("https://login.salesforce.com");
		maximizeBrowserWindow();
		
		SalesForceBase app = new SalesForceBase();
		
		String username = app.getUserName();
		
		WebElement userNameEle = driver.findElement(By.id("username"));
		enterText(userNameEle, username, "username text field");
		
		
		WebElement pswdEle = driver.findElement(By.id("password"));
		clearText(pswdEle, "Password text field");
		
		WebElement loginEle = driver.findElement(By.id("Login"));
		clickElement(loginEle, "Login");
		
		addWait(5);
		
		WebElement errorMessageEle = driver.findElement(By.id("error"));
		
		String extractedTxt = extractText(errorMessageEle, "Error message");
		
		boolean bool = validateText("Please enter your password.", extractedTxt);
		
		assertEquals(bool, true);
	
		

	}
	
	@Test
	public void TC02_LoginWithValidLoginCredentials(){
		SalesForceBase app = new SalesForceBase();
		
		app.loginToSalesForce("chrome", false);
		
		String  homepageTitle = getWebPageTitle();
		
		System.out.println("homepageTitle: "+ homepageTitle);
		
		boolean bool = validatePartialText(homepageTitle, "Home Page");
		
		assertEquals(bool, true);
			
	}	
	
	@Test
	public void TC03_CheckRememberMe(){
		SalesForceBase app = new SalesForceBase();
		
		app.loginToSalesForce("chrome", true);
		
		
		WebElement staticDropDown = driver.findElement(By.id("userNavButton"));
		clickElement(staticDropDown,"user dropdown menu");
		
		List<WebElement> elems =  driver.findElements(By.xpath("//div[@id='userNav-menuItems']/a"));
		dropDownElementSelection(elems, "Logout", "Logout");
		
		addWait(4);
		
		WebElement rememberedUserNameEle = driver.findElement(By.id("idcard"));
		String rememberedUserName = extractText(rememberedUserNameEle, "Username text field ");
		
		mylog.info("rememberedUserName: "+ rememberedUserName);
		
		Boolean bool = validateText(rememberedUserName, app.getUserName());
		
		assertEquals(bool, true);

	}
	
	@Test
	public void TC04A_ForgotPassword(){
		launchBrowser("chrome");
		getUrl("https://login.salesforce.com");
		maximizeBrowserWindow();

		SalesForceBase app = new SalesForceBase();
		String userName = app.getUserName();
		
		WebElement forgotPswdEle = driver.findElement(By.id("forgot_password_link"));
		clickElement(forgotPswdEle, "forgot your password? link");
		
		WebElement userNameEle = driver.findElement(By.cssSelector("input[id='un']"));
		enterText(userNameEle, userName, "username text field");
		
		WebElement continueButton = driver.findElement(By.xpath("//input[@id='continue']"));
		clickElement(continueButton, "Continue");
		
		addWait(5);
		
		WebElement passwordResetMessageEle = driver.findElement(By.xpath("//div[@class= 'message']"));
		String passwordResetMessage = extractText(passwordResetMessageEle, "Password reset message");
		
		System.out.println("\nExtracted passwordResetMessage: " + passwordResetMessage + "\n");
		
		boolean bool = validatePartialText(passwordResetMessage, "sent you an email with a link to finish resetting your password." );
		
		
		assertEquals(bool, true);
		
	}
	
	@Test
	public void TC04B_LoginWithInvalidUserNamePswd(){
		SalesForceBase app = new SalesForceBase();
		
		boolean bool = app.loginToSalesForce("chrome", "123" , "22131");
		
		if(!bool) {
			WebElement errorMsgEle = driver.findElement(By.xpath("//div[@id='error']"));
			String errorMsg = extractText(errorMsgEle, "Error message text ");
			
			System.out.println("Extracted error message: " + errorMsg);
			
			boolean validateErrorMsg = validatePartialText(errorMsg, "Please check your username and password.");
			assertEquals(validateErrorMsg, true);
			
		}
		
	}

			
}
