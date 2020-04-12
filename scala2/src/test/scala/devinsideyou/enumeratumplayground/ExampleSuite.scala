package devinsideyou
package scala2

// Not necessary since TestSuite uses ScalacheckShapeless
import enumeratum.scalacheck._

final class ExampleSuite extends TestSuite {
  import ExampleSuite._

  test("hello world") {
    // compiles either with enumeratum.scalacheck._ or with ScalacheckShapeless
    forAll { planet: PlanetEnumeratum => }

    // compiles because of ScalacheckShapeless
    // enumeratum.scalacheck._ is not enough
    forAll { person: Person => }
  }
}

object ExampleSuite {
  final case class Person(name: String, favoritePlanet: PlanetEnumeratum)
}
