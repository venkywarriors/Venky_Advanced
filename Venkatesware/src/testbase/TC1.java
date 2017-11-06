package testbase;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.winium.DesktopOptions;
import org.openqa.selenium.winium.WiniumDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;

public class TC1 {
			
		ExtentReports extent;
		ExtentTest test;
		WebDriver driver;
		public static WiniumDriver windriver;
		
		public static String path = System.getProperty("user.dir");
		
		
		/*
		@BeforeSuite
		void LauchExe() throws MalformedURLException
		{
			  DesktopOptions options= new DesktopOptions();
			  options.setApplicationPath("Exe path.exe");
			  windriver=new WiniumDriver(new URL("http://localhost:9999"),options);
		}*/
		
		@BeforeClass
		public void M1(){
			extent = ExtentManager.GetExtent();
		}
		
		 @Test
		    public void demoTestPass()
		    {
		        test = extent.createTest("demoTestPass", "This test will demonstrate the PASS test case")
		        .assignAuthor("venky");
		        
				test.log(Status.PASS, MarkupHelper.createLabel("\"<b>ragavendra  TC001 passed\" </b> ", ExtentColor.GREEN));

		        Assert.assertTrue(true);
		    }
		     
		    @Test
		    public void demoTestFail()
		    {
		        test = extent.createTest("demoTestFail", "This test will demonstrate the FAIL test case")
		        .assignAuthor("venky");
		    	test.log(Status.INFO, MarkupHelper.createLabel("\"<b>ragavendra  TC001 failed\" </b> ", ExtentColor.PINK));
		        Assert.assertTrue(false);
		        
		        
		    }
		     
		    @Test
		    public void demoTestSkip()
		    {
		        test = extent.createTest("demoTestSkip", "This test will demonstrate the SKIP test case")
		        .assignAuthor("venky");
		        
				test.log(Status.SKIP,MarkupHelper.createLabel("\"<b>ragavendra  TC001 failed\" </b> ", ExtentColor.ORANGE));

		        throw new SkipException("This test case not ready for execution");
		        
		        
		    }
		
		@Test(priority = 2)
		public void checkFail(){
			test = extent.createTest("Testing how fail works")
					.assignAuthor("venky");
		
			test.log(Status.FAIL,MarkupHelper.createLabel("\"<b>ragavendra  TC001 failed\" </b> ", ExtentColor.RED));

		}
			
		@AfterClass
		public void tear()
		{
			
			extent.flush();
		
		}
	
}
