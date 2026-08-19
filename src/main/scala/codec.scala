package no.vedaadata.text

import scala.util.*

import java.time.*

trait TextEncoder[-A]:
  def encode(x: A): String

object TextEncoder:

  given optionEncoder[A](using inner: TextEncoder[A]): TextEncoder[Option[A]] with
    def encode(x: Option[A]) = x.map(inner.encode).getOrElse("")

  given stringEncoder: TextEncoder[String] with
    def encode(x: String) = x

  given charEncoder: TextEncoder[Char] with
    def encode(x: Char) = x.toString

  given booleanEncoder: TextEncoder[Boolean] with
    def encode(x: Boolean) = if x then "true" else "false"

  given fromFormat[A](using format: Format[A]): TextEncoder[A] with
    def encode(x: A) = format.format(x)

end TextEncoder

trait TextDecoder[+A]:
  def decode(x: String): Try[A]

object TextDecoder:

  given stringDecoder: TextDecoder[String] with
    def decode(x: String) = Success(x)

  given charDecoder: TextDecoder[Char] with
    def decode(x: String) = Try(x.charAt(0))

  given booleanDecoder: TextDecoder[Boolean] with
    def decode(x: String) = 
      if x == "true" then Success(true)
      else if x == "false" then Success(false )
      else Failure(IllegalArgumentException(s"Could not parse $x as Boolean"))

  given fromFormat[A](using format: Format[A]): TextDecoder[A] with
    def decode(x: String) = format.parse(x)

end TextDecoder