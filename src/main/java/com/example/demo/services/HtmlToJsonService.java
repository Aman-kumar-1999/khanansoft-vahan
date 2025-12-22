package com.example.demo.services;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.demo.TableRowData;

import io.github.bonigarcia.wdm.WebDriverManager;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class HtmlToJsonService {
	
	
	public List<Map<String,String>> fetchWebScraping(){
		List<Map<String, String>> jsonList = new ArrayList<>();
		String url = "https://khanansoft.bihar.gov.in/portal/CitizenRpt/epassreportAllDist.aspx";
		try {
            WebDriverManager.chromedriver().setup();
            WebDriver driver = new HtmlUnitDriver(false);

            //driver.manage().window().maximize();
            driver.get("https://khanansoft.bihar.gov.in/portal/CitizenRpt/epassreportAllDist.aspx");

            // Wait for the element to appear
            //Thread.sleep(2000);

            // Perform click — e.g. click the first <a> tag
            //driver.findElement(By.tagName("table")).click();

            //Thread.sleep(2000); // wait to observe click
            //driver.quit();

//            Document doc = Jsoup.connect(url).get();
//    		//System.out.println("doc : "+doc);
//    		Element table = doc.select("table").get(1); // pick the first table
//    		//System.out.println("table " + table);
//    		List<TableRowData> result = new ArrayList<>();
//    		//List<String> hrefs = new ArrayList<>();
//    		Map<String,String> lisOfAnchor = new HashMap<>();
//    		if (table != null) {
//    			Elements rows = table.select("tr");
//    			List<String> headers = new ArrayList<>();
//    			for (int i = 0; i < rows.size(); i++) {
//    				Elements cols = rows.get(i).select("th, td");
//
//    				// first row = headers
//    				if (i == 0) {
//    					for (Element col : cols) {
//    						headers.add(col.text().trim());
//    					}
//    				} else {
//    					
//    					Elements anchors = cols.select("a[href]");
//    					//System.out.println("Anchors Size ====== "+anchors.size());
//    					List<String> hrefs = new ArrayList<>();
//    					Map<String, String> rowData = new LinkedHashMap<>();
//    					
//    					for (int j = 0; j < cols.size(); j++) {
//    						
//    						if ( anchors.size() == 2) {
//    							String district = ""; 
//    							if(j==4) {
//    								String key1 = j < headers.size() ? headers.get(j) : "i : "+i+" Column" + j;
//    								lisOfAnchor.put(key1, anchors.get(0).absUrl("href"));
//    								district = cols.get(0).text();
//    								hrefs.add(anchors.get(0).absUrl("href"));
//    							}
//    							if(j==8) {
//    								String key1 = j < headers.size() ? headers.get(j) : "i : "+i+" Column" + j;
//    								lisOfAnchor.put(key1, anchors.get(1).absUrl("href"));
//    								district = cols.get(1).text();
//    								hrefs.add(anchors.get(1).absUrl("href"));	
//    							}
//    							
//    						}
//    						String key = j < headers.size() ? headers.get(j) : "Column" + j;
//    						rowData.put(key, cols.get(j).text().trim());
//    					}
//    					jsonList.add(rowData);
//    				}
//    			}
//    		}
    		
//    		lisOfAnchor.entrySet().stream()
//            .sorted(Map.Entry.comparingByKey()) // sort by key
//            .forEach(entry -> System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue()));

    		return jsonList;
        } catch (Exception e) {
            e.printStackTrace();
            return jsonList;
        }
	}
	
	
	
	
	

	public List<Map<String, String>> fetchAndParseTable() throws IOException {
		String url = "https://khanansoft.bihar.gov.in/portal/CitizenRpt/epassreportAllDist.aspx";

		Document doc = Jsoup.connect(url).get();
		//System.out.println("doc : "+doc);
		Element table = doc.select("table").get(1); // pick the second table
		//System.out.println("table " + table);
		List<Map<String, String>> jsonList = new ArrayList<>();
		List<TableRowData> result = new ArrayList<>();
		//List<String> hrefs = new ArrayList<>();
		Map<String,String> lisOfAnchor = new HashMap<>();
		if (table != null) {
			Elements rows = table.select("tr");
			List<String> headers = new ArrayList<>();
			for (int i = 0; i < rows.size(); i++) {
				Elements cols = rows.get(i).select("th, td");

				// first row = headers
				if (i == 0) {
					for (Element col : cols) {
						headers.add(col.text().trim());
					}
				} else {
					
					Elements anchors = cols.select("a[href]");
					//System.out.println("Anchors Size ====== "+anchors.size());
					List<String> hrefs = new ArrayList<>();
					Map<String, String> rowData = new LinkedHashMap<>();
					
					for (int j = 0; j < cols.size(); j++) {
						
						if ( anchors.size() == 2) {
							String district = ""; 
							if(j==4) {
								String key1 = j < headers.size() ? headers.get(j) : "i : "+i+" Column" + j;
								lisOfAnchor.put(key1, anchors.get(0).absUrl("href"));
								district = cols.get(0).text();
								hrefs.add(anchors.get(0).absUrl("href"));
							}
							if(j==8) {
								String key1 = j < headers.size() ? headers.get(j) : "i : "+i+" Column" + j;
								lisOfAnchor.put(key1, anchors.get(1).absUrl("href"));
								district = cols.get(1).text();
								hrefs.add(anchors.get(1).absUrl("href"));	
							}
							
						}
						String key = j < headers.size() ? headers.get(j) : "Column" + j;
						rowData.put(key, cols.get(j).text().trim());
					}
					jsonList.add(rowData);
				}
			}
		}
		
		lisOfAnchor.entrySet().stream()
        .sorted(Map.Entry.comparingByKey()) // sort by key
        .forEach(entry -> System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue()));

		return jsonList;
	}

	public Document getReportData(String dmoValue, String passTypeValue, String newDate) throws Exception {

		String baseUrl = "https://khanansoft.bihar.gov.in/portal/CitizenRpt/ePassReportAllConsigner.aspx";

		String URL = "https://khanansoft.bihar.gov.in/portal/CitizenRpt/ePassReportAllConsigner.aspx?ASHxNlmoFyLHysPqHK2s1TwFgowNPHidOTrr5XhEWswdu5gpobGqv+72I/1rwkLPB0JdWHjzMrgGY8WgzHvrQsMFBsTpyEs8qxGPUUBCa/nuq+dowsC1AuMXB8GOhlTExOId/pS80TQ=";

		// 1. Initial GET Request to retrieve the dynamic form parameters (especially
		// __VIEWSTATE)
		Connection.Response initialResponse = Jsoup.connect(URL).method(Connection.Method.GET).execute();

		// Get the initial HTML Document
		Document initialDoc = initialResponse.parse();
		System.out.println("iiiiiiiiiiiiiiiiiii + " + initialDoc);

		// Get the cookies from the initial response, as ASP.NET often uses them for
		// session state
		Map<String, String> cookies = initialResponse.cookies();
		System.out.println("Cookies " + cookies);

		// 2. Prepare the data payload for the POST request
		Map<String, String> formData = new HashMap<>();

		// Extract and add the crucial hidden ASP.NET form fields
		formData.put("__VIEWSTATE", initialDoc.select("#__VIEWSTATE").val());
		formData.put("__VIEWSTATEGENERATOR", initialDoc.select("#__VIEWSTATEGENERATOR").val());
		formData.put("__PREVIOUSPAGE", initialDoc.select("#__PREVIOUSPAGE").val());
		// Add other hidden fields if necessary (like __EVENTVALIDATION)
		// for (Element input : initialDoc.select("input[type=hidden]")) {
		// if (!formData.containsKey(input.attr("name"))) {
		// formData.put(input.attr("name"), input.val());
		// }
		// }

		// Add the button that was clicked to trigger the postback
		formData.put("ctl00$MainContent$btnshow", "Show");

		// 3. Add your custom/modified form values

		// DMO dropdown (e.g., "1" for ARARIA, "26" for PATNA, etc.)
		formData.put("ctl00$MainContent$ddlDMO", dmoValue);

		// Source Type dropdown (e.g., "1" for Lessee, "2" for Dealer)
		formData.put("ctl00$MainContent$ddlPassType", passTypeValue);

		// The 7th input tag (Date/तारीख) - This is where you set your custom date!
		formData.put("ctl00$MainContent$txtDate", newDate); // Format typically "dd-MMM-yyyy", e.g., "19-Oct-2025"

		String encryptedParam = "ASHxNlmoFyLHysPqHK2s1TwFgowNPHidOTrr5XhEWswdu5gpobGqv+72I/1rwkLPB0JdWHjzMrgGY8WgzHvrQsMFBsTpyEs8qxGPUUBCa/nuq+dowsC1AuMXB8GOhlTExOId/pS80TQ=";

		// ✅ Encode before sending
		String encodedParam = URLEncoder.encode(encryptedParam, StandardCharsets.UTF_8);
		String url = baseUrl + "?" + encodedParam;

		// 4. Execute the POST request (the form submission/postback)
		Document resultDoc = Jsoup.connect(URL).cookies(cookies) // Send the session cookies
				.data(formData).method(Connection.Method.POST).timeout(30000) // Increase timeout for slow requests
				.userAgent("Mozilla/5.0") // Mimic a real browser
				.followRedirects(true).post();

		System.out.println("4th Step data : + " + resultDoc);

		return resultDoc;
	}

	@Autowired
	private WebClient webClient;

	// The base URL and the query part, which needs to be URL-encoded for safety
//	private static final String FULL_URL = "https://khanansoft.bihar.gov.in/portal/CitizenRpt/ePassReportAllConsigner.aspx?ASHxNlmoFyLHysPqHK2s1TwFgowNPHidOTrr5XhEWswdu5gpobGqv+72I/1rwkLPB0JdWHjzMrgGY8WgzHvrQsMFBsTpyEs8qxGPUUBCa/nuq+dowsC1AuMXB8GOhlTExOId/pS80TQ=";
//
//	public HtmlToJsonService(WebClient.Builder webClientBuilder) {
//		// Customize WebClient instance
//		this.webClient = webClientBuilder.baseUrl(FULL_URL).defaultHeader(HttpHeaders.ACCEPT,
//				"text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7")
//				.defaultHeader(HttpHeaders.ACCEPT_ENCODING, "gzip, deflate, br, zstd")
//				.defaultHeader(HttpHeaders.CACHE_CONTROL, "max-age=0")
//				.defaultHeader(HttpHeaders.CONNECTION, "keep-alive").defaultHeader("sec-fetch-dest", "document")
//				.defaultHeader("sec-fetch-mode", "navigate").defaultHeader("sec-fetch-site", "none")
//				.defaultHeader("sec-fetch-user", "?1").defaultHeader("upgrade-insecure-requests", "1").build();
//	}

	public String fetchEpassReportHtml(String sessionId, String userAgent) {

		String responseBody = webClient.get()
				// The full URL includes the path and query, so we use retrieve() directly.
				.uri(uriBuilder -> uriBuilder.build()) // Builds the URI using the base URL

				// 1. Add the Cookie header
				.header(HttpHeaders.COOKIE, "ASP.NET_SessionId=" + sessionId)

				// 2. Add the User-Agent header for customization
				.header(HttpHeaders.USER_AGENT, userAgent)

				// 3. Add other specific headers (e.g., Accept-Language, Host, Referer)
				.header(HttpHeaders.ACCEPT_LANGUAGE, "en-US,en;q=0.9")
				.header(HttpHeaders.HOST, "khanansoft.bihar.gov.in")
				// Note: Referer is tricky; if the request isn't coming from the previous page,
				// omit it or use an appropriate value.
				// .header(HttpHeaders.REFERER, "...")

				// Execute the request and retrieve the body
				.retrieve()

				// Handle HTTP errors gracefully
				.onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
						clientResponse -> clientResponse.createException())

				// Extract the response body as a String (since it's HTML)
				.bodyToMono(String.class)
				//formData.put("ctl00$MainContent$txtDate", newDate);

				// Block and wait for the response (use sparingly in a real-world Spring Boot
				// web app,
				// prefer Mono/Flux/async/reactive flow if this is part of a REST controller)
				.block();

		return responseBody;
	}

}
