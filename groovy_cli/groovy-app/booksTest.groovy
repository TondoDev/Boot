class Book {
  String author
  String title
}

class BookTest {

  @Test
  void testBooks() {
    Book book = new Book(author: "Tom Clancy",
    title: "Threat Vector")
    assertEquals("Tom Clancy", book.author)
  }
}
