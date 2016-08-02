package org.tondo.booklist;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.tondo.booklist.domain.Book;

/**
 * In this test is used mock of servlet container, so no real server is started.
 * Mock is created reading web application context which contains all accessible controllers
 * created during spring bean loading.
 * @author TondoDev
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BookListApplication.class)
@WebAppConfiguration
public class BookControllerServletMockingTest {

	@Autowired
	private WebApplicationContext context;
	
	
	private MockMvc mockMvc;
	
	@Before
	public void setupMock() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
				.build();
	}
	
	@Test
	public void testBookAdd() throws Exception {
		// ensure DB fore reader is empty
		this.mockMvc.perform(MockMvcRequestBuilders.get("/a/test"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.view().name("bookList"))
			.andExpect(MockMvcResultMatchers.model().attribute("books", Matchers.is(Matchers.empty())));
		
		// insert one record into DB
		this.mockMvc.perform(
				MockMvcRequestBuilders.post("/a/test")
					.contentType(MediaType.APPLICATION_FORM_URLENCODED)
					.param("isbn", "464df46sd4asd")
					.param("title", "Spring Boot")
					.param("description", "cool stuff"))
					.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
					.andExpect(MockMvcResultMatchers.header().string("Location", "/a/test"));
		
		Book referenceBook = new Book();
		referenceBook.setId(1L);			// expected that key auto-generation will start with 1
		referenceBook.setReader("test");	// same as specified in URL
		referenceBook.setIsbn("464df46sd4asd");
		referenceBook.setTitle("Spring Boot");
		referenceBook.setDescription("cool stuff");
		
		// read back inserted data
		this.mockMvc.perform(MockMvcRequestBuilders.get("/a/test"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeExists("books"))
			.andExpect(MockMvcResultMatchers.model().attribute("books", Matchers.hasSize(1)))
			.andExpect(MockMvcResultMatchers.model().attribute("books", 
					Matchers.contains(Matchers.samePropertyValuesAs(referenceBook))));
	}

}
