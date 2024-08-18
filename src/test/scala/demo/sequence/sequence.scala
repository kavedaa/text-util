package demo.sequence

import java.time.LocalDate

import no.vedaadata.text.*

case class Person(
  name: String,
  birthDate: LocalDate,
  city: String,
  country: Option[String])

object Person:
  val items = List(
    Person("Bob", LocalDate.of(2000, 1, 1), "Bergen", Some("Norway")),
    Person("Tom", LocalDate.of(2001, 2, 3), "London", None))

@main def main =

  given Format.DateFormatter = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")

  val layout = TextSequence.Layout[Person](
    TextSequence.Layout.Column("Name", _.name),
    TextSequence.Layout.Column("Birthdate", _.birthDate),
    TextSequence.Layout.Column("City", _.city),
    TextSequence.Layout.Column("Country", _.country))

  val csv = TextSequence.encodeHeaderAndLines(Person.items)(layout)

  csv.foreach(println)