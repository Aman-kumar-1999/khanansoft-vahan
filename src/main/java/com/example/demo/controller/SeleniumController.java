package com.example.demo.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.KhananData;
import com.example.demo.services.SeleniumTestService;

@RestController
@RequestMapping("/api/selenium")
public class SeleniumController {

    @Autowired
    private SeleniumTestService seleniumTestService;

    // ==================== LOAD KHANANSOFT DATA BY FROM CURRENT DATE TO LAST DATE
    // WISE ENDPOINTS ====================

    @GetMapping("/by-date-range")
    public ResponseEntity<List<Map<String, String>>> getKhananDataByDateRange(
            @RequestParam(defaultValue = "https://khanansoft.bihar.gov.in/portal/CitizenRpt/epassreportAllDist.aspx") String url,
            @RequestParam(defaultValue = "%23ctl00_MainContent_btnshow") String selector,
            @RequestParam(defaultValue = "%23ctl00_MainContent_txtDate1") String inputCssSelector,
            // @RequestParam(defaultValue = "") String inputCssSelectorData,
            @RequestParam String fromDate,
            @RequestParam String toDate) {
        // List<KhananData> data = seleniumTestService.getKhananDataByDateRange(url,
        // selector, inputCssSelector, fromDate, toDate);
        try {
            List<Map<String, String>> data = seleniumTestService.openWebsiteAndClickWithAnchorLoopDateWaise(url,
                    selector, inputCssSelector, fromDate, toDate);

            return new ResponseEntity<>(data, HttpStatus.OK);
        } catch (InterruptedException e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
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

    @GetMapping("/click-test-with-anchor-tag")
    public List<Map<String, String>> clickTestWithAnchorTag(
            @RequestParam String url,
            @RequestParam String selector,
            @RequestParam String inputCssSelector,
            @RequestParam String inputCssSelectorData

    ) throws InterruptedException {
        return seleniumTestService.openWebsiteAndClickWithAnchor(url, selector, inputCssSelector, inputCssSelectorData);
    }

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
