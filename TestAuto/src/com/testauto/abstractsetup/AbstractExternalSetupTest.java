package com.testauto.abstractsetup;
import static  com.testauto.setuptestng.SeleniumTestCaseBase.webDriver;
import static com.testauto.setuptestng.SeleniumTestCaseBase.ngWebDriver;

import com.testauto.seleniumsetup.WebDriverExtended;
import com.testauto.util.angular.NgWebDriver;


public abstract class AbstractExternalSetupTest {
	
	public WebDriverExtended getWebDriverExtended() {
		return webDriver;
	}
	
	public NgWebDriver getWebDriverNg() {
		return ngWebDriver;
	}

}
