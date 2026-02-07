package com.example.demo.services;

import io.github.bonigarcia.wdm.WebDriverManager;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.KhananData;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.lang.model.util.Elements;

@Service
public class SeleniumTestService {

    @Autowired
    public KhananDataService khananDataService;

    public List<Map<String, String>> openWebsiteAndClick(String url, String cssSelector, String inputCssSelector,
            String inputCssSelectorData) {
        System.out.println(" Selenium Test Service Called.... ");
        System.out.println(" URL : " + url);
        System.out.println(" CSS Selector : " + cssSelector);
        System.out.println(" Input CSS Selector : " + inputCssSelector);
        System.out.println(" Input CSS Selector Data : " + inputCssSelectorData);

        System.out.println(" Chrome Driver Setup Completed.... ");
        WebDriverManager.chromedriver().setup();

        // options.addArguments("--headless=new"); // runs in background
        // options.addArguments("--no-sandbox");
        // options.addArguments("--disable-gpu");
        // options.addArguments("--disable-dev-shm-usage");
        // WebDriver driver = new ChromeDriver(options);

        // Use headless mode (important for EC2)
        // To Run the below code in Headless mode on Linux Server (like AWS EC2), you
        // need to set following options
        // install the Google Chrome browser on your server
        // command : sudo apt update
        // command : sudo apt install -y wget unzip
        // command : wget
        // https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb
        // command : sudo apt install -y ./google-chrome-stable_current_amd64.deb
        // or
        // Verify installation by running:
        // command : sudo apt-get install -y google-chrome-stable
        // install ChromeDriver compatible with your Chrome browser version on your
        // server
        // command : CHROME_VERSION=$(google-chrome --version | grep -oP
        // '\d+\.\d+\.\d+')
        // command : wget
        // https://chromedriver.storage.googleapis.com/$CHROME_VERSION/chromedriver_linux64.zip
        // command : unzip chromedriver_linux64.zip
        // command : sudo mv chromedriver /usr/bin/chromedriver
        // command : sudo chown root:root /usr/bin/chromedriver
        // command : sudo chmod +x /usr/bin/chromedriver
        // Now, you can run Selenium with Chrome in headless mode on your Linux server.

        // Check if Google Chrome is installed
        // command : google-chrome --version
        // Check if ChromeDriver is installed
        // command : chromedriver --version

        System.out.println(" Setting Chrome Options.... ");
        ChromeOptions options = new ChromeOptions();
        System.out.println(" Setting Chrome Options - Headless.... ");
        options.addArguments("--headless=new");
        System.out.println(" Setting Chrome Options - No Sandbox.... ");
        options.addArguments("--no-sandbox");
        System.out.println(" Setting Chrome Options - Disable Dev SHM Usage.... ");
        options.addArguments("--disable-dev-shm-usage");
        System.out.println(" Setting Chrome Options - Disable GPU.... ");
        options.addArguments("--disable-gpu");

        // If using Google Chrome in your ubuntu server use this:
        // options.setBinary("/usr/bin/google-chrome");

        // System.out.println(" Setting Chrome Options - Window Size.... ");
        // options.addArguments("window-size=1920,1080");
        // System.out.println(" Setting Chrome Options - Disable Extensions.... ");
        // options.addArguments("--disable-extensions");
        // System.out.println(" Setting Chrome Options - Disable Images.... ");
        // options.addArguments("blink-settings=imagesEnabled=false");

        System.out.println(" Setting Chrome Options Completed.... ");

        System.out.println(" Initializing Chrome Driver.... ");

        // This for headless mode (runs in background)
        WebDriver driver = new ChromeDriver(options);

        // this for non-headless mode (runs with browser UI)
        // WebDriver driver = new ChromeDriver();

        System.out.println(" Chrome Driver Initialized.... ");
        try {
            driver.manage().window().maximize();
            System.out.println(" Before Navigating to URL.... ");
            driver.get(url);
            System.out.println(" After Navigated to URL.... ");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            // wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(inputCssSelector)));
            // wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(inputCssSelector)));
            // #ctl00_MainContent_txtDate1
            System.out.println(" Locating Date Input Element.... ");
            WebElement dateinput =
                    // wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(inputCssSelector)));
                    driver.findElement(By.cssSelector(inputCssSelector));
            // Define the formatter matching your input
            // DateTimeFormatter inputFormatter =
            // DateTimeFormatter.ofPattern("dd-MMM-yyyy");

            // Parse the string to a LocalDate
            // LocalDate date = LocalDate.parse(inputCssSelectorData, inputFormatter);

            System.out.println(" Clearing Date Input Element.... ");
            dateinput.clear();
            System.out.println(" Sending Data to Date Input Element.... ");
            dateinput.sendKeys(inputCssSelectorData);
            dateinput.sendKeys(Keys.RETURN);
            // WebElement clickOnShowButton = wait
            // .until(ExpectedConditions.elementToBeClickable(By.cssSelector(cssSelector)));

            // clickOnShowButton.click();

            // Document doc =
            //
            // Element table = doc.select("table").get(1);

            WebElement changedDate =
                    // wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(inputCssSelector)));
                    driver.findElement(By.cssSelector(inputCssSelector));
            System.out.println(" Changed Data : " + changedDate.getText());

            try {
                // Open the target website
                // driver.get("https://example.com");

                // ✅ Locate all tables on the page
                System.out.println("Extracting data from the second table on the page...");
                List<WebElement> allTables = driver.findElements(By.tagName("table"));

                List<Map<String, String>> jsonList = new ArrayList<>();
                // List<TableRowData> result = new ArrayList<>();
                Map<String, String> listOfAnchor = new HashMap<>();
                // Ensure there are at least 2 tables
                System.out.println("Total Tables Found: " + allTables.size());
                if (allTables.size() >= 2) {
                    WebElement secondTable = allTables.get(1); // index starts from 0

                    // Find all rows in the second table

                    List<WebElement> rows = secondTable.findElements(By.tagName("tr"));
                    List<String> headers = new ArrayList<>();
                    System.out.println("Total Rows in Second Table: " + rows.size());

                    // ✅ Loop through rows and extract each cell
                    for (int i = 0; i < rows.size(); i++) {
                        WebElement row = rows.get(i);
                        List<WebElement> cols = row.findElements(By.tagName("td"));
                        if (i == 0) {
                            for (WebElement col : cols) {
                                headers.add(col.getText().trim());
                            }
                        } else {
                            // WebElement anchors = cols.get(4);
                            // List<WebElement> anchorElements = anchors.findElements(By.tagName("a"));

                            // cols.select("a[href]");

                            // System.out.println("Anchors Size ====== "+anchors.size());
                            List<String> hrefs = new ArrayList<>();
                            Map<String, String> rowData = new LinkedHashMap<>();

                            for (int j = 0; j < cols.size(); j++) {
                                WebElement col = cols.get(j);
                                System.out.print(col.getText() + "\t");
                                // if (anchors.size() == 2) {
                                // String district = "";
                                // if (j == 4) {
                                // String key1 = j < headers.size() ? headers.get(j) : "i : " + i + " Column" +
                                // j;
                                // listOfAnchor.put(key1, anchors.get(0).absUrl("href"));
                                // district = cols.get(0).text();
                                // hrefs.add(anchors.get(0).absUrl("href"));
                                // }
                                // if (j == 8) {
                                // String key1 = j < headers.size() ? headers.get(j) : "i : " + i + " Column" +
                                // j;
                                // listOfAnchor.put(key1, anchors.get(1).absUrl("href"));
                                // district = cols.get(1).text();
                                // hrefs.add(anchors.get(1).absUrl("href"));
                                // }
                                //
                                // }
                                String key = j < headers.size() ? headers.get(j) : "Column" + j;
                                rowData.put(key, cols.get(j).getText().trim());

                            }
                            jsonList.add(rowData);
                        }
                        System.out.println();
                    }

                } else {
                    System.out.println("Less than two tables found on the page.");
                }
                listOfAnchor.entrySet().stream()
                        .sorted(Map.Entry.comparingByKey()) // sort by key
                        .forEach(
                                entry -> System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue()));
                return jsonList;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            } finally {
                driver.quit();
            }

            // String pageTitle = driver.getTitle();
            // // Thread.sleep(90000);

            // return "Clicked successfully! Page title: " + pageTitle;
        } catch (Exception e) {
            e.printStackTrace();
            // return "Error: " + e.getMessage();
            return null;
        } finally {
            driver.quit();

        }
    }

    public String checkLoginFunction(String url, String username, String password) {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();

        try {
            driver.get(url);
            driver.manage().window().maximize();

            driver.findElement(By.id("username")).sendKeys(username);
            driver.findElement(By.id("password")).sendKeys(password);
            driver.findElement(By.id("loginButton")).click();

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.urlContains("dashboard"));

            return "✅ Login successful, redirected to: " + driver.getCurrentUrl();
        } catch (Exception e) {
            e.printStackTrace();
            return "❌ Login failed: " + e.getMessage();
        } finally {
            driver.quit();
        }
    }

    public List<Map<String, String>> openWebsiteAndClickWithAnchor(String url, String cssSelector,
            String inputCssSelector,
            String inputCssSelectorData) {
        System.out.println(" Selenium Test Service Called.... ");
        System.out.println(" URL : " + url);
        System.out.println(" CSS Selector : " + cssSelector);
        System.out.println(" Input CSS Selector : " + inputCssSelector);
        System.out.println(" Input CSS Selector Data : " + inputCssSelectorData);

        System.out.println(" Chrome Driver Setup Completed.... ");
        WebDriverManager.chromedriver().setup();

        // options.addArguments("--headless=new"); // runs in background
        // options.addArguments("--no-sandbox");
        // options.addArguments("--disable-gpu");
        // options.addArguments("--disable-dev-shm-usage");
        // WebDriver driver = new ChromeDriver(options);

        // Use headless mode (important for EC2)
        // To Run the below code in Headless mode on Linux Server (like AWS EC2), you
        // need to set following options
        // install the Google Chrome browser on your server
        // command : sudo apt update
        // command : sudo apt install -y wget unzip
        // command : wget
        // https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb
        // command : sudo apt install -y ./google-chrome-stable_current_amd64.deb
        // or
        // Verify installation by running:
        // command : sudo apt-get install -y google-chrome-stable
        // install ChromeDriver compatible with your Chrome browser version on your
        // server
        // command : CHROME_VERSION=$(google-chrome --version | grep -oP
        // '\d+\.\d+\.\d+')
        // command : wget
        // https://chromedriver.storage.googleapis.com/$CHROME_VERSION/chromedriver_linux64.zip
        // command : unzip chromedriver_linux64.zip
        // command : sudo mv chromedriver /usr/bin/chromedriver
        // command : sudo chown root:root /usr/bin/chromedriver
        // command : sudo chmod +x /usr/bin/chromedriver
        // Now, you can run Selenium with Chrome in headless mode on your Linux server.

        // Check if Google Chrome is installed
        // command : google-chrome --version
        // Check if ChromeDriver is installed
        // command : chromedriver --version

        System.out.println(" Setting Chrome Options.... ");
        ChromeOptions options = new ChromeOptions();
        System.out.println(" Setting Chrome Options - Headless.... ");
        options.addArguments("--headless=new");
        System.out.println(" Setting Chrome Options - No Sandbox.... ");
        options.addArguments("--no-sandbox");
        System.out.println(" Setting Chrome Options - Disable Dev SHM Usage.... ");
        options.addArguments("--disable-dev-shm-usage");
        System.out.println(" Setting Chrome Options - Disable GPU.... ");
        options.addArguments("--disable-gpu");

        // If using Google Chrome in your ubuntu server use this:
        // options.setBinary("/usr/bin/google-chrome");

        // System.out.println(" Setting Chrome Options - Window Size.... ");
        // options.addArguments("window-size=1920,1080");
        // System.out.println(" Setting Chrome Options - Disable Extensions.... ");
        // options.addArguments("--disable-extensions");
        // System.out.println(" Setting Chrome Options - Disable Images.... ");
        // options.addArguments("blink-settings=imagesEnabled=false");

        System.out.println(" Setting Chrome Options Completed.... ");

        System.out.println(" Initializing Chrome Driver.... ");

        // This for headless mode (runs in background)
        // WebDriver driver = new ChromeDriver(options);

        // this for non-headless mode (runs with browser UI)
        WebDriver driver = new ChromeDriver();

        System.out.println(" Chrome Driver Initialized.... ");
        try {
            driver.manage().window().maximize();
            System.out.println(" Before Navigating to URL.... ");
            driver.get(url);
            System.out.println(" After Navigated to URL.... ");
            // WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            // wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(inputCssSelector)));
            // wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(inputCssSelector)));
            // #ctl00_MainContent_txtDate1
            System.out.println(" Locating Date Input Element.... ");
            WebElement dateinput =
                    // wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(inputCssSelector)));
                    driver.findElement(By.cssSelector(inputCssSelector));
            // Define the formatter matching your input
            // DateTimeFormatter inputFormatter =
            // DateTimeFormatter.ofPattern("dd-MMM-yyyy");

            // Parse the string to a LocalDate
            // LocalDate date = LocalDate.parse(inputCssSelectorData, inputFormatter);

            System.out.println(" Clearing Date Input Element.... ");
            dateinput.clear();
            System.out.println(" Sending Data to Date Input Element.... ");
            dateinput.sendKeys(inputCssSelectorData);
            dateinput.sendKeys(Keys.RETURN);
            // WebElement clickOnShowButton = wait
            // .until(ExpectedConditions.elementToBeClickable(By.cssSelector(cssSelector)));

            // clickOnShowButton.click();

            // Document doc =
            //
            // Element table = doc.select("table").get(1);

            // WebElement changedDate =
            // //
            // wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(inputCssSelector)));
            // driver.findElement(By.cssSelector(inputCssSelector));
            // System.out.println(" Changed Data : " + changedDate.getText());

            Thread.sleep(2000);

            try {
                // Open the target website
                // driver.get("https://example.com");

                // ✅ Locate all tables on the page
                System.out.println("Extracting data from the second table on the page...");
                List<WebElement> allTables = driver.findElements(By.tagName("table"));

                List<Map<String, String>> jsonList = new ArrayList<>();
                // List<TableRowData> result = new ArrayList<>();
                Map<String, String> listOfAnchor = new HashMap<>();
                // Ensure there are at least 2 tables
                System.out.println("Total Tables Found: " + allTables.size());
                if (allTables.size() >= 2) {
                    WebElement secondTable = allTables.get(1); // index starts from 0

                    // Find all rows in the second table

                    List<WebElement> rows = secondTable.findElements(By.tagName("tr"));
                    List<String> headers = new ArrayList<>();
                    System.out.println("Total Rows in Second Table: " + rows.size());

                    // ✅ Loop through rows and extract each cell
                    for (int i = 1; i < rows.size(); i++) {
                        WebElement row = rows.get(i);
                        List<WebElement> cols = row.findElements(By.tagName("td"));
                        if (i == 1) {
                            for (WebElement col : cols) {
                                headers.add(col.getText().trim());
                            }
                        } else {
                            // WebElement anchors = cols.get(4);
                            // List<WebElement> anchorElements = anchors.findElements(By.tagName("a"));

                            // cols.select("a[href]");

                            // System.out.println("Anchors Size ====== "+anchors.size());
                            Map<String, String> rowData = new LinkedHashMap<>();
                            // Find all anchor tags within the current row
                            List<WebElement> anchors = cols.stream()
                                    .flatMap(c -> c.findElements(By.cssSelector("a[href]")).stream()) // correct CSS
                                                                                                      // selector
                                    .toList();
                            // List<WebElement> anchors = driver.findElements(By.tagName("a"));
                            List<String> validHrefs = anchors.stream()
                                    .map(a -> a.getAttribute("href"))
                                    .filter(href -> href != null && !href.trim().isEmpty())
                                    .filter(href -> !href.equalsIgnoreCase(
                                            "https://khanansoft.bihar.gov.in/portal/CitizenRpt/epassreportAllDist.aspx#"))
                                    .collect(Collectors.toList());
                            // for (int k = 0; k < validHrefs.size(); k++) {
                            // System.out.println("Index: " + k + " → Valid Href: " + validHrefs.get(k));
                            // }

                            for (int j = 0; j < cols.size(); j++) {
                                WebElement col = cols.get(j);
                                System.out.print(col.getText() + "\t");

                                if (anchors.size() == 2 || anchors.size() == 3) {
                                    if (j == 4) {
                                        // System.out.println("Anchor " + i);
                                        String key1 = " row = " + (i - 1) + " col = " + j;
                                        // for(int k = 0; k < 1;k++) {
                                        // //rows.get(i).findElements(By.xpath("./td"));
                                        // key1 = cols.get(j).getText().trim()+ i + " " + j;
                                        // }
                                        // WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(220));
                                        // WebElement thirdAnchor = anchors.get(0);
                                        //
                                        // wait.until(ExpectedConditions.elementToBeClickable(thirdAnchor)).click();
                                        //
                                        // WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofMinutes(5));
                                        // Thread.sleep(300000);
                                        if (!anchors.get(0).getAttribute("href").equalsIgnoreCase(
                                                "https://khanansoft.bihar.gov.in/portal/CitizenRpt/epassreportAllDist.aspx#")) {
                                            listOfAnchor.put(key1, anchors.get(0).getAttribute("href"));
                                        }
                                        // listOfAnchor.put(key1, anchors.get(0).getAttribute("href"));
                                    }
                                    if (j == 8) {
                                        // System.out.println("Anchor " + i);
                                        String key1 = " row = " + (i - 1) + " col = " + j;
                                        // anchors.get(1).click();
                                        if (!anchors.get(1).getAttribute("href").equalsIgnoreCase(
                                                "https://khanansoft.bihar.gov.in/portal/CitizenRpt/epassreportAllDist.aspx#")) {
                                            listOfAnchor.put(key1, anchors.get(1).getAttribute("href"));
                                        }
                                        // listOfAnchor.put(key1, anchors.get(1).getAttribute("href"));
                                    }
                                }
                                String key = j < headers.size() ? headers.get(j) : "Column" + j;
                                rowData.put(key, cols.get(j).getText().trim());

                            }
                            jsonList.add(rowData);
                        }
                        System.out.println();
                    }

                } else {
                    System.out.println("Less than two tables found on the page.");
                }
                // listOfAnchor.entrySet().stream()
                // .sorted(Map.Entry.comparingByKey()) // sort by key
                // .forEach(
                // entry -> System.out.println("Key: " + entry.getKey() + ", Value: " +
                // entry.getValue()));

                // listOfAnchor.entrySet()
                // .stream()
                // .sorted(Map.Entry.comparingByKey()) // sort by key
                // .forEach(entry -> {
                // System.out.println("Key: " + entry.getKey() + ", Value: " +
                // entry.getValue());
                // // driver.switchTo().newWindow(WindowType.TAB);
                // driver.get(entry.getValue());
                // }
                // );

                // Second layer Work
                listOfAnchor.entrySet()
                        .stream()
                        .sorted(Map.Entry.comparingByKey())
                        // .findFirst()
                        // .ifPresent
                        .forEach(entry -> {

                            System.out.println("Second Layer Key: " + entry.getKey() + ", Value: " + entry.getValue());
                            try {
                                driver.manage().window().maximize();
                                driver.get(entry.getValue());
                                // driver.get(
                                // "https://khanansoft.bihar.gov.in/portal/CitizenRpt/ePassReportAllConsigner.aspx?ASHxNlmoFyKWVTw3JRIqNxcbqoTn8ifRT+5+Fljj+RIW2b6TUpdEC3Ri/5qzPs8e8hYZcCCpckeZiacSTWEkBUwOIYewYlXpnpULwNe+ZheGUBijC+XPRUr6yH2w2+1ZkrCabjgDzhg=");
                                WebElement viewAllElement = driver.findElement(By.id("ctl00_MainContent_lbtnAll"));
                                viewAllElement.click();
                                Thread.sleep(2000);
                                WebElement sourceTypeElement = driver.findElement(By
                                        .id("ctl00_MainContent_ddlPassType"));

                                Select sourceTypeSelect = new Select(sourceTypeElement);
                                String sourceTypeValue = sourceTypeSelect.getFirstSelectedOption().getText();

                                List<WebElement> secondLayerallTables = driver.findElements(By.tagName("table"));

                                // List<Map<String, String>> secondLayerJsonList = new ArrayList<>();
                                // List<TableRowData> result = new ArrayList<>();
                                Map<String, String> secondLayerListOfAnchor = new HashMap<>();

                                // Ensure there are at least 2 tables
                                System.out.println("Total Tables Found: " + secondLayerallTables.size());
                                if (secondLayerallTables.size() >= 2) {
                                    WebElement secondLayersecondTable = secondLayerallTables.get(1); // index starts
                                                                                                     // from 0

                                    // Find all rows in the second table

                                    List<WebElement> rows = secondLayersecondTable.findElements(By.tagName("tr"));
                                    List<String> headers = new ArrayList<>();
                                    System.out.println("Total Rows in Second Table: " + rows.size());

                                    // ✅ Loop through rows and extract each cell
                                    for (int i = 1; i < rows.size(); i++) {
                                        WebElement row = rows.get(i);
                                        List<WebElement> cols = row.findElements(By.tagName("td"));
                                        // if (i == 1) {
                                        // for (WebElement col : cols) {
                                        // headers.add(col.getText().trim());
                                        // }
                                        // } else {
                                        // Map<String, String> rowData = new LinkedHashMap<>();
                                        // List<WebElement> anchors = cols.stream()
                                        // .flatMap(c -> c.findElements(By.cssSelector("a[href]")).stream()) // correct
                                        // CSS
                                        // // selector
                                        // .toList();
                                        for (int j = 0; j < cols.size(); j++) {
                                            WebElement col = cols.get(j);
                                            System.out.print(col.getText() + "\t");
                                            // if (j == 4) {
                                            // String key1 = " row = " + (i ) + " col = " + j;
                                            // if (!anchors.get(0).getAttribute("href").equalsIgnoreCase(
                                            // "https://khanansoft.bihar.gov.in/portal/CitizenRpt/epassreportAllDist.aspx#"))
                                            // {
                                            // secondLayerListOfAnchor.put(key1, anchors.get(0).getAttribute("href"));
                                            // }
                                            // // String link =
                                            // cols.get(j).findElement(By.cssSelector("a[href]")).getAttribute("href");
                                            // // System.out.println(" Link : " + link);
                                            // }

                                            if (j == 4) {
                                                List<WebElement> anchorElements = col
                                                        .findElements(By.cssSelector("a[href]"));

                                                if (!anchorElements.isEmpty()) {
                                                    String href = anchorElements.get(0).getAttribute("href");

                                                    if (!href.equalsIgnoreCase(
                                                            "https://khanansoft.bihar.gov.in/portal/CitizenRpt/epassreportAllDist.aspx#")) {

                                                        String key1 = "row=" + i + " col=" + j;
                                                        secondLayerListOfAnchor.put(key1, href);
                                                    }
                                                }
                                            }

                                            // String key = j < headers.size() ? headers.get(j) : "Column" + j;
                                            // rowData.put(key, cols.get(j).getText().trim());

                                        }
                                        // secondLayerJsonList.add(rowData);
                                        // }
                                        System.out.println();
                                    }
                                    // Third Layer Work
                                    secondLayerListOfAnchor.entrySet().stream()
                                            .sorted(Map.Entry.comparingByKey()) // sort by key
                                            // .findFirst()
                                            // .ifPresent
                                            .forEach(e -> {
                                                System.out.println(
                                                        "Third Layer Key: " + e.getKey() + ", Value: " + e.getValue());
                                                try {
                                                    driver.manage().window().maximize();
                                                    driver.get(e.getValue());
                                                    // driver.get(
                                                    // "https://khanansoft.bihar.gov.in/portal/CitizenRpt/ePassRptByConsigner.aspx?ASHxNlmoFyKWVTw3JRIqN0OYDRqYeiSRVE5mv0Ad+jmKwd7ghMUE8Q6Lpj70hq/xUORwZiTS3RyBA8iuNxpnC6oIVEGL/6q//fhbKMCV9a6urMznWnMFF8ObySfdL2phbW/NH+VBuR0=");
                                                    WebElement viewAllElementOfThirdLayer = driver
                                                            .findElement(By.id("ctl00_MainContent_lbtnAll"));
                                                    viewAllElementOfThirdLayer.click();
                                                    Thread.sleep(2000);
                                                    List<WebElement> thiredLayerallTables = driver
                                                            .findElements(By.tagName("table"));

                                                    // List<Map<String, String>> secondLayerJsonList = new
                                                    // ArrayList<>();
                                                    // List<TableRowData> result = new ArrayList<>();
                                                    Map<String, String> thirdLayerListOfAnchor = new HashMap<>();

                                                    // Ensure there are at least 2 tables
                                                    System.out.println(
                                                            "Total Tables Found: " + thiredLayerallTables.size());
                                                    if (thiredLayerallTables.size() >= 2) {
                                                        WebElement thirdLayerTable = thiredLayerallTables.get(1); // index
                                                                                                                  // starts
                                                                                                                  // from
                                                                                                                  // 0

                                                        // Find all rows in the second table

                                                        List<WebElement> rowsthird = thirdLayerTable
                                                                .findElements(By.tagName("tr"));
                                                        List<String> headersThird = new ArrayList<>();
                                                        System.out
                                                                .println("Total Rows in Third Layer Table: "
                                                                        + rowsthird.size());
                                                        // ✅ Loop through rows and extract each cell
                                                        for (int i = 1; i < rowsthird.size(); i++) {
                                                            WebElement row = rowsthird.get(i);
                                                            List<WebElement> cols = row.findElements(By.tagName("td"));
                                                            // if (i == 1) {
                                                            // for (WebElement col : cols) {
                                                            // headers.add(col.getText().trim());
                                                            // }
                                                            // } else {
                                                            // Map<String, String> rowData = new LinkedHashMap<>();
                                                            // List<WebElement> anchors = cols.stream()
                                                            // .flatMap(c ->
                                                            // c.findElements(By.cssSelector("a[href]")).stream()) //
                                                            // correct
                                                            // CSS
                                                            // // selector
                                                            // .toList();
                                                            for (int j = 0; j < cols.size(); j++) {
                                                                WebElement col = cols.get(j);
                                                                System.out.print(col.getText() + "\t");
                                                                // if (j == 4) {
                                                                // String key1 = " row = " + (i ) + " col = " + j;
                                                                // if
                                                                // (!anchors.get(0).getAttribute("href").equalsIgnoreCase(
                                                                // "https://khanansoft.bihar.gov.in/portal/CitizenRpt/epassreportAllDist.aspx#"))
                                                                // {
                                                                // secondLayerListOfAnchor.put(key1,
                                                                // anchors.get(0).getAttribute("href"));
                                                                // }
                                                                // // String link =
                                                                // cols.get(j).findElement(By.cssSelector("a[href]")).getAttribute("href");
                                                                // // System.out.println(" Link : " + link);
                                                                // }

                                                                if (j == 4) {
                                                                    List<WebElement> anchorElements = col
                                                                            .findElements(By.cssSelector("a[href]"));

                                                                    if (!anchorElements.isEmpty()) {
                                                                        String href = anchorElements.get(0)
                                                                                .getAttribute("href");

                                                                        if (!href.equalsIgnoreCase(
                                                                                "https://khanansoft.bihar.gov.in/portal/CitizenRpt/epassreportAllDist.aspx#")) {

                                                                            String key1 = "row=" + i + " col=" + j;
                                                                            thirdLayerListOfAnchor.put(key1, href);
                                                                        }
                                                                    }
                                                                }

                                                                // String key = j < headers.size() ? headers.get(j) :
                                                                // "Column" + j;
                                                                // rowData.put(key, cols.get(j).getText().trim());

                                                            }
                                                            // secondLayerJsonList.add(rowData);
                                                            // }
                                                            System.out.println();
                                                        }
                                                        // Forth Layer Work
                                                        thirdLayerListOfAnchor.entrySet().stream()
                                                                .sorted(Map.Entry.comparingByKey()) // sort by key
                                                                // .findFirst()
                                                                // .ifPresent
                                                                .forEach(ent -> {
                                                                    System.out
                                                                            .println("------------------------------");
                                                                    System.out
                                                                            .println("Forth Layer Key: " + ent.getKey()
                                                                                    + ", Value: " + ent.getValue());
                                                                    System.out
                                                                            .println("------------------------------");
                                                                    try {
                                                                        // driver.manage().window().maximize();
                                                                        driver.get(ent.getValue());

                                                                        // driver.get(
                                                                        // "https://khanansoft.bihar.gov.in/portal/CitizenRpt/ePassRptByConsigner.aspx?ASHxNlmoFyKWVTw3JRIqN0OYDRqYeiSRVE5mv0Ad+jmKwd7ghMUE8Q6Lpj70hq/xUORwZiTS3RyBA8iuNxpnC6oIVEGL/6q//fhbKMCV9a6urMznWnMFF8ObySfdL2phbW/NH+VBuR0=");
                                                                        WebElement viewAllElementForth = driver
                                                                                .findElement(By.id(
                                                                                        "ctl00_MainContent_lbtnAll"));

                                                                        viewAllElementForth.click();
                                                                        Thread.sleep(2000);

                                                                        WebElement district = driver.findElement(By
                                                                                .id(
                                                                                        "ctl00_MainContent_ddlDMO"));
                                                                        WebElement consigner = driver.findElement(By
                                                                                .id(
                                                                                        "ctl00_MainContent_ddlConsigner"));
                                                                        WebElement date = driver.findElement(By
                                                                                .id(
                                                                                        "ctl00_MainContent_txtDate"));

                                                                        Select districtSelect = new Select(district);
                                                                        String districtValue = districtSelect
                                                                                .getFirstSelectedOption().getText();

                                                                        Select consignerSelect = new Select(consigner);
                                                                        String consignerValue = consignerSelect
                                                                                .getFirstSelectedOption().getText();

                                                                        String dateValue = date.getAttribute("value");
                                                                        String sourceType = sourceTypeValue;

                                                                        System.out.println(districtValue);
                                                                        System.out.println(consignerValue);
                                                                        System.out.println(dateValue);
                                                                        System.out.println(sourceType);

                                                                        List<WebElement> forthLayerallTables = driver
                                                                                .findElements(By.tagName("table"));

                                                                        // List<Map<String, String>> secondLayerJsonList
                                                                        // = new ArrayList<>();
                                                                        // List<TableRowData> result = new
                                                                        // ArrayList<>();
                                                                        Map<String, String> forthLayerListOfAnchor = new HashMap<>();

                                                                        // Ensure there are at least 2 tables
                                                                        System.out.println("Total Tables Found: "
                                                                                + forthLayerallTables.size());
                                                                        List<KhananData> khananDataList = new ArrayList<>();
                                                                        if (forthLayerallTables.size() >= 2) {
                                                                            WebElement forthLayersecondTable = forthLayerallTables
                                                                                    .get(1); // index starts from 0

                                                                            // Find all rows in the second table

                                                                            List<WebElement> rowsForth = forthLayersecondTable
                                                                                    .findElements(By.tagName("tr"));
                                                                            List<String> headersForth = new ArrayList<>();
                                                                            System.out.println(
                                                                                    "Total Rows in Second Table: "
                                                                                            + rowsForth.size());

                                                                            // ✅ Loop through rows and extract each cell
                                                                            for (int i = 1; i < rowsForth.size()
                                                                                    - 1; i++) {
                                                                                WebElement row = rowsForth.get(i);
                                                                                List<WebElement> cols = row
                                                                                        .findElements(By.tagName("td"));
                                                                                KhananData khananData = new KhananData(
                                                                                        districtValue,
                                                                                        consignerValue,
                                                                                        dateValue,
                                                                                        sourceType,
                                                                                        cols.get(1).getText().trim(),
                                                                                        cols.get(2).getText().trim(),
                                                                                        cols.get(3).getText().trim(),
                                                                                        cols.get(4).getText().trim(),
                                                                                        cols.get(5).getText().trim(),
                                                                                        cols.get(6).getText().trim(),
                                                                                        cols.get(7).getText().trim(),
                                                                                        cols.get(8).getText().trim(),
                                                                                        cols.get(9).getText().trim(),
                                                                                        cols.get(10).getText().trim());
                                                                                khananDataList.add(khananData);
                                                                                for (int j = 0; j < cols.size(); j++) {
                                                                                    WebElement col = cols.get(j);
                                                                                    System.out.print(
                                                                                            col.getText() + "\t");

                                                                                }

                                                                                System.out.println();
                                                                            }
                                                                            khananDataService
                                                                                    .saveAllKhananData(khananDataList);
                                                                            System.out.println(
                                                                                    "Data Saved Successfully.");
                                                                            // ✅ End of Loop
                                                                            // secondLayerListOfAnchor.entrySet().stream()
                                                                            // .sorted(Map.Entry.comparingByKey()) //
                                                                            // sort by key
                                                                            // .forEach(entry -> System.out
                                                                            // .println("Key: " + entry.getKey() + ",
                                                                            // Value: " + entry.getValue()));
                                                                        } else {
                                                                            System.out.println(
                                                                                    "Less than two tables found on the page.");
                                                                        }

                                                                        // });
                                                                    } catch (Exception exc) {
                                                                        exc.printStackTrace();
                                                                    }

                                                                });
                                                    } else {
                                                        System.out.println("Less than two tables found on the page.");
                                                    }

                                                    // });
                                                } catch (Exception ex) {
                                                    ex.printStackTrace();
                                                }
                                            });
                                } else {
                                    System.out.println("Less than two tables found on the page.");
                                }

                                // });
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        });

                return jsonList;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            } finally {
                // driver.quit();
            }

            // String pageTitle = driver.getTitle();
            // // Thread.sleep(90000);

            // return "Clicked successfully! Page title: " + pageTitle;
        } catch (Exception e) {
            e.printStackTrace();
            // return "Error: " + e.getMessage();
            return null;
        } finally {
            // driver.quit();

        }
    }

    public String getSecondLayerData() {
        WebDriverManager.chromedriver().setup();

        System.out.println(" Setting Chrome Options.... ");
        ChromeOptions options = new ChromeOptions();
        System.out.println(" Setting Chrome Options - Headless.... ");
        options.addArguments("--headless=new");
        System.out.println(" Setting Chrome Options - No Sandbox.... ");
        options.addArguments("--no-sandbox");
        System.out.println(" Setting Chrome Options - Disable Dev SHM Usage.... ");
        options.addArguments("--disable-dev-shm-usage");
        System.out.println(" Setting Chrome Options - Disable GPU.... ");
        options.addArguments("--disable-gpu");

        // This for headless mode (runs in background)
        // WebDriver driver = new ChromeDriver(options);

        // this for non-headless mode (runs with browser UI)
        WebDriver driver = new ChromeDriver();
        // listOfAnchor.entrySet()
        // .stream()
        // .sorted(Map.Entry.comparingByKey())
        // .findFirst()
        // .ifPresent(entry -> {

        // System.out.println(entry.getKey() + " : " + entry.getValue());
        try {
            driver.manage().window().maximize();
            driver.get(
                    "https://khanansoft.bihar.gov.in/portal/CitizenRpt/ePassReportAllConsigner.aspx?ASHxNlmoFyKWVTw3JRIqNxcbqoTn8ifRT+5+Fljj+RIW2b6TUpdEC3Ri/5qzPs8e8hYZcCCpckeZiacSTWEkBUwOIYewYlXpnpULwNe+ZheGUBijC+XPRUr6yH2w2+1ZkrCabjgDzhg=");
            WebElement viewAllElement = driver.findElement(By.id("ctl00_MainContent_lbtnAll"));
            viewAllElement.click();
            Thread.sleep(4000);
            List<WebElement> secondLayerallTables = driver.findElements(By.tagName("table"));

            // List<Map<String, String>> secondLayerJsonList = new ArrayList<>();
            // List<TableRowData> result = new ArrayList<>();
            Map<String, String> secondLayerListOfAnchor = new HashMap<>();

            // Ensure there are at least 2 tables
            System.out.println("Total Tables Found: " + secondLayerallTables.size());
            if (secondLayerallTables.size() >= 2) {
                WebElement secondLayersecondTable = secondLayerallTables.get(1); // index starts from 0

                // Find all rows in the second table

                List<WebElement> rows = secondLayersecondTable.findElements(By.tagName("tr"));
                List<String> headers = new ArrayList<>();
                System.out.println("Total Rows in Second Table: " + rows.size());

                // ✅ Loop through rows and extract each cell
                for (int i = 1; i < rows.size(); i++) {
                    WebElement row = rows.get(i);
                    List<WebElement> cols = row.findElements(By.tagName("td"));
                    // if (i == 1) {
                    // for (WebElement col : cols) {
                    // headers.add(col.getText().trim());
                    // }
                    // } else {
                    // Map<String, String> rowData = new LinkedHashMap<>();
                    // List<WebElement> anchors = cols.stream()
                    // .flatMap(c -> c.findElements(By.cssSelector("a[href]")).stream()) // correct
                    // CSS
                    // // selector
                    // .toList();
                    for (int j = 0; j < cols.size(); j++) {
                        WebElement col = cols.get(j);
                        System.out.print(col.getText() + "\t");
                        // if (j == 4) {
                        // String key1 = " row = " + (i ) + " col = " + j;
                        // if (!anchors.get(0).getAttribute("href").equalsIgnoreCase(
                        // "https://khanansoft.bihar.gov.in/portal/CitizenRpt/epassreportAllDist.aspx#"))
                        // {
                        // secondLayerListOfAnchor.put(key1, anchors.get(0).getAttribute("href"));
                        // }
                        // // String link =
                        // cols.get(j).findElement(By.cssSelector("a[href]")).getAttribute("href");
                        // // System.out.println(" Link : " + link);
                        // }

                        if (j == 4) {
                            List<WebElement> anchorElements = col.findElements(By.cssSelector("a[href]"));

                            if (!anchorElements.isEmpty()) {
                                String href = anchorElements.get(0).getAttribute("href");

                                if (!href.equalsIgnoreCase(
                                        "https://khanansoft.bihar.gov.in/portal/CitizenRpt/epassreportAllDist.aspx#")) {

                                    String key1 = "row=" + i + " col=" + j;
                                    secondLayerListOfAnchor.put(key1, href);
                                }
                            }
                        }

                        // String key = j < headers.size() ? headers.get(j) : "Column" + j;
                        // rowData.put(key, cols.get(j).getText().trim());

                    }
                    // secondLayerJsonList.add(rowData);
                    // }
                    System.out.println();
                }
                secondLayerListOfAnchor.entrySet().stream()
                        .sorted(Map.Entry.comparingByKey()) // sort by key
                        .forEach(entry -> System.out
                                .println("Key: " + entry.getKey() + ", Value: " + entry.getValue()));
            } else {
                System.out.println("Less than two tables found on the page.");
            }

            // });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Hello from second layer data!";
    }

    public String getThirdLayerData() {
        WebDriverManager.chromedriver().setup();

        System.out.println(" Setting Chrome Options.... ");
        ChromeOptions options = new ChromeOptions();
        System.out.println(" Setting Chrome Options - Headless.... ");
        options.addArguments("--headless=new");
        System.out.println(" Setting Chrome Options - No Sandbox.... ");
        options.addArguments("--no-sandbox");
        System.out.println(" Setting Chrome Options - Disable Dev SHM Usage.... ");
        options.addArguments("--disable-dev-shm-usage");
        System.out.println(" Setting Chrome Options - Disable GPU.... ");
        options.addArguments("--disable-gpu");

        // This for headless mode (runs in background)
        // WebDriver driver = new ChromeDriver(options);

        // this for non-headless mode (runs with browser UI)
        WebDriver driver = new ChromeDriver();
        // listOfAnchor.entrySet()
        // .stream()
        // .sorted(Map.Entry.comparingByKey())
        // .findFirst()
        // .ifPresent(entry -> {

        // System.out.println(entry.getKey() + " : " + entry.getValue());
        try {
            driver.manage().window().maximize();
            driver.get(
                    "https://khanansoft.bihar.gov.in/portal/CitizenRpt/ePassRptByConsigner.aspx?ASHxNlmoFyKWVTw3JRIqN0OYDRqYeiSRVE5mv0Ad+jmKwd7ghMUE8Q6Lpj70hq/xUORwZiTS3RyBA8iuNxpnC6oIVEGL/6q//fhbKMCV9a6urMznWnMFF8ObySfdL2phbW/NH+VBuR0=");
            WebElement viewAllElement = driver.findElement(By.id("ctl00_MainContent_lbtnAll"));
            viewAllElement.click();
            Thread.sleep(4000);
            List<WebElement> secondLayerallTables = driver.findElements(By.tagName("table"));

            // List<Map<String, String>> secondLayerJsonList = new ArrayList<>();
            // List<TableRowData> result = new ArrayList<>();
            Map<String, String> secondLayerListOfAnchor = new HashMap<>();

            // Ensure there are at least 2 tables
            System.out.println("Total Tables Found: " + secondLayerallTables.size());
            if (secondLayerallTables.size() >= 2) {
                WebElement secondLayersecondTable = secondLayerallTables.get(1); // index starts from 0

                // Find all rows in the second table

                List<WebElement> rows = secondLayersecondTable.findElements(By.tagName("tr"));
                List<String> headers = new ArrayList<>();
                System.out.println("Total Rows in Second Table: " + rows.size());

                // ✅ Loop through rows and extract each cell
                for (int i = 1; i < rows.size(); i++) {
                    WebElement row = rows.get(i);
                    List<WebElement> cols = row.findElements(By.tagName("td"));
                    // if (i == 1) {
                    // for (WebElement col : cols) {
                    // headers.add(col.getText().trim());
                    // }
                    // } else {
                    // Map<String, String> rowData = new LinkedHashMap<>();
                    // List<WebElement> anchors = cols.stream()
                    // .flatMap(c -> c.findElements(By.cssSelector("a[href]")).stream()) // correct
                    // CSS
                    // // selector
                    // .toList();
                    for (int j = 0; j < cols.size(); j++) {
                        WebElement col = cols.get(j);
                        System.out.print(col.getText() + "\t");
                        // if (j == 4) {
                        // String key1 = " row = " + (i ) + " col = " + j;
                        // if (!anchors.get(0).getAttribute("href").equalsIgnoreCase(
                        // "https://khanansoft.bihar.gov.in/portal/CitizenRpt/epassreportAllDist.aspx#"))
                        // {
                        // secondLayerListOfAnchor.put(key1, anchors.get(0).getAttribute("href"));
                        // }
                        // // String link =
                        // cols.get(j).findElement(By.cssSelector("a[href]")).getAttribute("href");
                        // // System.out.println(" Link : " + link);
                        // }

                        if (j == 4) {
                            List<WebElement> anchorElements = col.findElements(By.cssSelector("a[href]"));

                            if (!anchorElements.isEmpty()) {
                                String href = anchorElements.get(0).getAttribute("href");

                                if (!href.equalsIgnoreCase(
                                        "https://khanansoft.bihar.gov.in/portal/CitizenRpt/epassreportAllDist.aspx#")) {

                                    String key1 = "row=" + i + " col=" + j;
                                    secondLayerListOfAnchor.put(key1, href);
                                }
                            }
                        }

                        // String key = j < headers.size() ? headers.get(j) : "Column" + j;
                        // rowData.put(key, cols.get(j).getText().trim());

                    }
                    // secondLayerJsonList.add(rowData);
                    // }
                    System.out.println();
                }
                secondLayerListOfAnchor.entrySet().stream()
                        .sorted(Map.Entry.comparingByKey()) // sort by key
                        .forEach(entry -> System.out
                                .println("Key: " + entry.getKey() + ", Value: " + entry.getValue()));
            } else {
                System.out.println("Less than two tables found on the page.");
            }

            // });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Hello from second layer data!";
    }

    public String getForthLayerData(String url) {
        WebDriverManager.chromedriver().setup();

        System.out.println(" Setting Chrome Options.... ");
        ChromeOptions options = new ChromeOptions();
        System.out.println(" Setting Chrome Options - Headless.... ");
        options.addArguments("--headless=new");
        System.out.println(" Setting Chrome Options - No Sandbox.... ");
        options.addArguments("--no-sandbox");
        System.out.println(" Setting Chrome Options - Disable Dev SHM Usage.... ");
        options.addArguments("--disable-dev-shm-usage");
        System.out.println(" Setting Chrome Options - Disable GPU.... ");
        options.addArguments("--disable-gpu");

        // This for headless mode (runs in background)
        // WebDriver driver = new ChromeDriver(options);

        // this for non-headless mode (runs with browser UI)
        WebDriver driver = new ChromeDriver();
        // listOfAnchor.entrySet()
        // .stream()
        // .sorted(Map.Entry.comparingByKey())
        // .findFirst()
        // .ifPresent(entry -> {

        // System.out.println(entry.getKey() + " : " + entry.getValue());
        try {
            driver.manage().window().maximize();
            driver.get(url);
            // driver.get(
            // "https://khanansoft.bihar.gov.in/portal/CitizenRpt/ePassRptByConsigner.aspx?ASHxNlmoFyKWVTw3JRIqN0OYDRqYeiSRVE5mv0Ad+jmKwd7ghMUE8Q6Lpj70hq/xUORwZiTS3RyBA8iuNxpnC6oIVEGL/6q//fhbKMCV9a6urMznWnMFF8ObySfdL2phbW/NH+VBuR0=");
            WebElement viewAllElement = driver.findElement(By.id("ctl00_MainContent_lbtnAll"));
            viewAllElement.click();
            Thread.sleep(4000);
            List<WebElement> secondLayerallTables = driver.findElements(By.tagName("table"));

            // List<Map<String, String>> secondLayerJsonList = new ArrayList<>();
            // List<TableRowData> result = new ArrayList<>();
            Map<String, String> secondLayerListOfAnchor = new HashMap<>();

            // Ensure there are at least 2 tables
            System.out.println("Total Tables Found: " + secondLayerallTables.size());
            if (secondLayerallTables.size() >= 2) {
                WebElement secondLayersecondTable = secondLayerallTables.get(1); // index starts from 0

                // Find all rows in the second table

                List<WebElement> rows = secondLayersecondTable.findElements(By.tagName("tr"));
                List<String> headers = new ArrayList<>();
                System.out.println("Total Rows in Second Table: " + rows.size());

                // ✅ Loop through rows and extract each cell
                for (int i = 1; i < rows.size(); i++) {
                    WebElement row = rows.get(i);
                    List<WebElement> cols = row.findElements(By.tagName("td"));
                    // if (i == 1) {
                    // for (WebElement col : cols) {
                    // headers.add(col.getText().trim());
                    // }
                    // } else {
                    // Map<String, String> rowData = new LinkedHashMap<>();
                    // List<WebElement> anchors = cols.stream()
                    // .flatMap(c -> c.findElements(By.cssSelector("a[href]")).stream()) // correct
                    // CSS
                    // // selector
                    // .toList();
                    for (int j = 0; j < cols.size(); j++) {
                        WebElement col = cols.get(j);
                        System.out.print(col.getText() + "\t");
                        // if (j == 4) {
                        // String key1 = " row = " + (i ) + " col = " + j;
                        // if (!anchors.get(0).getAttribute("href").equalsIgnoreCase(
                        // "https://khanansoft.bihar.gov.in/portal/CitizenRpt/epassreportAllDist.aspx#"))
                        // {
                        // secondLayerListOfAnchor.put(key1, anchors.get(0).getAttribute("href"));
                        // }
                        // // String link =
                        // cols.get(j).findElement(By.cssSelector("a[href]")).getAttribute("href");
                        // // System.out.println(" Link : " + link);
                        // }

                        // if (j == 4) {
                        // List<WebElement> anchorElements =
                        // col.findElements(By.cssSelector("a[href]"));

                        // if (!anchorElements.isEmpty()) {
                        // String href = anchorElements.get(0).getAttribute("href");

                        // if (!href.equalsIgnoreCase(
                        // "https://khanansoft.bihar.gov.in/portal/CitizenRpt/epassreportAllDist.aspx#"))
                        // {

                        // String key1 = "row=" + i + " col=" + j;
                        // secondLayerListOfAnchor.put(key1, href);
                        // }
                        // }
                        // }

                        // String key = j < headers.size() ? headers.get(j) : "Column" + j;
                        // rowData.put(key, cols.get(j).getText().trim());

                    }
                    // secondLayerJsonList.add(rowData);
                    // }
                    System.out.println();
                }
                // secondLayerListOfAnchor.entrySet().stream()
                // .sorted(Map.Entry.comparingByKey()) // sort by key
                // .forEach(entry -> System.out
                // .println("Key: " + entry.getKey() + ", Value: " + entry.getValue()));
            } else {
                System.out.println("Less than two tables found on the page.");
            }

            // });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Hello from second layer data!";
    }

    public List<Map<String, String>> fetchAndParseTableWithSelenium(String url, String cssSelector,
            String inputCssSelector, String inputCssSelectorData) throws InterruptedException {
        // Setup Chrome in headless mode (works on server too)
        // ChromeOptions options = new ChromeOptions();
        // options.addArguments("--headless");
        // options.addArguments("--no-sandbox");
        // options.addArguments("--disable-dev-shm-usage");
        // options.addArguments("--disable-gpu");

        // WebDriver driver = new ChromeDriver(options);

        // String url =
        // "https://khanansoft.bihar.gov.in/portal/CitizenRpt/epassreportAllDist.aspx";
        // driver.get(url);
        // driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        // // Wait briefly to ensure tables load
        // Thread.sleep(3000);

        System.out.println(" Selenium Test Service Called.... ");
        System.out.println(" URL : " + url);
        System.out.println(" CSS Selector : " + cssSelector);
        System.out.println(" Input CSS Selector : " + inputCssSelector);
        System.out.println(" Input CSS Selector Data : " + inputCssSelectorData);

        System.out.println(" Chrome Driver Setup Completed.... ");
        try {
            System.out.println("🧹 Cleaning up old Chrome/Driver sessions...");

            // Kill existing Chrome/ChromeDriver processes
            Runtime.getRuntime().exec("pkill -f chromedriver");
            Runtime.getRuntime().exec("pkill -f chrome");

            // Wait for cleanup to complete (1 sec)
            Thread.sleep(1000);
        } catch (Exception e) {
            System.out.println("Cleanup failed: " + e.getMessage());
        }
        WebDriverManager.chromedriver().setup();

        // options.addArguments("--headless=new"); // runs in background
        // options.addArguments("--no-sandbox");
        // options.addArguments("--disable-gpu");
        // options.addArguments("--disable-dev-shm-usage");
        // WebDriver driver = new ChromeDriver(options);

        // Use headless mode (important for EC2)
        // To Run the below code in Headless mode on Linux Server (like AWS EC2), you
        // need to set following options
        // install the Google Chrome browser on your server
        // command : sudo apt update
        // command : sudo apt install -y wget unzip
        // command : wget
        // https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb
        // command : sudo apt install -y ./google-chrome-stable_current_amd64.deb
        // or
        // Verify installation by running:
        // command : sudo apt-get install -y google-chrome-stable
        // install ChromeDriver compatible with your Chrome browser version on your
        // server
        // command : CHROME_VERSION=$(google-chrome --version | grep -oP
        // '\d+\.\d+\.\d+')
        // command : wget
        // https://chromedriver.storage.googleapis.com/$CHROME_VERSION/chromedriver_linux64.zip
        // command : unzip chromedriver_linux64.zip
        // command : sudo mv chromedriver /usr/bin/chromedriver
        // command : sudo chown root:root /usr/bin/chromedriver
        // command : sudo chmod +x /usr/bin/chromedriver
        // Now, you can run Selenium with Chrome in headless mode on your Linux server.

        // Check if Google Chrome is installed
        // command : google-chrome --version
        // Check if ChromeDriver is installed
        // command : chromedriver --version

        // System.out.println(" Setting Chrome Options.... ");
        // ChromeOptions options = new ChromeOptions();
        // System.out.println(" Setting Chrome Options - Headless.... ");
        // options.addArguments("--headless=new");
        // System.out.println(" Setting Chrome Options - No Sandbox.... ");
        // options.addArguments("--no-sandbox");
        // System.out.println(" Setting Chrome Options - Disable Dev SHM Usage.... ");
        // options.addArguments("--disable-dev-shm-usage");
        // System.out.println(" Setting Chrome Options - Disable GPU.... ");
        // options.addArguments("--disable-gpu");
        //
        // // If using Google Chrome in your ubuntu server use this:
        // options.setBinary("/usr/bin/google-chrome");
        //
        // // System.out.println(" Setting Chrome Options - Window Size.... ");
        // // options.addArguments("window-size=1920,1080");
        // System.out.println(" Setting Chrome Options - Disable Extensions.... ");
        // options.addArguments("--disable-extensions");
        // System.out.println(" Setting Chrome Options - Disable Images.... ");
        // options.addArguments("blink-settings=imagesEnabled=false");

        System.out.println(" Setting Chrome Options Completed.... ");

        System.out.println(" Initializing Chrome Driver.... ");

        // This for headless mode (runs in background)
        // WebDriver driver = new ChromeDriver(options);

        // this for non-headless mode (runs with browser UI)
        WebDriver driver = new ChromeDriver();
        System.out.println(" Chrome Driver Initialized.... ");

        driver.manage().window().maximize();

        System.out.println(" Before Navigating to URL.... ");
        driver.get(url);

        System.out.println(" After Navigated to URL.... ");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        System.out.println(" Locating Date Input Element.... ");
        WebElement dateinput = driver.findElement(By.cssSelector(inputCssSelector));

        System.out.println(" Clearing Date Input Element.... ");
        dateinput.clear();

        System.out.println(" Sending Data to Date Input Element.... ");
        dateinput.sendKeys(inputCssSelectorData);
        dateinput.sendKeys(Keys.RETURN);
        List<Map<String, String>> jsonList = new ArrayList<>();
        Map<String, String> listOfAnchor = new HashMap<>();
        try {
            // System.out.println(" Submitting Date Input Element.... ");
            // WebElement clickOnShowButton =
            // wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(cssSelector)));

            // System.out.println(" Clicking on Show Button.... ");
            // clickOnShowButton.click();

            // Get all tables on the page
            // WebDriverWait waitForTable = new WebDriverWait(driver,
            // Duration.ofSeconds(30));

            List<WebElement> tables =
                    // waitForTable.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.tagName("table"),
                    // 2));
                    driver.findElements(By.tagName("table"));
            System.out.println("Total tables found: " + tables.size());

            if (tables.size() == 3) {
                WebElement table = tables.get(1); // pick the 2nd table
                // Get rows
                List<WebElement> rows = table.findElements(By.tagName("tr"));
                List<String> headers = new ArrayList<>();

                // try {
                for (int i = 0; i < rows.size(); i++) {
                    List<WebElement> cols = rows.get(i).findElements(By.xpath("./th|./td"));

                    // header row
                    if (i == 0) {
                        for (WebElement col : cols) {

                            String input = col.getText().trim();
                            headers.add(input);

                        }
                    } else {
                        Map<String, String> rowData = new LinkedHashMap<>();
                        // Find all anchor tags within the current row
                        List<WebElement> anchors = cols.stream()
                                .flatMap(c -> c.findElements(By.cssSelector("a[href]")).stream()) // correct CSS
                                                                                                  // selector
                                .toList();
                        // List<WebElement> anchors = driver.findElements(By.tagName("a"));
                        List<String> validHrefs = anchors.stream()
                                .map(a -> a.getAttribute("href"))
                                .filter(href -> href != null && !href.trim().isEmpty())
                                .filter(href -> !href.equalsIgnoreCase(
                                        "https://khanansoft.bihar.gov.in/portal/CitizenRpt/epassreportAllDist.aspx#"))
                                .collect(Collectors.toList());
                        for (int k = 0; k < validHrefs.size(); k++) {
                            System.out.println("Index: " + k + " → Valid Href: " + validHrefs.get(k));
                        }

                        System.out.println("Anchor Size" + anchors.size());

                        for (int j = 0; j < cols.size(); j++) {
                            String key = j < headers.size() ? headers.get(j) : "Column" + j;
                            String value = cols.get(j).getText().trim();
                            rowData.put(key, value);

                            // Capture href links conditionally (like your JSoup code)
                            if (anchors.size() == 2) {
                                if (j == 4) {
                                    System.out.println("Anchor " + i);
                                    String key1 = " row = " + i + "col = " + j;
                                    // for(int k = 0; k < 1;k++) {
                                    // //rows.get(i).findElements(By.xpath("./td"));
                                    // key1 = cols.get(j).getText().trim()+ i + " " + j;
                                    // }
                                    // WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(220));
                                    // WebElement thirdAnchor = anchors.get(0);
                                    //
                                    // wait.until(ExpectedConditions.elementToBeClickable(thirdAnchor)).click();
                                    //
                                    // WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofMinutes(5));
                                    // Thread.sleep(300000);

                                    listOfAnchor.put(key1, anchors.get(0).getAttribute("href"));
                                }
                                if (j == 8) {
                                    System.out.println("Anchor " + i);
                                    String key1 = " row = " + i + "col = " + j;
                                    // anchors.get(1).click();
                                    listOfAnchor.put(key1, anchors.get(1).getAttribute("href"));
                                }
                            }
                        }
                        jsonList.add(rowData);
                    }
                }

                // ✅ Print anchor links (sorted by key)
                listOfAnchor.entrySet().stream().sorted(Map.Entry.comparingByKey())
                        .forEach(
                                entry -> System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue()));

                driver.quit();
            } else {
                System.out.println("Under Else");
                System.out.println("Before : tables found: ");
                WebElement table = tables.get(1); // pick the 2nd table
                System.out.println("After : tables found: ");
                // Get rows
                System.out.println("Before : rows with tr tag found: ");
                List<WebElement> rows = table.findElements(By.tagName("tr"));
                System.out.println("After : rows with tr tag found: ");
                List<String> headers = new ArrayList<>();

                System.out.println("Before : for loop found: ");
                for (int i = 0; i < rows.size(); i++) {
                    System.out.println("In for loop : rows size: " + rows.size());
                    System.out.println("Before : cols found: ");
                    List<WebElement> cols = rows.get(i).findElements(By.xpath("./th|./td"));
                    System.out.println("After : cols found: ");

                    // header row

                    System.out.println("Before If : i value: ");
                    if (i == 0) {
                        System.out.println("Under called Header if : i value: " + i);
                        for (WebElement col : cols) {

                            String input = col.getText().trim();
                            headers.add(input);

                        }
                    } else {
                        Map<String, String> rowData = new LinkedHashMap<>();
                        // Find all anchor tags within the current row
                        System.out.println("Before : anchors found: ");
                        List<WebElement> anchors = cols.stream()
                                .flatMap(c -> c.findElements(By.cssSelector("a[href]")).stream()) // correct CSS
                                                                                                  // selector
                                .toList();
                        // List<WebElement> anchors = driver.findElements(By.tagName("a"));
                        System.out.println("After : anchors found: ");
                        System.out.println("Before : validHrefs found: ");
                        List<String> validHrefs = anchors.stream()
                                .map(a -> a.getAttribute("href"))
                                .filter(href -> href != null && !href.trim().isEmpty())
                                .filter(href -> !href.equalsIgnoreCase(
                                        "https://khanansoft.bihar.gov.in/portal/CitizenRpt/epassreportAllDist.aspx#"))
                                .collect(Collectors.toList());
                        System.out.println("After : validHrefs found: ");
                        // for (int k = 0; k < validHrefs.size(); k++) {
                        // System.out.println("Index: " + k + " → Valid Href: " + validHrefs.get(k));
                        // }

                        System.out.println("Anchor Size" + anchors.size());

                        for (int j = 0; j < validHrefs.size(); j++) {
                            String key = j < headers.size() ? headers.get(j) : "Column" + j;
                            String value = cols.get(j).getText().trim();
                            rowData.put(key, value);

                            // Capture href links conditionally (like your JSoup code)
                            if (anchors.size() == 3) {
                                if (j == 4) {
                                    System.out.println("Anchor " + i);
                                    String key1 = " row = " + i + "col = " + j;
                                    // for(int k = 0; k < 1;k++) {
                                    // //rows.get(i).findElements(By.xpath("./td"));
                                    // key1 = cols.get(j).getText().trim()+ i + " " + j;
                                    // }
                                    // WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(220));
                                    // WebElement thirdAnchor = anchors.get(0);
                                    //
                                    // wait.until(ExpectedConditions.elementToBeClickable(thirdAnchor)).click();
                                    //
                                    // WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofMinutes(5));
                                    // Thread.sleep(300000);

                                    listOfAnchor.put(key1, anchors.get(0).getAttribute("href"));
                                }
                                if (j == 8) {
                                    System.out.println("Anchor " + i);
                                    String key1 = " row = " + i + "col = " + j;
                                    // anchors.get(1).click();
                                    listOfAnchor.put(key1, anchors.get(1).getAttribute("href"));
                                }
                            }
                            if (anchors.size() == 2) {
                                if (j == 4) {
                                    System.out.println("Anchor " + i);
                                    String key1 = " row = " + i + "col = " + j;
                                    // for(int k = 0; k < 1;k++) {
                                    // //rows.get(i).findElements(By.xpath("./td"));
                                    // key1 = cols.get(j).getText().trim()+ i + " " + j;
                                    // }
                                    // WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(220));
                                    // WebElement thirdAnchor = anchors.get(0);
                                    //
                                    // wait.until(ExpectedConditions.elementToBeClickable(thirdAnchor)).click();
                                    //
                                    // WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofMinutes(5));
                                    // Thread.sleep(300000);

                                    listOfAnchor.put(key1, anchors.get(0).getAttribute("href"));
                                }
                                if (j == 8) {
                                    System.out.println("Anchor " + i);
                                    String key1 = " row = " + i + "col = " + j;
                                    // anchors.get(1).click();
                                    listOfAnchor.put(key1, anchors.get(1).getAttribute("href"));
                                }
                            }
                        }
                        jsonList.add(rowData);
                    }
                }

                // ✅ Print anchor links (sorted by key)
                listOfAnchor.entrySet().stream().sorted(Map.Entry.comparingByKey())
                        .forEach(
                                entry -> System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue()));
                driver.quit();
                return jsonList;

                // driver.quit();

                // return null;
            }
        } catch (StaleElementReferenceException e) {
            e.printStackTrace();
            System.out.println("StaleElementReferenceException encountered. Retrying...");
            driver.quit();
        }
        driver.quit();
        return jsonList;
    }

}
