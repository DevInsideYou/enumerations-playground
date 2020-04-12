package devinsideyou.scala2;

import scala.collection.immutable.ArraySeq;
import scala.collection.immutable.IndexedSeq;
import scala.math.Ordered;
import scala.Option;

public enum PlanetJava implements Ordered<PlanetJava> {
  MERCURY(1, "Mercury", 3.303e+23, 2.4397e6),
  VENUS(2, "Venus", 4.869e+24, 6.0518e6),
  EARTH(3, "Earth", 5.976e+24, 6.3781e6),
  MARS(4, "Mars", 6.421e+23, 3.3972e6),
  JUPITER(5, "Jupiter", 1.9e+27, 7.1492e7),
  SATURN(6, "Saturn", 5.688e+26, 6.0268e7),
  URANUS(7, "Uranus", 8.686e+25, 2.5559e7),
  NEPTUNE(8, "Neptune", 1.024e+26, 2.4746e7);

  final public int orderFromSun;
  final public String entryName;
  final public double mass;
  final public double radius;

  PlanetJava(int orderFromSun, String entryName, double mass, double radius) {
    this.orderFromSun = orderFromSun;
    this.entryName = entryName;
    this.mass = mass;
    this.radius = radius;
  }

  @Override
  final public int compare(PlanetJava that) {
    return this.orderFromSun - that.orderFromSun;
  }

  @Override
  final public boolean $greater$eq(PlanetJava that) {
    return this.compare(that) >= 0;
  }

  @Override
  final public boolean $less$eq(PlanetJava that) {
    return this.compare(that) <= 0;
  }

  @Override
  final public boolean $greater(PlanetJava that) {
    return this.compare(that) > 0;
  }

  @Override
  final public boolean $less(PlanetJava that) {
    return this.compare(that) < 0;
  }

  final public double surfaceGravity() {
    return G * mass / (radius * radius);
  }

  final public double surfaceWeight(double otherMass) {
    return otherMass * surfaceGravity();
  }

  final private static double G =
    6.67300e-11;

  final public static IndexedSeq<PlanetJava> immutableValues =
    ArraySeq.unsafeWrapArray(values());

  public static Option<PlanetJava> withIndexOption(int index) {
    return immutableValues.lift().apply(index);
  }

  public static Option<PlanetJava> withNameOption(String entryName) {
    return immutableValues.find(p -> p.entryName.equals(entryName));
  }
}
