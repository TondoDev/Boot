class HelloSpock extends Specification {
  def "length of Spock's and his friends' names"() {
    expect:
    name.size() == length
    where:
    name | length
    "Spock" | 2
    "Kirk" | 4
    "Scotty" | 1
  }
}
