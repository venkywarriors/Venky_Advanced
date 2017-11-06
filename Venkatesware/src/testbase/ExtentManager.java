package testbase;
import java.net.InetAddress;
import java.net.UnknownHostException;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager {
	private static ExtentReports extent;
	private static ExtentTest test;
	private static ExtentHtmlReporter htmlReporter;
	
	static String Ostype=System.getProperty("sun.arch.data.model"); 
	static String windowsUser = System.getProperty("user.name");
	  static String windowsDomain = System.getenv("USERDOMAIN");
	  static String OS = System.getProperty("os.name");
	static String IP_Address()
	{
		InetAddress addr = null;
		try {
			addr = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   	String ip = addr.getHostAddress();	
	   	
	   return ip;	
	}
	static String HostName()
	{
		InetAddress addr = null;
		try {
			addr = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String hostname = addr.getHostName();
	   	
	   return hostname;	
	}
		
	public static ExtentReports GetExtent(){
		if (extent != null)
                    return extent; //avoid creating new instance of html file
                extent = new ExtentReports();		
		extent.attachReporter(getHtmlReporter());
		return extent;
	}

	private static ExtentHtmlReporter getHtmlReporter() {
	
        htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") +"//ExtentReport.htm");
		
	// make the charts visible on report open
        htmlReporter.config().setChartVisibilityOnOpen(true);
        extent.setSystemInfo("Host Name", HostName());
        extent.setSystemInfo("operating systen", OS);
		extent.setSystemInfo("User Name", windowsDomain + "\\" + windowsUser);
		extent.setSystemInfo("OS bit type", Ostype +" Bit");
		extent.setSystemInfo("Environment", IP_Address());
		
		
        htmlReporter.config().setDocumentTitle("ragavendra");
        htmlReporter.config().setReportName("Automation by ragavendra");
        htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
		htmlReporter.config().setTheme(Theme.DARK);
        return htmlReporter;
	}
	
	public static ExtentTest createTest(String name, String description){
		test = extent.createTest(name, description);
		return test;
	}
	
}
