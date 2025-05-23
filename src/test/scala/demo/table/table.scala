package demo.table

import no.vedaadata.text.table.*
import no.vedaadata.generator.Generator

case class Person(name: String, age: Int, city: String, height: Double, fortune: Int) derives TextTableEncoder

object Person:

  val generator = 
    (Generator("Alice", "Bob", "Charles"), 
    Generator.between(20, 50), 
    Generator("Oslo", "Bergen", "London"), 
    Generator.between(1.60, 1.90), 
    Generator.between(1000, 1000000)).mapN(Person.apply)

@main def main =

  val persons = Person.generator.take(10)

  TextTable.renderAndPrint(persons)