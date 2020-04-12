package devinsideyou
package scala2

object PlanetScala extends Enumeration {
  type PlanetScala = Value

  private val G: Double = 6.67300e-11

  sealed protected case class Val(
      orderFromSun: Int,
      mass: Double,
      radius: Double
    ) extends super.Val {
    def surfaceGravity: Double =
      G * mass / (radius * radius)

    def surfaceWeight(otherMass: Double): Double =
      otherMass * surfaceGravity

    final lazy val entryName: String =
      toString
  }

  implicit def valueToPlanetVal(x: PlanetScala): Val =
    x.asInstanceOf[Val]

  val Mercury: PlanetScala = Val(1, 3.303e+23, 2.4397e6)
  val Venus: PlanetScala = Val(2, 4.869e+24, 6.0518e6)
  val Earth: PlanetScala = Val(3, 5.976e+24, 6.37814e6)
  val Mars: PlanetScala = Val(4, 6.421e+23, 3.3972e6)
  val Jupiter: PlanetScala = Val(5, 1.9e+27, 7.1492e7)
  val Saturn: PlanetScala = Val(6, 5.688e+26, 6.0268e7)
  val Uranus: PlanetScala = Val(7, 8.686e+25, 2.5559e7)
  val Neptune: PlanetScala = Val(8, 1.024e+26, 2.4746e7)

  val valuesSeq: IndexedSeq[PlanetScala] =
    values.toIndexedSeq.sorted

  def withNameOption(name: String): Option[PlanetScala] =
    values.find(_.entryName == name)

  def withIndexOption(index: Int): Option[PlanetScala] =
    valuesSeq
      .lift(index)
      .map(_.ensuring(p => p.orderFromSun == (index + 1) && p.id == index))
}

object ColorScala extends Enumeration {
  type ColorScala = Value

  val Red, Green, Blue = Value
}

object PlanetScalaMain extends App {
  printlnHyphens(100)

  val values: PlanetScala.ValueSet =
    PlanetScala
      .values
      .tap(println)

  val valuesSeq: IndexedSeq[PlanetScala.PlanetScala] =
    PlanetScala
      .valuesSeq
      .tap(println)

  val instanceByIndex: Option[PlanetScala.PlanetScala] =
    PlanetScala
      .withIndexOption(2)
      .tap(println)

  val instanceByName: Option[PlanetScala.PlanetScala] =
    PlanetScala
      .withNameOption("Earth")
      .tap(println)

  printlnHyphens(100)

  println(PlanetScala.Earth < PlanetScala.Mars)

  // does NOT compile
  // def overloadingTest(planet: PlanetScala.PlanetScala): String =
  //   Console.GREEN + planet.toString + Console.RESET

  // def overloadingTest(color: ColorScala.ColorScala): String =
  //   Console.RED + color.toString + Console.RESET

  // overloadingTest(PlanetScala.Earth).tap(println)
  // overloadingTest(ColorScala.Red).tap(println)

  // does NOT produce a warning
  def patternMatchingExhaustivenessTest(planet: PlanetScala.PlanetScala): Unit =
    planet match {
      case PlanetScala.Earth =>
    }

  printlnHyphens(100)
}
