package com.training.utilities;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.training.base.BaseTest;

public class CleanUpUtitlity extends BaseTest {
	public void removeEditViewSelectedFields(String optionValue) {
		Select selectDDEle = new Select(driver.findElement(By.id("colselector_select_1")));
		try {
		selectDDEle.selectByValue(optionValue);
		WebElement removeBtn = driver.findElement(By.id("colselector_select_0_left"));
		System.out.println("Remove the option " + optionValue +" from the Selected field dropdown menu");
		clickElement(removeBtn, "Remove");
		}
		catch (Exception e) {
			System.out.println(e + ": Option "+ optionValue + " not present in the dropdown" );
		}
		
	}

}
