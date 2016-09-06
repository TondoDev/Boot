package org.tondo.booklist.actuator;

import java.util.Date;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Demo of health indicator. Class is picked automatically by actuator because of 
 * it implements HealtIndicator interface.
 * 
 * This implementation is silly, just try access some page to determine if it is up or down.
 * Additional data can be written to state. 
 *  
 * @author TondoDev
 *
 */
@Component
public class SectorHealthIndicator implements HealthIndicator{

	
	@Override
	public Health health() {
		// sets timeout for 3 seconds
		SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
		requestFactory.setConnectTimeout(3000);
		RestTemplate restTemplate = new RestTemplate(requestFactory);
		
		
		String sectorUrl = "http://www.sector.sk";
		// use some non existing addres to get DOWN status
		try {
			restTemplate.getForObject(sectorUrl, String.class);
			// create indicator with status UP
			return Health.up()
					// contains arbitrary information - can be provided many times
					.withDetail("test time", new Date())
					.withDetail("indicator name", SectorHealthIndicator.class.getName())
					// can provide different message from UP/DOWN
					//.status("another description")
					.build();
		} catch (Exception e) {
			// in case of failure indicator will have status DOWN
			return Health.down()
					.down(e)
					.withDetail("location not responding", sectorUrl)
					.build();
		}
	}

}
