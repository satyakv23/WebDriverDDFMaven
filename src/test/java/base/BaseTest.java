package base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import utilities.DbManager;
import utilities.ExcelReader;
import utilities.MonitoringMail;

public class BaseTest {

	/*
	 * WebDriver - done 
	 * Wait - Explicit - done and Implicit - done 
	 * Properties - OR and
	 * Config - done 
	 * Logs - done
	 * Database - done
	 * Excel - done 
	 * Mail - done 
	 * ReportNg - done
	 * Keywords
	 * Screenshots - timestamps
	 * 
	 */

	public static WebDriver driver;
	public static WebDriverWait wait;
	public static Properties OR = new Properties();
	public static Properties Config = new Properties();
	public static FileInputStream fis;
	public static Logger log = Logger.getLogger(BaseTest.class);
	public static MonitoringMail mail = new MonitoringMail();
	public static ExcelReader excel = new ExcelReader(
			System.getProperty("user.dir") + "\\src\\test\\resources\\excel\\testdata.xlsx");
	// CTRL + SHIFT + O
	public static WebElement dropdown;

	// Loading the log4j and properties files
	@BeforeSuite
	public void setUp() {

		if (driver == null) {

			PropertyConfigurator
					.configure(System.getProperty("user.dir") + "\\src\\test\\resources\\properties\\log4j.properties");
			try {
				fis = new FileInputStream(
						System.getProperty("user.dir") + "\\src\\test\\resources\\properties\\OR.properties");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				OR.load(fis);
				log.info("OR Properties Loaded !!!");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				fis = new FileInputStream(
						System.getProperty("user.dir") + "\\src\\test\\resources\\properties\\Config.properties");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				Config.load(fis);
				log.info("Config Properties Loaded !!!");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (Config.getProperty("browser").equals("firefox")) {

				System.setProperty("webdriver.gecko.driver",
						System.getProperty("user.dir") + "\\src\\test\\resources\\executables\\geckodriver.exe");
				driver = new FirefoxDriver();
				log.info("firefox launched");
			} else if (Config.getProperty("browser").equals("chrome")) {

				System.setProperty("webdriver.chrome.driver",
						System.getProperty("user.dir") + "\\src\\test\\resources\\executables\\chromedriver.exe");
				driver = new ChromeDriver();
				log.info("Chrome Launched");
			} else if (Config.getProperty("browser").equals("ie")) {

				System.setProperty("webdriver.ie.driver",
						System.getProperty("user.dir") + "\\src\\test\\resources\\executables\\IEDriverServer.exe");
				driver = new InternetExplorerDriver();
				log.info("IE Launched");
			}
			
			driver.get(Config.getProperty("testsiteurl"));
			log.info("Navigated to : "+Config.getProperty("testsiteurl"));
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(Integer.parseInt(Config.getProperty("implicit.wait")), TimeUnit.SECONDS);

			try {
				DbManager.setMysqlDbConnection();
				log.info("DB Connection created");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			wait = new WebDriverWait(driver,Integer.parseInt(Config.getProperty("explicit.wait")));
			
		}

	}

	@AfterSuite
	public void tearDown() {

		driver.quit();
		log.info("Test Execution Completed !!!");
	}
	
	
	
	public static void click(String key) {

		if (key.endsWith("_XPATH")) {

			driver.findElement(By.xpath(OR.getProperty(key))).click();

		}else if (key.endsWith("_CSS")) {

			driver.findElement(By.cssSelector(OR.getProperty(key))).click();

		}else if (key.endsWith("_ID")) {

			driver.findElement(By.id(OR.getProperty(key))).click();

		}
		
		log.info("User clicked on : "+key);
	}

	
	
	public static boolean isElementPresent(String key) {

		try {
		if (key.endsWith("_XPATH")) {

			driver.findElement(By.xpath(OR.getProperty(key)));
			

		}else if (key.endsWith("_CSS")) {

			driver.findElement(By.cssSelector(OR.getProperty(key)));
			

		}else if (key.endsWith("_ID")) {

			driver.findElement(By.id(OR.getProperty(key)));
			

		}else {
			
			log.info("Invalid key entered : "+key);
			Assert.fail("Invalid key entered");
		}
		log.info("Finding the Element : "+key);

		return true;
		}catch(Throwable t) {
			
			log.info("Error while Finding the Element : "+key+" error message : "+t.getMessage());
			return false;
		}
		
	
	}
	
	
	public static void type(String key, String value) {

		if (key.endsWith("_XPATH")) {

			driver.findElement(By.xpath(OR.getProperty(key))).sendKeys(value);

		}else if (key.endsWith("_CSS")) {

			driver.findElement(By.cssSelector(OR.getProperty(key))).sendKeys(value);

		}else if (key.endsWith("_ID")) {

			driver.findElement(By.id(OR.getProperty(key))).sendKeys(value);

		}
		
		log.info("User typed in : "+key+" and entered value as : "+value);

	}

	
	
	public static void select(String key, String value) {
		
		

		if (key.endsWith("_XPATH")) {

			dropdown = driver.findElement(By.xpath(OR.getProperty(key)));

		}else if (key.endsWith("_CSS")) {

			dropdown = driver.findElement(By.cssSelector(OR.getProperty(key)));

		}else if (key.endsWith("_ID")) {

			dropdown = driver.findElement(By.id(OR.getProperty(key)));

		}
		
	
		Select select = new Select(dropdown);
		select.selectByVisibleText(value);
		log.info("Selecting the value from  : "+key+" and entered the value as : "+value);
	}
	
	
}
