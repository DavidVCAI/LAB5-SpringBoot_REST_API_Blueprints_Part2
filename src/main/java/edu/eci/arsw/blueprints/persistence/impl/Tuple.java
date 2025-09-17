package edu.eci.arsw.blueprints.persistence.impl;

import java.util.Objects;

/**
 * Generic tuple class that holds two objects of potentially different types.
 * This utility class is used as a composite key for storing blueprints in
 * HashMap
 * based on author and blueprint name combination.
 *
 * @param <T1> the type of the first element
 * @param <T2> the type of the second element
 * @author Jesús Pinzón & David Velásquez
 * @version 1.0
 * @since 2025-09-12
 */
public class Tuple<T1, T2> {

  private T1 firstElement;
  private T2 secondElement;

  /**
   * Constructs a Tuple with the specified elements.
   *
   * @param firstElement  the first element of the tuple
   * @param secondElement the second element of the tuple
   */
  public Tuple(T1 firstElement, T2 secondElement) {
    super();
    this.firstElement = firstElement;
    this.secondElement = secondElement;
  }

  /**
   * Gets the first element of the tuple.
   *
   * @return the first element
   */
  public T1 getFirstElement() {
    return firstElement;
  }

  /**
   * Gets the second element of the tuple.
   *
   * @return the second element
   */
  public T2 getSecondElement() {
    return secondElement;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 17 * hash + Objects.hashCode(this.firstElement);
    hash = 17 * hash + Objects.hashCode(this.secondElement);
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
    final Tuple<?, ?> other = (Tuple<?, ?>) obj;
    if (!Objects.equals(this.firstElement, other.firstElement)) {
      return false;
    }
    if (!Objects.equals(this.secondElement, other.secondElement)) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "Tuple{" +
        "firstElement=" + firstElement +
        ", secondElement=" + secondElement +
        '}';
  }
}
