package zgenericpackage;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.Test;

@Test
public class BrokenLink {
	public void brklink() throws InterruptedException {
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
		RemoteWebDriver driver = new ChromeDriver();
		driver.get("https://www.google.co.in/");
		driver.manage().window().maximize();	
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);				
		Thread.sleep(5000);
		List<WebElement> links = driver.findElements(By.tagName("a"));		
		System.out.println("Total links are "+links.size());
		for(int i=0; i<links.size(); i++) {
			WebElement element = links.get(i);			
			String url=element.getAttribute("href");
			verifyLink(url);            
		}   
		
		driver.findElementById("").sendKeys("");
		
		//driver.findElement(By.id("")).sendKeys("");
		
		
	}
	
	
	public  void verifyLink(String urlLink) {
		try {			
			URL link = new URL(urlLink);
			HttpURLConnection httpConn =(HttpURLConnection)link.openConnection();		
			httpConn.setConnectTimeout(2000);	
			httpConn.connect();			
			if(httpConn.getResponseCode()== 200) {  
				System.out.println(urlLink+" - "+httpConn.getResponseMessage());
			}
			if(httpConn.getResponseCode()== 404) {
				System.out.println(urlLink+" - "+httpConn.getResponseMessage());
			}
		} 
		catch (Exception e) {
		}
	} 

}
