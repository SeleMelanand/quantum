package zgenericpackage;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class SearchAndDownload {

	@Test
	public void searchtext() throws InterruptedException {
		// Launching chrome browser
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
		RemoteWebDriver driver = new ChromeDriver();
		driver.get("https://www.google.com/?gws_rd=ssl");
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();

		WebElement alertnotification = driver.findElementByXPath("//a[text()='NO THANKS']");
		if (alertnotification.isDisplayed()) {

			alertnotification.click();

		} else {
			System.out.println("Alert notification doesnot exists");
		}

		String searchtext = "uncompressed development jquery 3.3.1 slim build";

		driver.findElementByXPath("//input[@title='Search']").sendKeys(searchtext, Keys.ENTER);

		List<WebElement> searchresult = driver.findElementsByXPath("//div[@id='ires']//h3/a");

		for (WebElement searchval : searchresult) {
			String textvalue = searchval.getText();
			System.out.println(textvalue);

			if (textvalue.equalsIgnoreCase("Download jQuery | jQuery")) {
				searchval.click();
				break;
			}

		}
		
		//Thread.sleep(5000);
		WebElement jquerypageelement = driver.findElementByXPath("//div[contains(@class,'twelve columns')]//h2[1]/a");
		WebDriverWait wait1 = new WebDriverWait(driver, 50);
		wait1.until(ExpectedConditions.attributeContains(jquerypageelement, "id", "downloading-jquery"));
		
		String pagetext = driver.findElementByXPath("//div[contains(@class,'twelve columns')]//h2[1]").getText();
		
		if (pagetext.equalsIgnoreCase("Downloading jQuery")) {
			System.out.println("Landed in correct page");
			System.out.println(pagetext);

		}
		else {
			System.out.println(pagetext);
			System.out.println("Landed in improper page");
		}
		
		
		List<WebElement> availablelinklist = driver.findElementsByXPath("//div[contains(@class,'twelve columns')]//a");
					
		for (WebElement val : availablelinklist) {
			
			System.out.println(val.getText());
			String eachlinkvalue = val.getText();
		
			if(eachlinkvalue.toLowerCase().contains("development JQUERY 3.3.1 slim build".toLowerCase())== true) {
			System.out.println("Inside if loop");
			val.click();
			break;
			}
		}
		
		
		
		
		

	}

}
