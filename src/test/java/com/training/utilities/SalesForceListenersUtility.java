package com.training.utilities;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.training.base.BaseTest;


public class SalesForceListenersUtility extends BaseTest implements ITestListener {
	
	
	private Logger mylog = LogManager.getLogger(SalesForceListenersUtility.class);
	private ExtentReportsUtility extentReportUtility=ExtentReportsUtility.getInstance();

	@Override
	public void onTestStart(ITestResult result) {
		mylog.info("test method: " + result.getMethod().getMethodName()+ " :: started");
		extentReportUtility.startSingleTestReport(result.getMethod().getMethodName());
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		mylog.info("test method: "+ result.getMethod().getMethodName()+" :: Successfully completed");
		extentReportUtility.logTestpassed(result.getMethod().getMethodName()+" Successfully completed");
	}

	@Override
	public void onTestFailure(ITestResult result) {
		mylog.error("test method: "+ result.getMethod().getMethodName()+" :: Failed.");
		mylog.error(result.getMethod().getMethodName()+" ended with failure.....................");
		extentReportUtility.logTestFailedWithException(result.getThrowable());
		String filename=new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date());
		String path=Constants.SCREENSHOTS_PATH+filename+".png";
		takeScreenShot(path);
		extentReportUtility.logTestWithscreenshot(path);
	}

	@Override
	public void onStart(ITestContext context) {
		mylog.info("Test " + context.getName()+ " :: started");
		extentReportUtility.startExtentReport();
	}

	@Override
	public void onFinish(ITestContext context) {
		mylog.info("Test " + context.getName()+ " :: completed");
		extentReportUtility.endextent();
	}
	
	
	@Override
	public void onTestSkipped(ITestResult result) {
		mylog.warn(result.getMethod().getMethodName()+" skipped");
		extentReportUtility.logTestFailed(result.getMethod().getMethodName()+" skipped");
		
	}
	
}