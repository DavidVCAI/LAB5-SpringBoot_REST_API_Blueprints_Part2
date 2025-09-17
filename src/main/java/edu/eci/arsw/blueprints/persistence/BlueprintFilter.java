package edu.eci.arsw.blueprints.persistence;

import edu.eci.arsw.blueprints.model.Blueprint;

/**
 * Interface defining the contract for blueprint filtering operations.
 * This interface provides abstraction for different filtering strategies
 * that can be applied to blueprints to reduce their size or optimize their
 * data.
 *
 * @author Jesús Pinzón & David Velásquez
 * @version 1.0
 * @since 2025-09-12
 */
public interface BlueprintFilter {

  /**
   * Applies a specific filtering algorithm to the given blueprint.
   * The filtering process modifies the blueprint's points according to
   * the implemented strategy (redundancy removal, subsampling, etc.).
   *
   * @param blueprint the blueprint to be filtered
   * @return a new filtered blueprint with optimized points
   */
  Blueprint filter(Blueprint blueprint);
}
