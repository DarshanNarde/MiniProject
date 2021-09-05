package pkg;

import java.io.IOException;

public class MiniProject  {
  
  
    
	public static void main(String[] args) throws IOException, InterruptedException {
		//set property for chrome browser 
		System.setProperty("webdriver.chrome.driver","D:\\selenium\\driver\\chromedriver.exe");
	    //calling methods from testCases class
		testCases.excelFile();
		testCases.gotoUrl();
		testCases.web();
	
		
		
		
	}

}
