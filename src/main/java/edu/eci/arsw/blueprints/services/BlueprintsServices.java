package edu.eci.arsw.blueprints.services;

import edu.eci.arsw.blueprints.persistence.BlueprintFilter;
import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.exceptions.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.exceptions.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.BlueprintsPersistence;

import java.util.Set;
import java.util.HashSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class that provides business logic for blueprint management
 * operations.
 * This class acts as an intermediary between the presentation layer and the
 * persistence layer,
 * implementing the service layer pattern for blueprint-related functionality
 * with filtering capabilities.
 *
 * @author Jesús Pinzón & David Velásquez
 * @version 1.0
 * @since 2025-09-12
 */
@Service
public class BlueprintsServices {

  @Autowired
  private BlueprintsPersistence blueprintsPersistence;

  @Autowired
  private BlueprintFilter blueprintFilter;

  /**
   * Sets the blueprint persistence implementation (used for testing without
   * Spring context).
   * 
   * @param blueprintsPersistence the persistence implementation to set
   */
  public void setBlueprintsPersistence(BlueprintsPersistence blueprintsPersistence) {
    this.blueprintsPersistence = blueprintsPersistence;
  }

  /**
   * Sets the blueprint filter implementation (used for testing without Spring
   * context).
   * 
   * @param blueprintFilter the filter implementation to set
   */
  public void setBlueprintFilter(BlueprintFilter blueprintFilter) {
    this.blueprintFilter = blueprintFilter;
  }

  /**
   * Registers a new blueprint in the system.
   *
   * @param blueprint the blueprint to be added
   * @throws BlueprintPersistenceException if the blueprint already exists or a
   *                                       persistence error occurs
   */
  public void addNewBlueprint(Blueprint blueprint) throws BlueprintPersistenceException {
    blueprintsPersistence.saveBlueprint(blueprint);
  }

  /**
   * Retrieves all blueprints stored in the system with applied filtering.
   * 
   * @return a set containing all filtered blueprints
   */
  public Set<Blueprint> getAllBlueprints() {
    Set<Blueprint> blueprints = blueprintsPersistence.getAllBlueprints();
    Set<Blueprint> filteredBlueprints = new HashSet<>();

    for (Blueprint blueprint : blueprints) {
      filteredBlueprints.add(blueprintFilter.filter(blueprint));
    }

    return filteredBlueprints;
  }

  /**
   * Retrieves a specific blueprint by its author and name with applied filtering.
   *
   * @param author the blueprint's author
   * @param name   the blueprint's name
   * @return the filtered blueprint matching the specified criteria
   * @throws BlueprintNotFoundException if no blueprint is found with the given
   *                                    parameters
   */
  public Blueprint getBlueprint(String author, String name) throws BlueprintNotFoundException {
    Blueprint blueprint = blueprintsPersistence.getBlueprint(author, name);
    return blueprintFilter.filter(blueprint);
  }

  /**
   * Retrieves all blueprints created by a specific author with applied filtering.
   *
   * @param author the blueprint author to search for
   * @return a set containing all filtered blueprints by the specified author
   * @throws BlueprintNotFoundException if no blueprints are found for the given
   *                                    author
   */
  public Set<Blueprint> getBlueprintsByAuthor(String author) throws BlueprintNotFoundException {
    Set<Blueprint> blueprints = blueprintsPersistence.getBlueprintsByAuthor(author);
    Set<Blueprint> filteredBlueprints = new HashSet<>();

    for (Blueprint blueprint : blueprints) {
      filteredBlueprints.add(blueprintFilter.filter(blueprint));
    }

    return filteredBlueprints;
  }

  /**
   * Updates an existing blueprint in the system.
   *
   * @param blueprint the blueprint to be updated
   * @throws BlueprintNotFoundException    if the blueprint to update doesn't
   *                                       exist
   * @throws BlueprintPersistenceException if any persistence error occurs
   */
  public void updateBlueprint(Blueprint blueprint) throws BlueprintNotFoundException, BlueprintPersistenceException {
    blueprintsPersistence.updateBlueprint(blueprint);
  }
}
