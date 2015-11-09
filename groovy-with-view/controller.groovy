@Grab("thymeleaf-spring4")
@Controller
class ViewBasedApp {
  def chapters = ["PRvy", "druhy", "treti", "stvrty"]

  @RequestMapping(value="/")
  def home(@RequestParam(value="name", defaultValue="World") String name) {
    // thenekeaf needs templates/ forlder on classpath
    return new ModelAndView("home") //templates/home.html, view resolver is configured automatically by boot
      .addObject("name", name) // binding object to names which are used in template
      .addObject("chapters", chapters)
  }
}
