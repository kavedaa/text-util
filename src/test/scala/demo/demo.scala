package demo

import no.vedaadata.text.*

case class Country(
  code: String,
  name: String,
  language: String,
  inhabitants: BigDecimal)
  derives Show

case class Hobby(
  name: String,
  description: Option[String])
  derives Show

case class Person(
  firstName: String,
  lastName: String,
  age: Int,
  city: Option[String],
  country: Country,
  income: Option[BigDecimal],
  hobbies: List[Hobby])
  derives Show

object Test:

  val Norway = Country("NO", "Norway", "Norwegian", BigDecimal(5000000))
  val Sweden = Country("SE", "Sweden", "Swedish", BigDecimal(7000000))

  val Chess = Hobby("Chess", Some("Board game"))
  val Golf = Hobby("Golf", None)
  val Tennis = Hobby("Tennis", None)

  val Tom = Person(
    "Tom",
    "Smith",
    30,
    Some("Bergen"),
    Norway,
    Some(BigDecimal(123000)),
    Nil)

  val Bob = Person(
    "Bob",
    "Smith",
    45,
    None,
    Sweden,
    None,
    List(Golf, Tennis))

  val persons = List(Tom, Bob)

@main def main =
  given Show[Country] = Show.proxy[Country, String](_.name)
  given Show[Person] = Show.derived
//  given Show.EmptyPolicy = Show.EmptyPolicy.Hide
  println(Test.persons.show)