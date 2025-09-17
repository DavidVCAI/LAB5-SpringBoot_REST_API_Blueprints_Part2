package edu.eci.arsw.blueprints.exceptions;

/**
 * Exception thrown when a requested blueprint is not found in the persistence
 * layer.
 *
 * @author Jesús Pinzón & David Velásquez
 * @version 1.0
 * @since 2025-09-12
 */
public class BlueprintNotFoundException extends Exception {

  /**
   * Constructs a BlueprintNotFoundException with the specified detail message.
   * 
   * @param message the detail message explaining the reason for the exception
   */
  public BlueprintNotFoundException(String message) {
    super(message);
  }

  /**
   * Constructs a BlueprintNotFoundException with the specified detail message and
   * cause.
   *
   * @param message the detail message explaining the reason for the exception
   * @param cause   the cause of this exception (which is saved for later
   *                retrieval)
   */
  public BlueprintNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }
}
