package pkg;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class testCases 

{
	 
	  //static variable to access in class
	  static String result=" ";
	  static String comment=" ";
	  static String moduleName="";
	  //created arrayList for storing excel file data 
	  static ArrayList<String> ar=new ArrayList<String>();
	  static WebDriver driver=new ChromeDriver();
	  
	
	  static void readExcelFile() 
		{
			
			try{
		    //creating file and get data from excel to array
	        File file = new File("D:\\selenium\\search_terms.xls");
	        FileInputStream inputStream = new FileInputStream(file);
	        HSSFWorkbook wb=new HSSFWorkbook(inputStream);
	        //object of excel sheet
	        HSSFSheet sheet=wb.getSheet("orders");
	        
	        //iterate through each rows one by one
	        Iterator<Row> itr = sheet.iterator();

	        //return true if the given list iterator contains more number
	        while (itr.hasNext()) {
	            Row row = itr.next();
	            
	            //reading rows from excel 
	            Iterator<Cell> cellIterator = row.cellIterator();
	            //Returns true if the iteration has more elements.
	            while (cellIterator.hasNext()) 
	            {
	            	//Returns the next element in the iteration.
	                Cell cell = cellIterator.next();
	                //Set the cells type (numeric, formula or string).If the cell currently contains 
	                switch (cell.getCellType())
	                {
	                case STRING:
	                    //getting values form excel and stored in variable 
	                    String get = cell.getStringCellValue();
	                    //add data into array
	                    ar.add(get);
	                    break;  
	                default:  
	                }
	            }
	            wb.close();      
	            }
	        }catch (Exception e) {
	            e.printStackTrace();
	        }
			
			System.out.println(ar);
			
			
		}
	  
	 //visiting URL
	 public static void gotoUrl()
	    {  
	    	driver.get("https://www.amazon.in/");
	    	driver.manage().window().maximize();
	    }
	    
	 //log-in in account of amazon.in
	 static void logIn() throws IOException, InterruptedException
	    {
	    	//selecting sing-in object form amazon.in 
			driver.findElement(By.xpath("//span[@id='nav-link-accountList-nav-line-1']")).click();
			
		    moduleName=driver.getTitle();
		    Thread.sleep(3000);
			//add user name or phone number for amazon login
		    driver.findElement(By.xpath("//input[@id='ap_email']")).sendKeys("9604170180");
			driver.findElement(By.xpath("//input[@id='continue']")).click();
			Thread.sleep(3000);
			
			//add password for amazon login with respect to user name or phone number
			driver.findElement(By.xpath("//input[@type='password']")).sendKeys("Darshan@123");
			String FirstTitle=driver.getCurrentUrl();
			
			//clicking on submit button
			driver.findElement(By.xpath("//input[@id='signInSubmit']")).click();
			Thread.sleep(3000);
			String SecondTitle=driver.getCurrentUrl();
			
			//writing test case for login pass or fail
			if (FirstTitle.equals(SecondTitle)) 
			{
				result = "Fail";
				comment = "SignIn failed";
				System.out.println("FAIL");
				
			}
			else 
			{
				result = "Pass";
				comment = "SignIn Successful";
				System.out.println("PASS");
				
			}
			
			//passing values to testCaseModule
			testCases.testCaseModule(moduleName, result, comment);
			//calling searchMoule
            testCases.searchModule();
	       
	  }	
	  
	    	
	    
	 public static boolean searchModule() throws InterruptedException, IOException 
	   {
		   
		   boolean insertCheck=false;
		   
		   //for loop to pass each element form array 
		   for(int i=0;i<ar.size();i++)
	       
		   { 
			 
			//adding each element in search box     
	        Thread.sleep(3000);
			driver.findElement(By.xpath("//input[@id='twotabsearchtextbox']")).clear();
			driver.findElement(By.xpath("//input[@id='twotabsearchtextbox']")).sendKeys(ar.get(i));
			driver.findElement(By.xpath("//input[@id='nav-search-submit-button']")).click();
			
			//selecting all the elements(search results) in current page
			List<WebElement> results=driver.findElements(By.xpath("//div[@class='s-expand-height s-include-content-margin s-latency-cf-section s-border-bottom']"));
			
			List<WebElement> links;
			
			System.out.println("\n"+ar.get(i)+"- \n");
			
			//we used counter to break loop after 5 search result of that element 			
			int counter=0;
			
			//file created to write that 5 result into text file 
			File file=new File("D:\\selenium\\xyz.txt");
			FileWriter f1=new FileWriter(file,true);
			BufferedWriter wr = new BufferedWriter(f1);
			wr.newLine();
			wr.append("\n "+ar.get(i)+"- \n");
			
			//for loop to find result 
			for(WebElement element:results)
			{
				//selecting elements with tag name "a"
				links=element.findElements(By.tagName("a"));
				 
				
				for(int j=0;j<links.size();j++)
				{
					//getting text from from searched results 
					if(links.get(j).getText().length()>20)
					{
						counter++;
					    //printing elements  
						System.out.println("result "+counter+"-"+links.get(j).getText());
						//appending elements to text file
						wr.append("result "+counter+"-"+links.get(j).getText()+"\n");	
						
						
					}
					//here we breaking that loop after first 5 results
					if(counter>=5)
					{
						insertCheck=true;
						break;
					}
					
					
				}
				
				
			}
			
			wr.close();
			
			 //checking whether the search module executed or not 
			 moduleName=ar.get(i);
			 if(insertCheck)
			 {
				 result="PASS";
				 comment="Serach module execute successfully";
			 }
			 else
			 {
				 result="FAIL";
				 comment="Serach module execute unsuccessfully";
				 
			 }
		      
			 //passing values to testCaseModule
			 testCases.testCaseModule(moduleName, result, comment);
		   
	   }
		   
		  
		   return  insertCheck; 
	       
	   }
	 
	 
	public static void testCaseModule(String moduleName,String result,String comment) throws IOException 
	   {
	    //Created file to add test cases 	  
	    File filePath=new File("D:\\selenium\\testCase.txt");
	    FileWriter FW = new FileWriter(filePath, true);
	    BufferedWriter BW = new BufferedWriter(FW);
	    
	        BW.newLine();
	    	BW.newLine();
	    	BW.write("--------------------------------------------------------------------");
	    	BW.write(moduleName+" Module");
	    	BW.newLine();
	    	BW.write("--------------------------------------------------------------------");
	    	
	    	BW.newLine();
	    	BW.newLine();
	    	BW.write("Module Name 		||"); // Writing In To File.
	    	BW.write("  Test Result     ||"); 
	    	BW.write("  Comments"); 

	    	// To write next string on new line.
	    	BW.newLine();
	    	BW.write("--------------------------------------------------------------------");
	    	BW.newLine();
	    	BW.append(  moduleName+"           ||   ");//appending data to file with respect ot object
	    	BW.append("    "+result+"          ||   ");
	    	BW.append("   "+comment+"    ");
	    	BW.close();
	   }
	    
	   

}
