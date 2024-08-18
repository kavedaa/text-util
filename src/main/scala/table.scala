package no.vedaadata.text

object TextTable:

  val VerticalLineChar = '|'
  val HorizontalLineChar = '-'
  val HeaderHorizontalLineChar = '='
  val CrossChar = '+'
  val OverflowChar = '#'
  val Padding = " "

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

  case class Cell(text: String, alignment: Alignment):

    val paddedText = Padding + text + Padding 

    def fittedIn(width: Int): String =
      val space = width - text.length
      if space < 0 then OverflowChar.toString * width
      else text
        
    def alignedIn(width: Int): String =
      val fittedText = fittedIn(width)
      val space = width - paddedText.length
      alignment match
        case Alignment.Left =>
          paddedText + " " * space
        case Alignment.Right =>
          " " * space + paddedText
        case Alignment.Center =>  
          val leftSpace = space / 2
          val rightSpace = space - leftSpace
          " " * leftSpace + paddedText + " " * rightSpace   

  def render(xss: List[List[Cell]], hasHeader: Boolean = false): List[String] =
    if xss.isEmpty then Nil
    else
      val size = xss.map(_.size).max
      val lifted = xss.map(_.lift)
      val widths = (0 until size).toList.map: index =>
        val column = lifted.map(_.apply(index))
        column.flatMap(_.map(_.paddedText.length)).max
      val lines = lifted.map: xs =>
        val alignedTexts = (0 until size).map: index =>
          val cell = xs(index)
          val width = widths(index)
          cell.map(_.alignedIn(width)).getOrElse(" " * width)
        VerticalLineChar + alignedTexts.mkString(VerticalLineChar.toString) + VerticalLineChar
      val totalWidth = widths.sum + widths.size + 1
      val horizontalLine = HorizontalLineChar.toString * totalWidth
      val headerHorizontalLine = HeaderHorizontalLineChar.toString * totalWidth
      //  not sure what is the best kind of horizontal line
      //  val horizontalLine = widths.map(w => CrossChar.toString + HorizontalLineChar.toString * w).mkString + CrossChar.toString
      if hasHeader then
        val (headerLine, otherLines) = (lines.head, lines.tail)
        List(List(horizontalLine, headerLine, headerHorizontalLine), otherLines.flatMap(line => List(line, horizontalLine))).flatten
      else 
        List(List(horizontalLine), lines.flatMap(line => List(line, horizontalLine))).flatten