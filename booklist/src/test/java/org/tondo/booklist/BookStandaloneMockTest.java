package org.tondo.booklist;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.tondo.booklist.web.BookController;

/**
 * Demo of StandaloMvcMOck configuration. Little trick is used for instantiation of controller, where
 * loading of application context is used. Better way for this approach is maybe create controller inside
 * this test.
 * @author TondoDev
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BookListApplication.class)
public class BookStandaloneMockTest {
	private MockMvc mockMvc;
	
	
	@Autowired
	private ApplicationContext ctx;
	
	
	@Before
	public void setupMock() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(ctx.getBean(BookController.class))
				.build();
	}
	
	@Test
	public void testStandaloneSetup() throws Exception {
		// we can do same things as we did in BookControllerServletMockingTest, 
		// but only with by ourselves registered controllers
		this.mockMvc.perform(MockMvcRequestBuilders.get("/a/test"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.view().name("bookList"))
			.andExpect(MockMvcResultMatchers.model().attribute("books", Matchers.is(Matchers.empty())));
			
	}
}
