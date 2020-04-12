package devinsideyou
package scala2

object PlanetJavaMain extends App {
  printlnHyphens(100)

  val values: Array[PlanetJava] =
    PlanetJava
      .values
      .tap(_.mkString("Array(", ", ", ")").pipe(println))

  val immutableValues: IndexedSeq[PlanetJava] =
    PlanetJava
      .immutableValues
      .tap(println)

  val instanceByIndex: Option[PlanetJava] =
    PlanetJava
      .withIndexOption(2)
      .tap(println)

  val instanceByName: Option[PlanetJava] =
    PlanetJava
      .withNameOption("Earth")
      .tap(println)

  printlnHyphens(100)

  println(PlanetJava.EARTH < PlanetJava.MARS)

  printlnHyphens(100)

  def overloadingTest(planet: PlanetJava): String =
    Console.GREEN + planet.toString + Console.RESET

  def overloadingTest(color: ColorJava): String =
    Console.RED + color.toString + Console.RESET

  overloadingTest(PlanetJava.EARTH).tap(println)
  overloadingTest(ColorJava.RED).tap(println)

  // produces a warning as it should
  // def patternMatchingExhaustivenessTest(planet: PlanetJava): Unit =
  //   planet match {
  //     case PlanetJava.EARTH =>
  //   }

  printlnHyphens(100)
}
