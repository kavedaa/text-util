package no.vedaadata.text.table

object TextTableRenderer:

  val VerticalLineChar = '|'
  val HorizontalLineChar = '-'
  val HeaderHorizontalLineChar = '='
  val CrossChar = '+'
  val OverflowChar = '#'
  val Padding = " "

  def render(textTable: TextTable): List[String] =
    if textTable.rows.isEmpty then Nil
    else
      val size = textTable.rows.map(_.size).max
      val lifted = textTable.rows.map(_.lift)
      val widths = (0 until size).toList.map: index =>
        val column = lifted.map(_.apply(index))
        column.flatMap(_.map(cell => paddedText(cell).length)).max
      val lines = lifted.map: xs =>
        val alignedTexts = (0 until size).map: index =>
          val cell = xs(index)
          val width = widths(index)
          cell.map(c => alignedIn(c, width)).getOrElse(" " * width)
        VerticalLineChar + alignedTexts.mkString(VerticalLineChar.toString) + VerticalLineChar
      val totalWidth = widths.sum + widths.size + 1
      val horizontalLine = HorizontalLineChar.toString * totalWidth
      val headerHorizontalLine = HeaderHorizontalLineChar.toString * totalWidth
      //  not sure what is the best kind of horizontal line
      //  val horizontalLine = widths.map(w => CrossChar.toString + HorizontalLineChar.toString * w).mkString + CrossChar.toString
      if textTable.hasHeader then
        val (headerLine, otherLines) = (lines.head, lines.tail)
        List(List(horizontalLine, headerLine, headerHorizontalLine), otherLines.flatMap(line => List(line, horizontalLine))).flatten
      else 
        List(List(horizontalLine), lines.flatMap(line => List(line, horizontalLine))).flatten  

  private def paddedText(cell: TextTable.Cell) = Padding + cell.text + Padding 

  private def fittedIn(cell: TextTable.Cell, width: Int): String =
    val space = width - cell.text.length
    if space < 0 then OverflowChar.toString * width
    else cell.text
      
  private def alignedIn(cell: TextTable.Cell, width: Int): String =
    val fittedText = fittedIn(cell, width)
    val space = width - paddedText(cell).length
    import TextTable.Alignment
    cell.alignment match
      case Alignment.Left =>
        paddedText(cell) + " " * space
      case Alignment.Right =>
        " " * space + paddedText(cell)
      case Alignment.Center =>  
        val leftSpace = space / 2
        val rightSpace = space - leftSpace
        " " * leftSpace + paddedText(cell) + " " * rightSpace   


