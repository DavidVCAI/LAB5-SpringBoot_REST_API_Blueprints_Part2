package edu.eci.arsw.blueprints.persistence.impl;

import edu.eci.arsw.blueprints.persistence.BlueprintFilter;
import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Blueprint filter implementation that removes consecutive duplicate points.
 * This filter optimizes blueprints by eliminating redundant consecutive points
 * that represent the same coordinate, reducing the overall size of the
 * blueprint.
 *
 * @author Jesús Pinzón & David Velásquez
 * @version 1.0
 * @since 2025-09-12
 */
@Component
public class RedundancyBlueprintFilter implements BlueprintFilter {

  /**
   * Filters the blueprint by removing consecutive duplicate points.
   * The algorithm preserves the first occurrence of each consecutive group of
   * identical points.
   *
   * @param blueprint the blueprint to be filtered
   * @return a new blueprint with consecutive duplicate points removed
   */
  @Override
  public Blueprint filter(Blueprint blueprint) {
    if (blueprint == null || blueprint.getPoints() == null || blueprint.getPoints().isEmpty()) {
      return blueprint;
    }

    List<Point> originalPoints = blueprint.getPoints();
    List<Point> filteredPoints = new ArrayList<>();

    // Add the first point
    filteredPoints.add(originalPoints.get(0));

    // Check each subsequent point against the previous one
    for (int i = 1; i < originalPoints.size(); i++) {
      Point currentPoint = originalPoints.get(i);
      Point previousPoint = originalPoints.get(i - 1);

      // Only add the point if it's different from the previous one
      if (!currentPoint.equals(previousPoint)) {
        filteredPoints.add(currentPoint);
      }
    }

    // Create and return new filtered blueprint
    Blueprint filteredBlueprint = new Blueprint();
    filteredBlueprint.setAuthor(blueprint.getAuthor());
    filteredBlueprint.setName(blueprint.getName());
    filteredBlueprint.setPoints(filteredPoints);

    return filteredBlueprint;
  }
}
