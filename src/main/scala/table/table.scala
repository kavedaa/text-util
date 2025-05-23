package no.vedaadata.text.table

import no.vedaadata.text.TextEncoder

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
    def apply[A](value: A)(using encoder: TextEncoder[A], alignment: AlignmentFactory[A]): Cell = 
      Cell(encoder.encode(value), alignment)

  enum Alignment:
    case Left, Center, Right

  opaque type AlignmentFactory[A] <: Alignment = Alignment

  object AlignmentFactory:
    given AlignmentFactory[Byte] = Alignment.Right
    given AlignmentFactory[Short] = Alignment.Right
    given AlignmentFactory[Int] = Alignment.Right
    given AlignmentFactory[Long] = Alignment.Right
    given AlignmentFactory[Float] = Alignment.Right
    given AlignmentFactory[Double] = Alignment.Right
    given AlignmentFactory[BigDecimal] = Alignment.Right
    given AlignmentFactory[BigInt] = Alignment.Right
    given [A](using inner: AlignmentFactory[A]): AlignmentFactory[Option[A]] = inner
    given [A]: AlignmentFactory[A] = Alignment.Left


