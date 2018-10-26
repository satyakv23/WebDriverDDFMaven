package testcases;

import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;

public class BankManagerLoginTest extends BaseTest{
	
	
	@Test
	public void loginAsBankManager() {
		
		click("bankmanagerloginBtn_CSS");
		Assert.assertTrue(isElementPresent("addCustomerBtn_CSS"),"Not able to login");
	//	Assert.fail("Failing bank manager test");
	}

}
