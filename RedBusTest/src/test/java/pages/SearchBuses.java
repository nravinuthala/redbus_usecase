package pages;

import java.util.List;

import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;


public class SearchBuses {
	WebDriver driver;
	
	@FindBy(id="src")
	WebElement from_place;

	/*@FindBy(xpath="//*[@id=\"search\"]/div/div[1]/div/ul")
	WebElement from_ulst;*/
	
	@FindBy(id="dest")
	WebElement to_place;
	
	/*@FindBy(xpath="//*[@id=\"search\"]/div/div[2]/div/ul")
	WebElement to_ulst;*/
	
	@FindBy(id="onward_cal")
	WebElement onward_date;
	
	@FindBy(id="return_cal")
	WebElement return_date;
	
	@FindBy(id="search_btn")
	WebElement search_button;
	
	@FindBy(id="bt_AC")
	//@FindBy(xpath="/html/body/section/div[2]/div[1]/div/div[2]/div[1]/div/div[2]/ul[3]/li[3]/input")
	WebElement ac_box;
	
	@FindAll(@FindBy(how = How.CLASS_NAME, using = "cbox-label"))
	List<WebElement> cboxes;
	
	@FindBy(xpath="/html/body/section/div[2]/div[1]/div/div[2]/div[2]/div[2]/ul/div[2]/li/div/div[1]/div[1]/div[1]/div[1]")
	WebElement bus_name;
	
	@FindBy(xpath="/html/body/section/div[2]/div[1]/div/div[2]/div[2]/div[2]/ul/div[2]/li/div/div[1]/div[1]/div[6]/div/div[2]/span")
	WebElement ticket_price;
	
	@FindBy(xpath="/html/body/section/div[2]/div[1]/div/div[2]/div[2]/div[2]/ul/div[2]/li/div/div[1]/div[1]/div[6]/div[1]/div[3]/span")
	WebElement offer_ticket_price;
	
	public SearchBuses(WebDriver driver) {
		this.driver = driver;
		
		PageFactory.initElements(driver, this);
	}
	
	public void setFromPlace(String fromPlace) {
		from_place.sendKeys(fromPlace);
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		from_place.sendKeys(Keys.ENTER);
	}
		
	public void setToPlace(String toPlace) {
		to_place.sendKeys(toPlace);
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		to_place.sendKeys(Keys.ENTER);
	}
	
	public void setOnwardDate(String onwardDate) {
		onward_date.sendKeys(onwardDate);
	}
	
	public void setReturnDate(String returnDate) {
		return_date.sendKeys(returnDate);
	}
	
	public void clickSearchButton() {
		search_button.click();
	}
	
	public void clickACCheckBox() {
		/*WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.visibilityOf(ac_box));*/
		//ac_box.click();
		for(WebElement cbox : cboxes) {
			if(cbox.getAttribute("title") == "AC")
				cbox.click();
		}
	}
	
	public String getBusName() {
		String busName = "";
		busName = bus_name.getText(); 
		return busName;
	}
	
	public String getPrice() {
		try {
			
			String offer_price = "";
			offer_price = offer_ticket_price.getText();
			return offer_price;
		}catch(NoSuchElementException e) {
			String price="";
			price = ticket_price.getText();
			return price;
		}
	}
}
