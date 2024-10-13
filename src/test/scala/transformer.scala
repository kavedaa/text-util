package no.vedaadata.text

import org.scalatest.featurespec._
import org.scalatest.matchers.should._

class LabelTransformerTest extends AnyFeatureSpec with Matchers:

  import LabelTransformer.*

  Feature("from natural case"):

    Scenario("composite"):
      FromNaturalCase("Company office address") shouldEqual Array("company", "office", "address")

    Scenario("empty"):
      FromNaturalCase("") shouldEqual Array[String]()

    Scenario("single word"):
      FromNaturalCase("name") shouldEqual Array("name")

    Scenario("single word uppercase"):
      FromNaturalCase("Name") shouldEqual Array("name")


  Feature("from camel case"):

    Scenario("composite"):
      FromCamelCase("companyOfficeAddress") shouldEqual Array("company", "office", "address")

    Scenario("empty"):
      FromCamelCase("") shouldEqual Array[String]()

    Scenario("single word"):
      FromCamelCase("name") shouldEqual Array("name")


  Feature("from pascal case"):

    Scenario("composite"):
      FromPascalCase("CompanyOfficeAddress") shouldEqual Array("company", "office", "address")

    Scenario("empty"):
      FromPascalCase("") shouldEqual Array[String]()

    Scenario("single word"):
      FromPascalCase("Name") shouldEqual Array("name")


  Feature("from snake case"):

    Scenario("composite"):
      FromSnakeCase("company_office_address") shouldEqual Array("company", "office", "address")

    Scenario("empty"):
      FromSnakeCase("") shouldEqual Array[String]()

    Scenario("single word"):
      FromSnakeCase("name") shouldEqual Array("name")


  Feature("from kebab case"):

    Scenario("composite"):
      FromKebabCase("company-office-address") shouldEqual Array("company", "office", "address")

    Scenario("empty"):
      FromKebabCase("") shouldEqual Array[String]()

    Scenario("single word"):
      FromKebabCase("name") shouldEqual Array("name")


  Feature("to natural case"):

    Scenario("composite"):
      ToNaturalCase(Array("company", "office", "address")) shouldEqual "Company office address"

    Scenario("empty"):
      ToNaturalCase(Array[String]()) shouldEqual ""

    Scenario("single word"):
      ToNaturalCase(Array("name")) shouldEqual "Name"


  Feature("to camel case"):

    Scenario("composite"):
      ToCamelCase(Array("company", "office", "address")) shouldEqual "companyOfficeAddress"

    Scenario("empty"):
      ToCamelCase(Array[String]()) shouldEqual ""

    Scenario("single word"):
      ToCamelCase(Array("name")) shouldEqual "name"


  Feature("to pascal case"):

    Scenario("composite"):
      ToPascalCase(Array("company", "office", "address")) shouldEqual "CompanyOfficeAddress"

    Scenario("empty"):
      ToPascalCase(Array[String]()) shouldEqual ""

    Scenario("single word"):
      ToPascalCase(Array("name")) shouldEqual "Name"


  Feature("to snake case"):

    Scenario("composite"):
      ToSnakeCase(Array("company", "office", "address")) shouldEqual "company_office_address"

    Scenario("empty"):
      ToSnakeCase(Array[String]()) shouldEqual ""

    Scenario("single word"):
      ToSnakeCase(Array("name")) shouldEqual "name"


  Feature("to kebab case"):

    Scenario("composite"):
      ToKebabCase(Array("company", "office", "address")) shouldEqual "company-office-address"

    Scenario("empty"):
      ToKebabCase(Array[String]()) shouldEqual ""

    Scenario("single word"):
      ToKebabCase(Array("name")) shouldEqual "name"

