package org.tondo.booklist;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * With selenium api, we can access page elements and submits forms.
 * Chrome driver must be installed on system for running this test
 *
 * <p>https://sites.google.com/a/chromium.org/chromedriver/downloads</p>
 * 
 * @author TondoDev
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebIntegrationTest
@SpringApplicationConfiguration(classes = BookListApplication.class)
public class BookSeleniumIntegrationTest {
	
	private ChromeDriver chrome;
	
	@Value("${tondo.label}")
	private String envHeader;
	
	@BeforeClass
	public static void setDriverLocation() {
		// property must point to driver executable
		System.setProperty("webdriver.chrome.driver", "D:\\chromedriver\\chromedriver.exe");
	}
	
	@Before
	public void setupBrowser() {
		System.out.println("== " + envHeader);
		this.chrome = new ChromeDriver();
		// timeout before accessing page elements
		this.chrome.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
	}
	
	@Test
	public void testEnvironmentHeader() {
		this.chrome.get("http://localhost:8080/a/ahoj");
		Assert.assertEquals(envHeader, this.chrome.findElementByTagName("h1").getText());
		
		Assert.assertEquals("No books added so far!",  0, chrome.findElementsByTagName("dl").size());
		
		chrome.findElementByName("author").sendKeys("Jon Doe");
		chrome.findElementByName("title").sendKeys("How to make a kite!");
		chrome.findElementByTagName("form").submit();
		
		Assert.assertEquals("One book should be added",  1, chrome.findElementsByTagName("dl").size());
		
	}
	
	@After
	public void destroyBrowser() {
		this.chrome.quit();
	}

}
