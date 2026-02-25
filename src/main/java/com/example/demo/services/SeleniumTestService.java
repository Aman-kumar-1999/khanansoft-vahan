package com.example.demo.services;

// import io.github.bonigarcia.wdm.WebDriverManager;

// import org.jsoup.nodes.Document;
// import org.jsoup.nodes.Element;
// import org.openqa.selenium.*;
// import org.openqa.selenium.chrome.ChromeDriver;
// import org.openqa.selenium.chrome.ChromeOptions;
// import org.openqa.selenium.remote.UnreachableBrowserException;
// import org.openqa.selenium.support.ui.*;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.scheduling.annotation.Scheduled;
// import org.springframework.stereotype.Service;

// import com.example.demo.entity.KhananData;

// import java.time.Duration;
// import java.time.LocalDate;
// import java.time.LocalDateTime;
// import java.time.format.DateTimeFormatter;
// import java.util.ArrayList;
// import java.util.Collections;
// import java.util.HashMap;
// import java.util.LinkedHashMap;
// import java.util.List;
// import java.util.Map;
// import java.util.concurrent.atomic.AtomicBoolean;
// import java.util.stream.Collectors;

// import javax.lang.model.util.Elements;

// @Service
// public class SeleniumTestService {

//     @Autowired
//     public KhananDataService khananDataService;

//     // Add these constants at the top of the class after the service injection
//     private static final String INVALID_HREF = "https://khanansoft.bihar.gov.in/portal/CitizenRpt/epassreportAllDist.aspx#";
//     private static final int TABLE_INDEX = 1;
//     private static final int WAIT_TIME_MS = 2000;
//     private static final String VIEW_ALL_BTN_ID = "ctl00_MainContent_lbtnAll";
//     private static final String PASS_TYPE_ID = "ctl00_MainContent_ddlPassType";
//     private static final String DISTRICT_ID = "ctl00_MainContent_ddlDMO";
//     private static final String CONSIGNER_ID = "ctl00_MainContent_ddlConsigner";
//     private static final String DATE_ID = "ctl00_MainContent_txtDate";
//     private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
//     private static final Logger logger = LoggerFactory.getLogger(SeleniumTestService.class);

//     private final AtomicBoolean isProcessing = new AtomicBoolean(false);
//     private String currentStatusMessage = "IDLE";

//     public boolean isCurrentlyRunning() {
//         return isProcessing.get();
//     }

//     public String getStatusMessage() {
//         return currentStatusMessage;
//     }

//     // Helper method to setup Chrome driver
//     // private WebDriver setupChromeDriver(boolean headless) {
//     // WebDriverManager.chromedriver().setup();

//     // System.out.println(" Setting Chrome Options.... ");
//     // ChromeOptions options = new ChromeOptions();
//     // System.out.println(" Setting Chrome Options - Headless.... ");
//     // options.addArguments("--headless=new");
//     // System.out.println(" Setting Chrome Options - No Sandbox.... ");
//     // options.addArguments("--no-sandbox");
//     // System.out.println(" Setting Chrome Options - Disable Dev SHM Usage.... ");
//     // options.addArguments("--disable-dev-shm-usage");
//     // System.out.println(" Setting Chrome Options - Disable GPU.... ");
//     // options.addArguments("--disable-gpu");

//     // // ChromeOptions options = new ChromeOptions();
//     // // if (headless) {
//     // // options.addArguments("--headless=new");
//     // // }
//     // // options.addArguments("--no-sandbox");
//     // // options.addArguments("--disable-dev-shm-usage");
//     // // options.addArguments("--disable-gpu");
//     // return new ChromeDriver(options);
//     // }
//     private WebDriver setupChromeDriver() {
//         WebDriverManager.chromedriver().setup();
//         ChromeOptions options = new ChromeOptions();

//         options.addArguments("--headless=new"); // Best for Servers/Schedulers
//         options.addArguments("--no-sandbox");
//         options.addArguments("--disable-dev-shm-usage");
//         options.addArguments("--window-size=1920,1080");
//         options.addArguments("--disable-gpu");
//         // Helps bypass basic bot detection
//         // options.addArguments(
//         //         "--user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36");

//         return new ChromeDriver(options);
//     }

//     // Helper method to set date input
//     private void setDateInput(WebDriver driver, String dateInputSelector, String dateValue)
//             throws InterruptedException {
//         try {
//             WebElement dateInput = driver.findElement(By.cssSelector(dateInputSelector));
//             dateInput.clear();
//             dateInput.sendKeys(dateValue);
//             dateInput.sendKeys(Keys.RETURN);
//             // Thread.sleep(WAIT_TIME_MS);
//         } catch (Exception e) {
//             logger.error("Error setting date input: {}", dateValue, e);
//             throw new RuntimeException("Failed to set date input", e);
//         }
//     }

//     // Helper method to extract table data
//     private Map<String, String> extractAnchorFromRow(List<WebElement> cols, int rowIndex, int... anchorColumns) {
//         Map<String, String> anchors = new HashMap<>();
//         for (int colIndex : anchorColumns) {
//             if (colIndex < cols.size()) {
//                 List<WebElement> anchorElements = cols.get(colIndex).findElements(By.cssSelector("a[href]"));
//                 if (!anchorElements.isEmpty()) {
//                     String href = anchorElements.get(0).getAttribute("href");
//                     if (isValidHref(href)) {
//                         anchors.put(String.format("row=%d col=%d", rowIndex, colIndex), href);
//                     }
//                 }
//             }
//         }
//         return anchors;
//     }

//     // Helper method to validate href
//     private boolean isValidHref(String href) {
//         return href != null && !href.trim().isEmpty() && !href.equalsIgnoreCase(INVALID_HREF);
//     }

//     // Helper method to extract table content
//     // private Map<String, String> extractTableData(WebDriver driver, int
//     // tableIndex) {
//     // Map<String, String> tableAnchors = new HashMap<>();
//     // try {
//     // List<WebElement> tables = driver.findElements(By.tagName("table"));
//     // if (tables.size() <= tableIndex) {
//     // logger.info("Table at index {} not found. Total tables: {}", tableIndex,
//     // tables.size());
//     // return tableAnchors;
//     // }

//     // WebElement table = tables.get(tableIndex);
//     // List<WebElement> rows = table.findElements(By.tagName("tr"));
//     // logger.info("Rows found in table: {}", rows.size());

//     // for (int i = 1; i < rows.size(); i++) {
//     // List<WebElement> cols = rows.get(i).findElements(By.tagName("td"));
//     // Map<String, String> rowAnchors = extractAnchorFromRow(cols, i, 4, 8);
//     // tableAnchors.putAll(rowAnchors);
//     // }
//     // } catch (Exception e) {
//     // logger.error("Error extracting table data", e);
//     // }
//     // return tableAnchors;
//     // }
//     private Map<String, String> extractTableData(WebDriver driver, int tableIndex) {
//         Map<String, String> tableAnchors = new HashMap<>();
//         WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

//         try {
//             // Wait until at least one table is present
//             wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("table")));

//             List<WebElement> tables = driver.findElements(By.tagName("table"));
//             if (tables.size() <= tableIndex)
//                 return tableAnchors;

//             WebElement table = tables.get(tableIndex);
//             // Wait for rows to be populated
//             List<WebElement> rows = table.findElements(By.tagName("tr"));

//             for (int i = 1; i < rows.size(); i++) {
//                 try {
//                     List<WebElement> cols = rows.get(i).findElements(By.tagName("td"));
//                     // 4 and 8 are your target columns for links
//                     Map<String, String> rowAnchors = extractAnchorFromRow(cols, i, 4, 8);
//                     tableAnchors.putAll(rowAnchors);
//                 } catch (StaleElementReferenceException e) {
//                     // If the DOM refreshed, skip this row and continue
//                     continue;
//                 }
//             }
//         } catch (Exception e) {
//             logger.error("Timeout or Error extracting table: {}", e.getMessage());
//         }
//         return tableAnchors;
//     }

//     // Helper method to click view all button
//     private void clickViewAllButton(WebDriver driver) throws InterruptedException {
//         try {

//             // WebElement viewAllBtn = driver.findElement(By.id(VIEW_ALL_BTN_ID));
//             // viewAllBtn.click();
//             try {
//                 By viewAllLocator = By.id(VIEW_ALL_BTN_ID);
//                 WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

//                 // 1. Wait for element to be present
//                 WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(viewAllLocator));

//                 // 2. Scroll to it (helps in headless mode)
//                 ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);

//                 // 3. Perform JavaScript click (Bypasses the "ElementClickInterceptedException")
//                 ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);

//                 logger.info("View All button clicked successfully via JS.");
//             } catch (Exception e) {
//                 logger.error("Failed to click view all button: " + e.getMessage());
//                 throw new RuntimeException("Failed to click view all button", e);
//             }
//             // Thread.sleep(WAIT_TIME_MS);
//         } catch (Exception e) {
//             logger.error("Error clicking view all button", e);
//             throw new RuntimeException("Failed to click view all button", e);
//         }
//     }

//     // Helper method to get dropdown value
//     private String getDropdownValue(WebDriver driver, String elementId) {
//         try {
//             WebElement element = driver.findElement(By.id(elementId));
//             Select select = new Select(element);
//             return select.getFirstSelectedOption().getText();
//         } catch (Exception e) {
//             logger.error("Error getting dropdown value for element: {}", elementId, e.getMessage());
//             return "N/A";
//         }
//     }

//     // Helper method to extract fourth layer data
//     private void processFourthLayer(WebDriver driver, String forthLayerUrl,
//             String sourceType, List<KhananData> khananDataList) throws InterruptedException {
//         try {
//             driver.get(forthLayerUrl);
//             driver.manage().window().maximize();
//             clickViewAllButton(driver);
//             Thread.sleep(WAIT_TIME_MS);

//             String districtValue = getDropdownValue(driver, DISTRICT_ID);
//             String consignerValue = getDropdownValue(driver, CONSIGNER_ID);
//             String dateValue = driver.findElement(By.id(DATE_ID)).getAttribute("value");

//             logger.info("Fourth Layer - District: {}, Consigner: {}, Date: {}",
//                     districtValue, consignerValue, dateValue);

//             List<WebElement> tables = driver.findElements(By.tagName("table"));
//             if (tables.size() >= 2) {
//                 List<WebElement> rows = tables.get(1).findElements(By.tagName("tr"));
//                 for (int i = 1; i < rows.size() - 1; i++) {
//                     List<WebElement> cols = rows.get(i).findElements(By.tagName("td"));
//                     if (cols.size() >= 11) {
//                         KhananData data = new KhananData(
//                                 districtValue, consignerValue, dateValue, sourceType,
//                                 getColumnText(cols, 1), getColumnText(cols, 2),
//                                 getColumnText(cols, 3), getColumnText(cols, 4),
//                                 getColumnText(cols, 5), getColumnText(cols, 6),
//                                 getColumnText(cols, 7), getColumnText(cols, 8),
//                                 getColumnText(cols, 9), getColumnText(cols, 10));
//                         khananDataList.add(data);
//                     }
//                 }
//                 khananDataService.saveAllKhananData(khananDataList);
//                 logger.info("Data saved successfully. Count: {}", khananDataList.size());
//             }
//         } catch (Exception e) {
//             logger.error("Error processing fourth layer", e.getMessage());
//         }
//     }

//     private String getColumnText(List<WebElement> cols, int index) {
//         try {
//             return index < cols.size() ? cols.get(index).getText().trim() : "";
//         } catch (Exception e) {
//             logger.warn("Error getting column text at index: {}", index);
//             return "";
//         }
//     }

//     // Main optimized method
//     // public List<Map<String, String>> newOpenWebsiteAndClickWithAnchorLoopDateWaise(String url,
//     //         String inputCssSelector, String inputCssSelectorData, String fromDate, String toDate) {

//     //     try {
//     //         logger.info("Starting web scraping process - URL: {}", url);

//     //         if (!validateInputs(url, inputCssSelector, inputCssSelectorData, fromDate, toDate)) {
//     //             logger.error("Invalid input parameters");
//     //             return Collections.emptyList();
//     //         }

//     //         WebDriver driver = null;
//     //         driver = setupChromeDriver(false);
//     //         driver.manage().window().maximize();
//     //         driver.get(url);

//     //         LocalDate startDate = LocalDate.parse(fromDate, DATE_FORMATTER);
//     //         LocalDate endDate = LocalDate.parse(toDate, DATE_FORMATTER);

//     //         for (LocalDate currentDate = startDate; !currentDate.isBefore(endDate); currentDate = currentDate
//     //                 .minusDays(1)) {
//     //             // Check if the program is trying to stop
//     //             if (Thread.currentThread().isInterrupted()) {
//     //                 break;
//     //             }

//     //             try {
//     //                 // processSecondLayer(entry.getValue());
//     //                 processDateIteration(driver, url, inputCssSelectorData, currentDate);
//     //             } catch (UnreachableBrowserException | NoSuchSessionException e) {
//     //                 // Log a clean message instead of a giant stack trace
//     //                 logger.warn("Browser session lost. Stopping process.");
//     //                 break;
//     //             } catch (Exception e) {
//     //                 logger.error("General error: " + e.getMessage());
//     //                 break;
//     //             }

//     //         }

//     //         return new ArrayList<>();
//     //     } catch (Exception e) {

//     //         logger.error("Error in scraping process", e.getMessage());

//     //         return Collections.emptyList();

//     //     } finally {
//     //         // Driver will be closed in the calling method to allow reuse across iterations

//     //     }
//     // }

//     public void scheduledScrapingTask(String url, String dateSelector, String fromDate, String toDate) {
//         WebDriver driver = null;
//         if (isProcessing.get()) {
//             logger.warn("Scraper is already running. Skipping this request.");
//             return;
//         }
//         try {
//             driver = setupChromeDriver();
//             LocalDate startDate = LocalDate.parse(fromDate, DATE_FORMATTER);
//             LocalDate endDate = LocalDate.parse(toDate, DATE_FORMATTER);
//             isProcessing.set(true);
//             currentStatusMessage = "RUNNING: Processing data starting from " + fromDate;

//             for (LocalDate date = startDate; !date.isBefore(endDate); date = date.minusDays(1)) {
//                 if (Thread.currentThread().isInterrupted()) break;
                
//                 processDateIteration(driver, url, dateSelector, date);
//                 // logger.info("Data : "+date);
//                 // Optional: Refresh session every 5 days to keep memory clean
//                 if (date.getDayOfMonth() % 5 == 0) {
//                     driver.manage().deleteAllCookies();
//                 }
//             }
//         } catch (Exception e) {
//             logger.error("Critical Failure in Scraper: ", e);
//         } finally {
//             isProcessing.set(false);
//             currentStatusMessage = "IDLE - Last run completed at " + LocalDateTime.now();
//             if (driver != null) {
//                 driver.quit(); // Memory safety
//                 logger.info("Browser session closed successfully.");
//             }
//         }
//     }

//     private boolean validateInputs(String... inputs) {
//         for (String input : inputs) {
//             if (input == null || input.trim().isEmpty()) {
//                 return false;
//             }
//         }
//         return true;
//     }

//     private void processDateIteration(WebDriver driver, String url, String dateSelector, LocalDate currentDate) {
//         String formattedDate = currentDate.format(DATE_FORMATTER);
//         try {
//             logger.info("Processing date: {}", formattedDate);
//             System.out.println("Processing date: ==================== " + formattedDate + "====================");

//             driver.get(url);
//             setDateInput(driver, dateSelector, formattedDate);
//             Thread.sleep(WAIT_TIME_MS);

//             Map<String, String> anchors = extractTableData(driver, TABLE_INDEX);
//             if (!anchors.isEmpty()) {
//                 anchors.values().forEach(allAnchorUrl -> {
//                     System.out.println("URL : " + allAnchorUrl);
//                     try {
//                         processSecondLayer(driver, allAnchorUrl);
//                     } catch (Exception e) {
//                         logger.error("Error processing second layer for URL: {}", allAnchorUrl, e.getMessage());
//                     }
//                 });
//                 // String firstAnchorUrl = anchors.values().iterator().next();
//                 // System.out.println("URL : "+firstAnchorUrl);
//                 // processSecondLayer(driver, firstAnchorUrl);
//             }
//         } catch (Exception e) {
//             logger.error("Error processing date iteration for: {}", formattedDate, e.getMessage());
//         }
//     }

//     private void processSecondLayer(WebDriver driver, String secondLayerUrl) {
//         try {
//             driver.get(secondLayerUrl);
//             driver.manage().window().maximize();
//             clickViewAllButton(driver);
//             Thread.sleep(WAIT_TIME_MS);

//             String sourceType = getDropdownValue(driver, PASS_TYPE_ID);
//             Map<String, String> secondLayerAnchors = extractTableData(driver, TABLE_INDEX);

//             if (!secondLayerAnchors.isEmpty()) {
//                 secondLayerAnchors.values().forEach(thirdLayerUrl -> {
//                     // System.out.println("URL : " + thirdLayerUrl);
//                     try {
//                         processThirdLayer(driver, thirdLayerUrl, sourceType);
//                     } catch (Exception e) {
//                         logger.error("Error processing third layer for URL: {}", thirdLayerUrl, e.getMessage());
//                     }
//                 });
//                 // String thirdLayerUrl = secondLayerAnchors.values().iterator().next();
//                 // processThirdLayer(driver, thirdLayerUrl, sourceType);
//             }
//         } catch (Exception e) {

//             logger.error("Error processing second layer", e.getMessage());
//             // logger.error("Browser died! Restarting...");

//         }
//     }

//     private void processThirdLayer(WebDriver driver, String thirdLayerUrl, String sourceType) {
//         try {
//             driver.get(thirdLayerUrl);
//             driver.manage().window().maximize();
//             clickViewAllButton(driver);
//             Thread.sleep(WAIT_TIME_MS);

//             Map<String, String> thirdLayerAnchors = extractTableData(driver, TABLE_INDEX);
//             if (!thirdLayerAnchors.isEmpty()) {
//                 thirdLayerAnchors.values().forEach(forthLayerUrl -> {
//                     System.out.println("URL : " + forthLayerUrl);
//                     try {
//                         List<KhananData> khananDataList = new ArrayList<>();
//                         processFourthLayer(driver, forthLayerUrl, sourceType, khananDataList);
//                     } catch (InterruptedException e) {
//                         logger.error("Interrupted while processing fourth layer for URL: {}", forthLayerUrl,
//                                 e.getMessage());
//                     }
//                 });
//                 // String forthLayerUrl = thirdLayerAnchors.values().iterator().next();
//                 // List<KhananData> khananDataList = new ArrayList<>();
//                 // processFourthLayer(driver, forthLayerUrl, sourceType, khananDataList);
//             }
//         } catch (Exception e) {
//             logger.error("Error processing third layer", e.getMessage());
//         }
//     }

//     // -----------------------------------------------------------------

//     @Scheduled(cron = "0 0 1 * * ?") // Schedule to run daily at 1:00 AM
//     public List<Map<String, String>> scrapeDataForCurrentDate()
//             throws InterruptedException {
//         logger.info("Starting web scraping process for Single Date. - URL: {}", INVALID_HREF);

//         String url = INVALID_HREF;
//         // String inputCssSelector = "#ctl00_MainContent_btnshow";
//         String inputCssSelectorData = "#ctl00_MainContent_txtDate1";

//         WebDriver driver = null;
//         try {
//             // driver = setupChromeDriver(false);
//             // driver.manage().window().maximize();
//             // driver.get(url);

//             // LocalDate currentDate = LocalDate.parse(LocalDate.now().toString(),
//             // DATE_FORMATTER);
//             // processDateIteration(driver, url, inputCssSelectorData, currentDate);

//             return new ArrayList<>();
//         } catch (Exception e) {
//             logger.error("Error in scraping process", e);
//             return Collections.emptyList();
//         } finally {
//             if (driver != null) {
//                 try {
//                     driver.quit();
//                 } catch (Exception e) {
//                     logger.warn("Error closing driver", e);
//                 }
//             }
//         }
//     }

//     // -----------------------------------------------------------------

//     public List<Map<String, String>> openWebsiteAndClick(String url, String cssSelector, String inputCssSelector,
//             String inputCssSelectorData) {
//         System.out.println(" Selenium Test Service Called.... ");
//         System.out.println(" URL : " + url);
//         System.out.println(" CSS Selector : " + cssSelector);
//         System.out.println(" Input CSS Selector : " + inputCssSelector);
//         System.out.println(" Input CSS Selector Data : " + inputCssSelectorData);

//         System.out.println(" Chrome Driver Setup Completed.... ");
//         WebDriverManager.chromedriver().setup();

//         // options.addArguments("--headless=new"); // runs in background
//         // options.addArguments("--no-sandbox");
//         // options.addArguments("--disable-gpu");
//         // options.addArguments("--disable-dev-shm-usage");
//         // WebDriver driver = new ChromeDriver(options);

//         // Use headless mode (important for EC2)
//         // To Run the below code in Headless mode on Linux Server (like AWS EC2), you
//         // need to set following options
//         // install the Google Chrome browser on your server
//         // command : sudo apt update
//         // command : sudo apt install -y wget unzip
//         // command : wget
//         // https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb
//         // command : sudo apt install -y ./google-chrome-stable_current_amd64.deb
//         // or
//         // Verify installation by running:
//         // command : sudo apt-get install -y google-chrome-stable
//         // install ChromeDriver compatible with your Chrome browser version on your
//         // server
//         // command : CHROME_VERSION=$(google-chrome --version | grep -oP
//         // '\d+\.\d+\.\d+')
//         // command : wget
//         // https://chromedriver.storage.googleapis.com/$CHROME_VERSION/chromedriver_linux64.zip
//         // command : unzip chromedriver_linux64.zip
//         // command : sudo mv chromedriver /usr/bin/chromedriver
//         // command : sudo chown root:root /usr/bin/chromedriver
//         // command : sudo chmod +x /usr/bin/chromedriver
//         // Now, you can run Selenium with Chrome in headless mode on your Linux server.

//         // Check if Google Chrome is installed
//         // command : google-chrome --version
//         // Check if ChromeDriver is installed
//         // command : chromedriver --version

//         System.out.println(" Setting Chrome Options.... ");
//         ChromeOptions options = new ChromeOptions();
//         System.out.println(" Setting Chrome Options - Headless.... ");
//         options.addArguments("--headless=new");
//         System.out.println(" Setting Chrome Options - No Sandbox.... ");
//         options.addArguments("--no-sandbox");
//         System.out.println(" Setting Chrome Options - Disable Dev SHM Usage.... ");
//         options.addArguments("--disable-dev-shm-usage");
//         System.out.println(" Setting Chrome Options - Disable GPU.... ");
//         options.addArguments("--disable-gpu");

//         // If using Google Chrome in your ubuntu server use this:
//         // options.setBinary("/usr/bin/google-chrome");

//         // System.out.println(" Setting Chrome Options - Window Size.... ");
//         // options.addArguments("window-size=1920,1080");
//         // System.out.println(" Setting Chrome Options - Disable Extensions.... ");
//         // options.addArguments("--disable-extensions");
//         // System.out.println(" Setting Chrome Options - Disable Images.... ");
//         // options.addArguments("blink-settings=imagesEnabled=false");

//         System.out.println(" Setting Chrome Options Completed.... ");

//         System.out.println(" Initializing Chrome Driver.... ");

//         // This for headless mode (runs in background)
//         WebDriver driver = new ChromeDriver(options);

//         // this for non-headless mode (runs with browser UI)
//         // WebDriver driver = new ChromeDriver();

//         System.out.println(" Chrome Driver Initialized.... ");
//         try {
//             driver.manage().window().maximize();
//             System.out.println(" Before Navigating to URL.... ");
//             driver.get(url);
//             System.out.println(" After Navigated to URL.... ");
//             WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
//             // wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(inputCssSelector)));
//             // wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(inputCssSelector)));
//             // #ctl00_MainContent_txtDate1
//             System.out.println(" Locating Date Input Element.... ");
//             WebElement dateinput =
//                     // wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(inputCssSelector)));
//                     driver.findElement(By.cssSelector(inputCssSelector));
//             // Define the formatter matching your input
//             // DateTimeFormatter inputFormatter =
//             // DateTimeFormatter.ofPattern("dd-MMM-yyyy");

//             // Parse the string to a LocalDate
//             // LocalDate date = LocalDate.parse(inputCssSelectorData, inputFormatter);

//             System.out.println(" Clearing Date Input Element.... ");
//             dateinput.clear();
//             System.out.println(" Sending Data to Date Input Element.... ");
//             dateinput.sendKeys(inputCssSelectorData);
//             dateinput.sendKeys(Keys.RETURN);
//             // WebElement clickOnShowButton = wait
//             // .until(ExpectedConditions.elementToBeClickable(By.cssSelector(cssSelector)));

//             // clickOnShowButton.click();

//             // Document doc =
//             //
//             // Element table = doc.select("table").get(1);

//             WebElement changedDate =
//                     // wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(inputCssSelector)));
//                     driver.findElement(By.cssSelector(inputCssSelector));
//             System.out.println(" Changed Data : " + changedDate.getText());

//             try {
//                 // Open the target website
//                 // driver.get("https://example.com");

//                 // ✅ Locate all tables on the page
//                 System.out.println("Extracting data from the second table on the page...");
//                 List<WebElement> allTables = driver.findElements(By.tagName("table"));

//                 List<Map<String, String>> jsonList = new ArrayList<>();
//                 // List<TableRowData> result = new ArrayList<>();
//                 Map<String, String> listOfAnchor = new HashMap<>();
//                 // Ensure there are at least 2 tables
//                 System.out.println("Total Tables Found: " + allTables.size());
//                 if (allTables.size() >= 2) {
//                     WebElement secondTable = allTables.get(1); // index starts from 0

//                     // Find all rows in the second table

//                     List<WebElement> rows = secondTable.findElements(By.tagName("tr"));
//                     List<String> headers = new ArrayList<>();
//                     System.out.println("Total Rows in Second Table: " + rows.size());

//                     // ✅ Loop through rows and extract each cell
//                     for (int i = 0; i < rows.size(); i++) {
//                         WebElement row = rows.get(i);
//                         List<WebElement> cols = row.findElements(By.tagName("td"));
//                         if (i == 0) {
//                             for (WebElement col : cols) {
//                                 headers.add(col.getText().trim());
//                             }
//                         } else {
//                             // WebElement anchors = cols.get(4);
//                             // List<WebElement> anchorElements = anchors.findElements(By.tagName("a"));

//                             // cols.select("a[href]");

//                             // System.out.println("Anchors Size ====== "+anchors.size());
//                             List<String> hrefs = new ArrayList<>();
//                             Map<String, String> rowData = new LinkedHashMap<>();

//                             for (int j = 0; j < cols.size(); j++) {
//                                 WebElement col = cols.get(j);
//                                 System.out.print(col.getText() + "\t");
//                                 // if (anchors.size() == 2) {
//                                 // String district = "";
//                                 // if (j == 4) {
//                                 // String key1 = j < headers.size() ? headers.get(j) : "i : " + i + " Column" +
//                                 // j;
//                                 // listOfAnchor.put(key1, anchors.get(0).absUrl("href"));
//                                 // district = cols.get(0).text();
//                                 // hrefs.add(anchors.get(0).absUrl("href"));
//                                 // }
//                                 // if (j == 8) {
//                                 // String key1 = j < headers.size() ? headers.get(j) : "i : " + i + " Column" +
//                                 // j;
//                                 // listOfAnchor.put(key1, anchors.get(1).absUrl("href"));
//                                 // district = cols.get(1).text();
//                                 // hrefs.add(anchors.get(1).absUrl("href"));
//                                 // }
//                                 //
//                                 // }
//                                 String key = j < headers.size() ? headers.get(j) : "Column" + j;
//                                 rowData.put(key, cols.get(j).getText().trim());

//                             }
//                             jsonList.add(rowData);
//                         }
//                         System.out.println();
//                     }

//                 } else {
//                     System.out.println("Less than two tables found on the page.");
//                 }
//                 listOfAnchor.entrySet().stream()
//                         .sorted(Map.Entry.comparingByKey()) // sort by key
//                         .forEach(
//                                 entry -> System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue()));
//                 return jsonList;
//             } catch (Exception e) {
//                 e.printStackTrace();
//                 return null;
//             } finally {
//                 driver.quit();
//             }

//             // String pageTitle = driver.getTitle();
//             // // Thread.sleep(90000);

//             // return "Clicked successfully! Page title: " + pageTitle;
//         } catch (Exception e) {
//             e.printStackTrace();
//             // return "Error: " + e.getMessage();
//             return null;
//         } finally {
//             driver.quit();

//         }
//     }

//     public String checkLoginFunction(String url, String username, String password) {
//         WebDriverManager.chromedriver().setup();
//         WebDriver driver = new ChromeDriver();

//         try {
//             driver.get(url);
//             driver.manage().window().maximize();

//             driver.findElement(By.id("username")).sendKeys(username);
//             driver.findElement(By.id("password")).sendKeys(password);
//             driver.findElement(By.id("loginButton")).click();

//             WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
//             wait.until(ExpectedConditions.urlContains("dashboard"));

//             return "✅ Login successful, redirected to: " + driver.getCurrentUrl();
//         } catch (Exception e) {
//             e.printStackTrace();
//             return "❌ Login failed: " + e.getMessage();
//         } finally {
//             driver.quit();
//         }
//     }

//     public List<Map<String, String>> openWebsiteAndClickWithAnchor(String url, String cssSelector,
//             String inputCssSelector,
//             String inputCssSelectorData) {
//         System.out.println(" Selenium Test Service Called.... ");
//         System.out.println(" URL : " + url);
//         System.out.println(" CSS Selector : " + cssSelector);
//         System.out.println(" Input CSS Selector : " + inputCssSelector);
//         System.out.println(" Input CSS Selector Data : " + inputCssSelectorData);

//         System.out.println(" Chrome Driver Setup Completed.... ");
//         WebDriverManager.chromedriver().setup();

//         // options.addArguments("--headless=new"); // runs in background
//         // options.addArguments("--no-sandbox");
//         // options.addArguments("--disable-gpu");
//         // options.addArguments("--disable-dev-shm-usage");
//         // WebDriver driver = new ChromeDriver(options);

//         // Use headless mode (important for EC2)
//         // To Run the below code in Headless mode on Linux Server (like AWS EC2), you
//         // need to set following options
//         // install the Google Chrome browser on your server
//         // command : sudo apt update
//         // command : sudo apt install -y wget unzip
//         // command : wget
//         // https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb
//         // command : sudo apt install -y ./google-chrome-stable_current_amd64.deb
//         // or
//         // Verify installation by running:
//         // command : sudo apt-get install -y google-chrome-stable
//         // install ChromeDriver compatible with your Chrome browser version on your
//         // server
//         // command : CHROME_VERSION=$(google-chrome --version | grep -oP
//         // '\d+\.\d+\.\d+')
//         // command : wget
//         // https://chromedriver.storage.googleapis.com/$CHROME_VERSION/chromedriver_linux64.zip
//         // command : unzip chromedriver_linux64.zip
//         // command : sudo mv chromedriver /usr/bin/chromedriver
//         // command : sudo chown root:root /usr/bin/chromedriver
//         // command : sudo chmod +x /usr/bin/chromedriver
//         // Now, you can run Selenium with Chrome in headless mode on your Linux server.

//         // Check if Google Chrome is installed
//         // command : google-chrome --version
//         // Check if ChromeDriver is installed
//         // command : chromedriver --version

//         System.out.println(" Setting Chrome Options.... ");
//         ChromeOptions options = new ChromeOptions();
//         System.out.println(" Setting Chrome Options - Headless.... ");
//         options.addArguments("--headless=new");
//         System.out.println(" Setting Chrome Options - No Sandbox.... ");
//         options.addArguments("--no-sandbox");
//         System.out.println(" Setting Chrome Options - Disable Dev SHM Usage.... ");
//         options.addArguments("--disable-dev-shm-usage");
//         System.out.println(" Setting Chrome Options - Disable GPU.... ");
//         options.addArguments("--disable-gpu");

//         // If using Google Chrome in your ubuntu server use this:
//         // options.setBinary("/usr/bin/google-chrome");

//         // System.out.println(" Setting Chrome Options - Window Size.... ");
//         // options.addArguments("window-size=1920,1080");
//         // System.out.println(" Setting Chrome Options - Disable Extensions.... ");
//         // options.addArguments("--disable-extensions");
//         // System.out.println(" Setting Chrome Options - Disable Images.... ");
//         // options.addArguments("blink-settings=imagesEnabled=false");

//         System.out.println(" Setting Chrome Options Completed.... ");

//         System.out.println(" Initializing Chrome Driver.... ");

//         // This for headless mode (runs in background)
//         // WebDriver driver = new ChromeDriver(options);

//         // this for non-headless mode (runs with browser UI)
//         WebDriver driver = new ChromeDriver();

//         System.out.println(" Chrome Driver Initialized.... ");
//         try {
//             driver.manage().window().maximize();
//             System.out.println(" Before Navigating to URL.... ");
//             driver.get(url);
//             System.out.println(" After Navigated to URL.... ");
//             // WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
//             // wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(inputCssSelector)));
//             // wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(inputCssSelector)));
//             // #ctl00_MainContent_txtDate1
//             System.out.println(" Locating Date Input Element.... ");
//             WebElement dateinput =
//                     // wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(inputCssSelector)));
//                     driver.findElement(By.cssSelector(inputCssSelector));
//             // Define the formatter matching your input
//             // DateTimeFormatter inputFormatter =
//             // DateTimeFormatter.ofPattern("dd-MMM-yyyy");

//             // Parse the string to a LocalDate
//             // LocalDate date = LocalDate.parse(inputCssSelectorData, inputFormatter);

//             System.out.println(" Clearing Date Input Element.... ");
//             dateinput.clear();
//             System.out.println(" Sending Data to Date Input Element.... ");
//             dateinput.sendKeys(inputCssSelectorData);
//             dateinput.sendKeys(Keys.RETURN);
//             // WebElement clickOnShowButton = wait
//             // .until(ExpectedConditions.elementToBeClickable(By.cssSelector(cssSelector)));

//             // clickOnShowButton.click();

//             // Document doc =
//             //
//             // Element table = doc.select("table").get(1);

//             // WebElement changedDate =
//             // //
//             // wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(inputCssSelector)));
//             // driver.findElement(By.cssSelector(inputCssSelector));
//             // System.out.println(" Changed Data : " + changedDate.getText());

//             Thread.sleep(2000);

//             try {
//                 // Open the target website
//                 // driver.get("https://example.com");

//                 // ✅ Locate all tables on the page
//                 System.out.println("Extracting data from the second table on the page...");
//                 List<WebElement> allTables = driver.findElements(By.tagName("table"));

//                 List<Map<String, String>> jsonList = new ArrayList<>();
//                 // List<TableRowData> result = new ArrayList<>();
//                 Map<String, String> listOfAnchor = new HashMap<>();
//                 // Ensure there are at least 2 tables
//                 System.out.println("Total Tables Found: " + allTables.size());
//                 if (allTables.size() >= 2) {
//                     WebElement secondTable = allTables.get(1); // index starts from 0

//                     // Find all rows in the second table

//                     List<WebElement> rows = secondTable.findElements(By.tagName("tr"));
//                     List<String> headers = new ArrayList<>();
//                     System.out.println("Total Rows in Second Table: " + rows.size());

//                     // ✅ Loop through rows and extract each cell
//                     for (int i = 1; i < rows.size(); i++) {
//                         WebElement row = rows.get(i);
//                         List<WebElement> cols = row.findElements(By.tagName("td"));
//                         if (i == 1) {
//                             for (WebElement col : cols) {
//                                 headers.add(col.getText().trim());
//                             }
//                         } else {
//                             // WebElement anchors = cols.get(4);
//                             // List<WebElement> anchorElements = anchors.findElements(By.tagName("a"));

//                             // cols.select("a[href]");

//                             // System.out.println("Anchors Size ====== "+anchors.size());
//                             Map<String, String> rowData = new LinkedHashMap<>();
//                             // Find all anchor tags within the current row
//                             List<WebElement> anchors = cols.stream()
//                                     .flatMap(c -> c.findElements(By.cssSelector("a[href]")).stream()) // correct CSS
//                                                                                                       // selector
//                                     .toList();
//                             // List<WebElement> anchors = driver.findElements(By.tagName("a"));
//                             List<String> validHrefs = anchors.stream()
//                                     .map(a -> a.getAttribute("href"))
//                                     .filter(href -> href != null && !href.trim().isEmpty())
//                                     .filter(href -> !href.equalsIgnoreCase(
//                                             "https://khanansoft.bihar.gov.in/portal/CitizenRpt/epassreportAllDist.aspx#"))
//                                     .collect(Collectors.toList());
//                             // for (int k = 0; k < validHrefs.size(); k++) {
//                             // System.out.println("Index: " + k + " → Valid Href: " + validHrefs.get(k));
//                             // }

//                             for (int j = 0; j < cols.size(); j++) {
//                                 WebElement col = cols.get(j);
//                                 System.out.print(col.getText() + "\t");

//                                 if (anchors.size() == 2 || anchors.size() == 3) {
//                                     if (j == 4) {
//                                         // System.out.println("Anchor " + i);
//                                         String key1 = " row = " + (i - 1) + " col = " + j;
//                                         // for(int k = 0; k < 1;k++) {
//                                         // //rows.get(i).findElements(By.xpath("./td"));
//                                         // key1 = cols.get(j).getText().trim()+ i + " " + j;
//                                         // }
//                                         // WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(220));
//                                         // WebElement thirdAnchor = anchors.get(0);
//                                         //
//                                         // wait.until(ExpectedConditions.elementToBeClickable(thirdAnchor)).click();
//                                         //
//                                         // WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofMinutes(5));
//                                         // Thread.sleep(300000);
//                                         if (!anchors.get(0).getAttribute("href").equalsIgnoreCase(
//                                                 "https://khanansoft.bihar.gov.in/portal/CitizenRpt/epassreportAllDist.aspx#")) {
//                                             listOfAnchor.put(key1, anchors.get(0).getAttribute("href"));
//                                         }
//                                         // listOfAnchor.put(key1, anchors.get(0).getAttribute("href"));
//                                     }
//                                     if (j == 8) {
//                                         // System.out.println("Anchor " + i);
//                                         String key1 = " row = " + (i - 1) + " col = " + j;
//                                         // anchors.get(1).click();
//                                         if (!anchors.get(1).getAttribute("href").equalsIgnoreCase(
//                                                 "https://khanansoft.bihar.gov.in/portal/CitizenRpt/epassreportAllDist.aspx#")) {
//                                             listOfAnchor.put(key1, anchors.get(1).getAttribute("href"));
//                                         }
//                                         // listOfAnchor.put(key1, anchors.get(1).getAttribute("href"));
//                                     }
//                                 }
//                                 String key = j < headers.size() ? headers.get(j) : "Column" + j;
//                                 rowData.put(key, cols.get(j).getText().trim());

//                             }
//                             jsonList.add(rowData);
//                         }
//                         System.out.println();
//                     }

//                 } else {
//                     System.out.println("Less than two tables found on the page.");
//                 }
//                 // listOfAnchor.entrySet().stream()
//                 // .sorted(Map.Entry.comparingByKey()) // sort by key
//                 // .forEach(
//                 // entry -> System.out.println("Key: " + entry.getKey() + ", Value: " +
//                 // entry.getValue()));

//                 // listOfAnchor.entrySet()
//                 // .stream()
//                 // .sorted(Map.Entry.comparingByKey()) // sort by key
//                 // .forEach(entry -> {
//                 // System.out.println("Key: " + entry.getKey() + ", Value: " +
//                 // entry.getValue());
//                 // // driver.switchTo().newWindow(WindowType.TAB);
//                 // driver.get(entry.getValue());
//                 // }
//                 // );

//                 // Second layer Work
//                 listOfAnchor.entrySet()
//                         .stream()
//                         .sorted(Map.Entry.comparingByKey())
//                         // .findFirst()
//                         // .ifPresent
//                         .forEach(entry -> {

//                             System.out.println("Second Layer Key: " + entry.getKey() + ", Value: " + entry.getValue());
//                             try {
//                                 driver.manage().window().maximize();
//                                 driver.get(entry.getValue());
//                                 // driver.get(
//                                 // "https://khanansoft.bihar.gov.in/portal/CitizenRpt/ePassReportAllConsigner.aspx?ASHxNlmoFyKWVTw3JRIqNxcbqoTn8ifRT+5+Fljj+RIW2b6TUpdEC3Ri/5qzPs8e8hYZcCCpckeZiacSTWEkBUwOIYewYlXpnpULwNe+ZheGUBijC+XPRUr6yH2w2+1ZkrCabjgDzhg=");
//                                 WebElement viewAllElement = driver.findElement(By.id("ctl00_MainContent_lbtnAll"));
//                                 viewAllElement.click();
//                                 Thread.sleep(2000);
//                                 WebElement sourceTypeElement = driver.findElement(By
//                                         .id("ctl00_MainContent_ddlPassType"));

//                                 Select sourceTypeSelect = new Select(sourceTypeElement);
//                                 String sourceTypeValue = sourceTypeSelect.getFirstSelectedOption().getText();

//                                 List<WebElement> secondLayerallTables = driver.findElements(By.tagName("table"));

//                                 // List<Map<String, String>> secondLayerJsonList = new ArrayList<>();
//                                 // List<TableRowData> result = new ArrayList<>();
//                                 Map<String, String> secondLayerListOfAnchor = new HashMap<>();

//                                 // Ensure there are at least 2 tables
//                                 System.out.println("Total Tables Found: " + secondLayerallTables.size());
//                                 if (secondLayerallTables.size() >= 2) {
//                                     WebElement secondLayersecondTable = secondLayerallTables.get(1); // index starts
//                                                                                                      // from 0

//                                     // Find all rows in the second table

//                                     List<WebElement> rows = secondLayersecondTable.findElements(By.tagName("tr"));
//                                     List<String> headers = new ArrayList<>();
//                                     System.out.println("Total Rows in Second Table: " + rows.size());

//                                     // ✅ Loop through rows and extract each cell
//                                     for (int i = 1; i < rows.size(); i++) {
//                                         WebElement row = rows.get(i);
//                                         List<WebElement> cols = row.findElements(By.tagName("td"));
//                                         // if (i == 1) {
//                                         // for (WebElement col : cols) {
//                                         // headers.add(col.getText().trim());
//                                         // }
//                                         // } else {
//                                         // Map<String, String> rowData = new LinkedHashMap<>();
//                                         // List<WebElement> anchors = cols.stream()
//                                         // .flatMap(c -> c.findElements(By.cssSelector("a[href]")).stream()) // correct
//                                         // CSS
//                                         // // selector
//                                         // .toList();
//                                         for (int j = 0; j < cols.size(); j++) {
//                                             WebElement col = cols.get(j);
//                                             System.out.print(col.getText() + "\t");
//                                             // if (j == 4) {
//                                             // String key1 = " row = " + (i ) + " col = " + j;
//                                             // if (!anchors.get(0).getAttribute("href").equalsIgnoreCase(
//                                             // "https://khanansoft.bihar.gov.in/portal/CitizenRpt/epassreportAllDist.aspx#"))
//                                             // {
//                                             // secondLayerListOfAnchor.put(key1, anchors.get(0).getAttribute("href"));
//                                             // }
//                                             // // String link =
//                                             // cols.get(j).findElement(By.cssSelector("a[href]")).getAttribute("href");
//                                             // // System.out.println(" Link : " + link);
//                                             // }

//                                             if (j == 4) {
//                                                 List<WebElement> anchorElements = col
//                                                         .findElements(By.cssSelector("a[href]"));

//                                                 if (!anchorElements.isEmpty()) {
//                                                     String href = anchorElements.get(0).getAttribute("href");

//                                                     if (!href.equalsIgnoreCase(
//                                                             "https://khanansoft.bihar.gov.in/portal/CitizenRpt/epassreportAllDist.aspx#")) {

//                                                         String key1 = "row=" + i + " col=" + j;
//                                                         secondLayerListOfAnchor.put(key1, href);
//                                                     }
//                                                 }
//                                             }

//                                             // String key = j < headers.size() ? headers.get(j) : "Column" + j;
//                                             // rowData.put(key, cols.get(j).getText().trim());

//                                         }
//                                         // secondLayerJsonList.add(rowData);
//                                         // }
//                                         System.out.println();
//                                     }
//                                     // Third Layer Work
//                                     secondLayerListOfAnchor.entrySet().stream()
//                                             .sorted(Map.Entry.comparingByKey()) // sort by key
//                                             // .findFirst()
//                                             // .ifPresent
//                                             .forEach(e -> {
//                                                 System.out.println(
//                                                         "Third Layer Key: " + e.getKey() + ", Value: " + e.getValue());
//                                                 try {
//                                                     driver.manage().window().maximize();
//                                                     driver.get(e.getValue());
//                                                     // driver.get(
//                                                     // "https://khanansoft.bihar.gov.in/portal/CitizenRpt/ePassRptByConsigner.aspx?ASHxNlmoFyKWVTw3JRIqN0OYDRqYeiSRVE5mv0Ad+jmKwd7ghMUE8Q6Lpj70hq/xUORwZiTS3RyBA8iuNxpnC6oIVEGL/6q//fhbKMCV9a6urMznWnMFF8ObySfdL2phbW/NH+VBuR0=");
//                                                     WebElement viewAllElementOfThirdLayer = driver
//                                                             .findElement(By.id("ctl00_MainContent_lbtnAll"));
//                                                     viewAllElementOfThirdLayer.click();
//                                                     Thread.sleep(2000);
//                                                     List<WebElement> thiredLayerallTables = driver
//                                                             .findElements(By.tagName("table"));

//                                                     // List<Map<String, String>> secondLayerJsonList = new
//                                                     // ArrayList<>();
//                                                     // List<TableRowData> result = new ArrayList<>();
//                                                     Map<String, String> thirdLayerListOfAnchor = new HashMap<>();

//                                                     // Ensure there are at least 2 tables
//                                                     System.out.println(
//                                                             "Total Tables Found: " + thiredLayerallTables.size());
//                                                     if (thiredLayerallTables.size() >= 2) {
//                                                         WebElement thirdLayerTable = thiredLayerallTables.get(1); // index
//                                                                                                                   // starts
//                                                                                                                   // from
//                                                                                                                   // 0

//                                                         // Find all rows in the second table

//                                                         List<WebElement> rowsthird = thirdLayerTable
//                                                                 .findElements(By.tagName("tr"));
//                                                         List<String> headersThird = new ArrayList<>();
//                                                         System.out
//                                                                 .println("Total Rows in Third Layer Table: "
//                                                                         + rowsthird.size());
//                                                         // ✅ Loop through rows and extract each cell
//                                                         for (int i = 1; i < rowsthird.size(); i++) {
//                                                             WebElement row = rowsthird.get(i);
//                                                             List<WebElement> cols = row.findElements(By.tagName("td"));
//                                                             // if (i == 1) {
//                                                             // for (WebElement col : cols) {
//                                                             // headers.add(col.getText().trim());
//                                                             // }
//                                                             // } else {
//                                                             // Map<String, String> rowData = new LinkedHashMap<>();
//                                                             // List<WebElement> anchors = cols.stream()
//                                                             // .flatMap(c ->
//                                                             // c.findElements(By.cssSelector("a[href]")).stream()) //
//                                                             // correct
//                                                             // CSS
//                                                             // // selector
//                                                             // .toList();
//                                                             for (int j = 0; j < cols.size(); j++) {
//                                                                 WebElement col = cols.get(j);
//                                                                 System.out.print(col.getText() + "\t");
//                                                                 // if (j == 4) {
//                                                                 // String key1 = " row = " + (i ) + " col = " + j;
//                                                                 // if
//                                                                 // (!anchors.get(0).getAttribute("href").equalsIgnoreCase(
//                                                                 // "https://khanansoft.bihar.gov.in/portal/CitizenRpt/epassreportAllDist.aspx#"))
//                                                                 // {
//                                                                 // secondLayerListOfAnchor.put(key1,
//                                                                 // anchors.get(0).getAttribute("href"));
//                                                                 // }
//                                                                 // // String link =
//                                                                 // cols.get(j).findElement(By.cssSelector("a[href]")).getAttribute("href");
//                                                                 // // System.out.println(" Link : " + link);
//                                                                 // }

//                                                                 if (j == 4) {
//                                                                     List<WebElement> anchorElements = col
//                                                                             .findElements(By.cssSelector("a[href]"));

//                                                                     if (!anchorElements.isEmpty()) {
//                                                                         String href = anchorElements.get(0)
//                                                                                 .getAttribute("href");

//                                                                         if (!href.equalsIgnoreCase(
//                                                                                 "https://khanansoft.bihar.gov.in/portal/CitizenRpt/epassreportAllDist.aspx#")) {

//                                                                             String key1 = "row=" + i + " col=" + j;
//                                                                             thirdLayerListOfAnchor.put(key1, href);
//                                                                         }
//                                                                     }
//                                                                 }

//                                                                 // String key = j < headers.size() ? headers.get(j) :
//                                                                 // "Column" + j;
//                                                                 // rowData.put(key, cols.get(j).getText().trim());

//                                                             }
//                                                             // secondLayerJsonList.add(rowData);
//                                                             // }
//                                                             System.out.println();
//                                                         }
//                                                         // Forth Layer Work
//                                                         thirdLayerListOfAnchor.entrySet().stream()
//                                                                 .sorted(Map.Entry.comparingByKey()) // sort by key
//                                                                 // .findFirst()
//                                                                 // .ifPresent
//                                                                 .forEach(ent -> {
//                                                                     System.out
//                                                                             .println("------------------------------");
//                                                                     System.out
//                                                                             .println("Forth Layer Key: " + ent.getKey()
//                                                                                     + ", Value: " + ent.getValue());
//                                                                     System.out
//                                                                             .println("------------------------------");
//                                                                     try {
//                                                                         // driver.manage().window().maximize();
//                                                                         driver.get(ent.getValue());

//                                                                         // driver.get(
//                                                                         // "https://khanansoft.bihar.gov.in/portal/CitizenRpt/ePassRptByConsigner.aspx?ASHxNlmoFyKWVTw3JRIqN0OYDRqYeiSRVE5mv0Ad+jmKwd7ghMUE8Q6Lpj70hq/xUORwZiTS3RyBA8iuNxpnC6oIVEGL/6q//fhbKMCV9a6urMznWnMFF8ObySfdL2phbW/NH+VBuR0=");
//                                                                         WebElement viewAllElementForth = driver
//                                                                                 .findElement(By.id(
//                                                                                         "ctl00_MainContent_lbtnAll"));

//                                                                         viewAllElementForth.click();
//                                                                         Thread.sleep(2000);

//                                                                         WebElement district = driver.findElement(By
//                                                                                 .id(
//                                                                                         "ctl00_MainContent_ddlDMO"));
//                                                                         WebElement consigner = driver.findElement(By
//                                                                                 .id(
//                                                                                         "ctl00_MainContent_ddlConsigner"));
//                                                                         WebElement date = driver.findElement(By
//                                                                                 .id(
//                                                                                         "ctl00_MainContent_txtDate"));

//                                                                         Select districtSelect = new Select(district);
//                                                                         String districtValue = districtSelect
//                                                                                 .getFirstSelectedOption().getText();

//                                                                         Select consignerSelect = new Select(consigner);
//                                                                         String consignerValue = consignerSelect
//                                                                                 .getFirstSelectedOption().getText();

//                                                                         String dateValue = date.getAttribute("value");
//                                                                         String sourceType = sourceTypeValue;

//                                                                         System.out.println(districtValue);
//                                                                         System.out.println(consignerValue);
//                                                                         System.out.println(dateValue);
//                                                                         System.out.println(sourceType);

//                                                                         List<WebElement> forthLayerallTables = driver
//                                                                                 .findElements(By.tagName("table"));

//                                                                         // List<Map<String, String>> secondLayerJsonList
//                                                                         // = new ArrayList<>();
//                                                                         // List<TableRowData> result = new
//                                                                         // ArrayList<>();
//                                                                         Map<String, String> forthLayerListOfAnchor = new HashMap<>();

//                                                                         // Ensure there are at least 2 tables
//                                                                         System.out.println("Total Tables Found: "
//                                                                                 + forthLayerallTables.size());
//                                                                         List<KhananData> khananDataList = new ArrayList<>();
//                                                                         if (forthLayerallTables.size() >= 2) {
//                                                                             WebElement forthLayersecondTable = forthLayerallTables
//                                                                                     .get(1); // index starts from 0

//                                                                             // Find all rows in the second table

//                                                                             List<WebElement> rowsForth = forthLayersecondTable
//                                                                                     .findElements(By.tagName("tr"));
//                                                                             List<String> headersForth = new ArrayList<>();
//                                                                             System.out.println(
//                                                                                     "Total Rows in Second Table: "
//                                                                                             + rowsForth.size());

//                                                                             // ✅ Loop through rows and extract each cell
//                                                                             for (int i = 1; i < rowsForth.size()
//                                                                                     - 1; i++) {
//                                                                                 WebElement row = rowsForth.get(i);
//                                                                                 List<WebElement> cols = row
//                                                                                         .findElements(By.tagName("td"));
//                                                                                 // DateTimeFormatter formatter =
//                                                                                 // DateTimeFormatter.ofPattern("yyyy-MM-dd
//                                                                                 // HH:mm:ss");
//                                                                                 // String createdAtValue =
//                                                                                 // LocalDateTime.now().format(formatter);
//                                                                                 KhananData khananData = new KhananData(
//                                                                                         districtValue,
//                                                                                         consignerValue,
//                                                                                         dateValue,
//                                                                                         sourceType,
//                                                                                         cols.get(1).getText().trim(),
//                                                                                         cols.get(2).getText().trim(),
//                                                                                         cols.get(3).getText().trim(),
//                                                                                         cols.get(4).getText().trim(),
//                                                                                         cols.get(5).getText().trim(),
//                                                                                         cols.get(6).getText().trim(),
//                                                                                         cols.get(7).getText().trim(),
//                                                                                         cols.get(8).getText().trim(),
//                                                                                         cols.get(9).getText().trim(),
//                                                                                         cols.get(10).getText().trim());
//                                                                                 khananDataList.add(khananData);
//                                                                                 for (int j = 0; j < cols.size(); j++) {
//                                                                                     WebElement col = cols.get(j);
//                                                                                     System.out.print(
//                                                                                             col.getText() + "\t");

//                                                                                 }

//                                                                                 System.out.println();
//                                                                             }
//                                                                             khananDataService
//                                                                                     .saveAllKhananData(khananDataList);
//                                                                             System.out.println(
//                                                                                     "Data Saved Successfully.");
//                                                                             // ✅ End of Loop
//                                                                             // secondLayerListOfAnchor.entrySet().stream()
//                                                                             // .sorted(Map.Entry.comparingByKey()) //
//                                                                             // sort by key
//                                                                             // .forEach(entry -> System.out
//                                                                             // .println("Key: " + entry.getKey() + ",
//                                                                             // Value: " + entry.getValue()));
//                                                                         } else {
//                                                                             System.out.println(
//                                                                                     "Less than two tables found on the page.");
//                                                                         }

//                                                                         // });
//                                                                     } catch (Exception exc) {
//                                                                         exc.printStackTrace();
//                                                                     }

//                                                                 });
//                                                     } else {
//                                                         System.out.println("Less than two tables found on the page.");
//                                                     }

//                                                     // });
//                                                 } catch (Exception ex) {
//                                                     ex.printStackTrace();
//                                                 }
//                                             });
//                                 } else {
//                                     System.out.println("Less than two tables found on the page.");
//                                 }

//                                 // });
//                             } catch (Exception e) {
//                                 e.printStackTrace();
//                             }

//                         });

//                 return jsonList;
//             } catch (Exception e) {
//                 e.printStackTrace();
//                 return null;
//             } finally {
//                 // driver.quit();
//             }

//             // String pageTitle = driver.getTitle();
//             // // Thread.sleep(90000);

//             // return "Clicked successfully! Page title: " + pageTitle;
//         } catch (Exception e) {
//             e.printStackTrace();
//             // return "Error: " + e.getMessage();
//             return null;
//         } finally {
//             // driver.quit();

//         }
//     }

//     public List<Map<String, String>> openWebsiteAndClickWithAnchorLoopDateWaiseInASameMethod(String url,
//             String inputCssSelector, // button
//             String inputCssSelectorData, // date
//             String fromDate,
//             String toDate) throws InterruptedException {
//         // ) {
//         System.out.println(" Selenium Test Service Called.... ");
//         System.out.println(" URL : " + url);
//         // System.out.println(" CSS Selector : " + cssSelector);
//         // System.out.println(" Input CSS Selector : " + inputCssSelector);
//         System.out.println(" Input CSS Selector Data : " + inputCssSelectorData);

//         System.out.println(" Chrome Driver Setup Completed.... ");

//         WebDriverManager.chromedriver().setup();

//         // options.addArguments("--headless=new"); // runs in background
//         // options.addArguments("--no-sandbox");
//         // options.addArguments("--disable-gpu");
//         // options.addArguments("--disable-dev-shm-usage");
//         // WebDriver driver = new ChromeDriver(options);

//         // Use headless mode (important for EC2)
//         // To Run the below code in Headless mode on Linux Server (like AWS EC2), you
//         // need to set following options
//         // install the Google Chrome browser on your server
//         // command : sudo apt update
//         // command : sudo apt install -y wget unzip
//         // command : wget
//         // https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb
//         // command : sudo apt install -y ./google-chrome-stable_current_amd64.deb
//         // or
//         // Verify installation by running:
//         // command : sudo apt-get install -y google-chrome-stable
//         // install ChromeDriver compatible with your Chrome browser version on your
//         // server
//         // command : CHROME_VERSION=$(google-chrome --version | grep -oP
//         // '\d+\.\d+\.\d+')
//         // command : wget
//         // https://chromedriver.storage.googleapis.com/$CHROME_VERSION/chromedriver_linux64.zip
//         // command : unzip chromedriver_linux64.zip
//         // command : sudo mv chromedriver /usr/bin/chromedriver
//         // command : sudo chown root:root /usr/bin/chromedriver
//         // command : sudo chmod +x /usr/bin/chromedriver
//         // Now, you can run Selenium with Chrome in headless mode on your Linux server.

//         // Check if Google Chrome is installed
//         // command : google-chrome --version
//         // Check if ChromeDriver is installed
//         // command : chromedriver --version

//         System.out.println(" Setting Chrome Options.... ");
//         ChromeOptions options = new ChromeOptions();
//         System.out.println(" Setting Chrome Options - Headless.... ");
//         options.addArguments("--headless=new");
//         System.out.println(" Setting Chrome Options - No Sandbox.... ");
//         options.addArguments("--no-sandbox");
//         System.out.println(" Setting Chrome Options - Disable Dev SHM Usage.... ");
//         options.addArguments("--disable-dev-shm-usage");
//         System.out.println(" Setting Chrome Options - Disable GPU.... ");
//         options.addArguments("--disable-gpu");

//         // If using Google Chrome in your ubuntu server use this:
//         // options.setBinary("/usr/bin/google-chrome");

//         // System.out.println(" Setting Chrome Options - Window Size.... ");
//         // options.addArguments("window-size=1920,1080");
//         // System.out.println(" Setting Chrome Options - Disable Extensions.... ");
//         // options.addArguments("--disable-extensions");
//         // System.out.println(" Setting Chrome Options - Disable Images.... ");
//         // options.addArguments("blink-settings=imagesEnabled=false");

//         System.out.println(" Setting Chrome Options Completed.... ");

//         System.out.println(" Initializing Chrome Driver.... ");

//         // This for headless mode (runs in background)
//         // WebDriver driver = new ChromeDriver(options);

//         // this for non-headless mode (runs with browser UI)
//         WebDriver driver = new ChromeDriver();

//         System.out.println(" Chrome Driver Initialized.... ");
//         try {
//             driver.manage().window().maximize();
//             System.out.println(" Before Navigating to URL.... ");
//             driver.get(url);
//             System.out.println(" After Navigated to URL.... ");
//             // WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
//             // wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(inputCssSelector)));
//             // wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(inputCssSelector)));
//             // #ctl00_MainContent_txtDate1
//             System.out.println(" Locating Date Input Element.... ");

//             DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");

//             // Convert String → LocalDate
//             LocalDate startDate = LocalDate.parse(fromDate, inputFormatter);
//             LocalDate endDate = LocalDate.parse(toDate, inputFormatter);

//             DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");

//             for (LocalDate date1 = startDate; !date1.isBefore(endDate); date1 = date1.minusDays(1)) {

//                 System.out.println("==================== " + date1.format(outputFormatter) + "====================");
//                 // openWebsiteAndClickWithAnchor(url, cssSelector,inputCssSelector,
//                 date1.format(outputFormatter);

//                 driver.get(url);

//                 WebElement dateinput = driver.findElement(By.cssSelector(inputCssSelectorData));

//                 System.out.println(" Clearing Date Input Element.... ");
//                 try {
//                     dateinput.clear();
//                 } catch (Exception e) {

//                     e.printStackTrace();
//                 }
//                 System.out.println(" Sending Data to Date Input Element.... ");
//                 dateinput.sendKeys(date1.format(outputFormatter));
//                 dateinput.sendKeys(Keys.RETURN);

//                 Thread.sleep(2000);

//                 try {

//                     System.out.println("Extracting data from the second table on the page...");
//                     List<WebElement> allTables = driver.findElements(By.tagName("table"));

//                     List<Map<String, String>> jsonList = new ArrayList<>();

//                     Map<String, String> listOfAnchor = new HashMap<>();
//                     // Ensure there are at least 2 tables
//                     System.out.println("Total Tables Found: " + allTables.size());
//                     if (allTables.size() >= 2) {
//                         WebElement secondTable = allTables.get(1); // index starts from 0

//                         List<WebElement> rows = secondTable.findElements(By.tagName("tr"));
//                         List<String> headers = new ArrayList<>();
//                         System.out.println("Total Rows in Second Table: " + rows.size());
//                         for (int i = 1; i < rows.size(); i++) {
//                             WebElement row = rows.get(i);
//                             List<WebElement> cols = row.findElements(By.tagName("td"));
//                             if (i == 1) {
//                                 for (WebElement col : cols) {
//                                     headers.add(col.getText().trim());
//                                 }
//                             } else {
//                                 Map<String, String> rowData = new LinkedHashMap<>();
//                                 List<WebElement> anchors = cols.stream()
//                                         .flatMap(c -> c.findElements(By.cssSelector("a[href]")).stream()).toList();
//                                 List<String> validHrefs = anchors.stream()
//                                         .map(a -> a.getAttribute("href"))
//                                         .filter(href -> href != null && !href.trim().isEmpty())
//                                         .filter(href -> !href.equalsIgnoreCase(
//                                                 "https://khanansoft.bihar.gov.in/portal/CitizenRpt/epassreportAllDist.aspx#"))
//                                         .collect(Collectors.toList());

//                                 for (int j = 0; j < cols.size(); j++) {
//                                     WebElement col = cols.get(j);
//                                     System.out.print(col.getText() + "\t");

//                                     if (anchors.size() == 2 || anchors.size() == 3) {
//                                         if (j == 4) {

//                                             String key1 = " row = " + (i - 1) + " col = " + j;

//                                             if (!anchors.get(0).getAttribute("href").equalsIgnoreCase(
//                                                     "https://khanansoft.bihar.gov.in/portal/CitizenRpt/epassreportAllDist.aspx#")) {
//                                                 listOfAnchor.put(key1, anchors.get(0).getAttribute("href"));
//                                             }
//                                         }
//                                         if (j == 8) {
//                                             String key1 = " row = " + (i - 1) + " col = " + j;
//                                             if (!anchors.get(1).getAttribute("href").equalsIgnoreCase(
//                                                     "https://khanansoft.bihar.gov.in/portal/CitizenRpt/epassreportAllDist.aspx#")) {
//                                                 listOfAnchor.put(key1, anchors.get(1).getAttribute("href"));
//                                             }
//                                         }
//                                     }
//                                     String key = j < headers.size() ? headers.get(j) : "Column" + j;
//                                     rowData.put(key, cols.get(j).getText().trim());

//                                 }
//                                 jsonList.add(rowData);
//                             }
//                             System.out.println();
//                         }

//                     } else {
//                         System.out.println("Less than two tables found on the page.");
//                     }

//                     listOfAnchor.entrySet()
//                             .stream()
//                             .sorted(Map.Entry.comparingByKey())
//                             // .findFirst()
//                             // .ifPresent
//                             .forEach(entry -> {

//                                 System.out.println(
//                                         "Second Layer Key: " + entry.getKey() + ", Value: " + entry.getValue());
//                                 try {
//                                     driver.manage().window().maximize();
//                                     driver.get(entry.getValue());
//                                     WebElement viewAllElement = driver.findElement(By.id("ctl00_MainContent_lbtnAll"));
//                                     viewAllElement.click();
//                                     Thread.sleep(2000);
//                                     WebElement sourceTypeElement = driver
//                                             .findElement(By.id("ctl00_MainContent_ddlPassType"));

//                                     Select sourceTypeSelect = new Select(sourceTypeElement);
//                                     String sourceTypeValue = sourceTypeSelect.getFirstSelectedOption().getText();

//                                     List<WebElement> secondLayerallTables = driver.findElements(By.tagName("table"));

//                                     Map<String, String> secondLayerListOfAnchor = new HashMap<>();

//                                     System.out.println("Total Tables Found: " + secondLayerallTables.size());
//                                     if (secondLayerallTables.size() >= 2) {
//                                         WebElement secondLayersecondTable = secondLayerallTables.get(1);

//                                         List<WebElement> rows = secondLayersecondTable.findElements(By.tagName("tr"));
//                                         List<String> headers = new ArrayList<>();
//                                         System.out.println("Total Rows in Second Table: " + rows.size());

//                                         for (int i = 1; i < rows.size(); i++) {
//                                             WebElement row = rows.get(i);
//                                             List<WebElement> cols = row.findElements(By.tagName("td"));

//                                             for (int j = 0; j < cols.size(); j++) {
//                                                 WebElement col = cols.get(j);
//                                                 System.out.print(col.getText() + "\t");

//                                                 if (j == 4) {
//                                                     List<WebElement> anchorElements = col
//                                                             .findElements(By.cssSelector("a[href]"));

//                                                     if (!anchorElements.isEmpty()) {
//                                                         String href = anchorElements.get(0).getAttribute("href");

//                                                         if (!href.equalsIgnoreCase(
//                                                                 "https://khanansoft.bihar.gov.in/portal/CitizenRpt/epassreportAllDist.aspx#")) {

//                                                             String key1 = "row=" + i + " col=" + j;
//                                                             secondLayerListOfAnchor.put(key1, href);
//                                                         }
//                                                     }
//                                                 }

//                                             }

//                                             System.out.println();
//                                         }

//                                         secondLayerListOfAnchor.entrySet().stream()
//                                                 .sorted(Map.Entry.comparingByKey())
//                                                 // .findFirst()
//                                                 // .ifPresent
//                                                 .forEach(e -> {
//                                                     System.out.println(
//                                                             "Third Layer Key: " + e.getKey() + ", Value: "
//                                                                     + e.getValue());
//                                                     try {
//                                                         driver.manage().window().maximize();
//                                                         driver.get(e.getValue());
//                                                         WebElement viewAllElementOfThirdLayer = driver
//                                                                 .findElement(By.id("ctl00_MainContent_lbtnAll"));
//                                                         viewAllElementOfThirdLayer.click();
//                                                         Thread.sleep(2000);
//                                                         List<WebElement> thiredLayerallTables = driver
//                                                                 .findElements(By.tagName("table"));

//                                                         Map<String, String> thirdLayerListOfAnchor = new HashMap<>();

//                                                         System.out.println(
//                                                                 "Total Tables Found: " + thiredLayerallTables.size());
//                                                         if (thiredLayerallTables.size() >= 2) {
//                                                             WebElement thirdLayerTable = thiredLayerallTables.get(1); // index

//                                                             List<WebElement> rowsthird = thirdLayerTable
//                                                                     .findElements(By.tagName("tr"));
//                                                             List<String> headersThird = new ArrayList<>();
//                                                             System.out
//                                                                     .println("Total Rows in Third Layer Table: "
//                                                                             + rowsthird.size());

//                                                             for (int i = 1; i < rowsthird.size(); i++) {
//                                                                 WebElement row = rowsthird.get(i);
//                                                                 List<WebElement> cols = row
//                                                                         .findElements(By.tagName("td"));

//                                                                 for (int j = 0; j < cols.size(); j++) {
//                                                                     WebElement col = cols.get(j);
//                                                                     System.out.print(col.getText() + "\t");

//                                                                     if (j == 4) {
//                                                                         List<WebElement> anchorElements = col
//                                                                                 .findElements(
//                                                                                         By.cssSelector("a[href]"));

//                                                                         if (!anchorElements.isEmpty()) {
//                                                                             String href = anchorElements.get(0)
//                                                                                     .getAttribute("href");

//                                                                             if (!href.equalsIgnoreCase(
//                                                                                     "https://khanansoft.bihar.gov.in/portal/CitizenRpt/epassreportAllDist.aspx#")) {

//                                                                                 String key1 = "row=" + i + " col=" + j;
//                                                                                 thirdLayerListOfAnchor.put(key1, href);
//                                                                             }
//                                                                         }
//                                                                     }

//                                                                 }

//                                                                 System.out.println();
//                                                             }
//                                                             // Forth Layer Work
//                                                             thirdLayerListOfAnchor.entrySet().stream()
//                                                                     .sorted(Map.Entry.comparingByKey()) // sort by key
//                                                                     // .findFirst()
//                                                                     // .ifPresent
//                                                                     .forEach(ent -> {
//                                                                         System.out
//                                                                                 .println(
//                                                                                         "------------------------------");
//                                                                         System.out
//                                                                                 .println("Forth Layer Key: "
//                                                                                         + ent.getKey()
//                                                                                         + ", Value: " + ent.getValue());
//                                                                         System.out
//                                                                                 .println(
//                                                                                         "------------------------------");
//                                                                         try {

//                                                                             driver.get(ent.getValue());
//                                                                             WebElement viewAllElementForth = driver
//                                                                                     .findElement(By.id(
//                                                                                             "ctl00_MainContent_lbtnAll"));

//                                                                             viewAllElementForth.click();
//                                                                             Thread.sleep(2000);

//                                                                             WebElement district = driver.findElement(By
//                                                                                     .id(
//                                                                                             "ctl00_MainContent_ddlDMO"));
//                                                                             WebElement consigner = driver.findElement(By
//                                                                                     .id(
//                                                                                             "ctl00_MainContent_ddlConsigner"));
//                                                                             WebElement date = driver.findElement(By
//                                                                                     .id(
//                                                                                             "ctl00_MainContent_txtDate"));

//                                                                             Select districtSelect = new Select(
//                                                                                     district);
//                                                                             String districtValue = districtSelect
//                                                                                     .getFirstSelectedOption().getText();

//                                                                             Select consignerSelect = new Select(
//                                                                                     consigner);
//                                                                             String consignerValue = consignerSelect
//                                                                                     .getFirstSelectedOption().getText();

//                                                                             String dateValue = date
//                                                                                     .getAttribute("value");
//                                                                             String sourceType = sourceTypeValue;

//                                                                             System.out.println(districtValue);
//                                                                             System.out.println(consignerValue);
//                                                                             System.out.println(dateValue);
//                                                                             System.out.println(sourceType);

//                                                                             List<WebElement> forthLayerallTables = driver
//                                                                                     .findElements(By.tagName("table"));

//                                                                             Map<String, String> forthLayerListOfAnchor = new HashMap<>();
//                                                                             System.out.println("Total Tables Found: "
//                                                                                     + forthLayerallTables.size());
//                                                                             List<KhananData> khananDataList = new ArrayList<>();
//                                                                             if (forthLayerallTables.size() >= 2) {
//                                                                                 WebElement forthLayersecondTable = forthLayerallTables
//                                                                                         .get(1);

//                                                                                 List<WebElement> rowsForth = forthLayersecondTable
//                                                                                         .findElements(By.tagName("tr"));
//                                                                                 List<String> headersForth = new ArrayList<>();
//                                                                                 System.out.println(
//                                                                                         "Total Rows in Second Table: "
//                                                                                                 + rowsForth.size());
//                                                                                 for (int i = 1; i < rowsForth.size()
//                                                                                         - 1; i++) {
//                                                                                     WebElement row = rowsForth.get(i);
//                                                                                     List<WebElement> cols = row
//                                                                                             .findElements(
//                                                                                                     By.tagName("td"));
//                                                                                     KhananData khananData = new KhananData(
//                                                                                             districtValue,
//                                                                                             consignerValue,
//                                                                                             dateValue,
//                                                                                             sourceType,
//                                                                                             cols.get(1).getText()
//                                                                                                     .trim(),
//                                                                                             cols.get(2).getText()
//                                                                                                     .trim(),
//                                                                                             cols.get(3).getText()
//                                                                                                     .trim(),
//                                                                                             cols.get(4).getText()
//                                                                                                     .trim(),
//                                                                                             cols.get(5).getText()
//                                                                                                     .trim(),
//                                                                                             cols.get(6).getText()
//                                                                                                     .trim(),
//                                                                                             cols.get(7).getText()
//                                                                                                     .trim(),
//                                                                                             cols.get(8).getText()
//                                                                                                     .trim(),
//                                                                                             cols.get(9).getText()
//                                                                                                     .trim(),
//                                                                                             cols.get(10).getText()
//                                                                                                     .trim());
//                                                                                     khananDataList.add(khananData);
//                                                                                     for (int j = 0; j < cols
//                                                                                             .size(); j++) {
//                                                                                         WebElement col = cols.get(j);
//                                                                                         System.out.print(
//                                                                                                 col.getText() + "\t");

//                                                                                     }

//                                                                                     System.out.println();
//                                                                                 }
//                                                                                 khananDataService
//                                                                                         .saveAllKhananData(
//                                                                                                 khananDataList);
//                                                                                 System.out.println(
//                                                                                         "Data Saved Successfully.");

//                                                                             } else {
//                                                                                 System.out.println(
//                                                                                         "Less than two tables found on the page.");
//                                                                             }

//                                                                             // });
//                                                                         } catch (Exception exc) {
//                                                                             exc.printStackTrace();
//                                                                         }

//                                                                     });
//                                                         } else {
//                                                             System.out
//                                                                     .println("Less than two tables found on the page.");
//                                                         }

//                                                         // });
//                                                     } catch (Exception ex) {
//                                                         ex.printStackTrace();
//                                                     }
//                                                 });
//                                     } else {
//                                         System.out.println("Less than two tables found on the page.");
//                                     }

//                                     // });
//                                 } catch (Exception e) {
//                                     e.printStackTrace();
//                                 }

//                             });

//                     return jsonList;
//                 } catch (Exception e) {
//                     e.printStackTrace();
//                     return null;
//                 } finally {
//                     // driver.quit();
//                 }
//             }

//         } catch (Exception e) {
//             e.printStackTrace();
//             // return "Error: " + e.getMessage();
//             return null;
//         } finally {
//             // driver.quit();

//         }
//         return null;
//     }

//     public String getSecondLayerData() {
//         WebDriverManager.chromedriver().setup();

//         System.out.println(" Setting Chrome Options.... ");
//         ChromeOptions options = new ChromeOptions();
//         System.out.println(" Setting Chrome Options - Headless.... ");
//         options.addArguments("--headless=new");
//         System.out.println(" Setting Chrome Options - No Sandbox.... ");
//         options.addArguments("--no-sandbox");
//         System.out.println(" Setting Chrome Options - Disable Dev SHM Usage.... ");
//         options.addArguments("--disable-dev-shm-usage");
//         System.out.println(" Setting Chrome Options - Disable GPU.... ");
//         options.addArguments("--disable-gpu");

//         // This for headless mode (runs in background)
//         // WebDriver driver = new ChromeDriver(options);

//         // this for non-headless mode (runs with browser UI)
//         WebDriver driver = new ChromeDriver();
//         // listOfAnchor.entrySet()
//         // .stream()
//         // .sorted(Map.Entry.comparingByKey())
//         // .findFirst()
//         // .ifPresent(entry -> {

//         // System.out.println(entry.getKey() + " : " + entry.getValue());
//         try {
//             driver.manage().window().maximize();
//             driver.get(
//                     "https://khanansoft.bihar.gov.in/portal/CitizenRpt/ePassReportAllConsigner.aspx?ASHxNlmoFyKWVTw3JRIqNxcbqoTn8ifRT+5+Fljj+RIW2b6TUpdEC3Ri/5qzPs8e8hYZcCCpckeZiacSTWEkBUwOIYewYlXpnpULwNe+ZheGUBijC+XPRUr6yH2w2+1ZkrCabjgDzhg=");
//             WebElement viewAllElement = driver.findElement(By.id("ctl00_MainContent_lbtnAll"));
//             viewAllElement.click();
//             Thread.sleep(4000);
//             List<WebElement> secondLayerallTables = driver.findElements(By.tagName("table"));

//             // List<Map<String, String>> secondLayerJsonList = new ArrayList<>();
//             // List<TableRowData> result = new ArrayList<>();
//             Map<String, String> secondLayerListOfAnchor = new HashMap<>();

//             // Ensure there are at least 2 tables
//             System.out.println("Total Tables Found: " + secondLayerallTables.size());
//             if (secondLayerallTables.size() >= 2) {
//                 WebElement secondLayersecondTable = secondLayerallTables.get(1); // index starts from 0

//                 // Find all rows in the second table

//                 List<WebElement> rows = secondLayersecondTable.findElements(By.tagName("tr"));
//                 List<String> headers = new ArrayList<>();
//                 System.out.println("Total Rows in Second Table: " + rows.size());

//                 // ✅ Loop through rows and extract each cell
//                 for (int i = 1; i < rows.size(); i++) {
//                     WebElement row = rows.get(i);
//                     List<WebElement> cols = row.findElements(By.tagName("td"));
//                     // if (i == 1) {
//                     // for (WebElement col : cols) {
//                     // headers.add(col.getText().trim());
//                     // }
//                     // } else {
//                     // Map<String, String> rowData = new LinkedHashMap<>();
//                     // List<WebElement> anchors = cols.stream()
//                     // .flatMap(c -> c.findElements(By.cssSelector("a[href]")).stream()) // correct
//                     // CSS
//                     // // selector
//                     // .toList();
//                     for (int j = 0; j < cols.size(); j++) {
//                         WebElement col = cols.get(j);
//                         System.out.print(col.getText() + "\t");
//                         // if (j == 4) {
//                         // String key1 = " row = " + (i ) + " col = " + j;
//                         // if (!anchors.get(0).getAttribute("href").equalsIgnoreCase(
//                         // "https://khanansoft.bihar.gov.in/portal/CitizenRpt/epassreportAllDist.aspx#"))
//                         // {
//                         // secondLayerListOfAnchor.put(key1, anchors.get(0).getAttribute("href"));
//                         // }
//                         // // String link =
//                         // cols.get(j).findElement(By.cssSelector("a[href]")).getAttribute("href");
//                         // // System.out.println(" Link : " + link);
//                         // }

//                         if (j == 4) {
//                             List<WebElement> anchorElements = col.findElements(By.cssSelector("a[href]"));

//                             if (!anchorElements.isEmpty()) {
//                                 String href = anchorElements.get(0).getAttribute("href");

//                                 if (!href.equalsIgnoreCase(
//                                         "https://khanansoft.bihar.gov.in/portal/CitizenRpt/epassreportAllDist.aspx#")) {

//                                     String key1 = "row=" + i + " col=" + j;
//                                     secondLayerListOfAnchor.put(key1, href);
//                                 }
//                             }
//                         }

//                         // String key = j < headers.size() ? headers.get(j) : "Column" + j;
//                         // rowData.put(key, cols.get(j).getText().trim());

//                     }
//                     // secondLayerJsonList.add(rowData);
//                     // }
//                     System.out.println();
//                 }
//                 secondLayerListOfAnchor.entrySet().stream()
//                         .sorted(Map.Entry.comparingByKey()) // sort by key
//                         .forEach(entry -> System.out
//                                 .println("Key: " + entry.getKey() + ", Value: " + entry.getValue()));
//             } else {
//                 System.out.println("Less than two tables found on the page.");
//             }

//             // });
//         } catch (Exception e) {
//             e.printStackTrace();
//         }
//         return "Hello from second layer data!";
//     }

//     public String getThirdLayerData() {
//         WebDriverManager.chromedriver().setup();

//         System.out.println(" Setting Chrome Options.... ");
//         ChromeOptions options = new ChromeOptions();
//         System.out.println(" Setting Chrome Options - Headless.... ");
//         options.addArguments("--headless=new");
//         System.out.println(" Setting Chrome Options - No Sandbox.... ");
//         options.addArguments("--no-sandbox");
//         System.out.println(" Setting Chrome Options - Disable Dev SHM Usage.... ");
//         options.addArguments("--disable-dev-shm-usage");
//         System.out.println(" Setting Chrome Options - Disable GPU.... ");
//         options.addArguments("--disable-gpu");

//         // This for headless mode (runs in background)
//         // WebDriver driver = new ChromeDriver(options);

//         // this for non-headless mode (runs with browser UI)
//         WebDriver driver = new ChromeDriver();
//         // listOfAnchor.entrySet()
//         // .stream()
//         // .sorted(Map.Entry.comparingByKey())
//         // .findFirst()
//         // .ifPresent(entry -> {

//         // System.out.println(entry.getKey() + " : " + entry.getValue());
//         try {
//             driver.manage().window().maximize();
//             driver.get(
//                     "https://khanansoft.bihar.gov.in/portal/CitizenRpt/ePassRptByConsigner.aspx?ASHxNlmoFyKWVTw3JRIqN0OYDRqYeiSRVE5mv0Ad+jmKwd7ghMUE8Q6Lpj70hq/xUORwZiTS3RyBA8iuNxpnC6oIVEGL/6q//fhbKMCV9a6urMznWnMFF8ObySfdL2phbW/NH+VBuR0=");
//             WebElement viewAllElement = driver.findElement(By.id("ctl00_MainContent_lbtnAll"));
//             viewAllElement.click();
//             Thread.sleep(4000);
//             List<WebElement> secondLayerallTables = driver.findElements(By.tagName("table"));

//             // List<Map<String, String>> secondLayerJsonList = new ArrayList<>();
//             // List<TableRowData> result = new ArrayList<>();
//             Map<String, String> secondLayerListOfAnchor = new HashMap<>();

//             // Ensure there are at least 2 tables
//             System.out.println("Total Tables Found: " + secondLayerallTables.size());
//             if (secondLayerallTables.size() >= 2) {
//                 WebElement secondLayersecondTable = secondLayerallTables.get(1); // index starts from 0

//                 // Find all rows in the second table

//                 List<WebElement> rows = secondLayersecondTable.findElements(By.tagName("tr"));
//                 List<String> headers = new ArrayList<>();
//                 System.out.println("Total Rows in Second Table: " + rows.size());

//                 // ✅ Loop through rows and extract each cell
//                 for (int i = 1; i < rows.size(); i++) {
//                     WebElement row = rows.get(i);
//                     List<WebElement> cols = row.findElements(By.tagName("td"));
//                     // if (i == 1) {
//                     // for (WebElement col : cols) {
//                     // headers.add(col.getText().trim());
//                     // }
//                     // } else {
//                     // Map<String, String> rowData = new LinkedHashMap<>();
//                     // List<WebElement> anchors = cols.stream()
//                     // .flatMap(c -> c.findElements(By.cssSelector("a[href]")).stream()) // correct
//                     // CSS
//                     // // selector
//                     // .toList();
//                     for (int j = 0; j < cols.size(); j++) {
//                         WebElement col = cols.get(j);
//                         System.out.print(col.getText() + "\t");
//                         // if (j == 4) {
//                         // String key1 = " row = " + (i ) + " col = " + j;
//                         // if (!anchors.get(0).getAttribute("href").equalsIgnoreCase(
//                         // "https://khanansoft.bihar.gov.in/portal/CitizenRpt/epassreportAllDist.aspx#"))
//                         // {
//                         // secondLayerListOfAnchor.put(key1, anchors.get(0).getAttribute("href"));
//                         // }
//                         // // String link =
//                         // cols.get(j).findElement(By.cssSelector("a[href]")).getAttribute("href");
//                         // // System.out.println(" Link : " + link);
//                         // }

//                         if (j == 4) {
//                             List<WebElement> anchorElements = col.findElements(By.cssSelector("a[href]"));

//                             if (!anchorElements.isEmpty()) {
//                                 String href = anchorElements.get(0).getAttribute("href");

//                                 if (!href.equalsIgnoreCase(
//                                         "https://khanansoft.bihar.gov.in/portal/CitizenRpt/epassreportAllDist.aspx#")) {

//                                     String key1 = "row=" + i + " col=" + j;
//                                     secondLayerListOfAnchor.put(key1, href);
//                                 }
//                             }
//                         }

//                         // String key = j < headers.size() ? headers.get(j) : "Column" + j;
//                         // rowData.put(key, cols.get(j).getText().trim());

//                     }
//                     // secondLayerJsonList.add(rowData);
//                     // }
//                     System.out.println();
//                 }
//                 secondLayerListOfAnchor.entrySet().stream()
//                         .sorted(Map.Entry.comparingByKey()) // sort by key
//                         .forEach(entry -> System.out
//                                 .println("Key: " + entry.getKey() + ", Value: " + entry.getValue()));
//             } else {
//                 System.out.println("Less than two tables found on the page.");
//             }

//             // });
//         } catch (Exception e) {
//             e.printStackTrace();
//         }
//         return "Hello from second layer data!";
//     }

//     public String getForthLayerData(String url) {
//         WebDriverManager.chromedriver().setup();

//         System.out.println(" Setting Chrome Options.... ");
//         ChromeOptions options = new ChromeOptions();
//         System.out.println(" Setting Chrome Options - Headless.... ");
//         options.addArguments("--headless=new");
//         System.out.println(" Setting Chrome Options - No Sandbox.... ");
//         options.addArguments("--no-sandbox");
//         System.out.println(" Setting Chrome Options - Disable Dev SHM Usage.... ");
//         options.addArguments("--disable-dev-shm-usage");
//         System.out.println(" Setting Chrome Options - Disable GPU.... ");
//         options.addArguments("--disable-gpu");

//         // This for headless mode (runs in background)
//         // WebDriver driver = new ChromeDriver(options);

//         // this for non-headless mode (runs with browser UI)
//         WebDriver driver = new ChromeDriver();
//         // listOfAnchor.entrySet()
//         // .stream()
//         // .sorted(Map.Entry.comparingByKey())
//         // .findFirst()
//         // .ifPresent(entry -> {

//         // System.out.println(entry.getKey() + " : " + entry.getValue());
//         try {
//             driver.manage().window().maximize();
//             driver.get(url);
//             // driver.get(
//             // "https://khanansoft.bihar.gov.in/portal/CitizenRpt/ePassRptByConsigner.aspx?ASHxNlmoFyKWVTw3JRIqN0OYDRqYeiSRVE5mv0Ad+jmKwd7ghMUE8Q6Lpj70hq/xUORwZiTS3RyBA8iuNxpnC6oIVEGL/6q//fhbKMCV9a6urMznWnMFF8ObySfdL2phbW/NH+VBuR0=");
//             WebElement viewAllElement = driver.findElement(By.id("ctl00_MainContent_lbtnAll"));
//             viewAllElement.click();
//             Thread.sleep(4000);
//             List<WebElement> secondLayerallTables = driver.findElements(By.tagName("table"));

//             // List<Map<String, String>> secondLayerJsonList = new ArrayList<>();
//             // List<TableRowData> result = new ArrayList<>();
//             Map<String, String> secondLayerListOfAnchor = new HashMap<>();

//             // Ensure there are at least 2 tables
//             System.out.println("Total Tables Found: " + secondLayerallTables.size());
//             if (secondLayerallTables.size() >= 2) {
//                 WebElement secondLayersecondTable = secondLayerallTables.get(1); // index starts from 0

//                 // Find all rows in the second table

//                 List<WebElement> rows = secondLayersecondTable.findElements(By.tagName("tr"));
//                 List<String> headers = new ArrayList<>();
//                 System.out.println("Total Rows in Second Table: " + rows.size());

//                 // ✅ Loop through rows and extract each cell
//                 for (int i = 1; i < rows.size(); i++) {
//                     WebElement row = rows.get(i);
//                     List<WebElement> cols = row.findElements(By.tagName("td"));
//                     // if (i == 1) {
//                     // for (WebElement col : cols) {
//                     // headers.add(col.getText().trim());
//                     // }
//                     // } else {
//                     // Map<String, String> rowData = new LinkedHashMap<>();
//                     // List<WebElement> anchors = cols.stream()
//                     // .flatMap(c -> c.findElements(By.cssSelector("a[href]")).stream()) // correct
//                     // CSS
//                     // // selector
//                     // .toList();
//                     for (int j = 0; j < cols.size(); j++) {
//                         WebElement col = cols.get(j);
//                         System.out.print(col.getText() + "\t");
//                         // if (j == 4) {
//                         // String key1 = " row = " + (i ) + " col = " + j;
//                         // if (!anchors.get(0).getAttribute("href").equalsIgnoreCase(
//                         // "https://khanansoft.bihar.gov.in/portal/CitizenRpt/epassreportAllDist.aspx#"))
//                         // {
//                         // secondLayerListOfAnchor.put(key1, anchors.get(0).getAttribute("href"));
//                         // }
//                         // // String link =
//                         // cols.get(j).findElement(By.cssSelector("a[href]")).getAttribute("href");
//                         // // System.out.println(" Link : " + link);
//                         // }

//                         // if (j == 4) {
//                         // List<WebElement> anchorElements =
//                         // col.findElements(By.cssSelector("a[href]"));

//                         // if (!anchorElements.isEmpty()) {
//                         // String href = anchorElements.get(0).getAttribute("href");

//                         // if (!href.equalsIgnoreCase(
//                         // "https://khanansoft.bihar.gov.in/portal/CitizenRpt/epassreportAllDist.aspx#"))
//                         // {

//                         // String key1 = "row=" + i + " col=" + j;
//                         // secondLayerListOfAnchor.put(key1, href);
//                         // }
//                         // }
//                         // }

//                         // String key = j < headers.size() ? headers.get(j) : "Column" + j;
//                         // rowData.put(key, cols.get(j).getText().trim());

//                     }
//                     // secondLayerJsonList.add(rowData);
//                     // }
//                     System.out.println();
//                 }
//                 // secondLayerListOfAnchor.entrySet().stream()
//                 // .sorted(Map.Entry.comparingByKey()) // sort by key
//                 // .forEach(entry -> System.out
//                 // .println("Key: " + entry.getKey() + ", Value: " + entry.getValue()));
//             } else {
//                 System.out.println("Less than two tables found on the page.");
//             }

//             // });
//         } catch (Exception e) {
//             e.printStackTrace();
//         }
//         return "Hello from second layer data!";
//     }

//     public List<Map<String, String>> fetchAndParseTableWithSelenium(String url, String cssSelector,
//             String inputCssSelector, String inputCssSelectorData) throws InterruptedException {
//         // Setup Chrome in headless mode (works on server too)
//         // ChromeOptions options = new ChromeOptions();
//         // options.addArguments("--headless");
//         // options.addArguments("--no-sandbox");
//         // options.addArguments("--disable-dev-shm-usage");
//         // options.addArguments("--disable-gpu");

//         // WebDriver driver = new ChromeDriver(options);

//         // String url =
//         // "https://khanansoft.bihar.gov.in/portal/CitizenRpt/epassreportAllDist.aspx";
//         // driver.get(url);
//         // driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

//         // // Wait briefly to ensure tables load
//         // Thread.sleep(3000);

//         System.out.println(" Selenium Test Service Called.... ");
//         System.out.println(" URL : " + url);
//         System.out.println(" CSS Selector : " + cssSelector);
//         System.out.println(" Input CSS Selector : " + inputCssSelector);
//         System.out.println(" Input CSS Selector Data : " + inputCssSelectorData);

//         System.out.println(" Chrome Driver Setup Completed.... ");
//         try {
//             System.out.println("🧹 Cleaning up old Chrome/Driver sessions...");

//             // Kill existing Chrome/ChromeDriver processes
//             Runtime.getRuntime().exec("pkill -f chromedriver");
//             Runtime.getRuntime().exec("pkill -f chrome");

//             // Wait for cleanup to complete (1 sec)
//             Thread.sleep(1000);
//         } catch (Exception e) {
//             System.out.println("Cleanup failed: " + e.getMessage());
//         }
//         WebDriverManager.chromedriver().setup();

//         // options.addArguments("--headless=new"); // runs in background
//         // options.addArguments("--no-sandbox");
//         // options.addArguments("--disable-gpu");
//         // options.addArguments("--disable-dev-shm-usage");
//         // WebDriver driver = new ChromeDriver(options);

//         // Use headless mode (important for EC2)
//         // To Run the below code in Headless mode on Linux Server (like AWS EC2), you
//         // need to set following options
//         // install the Google Chrome browser on your server
//         // command : sudo apt update
//         // command : sudo apt install -y wget unzip
//         // command : wget
//         // https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb
//         // command : sudo apt install -y ./google-chrome-stable_current_amd64.deb
//         // or
//         // Verify installation by running:
//         // command : sudo apt-get install -y google-chrome-stable
//         // install ChromeDriver compatible with your Chrome browser version on your
//         // server
//         // command : CHROME_VERSION=$(google-chrome --version | grep -oP
//         // '\d+\.\d+\.\d+')
//         // command : wget
//         // https://chromedriver.storage.googleapis.com/$CHROME_VERSION/chromedriver_linux64.zip
//         // command : unzip chromedriver_linux64.zip
//         // command : sudo mv chromedriver /usr/bin/chromedriver
//         // command : sudo chown root:root /usr/bin/chromedriver
//         // command : sudo chmod +x /usr/bin/chromedriver
//         // Now, you can run Selenium with Chrome in headless mode on your Linux server.

//         // Check if Google Chrome is installed
//         // command : google-chrome --version
//         // Check if ChromeDriver is installed
//         // command : chromedriver --version

//         // System.out.println(" Setting Chrome Options.... ");
//         // ChromeOptions options = new ChromeOptions();
//         // System.out.println(" Setting Chrome Options - Headless.... ");
//         // options.addArguments("--headless=new");
//         // System.out.println(" Setting Chrome Options - No Sandbox.... ");
//         // options.addArguments("--no-sandbox");
//         // System.out.println(" Setting Chrome Options - Disable Dev SHM Usage.... ");
//         // options.addArguments("--disable-dev-shm-usage");
//         // System.out.println(" Setting Chrome Options - Disable GPU.... ");
//         // options.addArguments("--disable-gpu");
//         //
//         // // If using Google Chrome in your ubuntu server use this:
//         // options.setBinary("/usr/bin/google-chrome");
//         //
//         // // System.out.println(" Setting Chrome Options - Window Size.... ");
//         // // options.addArguments("window-size=1920,1080");
//         // System.out.println(" Setting Chrome Options - Disable Extensions.... ");
//         // options.addArguments("--disable-extensions");
//         // System.out.println(" Setting Chrome Options - Disable Images.... ");
//         // options.addArguments("blink-settings=imagesEnabled=false");

//         System.out.println(" Setting Chrome Options Completed.... ");

//         System.out.println(" Initializing Chrome Driver.... ");

//         // This for headless mode (runs in background)
//         // WebDriver driver = new ChromeDriver(options);

//         // this for non-headless mode (runs with browser UI)
//         WebDriver driver = new ChromeDriver();
//         System.out.println(" Chrome Driver Initialized.... ");

//         driver.manage().window().maximize();

//         System.out.println(" Before Navigating to URL.... ");
//         driver.get(url);

//         System.out.println(" After Navigated to URL.... ");
//         WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

//         System.out.println(" Locating Date Input Element.... ");
//         WebElement dateinput = driver.findElement(By.cssSelector(inputCssSelector));

//         System.out.println(" Clearing Date Input Element.... ");
//         dateinput.clear();

//         System.out.println(" Sending Data to Date Input Element.... ");
//         dateinput.sendKeys(inputCssSelectorData);
//         dateinput.sendKeys(Keys.RETURN);
//         List<Map<String, String>> jsonList = new ArrayList<>();
//         Map<String, String> listOfAnchor = new HashMap<>();
//         try {
//             // System.out.println(" Submitting Date Input Element.... ");
//             // WebElement clickOnShowButton =
//             // wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(cssSelector)));

//             // System.out.println(" Clicking on Show Button.... ");
//             // clickOnShowButton.click();

//             // Get all tables on the page
//             // WebDriverWait waitForTable = new WebDriverWait(driver,
//             // Duration.ofSeconds(30));

//             List<WebElement> tables =
//                     // waitForTable.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.tagName("table"),
//                     // 2));
//                     driver.findElements(By.tagName("table"));
//             System.out.println("Total tables found: " + tables.size());

//             if (tables.size() == 3) {
//                 WebElement table = tables.get(1); // pick the 2nd table
//                 // Get rows
//                 List<WebElement> rows = table.findElements(By.tagName("tr"));
//                 List<String> headers = new ArrayList<>();

//                 // try {
//                 for (int i = 0; i < rows.size(); i++) {
//                     List<WebElement> cols = rows.get(i).findElements(By.xpath("./th|./td"));

//                     // header row
//                     if (i == 0) {
//                         for (WebElement col : cols) {

//                             String input = col.getText().trim();
//                             headers.add(input);

//                         }
//                     } else {
//                         Map<String, String> rowData = new LinkedHashMap<>();
//                         // Find all anchor tags within the current row
//                         List<WebElement> anchors = cols.stream()
//                                 .flatMap(c -> c.findElements(By.cssSelector("a[href]")).stream()) // correct CSS
//                                                                                                   // selector
//                                 .toList();
//                         // List<WebElement> anchors = driver.findElements(By.tagName("a"));
//                         List<String> validHrefs = anchors.stream()
//                                 .map(a -> a.getAttribute("href"))
//                                 .filter(href -> href != null && !href.trim().isEmpty())
//                                 .filter(href -> !href.equalsIgnoreCase(
//                                         "https://khanansoft.bihar.gov.in/portal/CitizenRpt/epassreportAllDist.aspx#"))
//                                 .collect(Collectors.toList());
//                         for (int k = 0; k < validHrefs.size(); k++) {
//                             System.out.println("Index: " + k + " → Valid Href: " + validHrefs.get(k));
//                         }

//                         System.out.println("Anchor Size" + anchors.size());

//                         for (int j = 0; j < cols.size(); j++) {
//                             String key = j < headers.size() ? headers.get(j) : "Column" + j;
//                             String value = cols.get(j).getText().trim();
//                             rowData.put(key, value);

//                             // Capture href links conditionally (like your JSoup code)
//                             if (anchors.size() == 2) {
//                                 if (j == 4) {
//                                     System.out.println("Anchor " + i);
//                                     String key1 = " row = " + i + "col = " + j;
//                                     // for(int k = 0; k < 1;k++) {
//                                     // //rows.get(i).findElements(By.xpath("./td"));
//                                     // key1 = cols.get(j).getText().trim()+ i + " " + j;
//                                     // }
//                                     // WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(220));
//                                     // WebElement thirdAnchor = anchors.get(0);
//                                     //
//                                     // wait.until(ExpectedConditions.elementToBeClickable(thirdAnchor)).click();
//                                     //
//                                     // WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofMinutes(5));
//                                     // Thread.sleep(300000);

//                                     listOfAnchor.put(key1, anchors.get(0).getAttribute("href"));
//                                 }
//                                 if (j == 8) {
//                                     System.out.println("Anchor " + i);
//                                     String key1 = " row = " + i + "col = " + j;
//                                     // anchors.get(1).click();
//                                     listOfAnchor.put(key1, anchors.get(1).getAttribute("href"));
//                                 }
//                             }
//                         }
//                         jsonList.add(rowData);
//                     }
//                 }

//                 // ✅ Print anchor links (sorted by key)
//                 listOfAnchor.entrySet().stream().sorted(Map.Entry.comparingByKey())
//                         .forEach(
//                                 entry -> System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue()));

//                 driver.quit();
//             } else {
//                 System.out.println("Under Else");
//                 System.out.println("Before : tables found: ");
//                 WebElement table = tables.get(1); // pick the 2nd table
//                 System.out.println("After : tables found: ");
//                 // Get rows
//                 System.out.println("Before : rows with tr tag found: ");
//                 List<WebElement> rows = table.findElements(By.tagName("tr"));
//                 System.out.println("After : rows with tr tag found: ");
//                 List<String> headers = new ArrayList<>();

//                 System.out.println("Before : for loop found: ");
//                 for (int i = 0; i < rows.size(); i++) {
//                     System.out.println("In for loop : rows size: " + rows.size());
//                     System.out.println("Before : cols found: ");
//                     List<WebElement> cols = rows.get(i).findElements(By.xpath("./th|./td"));
//                     System.out.println("After : cols found: ");

//                     // header row

//                     System.out.println("Before If : i value: ");
//                     if (i == 0) {
//                         System.out.println("Under called Header if : i value: " + i);
//                         for (WebElement col : cols) {

//                             String input = col.getText().trim();
//                             headers.add(input);

//                         }
//                     } else {
//                         Map<String, String> rowData = new LinkedHashMap<>();
//                         // Find all anchor tags within the current row
//                         System.out.println("Before : anchors found: ");
//                         List<WebElement> anchors = cols.stream()
//                                 .flatMap(c -> c.findElements(By.cssSelector("a[href]")).stream()) // correct CSS
//                                                                                                   // selector
//                                 .toList();
//                         // List<WebElement> anchors = driver.findElements(By.tagName("a"));
//                         System.out.println("After : anchors found: ");
//                         System.out.println("Before : validHrefs found: ");
//                         List<String> validHrefs = anchors.stream()
//                                 .map(a -> a.getAttribute("href"))
//                                 .filter(href -> href != null && !href.trim().isEmpty())
//                                 .filter(href -> !href.equalsIgnoreCase(
//                                         "https://khanansoft.bihar.gov.in/portal/CitizenRpt/epassreportAllDist.aspx#"))
//                                 .collect(Collectors.toList());
//                         System.out.println("After : validHrefs found: ");
//                         // for (int k = 0; k < validHrefs.size(); k++) {
//                         // System.out.println("Index: " + k + " → Valid Href: " + validHrefs.get(k));
//                         // }

//                         System.out.println("Anchor Size" + anchors.size());

//                         for (int j = 0; j < validHrefs.size(); j++) {
//                             String key = j < headers.size() ? headers.get(j) : "Column" + j;
//                             String value = cols.get(j).getText().trim();
//                             rowData.put(key, value);

//                             // Capture href links conditionally (like your JSoup code)
//                             if (anchors.size() == 3) {
//                                 if (j == 4) {
//                                     System.out.println("Anchor " + i);
//                                     String key1 = " row = " + i + "col = " + j;
//                                     // for(int k = 0; k < 1;k++) {
//                                     // //rows.get(i).findElements(By.xpath("./td"));
//                                     // key1 = cols.get(j).getText().trim()+ i + " " + j;
//                                     // }
//                                     // WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(220));
//                                     // WebElement thirdAnchor = anchors.get(0);
//                                     //
//                                     // wait.until(ExpectedConditions.elementToBeClickable(thirdAnchor)).click();
//                                     //
//                                     // WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofMinutes(5));
//                                     // Thread.sleep(300000);

//                                     listOfAnchor.put(key1, anchors.get(0).getAttribute("href"));
//                                 }
//                                 if (j == 8) {
//                                     System.out.println("Anchor " + i);
//                                     String key1 = " row = " + i + "col = " + j;
//                                     // anchors.get(1).click();
//                                     listOfAnchor.put(key1, anchors.get(1).getAttribute("href"));
//                                 }
//                             }
//                             if (anchors.size() == 2) {
//                                 if (j == 4) {
//                                     System.out.println("Anchor " + i);
//                                     String key1 = " row = " + i + "col = " + j;
//                                     // for(int k = 0; k < 1;k++) {
//                                     // //rows.get(i).findElements(By.xpath("./td"));
//                                     // key1 = cols.get(j).getText().trim()+ i + " " + j;
//                                     // }
//                                     // WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(220));
//                                     // WebElement thirdAnchor = anchors.get(0);
//                                     //
//                                     // wait.until(ExpectedConditions.elementToBeClickable(thirdAnchor)).click();
//                                     //
//                                     // WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofMinutes(5));
//                                     // Thread.sleep(300000);

//                                     listOfAnchor.put(key1, anchors.get(0).getAttribute("href"));
//                                 }
//                                 if (j == 8) {
//                                     System.out.println("Anchor " + i);
//                                     String key1 = " row = " + i + "col = " + j;
//                                     // anchors.get(1).click();
//                                     listOfAnchor.put(key1, anchors.get(1).getAttribute("href"));
//                                 }
//                             }
//                         }
//                         jsonList.add(rowData);
//                     }
//                 }

//                 // ✅ Print anchor links (sorted by key)
//                 listOfAnchor.entrySet().stream().sorted(Map.Entry.comparingByKey())
//                         .forEach(
//                                 entry -> System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue()));
//                 driver.quit();
//                 return jsonList;

//                 // driver.quit();

//                 // return null;
//             }
//         } catch (StaleElementReferenceException e) {
//             e.printStackTrace();
//             System.out.println("StaleElementReferenceException encountered. Retrying...");
//             driver.quit();
//         }
//         driver.quit();
//         return jsonList;
//     }

//     public List<KhananData> getKhananDataByDateRange(String url, String selector, String inputCssSelector,
//             String fromDate, String toDate) {

//         DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");

//         // Convert String → LocalDate
//         LocalDate startDate = LocalDate.parse(fromDate, inputFormatter);
//         LocalDate endDate = LocalDate.parse(toDate, inputFormatter);

//         DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");

//         for (LocalDate date = startDate; !date.isBefore(endDate); date = date.minusDays(1)) {

//             System.out.println("==================== " + date.format(outputFormatter) + " ====================");
//             openWebsiteAndClickWithAnchor(url, selector, inputCssSelector, date.format(outputFormatter));
//         }

//         return new ArrayList<>(); // Replace with actual DB call
//     }

// }


import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.demo.entity.KhananData;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class SeleniumTestService {

    @Autowired
    public KhananDataService khananDataService;

    private static final String INVALID_HREF = "https://khanansoft.bihar.gov.in/portal/CitizenRpt/epassreportAllDist.aspx#";
    private static final int TABLE_INDEX = 1;
    private static final String VIEW_ALL_BTN_ID = "ctl00_MainContent_lbtnAll";
    private static final String PASS_TYPE_ID = "ctl00_MainContent_ddlPassType";
    private static final String DISTRICT_ID = "ctl00_MainContent_ddlDMO";
    private static final String CONSIGNER_ID = "ctl00_MainContent_ddlConsigner";
    private static final String DATE_ID = "ctl00_MainContent_txtDate";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
    private static final Logger logger = LoggerFactory.getLogger(SeleniumTestService.class);

    private final AtomicBoolean isProcessing = new AtomicBoolean(false);
    private String currentStatusMessage = "IDLE";

    // --- Status Methods ---
    public boolean isCurrentlyRunning() { return isProcessing.get(); }
    public String getStatusMessage() { return currentStatusMessage; }

    // --- Driver Setup ---
    private WebDriver setupChromeDriver() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new", "--no-sandbox", "--disable-dev-shm-usage", "--disable-gpu", "--window-size=1920,1080");
        return new ChromeDriver(options);
    }

    // --- Main Scheduled Task ---
    public void scheduledScrapingTask(String url, String dateSelector, String fromDate, String toDate) {
        if (!isProcessing.compareAndSet(false, true)) {
            logger.warn("Scraper execution skipped: Already running.");
            return;
        }

        WebDriver driver = null;
        LocalDateTime lastResetTime = LocalDateTime.now();

        try {
            driver = setupChromeDriver();
            LocalDate startDate = LocalDate.parse(fromDate, DATE_FORMATTER);
            LocalDate endDate = LocalDate.parse(toDate, DATE_FORMATTER);
            
            for (LocalDate date = startDate; !date.isBefore(endDate); date = date.minusDays(1)) {
                if (Thread.currentThread().isInterrupted()) break;
                
                currentStatusMessage = "RUNNING: Processing date " + date;
                
                // System.out.println("==================== " + date.format(DATE_FORMATTER) + " ====================");
                // Optimized: Reset session every 5 hours instead of every 5 days
                if (ChronoUnit.HOURS.between(lastResetTime, LocalDateTime.now()) >= 5) {
                    logger.info("5-hour threshold reached. Resetting session...");
                    driver.manage().deleteAllCookies();
                    lastResetTime = LocalDateTime.now();
                }
                

                processDateIteration(driver, url, dateSelector, date);
            }
        } catch (Exception e) {
            logger.error("Critical Failure in Scraper: ", e);
        } finally {
            isProcessing.set(false);
            currentStatusMessage = "IDLE - Last run completed at " + LocalDateTime.now();
            if (driver != null) {
                driver.quit();
                logger.info("Resources cleaned up successfully.");
            }
        }
    }

    private void processDateIteration(WebDriver driver, String url, String dateSelector, LocalDate currentDate) {
        String formattedDate = currentDate.format(DATE_FORMATTER);
        try {
            driver.get(url);
            setDateInput(driver, dateSelector, formattedDate);

            Map<String, String> anchors = extractTableData(driver, TABLE_INDEX);
            for (String anchorUrl : anchors.values()) {
                processSecondLayer(driver, anchorUrl);
            }
        } catch (Exception e) {
            logger.error("Error on date {}: {}", formattedDate, e.getMessage());
        }
    }

    private void processSecondLayer(WebDriver driver, String url) {
        try {
            driver.get(url);
            clickViewAllButton(driver);
            String sourceType = getDropdownValue(driver, PASS_TYPE_ID);
            Map<String, String> anchors = extractTableData(driver, TABLE_INDEX);
            for (String nextUrl : anchors.values()) {
                processThirdLayer(driver, nextUrl, sourceType);
            }
        } catch (Exception e) {
            logger.error("Error in Second Layer: {}", e.getMessage());
        }
    }

    private void processThirdLayer(WebDriver driver, String url, String sourceType) {
        try {
            driver.get(url);
            clickViewAllButton(driver);
            Map<String, String> anchors = extractTableData(driver, TABLE_INDEX);
            for (String nextUrl : anchors.values()) {
                List<KhananData> batchList = new ArrayList<>();
                processFourthLayer(driver, nextUrl, sourceType, batchList);
            }
        } catch (Exception e) {
            logger.error("Error in Third Layer: {}", e.getMessage());
        }
    }

    // private void processFourthLayer(WebDriver driver, String url, String sourceType, List<KhananData> khananDataList) {
    //     try {
    //         driver.get(url);
    //         clickViewAllButton(driver);
            
    //         // Explicit Wait for table content
    //         WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    //         wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("table")));

    //         String district = getDropdownValue(driver, DISTRICT_ID);
    //         String consigner = getDropdownValue(driver, CONSIGNER_ID);
    //         String dateVal = driver.findElement(By.id(DATE_ID)).getAttribute("value");

    //         List<WebElement> tables = driver.findElements(By.tagName("table"));
    //         if (tables.size() >= 2) {
    //             List<WebElement> rows = tables.get(1).findElements(By.tagName("tr"));
    //             List<KhananData> pageBatch = new ArrayList<>();

    //             for (int i = 1; i < rows.size() - 1; i++) {
    //                 List<WebElement> cols = rows.get(i).findElements(By.tagName("td"));
    //                 if (cols.size() >= 11 && !cols.get(0).getText().contains("No Data Found")) {
    //                     pageBatch.add(new KhananData(district, consigner, dateVal, sourceType,
    //                             getColumnText(cols, 1), getColumnText(cols, 2), getColumnText(cols, 3),
    //                             getColumnText(cols, 4), getColumnText(cols, 5), getColumnText(cols, 6),
    //                             getColumnText(cols, 7), getColumnText(cols, 8), getColumnText(cols, 9),
    //                             getColumnText(cols, 10)));
    //                 }
    //             }
    //             // Optimized: Single database hit per page
    //             if (!pageBatch.isEmpty()) {
    //                 khananDataService.saveAllKhananData(pageBatch);
    //                 khananDataList.addAll(pageBatch);
    //             }
    //         }
    //     } catch (Exception e) {
    //         logger.error("Error in Fourth Layer: {}", e.getMessage());
    //     }
    // }

    private void processFourthLayer(WebDriver driver, String url, String sourceType, List<KhananData> khananDataList) {
        int maxRetries = 2;
        for (int attempt = 0; attempt < maxRetries; attempt++) {
            try {
                driver.get(url);
                handleUnexpectedAlert(driver);
                clickViewAllButton(driver);
                
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(DISTRICT_ID)));

                String district = getDropdownValue(driver, DISTRICT_ID);
                String consigner = getDropdownValue(driver, CONSIGNER_ID);
                String dateVal = driver.findElement(By.id(DATE_ID)).getAttribute("value");

                List<WebElement> tables = driver.findElements(By.tagName("table"));
                if (tables.size() >= 2) {
                    List<WebElement> rows = tables.get(1).findElements(By.tagName("tr"));
                    List<KhananData> pageBatch = new ArrayList<>();

                    for (int i = 1; i < rows.size() - 1; i++) {
                        try {
                            // Refresh row reference inside loop
                            WebElement row = driver.findElements(By.tagName("table")).get(1)
                                                .findElements(By.tagName("tr")).get(i);
                            List<WebElement> cols = row.findElements(By.tagName("td"));
                            
                            if (cols.size() >= 11 && !cols.get(0).getText().contains("No Data Found")) {
                                pageBatch.add(new KhananData(district, consigner, dateVal, sourceType,
                                        getColumnText(cols, 1), getColumnText(cols, 2), getColumnText(cols, 3),
                                        getColumnText(cols, 4), getColumnText(cols, 5), getColumnText(cols, 6),
                                        getColumnText(cols, 7), getColumnText(cols, 8), getColumnText(cols, 9),
                                        getColumnText(cols, 10)));
                            }
                        } catch (StaleElementReferenceException e) {
                            continue; // Skip the single stale row and keep going
                        }
                    }
                    
                    if (!pageBatch.isEmpty()) {
                        khananDataService.saveAllKhananData(pageBatch);
                        logger.info("Saved batch of {} records", pageBatch.size());
                    }
                }
                break; // Success, exit retry loop
            } catch (Exception e) {
                logger.error("Attempt {} failed for Fourth Layer: {}", attempt + 1, e.getMessage());
                if (attempt == maxRetries - 1) throw e; 
            }
        }
    }

    // --- Helper Methods ---
    private void setDateInput(WebDriver driver, String selector, String value) {
        WebElement dateInput = driver.findElement(By.cssSelector(selector));
        dateInput.clear();
        dateInput.sendKeys(value, Keys.RETURN);
        logger.info("======== Running Date: {} =======", value);
    }

    private void clickViewAllButton(WebDriver driver) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            // Check if button exists before trying to click
            List<WebElement> buttons = driver.findElements(By.id(VIEW_ALL_BTN_ID));
            
            if (!buttons.isEmpty() && buttons.get(0).isDisplayed()) {
                WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(By.id(VIEW_ALL_BTN_ID)));
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", btn);
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
                logger.info("View All button clicked.");
                
                // Critical: Wait for the postback to finish (loading overlay to disappear)
                Thread.sleep(2000); 
            } else {
                logger.info("View All button not present or required for this page. Proceeding...");
            }
        } catch (Exception e) {
            logger.warn("View All button check skipped/failed: {}", e.getMessage());
            // Don't throw a RuntimeException here, just let it try to scrape what's available
        }
    }

    private void handleUnexpectedAlert(WebDriver driver) {
        try {
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            logger.warn("Target site displayed an internal error alert: {}", alertText);
            alert.accept(); // Closes the "SQL Server error" popup
        } catch (NoAlertPresentException e) {
            // No alert, we are good to go
        }
    }

    private Map<String, String> extractTableData(WebDriver driver, int index) {
        Map<String, String> anchors = new HashMap<>();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        
        try {
            // Wait for the table to be stable
            wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("table")));
            
            List<WebElement> tables = driver.findElements(By.tagName("table"));
            if (tables.size() > index) {
                WebElement table = tables.get(index);
                List<WebElement> rows = table.findElements(By.tagName("tr"));
                
                for (int i = 1; i < rows.size(); i++) {
                    try {
                        // Re-find the row within the loop to prevent staleness
                        WebElement currentRow = driver.findElements(By.tagName("table")).get(index)
                                            .findElements(By.tagName("tr")).get(i);
                        
                        List<WebElement> cols = currentRow.findElements(By.tagName("td"));
                        anchors.putAll(extractAnchorFromRow(cols, i, 4, 8));
                    } catch (StaleElementReferenceException | IndexOutOfBoundsException e) {
                        // If the table refreshed, we stop and return what we found
                        logger.warn("Table refreshed during extraction at row {}", i);
                        break; 
                    }
                }
            }
        } catch (Exception e) { 
            logger.error("Table extraction failed: {}", e.getMessage()); 
        }
        return anchors;
    }

    private Map<String, String> extractAnchorFromRow(List<WebElement> cols, int rowIdx, int... targetCols) {
        Map<String, String> found = new HashMap<>();
        for (int cIdx : targetCols) {
            if (cIdx < cols.size()) {
                List<WebElement> a = cols.get(cIdx).findElements(By.tagName("a"));
                if (!a.isEmpty() && isValidHref(a.get(0).getAttribute("href"))) {
                    found.put(rowIdx + "-" + cIdx, a.get(0).getAttribute("href"));
                }
            }
        }
        return found;
    }

    private boolean isValidHref(String h) { return h != null && !h.isEmpty() && !h.equals(INVALID_HREF); }
    private String getDropdownValue(WebDriver driver, String id) {
        try { return new Select(driver.findElement(By.id(id))).getFirstSelectedOption().getText(); } 
        catch (Exception e) { return "N/A"; }
    }
    private String getColumnText(List<WebElement> cols, int i) {
        return (i < cols.size()) ? cols.get(i).getText().trim() : "";
    }

    // Run at the particualr date (-1 days data has been loaded) and also can be triggered manually without Schdular
    public void triggerDailyScraping() {
        if (isProcessing.get()) {
            logger.warn("Manual trigger skipped: Scraper is already running.");
            return;
        }
        String today = LocalDate.now().minusDays(1).format(DATE_FORMATTER); // Scrape yesterday's data by default
        String dateSelector = "#ctl00_MainContent_txtDate1";
        try {
            logger.info("Manual scraping triggered for date {}", today);
            scheduledScrapingTask(INVALID_HREF, dateSelector, today, today);
        } catch (Exception e) {
            logger.error("Manual scraping failed for date {}", today, e);
        }
    }

    @Scheduled(cron = "0 0 6 * * ?", zone = "Asia/Kolkata")
    public void runDailyScrapingAtOneAM() {
        if (isProcessing.get()) {
            logger.warn("Manual trigger skipped: Scraper is already running.");
            return;
        }
        String today = LocalDate.now().minusDays(1).format(DATE_FORMATTER); // Scrape yesterday's data by default
        String dateSelector = "#ctl00_MainContent_txtDate1";
        try {
            logger.info("Manual scraping triggered for date {}", today);
            scheduledScrapingTask(INVALID_HREF, dateSelector, today, today);
        } catch (Exception e) {
            logger.error("Manual scraping failed for date {}", today, e);
        }
    }


    
}