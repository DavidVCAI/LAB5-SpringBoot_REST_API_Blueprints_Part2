package edu.eci.arsw.blueprints.persistence.impl;

import edu.eci.arsw.blueprints.persistence.BlueprintFilter;
import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Blueprint filter implementation that applies subsampling to reduce points.
 * This filter optimizes blueprints by removing every other point in an
 * alternating pattern, effectively reducing the blueprint size by approximately
 * half.
 *
 * @author Jesús Pinzón & David Velásquez
 * @version 1.0
 * @since 2025-09-12
 */
@Component
@Primary
public class SubsamplingBlueprintFilter implements BlueprintFilter {

  /**
   * Filters the blueprint by removing every other point in an alternating
   * pattern.
   * The algorithm keeps points at even indices (0, 2, 4, ...) and removes odd
   * indices (1, 3, 5, ...).
   *
   * @param blueprint the blueprint to be filtered
   * @return a new blueprint with subsampled points
   */
  @Override
  public Blueprint filter(Blueprint blueprint) {
    if (blueprint == null || blueprint.getPoints() == null || blueprint.getPoints().isEmpty()) {
      return blueprint;
    }

    List<Point> originalPoints = blueprint.getPoints();
    List<Point> filteredPoints = new ArrayList<>();

    // Keep points at even indices (0, 2, 4, ...)
    for (int i = 0; i < originalPoints.size(); i += 2) {
      filteredPoints.add(originalPoints.get(i));
    }

    // Create and return new filtered blueprint
    Blueprint filteredBlueprint = new Blueprint();
    filteredBlueprint.setAuthor(blueprint.getAuthor());
    filteredBlueprint.setName(blueprint.getName());
    filteredBlueprint.setPoints(filteredPoints);

    return filteredBlueprint;
  }
}
