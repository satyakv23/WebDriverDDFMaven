package rough;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class TestBold {

	public static void main(String[] args) throws InterruptedException {


		System.setProperty("webdriver.chrome.driver",
				System.getProperty("user.dir") + "\\src\\test\\resources\\executables\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.get("https://www.linkedin.com/");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//*[@id='login-email']")).sendKeys("seleniumcoaching@gmail.com");
		driver.findElement(By.xpath("//*[@id='login-password']")).sendKeys("rahita1413");
		driver.findElement(By.xpath("//*[@id='login-submit']")).click();
		driver.findElement(By.xpath("//*[@id='notifications-tab-icon']")).click();
		
		Thread.sleep(5000);
		List<WebElement> buttons = driver.findElements(By.tagName("button"));
		
		for(WebElement button: buttons) {
			
			System.out.println(button.getText());
			if(button.getText().equals("View jobs")) {
				button.click();
				break;
			}
		}
	}

}
