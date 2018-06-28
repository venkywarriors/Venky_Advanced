
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
/*
Solution 1: you can send username and password in URL itself	
String URL = "http://" + USERNAME + ":" + PASSWORD + "@" + the-internet.herokuapp.com/basic_auth;
driver.get(URL);

Solution 2:

driver.get('https://the-internet.herokuapp.com/basic_auth')
driver.switchTo().alert().sendKeys("username" + Keys.TAB + "password");
driver.switchTo().alert().accept();

solution 3: AutoIt
$title= "Authentication Required"
WinWaitActive($title)
send("admin")
send("{TAB}")
send("admin")
send("{ENTER}")
*/
  }
  
}
