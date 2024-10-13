package no.vedaadata.text

import scala.collection.mutable.ArrayBuffer

type Case

type NaturalCase <: Case
type CamelCase <: Case
type PascalCase <: Case
type SnakeCase <: Case
type KebabCase <: Case

trait LabelTransformer extends (String => String):
  def from(x: String): Array[String]
  def to(xs: Array[String]): String
  def apply(x: String): String = to(from(x))  

object LabelTransformer:

  trait From[C <: Case] extends (String => Array[String])
  trait To[C <: Case] extends (Array[String] => String)

  def apply[FC <: Case, TC <: Case](using from0: From[FC], to0: To[TC]) = 
    new LabelTransformer:
      def from(x: String) = from0(x)
      def to(xs: Array[String]) = to0(xs)

  given default: LabelTransformer = LabelTransformer[CamelCase, NaturalCase]

  given From[NaturalCase] = FromNaturalCase
  given From[CamelCase] = FromCamelCase
  given From[PascalCase] = FromPascalCase
  given From[SnakeCase] = FromSnakeCase
  given From[KebabCase] = FromKebabCase

  given To[NaturalCase] = ToNaturalCase
  given To[CamelCase] = ToCamelCase
  given To[PascalCase] = ToPascalCase
  given To[SnakeCase] = ToSnakeCase
  given To[KebabCase] = ToKebabCase

  object FromNaturalCase extends From[NaturalCase]:
    def apply(s: String) = splitOn(s, ' ').map(_.toLowerCase)

  object FromCamelCase extends From[CamelCase]:
    def apply(s: String) = 
      val res = new ArrayBuffer[String]
      val sb = new StringBuilder
      val length = s.length
      var currIndex: Int = 0
      var prevChar: Char = 0
      while currIndex < length do
        val currChar = s.charAt(currIndex)
        sb.append(currChar.toLower)
        val nextIndex = currIndex + 1
        val isBoundary = (nextIndex == length) || (s.charAt(nextIndex).isUpper)
        if isBoundary then
          if prevChar.isLower then
            res.append(sb.toString)
            sb.clear
        prevChar = currChar
        currIndex = nextIndex
      end while
      res.toArray

  object FromPascalCase extends From[PascalCase]:
    def apply(s: String) = FromCamelCase(s)

  object FromSnakeCase extends From[SnakeCase]:
    def apply(s: String) = splitOn(s, '_')

  object FromKebabCase extends From[KebabCase]:
    def apply(s: String) = splitOn(s, '-')

  private def splitOn(s: String, c: Char) =
    s.split(c).map(_.trim).filterNot(_.isEmpty)

  object ToNaturalCase extends To[NaturalCase]:
    def apply(xs: Array[String]): String =
      xs.map(_.toLowerCase).mkString(" ").capitalize

  object ToCamelCase extends To[CamelCase]:
    def apply(xs: Array[String]): String =
      if xs.isEmpty then "" else (xs.head +: xs.tail.map(_.capitalize)).mkString

  object ToPascalCase extends To[PascalCase]:
    def apply(xs: Array[String]): String =
      xs.map(_.capitalize).mkString

  object ToSnakeCase extends To[SnakeCase]:
    def apply(xs: Array[String]): String =
      xs.map(_.toLowerCase).mkString("_")

  object ToKebabCase extends To[KebabCase]:
    def apply(xs: Array[String]): String =
      xs.map(_.toLowerCase).mkString("-")