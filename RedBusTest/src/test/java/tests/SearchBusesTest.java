package tests;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import org.apache.commons.io.FileUtils;

import pages.SearchBuses;
import utils.ReadData;

public class SearchBusesTest {
	WebDriver driver;
	SearchBuses searchBusObj;
	String filePath, sheetName;
	
	@Parameters ({"browser"})
	@BeforeClass
	public void launchBrowser(String browser) {
		filePath = "C:\\temp\\redbus_test_data.xls";
		sheetName = "Sheet1";
		
		try {
			if (browser.equalsIgnoreCase("Firefox")) {
				System.setProperty("webdriver.gecko.driver", "C:\\temp\\geckodriver-v0.26.0-win64\\geckodriver.exe");
				driver = new FirefoxDriver();
			} else if (browser.equalsIgnoreCase("chrome")) {
				System.setProperty("webdriver.chrome.driver", "C:\\temp\\chromedriver_win32\\chromedriver.exe");
				driver = new ChromeDriver();
			}
			
			driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
			driver.get("https://www.redbus.in/");
			driver.manage().window().maximize();
		}
		catch(WebDriverException e) {
			System.out.println(e.getMessage());
		}
	}
	 
	@DataProvider(name = "excelData")
	public Object[][] readExcel() throws IOException{
		System.out.println("File path is " + filePath);
		return ReadData.readExcel(filePath, sheetName);
	}
	
	public String nextDate(String curDate){
        String nextDate = "";
        try{
            Calendar today = Calendar.getInstance();
            DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
            Date dt = df.parse(curDate);
            today.setTime(dt);
            today.add(Calendar.DAY_OF_YEAR, 1);
            nextDate = df.format(today.getTime());
        }catch (Exception e){
            return nextDate;
        }
        return nextDate;
    }
	
	public void takeSnapShot(WebDriver driver, String filePath) {
		TakesScreenshot scrShot = ((TakesScreenshot)driver);
		File srcFile = scrShot.getScreenshotAs(OutputType.FILE);
		File dstFile = new File(filePath);
		try {
			FileUtils.copyFile(srcFile, dstFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test(dataProvider = "excelData")
	public void testSearchPage(String fromCity, String toCity) {
		
		//Take current date and increment it by one, so that whenever code is run, it runs for a future date
		DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
		Date dt = new Date();
		String today = df.format(dt);
		String tomorrow = nextDate(today);
		
		searchBusObj = new SearchBuses(driver);
		
		String fromPlace = fromCity;
		String toPlace = toCity;
		
		searchBusObj.setFromPlace(fromPlace);
					
		searchBusObj.setToPlace(toPlace);
		
		((JavascriptExecutor)driver).executeScript("document.getElementById('onward_cal').removeAttribute('readonly',0);");
		((JavascriptExecutor)driver).executeScript("document.getElementById('return_cal').removeAttribute('readonly',0);");
		searchBusObj.setOnwardDate(tomorrow);
		
		searchBusObj.clickSearchButton();
		
		searchBusObj.clickACCheckBox();
		takeSnapShot(driver, "C:\\temp\\test.png");
		
		System.out.println("Second bus name is " + searchBusObj.getBusName());
		System.out.println("Second bus price is " + searchBusObj.getPrice());
	}
	
	@AfterTest
	public void teardown() {
		driver.quit();
	}
}
