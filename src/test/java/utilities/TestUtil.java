package utilities;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import base.BaseTest;

public class TestUtil extends BaseTest {
	
	public static String fileName;
	
	// Capturing  the screenshot
	public static void captureScreenshot() throws IOException {

		Date d = new Date();
		fileName = d.toString().replace(":", "_").replace(" ", "_")+".jpg";

		File scr = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(scr, new File(System.getProperty("user.dir") + "\\target\\surefire-reports\\html\\"+fileName));
		FileUtils.copyFile(scr, new File(System.getProperty("user.dir") + "\\target\\surefire-reports\\"+fileName));

	}
	
	// getting data from excel
	public static Object[][] getData(String sheetName) {
		
		
		
		int rowNum = excel.getRowCount(sheetName);
		int colNum = excel.getColumnCount(sheetName);
		
		
		Object[][] data = new Object[rowNum-1][colNum];
	
		
		
		for(int rows=2; rows<=rowNum; rows++) {
			
			for(int cols=0; cols<colNum; cols++) {
				
				//data[0][0] = "LoginTest",1,2
				data[rows-2][cols] =excel.getCellData(sheetName, cols, rows);
				
			}
			
			
		}
		
		
		return data;
		
	}

}
