package devinsideyou
package scala2

import enumeratum.values._ // here

sealed abstract class PlanetEnumeratumValue(
    override val value: Int, // here
    val mass: Double,
    val radius: Double
  ) extends IntEnumEntry // here
    with Product // here
    with Serializable // here
    with Ordered[PlanetEnumeratumValue] {
  import PlanetEnumeratumValue._

  final val orderFromSun: Int = // here
    value

  final val name: String =
    toString

  final override def compare(that: PlanetEnumeratumValue): Int =
    this.orderFromSun - that.orderFromSun

  final val surfaceGravity: Double =
    G * mass / (radius * radius)

  final def surfaceWeight(otherMass: Double): Double =
    otherMass * surfaceGravity
}

import cats.implicits._ // required for CatsOrderValueEnum

object PlanetEnumeratumValue
    extends CatsOrderValueEnum[Int, PlanetEnumeratumValue] // here
    with IntEnum[PlanetEnumeratumValue] // here
    with IntCirceEnum[PlanetEnumeratumValue] { // here
  final private val G: Double =
    6.67300e-11

  case object Mercury extends PlanetEnumeratumValue(1, 3.303e+23, 2.4397e6)
  case object Venus extends PlanetEnumeratumValue(2, 4.869e+24, 6.0518e6)
  case object Earth extends PlanetEnumeratumValue(3, 5.976e+24, 6.3781e6)
  case object Mars extends PlanetEnumeratumValue(4, 6.421e+23, 3.3972e6)
  case object Jupiter extends PlanetEnumeratumValue(5, 1.9e+27, 7.1492e7)
  case object Saturn extends PlanetEnumeratumValue(6, 5.688e+26, 6.0268e7)
  case object Uranus extends PlanetEnumeratumValue(7, 8.686e+25, 2.5559e7)
  case object Neptune extends PlanetEnumeratumValue(8, 1.024e+26, 2.4746e7)

  // implicit val o: cats.Order[PlanetEnumeratumValue] = // here
  //   cats.Order.fromComparable

  override val values: IndexedSeq[PlanetEnumeratumValue] =
    findValues.ensuring(_ == findValues.sorted)

  // here
  def withIndexOption(index: Int): Option[PlanetEnumeratumValue] =
    values
      .lift(index)
      .map(_.ensuring(_.orderFromSun == (index + 1)))

  final def withNameOption(entryName: String): Option[PlanetEnumeratumValue] =
    values.find(_.name == entryName)
}

sealed abstract class ColorEnumeratumValue(override val value: Int) extends IntEnumEntry with Product with Serializable
object ColorEnumeratumValue extends IntEnum[ColorEnumeratumValue] {
  override val values: IndexedSeq[ColorEnumeratumValue] =
    findValues

  case object Red extends ColorEnumeratumValue(0xFF0000)
  case object Green extends ColorEnumeratumValue(0x00FF00)
  case object Blue extends ColorEnumeratumValue(0x0000FF)
}

object PlanetEnumeratumValueMain extends App {
  printlnHyphens(100)

  val values: IndexedSeq[PlanetEnumeratumValue] =
    PlanetEnumeratumValue
      .values
      .tap(println)

  val instanceByIndex: Option[PlanetEnumeratumValue] =
    PlanetEnumeratumValue
      .withIndexOption(2)
      .tap(println)

  // here
  val instanceByValue: Option[PlanetEnumeratumValue] =
    PlanetEnumeratumValue
      .withValueOpt(3) // not 2 since value == orderFromSun
      .tap(println)

  // here
  val instanceByName: Option[PlanetEnumeratumValue] =
    PlanetEnumeratumValue
      .withNameOption("Earth")
      .tap(println)

  printlnHyphens(100)

  println(PlanetEnumeratumValue.Earth < PlanetEnumeratumValue.Mars)

  printlnHyphens(100)

  def overloadingTest(planet: PlanetEnumeratumValue): String =
    Console.GREEN + planet.toString + Console.RESET

  def overloadingTest(color: ColorEnumeratumValue): String =
    Console.RED + color.toString + Console.RESET

  overloadingTest(PlanetEnumeratumValue.Earth).tap(println)
  overloadingTest(ColorEnumeratumValue.Red).tap(println)

  // produces a warning as it should
  // def patternMatchingExhaustivenessTest(planet: PlanetEnumeratumValue): Unit =
  //   planet match {
  //     case PlanetEnumeratumValue.Earth =>
  //   }

  printlnHyphens(100)

  // More capabilities:

  // here
  // PlanetEnumeratumValue.withNameOption("Earth").tap(println).map(_.entryName).tap(println)
  // PlanetEnumeratumValue.withNameEither("Earth").tap(println).map(_.entryName).tap(println)

  // printlnHyphens(100)

  // here
  // PlanetEnumeratumValue.withNameInsensitiveOption("eArTh").tap(println).map(_.entryName).tap(println)
  // PlanetEnumeratumValue.withNameLowercaseOnlyOption("earth").tap(println).map(_.entryName).tap(println)
  // PlanetEnumeratumValue.withNameUppercaseOnlyOption("EARTH").tap(println).map(_.entryName).tap(println)

  // printlnHyphens(100)

  import cats.implicits._

  // Cats Eq:
  val planetA: PlanetEnumeratumValue = PlanetEnumeratumValue.Earth
  val planetB: PlanetEnumeratumValue = PlanetEnumeratumValue.Earth
  val planetC: PlanetEnumeratumValue = PlanetEnumeratumValue.Mars
  println(planetA === planetB)
  println(planetB === planetC)
  println(planetA === planetC)

  printlnHyphens(100)

  // Cats Order:
  PlanetEnumeratumValue
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
    .tap(println) // serializes only Int 3

  printlnHyphens(100)

  planetA.value // here
    .toString
    .pipe(decode[PlanetEnumeratumValue])
    .tap(println)

  printlnHyphens(100)
}
