
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HandlingAuthenticationPopup {
  	public static String path = System.getProperty("user.dir");
  	public static WebDriver drive;
  
  public static void main(String[] args) throws InterruptedException {
		System.setProperty("webdriver.chrome.driver","D:\\drivers\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://the-internet.herokuapp.com/basic_auth");
    // It will show authentication popup and asks for username and password
		Thread.sleep(1000);
    // "https://USERNAME:PASSWORD@the-internet.herokuapp.com/basic_auth"
    
    // you can send username and password in URL itself
    
    //	driver.get("https://admin:admin@tthe-internet.herokuapp.com/basic_auth");
    
    
    
  }
  
}
