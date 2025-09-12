package edu.eci.arsw.blueprints.exceptions;

/**
 * Exception thrown when a persistence operation fails.
 * 
 * @author Jesús Pinzón & David Velásquez
 * @version 1.0
 * @since 2025-09-12
 */
public class BlueprintPersistenceException extends Exception {

  /**
   * Constructs a BlueprintPersistenceException with the specified detail message.
   * 
   * @param message the detail message explaining the reason for the exception
   */
  public BlueprintPersistenceException(String message) {
    super(message);
  }

  /**
   * Constructs a BlueprintPersistenceException with the specified detail message
   * and cause.
   *
   * @param message the detail message explaining the reason for the exception
   * @param cause   the cause of this exception (which is saved for later
   *                retrieval)
   */
  public BlueprintPersistenceException(String message, Throwable cause) {
    super(message, cause);
  }
}
