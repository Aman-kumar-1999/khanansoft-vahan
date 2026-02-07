package com.example.demo.controller;




import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.services.SeleniumTestService;

@RestController
@RequestMapping("/api/selenium")
public class SeleniumController {

    @Autowired
    private SeleniumTestService seleniumTestService;

    // Simple click test
    // @GetMapping("/click")
    // public List<Map<String, String>> clickTest(
    //         @RequestParam String url,
    //         @RequestParam String selector,
    //         @RequestParam String inputCssSelector,
    //         @RequestParam String inputCssSelectorData
            
            
    // ) {
    //     return seleniumTestService.openWebsiteAndClick(url, selector,inputCssSelector, inputCssSelectorData);
    // }
    
    @GetMapping("/click-test-with-anchor-tag")
    public List<Map<String, String>> clickTestWithAnchorTag(
            @RequestParam String url,
            @RequestParam String selector,
            @RequestParam String inputCssSelector,
            @RequestParam String inputCssSelectorData
            
            
    ) throws InterruptedException {
        return seleniumTestService.openWebsiteAndClickWithAnchor(url, selector,inputCssSelector, inputCssSelectorData);
    }

    // @GetMapping("/second-layer-load-data")
    // public String secondLayerLoadData(
    //         // @RequestParam String url,
    //         // @RequestParam String selector,
    //         // @RequestParam String inputCssSelector,
    //         // @RequestParam String inputCssSelectorData
            
            
    // ) throws InterruptedException {
    //     return seleniumTestService.getSecondLayerData();
    // }

    // @GetMapping("/third-layer-load-data")
    // public String thirdLayerLoadData(
    //         // @RequestParam String url,
    //         // @RequestParam String selector,
    //         // @RequestParam String inputCssSelector,
    //         // @RequestParam String inputCssSelectorData
            
            
    // ) throws InterruptedException {
    //     return seleniumTestService.getThirdLayerData();
    // }
    
    // @GetMapping("/forth-layer-load-data")
    // public String forthLayerLoadData(
    //         @RequestParam String url
    //         // @RequestParam String selector,
    //         // @RequestParam String inputCssSelector,
    //         // @RequestParam String inputCssSelectorData
            
            
    // ) throws InterruptedException {
    //     return seleniumTestService.getForthLayerData(url);
    // }

    // Login flow test
    // @PostMapping("/login")
    // public String loginTest(
    //         @RequestParam String url,
    //         @RequestParam String username,
    //         @RequestParam String password
    // ) {
    //     return seleniumTestService.checkLoginFunction(url, username, password);
    // }
}
