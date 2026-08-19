package no.vedaadata.text

import scala.util.*

import java.text.DecimalFormat
import java.time.format.DateTimeFormatter
import java.time.*

/**
  * A generalization of ways to format and parse primitive types like numbers and dates,
  * instead of using e.g. [[java.text.DecimalFormat]] or [[java.time.format.DateTimeFormatter]] directly.
  */
trait Format[A]:
  def format(x: A): String
  def parse(x: String): Try[A]

object Format:

  //  Numbers

  given defaultIntFormat: Format[Int] = new Format[Int]:
    def format(x: Int): String = x.toString
    def parse(x: String): Try[Int] = Try(x.toInt)

  def intFormatFromDecimalFormat(dcf: DecimalFormat): Format[Int] = new Format[Int]:
    def format(x: Int): String = dcf.format(x)
    def parse(x: String): Try[Int] = Try(dcf.parse(x).intValue)

  def intFormatFromDecimalFormatPattern(pattern: String): Format[Int] = 
    intFormatFromDecimalFormat(new DecimalFormat(pattern))

  given defaultLongFormat: Format[Long] = new Format[Long]:
    def format(x: Long): String = x.toString
    def parse(x: String): Try[Long] = Try(x.toLong)

  def longFormatFromDecimalFormat(dcf: DecimalFormat): Format[Long] = new Format[Long]:
    def format(x: Long): String = dcf.format(x)
    def parse(x: String): Try[Long] = Try(dcf.parse(x).longValue)

  def longFormatFromDecimalFormatPattern(pattern: String): Format[Long] =
    longFormatFromDecimalFormat(new DecimalFormat(pattern))

  given defaultFloatFormat: Format[Float] = new Format[Float]:
    def format(x: Float): String = x.toString
    def parse(x: String): Try[Float] = Try(x.toFloat)

  def floatFormatFromDecimalFormat(dcf: DecimalFormat): Format[Float] = new Format[Float]:
    def format(x: Float): String = dcf.format(x)
    def parse(x: String): Try[Float] = Try(dcf.parse(x).floatValue)

  def floatFormatFromDecimalFormatPattern(pattern: String): Format[Float] =
    floatFormatFromDecimalFormat(new DecimalFormat(pattern))

  given defaultDoubleFormat: Format[Double] = new Format[Double]:
    def format(x: Double): String = x.toString
    def parse(x: String): Try[Double] = Try(x.toDouble)

  def doubleFormatFromDecimalFormat(dcf: DecimalFormat): Format[Double] = new Format[Double]:
    def format(x: Double): String = dcf.format(x)
    def parse(x: String): Try[Double] = Try(dcf.parse(x).doubleValue)

  def doubleFormatFromDecimalFormatPattern(pattern: String): Format[Double] =
    doubleFormatFromDecimalFormat(new DecimalFormat(pattern))

  given defaultByteFormat: Format[Byte] = new Format[Byte]:
    def format(x: Byte): String = x.toString
    def parse(x: String): Try[Byte] = Try(x.toByte)

  def byteFormatFromDecimalFormat(dcf: DecimalFormat): Format[Byte] = new Format[Byte]:
    def format(x: Byte): String = dcf.format(x)
    def parse(x: String): Try[Byte] = Try(dcf.parse(x).byteValue)

  def byteFormatFromDecimalFormatPattern(pattern: String): Format[Byte] =
    byteFormatFromDecimalFormat(new DecimalFormat(pattern))
  
  given defaultShortFormat: Format[Short] = new Format[Short]:
    def format(x: Short): String = x.toString
    def parse(x: String): Try[Short] = Try(x.toShort)

  def shortFormatFromDecimalFormat(dcf: DecimalFormat): Format[Short] = new Format[Short]:
    def format(x: Short): String = dcf.format(x)
    def parse(x: String): Try[Short] = Try(dcf.parse(x).shortValue)

  def shortFormatFromDecimalFormatPattern(pattern: String): Format[Short] =
    shortFormatFromDecimalFormat(new DecimalFormat(pattern))

  given defaultBigIntFormat: Format[BigInt] = new Format[BigInt]:
    def format(x: BigInt): String = x.toString
    def parse(x: String): Try[BigInt] = Try(BigInt(x))

  def bigIntFormatFromDecimalFormat(dcf: DecimalFormat): Format[BigInt] = new Format[BigInt]:
    def format(x: BigInt): String = dcf.format(x)
    def parse(x: String): Try[BigInt] = Try(dcf.parse(x).asInstanceOf[java.math.BigDecimal].toBigInteger: BigInt)

  def bigIntFormatFromDecimalFormatPattern(pattern: String): Format[BigInt] =
    bigIntFormatFromDecimalFormat(new DecimalFormat(pattern) { setParseBigDecimal(true) })

  given defaultBigDecimalFormat: Format[BigDecimal] = new Format[BigDecimal]:
    def format(x: BigDecimal): String = x.toString
    def parse(x: String): Try[BigDecimal] = Try(BigDecimal(x))

  def bigDecimalFormatFromDecimalFormat(dcf: DecimalFormat): Format[BigDecimal] = new Format[BigDecimal]:
    def format(x: BigDecimal): String = dcf.format(x)
    def parse(x: String): Try[BigDecimal] = Try(dcf.parse(x).asInstanceOf[java.math.BigDecimal]: BigDecimal)

  def bigDecimalFormatFromDecimalFormatPattern(pattern: String): Format[BigDecimal] =
    bigDecimalFormatFromDecimalFormat(new DecimalFormat(pattern) { setParseBigDecimal(true) })
  
  //  Date and time

  given defaultLocalDateFormat: Format[LocalDate] = new Format[LocalDate]:
    def format(x: LocalDate): String = x.toString
    def parse(x: String): Try[LocalDate] = Try(LocalDate.parse(x))

  def localDateFormatFromDateTimeFormatter(df: DateTimeFormatter): Format[LocalDate] = new Format[LocalDate]:
    def format(x: LocalDate): String = df.format(x)
    def parse(x: String): Try[LocalDate] = Try(LocalDate.parse(x, df))

  def localDateFormatFromDateTimeFormatterPattern(pattern: String): Format[LocalDate] =
    localDateFormatFromDateTimeFormatter(DateTimeFormatter.ofPattern(pattern))

  given defaultLocalTimeFormat: Format[LocalTime] = new Format[LocalTime]:
    def format(x: LocalTime): String = x.toString
    def parse(x: String): Try[LocalTime] = Try(LocalTime.parse(x))

  def localTimeFormatFromDateTimeFormatter(df: DateTimeFormatter): Format[LocalTime] = new Format[LocalTime]:
    def format(x: LocalTime): String = df.format(x)
    def parse(x: String): Try[LocalTime] = Try(LocalTime.parse(x, df))

  def localTimeFormatFromDateTimeFormatterPattern(pattern: String): Format[LocalTime] =
    localTimeFormatFromDateTimeFormatter(DateTimeFormatter.ofPattern(pattern))

  given defaultLocalDateTimeFormat: Format[LocalDateTime] = new Format[LocalDateTime]:
    def format(x: LocalDateTime): String = x.toString
    def parse(x: String): Try[LocalDateTime] = Try(LocalDateTime.parse(x))

  def localDateTimeFormatFromDateTimeFormatter(df: DateTimeFormatter): Format[LocalDateTime] = new Format[LocalDateTime]:
    def format(x: LocalDateTime): String = df.format(x)
    def parse(x: String): Try[LocalDateTime] = Try(LocalDateTime.parse(x, df))

  def localDateTimeFormatFromDateTimeFormatterPattern(pattern: String): Format[LocalDateTime] =
    localDateTimeFormatFromDateTimeFormatter(DateTimeFormatter.ofPattern(pattern))