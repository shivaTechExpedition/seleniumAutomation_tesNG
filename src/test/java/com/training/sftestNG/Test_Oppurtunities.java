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


@Listeners(com.training.utilities.SalesForceListenersUtility.class)
public class Test_Oppurtunities extends SalesForceBase {
	
	@BeforeMethod
	public void setupBeforeMethod() {
		SalesForceBase app = new SalesForceBase();
		app.loginToSalesForce("chrome", false);
		app.clickOppurtunities();
	}
	
	@Test
	public void TC15_OppurtunitiesDropDown() {
		
		
		ArrayList<String> actualDropDownOptions = new ArrayList<String>();
		actualDropDownOptions.addAll( Arrays.asList("All Opportunities", "Closing Next Month", "Closing This Month", "My Opportunities", "New Last Week", "New This Week", "Opportunity Pipeline", "Private", "Recently Viewed Opportunities", "Won"
		));

		
		
		List<WebElement> dropDownOptions = driver.findElements(By.cssSelector("select[title='View:'] option"));
		List<String> dropDownOpText = new ArrayList<String>();
		for(WebElement ele: dropDownOptions) {
			dropDownOpText.add(ele.getText());
		}
		System.out.println(dropDownOpText);
		
		// validation
		assertEquals(actualDropDownOptions, dropDownOpText);
		
	}
	
	
	@Test
	public void TC16_CreateNewOpty(){
		
		WebElement newEle = driver.findElement(By.cssSelector("input[name='new']"));
		clickElement(newEle, "New");
		
		String optyName = generateRandomString();
		WebElement oppName = driver.findElement(By.cssSelector("input[id='opp3']"));
		enterText(oppName, optyName, "Oppurtunity Name");
		
		WebElement closeDateEle = driver.findElement(By.cssSelector("span[class='dateFormat']"));
		clickElement(closeDateEle, "Close Date");
		
		WebElement ele = driver.findElement(By.cssSelector("select[class='error']"));
		Select selDD = new Select(ele);
		selectByVisibleText(closeDateEle, optyName, optyName);

	}
	
	@Test
	public void TC17_OpportunityPipelineReport() {
		
		WebElement oppPipelineEle = driver.findElement(By.linkText("Opportunity Pipeline"));
		clickElement(oppPipelineEle, "Oppurtunity Pipeline");
		
		addWait(2);
		
		WebElement oppPipleLineTxt = driver.findElement(By.cssSelector("h1[class='noSecondHeader pageType']"));
		Boolean bool = validateText("Opportunity Pipeline", oppPipleLineTxt.getText());
		
		assertEquals(bool, true);
	}
	
	@Test
	public void TC18_StuckOpportunitiesReport() {
		
		WebElement stuckOppLink = driver.findElement(By.linkText("Stuck Opportunities"));
		mouseOverElement(stuckOppLink, "Stuck Oppurtunity");
		clickElement(stuckOppLink, "Stuck Oppurtunities");
		addWait(3);
		
		WebElement stuckOppTxt = driver.findElement(By.cssSelector("h1[class='noSecondHeader pageType']"));
		Boolean bool = validateText("Stuck Opportunities",stuckOppTxt.getText());
		
		assertEquals(bool, true);
		
	}
	
	@Test
	public void TC19_QuarterlySummaryReport() {
		
		WebElement ddEle = driver.findElement(By.id("quarter_q"));
		selectByVisibleText(ddEle, "Current FQ","Quarterly Summary : Interval");
		
		WebElement includeEle = driver.findElement(By.id("open"));
		selectByVisibleText(includeEle, "Open Opportunities", "Quarterly Summary : Include");
		
		WebElement runReportBtn = driver.findElement(By.cssSelector("input[value='Run Report']"));
		clickElement(runReportBtn, "Run Report");
		
		addWait(3);
		
		WebElement oppReportTxt = driver.findElement(By.cssSelector("h1[class='noSecondHeader pageType']"));
		boolean bool = validateText("Opportunity Report", oppReportTxt.getText());
		
		assertEquals(bool, true);
	}
	
}
