package devinsideyou
package scala2

import enumeratum._

sealed abstract class PlanetEnumeratum(
    val orderFromSun: Int,
    val mass: Double,
    val radius: Double
  ) extends Product
    with Serializable
    with EnumEntry.Snakecase
    with Ordered[PlanetEnumeratum] {
  import PlanetEnumeratum._

  final override def compare(that: PlanetEnumeratum): Int =
    this.orderFromSun - that.orderFromSun

  final val surfaceGravity: Double =
    G * mass / (radius * radius)

  final def surfaceWeight(otherMass: Double): Double =
    otherMass * surfaceGravity
}

object PlanetEnumeratum
    extends Enum[PlanetEnumeratum]
    with CatsEnum[PlanetEnumeratum]
    with CirceEnum[PlanetEnumeratum] {
  final private val G: Double =
    6.67300e-11

  case object Mercury extends PlanetEnumeratum(1, 3.303e+23, 2.4397e6)
  case object Venus extends PlanetEnumeratum(2, 4.869e+24, 6.0518e6)
  case object Earth extends PlanetEnumeratum(3, 5.976e+24, 6.3781e6)
  case object Mars extends PlanetEnumeratum(4, 6.421e+23, 3.3972e6)
  case object Jupiter extends PlanetEnumeratum(5, 1.9e+27, 7.1492e7)
  case object Saturn extends PlanetEnumeratum(6, 5.688e+26, 6.0268e7)
  case object Uranus extends PlanetEnumeratum(7, 8.686e+25, 2.5559e7)
  case object Neptune extends PlanetEnumeratum(8, 1.024e+26, 2.4746e7)

  implicit val o: cats.Order[PlanetEnumeratum] =
    cats.Order.fromComparable

  override val values: IndexedSeq[PlanetEnumeratum] =
    findValues.ensuring(v => v == v.sorted)

  def withIndexOption(index: Int): Option[PlanetEnumeratum] =
    values
      .lift(index)
      .map(_.ensuring(_.orderFromSun == (index + 1)))
}

sealed abstract class ColorEnumeratum extends Product with Serializable with EnumEntry
object ColorEnumeratum extends Enum[ColorEnumeratum] {
  override val values: IndexedSeq[ColorEnumeratum] =
    findValues

  case object Red extends ColorEnumeratum
  case object Green extends ColorEnumeratum
  case object Blue extends ColorEnumeratum
}

object PlanetEnumeratumMain extends App {
  printlnHyphens(100)

  val values: IndexedSeq[PlanetEnumeratum] =
    PlanetEnumeratum
      .values
      .tap(println)

  val instanceByIndex: Option[PlanetEnumeratum] =
    PlanetEnumeratum
      .withIndexOption(2)
      .tap(println)

  val instanceByName: Option[PlanetEnumeratum] =
    PlanetEnumeratum
      .withNameOption("Earth")
      .tap(println)

  printlnHyphens(100)

  println(PlanetEnumeratum.Earth < PlanetEnumeratum.Mars)

  printlnHyphens(100)

  def overloadingTest(planet: PlanetEnumeratum): String =
    Console.GREEN + planet.toString + Console.RESET

  def overloadingTest(color: ColorEnumeratum): String =
    Console.RED + color.toString + Console.RESET

  overloadingTest(PlanetEnumeratum.Earth).tap(println)
  overloadingTest(ColorEnumeratum.Red).tap(println)

  // produces a warning as it should
  // def patternMatchingExhaustivenessTest(planet: PlanetEnumeratum): Unit =
  //   planet match {
  //     case PlanetEnumeratum.Earth =>
  //   }

  printlnHyphens(100)

  // More capabilities:

  // Technically snake_case but it's a bad example:
  PlanetEnumeratum.withNameOption("Earth").tap(println).map(_.entryName).tap(println)
  PlanetEnumeratum.withNameEither("Earth").tap(println).map(_.entryName).tap(println)

  printlnHyphens(100)

  // Technically snake_case but it's a bad example:
  PlanetEnumeratum.withNameInsensitiveOption("eArTh").tap(println).map(_.entryName).tap(println)
  PlanetEnumeratum.withNameLowercaseOnlyOption("earth").tap(println).map(_.entryName).tap(println)
  PlanetEnumeratum.withNameUppercaseOnlyOption("EARTH").tap(println).map(_.entryName).tap(println)

  printlnHyphens(100)

  import cats.implicits._

  // Cats Eq:
  val planetA: PlanetEnumeratum = PlanetEnumeratum.Earth
  val planetB: PlanetEnumeratum = PlanetEnumeratum.Earth
  val planetC: PlanetEnumeratum = PlanetEnumeratum.Mars
  println(planetA === planetB)
  println(planetB === planetC)
  println(planetA === planetC)

  printlnHyphens(100)

  // Cats Order:
  PlanetEnumeratum
    .values
    .toList
    .maximumOption
    .tap(println)

  printlnHyphens(100)

  // Circe
  import io.circe.syntax._
  import io.circe.parser._

  planetA
    .asJson
    .tap(println) // serializes only String "earth"

  printlnHyphens(100)

  ("\"" + planetA.entryName + "\"")
    .pipe(decode[PlanetEnumeratum])
    .tap(println)

  printlnHyphens(100)
}
