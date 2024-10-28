package com.training.sftestNG;

import static org.testng.Assert.assertEquals;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.training.base.SalesForceBase;



public class Test_Leads extends SalesForceBase{
	
	@BeforeMethod
	public void setupBeforeMEthod() {
		SalesForceBase app = new SalesForceBase();
		app.loginToSalesForce("chrome", false);
		
		app.clickLeads();
	}
	
	@Test
	public void TC20_CheckLeadsTabLink() {
		String title = getWebPageTitle();
		mylog.info(title);
		boolean bool = validatePartialText(title, "Leads: Home");
		assertEquals(bool, true);
	
	}
	
	@Test
	public void TC21_LeadsSelectView() {
		
		WebElement dropDownEle = driver.findElement(By.id("hotlist_mode"));
		Select sel = new Select(dropDownEle);
		mylog.info(sel.getOptions());

	}
	
	@Test
	public void TC22_DefaultView() {
		
	}
	
	@Test
	public void TC23_TodaysLeads() {
		
		WebElement dropDownEle = driver.findElement(By.id("fcf"));
		List<WebElement> list = selectByVisibleText(dropDownEle, "Today's Leads", "Leads Dropdown");
		for(WebElement elem : list) {
			
			mylog.info(elem.getText());
		}
		
		
		WebElement ele = getSelectedOption(dropDownEle);
		Boolean bool = validateText("Today's Leads", ele.getText());
		
		assertEquals(bool, true);
	
		
	}
	
	@Test
	public void TC24_NewLead() {
		WebElement newEle = driver.findElement(By.cssSelector("input[name='new']"));
		clickElement(newEle, "New");
		
		addWait(3);
		
		WebElement lastNameEle = driver.findElement(By.cssSelector("input[id='name_lastlea2']"));
		enterText(lastNameEle, "ABC", "Last Name Text Field");
		
		WebElement companyEle = driver.findElement(By.cssSelector("input[id='lea3']"));
		enterText(companyEle, "ABCD", "Company Text Field");
		
		WebElement saveBtn = driver.findElement(By.xpath("//td[@id='topButtonRow']/input[@name='save']"));
		clickElement(saveBtn, "Save");
		
		addWait(4);
		
		WebElement leadText = driver.findElement(By.xpath("//h2[@class='topName']"));
		mylog.info("new lead name: " + leadText.getText());
		
		boolean bool = validateText("ABC", leadText.getText());
		
		assertEquals(bool, true);
		
	}

}
