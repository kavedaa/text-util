package demo.render

import no.vedaadata.text.Render

case class Person(name: String, age: Int)

object Person:
  given (Person => String) =
    p => s"${p.name} is ${p.age} years old"

@main def main =

  // helper functions for demonstration

  def print[A](value: A)(using render: Render[A]) =
    render(value).foreach(println)

  def printAll[A](values: Seq[A])(using render: Render[A]) =
    values.map(render(_)).foreach:
      case Some(str) => println(str)
      case None      => println("<<no render>>")

  // default renders

  print("Hello, World!")
  print(42)

  // option

  print(Some(42))

  // override the render for None

  given Render[None.type] =
    _ => Some("<<there is no spoon>>")

  print(None)

  // custom render for Person

  val persons = List(
    Person("Alice", 30),
    Person("Bob", 25),
    Person("Charlie", 35))

  printAll(persons)