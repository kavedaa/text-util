package no.vedaadata.text

enum Alignment:
    case Left, Center, Right

case class Align[A](alignment: Alignment)

object Align:

  given default[A]: Align[A] = Align(Alignment.Left)

  given byte: Align[Byte] = Align(Alignment.Right)
  given short: Align[Short] = Align(Alignment.Right)
  given int: Align[Int] = Align(Alignment.Right)
  given long: Align[Long] = Align(Alignment.Right)
  given float: Align[Float] = Align(Alignment.Right)
  given double: Align[Double] = Align(Alignment.Right)
  given bigDecimal: Align[BigDecimal] = Align(Alignment.Right)
  given bigInt: Align[BigInt] = Align(Alignment.Right)

  given option[A](using inner: Align[A]): Align[Option[A]] = Align(inner.alignment)


