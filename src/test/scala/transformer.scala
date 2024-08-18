package no.vedaadata.text

import org.scalatest.featurespec._
import org.scalatest.matchers.should._

class TransformerTest extends AnyFeatureSpec with Matchers:

  import LabelTransformer.fromCamelCase

  Feature("from camel case") {

    Scenario("common") {
      fromCamelCase("isTopEmployee") shouldEqual "Is top employee"
    }

    Scenario("empty input") {
      fromCamelCase("") shouldEqual ""
    }

    Scenario("single word") {
      fromCamelCase("name") shouldEqual "Name"
    }

    Scenario("single word uppercase") {
      fromCamelCase("Name") shouldEqual "Name"
    }

  }
