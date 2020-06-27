# Test Automation some useful selenium hacks

## Selenium Hacks-These tricky hacks are useful and make automation scripting effective, Tricks mentioned can be further optimized/updated as per requirement.

### :dart:Capturing full page screenshot, with vertical scrollbar-<br>
```Java
String timeStamp = new SimpleDateFormat("HH_mm_ss").format(new Date());
String screenshot_timestamp = null  ;
screen_timestamp = screenshotname+"_"+timeStamp;
Shutterbug.shootPage(driver,ScrollStrategy.VERTICALLY).withName(screen_timestamp).save("Locl drive location");
```
### :dart:Whether a webelement exist or not-
```Java
driver.findElements(By.id("element-id")).size()!=0
```
### :dart:How to declare webdriver and use the same in another class-
```Java
public class Superclass
{
public WebDriver driver;
public Superclass(){
driver = new FirefoxDriver();
}
public WebDriver getdriver(){
if (driver == null){
driver = new FirefoxDriver();
return driver;
}else{
return driver;
}
}
}

//In other class, call like below syntax
getdriver().findelement(by.xpath("xpath").click());
```
### :dart:Working on frame-
```Java
int i;
i=driver.findElements(By.xpath("//iframe")).size();
driver.switchTo().frame(i-1);
```
### :dart:Highlighting a web object with some colour-
```Java
public void WebElementHighlight(WebElement element, WebDriver driver)
{
if (driver instanceof JavascriptExecutor) {
((JavascriptExecutor)driver).executeScript("arguments[0].style.border='6px groove green'", element);
}
// return element;
}
```
### :dart:Method to handle Sync Issues in web automation
```Java
public WebElement Element_Finder(WebDriver driver, By locator, int T) throws InterruptedException
{
WebElement myElement = null;
//driver.Manage().Timeouts().ImplicitlyWait(TimeSpan.FromSeconds(0));
for (int i = 1; i < T; i++)
{
try
{
System.out.println("Iteration -->"+ i);
myElement = driver.findElement(locator);
//This method is written in #4
WebElementHighlight(myElement, driver);
break;
}
catch (Exception ex)
{
Thread.sleep(1000);
System.out.println("Did not found element with locator"+locator);
}
}
```
