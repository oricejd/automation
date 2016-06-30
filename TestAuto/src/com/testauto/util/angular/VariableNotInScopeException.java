package com.testauto.util.angular;

import org.openqa.selenium.WebDriverException;

public class VariableNotInScopeException extends WebDriverException {
    public VariableNotInScopeException(String msg) {
        super(msg);
    }
}
