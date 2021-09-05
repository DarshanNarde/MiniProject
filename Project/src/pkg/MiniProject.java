package pkg;

import java.io.IOException;

public class MiniProject  {
  
  
    
	public static void main(String[] args) throws IOException, InterruptedException {
		//Setting property of a system variable used by chrome driver 
		System.setProperty("webdriver.chrome.driver","D:\\selenium\\driver\\chromedriver.exe");
	    //calling methods from testCases class
		testCases.readExcelFile();
		testCases.gotoUrl();
		testCases.logIn();
	
		
		
		
	}

}
