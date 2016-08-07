package org.tondo.booklist;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import org.junit.Assert;

/**
 * 
 * Test with running embedded web servlet container.
 * All test methods are run within same server start session.
 * 
 * 
 * @author TondoDev
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebIntegrationTest
@SpringApplicationConfiguration(classes = BookListApplication.class)
public class BookIntegrationTest {

	// can be used to find server port
	@Value("${local.server.port}")
	private int port;
	
	@Test
	public void testNotFound() {
		
		RestTemplate rest = new RestTemplate();
		try {
			String text = rest.getForObject("http://localhost:8080/aa/ahoj", String.class);
			Assert.fail("Expected HttpClientErrorException");
		} catch (HttpClientErrorException e) {}
	}
	
	@Test
	public void getPageTest() {
		RestTemplate rest = new RestTemplate();
		ResponseEntity<String> response = rest.getForEntity("http://localhost:8080/a/ahoj", String.class);
		Assert.assertEquals("Page should be found and returned", HttpStatus.OK, response.getStatusCode());
	}
}
