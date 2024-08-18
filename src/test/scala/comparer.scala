package no.vedaadata.text

import org.scalatest.featurespec._
import org.scalatest.matchers.should._

class StringComparerTest extends AnyFeatureSpec with Matchers:

  Feature("basic") {

    val comparer = new StringComparer

    Scenario("equal") {
      comparer.compare("foo", "foo") shouldBe true
    }

    Scenario("not equal") {
      comparer.compare("foo", "FOO") shouldBe false
    }
  }
    
  Feature("case insensitive") {

    val comparer = new StringComparer with CaseInsensitive

    Scenario("equal") {
      comparer.compare("foo", "foo") shouldBe true
      comparer.compare("foo", "FOO") shouldBe true
    }
  }

  Feature("transform line breaks") {

    val comparer = new StringComparer with TransformLineBreaksToWhiteSpace

    Scenario("equal") {
      comparer.compare("foo bar", "foo bar") shouldBe true
      comparer.compare("foo bar", "foo\nbar") shouldBe true
      comparer.compare("foo bar", "foo\rbar") shouldBe true
      comparer.compare("foo bar", "foo\r\nbar") shouldBe true
      comparer.compare("foo bar", "foo\n\rbar") shouldBe true
      comparer.compare("foo bar", "foo\r\n\rbar") shouldBe true
      comparer.compare("foo bar", "foo\n\r\nbar") shouldBe true
      comparer.compare("foo bar", "foo\r\n\r\nbar") shouldBe true
      comparer.compare("foo bar", "foo\n\r\n\rbar") shouldBe true
    }

    Scenario("not equal") {
      comparer.compare("foo bar", "foo\n bar") shouldBe false
    }
  }

  Feature("normalize whitespace") {

    val comparer = new StringComparer with NormalizeWhitespace

    Scenario("equal") {
      comparer.compare("foo bar", "foo bar") shouldBe true
      comparer.compare("foo bar", "foo  bar") shouldBe true
    }

    Scenario("not equal") {
      comparer.compare("foobar", "foo bar") shouldBe false
    }
  }

  Feature("trim whitespace") {

    val comparer = new StringComparer with TrimWhitespace

    Scenario("equal") {
      comparer.compare("foobar", "foo bar") shouldBe true
      comparer.compare("foobar", "foo  bar") shouldBe true
      comparer.compare("foobar", " foo bar ") shouldBe true
    }

    Scenario("not equal") {
      comparer.compare("foo bar", "foo\nbar") shouldBe false
    }
  }
      
