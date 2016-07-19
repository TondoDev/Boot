package org.tondo.booklist.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.tondo.booklist.domain.Book;
import org.tondo.booklist.domain.BookRepository;

// enables populationg propetries (which have setters)  in this class with values
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

	
	@RequestMapping(value = "/{reader}", method = RequestMethod.GET)
	public String readersBookList(@PathVariable ("reader") String reader, Model model) {
		
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
		return "redirect:/a/{reader}";
	}
}
