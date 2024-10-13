package demo.table

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