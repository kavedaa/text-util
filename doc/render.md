## Render

The purpose of the `Render[A]` typeclass is to provide a canonical string representation of an instance of a class `A`.

This is similar to the purpose of `.toString`. However there are multiple reasons to use a separate mechanism for this, instead of overriding `.toString`:

* One does not necessarily have access to modify the class `A` (such that if it's provided by a library).
* `.toString`could provide a low-level technical representation, while `Render` would provide a user-facing representation.

Here's a simple example. Suppose the following class:

```scala
case class Person(name: String, age: Int)
```

and an instance of it:

```scala
val bob = Person("Bob", 35)
```

Calling `bob.toString` would give `Person("Bob", 35)`. This is useful for e.g. debugging, but in a user-facing context we might want to display e.g. just the name.

For this we could define a `Render` instance:

```scala
given Render[Person] with
  def apply(x: Person) = Some(x.name)
```

Now if we suppose a library-function like this simple example:

```scala
def print[A](value: A)(using render: Render[A]) =
  render(value).foreach(println)
```

Then we can call `print(bob)` and it will give `"Bob"`.

In other words, this allows libraries to create text representations of any object, as long as they require that a `Render[A]` is available.

One might not want to depend on the `Render[A]` class at the place where `A` is defined, though, as this might make an entire codebase hard-wired to use `Render`.

Instead, a more generic way is to define an `(A => String)` instance instead of a `Render[A]` instance:

```scala
object Person:
  given (Person => String) = _.name
```

This will automatically be turned into a `Render[A]` instance anywhere that is expected.

Hence, if one expects to use a library that uses `Render[A]`, a good practice would be to include a `given (A => String)` in the companion object of `A`.

As such `given (A => String)` could be seen as a general way of creating a string representation of `A` that can be used for any purpose, not just by `Render[A]`, as a replacement for overriding `.toString`.

Now one could ask, why use `Render[A]` at all, instead of just having generic library methods just requiring `(A => String)`?

The reason for this is that having a dedicated `Render` class provides a place for having pre-defined `given` instances, such as for built-in classes like `File` or for container classes like `Option`.

