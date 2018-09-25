package testcases;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import wrapperpackage.WrapperClass;

public class DocumentUpload extends WrapperClass {

	@Test
	public void documentcheck() throws InterruptedException, IOException {
		exceldata();
		int datacount = al.size();

		login();
		
		//Set<String> windowHandles = driver.getWindowHandles();

		clickelementbyid("main-search-box");
		typelementbyidandmouseclick("main-search-box", "2018073199");
		clickelementbyxpath("//span[text()='Documents']");
		String productname = null;

		for (int k = 0; k <= datacount - 1; k++) {

			System.out.println("New Iteration starts = k : " + k);
			WebDriverWait wait1 = new WebDriverWait(driver, 45);
			wait1.until(ExpectedConditions
					.elementToBeClickable(driver.findElementByXPath("//span[text()='Export Package']")));

			clickelementbyid("tabStandard");

			typelementbyxpathandmouseclick("//span[@data-field='StandardFormName']/span/span[1]/input",
					al.get(k).trim());
			Thread.sleep(5000);

			if (!driver.findElementByXPath("(//span[contains(@class,'k-pager-info')])[2]").getText().equalsIgnoreCase("No items to display")) {

				List<WebElement> formnameline = driver
						.findElementsByXPath("//table[@data-role='selectable']//tbody/tr");
				int formnamecount = formnameline.size();
				System.out.println("total form count : " + formnamecount);
				String updatedinputname = dataformat(al.get(k));
				int a = 1;
				for (int i = 1; i <= formnamecount; i++) {
					a = i + 1;
					System.out.println(a);
					String formname = driver
							.findElementByXPath("//div[@id='standardFormGrid']/div[3]/table/tbody/tr[" + a + "]/td[4]")
							.getText();
					String updatedformanme = dataformat(formname);
					System.out.println(formname);
					System.out.println(updatedinputname);
					if (updatedinputname.equalsIgnoreCase(updatedformanme)) {
						WebElement documentcheckbox = driver.findElementByXPath(
								"//div[@id='standardFormGrid']/div[3]/table/tbody/tr[" + a + "]//input");
						mouseoverclick(documentcheckbox);
						break;
					}

				}

				driver.findElementByXPath("//input[@value='Generate']").click();
				wait1.until(ExpectedConditions.textToBePresentInElement(
						driver.findElementByXPath("//div[@id='div-save-data-msg']//font"),
						"Order has been placed successfully to generate document!"));
				System.out.println("Document generated for  :" + al.get(k));

				WebElement utiliitieselement = driver.findElementByXPath("//a[@id='eSupplyChainManagement']/img");
				mouseover(utiliitieselement);

				WebElement psoelement = driver
						.findElementByXPath("//a[@id='eSupplyChainManagement_ProductOrderingNew']");
				mouseoverclick(psoelement);

				wait1.until(ExpectedConditions
						.elementToBeClickable(driver.findElementByXPath("//a[@id='tabfileSummary']")));

				Thread.sleep(10000);
				driver.findElementByXPath("//div[@id='globalContainer']//table//table//form/table//div/div[5]/div/div").click();
				Thread.sleep(5000);
				driver.findElementByXPath("//div[@id='globalContainer']//table//table//form/table//div/div[5]/div/div").click();
				
				String productname1 = driver.findElementByXPath("//div[@id='ordersListGrid']//td[3]").getText();	
				Thread.sleep(5000);
				
				if (productname1.contains("Document Package")||(productname1.contains(al.get(k)) )) {

					do {
						Thread.sleep(15000);
						driver.findElementByXPath(
								"//div[@id='globalContainer']/table//form/table/tbody/tr/td/div/div[5]//div/div")
								.click();
						productname = driver.findElementByXPath("//div[@id='ordersListGrid']//td[3]").getText();
						System.out.println(productname);
					} while (productname.contains("Document Package"));

					Thread.sleep(15000);
					driver.findElementByXPath(
							"//div[@id='globalContainer']/table//form/table/tbody/tr/td/div/div[5]//div/div").click();

					String orderstatus = driver.findElementByXPath("//div[@id='ordersListGrid']//tbody/tr[1]/td[4]")
							.getText();
					System.out.println(orderstatus);

					System.out.println(productname + ":" + orderstatus);

					FileInputStream fis1 = new FileInputStream(".//testdata/datasheet.xlsx");
					XSSFWorkbook workbook1 = new XSSFWorkbook(fis1);
					XSSFSheet worksheet1 = workbook1.getSheet("Sheet2");
					int rowcount = worksheet1.getLastRowNum() + 1;
					System.out.println("rowcount:" + rowcount);
					XSSFRow row = worksheet1.createRow(rowcount);
					row.createCell(0).setCellValue(productname);
					row.createCell(1).setCellValue(orderstatus);
					FileOutputStream fos = new FileOutputStream(".//testdata/datasheet.xlsx");
					workbook1.write(fos);
					workbook1.close();
				} else {

					System.out.println("Package order not placed successfully" + al.get(k));

					FileInputStream fis1 = new FileInputStream(".//testdata/datasheet.xlsx");
					XSSFWorkbook workbook1 = new XSSFWorkbook(fis1);
					XSSFSheet worksheet1 = workbook1.getSheet("Sheet2");
					int rowcount = worksheet1.getLastRowNum() + 1;
					System.out.println("rowcount:" + rowcount);
					XSSFRow row = worksheet1.createRow(rowcount);
					row.createCell(0).setCellValue(al.get(k));
					row.createCell(1).setCellValue("Package order not placed");
					FileOutputStream fos = new FileOutputStream(".//testdata/datasheet.xlsx");
					workbook1.write(fos);
					workbook1.close();
				}

				WebElement Qfilele = driver.findElementByXPath("//a[@id='eFile']");
				mouseover(Qfilele);
				WebElement docuelement = driver.findElementByXPath("//a[@id='eFile_DocumentManager']");
				mouseover(docuelement);

				WebElement documanagerelement = driver.findElementByXPath("//a[@id='DocumentManager_DocManagerPage']");
				mouseoverclick(documanagerelement);
			} else {

				System.out.println("Document package doesnot exists: " + al.get(k));
				FileInputStream fis1 = new FileInputStream(".//testdata/datasheet.xlsx");
				XSSFWorkbook workbook1 = new XSSFWorkbook(fis1);
				XSSFSheet worksheet1 = workbook1.getSheet("Sheet2");
				int rowcount = worksheet1.getLastRowNum() + 1;
				System.out.println("rowcount:" + rowcount);
				XSSFRow row = worksheet1.createRow(rowcount);
				row.createCell(0).setCellValue(al.get(k));
				row.createCell(1).setCellValue("Package doesnot exists");
				FileOutputStream fos = new FileOutputStream(".//testdata/datasheet.xlsx");
				workbook1.write(fos);
				workbook1.close();
				
				driver.findElementById("tabStandard").click();
				Thread.sleep(2000);

			}
		}

	}

}
