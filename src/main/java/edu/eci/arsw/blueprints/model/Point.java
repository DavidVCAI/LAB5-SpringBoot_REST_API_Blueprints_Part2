package edu.eci.arsw.blueprints.model;

import java.util.Objects;

/**
 * Represents a geometric point with x and y coordinates in a 2D plane.
 * This class is used to define the geometric structure of blueprint designs.
 *
 * @author Jesús Pinzón & David Velásquez
 * @version 1.0
 * @since 2025-09-12
 */
public class Point {

  private int x;
  private int y;

  /**
   * Default constructor.
   */
  public Point() {
  }

  /**
   * Constructs a Point with specified coordinates.
   * 
   * @param x the x coordinate
   * @param y the y coordinate
   */
  public Point(int x, int y) {
    this.x = x;
    this.y = y;
  }

  /**
   * Gets the x coordinate.
   * 
   * @return the x coordinate
   */
  public int getX() {
    return x;
  }

  /**
   * Sets the x coordinate.
   * 
   * @param x the x coordinate to set
   */
  public void setX(int x) {
    this.x = x;
  }

  /**
   * Gets the y coordinate.
   * 
   * @return the y coordinate
   */
  public int getY() {
    return y;
  }

  /**
   * Sets the y coordinate.
   * 
   * @param y the y coordinate to set
   */
  public void setY(int y) {
    this.y = y;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    Point point = (Point) obj;
    return x == point.x && y == point.y;
  }

  @Override
  public int hashCode() {
    return Objects.hash(x, y);
  }

  @Override
  public String toString() {
    return "Point{" + "x=" + x + ", y=" + y + '}';
  }
}
