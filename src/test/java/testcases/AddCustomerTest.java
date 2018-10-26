package testcases;

import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import base.BaseTest;
import utilities.TestUtil;



public class AddCustomerTest extends BaseTest{
	
	@Test(dataProvider="getData")
	public void addCustomer(String firstName, String lastName, String postCode,String alertMsg) {
		
		click("addCustomerBtn_CSS");
		type("firstName_CSS",firstName);
		type("lastName_CSS",lastName);
		type("postCode_CSS",postCode);
		click("addCustomer_CSS");
		
		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		Assert.assertTrue(alert.getText().contains(alertMsg),"Customer not added successfully");
		alert.accept();
		
		Assert.fail("Failing Add Customer test");
	}
	
	
	@DataProvider
	public Object[][] getData() {
	
		return TestUtil.getData("AddCustomerTest");
	}

}
