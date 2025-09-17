package edu.eci.arsw.blueprints.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Represents an architectural blueprint containing a collection of points that
 * define a design.
 * This class encapsulates the basic information of a blueprint including its
 * author, name, and geometric points.
 *
 * @author Jesús Pinzón & David Velásquez
 * @version 1.0
 * @since 2025-09-12
 */
public class Blueprint {

  private String author;
  private String name;
  private List<Point> points;

  /**
   * Default constructor.
   */
  public Blueprint() {
  }

  /**
   * Constructs a Blueprint with the specified author, name, and points list.
   *
   * @param author the author of the blueprint
   * @param name   the name of the blueprint
   * @param points list of points that define the blueprint design
   */
  public Blueprint(String author, String name, List<Point> points) {
    this.author = author;
    this.name = name;
    this.points = points;
  }

  /**
   * Constructs a Blueprint with the specified author, name, and points array.
   *
   * @param author the author of the blueprint
   * @param name   the name of the blueprint
   * @param points array of points that define the blueprint design
   */
  public Blueprint(String author, String name, Point[] points) {
    this.author = author;
    this.name = name;
    this.points = Arrays.asList(points);
  }

  /**
   * Gets the author of the blueprint.
   * 
   * @return the author
   */
  public String getAuthor() {
    return author;
  }

  /**
   * Sets the author of the blueprint.
   * 
   * @param author the author to set
   */
  public void setAuthor(String author) {
    this.author = author;
  }

  /**
   * Gets the name of the blueprint.
   * 
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the name of the blueprint.
   * 
   * @param name the name to set
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Gets the list of points in the blueprint.
   * 
   * @return the points
   */
  public List<Point> getPoints() {
    return points;
  }

  /**
   * Sets the list of points in the blueprint.
   * 
   * @param points the points to set
   */
  public void setPoints(List<Point> points) {
    this.points = points;
  }

  /**
   * Adds a point to the blueprint design.
   *
   * @param point the point to be added
   */
  public void addPoint(Point point) {
    if (this.points == null) {
      this.points = new ArrayList<>();
    }
    this.points.add(point);
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 17 * hash + Objects.hashCode(this.author);
    hash = 17 * hash + Objects.hashCode(this.name);
    return hash;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final Blueprint other = (Blueprint) obj;
    if (!Objects.equals(this.author, other.author)) {
      return false;
    }
    if (!Objects.equals(this.name, other.name)) {
      return false;
    }
    if (this.points.size() != other.points.size()) {
      return false;
    }
    for (int i = 0; i < this.points.size(); i++) {
      if (!this.points.get(i).equals(other.points.get(i))) {
        return false;
      }
    }

    return true;
  }
}
