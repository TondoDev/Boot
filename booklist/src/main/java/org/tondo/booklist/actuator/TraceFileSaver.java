package org.tondo.booklist.actuator;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.Writer;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.trace.InMemoryTraceRepository;
import org.springframework.boot.actuate.trace.Trace;
import org.springframework.boot.actuate.trace.TraceRepository;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Demo of custom trace repository.
 * Normally it would be implemented using some persistence
 * service. This is just for demo.
 * 
 *  This repo is using in memory repository, but all request are saved into 
 *  file.
 * 
 * @author TondoDev
 *
 */
@Component
public class TraceFileSaver implements TraceRepository{

	private InMemoryTraceRepository memoryRepository;
	// used for pretty printing
	@Autowired
	private ObjectMapper mapper;
	
	public TraceFileSaver() {
		this.memoryRepository = new InMemoryTraceRepository();
	}
	
	@Override
	public List<Trace> findAll() {
		return memoryRepository.findAll();
	}

	@Override
	public void add(Map<String, Object> traceInfo) {
		memoryRepository.add(traceInfo);
		// appending to file
		// I dont know why, but object mapper everytime closes the writer so 
		// I must open it everytime
		try (Writer writer = new BufferedWriter(new FileWriter("request.log", true))){
			mapper.writer(new DefaultPrettyPrinter()).writeValue(
					writer, traceInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}