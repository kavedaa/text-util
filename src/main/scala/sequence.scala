package no.vedaadata.text

object TextSequence:

  def encodeHeaderAndLines[A](xs: Iterable[A])(layout: Layout[A]) =
    layout.labels :: xs.toList.map(layout.encode)

  class Layout[A](val columns: Layout.Column[A, ?]*):
    def encode(x: A): List[String] = columns.toList.map(_.encode(x))
    def labels: List[String] = columns.toList.map(_.label)

  object Layout:

    case class Column[A, B](label: String, f: A => B)(using val encoder: TextEncoder[B]):
      def encode(x: A): String = encoder.encode(f(x))
