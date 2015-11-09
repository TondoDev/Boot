/**
 * http://www.webjars.org/
 * Wrapped JavaScript libraries into Maven or Gradle artifact/
 */
@Grab("org.webjars:jquery:2.1.4")
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

  @RequestMapping(value="/withjs")
  def modern(@RequestParam(value="name", defaultValue="World") String name) {

    return new ModelAndView("modern")
      .addObject("name", name)
      .addObject("chapters", chapters)
  }
}
