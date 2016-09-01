package org.tondo.booklist.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.tondo.booklist.domain.Book;
import org.tondo.booklist.domain.BookRepository;

// enables population of properties (which have setters)  in this class with values
// from property files
@ConfigurationProperties(prefix = "tondo")
@Controller
@RequestMapping("/a")
public class BookController {
	
	private BookRepository bookRepository;
	
	private String label;
	
	public void setLabel(String label) {
		this.label = label;
	}
	
	@Autowired
	public BookController(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}
	
	@Autowired
	private CounterService counterService;
	
	@Autowired
	private GaugeService gaugeService;

	
	@RequestMapping(value = "/{reader}", method = RequestMethod.GET)
	public String readersBookList(@PathVariable ("reader") String reader, Model model) {
		// adds one to custom counter accessible from /metrics actuator endpoint
		// after each time book list service is called
		// in metrics will be listed as counter.linstcnt
		counterService.increment("listcnt");
		
		List<Book> books = bookRepository.findByReader(reader);
		if (books != null) {
			model.addAttribute("books", books);
		}
		
		// adding label defined in property file
		model.addAttribute("customLabel", label);
		return "bookList";
	}
	
	@RequestMapping(value = "/{reader}", method = RequestMethod.POST)
	public String markForRead(@PathVariable String reader, Book book) {
		book.setReader(reader);
		bookRepository.save(book);
		
		// stores time in miliseconds when last book was saved
		gaugeService.submit("lastbooksaved", System.currentTimeMillis());
		return "redirect:/a/{reader}";
	}
}
