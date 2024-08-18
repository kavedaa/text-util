package demo.table

import scala.language.experimental.namedTuples

import no.vedaadata.text.*

@main def main =

  import TextTable.*

  val content = List(
    List("This", "is", "a", "sentence"),
    List("This", "is", "another", "sentence"),
    List("This", "is", "also", "another", "sentence"),
    List("This", "is", "also", "yet", "another", "sentence", "that", "is", "reduntantly", "long"))

  val cells = content.map(_.map(x => Cell(x, Alignment.Center)))

  val table = TextTable.render(cells)

  table.foreach(println)

  val namedTuples = List(
    (name = "Alice", age = 25, city = "Oslo", height = 1.75, fortune = 100000),
    (name = "Bob", age = 34, city = "Bergen", height = 1.80, fortune = 2000000),
    (name = "Charles", age = 45, city = "London", height = 1.85, fortune = 3000))

  val ntTable = namedTuples.renderTable

  ntTable.foreach(println)