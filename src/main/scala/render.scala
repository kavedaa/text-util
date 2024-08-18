package no.vedaadata.text

trait Render[A]:
  outer =>
    
  def apply(x: A): Option[String]

  def andThen(g: String => String): Render[A] = 
    new Render:
      def apply(x: A) = outer(x).map(g)

object Render:

  given default[A](using render: A => String): Render[A] with
    def apply(x: A) = Some(render(x))

  given fromTextEncoder[A](using textEncoder: TextEncoder[A]): Render[A] with
    def apply(x: A) = Some(textEncoder.encode(x))

  given [A](using inner: Render[A]): Render[Option[A]] with
    def apply(x: Option[A]) = x.flatMap(inner.apply)

  //  special

  given Render[Unit] with
    def apply(x: Unit) = None

  given Render[java.io.File] with
    def apply(x: java.io.File) = Some(x.getName)
