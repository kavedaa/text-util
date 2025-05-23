package no.vedaadata.text

import java.time.*
import java.time.temporal.TemporalAccessor

  //  Ensure we can use different givens for same underlying types

object Format:

  trait BooleanFormat:
    def formatBoolean(x: Boolean): String
    def parseBoolean(x: String): Boolean

  object BooleanFormat:
    given default: BooleanFormat with
      def formatBoolean(x: Boolean) = if x then "true" else "false"
      def parseBoolean(x: String) = if x == "true" then true else if x == "false" then false else throw new IllegalArgumentException(s"Could not parse $x as Boolean")

  opaque type ByteFormat >: java.text.DecimalFormat = java.text.DecimalFormat

  object ByteFormat:
    extension (f: ByteFormat) def formatByte(x: Byte) = f.format(x)
    extension (f: ByteFormat) def parseByte(x: String) = f.parse(x).byteValue
    given default: ByteFormat = new java.text.DecimalFormat

  opaque type ShortFormat >: java.text.DecimalFormat = java.text.DecimalFormat

  object ShortFormat:
    extension (f: ShortFormat) def parseShort(x: String) = f.parse(x).shortValue
    extension (f: ShortFormat) def formatShort(x: Short) = f.format(x)
    given default: ShortFormat = new java.text.DecimalFormat

  opaque type IntFormat >: java.text.DecimalFormat = java.text.DecimalFormat

  object IntFormat:
    extension (f: IntFormat) def parseInt(x: String) = f.parse(x).intValue
    extension (f: IntFormat) def formatInt(x: Int) = f.format(x)
    given default: IntFormat = new java.text.DecimalFormat

  opaque type LongFormat >: java.text.DecimalFormat = java.text.DecimalFormat

  object LongFormat:
    extension (f: LongFormat) def formatLong(x: Long) = f.format(x)
    extension (f: LongFormat) def parseLong(x: String) = f.parse(x).longValue
    given default: LongFormat = new java.text.DecimalFormat

  opaque type FloatFormat >: java.text.DecimalFormat = java.text.DecimalFormat

  object FloatFormat:
    extension (f: FloatFormat) def formatFloat(x: Float) = f.format(x)
    extension (f: FloatFormat) def parseFloat(x: String) = f.parse(x).floatValue
    given default: FloatFormat = new java.text.DecimalFormat

  opaque type DoubleFormat >: java.text.DecimalFormat = java.text.DecimalFormat

  object DoubleFormat:
    extension (f: DoubleFormat) def formatDouble(x: Double) = f.format(x)
    extension (f: DoubleFormat) def parseDouble(x: String) = f.parse(x).doubleValue
    given default: DoubleFormat = new java.text.DecimalFormat

  //  note that if a custom format is created, it needs to include the `setParseBigDecimal` call, otherwise parsing will fail
  //  also that we go through an intermediate BigDecimal since there is no support for BigInteger on DecimalFormat
  opaque type BigIntFormat >: java.text.DecimalFormat = java.text.DecimalFormat

  object BigIntFormat:
    extension (f: BigIntFormat) def formatBigInt(x: BigInt) = f.format(x)
    extension (f: BigIntFormat) def parseBigInt(x: String) = f.parse(x).asInstanceOf[java.math.BigDecimal].toBigInteger: BigInt
    given default: BigIntFormat = new java.text.DecimalFormat { setParseBigDecimal(true) }

  //  note that if a custom format is created, it needs to include the `setParseBigDecimal` call, otherwise parsing will fail
  opaque type BigDecimalFormat >: java.text.DecimalFormat = java.text.DecimalFormat

  object BigDecimalFormat:
    extension (f: BigDecimalFormat) def formatBigDecimal(x: BigDecimal) = f.format(x)    
    extension (f: BigDecimalFormat) def parseBigDecimal(x: String) = f.parse(x).asInstanceOf[java.math.BigDecimal]: BigDecimal
    given default: BigDecimalFormat = new java.text.DecimalFormat { setParseBigDecimal(true) }

  opaque type DateFormatter >: java.time.format.DateTimeFormatter = java.time.format.DateTimeFormatter

  object DateFormatter:
    extension (f: DateFormatter) def formatDate(x: TemporalAccessor) = f.format(x)
    extension (f: DateFormatter) def parseDate(x: String) = LocalDate.parse(x, f)
    given default: DateFormatter = java.time.format.DateTimeFormatter.ISO_DATE

  opaque type TimeFormatter >: java.time.format.DateTimeFormatter = java.time.format.DateTimeFormatter

  object TimeFormatter:
    extension (f: TimeFormatter) def formatTime(x: TemporalAccessor) = f.format(x)
    extension (f: TimeFormatter) def parseTime(x: String) = LocalTime.parse(x, f)
    given default: TimeFormatter = java.time.format.DateTimeFormatter.ISO_TIME

  opaque type DateTimeFormatter >: java.time.format.DateTimeFormatter = java.time.format.DateTimeFormatter
  
  object DateTimeFormatter:
    extension (f: DateTimeFormatter) def formatDateTime(x: TemporalAccessor) = f.format(x)
    extension (f: DateTimeFormatter) def parseDateTime(x: String) = LocalDateTime.parse(x, f)
    given default: DateTimeFormatter = java.time.format.DateTimeFormatter.ISO_DATE_TIME

