/**
 * Must implements CommandLineRunner
 */
class Command implements CommandLineRunner{

  void run(String... args) {
    println "----------AHOJ" + args
  }
}
