package edu.eci.arsw.blueprints.persistence;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.exceptions.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.exceptions.BlueprintPersistenceException;
import java.util.Set;

/**
 * Interface defining the contract for blueprint persistence operations.
 * This interface provides abstraction for different persistence
 * implementations,
 * allowing the system to work with various storage mechanisms.
 *
 * @author Jesús Pinzón & David Velásquez
 * @version 1.0
 * @since 2025-09-12
 */
public interface BlueprintsPersistence {

  /**
   * Saves a new blueprint to the persistence layer.
   *
   * @param blueprint the blueprint to be saved
   * @throws BlueprintPersistenceException if a blueprint with the same name and
   *                                       author already exists,
   *                                       or any other low-level persistence
   *                                       error occurs
   */
  public void saveBlueprint(Blueprint blueprint) throws BlueprintPersistenceException;

  /**
   * Retrieves a specific blueprint by its author and name.
   *
   * @param author        the blueprint's author
   * @param blueprintName the blueprint's name
   * @return the blueprint matching the specified author and name
   * @throws BlueprintNotFoundException if no blueprint is found with the given
   *                                    parameters
   */
  public Blueprint getBlueprint(String author, String blueprintName) throws BlueprintNotFoundException;

  /**
   * Retrieves all blueprints stored in the persistence layer.
   * 
   * @return a set containing all blueprints
   */
  public Set<Blueprint> getAllBlueprints();

  /**
   * Retrieves all blueprints created by a specific author.
   *
   * @param author the blueprint author to search for
   * @return a set containing all blueprints by the specified author
   * @throws BlueprintNotFoundException if no blueprints are found for the given
   *                                    author
   */
  public Set<Blueprint> getBlueprintsByAuthor(String author) throws BlueprintNotFoundException;

  /**
   * Updates an existing blueprint in the persistence layer.
   *
   * @param blueprint the blueprint to be updated
   * @throws BlueprintNotFoundException    if the blueprint to update doesn't
   *                                       exist
   * @throws BlueprintPersistenceException if any other persistence error occurs
   */
  public void updateBlueprint(Blueprint blueprint) throws BlueprintNotFoundException, BlueprintPersistenceException;
}
