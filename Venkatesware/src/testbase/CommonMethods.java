package ragavendraPackage;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import javax.imageio.ImageIO;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

public class CommonMethods 
{
	String path=System.getProperty("user.dir");
	protected WebDriver driver;
	
	public void clickJE(WebElement element, int wait_time)
	{
	int attempt =1;
	while(attempt < 6)
	{
		try{
		WebDriverWait wait = new WebDriverWait(driver, wait_time);
		wait.until(ExpectedConditions.elementToBeClickable(element);
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
		}catch(Exception e){}
		attempt++;
	}	
	}
	public void waitForPageToLoad(int wait_time)
	{
	 driver.manage().timeouts().pageLoadTimeout(wait_time, TimeUnit.SECONDS);	
	}
	
	 public void waitForPageLoaded(int time) {
        ExpectedCondition<Boolean> expectation = new
                ExpectedCondition<Boolean>() {
                    public Boolean apply(WebDriver driver) {
                        return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString().equals("complete");
                    }
                };
        try {
            Thread.sleep(1000);
            WebDriverWait wait = new WebDriverWait(driver, time);
            wait.until(expectation);
        } catch (Throwable error) {
            Assert.fail("Timeout waiting for Page Load Request to complete.");
        }
    }
	public void FullPageScreenShot(String FileName)
	{
	  Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot(driver);
  	  ImageIO.write(screenshot.getImage(),"PNG",new                              
          File(path +"/Screenshots/+"+FileName+".png"));	
	}
	
	public void OpenNewTab()
	{ 
		ArrayList<String> tabBefore = new ArrayList<String>(driver.getWindowHandle());
		System.out.println("Total no. of tab are " + tabBefore.size());
		((JavascriptExecutor) driver).executeScript("window.open()");
		ArrayList<String> tabAfter = new ArrayList<String>(driver.getWindowHandle());
		driver.switchTo().window(tabAfter.get(tabBefore.size())); 
	}	
	
	public void HighlightMyElement(WebElement element) throws InterruptedException {

		JavascriptExecutor javascript = (JavascriptExecutor) driver;
		javascript.executeScript("arguments[0].setAttribute('style', arguments[1]);", element,
				"color: blue; border: 4px solid blue;");
		Thread.sleep(250);
		javascript.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "");
	}
	
	protected int invalidImageCount;
	protected int invalidLinksCount;
	protected void validateInvalidImages() {
		try {
			invalidImageCount = 0;
			List<WebElement> imagesList = driver.findElements(By.tagName("img"));
			System.out.println("Total no. of images are " + imagesList.size());
			for (WebElement imgElement : imagesList) {
				if (imgElement != null) {
					verifyimageActive(imgElement);
				}
			}
			System.out.println("Total no. of invalid images are "	+ invalidImageCount);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
	}
	
	protected void validateInvalidLinks() {

		try {
			invalidLinksCount = 0;
			List<WebElement> anchorTagsList = driver.findElements(By
					.tagName("a"));
			System.out.println("Total no. of links are "
					+ anchorTagsList.size());
			for (WebElement anchorTagElement : anchorTagsList) {
				if (anchorTagElement != null) {
					String url = anchorTagElement.getAttribute("href");
					if (url != null && !url.contains("javascript")) {
						verifyURLStatus(url);
					} else {
						invalidLinksCount++;
					}
				}
			}

			System.out.println("Total no. of invalid links are "
					+ invalidLinksCount);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
		 protected void verifyURLStatus(String URL) {

			HttpClient client = HttpClientBuilder.create().build();
			HttpGet request = new HttpGet(URL);
			try {
				HttpResponse response = client.execute(request);
				// verifying response code and The HttpStatus should be 200 if not,
				// increment invalid link count
				////We can also check for 404 status code like response.getStatusLine().getStatusCode() == 404
				if (response.getStatusLine().getStatusCode() != 200)
					invalidLinksCount++;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	
	 private void verifyimageActive(WebElement imgElement) {
			try {
				HttpClient client = HttpClientBuilder.create().build();
				HttpGet request = new HttpGet(imgElement.getAttribute("src"));
				HttpResponse response = client.execute(request);
				// verifying response code he HttpStatus should be 200 if not,
				// increment as invalid images count
				if (response.getStatusLine().getStatusCode() != 200)
					invalidImageCount++;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	public boolean perform_AvoidStale(WebElement element,int wait_time)
	{
	boolean found = true;
	WebDriverWait wait;
	while(found)
	{
	try{
		wait = new WebDriverWait(driver,wait_time);
		wait.Until(ExpectedConditions.visibilityOf(element));
		break;
	}catch(StaleElementReferenceException e){ }
	}
		return found;
	}
	
	public string getBrowserName()
	{
		Capabilities cap = ((RemoteWebDriver) driver).getCapabilities();
		String browserName = cap.getBrowserName().toLowerCase();
		// String os = cap.getPlatform().toString();
    		// String version = cap.getVersion().toString();
    		// System.out.println("os "+os+" version "+version);
		return browserName.trim();
	}
	
	public void Clck_Hidden_Element(WebElement element)
	{
		String js = "arguments[0].style.height='auto'; arguments[0].style.visibility='visible';";
		((JavascriptExecutor) yourWebDriverInstance).executeScript(js, element);
	}
	
	protected void press_enter_key() throws AWTException
	{
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
	}
	
	protected void copy_paste() throws AWTException
	{
		Robot r = new Robot();
		 r.keyPress(KeyEvent.VK_CONTROL);
		 r.keyPress(KeyEvent.VK_V);
		 r.keyRelease(KeyEvent.VK_V);
		 r.keyRelease(KeyEvent.VK_CONTROL);
	}
	
	protected void close() throws AWTException
	{
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_ALT);
		robot.keyPress(KeyEvent.VK_F4);
		robot.keyRelease(KeyEvent.VK_ALT);
		robot.keyRelease(KeyEvent.VK_F4);
	}
	
	protected void press_edit_form() throws AWTException
	{
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_E);
		robot.keyRelease(KeyEvent.VK_E);
		robot.keyRelease(KeyEvent.VK_CONTROL);
	}
	
	protected void go_to_form() throws AWTException
	{
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_N);
		robot.keyRelease(KeyEvent.VK_N);
		robot.keyRelease(KeyEvent.VK_CONTROL);
	}
	
	protected void press_Down_arrow_key() throws AWTException
	{
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_DOWN);
		robot.keyRelease(KeyEvent.VK_DOWN);
	}
	
	protected void press_Left_arrow_key() throws AWTException
	{
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_LEFT);
		robot.keyRelease(KeyEvent.VK_LEFT);
	}
	
	protected void press_right_arrow_key() throws AWTException
	{
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_RIGHT);
		robot.keyRelease(KeyEvent.VK_RIGHT);
	}
	
	protected void press_up_arrow_key() throws AWTException
	{
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_UP);
		robot.keyRelease(KeyEvent.VK_UP);
	}
	
	protected void Press_ctrl_s() throws AWTException
	{
		Robot robot = new Robot();
	robot.keyPress(KeyEvent.VK_CONTROL);
	robot.keyPress(KeyEvent.VK_S);
	robot.keyRelease(KeyEvent.VK_S);
	robot.keyRelease(KeyEvent.VK_CONTROL);
	}
	
	protected void press_page_up_key() throws AWTException
	{
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_PAGE_UP);
		robot.keyRelease(KeyEvent.VK_PAGE_UP);
		robot.keyRelease(KeyEvent.VK_CONTROL);
	}
	
	protected void press_page_down_key() throws AWTException
	{
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_PAGE_DOWN);
		robot.keyRelease(KeyEvent.VK_PAGE_DOWN);
		robot.keyRelease(KeyEvent.VK_CONTROL);
	}
	
	protected void press_tab_key() throws AWTException
	{
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_TAB);
		robot.keyRelease(KeyEvent.VK_TAB);
	}
	
	/* Get the latest file from a specific directory*/
	@SuppressWarnings("unused")
	private File getLatestFilefromDir(String dirPath){
	    File dir = new File(dirPath);
	    File[] files = dir.listFiles();
	    if (files == null || files.length == 0) {
	        return null;
	    }
	
	    File lastModifiedFile = files[0];
	    for (int i = 1; i < files.length; i++) {
	       if (lastModifiedFile.lastModified() < files[i].lastModified()) {
	           lastModifiedFile = files[i];
	       }
	    }
	    return lastModifiedFile;
	}

	

	

	@SuppressWarnings("deprecation")
	public static  String readData(int coloum, int rows,  String sheetname) throws IOException
	{


		String data=null;
		Workbook wb=null;
		String fileName="Testdata.xlsx";
		FileInputStream fileInputStream = new FileInputStream(fileName);
		String fileExtensionName = fileName.substring(fileName.indexOf("."));
		if(fileExtensionName.equals(".xlsx")){		  	 
			wb = new XSSFWorkbook(fileInputStream);	 
		}	 
		else if(fileExtensionName.equals(".xls")){	 
			wb = new HSSFWorkbook(fileInputStream);	 
		}
		Sheet sheet = wb.getSheet(sheetname);
		Row row = sheet.getRow(rows);
		Cell cell = row.getCell(coloum);
		cell.setCellType(Cell.CELL_TYPE_STRING);
		if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
			System.out.println("string:" +cell.getStringCellValue()+"::");
			data=cell.getStringCellValue();
		}
		if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
			System.out.println("numeric: " + cell.getNumericCellValue());
		}
		System.out.println("any:" + cell.toString()+":::");
		data=cell.toString();
		return data;
	}

	@SuppressWarnings({ "null", "deprecation" })
	public void writeData(int coloum, int rows, String text, String sheetname, String color) throws EncryptedDocumentException, InvalidFormatException, IOException
	{
		Workbook wb1=null;
		Sheet sheet;
		Cell cell;
		Row row;
		CellStyle style=null;
		String fileName="TST-TC-BOUNCEFORM_PORTAL.xlsx";
		FileInputStream fileInputStream = new FileInputStream(fileName);
		String fileExtensionName = fileName.substring(fileName.indexOf(".")); 
		if(fileExtensionName.equals(".xlsx")){

			wb1 = new XSSFWorkbook(fileInputStream);
			style = wb1.createCellStyle();

			Font font = wb1.createFont();
			if(color.equalsIgnoreCase("Green"))
			{
				font.setColor(IndexedColors.GREEN.getIndex());
			}
			else if(color.equalsIgnoreCase("Red"))
			{
				font.setColor(IndexedColors.RED.getIndex());
			}
			else
			{
				font.setColor(IndexedColors.BLACK.getIndex());
			}

			style.setFont(font);
			style.setBorderBottom(CellStyle.BORDER_THIN);

			style.setBorderLeft(CellStyle.BORDER_THIN);

			style.setBorderRight(CellStyle.BORDER_THIN);

			style.setBorderTop(CellStyle.BORDER_THIN);

		}
		
		else if(fileExtensionName.equals(".xls")){

			wb1 = new HSSFWorkbook(fileInputStream);
			style = wb1.createCellStyle();
			Font font = wb1.createFont();

			if(color.equalsIgnoreCase("Green"))
			{
				font.setColor(HSSFColor.GREEN.index);
			}
			else if(color.equalsIgnoreCase("Red"))
			{
				font.setColor(HSSFColor.RED.index);
			}
			else
			{
				font.setColor(HSSFColor.BLACK.index);
			}
			style.setFont(font);
			style.setBorderBottom(CellStyle.BORDER_THIN);

			style.setBorderLeft(CellStyle.BORDER_THIN);

			style.setBorderRight(CellStyle.BORDER_THIN);

			style.setBorderTop(CellStyle.BORDER_THIN);

		} 
		
		if(wb1==null)
		{
			sheet = wb1.createSheet();
		}
		
		sheet = wb1.getSheet(sheetname);
		row = sheet.getRow(rows);
		if(row==null)
		{
			row = sheet.createRow(rows);
		}
		
		cell = row.getCell(coloum);
		
		if (cell == null)
			cell = row.createCell(coloum);  
		cell.setCellType(Cell.CELL_TYPE_STRING);
		cell.setCellValue(text);
		cell.setCellStyle(style);
		FileOutputStream fileOut = new FileOutputStream("TST-TC-BOUNCEFORM_PORTAL.xlsx");
		wb1.write(fileOut);
		fileOut.close();

	}


	@SuppressWarnings({ "null", "deprecation" })
	public void writeData_TC(int coloum, int rows, String text, String sheetname, String color) throws EncryptedDocumentException, InvalidFormatException, IOException
	{
		Workbook wb1=null;
		Sheet sheet;
		Cell cell;
		Row row;
		CellStyle style=null;
		String fileName="TST-TC-BOUNCEFORM_PORTAL.xlsx";
		FileInputStream fileInputStream = new FileInputStream(fileName);
		String fileExtensionName = fileName.substring(fileName.indexOf(".")); 
		if(fileExtensionName.equals(".xlsx")){

			wb1 = new XSSFWorkbook(fileInputStream);
			style = wb1.createCellStyle();

			Font font = wb1.createFont();
			if(color.equalsIgnoreCase("Green"))
			{
				font.setColor(IndexedColors.GREEN.getIndex());
			}
			else if(color.equalsIgnoreCase("Red"))
			{
				font.setColor(IndexedColors.RED.getIndex());
			}
			else
			{
				font.setColor(IndexedColors.BLACK.getIndex());
			}

			style.setFont(font);
			style.setBorderBottom(CellStyle.BORDER_THIN);

			style.setBorderLeft(CellStyle.BORDER_THIN);

			style.setBorderRight(CellStyle.BORDER_THIN);

			style.setBorderTop(CellStyle.BORDER_THIN);

		}
		
		else if(fileExtensionName.equals(".xls")){

			wb1 = new HSSFWorkbook(fileInputStream);
			style = wb1.createCellStyle();
			Font font = wb1.createFont();

			if(color.equalsIgnoreCase("Green"))
			{
				font.setColor(HSSFColor.GREEN.index);
			}
			else if(color.equalsIgnoreCase("Red"))
			{
				font.setColor(HSSFColor.RED.index);
			}
			else
			{
				font.setColor(HSSFColor.BLACK.index);
			}
			style.setFont(font);
			style.setBorderBottom(CellStyle.BORDER_THIN);

			style.setBorderLeft(CellStyle.BORDER_THIN);

			style.setBorderRight(CellStyle.BORDER_THIN);

			style.setBorderTop(CellStyle.BORDER_THIN);

		} 
		
		if(wb1==null)
		{
			sheet = wb1.createSheet();
		}
		sheet = wb1.getSheet(sheetname);
		row = sheet.getRow(rows);
		if(row==null)
		{
			row = sheet.createRow(rows);
		}
		cell = row.getCell(coloum);
		if (cell == null)
			cell = row.createCell(coloum);  
		cell.setCellType(Cell.CELL_TYPE_STRING);
		cell.setCellValue(text);
		cell.setCellStyle(style);
		FileOutputStream fileOut = new FileOutputStream("TST-TC-BOUNCEFORM_PORTAL.xlsx");
		wb1.write(fileOut);
		fileOut.close();

	}


	@SuppressWarnings({ "null", "deprecation" })
	public void writeData_TD(int coloum, int rows, String text, String sheetname, String color) throws EncryptedDocumentException, InvalidFormatException, IOException
	{
		Workbook wb1=null;
		Sheet sheet;
		Cell cell;
		Row row;
		CellStyle style=null;
		String fileName="Testdata.xlsx";
		FileInputStream fileInputStream = new FileInputStream(fileName);
		String fileExtensionName = fileName.substring(fileName.indexOf(".")); 
		if(fileExtensionName.equals(".xlsx")){

			wb1 = new XSSFWorkbook(fileInputStream);
			style = wb1.createCellStyle();

			Font font = wb1.createFont();
			if(color.equalsIgnoreCase("Green"))
			{
				font.setColor(IndexedColors.GREEN.getIndex());
			}
			else if(color.equalsIgnoreCase("Red"))
			{
				font.setColor(IndexedColors.RED.getIndex());
			}
			else
			{
				font.setColor(IndexedColors.BLACK.getIndex());
			}

			style.setFont(font);
			style.setBorderBottom(CellStyle.BORDER_THIN);

			style.setBorderLeft(CellStyle.BORDER_THIN);

			style.setBorderRight(CellStyle.BORDER_THIN);

			style.setBorderTop(CellStyle.BORDER_THIN);

		}
		else if(fileExtensionName.equals(".xls")){

			wb1 = new HSSFWorkbook(fileInputStream);
			style = wb1.createCellStyle();
			Font font = wb1.createFont();

			if(color.equalsIgnoreCase("Green"))
			{
				font.setColor(HSSFColor.GREEN.index);
			}
			else if(color.equalsIgnoreCase("Red"))
			{
				font.setColor(HSSFColor.RED.index);
			}
			else
			{
				font.setColor(HSSFColor.BLACK.index);
			}
			style.setFont(font);
			style.setBorderBottom(CellStyle.BORDER_THIN);

			style.setBorderLeft(CellStyle.BORDER_THIN);

			style.setBorderRight(CellStyle.BORDER_THIN);

			style.setBorderTop(CellStyle.BORDER_THIN);

		} 
		
		if(wb1==null)
		{
			sheet = wb1.createSheet();
		}
		sheet = wb1.getSheet(sheetname);
		row = sheet.getRow(rows);
		
		if(row==null)
		{
			row = sheet.createRow(rows);
		}
		
		cell = row.getCell(coloum);
		if (cell == null)
			cell = row.createCell(coloum);  
		cell.setCellType(Cell.CELL_TYPE_STRING);
		cell.setCellValue(text);
		cell.setCellStyle(style);
		FileOutputStream fileOut = new FileOutputStream("Testdata.xlsx");
		wb1.write(fileOut);
		fileOut.close();

	}



	public void executionDetail() throws EncryptedDocumentException, InvalidFormatException, IOException
	{
		writeData(1, 4, "Selenium with Sikuli", "Testcases", "Black");
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		//get current date time with Date()
		Date date = new Date();
		System.out.println(dateFormat.format(date));

		writeData(5, 4, dateFormat.format(date), "Testcases", "Black");

	}
	
	public void OnClick(String xpath) {
		String clickby = "";
		String clickvalue = "";
		int pathlength = xpath.length();
		int sepIndex = xpath.indexOf('#');
		clickby = xpath.substring(0, sepIndex);
		clickvalue = xpath.substring(sepIndex + 1, pathlength);
		if (clickby.equals("xpath")) {
			driver.findElement(By.xpath(clickvalue)).click();
			System.out.println("click by =" + clickby);
			System.out.println("click value =" + clickvalue);
		}
		if (clickby.equals("id")) {
			driver.findElement(By.id(clickvalue)).click();
			System.out.println("click by =" + clickby);
			System.out.println("click value =" + clickvalue);
		}
		if (clickby.equals("classname")) {
			driver.findElement(By.className(clickvalue)).click();
			System.out.println("click by =" + clickby);
			System.out.println("click value =" + clickvalue);
		}
		if (clickby.equals("cssselector")) {
			driver.findElement(By.cssSelector(clickvalue)).click();
			System.out.println("click by =" + clickby);
			System.out.println("click value =" + clickvalue);
		}
		if (clickby.equals("linktext")) {
			driver.findElement(By.linkText(clickvalue)).click();
			System.out.println("click by =" + clickby);
			System.out.println("click value =" + clickvalue);
		}
		if (clickby.equals("name")) {
			driver.findElement(By.name(clickvalue)).click();
			System.out.println("click by =" + clickby);
			System.out.println("click value =" + clickvalue);
		}
		if (clickby.equals("partiallinktext")) {
			driver.findElement(By.partialLinkText(clickvalue)).click();
			System.out.println("click by =" + clickby);
			System.out.println("click value =" + clickvalue);
		}
		if (clickby.equals("tagname")) {
			driver.findElement(By.tagName(clickvalue)).click();
			System.out.println("click by =" + clickby);
			System.out.println("click value =" + clickvalue);
		}
	}
	
	//Code for Gettext
	public String getText(String xpath) {
		String clickby = "";
		String clickvalue = "";
		String alerttext=null;
		int pathlength = xpath.length();
		int sepIndex = xpath.indexOf('#');
		clickby = xpath.substring(0, sepIndex);
		clickvalue = xpath.substring(sepIndex + 1, pathlength);
		if (clickby.equals("xpath")) {
			alerttext=driver.findElement(By.xpath(clickvalue)).getText();
			System.out.println("click by =" + clickby);
			System.out.println("click value =" + clickvalue);
		}
		if (clickby.equals("id")) {
			alerttext=driver.findElement(By.id(clickvalue)).getText();
			System.out.println("click by =" + clickby);
			System.out.println("click value =" + clickvalue);
		}
		if (clickby.equals("classname")) {
			alerttext=driver.findElement(By.className(clickvalue)).getText();
			System.out.println("click by =" + clickby);
			System.out.println("click value =" + clickvalue);
		}
		if (clickby.equals("cssselector")) {
			alerttext=driver.findElement(By.cssSelector(clickvalue)).getText();
			System.out.println("click by =" + clickby);
			System.out.println("click value =" + clickvalue);
		}
		if (clickby.equals("linktext")) {
			alerttext=driver.findElement(By.linkText(clickvalue)).getText();
			System.out.println("click by =" + clickby);
			System.out.println("click value =" + clickvalue);
		}
		if (clickby.equals("name")) {
			alerttext=driver.findElement(By.name(clickvalue)).getText();
			System.out.println("click by =" + clickby);
			System.out.println("click value =" + clickvalue);
		}
		if (clickby.equals("partiallinktext")) {
			alerttext=driver.findElement(By.partialLinkText(clickvalue)).getText();
			System.out.println("click by =" + clickby);
			System.out.println("click value =" + clickvalue);
		}
		if (clickby.equals("tagname")) {
			alerttext=driver.findElement(By.tagName(clickvalue)).getText();
			System.out.println("click by =" + clickby);
			System.out.println("click value =" + clickvalue);
		}
		return alerttext;
	}
	//code for typing values in the textbox
	public void TypeIn(String xpath,String text){
		String clickby = "";
		String clickvalue = "";
		int pathlength = xpath.length();
		int sepIndex = xpath.indexOf('#');
		clickby = xpath.substring(0, sepIndex);
		clickvalue = xpath.substring(sepIndex + 1, pathlength);
		if (clickby.equals("xpath")) {
			driver.findElement(By.xpath(clickvalue)).clear();
			driver.findElement(By.xpath(clickvalue)).sendKeys(text);
			System.out.println("click by =" + clickby);
			System.out.println("click value =" + clickvalue);
		}
		if (clickby.equals("id")) {
			driver.findElement(By.id(clickvalue)).clear();
			driver.findElement(By.id(clickvalue)).sendKeys(text);
			System.out.println("click by =" + clickby);
			System.out.println("click value =" + clickvalue);
		}
		if (clickby.equals("classname")) {
			driver.findElement(By.className(clickvalue)).clear();
			driver.findElement(By.className(clickvalue)).sendKeys(text);
			System.out.println("click by =" + clickby);
			System.out.println("click value =" + clickvalue);
		}
		if (clickby.equals("cssselector")) {
			driver.findElement(By.cssSelector(clickvalue)).clear();
			driver.findElement(By.cssSelector(clickvalue)).sendKeys(text);
			System.out.println("click by =" + clickby);
			System.out.println("click value =" + clickvalue);
		}
		if (clickby.equals("linktext")) {
			driver.findElement(By.linkText(clickvalue)).clear();
			driver.findElement(By.linkText(clickvalue)).sendKeys(text);
			System.out.println("click by =" + clickby);
			System.out.println("click value =" + clickvalue);
		}
		if (clickby.equals("name")) {
			driver.findElement(By.name(clickvalue)).clear();
			driver.findElement(By.name(clickvalue)).sendKeys(text);
			System.out.println("click by =" + clickby);
			System.out.println("click value =" + clickvalue);
		}
		if (clickby.equals("partiallinktext")) {
			driver.findElement(By.partialLinkText(clickvalue)).clear();
			driver.findElement(By.partialLinkText(clickvalue)).sendKeys(text);
			System.out.println("click by =" + clickby);
			System.out.println("click value =" + clickvalue);
		}
		if (clickby.equals("tagname")) {
			driver.findElement(By.tagName(clickvalue)).clear();
			driver.findElement(By.tagName(clickvalue)).sendKeys(text);
			System.out.println("click by =" + clickby);
			System.out.println("click value =" + clickvalue);
		}
	}
	//Clearing the Text Field
	public void cleartextfield(String xpath) {
		String clickby = "";
		String clickvalue = "";
		int pathlength = xpath.length();
		int sepIndex = xpath.indexOf('#');
		clickby = xpath.substring(0, sepIndex);
		clickvalue = xpath.substring(sepIndex + 1, pathlength);
		if (clickby.equals("id")) {
			driver.findElement(By.id(clickvalue)).clear();
			System.out.println("click by =" + clickby);
			System.out.println("click value =" + clickvalue);
		}
	}
	//Checking the field selected or Not
	public boolean isEnabled(String xpath) {
		String clickby = "";
		String clickvalue = "";
		int pathlength = xpath.length();
		int sepIndex = xpath.indexOf('#');
		clickby = xpath.substring(0, sepIndex);
		clickvalue = xpath.substring(sepIndex + 1, pathlength);
		if (clickby.equals("id")) {
			if(driver.findElement(By.id(clickvalue)).isEnabled())
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		return false;
	}
	/* To Select data in drop dowm using value */	
	public void selectbyValue(String xpath,String value){
		String clickby = "";
		String clickvalue = "";
		int pathlength = xpath.length();
		int sepIndex = xpath.indexOf('#');
		clickby = xpath.substring(0, sepIndex);
		clickvalue = xpath.substring(sepIndex + 1, pathlength);
		if (clickby.equals("xpath")) {
			Select anyValue=new Select(driver.findElement(By.xpath(clickvalue)));
			anyValue.selectByValue(value);
		}
		if (clickby.equals("id")) {
			Select anyValue=new Select(driver.findElement(By.id(clickvalue)));
			anyValue.selectByValue(value);
		}
	}

	/* To Select data in drop dowm using index */
	public void selectbyIndex(String xpath,int index){
		String clickby = "";
		String clickvalue = "";
		int pathlength = xpath.length();
		int sepIndex = xpath.indexOf('#');
		clickby = xpath.substring(0, sepIndex);
		clickvalue = xpath.substring(sepIndex + 1, pathlength);
		if (clickby.equals("xpath")) {
			Select anyValue=new Select(driver.findElement(By.xpath(clickvalue)));
			anyValue.selectByIndex(index);
		}
		if (clickby.equals("id")) {
			Select anyValue=new Select(driver.findElement(By.id(clickvalue)));
			anyValue.selectByIndex(index);
		}
	}

	/* To Select data in drop dowm using visible text */
	public void selectbyVisibletext(String xpath,String value){
		String clickby = "";
		String clickvalue = "";
		int pathlength = xpath.length();
		int sepIndex = xpath.indexOf('#');
		clickby = xpath.substring(0, sepIndex);
		clickvalue = xpath.substring(sepIndex + 1, pathlength);
		if (clickby.equals("xpath")) {
			Select anyValue=new Select(driver.findElement(By.xpath(clickvalue)));
			anyValue.selectByVisibleText(value);
		}
		if (clickby.equals("id")) {
			Select anyValue=new Select(driver.findElement(By.id(clickvalue)));
			anyValue.selectByVisibleText(value);
		}
	}

	public boolean VerifyDetails(String xpath, String text) {
		String clickby = "";
		String clickvalue = "";
		int pathlength = xpath.length();
		int sepIndex = xpath.indexOf('#');
		clickby = xpath.substring(0, sepIndex);
		clickvalue = xpath.substring(sepIndex + 1, pathlength);
		boolean result = false;
		if (clickby.equals("xpath")) {
			result = driver.findElement(By.xpath(clickvalue)).getText().contains(text);
		}
		if (clickby.equals("id")) {
			result = driver.findElement(By.id(clickvalue)).getText().contains(text);
		}
		return result;
	}

	/* To Verify Element is Present in the Web page */
	public boolean iselementpresent(List<WebElement> list) {


		if(((List<WebElement>) list).size()!=0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}



	public boolean iselementpresent(By element)
	{

		WebDriverWait wait1=new WebDriverWait(driver, 10);
		try
		{
			wait1.until(ExpectedConditions.presenceOfElementLocated(element));
			return true;
		}
		catch(Exception e)
		{
			return false;
		}
	}

	/* To Verify Text is Present in the Web Page */
	public boolean isTextPresent(String findText) {
		try {
			boolean b = driver.getPageSource().contains(findText);
			return b;
		} catch (Exception e) {
			return false;
		}
	}


	// code to click the menu and submenu method -1 
	public void click_menu(String menu,String submenu) throws InterruptedException
	{
		Actions actions = new Actions(driver);
		WebElement addusers = driver.findElement(By.linkText(menu));
		actions.moveToElement(addusers).build().perform();
		//wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText(submenu)));
		driver.findElement(By.linkText(submenu)).click();
		Thread.sleep(10000);
	}

	// code to click menu and submenu method - 2 
	public void click_menu_tosubmenu(String menu, String submenu1, String submenu2) throws InterruptedException
	{
		Actions actions=new Actions(driver);
		WebElement mainMenu = driver.findElement(By.linkText(menu));
		actions.moveToElement(mainMenu).build().perform();
		//wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText(submenu1)));
		WebElement subMenu = driver.findElement(By.linkText(submenu1));
		actions.moveToElement(subMenu).build().perform();
		//wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText(submenu2)));
		WebElement childsubMenu = driver.findElement(By.linkText(submenu2));
		actions.moveToElement(childsubMenu);
		actions.click().build().perform();
		Thread.sleep(10000);
	}


	public boolean isTimeBetweenTwoTime(String string1, String string2, String someRandomTime) throws java.text.ParseException
	{

		boolean flag;
		Date time1 = new SimpleDateFormat("hh:mm:ss a").parse(string1);
		Calendar calendar1 = Calendar.getInstance();
		calendar1.setTime(time1);


		Date time2 = new SimpleDateFormat("hh:mm:ss a").parse(string2);
		Calendar calendar2 = Calendar.getInstance();
		calendar2.setTime(time2);
		calendar2.add(Calendar.DATE, 1);


		Date d = new SimpleDateFormat("hh:mm:ss").parse(someRandomTime);
		Calendar calendar3 = Calendar.getInstance();
		calendar3.setTime(d);
		calendar3.add(Calendar.DATE, 1);
		Date x = calendar3.getTime();
		if (x.after(calendar1.getTime()) && x.before(calendar2.getTime())) {
			//checkes whether the current time is between 14:49:00 and 20:11:13.
			flag=true;
		}
		else
		{
			flag=false;
		}
		System.out.println(calendar1.getTime() + "  "+calendar2.getTime());
		return flag;
	}


	@SuppressWarnings("deprecation")
	public boolean checkDateisbetweentwodates(String start,String End, String givendate)
	{
		Date date1=new Date(start);
		Date date2=new Date(End);
		Date date3=new Date(givendate);
		// assume these are set to something
		// the date in question

		if(date3.after(date1) && date3.before(date2))
		{
			return true;

		}
		else
		{
			if(date3.equals(date1))
			{
				return true;
			}
			else if (date3.equals(date2)) 
			{
				return true;
			}
			else
			{
				return false;
			}

		}


	}   
	
	//Getting current date and time
	public String currentDate()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		System.out.println(dateFormat.format(date).toString()); //2014/08/06 15:59:48
		return dateFormat.format(date);
	}


	public String getText(String xpath, int get) {
		String clickby = "";
		String clickvalue = "";
		String alerttext=null;
		int pathlength = xpath.length();
		int sepIndex = xpath.indexOf('#');
		clickby = xpath.substring(0, sepIndex);
		clickvalue = xpath.substring(sepIndex + 1, pathlength);
		if (clickby.equals("xpath")) {
			alerttext=driver.findElements(By.xpath(clickvalue)).get(get).getText();
			System.out.println("click by =" + clickby);
			System.out.println("click value =" + clickvalue);
		}
		if (clickby.equals("id")) {
			alerttext=driver.findElements(By.id(clickvalue)).get(get).getText();
			System.out.println("click by =" + clickby);
			System.out.println("click value =" + clickvalue);
		}
		if (clickby.equals("classname")) {
			alerttext=driver.findElements(By.className(clickvalue)).get(get).getText();
			System.out.println("click by =" + clickby);
			System.out.println("click value =" + clickvalue);
		}
		if (clickby.equals("cssselector")) {
			alerttext=driver.findElements(By.cssSelector(clickvalue)).get(get).getText();
			System.out.println("click by =" + clickby);
			System.out.println("click value =" + clickvalue);
		}
		if (clickby.equals("linktext")) {
			alerttext=driver.findElements(By.linkText(clickvalue)).get(get).getText();
			System.out.println("click by =" + clickby);
			System.out.println("click value =" + clickvalue);
		}
		if (clickby.equals("name")) {
			alerttext=driver.findElements(By.name(clickvalue)).get(get).getText();
			System.out.println("click by =" + clickby);
			System.out.println("click value =" + clickvalue);
		}
		if (clickby.equals("partiallinktext")) {
			alerttext=driver.findElements(By.partialLinkText(clickvalue)).get(get).getText();
			System.out.println("click by =" + clickby);
			System.out.println("click value =" + clickvalue);
		}
		if (clickby.equals("tagname")) {
			alerttext=driver.findElements(By.tagName(clickvalue)).get(get).getText();
			System.out.println("click by =" + clickby);
			System.out.println("click value =" + clickvalue);
		}
		return alerttext;
	}
	
	//code for typing values in the textbox
	public void TypeIn(String xpath,String text, int get){
		String clickby = "";
		String clickvalue = "";
		int pathlength = xpath.length();
		int sepIndex = xpath.indexOf('#');
		clickby = xpath.substring(0, sepIndex);
		clickvalue = xpath.substring(sepIndex + 1, pathlength);
		if (clickby.equals("xpath")) {
			driver.findElements(By.xpath(clickvalue)).get(get).clear();
			driver.findElements(By.xpath(clickvalue)).get(get).sendKeys(text);
			System.out.println("click by =" + clickby);
			System.out.println("click value =" + clickvalue);
		}
		if (clickby.equals("id")) {
			driver.findElements(By.id(clickvalue)).get(get).clear();
			driver.findElements(By.id(clickvalue)).get(get).sendKeys(text);
			System.out.println("click by =" + clickby);
			System.out.println("click value =" + clickvalue);
		}
		if (clickby.equals("classname")) {
			driver.findElements(By.className(clickvalue)).get(get).clear();
			driver.findElements(By.className(clickvalue)).get(get).sendKeys(text);
			System.out.println("click by =" + clickby);
			System.out.println("click value =" + clickvalue);
		}
		if (clickby.equals("cssselector")) {
			driver.findElements(By.cssSelector(clickvalue)).get(get).clear();
			driver.findElements(By.cssSelector(clickvalue)).get(get).sendKeys(text);
			System.out.println("click by =" + clickby);
			System.out.println("click value =" + clickvalue);
		}
		if (clickby.equals("linktext")) {
			driver.findElements(By.linkText(clickvalue)).get(get).clear();
			driver.findElements(By.linkText(clickvalue)).get(get).sendKeys(text);
			System.out.println("click by =" + clickby);
			System.out.println("click value =" + clickvalue);
		}
		if (clickby.equals("name")) {
			driver.findElements(By.name(clickvalue)).get(get).clear();
			driver.findElements(By.name(clickvalue)).get(get).sendKeys(text);
			System.out.println("click by =" + clickby);
			System.out.println("click value =" + clickvalue);
		}
		if (clickby.equals("partiallinktext")) {
			driver.findElements(By.partialLinkText(clickvalue)).get(get).clear();
			driver.findElements(By.partialLinkText(clickvalue)).get(get).sendKeys(text);
			System.out.println("click by =" + clickby);
			System.out.println("click value =" + clickvalue);
		}
		if (clickby.equals("tagname")) {
			driver.findElements(By.tagName(clickvalue)).get(get).clear();
			driver.findElements(By.tagName(clickvalue)).get(get).sendKeys(text);
			System.out.println("click by =" + clickby);
			System.out.println("click value =" + clickvalue);
		}
	}
	
	public void OnClick(String xpath,int get) {
		String clickby = "";
		String clickvalue = "";
		int pathlength = xpath.length();
		int sepIndex = xpath.indexOf('#');
		clickby = xpath.substring(0, sepIndex);
		clickvalue = xpath.substring(sepIndex + 1, pathlength);
		if (clickby.equals("xpath")) {
			driver.findElements(By.xpath(clickvalue)).get(get).click();
			System.out.println("click by =" + clickby);
			System.out.println("click value =" + clickvalue);
		}
		if (clickby.equals("id")) {
			driver.findElements(By.id(clickvalue)).get(get).click();
			System.out.println("click by =" + clickby);
			System.out.println("click value =" + clickvalue);
		}
		if (clickby.equals("classname")) {
			driver.findElements(By.className(clickvalue)).get(get).click();
			System.out.println("click by =" + clickby);
			System.out.println("click value =" + clickvalue);
		}
		if (clickby.equals("cssselector")) {
			driver.findElements(By.cssSelector(clickvalue)).get(get).click();
			System.out.println("click by =" + clickby);
			System.out.println("click value =" + clickvalue);
		}
		if (clickby.equals("linktext")) {
			driver.findElements(By.linkText(clickvalue)).get(get).click();
			System.out.println("click by =" + clickby);
			System.out.println("click value =" + clickvalue);
		}
		if (clickby.equals("name")) {
			driver.findElements(By.name(clickvalue)).get(get).click();
			System.out.println("click by =" + clickby);
			System.out.println("click value =" + clickvalue);
		}
		if (clickby.equals("partiallinktext")) {
			driver.findElements(By.partialLinkText(clickvalue)).get(get).click();
			System.out.println("click by =" + clickby);
			System.out.println("click value =" + clickvalue);
		}
		if (clickby.equals("tagname")) {
			driver.findElements(By.tagName(clickvalue)).get(get).click();
			System.out.println("click by =" + clickby);
			System.out.println("click value =" + clickvalue);
		}
	}


	//To verify Element is clickable 
	@SuppressWarnings("unused")
	public boolean iselementclickable(String xpath) {

		String clickby = "";
		String clickvalue = "";
		String alerttext=null;
		int pathlength = xpath.length();
		int sepIndex = xpath.indexOf('#');
		clickby = xpath.substring(0, sepIndex);
		clickvalue = xpath.substring(sepIndex + 1, pathlength);
		if (clickby.equals("xpath")) {
			try
			{
				WebDriverWait wait=new WebDriverWait(driver, 2);
				wait.until(ExpectedConditions.elementToBeClickable(By.xpath(clickvalue)));
				return true;
			}
			catch(Exception e)
			{
				return false;
			}
		}
		if (clickby.equals("id")) {
			try
			{
				WebDriverWait wait=new WebDriverWait(driver, 8);
				wait.until(ExpectedConditions.elementToBeClickable(By.id(clickvalue)));
				return true;
			}
			catch(Exception e)
			{
				System.out.println(e);
				return false;
			}

		}
		return false;

	}


	public boolean isDisplayed(String xpath)
	{
		String clickby = "";
		String clickvalue = "";
		int pathlength = xpath.length();
		int sepIndex = xpath.indexOf('#');
		clickby = xpath.substring(0, sepIndex);
		clickvalue = xpath.substring(sepIndex + 1, pathlength);
		if (clickby.equals("xpath")) {

			if(driver.findElement(By.xpath(clickvalue)).isDisplayed())
			{
				return true;
			}
			else
			{
				return false;
			}

		}
		if (clickby.equals("id")) {
			if(driver.findElement(By.xpath(clickvalue)).isDisplayed())
			{
				return true;
			}
			else
			{
				return false;
			}

		}
		if (clickby.equals("classname")) {
			if(driver.findElement(By.xpath(clickvalue)).isDisplayed())
			{
				return true;
			}
			else
			{
				return false;
			}

		}
		if (clickby.equals("cssselector")) {
			if(driver.findElement(By.xpath(clickvalue)).isDisplayed())
			{
				return true;
			}
			else
			{
				return false;
			}

		}
		if (clickby.equals("linktext")) {
			if(driver.findElement(By.xpath(clickvalue)).isDisplayed())
			{
				return true;
			}
			else
			{
				return false;
			}

		}
		if (clickby.equals("name")) {
			if(driver.findElement(By.xpath(clickvalue)).isDisplayed())
			{
				return true;
			}
			else
			{
				return false;
			}

		}
		if (clickby.equals("partiallinktext")) {
			if(driver.findElement(By.xpath(clickvalue)).isDisplayed())
			{
				return true;
			}
			else
			{
				return false;
			}

		}
		if (clickby.equals("tagname")) {
			if(driver.findElement(By.xpath(clickvalue)).isDisplayed())
			{
				return true;
			}
			else
			{
				return false;
			}

		}
		return false;
	}


	/*public void dateselection(String setdate, String setmonth, String setyear) throws InterruptedException
	{
		boolean flag=false;
		for(int i=0;i<120;i++)
		{
			
			Thread.sleep(1000);
			String month=driver.findElement(monthid).getText();
			System.out.println(month + "====" + setmonth+", "+setyear);
			
			if(month.equalsIgnoreCase(setmonth+", "+setyear))
			{
				break;
			}
			else
			{
				driver.findElement(previousmonth).click();
			}
		}


		for(int j=0;j<6;j++)
		{
			for(int k=0;k<7;k++)
			{

				String date=driver.findElement(By.id("AdminContentPlaceHolder_cldrextStartdate_day_"+j+"_"+k+"")).getText();
				if(date.equals("1"))
				{
					flag=true;
				}
				if(flag)
				{
					if(date.equals(setdate))
					{
						driver.findElement(By.id("AdminContentPlaceHolder_cldrextStartdate_day_"+j+"_"+k+"")).click();
						break;
					}else
					{

					}

				}
			}
		}
	}*/




	public void getscreenshot_Pass(String tc_name) throws Exception 
	{
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		//The below method will save the screen shot in d drive with name "screenshot.png"
		FileUtils.copyFile(scrFile, new File("Passed\\"+tc_name+".png"));
	}

	public void getscreenshot_Fail(String tc_name) throws Exception 
	{
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		//The below method will save the screen shot in d drive with name "screenshot.png"
		FileUtils.copyFile(scrFile, new File("Failed\\"+tc_name+".png"));
	}


	public void getscreenshot_NA(String tc_name) throws Exception 
	{
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		//The below method will save the screen shot in d drive with name "screenshot.png"
		FileUtils.copyFile(scrFile, new File("Skipped\\"+tc_name+".png"));
	}
	public void getscreenshot_Alert(String tc_name) throws Exception 
	{

		BufferedImage image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
		ImageIO.write(image, "png", new File("Passed\\"+tc_name+".png"));

	}
	public String creator()
	{
		Date today = Calendar.getInstance().getTime();

		// (2) create a date "formatter" (the date format we want)
		SimpleDateFormat formatter = new SimpleDateFormat("dd-hh.mm.ss");

		// (3) create a new String using the date format we want
		String creator1 = formatter.format(today);

		String creator2=creator1.replaceAll("-", "");

		String creator3=creator2.replace(".","");

		return creator3;

	}
	
	public String colorCode(String colorCode)
	{
		Map<String, String> color= new HashMap<String, String>();
		color.put("White", 	"rgba(-255, 255, 255");
		color.put("Red", 	"rgba(255, 0, 0");
		color.put("Lime", 	"rgba(0, 255, 0");
		color.put("Blue", 	"rgba(0, 0, 255");
		color.put("Yellow", "rgba(255, 255, 0");
		color.put("Cyan / Aqua", "rgba(0, 255, 255");
		color.put("Magenta / Fuchsia", 	"rgba(255, 0, 255");
		color.put("Silver", 	"rgba(-192, 192, 192");
		color.put("Gray", 	"rgba(-128, 128, 128");
		color.put("Maroon", 	"rgba(128, 0, 0");
		color.put("Olive", 	"rgba(128, 128, 0");
		color.put("Green", 	"rgba(0, 128, 0");
		color.put("Purple", "rgba(128, 0, 128");
		color.put("Teal", 	"rgba(0, 128, 128");
		color.put("Navy", 	"rgba(0, 0, 128");
		System.out.println("The color values is :" + color.get(colorCode));
		return color.get(colorCode);		
	}
	
	public String monthCode(String monthCode)
	{
		Map<String, String> month= new HashMap<String, String>();
		month.put("January" , "01");
		month.put("February", "02");
		month.put("March", "03");
		month.put("April", "04");
		month.put("May", "05");
		month.put("June", "06");
		month.put("July", "07");
		month.put("August", "08");
		month.put("September", "09");
		month.put("October", "10");
		month.put("November", "11");
		month.put("December", "12");
		//System.out.println("The Month values is :" + month.get(monthCode));
		System.out.println("The Month values is :" + getKeyByValue(month,monthCode));
		return getKeyByValue(month,monthCode);		
	}
	
	public static <T, E> T getKeyByValue(Map<T, E> map, E value) {
	    for (Entry<T, E> entry : map.entrySet()) {
	        if (Objects.equals(value, entry.getValue())) {
	            return entry.getKey();
	        }
	    }
	    return null;
	}
	 public static String Monthselect(String str)
	 {
		Map<String, String> mp = new HashMap<String, String>();		
		mp.put("01", "January");
		mp.put("02", "February");
		mp.put("03", "March");
		mp.put("04", "April");
		mp.put("05", "May");
		mp.put("06", "June");
		mp.put("07", "July");
		mp.put("08", "August");
		mp.put("09", "September");
		mp.put("10", "October");
		mp.put("11", "November");
		mp.put("12", "December");		
		
		String month=(String) mp.get(str);
		
		 return month;
		 
	 }
	
	public String monthCodeSF(String monthCode)
	{
		Map<String, String> month= new HashMap<String, String>();
		month.put("January" , "Jan");
		month.put("February", "Feb");
		month.put("March", "Mar");
		month.put("April", "Apr");
		month.put("May", "May");
		month.put("June", "Jun");
		month.put("July", "Jul");
		month.put("August", "Aug");
		month.put("September", "Sep");
		month.put("October", "Oct");
		month.put("November", "Nov");
		month.put("December", "Dec");
		System.out.println("The Month values is :" + month.get(monthCode));
		return month.get(monthCode);		
	}
	
	public static String date(int args)
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	        
		Calendar calReturn = Calendar.getInstance();
		
		calReturn.add(Calendar.DATE, args);
				
		String daysback = dateFormat.format(calReturn.getTime());
		
		System.out.println(" Your required date is "+args+" : "+daysback);
				
		return daysback;
	}
	
	
	
	public WebElement todayDateSelect()
	{
	return	driver.findElement(By.id("AdminContentPlaceHolder_CalPurchaseDate_today"));
	}
	
	
	/*public void dateselec(int args) throws InterruptedException
	{
		boolean flag=false;
		Getting date for Calender selection
		String Date = date(args);
		Split Date
		String[] datesplit = Date.split("-");
		Set Date
		String setdate = datesplit[2].trim();
		System.out.println("Required date is :::: "+setdate);
		int setdateint = Integer.parseInt(setdate);
		Set Month
		String setmonth = datesplit[1].trim(); 
		System.out.println("Required month is :::: "+setmonth);
		String Month = monthCode(setmonth);
		int setmonthint = Integer.parseInt(setmonth);
		System.out.println("Integer value of required month is :::: "+setmonthint);
		String SetmonthtoSel = monthCodeSF(setmonth);		
		String SetmonthtoSel = monthCodeSF(monthCode(setmonth));
		Set Year
		String setyear = datesplit[0].trim();
		System.out.println("Required Year is :::: "+setyear);
		int setyearint = Integer.parseInt(setyear);
		
		wait.until(ExpectedConditions.presenceOfElementLocated(monthid));
		String monthInCalander=driver.findElement(monthid).getText();
		System.out.println("Value in calander is ::: "+monthInCalander);
		
		String[] mnthCal = monthInCalander.split(",");
		
		String mnthsplt = mnthCal[0].trim();
		System.out.println("Month in calender is :::: "+mnthsplt);
		monthCode(mnthsplt);
		int setmonthintCal = Integer.parseInt(monthCode(mnthsplt));
		System.out.println("Integer value of required month is :::: "+setmonthintCal);
		
		
		String yrsplt = mnthCal[1].trim();
		System.out.println("Year in calender is :::: "+yrsplt);
		int yrinCal = Integer.parseInt(yrsplt);
		
		System.out.println("-----------------------------------------------------------------------");
		
		for(int u=1;u<=1;u++)
		{
			driver.findElement(monthid).click();
			String YrInCalr=driver.findElement(monthid).getText();
			int YrInCalrin = Integer.parseInt(YrInCalr);
			System.out.println("Current month in calander ::: "+YrInCalrin);
			
			for(int q = 1;q<=120;q++)
			{
			
			System.out.println("Need to compare with requied "+q+" time is :: "+YrInCalrin);
			
			if (setyearint==YrInCalrin)
			{			
				break;
			}
			
			else if (setyearint<YrInCalrin)
			{
				wait.until(ExpectedConditions.presenceOfElementLocated(previousmonth));
				driver.findElement(previousmonth).click();
				
				YrInCalr=driver.findElement(By.id("AdminContentPlaceHolder_cldrextStartdate_title")).getText();
				YrInCalrin = Integer.parseInt(YrInCalr);
				}
			else
			{
				wait.until(ExpectedConditions.presenceOfElementLocated(nextmonth));
				driver.findElement(nextmonth).click();
				YrInCalr=driver.findElement(By.id("AdminContentPlaceHolder_cldrextStartdate_title")).getText();
				YrInCalrin = Integer.parseInt(YrInCalr);
			}
			}
		}
		
		System.out.println("-----------------------------------------------------------------------");
				
		String yrs=driver.findElement(monthid).getText();
		System.out.println("Year in calander is ::: "+yrs);
		int yers = Integer.parseInt(yrs);
		System.out.println("Years to check ::: "+setyearint);
		if(yers==setyearint){
			mainloop:
			for(int j=0;j<3;j++){
				System.out.println("J value : "+j);
			for(int k=0;k<4;k++){
				System.out.println("K value : "+k);
					String date=driver.findElement(By.xpath(".//*[@id='AdminContentPlaceHolder_CalPurchaseDate_month_"+j+"_"+k+"']")).getText().trim();
					System.out.println("Month values in date ::: "+date);
					System.out.println("Month values in SetmonthtoSel ::: "+SetmonthtoSel);
									
						if(date.equalsIgnoreCase(SetmonthtoSel)){
							driver.findElement(By.xpath(".//*[@id='AdminContentPlaceHolder_CalPurchaseDate_month_"+j+"_"+k+"']")).click();
							break mainloop;
						}}}}

		System.out.println("-----------------------------------------------------------------------");
		mainloop:
		for(int j=0;j<6;j++)
		{
			System.out.println("for iteration :: "+j);
			for(int k=0;k<7;k++)
			{
				System.out.println("for iteration :: "+k);
				
				String date=driver.findElement(By.xpath(".//*[@id='AdminContentPlaceHolder_CalPurchaseDate_day_"+j+"_"+k+"']")).getText();
				setdate = setdate.replaceFirst("^0*", "");
				System.out.println("Date is : "+date);
				System.out.println("Required date : "+setdate);
				if(date.equals("1"))
				{
					flag=true;
				}
				if(flag)
				{
					if(date.equals(setdate))
					{
						driver.findElement(By.xpath(".//*[@id='AdminContentPlaceHolder_CalPurchaseDate_day_"+j+"_"+k+"']")).click();
						break mainloop;
					}else
					{

					}

				}
			}
		}
		System.out.println("-----------------------------------------------------------------------");
	}*/
	

	
}



