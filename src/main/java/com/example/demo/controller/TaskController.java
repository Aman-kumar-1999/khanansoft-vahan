package com.example.demo.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.Task;
import com.example.demo.services.HtmlToJsonService;
import com.example.demo.services.TaskServices;

import io.github.bonigarcia.wdm.WebDriverManager;

@RestController
@RequestMapping("task")
public class TaskController {
	
	@Autowired
	private TaskServices taskServices;
	@Autowired
	private HtmlToJsonService service;
	
	
//	@GetMapping("/clickWebsite")
//    public String clickWebsite() {
//        try {
//            WebDriverManager.chromedriver().setup();
//            WebDriver driver = new ChromeDriver();
//
//            driver.manage().window().maximize();
//            driver.get("https://khanansoft.bihar.gov.in/portal/CitizenRpt/epassreportAllDist.aspx");
//
//            // Wait for the element to appear
//            Thread.sleep(2000);
//
//            // Perform click — e.g. click the first <a> tag
//            driver.findElement(By.tagName("a")).click();
//
//            Thread.sleep(2000); // wait to observe click
//            driver.quit();
//
//            return "Click performed successfully!";
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "Error: " + e.getMessage();
//        }
//    }
	
	@GetMapping("/")
	public ResponseEntity<?> getTask() {
		
		return ResponseEntity.ok(taskServices.getAllTask());
		
	}
	
	@PostMapping("/")
	public ResponseEntity<?> createTask(@RequestBody Task task){
		return ResponseEntity.ok(taskServices.createTask(task));
		
	}
	
	@RequestMapping(value = "/upload", method = RequestMethod.POST, consumes = {"multipart/form-data"})
    public ResponseEntity<?> createProductDataWithUserData(
            @RequestParam("file") MultipartFile file
//            @RequestPart("products") String product

    ){
		return ResponseEntity.ok("ok");
				
	}
	
	

    @GetMapping("/api/epass-data")
    public List<Map<String, String>> getEpassData() throws IOException {
        return service.fetchAndParseTable();
    }
    
    @GetMapping("/api/epass-data-with-html")
    public List<Map<String, String>> getEpassDataHtml() throws IOException {
    	
    	
    	try {
            // Target: DMO=PATNA (26), Source Type=Lessee (1), Date=01-Sep-2025
            Document updatedDocument = service.getReportData("26", "1", "01-Sep-2025"); 
            
            // Now you can parse the table from the updated document
            Element reportTable = updatedDocument.select("#ctl00_MainContent_grd").first();
            System.out.println("--- Updated Table Data ---");
            if (reportTable != null) {
                System.out.println(reportTable.outerHtml());
            } else {
                System.out.println("Table not found or an error occurred on the server.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    	
    	
    	
        return service.fetchAndParseTable();
    }
    
    
    

    @GetMapping("/fetch-report")
    public String getReport() {
        // Customizations for the call:
        String cookieSessionId = "w1ifcgpqjg5di420fxia32lf"; // Your specific cookie value
        String customUserAgent = "Mozilla/5.0 (iPhone; CPU iPhone OS 18_5 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/18.5 Mobile/15E148 Safari/604.1";

        try {
            // Call the service and get the HTML
            String htmlContent = service.fetchEpassReportHtml(cookieSessionId, customUserAgent);
            
            // In a real application, you would parse the HTML (e.g., using Jsoup) here.
            return htmlContent.substring(0, htmlContent.length());
        } catch (Exception e) {
            return "Error fetching data: " + e.getMessage();
        }
    }
    
    
    
    
    
    

}
