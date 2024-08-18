package no.vedaadata.text

import scala.util.*

import java.time.*

trait TextEncoder[-A]:
  def encode(x: A): String

object TextEncoder:

  given [A](using inner: TextEncoder[A]): TextEncoder[Option[A]] with
    def encode(x: Option[A]) = x.map(inner.encode).getOrElse("")

  given TextEncoder[String] with
    def encode(x: String) = x

  given TextEncoder[Char] with
    def encode(x: Char) = x.toString

  given (using format: Format.BooleanFormat): TextEncoder[Boolean] with
    def encode(x: Boolean) = format.formatBoolean(x)

  given (using format: Format.ByteFormat): TextEncoder[Byte] with
    def encode(x: Byte) = format.formatByte(x)      

  given (using format: Format.ShortFormat): TextEncoder[Short] with
    def encode(x: Short) = format.formatShort(x)      

  given (using format: Format.IntFormat): TextEncoder[Int] with 
    def encode(x: Int) = format.formatInt(x)

  given (using format: Format.LongFormat): TextEncoder[Long] with
    def encode(x: Long) = format.formatLong(x)
      
  given (using format: Format.FloatFormat): TextEncoder[Float] with
    def encode(x: Float) = format.formatFloat(x)

  given (using format: Format.DoubleFormat): TextEncoder[Double] with
    def encode(x: Double) = format.formatDouble(x)

  given (using format: Format.BigIntFormat): TextEncoder[BigInt] with
    def encode(x: BigInt) = format.formatBigInt(x)

  given (using format: Format.BigDecimalFormat): TextEncoder[BigDecimal] with
    def encode(x: BigDecimal) = format.formatBigDecimal(x)

  given (using formatter: Format.DateFormatter): TextEncoder[LocalDate] with
    def encode(x: LocalDate) = formatter.formatDate(x)

  given (using formatter: Format.TimeFormatter): TextEncoder[LocalTime] with
    def encode(x: LocalTime) = formatter.formatTime(x)

  given (using formatter: Format.DateTimeFormatter): TextEncoder[LocalDateTime] with
    def encode(x: LocalDateTime) = formatter.formatDateTime(x)

end TextEncoder

trait TextDecoder[+A]:
  def decode(x: String): Try[A]

object TextDecoder:

  given TextDecoder[String] with
    def decode(x: String) = Success(x)

  given TextDecoder[Char] with
    def decode(x: String) = Try(x.charAt(0))

  given (using format: Format.BooleanFormat): TextDecoder[Boolean] with
    def decode(x: String) = Try(format.parseBoolean(x))

  given (using format: Format.ByteFormat): TextDecoder[Byte] with
    def decode(x: String) = Try(format.parseByte(x))

  given (using format: Format.ShortFormat): TextDecoder[Short] with
    def decode(x: String) = Try(format.parseShort(x))      

  given (using format: Format.IntFormat): TextDecoder[Int] with 
    def decode(x: String) = Try(format.parseInt(x))

  given (using format: Format.LongFormat): TextDecoder[Long] with
    def decode(x: String) = Try(format.parseLong(x))
      
  given (using format: Format.FloatFormat): TextDecoder[Float] with
    def decode(x: String) = Try(format.parseFloat(x))

  given (using format: Format.DoubleFormat): TextDecoder[Double] with
    def decode(x: String) = Try(format.parseDouble(x))

  given (using format: Format.BigIntFormat): TextDecoder[BigInt] with
    def decode(x: String) = Try(format.parseBigInt(x))

  given (using format: Format.BigDecimalFormat): TextDecoder[BigDecimal] with
    def decode(x: String) = Try(format.parseBigDecimal(x))

  given (using formatter: Format.DateFormatter): TextDecoder[LocalDate] with
    def decode(x: String) = Try(formatter.parseDate(x))

  given (using formatter: Format.TimeFormatter): TextDecoder[LocalTime] with
    def decode(x: String) = Try(formatter.parseTime(x))

  given (using formatter: Format.DateTimeFormatter): TextDecoder[LocalDateTime] with
    def decode(x: String) = Try(formatter.parseDateTime(x))

end TextDecoder