package no.vedaadata.text

import scala.util.*
import scala.deriving.*
import scala.compiletime.*

trait Show[-A]:
  def tree(x: A)(using Show.EmptyPolicy): Show.Tree

object Show:

  opaque type Indentation >: Int = Int

  given Indentation = 2

  enum EmptyPolicy:
    case Show
    case Hide

  object EmptyPolicy:
    given EmptyPolicy = EmptyPolicy.Show

  sealed trait Tree:
    def withLabel(label: String): Tree

  object Tree:

    case class Leaf(item: String, label: Option[String] = None) extends Tree:
      def withLabel(label: String) = copy(label = Some(label))

    case class Node(name: String, items: List[Tree], label: Option[String] = None) extends Tree:
      def withLabel(label: String) = copy(label = Some(label))

    case object Empty extends Tree:
      def withLabel(label: String) = Empty

    private def indent(x: String, indentationLevel: Int)(using indentation: Indentation) =
      " " * indentationLevel * indentation + x

    private def renderLabeled(label: Option[String], x: String) =
      Seq(label, Some(x)).flatten.mkString(" = ")

    def render(tree: Tree, indentationLevel: Int = 0)(using Indentation): Option[String] =
      tree match
        case Tree.Leaf(item, label) => 
          Some(indent(renderLabeled(label, item), indentationLevel))
        case Tree.Node(name, items, label) => 
          val labelNameRender = indent(renderLabeled(label, name), indentationLevel)
          val subRenders = items.flatMap { x => render(x, indentationLevel + 1) }
          Some((labelNameRender :: subRenders).mkString(System.lineSeparator))
        case Empty =>
          None

  end Tree

  given default[A]: Show[A] with
    def tree(x: A)(using EmptyPolicy) = Tree.Leaf(x.toString)    

  given Show[String] with
    def tree(x: String)(using EmptyPolicy) = Tree.Leaf("\"" + x + "\"")

  given [A : Show]: Show[Option[A]] with
    def tree(x: Option[A])(using policy: EmptyPolicy) = x match
      case Some(value) => 
        summon[Show[A]].tree(value)
      case None => 
        policy match
          case EmptyPolicy.Show => Tree.Leaf("?")
          case EmptyPolicy.Hide => Tree.Empty

  given [A : Show]: Show[Try[A]] with
    def tree(x: Try[A])(using policy: EmptyPolicy) = x match
      case Success(value) => 
        summon[Show[A]].tree(value)
      case Failure(ex) => 
        policy match
          case EmptyPolicy.Show => Tree.Leaf(ex.getMessage)
          case EmptyPolicy.Hide => Tree.Empty

  given [A] (using inner: Show[A]): Show[Iterable[A]] with
    def tree(xs: Iterable[A])(using policy: EmptyPolicy) = 
      if xs.nonEmpty then 
        Tree.Node("", xs.map(inner.tree).toList)
      else policy match
        case EmptyPolicy.Show => Tree.Leaf("[]")
        case EmptyPolicy.Hide => Tree.Empty

  inline def derived[A <: Product](using m: Mirror.ProductOf[A]): Show[A] = 
    type Shows = Tuple.Map[m.MirroredElemTypes, Show]
    val shows = summonAll[Shows].toList.asInstanceOf[List[Show[Any]]]
    val name = constValue[m.MirroredLabel]
    val elemLabels = constValueTuple[m.MirroredElemLabels].toList.asInstanceOf[List[String]]
    new Show[A]:
      def tree(x: A)(using EmptyPolicy) =
        val elems = x.productIterator.toList
        val items = shows.zip(elems).zip(elemLabels) map { case ((show, elem), elemLabel) => show.tree(elem).withLabel(elemLabel) }
        Tree.Node(name + ":", items)

  inline given [A <: Product](using m: Mirror.ProductOf[A]): Show[A] = derived

  def proxy[A, B](f: A => B)(using show: Show[B]) = new Show[A]:
    def tree(x: A)(using EmptyPolicy) = show.tree(f(x))

end Show

extension [A](x: A) def show(using s: Show[A])(using Show.Indentation)(using Show.EmptyPolicy): String =
  Show.Tree.render(s.tree(x)).getOrElse("(empty)")