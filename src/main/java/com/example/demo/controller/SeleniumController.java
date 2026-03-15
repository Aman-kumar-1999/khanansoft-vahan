package com.example.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.services.SeleniumTestService;

@RestController
@RequestMapping("/api/selenium")
public class SeleniumController {

    @Autowired
    private SeleniumTestService seleniumTestService;

    // ==================== LOAD KHANANSOFT DATA BY FROM Custom DATE TO LAST DATE
    // WISE ENDPOINTS ====================
// ----------------------------------------------------------------------------------------------------------------------------
  
    @GetMapping("/by-date-range")
    public ResponseEntity<List<Map<String, String>>> getKhananDataByDateRange(
            @RequestParam(defaultValue = "https://khanansoft.bihar.gov.in/portal/CitizenRpt/epassreportAllDist.aspx") String url,
            @RequestParam(defaultValue = "#ctl00_MainContent_btnshow") String selectorShowButton,
            @RequestParam(defaultValue = "#ctl00_MainContent_txtDate1") String inputCssSelectorDateInputField,
            // @RequestParam(defaultValue = "") String inputCssSelectorData,
            @RequestParam String fromDate,
            @RequestParam String toDate) {
        // List<KhananData> data = seleniumTestService.getKhananDataByDateRange(url,
        // selector, inputCssSelector, fromDate, toDate);
        try {
            // List<Map<String, String>> data = seleniumTestService.openWebsiteAndClickWithAnchorLoopDateWaise(url,
            //         selectorShowButton, inputCssSelectorDateInputField, fromDate, toDate);
            
            // List<Map<String, String>> data = seleniumTestService.openWebsiteAndClickWithAnchorLoopDateWaise(url,
            //         selectorShowButton, inputCssSelectorDateInputField, fromDate, toDate);
            
            // List<Map<String, String>> data = seleniumTestService.newOpenWebsiteAndClickWithAnchorLoopDateWaise(url,
            //         selectorShowButton, inputCssSelectorDateInputField, fromDate, toDate);
            seleniumTestService.scheduledScrapingTask(url, inputCssSelectorDateInputField, fromDate, toDate);

            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
// ----------------------------------------------------------------------------------------------------------------------------
  
    @GetMapping("/status")
    public ResponseEntity<Map<String, Object>> getScraperStatus() {
        Map<String, Object> status = new HashMap<>();
        status.put("running", seleniumTestService.isCurrentlyRunning());
        status.put("details", seleniumTestService.getStatusMessage());
        return ResponseEntity.ok(status);
    }

    @GetMapping("/dailyScraping")
    public ResponseEntity<String> triggerDailyScraping() {
        
        try {
            seleniumTestService.triggerDailyScraping();
            return ResponseEntity.ok("Daily scraping task triggered successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to trigger daily scraping task.");
        }
    }

    // Simple click test
    // @GetMapping("/click")
    // public List<Map<String, String>> clickTest(
    // @RequestParam String url,
    // @RequestParam String selector,
    // @RequestParam String inputCssSelector,
    // @RequestParam String inputCssSelectorData

    // ) {
    // return seleniumTestService.openWebsiteAndClick(url,
    // selector,inputCssSelector, inputCssSelectorData);
    // }
// ----------------------------------------------------------------------------------------------------------------------------
    // @GetMapping("/click-test-with-anchor-tag")
    // public List<Map<String, String>> clickTestWithAnchorTag(
    //         @RequestParam String url,
    //         @RequestParam String selector,
    //         @RequestParam String inputCssSelector,
    //         @RequestParam String inputCssSelectorData

    // ) throws InterruptedException {
    //     return seleniumTestService.openWebsiteAndClickWithAnchor(url, selector, inputCssSelector, inputCssSelectorData);
    // }
// ----------------------------------------------------------------------------------------------------------------------------
  
    // @GetMapping("/second-layer-load-data")
    // public String secondLayerLoadData(
    // // @RequestParam String url,
    // // @RequestParam String selector,
    // // @RequestParam String inputCssSelector,
    // // @RequestParam String inputCssSelectorData

    // ) throws InterruptedException {
    // return seleniumTestService.getSecondLayerData();
    // }

    // @GetMapping("/third-layer-load-data")
    // public String thirdLayerLoadData(
    // // @RequestParam String url,
    // // @RequestParam String selector,
    // // @RequestParam String inputCssSelector,
    // // @RequestParam String inputCssSelectorData

    // ) throws InterruptedException {
    // return seleniumTestService.getThirdLayerData();
    // }

    // @GetMapping("/forth-layer-load-data")
    // public String forthLayerLoadData(
    // @RequestParam String url
    // // @RequestParam String selector,
    // // @RequestParam String inputCssSelector,
    // // @RequestParam String inputCssSelectorData

    // ) throws InterruptedException {
    // return seleniumTestService.getForthLayerData(url);
    // }

    // Login flow test
    // @PostMapping("/login")
    // public String loginTest(
    // @RequestParam String url,
    // @RequestParam String username,
    // @RequestParam String password
    // ) {
    // return seleniumTestService.checkLoginFunction(url, username, password);
    // }
}
