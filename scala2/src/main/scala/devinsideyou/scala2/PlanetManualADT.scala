package devinsideyou
package scala2

sealed abstract class PlanetManualADT(
    val orderFromSun: Int,
    val mass: Double,
    val radius: Double
  ) extends Product
    with Serializable
    with Ordered[PlanetManualADT] {
  import PlanetManualADT._

  final override def compare(that: PlanetManualADT): Int =
    this.orderFromSun - that.orderFromSun

  final val surfaceGravity: Double =
    G * mass / (radius * radius)

  final def surfaceWeight(otherMass: Double): Double =
    otherMass * surfaceGravity

  final val entryName: String =
    toString
}

object PlanetManualADT {
  case object Mercury extends PlanetManualADT(1, 3.303e+23, 2.4397e6)
  case object Venus extends PlanetManualADT(2, 4.869e+24, 6.0518e6)
  case object Earth extends PlanetManualADT(3, 5.976e+24, 6.3781e6)
  case object Mars extends PlanetManualADT(4, 6.421e+23, 3.3972e6)
  case object Jupiter extends PlanetManualADT(5, 1.9e+27, 7.1492e7)
  case object Saturn extends PlanetManualADT(6, 5.688e+26, 6.0268e7)
  case object Uranus extends PlanetManualADT(7, 8.686e+25, 2.5559e7)
  case object Neptune extends PlanetManualADT(8, 1.024e+26, 2.4746e7)

  final private val G: Double =
    6.67300e-11

  final val values: IndexedSeq[PlanetManualADT] =
    IndexedSeq(
      Mercury,
      Venus,
      Earth,
      Mars,
      Jupiter,
      Saturn,
      Uranus,
      Neptune
    ).sorted

  final def withNameOption(entryName: String): Option[PlanetManualADT] =
    values.find(_.entryName == entryName)

  final def withIndexOption(index: Int): Option[PlanetManualADT] =
    values
      .lift(index)
      .map(_.ensuring(_.orderFromSun == (index + 1)))
}

sealed abstract class ColorManualADT extends Product with Serializable
object ColorManualADT {
  case object Red extends ColorManualADT
  case object Green extends ColorManualADT
  case object Blue extends ColorManualADT
}

object PlanetManualMain extends App {
  printlnHyphens(100)

  val values: IndexedSeq[PlanetManualADT] =
    PlanetManualADT
      .values
      .tap(println)

  val instanceByIndex: Option[PlanetManualADT] =
    PlanetManualADT
      .withIndexOption(2)
      .tap(println)

  val instanceByName: Option[PlanetManualADT] =
    PlanetManualADT
      .withNameOption("Earth")
      .tap(println)

  printlnHyphens(100)

  println(PlanetManualADT.Earth < PlanetManualADT.Mars)

  printlnHyphens(100)

  def overloadingTest(planet: PlanetManualADT): String =
    Console.GREEN + planet.toString + Console.RESET

  def overloadingTest(color: ColorManualADT): String =
    Console.RED + color.toString + Console.RESET

  overloadingTest(PlanetManualADT.Earth).tap(println)
  overloadingTest(ColorManualADT.Red).tap(println)

  // produces a warning as it should
  // def patternMatchingExhaustivenessTest(planet: PlanetManualADT): Unit =
  //   planet match {
  //     case PlanetManualADT.Earth =>
  //   }

  printlnHyphens(100)
}
