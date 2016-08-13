@ConfigurationProperties(prefix = "tondo")
@Controller
@RequestMapping("/a")
class ReadingListController {

  String label

  @Autowired
  ReadingListRepository readingListRepository


  @RequestMapping(value = "/{reader}", method=RequestMethod.GET)
  def readersBooks(Model model, @PathVariable String reader) {
    List<Book> books = readingListRepository.findByReader(reader)

    if (books != null) {
			model.addAttribute("books", books)
		}

		// adding label defined in property file
		model.addAttribute("customLabel", label)
		return "bookList"
  }

  @RequestMapping(value = "/{reader}", method = RequestMethod.POST)
  def addToReadingList(Book book, @PathVariable String reader) {
      book.setReader(reader)
      readingListRepository.save(book)
      return "redirect:/a/{reader}"
  }
}
