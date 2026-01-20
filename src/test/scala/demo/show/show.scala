package demo.show

import no.vedaadata.text.*

case class Country(
  code: String,
  name: String,
  language: String,
  inhabitants: BigDecimal)

case class Hobby(
  name: String,
  description: Option[String])

case class Person(
  firstName: String,
  lastName: String,
  age: Int,
  city: Option[String],
  country: Country,
  income: Option[BigDecimal],
  hobbiesByCountry: Map[Country, List[Hobby]])

object Person:

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
    Map(
      Norway -> List(Chess, Golf),
      Sweden -> List(Tennis)))

  val Bob = Person(
    "Bob",
    "Smith",
    45,
    None,
    Sweden,
    None,
    Map(
      Sweden -> List(Golf, Tennis)))

  val persons = List(Tom, Bob)

@main def main =
  // given Show[Country] = Show.derived
  // given Show[Hobby] = Show.derived
  given Show[Person] = Show.derived

  given Show[Country] = Show.proxy(_.name)
  given Show[Hobby] = Show.proxy(_.name)
//  given Show.EmptyPolicy = Show.EmptyPolicy.Hide
  println(Person.persons.show)