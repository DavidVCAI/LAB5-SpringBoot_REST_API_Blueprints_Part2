package edu.eci.arsw.blueprints.persistence.impl;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.exceptions.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.exceptions.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.BlueprintsPersistence;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import org.springframework.stereotype.Component;

/**
 * In-memory implementation of the BlueprintsPersistence interface.
 * This class provides a simple storage mechanism using HashMap for development
 * and testing purposes.
 * Blueprints are stored in memory and will be lost when the application stops.
 * 
 * This implementation now includes multiple sample blueprints with at least 3
 * additional blueprints,
 * and 2 blueprints belonging to the same author as required for the REST API
 * demonstration.
 *
 * @author Jesús Pinzón & David Velásquez
 * @version 1.0
 * @since 2025-09-12
 */
@Component
public class InMemoryBlueprintPersistence implements BlueprintsPersistence {

  private final Map<Tuple<String, String>, Blueprint> blueprints = new ConcurrentHashMap<>();

  /**
   * Constructs an InMemoryBlueprintPersistence with initial sample data.
   * Loads sample blueprint data for testing and demonstration purposes.
   * Includes at least 3 additional blueprints with 2 belonging to the same
   * author.
   */
  public InMemoryBlueprintPersistence() {
    // Original stub data
    Point[] points1 = new Point[] { new Point(140, 140), new Point(115, 115) };
    Blueprint blueprint1 = new Blueprint("_authorname_", "_bpname_", points1);
    blueprints.put(new Tuple<>(blueprint1.getAuthor(), blueprint1.getName()), blueprint1);

    // Additional blueprint 1 - John's House Design
    Point[] housePoints = new Point[] {
        new Point(10, 10), new Point(10, 100), new Point(100, 100),
        new Point(100, 10), new Point(10, 10), new Point(50, 10),
        new Point(50, 50), new Point(80, 50), new Point(80, 80)
    };
    Blueprint houseBlueprint = new Blueprint("john", "house_design", housePoints);
    blueprints.put(new Tuple<>(houseBlueprint.getAuthor(), houseBlueprint.getName()), houseBlueprint);

    // Additional blueprint 2 - John's Office Design (same author as above)
    Point[] officePoints = new Point[] {
        new Point(0, 0), new Point(0, 80), new Point(120, 80),
        new Point(120, 0), new Point(0, 0), new Point(30, 20),
        new Point(30, 60), new Point(90, 60), new Point(90, 20), new Point(30, 20)
    };
    Blueprint officeBlueprint = new Blueprint("john", "office_design", officePoints);
    blueprints.put(new Tuple<>(officeBlueprint.getAuthor(), officeBlueprint.getName()), officeBlueprint);

    // Additional blueprint 3 - Maria's Park Design
    Point[] parkPoints = new Point[] {
        new Point(5, 5), new Point(5, 95), new Point(95, 95),
        new Point(95, 5), new Point(5, 5), new Point(25, 25),
        new Point(75, 25), new Point(75, 75), new Point(25, 75), new Point(25, 25)
    };
    Blueprint parkBlueprint = new Blueprint("maria", "park_design", parkPoints);
    blueprints.put(new Tuple<>(parkBlueprint.getAuthor(), parkBlueprint.getName()), parkBlueprint);

    // Additional blueprint 4 - Carlos's Bridge Design
    Point[] bridgePoints = new Point[] {
        new Point(0, 50), new Point(20, 45), new Point(40, 40),
        new Point(60, 40), new Point(80, 45), new Point(100, 50),
        new Point(80, 55), new Point(60, 60), new Point(40, 60),
        new Point(20, 55), new Point(0, 50)
    };
    Blueprint bridgeBlueprint = new Blueprint("carlos", "bridge_design", bridgePoints);
    blueprints.put(new Tuple<>(bridgeBlueprint.getAuthor(), bridgeBlueprint.getName()), bridgeBlueprint);
  }

  @Override
  public void saveBlueprint(Blueprint blueprint) throws BlueprintPersistenceException {
    Tuple<String, String> key = new Tuple<>(blueprint.getAuthor(), blueprint.getName());
    Blueprint existing = blueprints.putIfAbsent(key, blueprint);
    if (existing != null) {
      throw new BlueprintPersistenceException(
          "The given blueprint already exists: " + blueprint.getAuthor() + "/" + blueprint.getName());
    }
  }

  @Override
  public Blueprint getBlueprint(String author, String blueprintName) throws BlueprintNotFoundException {
    Blueprint result = blueprints.get(new Tuple<>(author, blueprintName));
    if (result == null) {
      throw new BlueprintNotFoundException("Blueprint not found: " + author + "/" + blueprintName);
    }
    return result;
  }

  @Override
  public Set<Blueprint> getAllBlueprints() {
    return new HashSet<>(blueprints.values());
  }

  @Override
  public Set<Blueprint> getBlueprintsByAuthor(String author) throws BlueprintNotFoundException {
    Set<Blueprint> authorBlueprints = new HashSet<>();
    for (Blueprint blueprint : blueprints.values()) {
      if (blueprint.getAuthor().equals(author)) {
        authorBlueprints.add(blueprint);
      }
    }
    if (authorBlueprints.isEmpty()) {
      throw new BlueprintNotFoundException("No blueprints found for author: " + author);
    }
    return authorBlueprints;
  }

  @Override
  public void updateBlueprint(Blueprint blueprint) throws BlueprintNotFoundException, BlueprintPersistenceException {
    Tuple<String, String> key = new Tuple<>(blueprint.getAuthor(), blueprint.getName());
    if (!blueprints.containsKey(key)) {
      throw new BlueprintNotFoundException("Blueprint not found: " + blueprint.getAuthor() + "/" + blueprint.getName());
    }
    blueprints.put(key, blueprint);
  }
}
