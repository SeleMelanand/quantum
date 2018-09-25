package wrapperpackage;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;

public class WrapperClass {
	public RemoteWebDriver driver;
	public ArrayList<String> al = new ArrayList<String>();
	public String formattedstring ;

	public void browserlaunch() {
		// Launching chrome browser
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
		driver = new ChromeDriver();
		driver.get("https://qa1.quantumesolutions.com/");
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	public void login() {
		browserlaunch();
		typelementbyid("ctl00_cphContent_UserName", "nyb\\mortgagebanker");
		typelementbyid("ctl00_cphContent_Password", "Quantum@201");
		clickelementbyid("ctl00_cphContent_LoginButton");
	}

	public void elementidentify() {

	}

	// Typing values for Elements with differnet locators
	public void typelementbyid(String idvalue, String uname) {
		driver.findElementById(idvalue).sendKeys(uname);
	}

	public void typelementbyidandmouseclick(String idvalue, String text) {
		driver.findElementById(idvalue).sendKeys(text, Keys.ENTER);
	}

	public void typelementbyxpath(String xpathvalue, String uname) {
		driver.findElementByXPath(xpathvalue).sendKeys(uname);
	}

	public void typelementbyxpathandmouseclick(String xpathvalue, String text) {
		driver.findElementByXPath(xpathvalue).sendKeys(text, Keys.ENTER.TAB);
	}

	// Click Elements with differnet locators
	public void clickelementbyid(String idvalue) {
		driver.findElementById(idvalue).click();
	}

	public void clickelementbyxpath(String xpathvalue) {
		driver.findElementByXPath(xpathvalue).click();
	}

	public void mouseover(WebElement elementval) {
		Actions actionmove = new Actions(driver);
		actionmove.moveToElement(elementval).build().perform();
	}

	public void mouseoverclick(WebElement elementval) {
		Actions actionmove = new Actions(driver);
		actionmove.moveToElement(elementval).click().build().perform();
	}

	public void exceldata() throws IOException {
		FileInputStream fis = new FileInputStream(".//testdata/datasheet.xlsx");
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		XSSFSheet worksheet = workbook.getSheet("Sheet1");
		int rowcount = worksheet.getLastRowNum() + 1;

		for (int i = 0; i <= rowcount - 1; i++) {
			String cellvalue = worksheet.getRow(i).getCell(0).getStringCellValue();
			 System.out.println(cellvalue);
			al.add(cellvalue);
		}

	}

	public String dataformat(String inputstring) {

		String[] a1 = inputstring.split(" ");
		formattedstring="";
		for (int i = 0; i < a1.length; i++) {
			formattedstring = formattedstring + a1[i];
		}
		System.out.println(formattedstring);
		return formattedstring;
	}

}
