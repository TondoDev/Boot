package org.tondo.booklist.actuator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.PublicMetrics;
import org.springframework.boot.actuate.metrics.Metric;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

/**
 * 
 * @author TondoDev
 *
 * This class is used for define new custom metrics.
 * It looks it is automatically picked up by auctuator because
 * it implements interface PublicMetrics.
 */
@Component
public class BookAppMetrics implements PublicMetrics {
	
	@Autowired
	private ApplicationContext context;
	
	@Override
	public Collection<Metric<?>> metrics() {
		List<Metric<?>> metrics = new ArrayList<>();
		//  sets controllers count
		//  for metric value Number (T extends Number)
		metrics.add(new Metric<Integer>("book.controllers", context.getBeanNamesForAnnotation(Controller.class).length));
		metrics.add(new Metric<Long>("book.time", new Date().getTime()));
		
		return metrics;
	}

}
