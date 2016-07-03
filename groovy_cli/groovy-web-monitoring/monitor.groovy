@Grab("spring-boot-actuator")
@RestController
class Monitor {
  @RequestMapping("/")
  def home(@RequestParam(value="name", defaultValue="World") String name){
    return "Hello, " + name
  }
}
