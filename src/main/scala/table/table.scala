package no.vedaadata.text.table

import no.vedaadata.text.*

class TextTable(val rows: List[List[TextTable.Cell]], val hasHeader: Boolean = false):
  def render: List[String] = TextTableRenderer.render(this)
  def renderAndPrint(): Unit = render.foreach(println)

object TextTable:

  def render[A](xs: Iterable[A])(using encoder: TextTableEncoder[A]): List[String] =
    encoder.encode(xs).render

  def renderAndPrint[A](xs: Iterable[A])(using encoder: TextTableEncoder[A]): Unit =
    render(xs).foreach(println)    

  case class Cell(text: String, alignment: Alignment)

  object Cell:
    def apply[A](value: A)(using encoder: TextEncoder[A], align: Align[A]): Cell = 
      Cell(encoder.encode(value), align.alignment)