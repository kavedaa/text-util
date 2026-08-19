package no.vedaadata.text

trait Render[A]:
  outer =>
    
  def apply(x: A): Option[String]

  def andThen(g: String => String): Render[A] = 
    new Render:
      def apply(x: A) = outer(x).map(g)

object Render:

  given default[A]: Render[A] with
    def apply(x: A) = Some(x.toString)

  given from[A](using f: A => String): Render[A] with
    def apply(x: A) = Some(f(x))

  // given fromFormat[A](using format: Format[A]): Render[A] with
  //   def apply(x: A) = Some(format.format(x))

  // given fromTextEncoder[A](using textEncoder: TextEncoder[A]): Render[A] with
  //   def apply(x: A) = Some(textEncoder.encode(x))

  given option[A](using inner: Render[A])(using noneRender: Render[None.type]): Render[Option[A]] with
    def apply(x: Option[A]) = x match
      case Some(value) => inner(value)
      case None        => noneRender(None)

  given none: Render[None.type] with
    def apply(x: None.type) = None

  //  special

  given Render[Unit] with
    def apply(x: Unit) = None

  given Render[java.io.File] with
    def apply(x: java.io.File) = Some(x.getName)

  given Render[java.nio.file.Path] with
    def apply(x: java.nio.file.Path) = Some(x.toString)