package no.vedaadata.text.table

import scala.deriving.*
import scala.compiletime.*

import no.vedaadata.text.*

trait TextTableEncoder[-A]:
  def encode(xs: Iterable[A]): TextTable

object TextTableEncoder:

  class DerivedTextTableEncoder[A](f: A => List[Any], labels: List[String], textEncoders: List[TextEncoder[Any]], alignments: List[TextTable.AlignmentFactory[Any]])
    extends TextTableEncoder[A]:
    def encode(xs: Iterable[A]): TextTable = 
      val headerRow = labels.map(label => TextTable.Cell(label, TextTable.Alignment.Left))
      val dataRows = xs.map: x =>
        f(x).zip(textEncoders).zip(alignments).map: 
          case ((value, encoder), alignment) =>
            TextTable.Cell(encoder.encode(value), alignment)
      new TextTable(headerRow :: dataRows.toList, hasHeader = true)

  inline def derived[P <: Product](using m: Mirror.ProductOf[P]): TextTableEncoder[P] =
    val labels = constValueTuple[m.MirroredElemLabels].toList.asInstanceOf[List[String]]
    val textEncoders = summonAll[Tuple.Map[m.MirroredElemTypes, TextEncoder]].toList.asInstanceOf[List[TextEncoder[Any]]]
    val alignments = summonAll[Tuple.Map[m.MirroredElemTypes, TextTable.AlignmentFactory]].toList.asInstanceOf[List[TextTable.AlignmentFactory[Any]]]
    new DerivedTextTableEncoder[P](_.productIterator.toList, labels, textEncoders, alignments)

  inline given [P <: Product](using m: Mirror.ProductOf[P]): TextTableEncoder[P] = derived[P]
