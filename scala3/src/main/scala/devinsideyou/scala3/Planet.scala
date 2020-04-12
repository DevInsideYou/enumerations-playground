package devinsideyou
package scala3

import scala.collection.immutable.ArraySeq
import scala.util.chaining._

enum Planet(
    val orderFromSun: Int,
    val mass: Double,
    val radius: Double
  ) extends Ordered[Planet] {
  import Planet._

  final override def compare(that: Planet): Int =
    this.orderFromSun - that.orderFromSun

  def surfaceGravity: Double =
    G * mass / (radius * radius)

  def surfaceWeight(otherMass: Double): Double =
    otherMass * surfaceGravity

  case Mercury extends Planet(1, 3.303e+23, 2.4397e6)
  case Venus   extends Planet(2, 4.869e+24, 6.0518e6)
  case Earth   extends Planet(3, 5.976e+24, 6.37814e6)
  case Mars    extends Planet(4, 6.421e+23, 3.3972e6)
  case Jupiter extends Planet(5, 1.9e+27,   7.1492e7)
  case Saturn  extends Planet(6, 5.688e+26, 6.0268e7)
  case Uranus  extends Planet(7, 8.686e+25, 2.5559e7)
  case Neptune extends Planet(8, 1.024e+26, 2.4746e7)
}

object Planet {
  private final val G = 6.67300E-11

  val valuesSeq: IndexedSeq[Planet] =
    values
      .pipe(ArraySeq.unsafeWrapArray)
      .sorted
      // .sortBy(_.ordinal) // works as well

  def withNameOption(name: String): Option[Planet] =
    valuesSeq.find(_.toString == name)

  def withIndexOption(index: Int): Option[Planet] =
    valuesSeq
      .lift(index)
      .map(_.ensuring(p => p.orderFromSun == (index + 1) && p.ordinal == index))
}

enum Color(val rgb: Int) {
  case Red   extends Color(0xFF0000)
  case Green extends Color(0x00FF00)
  case Blue  extends Color(0x0000FF)
}

object Main extends App {
  printlnHyphens(100)

  val values: Array[Planet] =
    Planet
      .values
      .tap(_.mkString("Array(", ", ", ")").pipe(println)) // wrong order even though it's an Array

  val valuesSeq: IndexedSeq[Planet] =
    Planet
      .valuesSeq
      .tap(println)

  val instanceByIndex: Option[Planet] =
    Planet
      .withIndexOption(2)
      .tap(println)

  val instanceByName: Option[Planet] =
    Planet
      .withNameOption("Earth")
      .tap(println)

  printlnHyphens(100)

  println(Planet.Earth < Planet.Mars)

  printlnHyphens(100)

  def overloadingTest(planet: Planet): String =
    Console.GREEN + planet.toString + Console.RESET

  def overloadingTest(color: Color): String =
    Console.RED + color.toString + Console.RESET

  overloadingTest(Planet.Earth).tap(println)
  overloadingTest(Color.Red).tap(println)

  // produces a warning as it should
  // def patternMatchingExhaustivenessTest(planet: Planet): Unit =
  //   planet match {
  //     case Planet.Earth =>
  //   }

  printlnHyphens(100)
}

def printlnHyphens(n: Int): Unit =
  println("─" * n)
