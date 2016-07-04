package com.testauto.attempts;

import org.testng.TestNG;

import com.testauto.setuptestng.listeners.ExecutionListener;
import com.testauto.setuptestng.listeners.Listener;
import com.testauto.setuptestng.listeners.TestListener;

public class MainExecClass {

	public static void main(String[] args) {

		TestNG testNG = new TestNG();
		testNG.setTestClasses(new Class[] { TestRun.class });
		testNG.addListener(new TestListener());
		testNG.run();

	}

}
