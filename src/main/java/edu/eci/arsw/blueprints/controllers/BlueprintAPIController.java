package edu.eci.arsw.blueprints.controllers;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.services.BlueprintsServices;
import edu.eci.arsw.blueprints.exceptions.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.exceptions.BlueprintPersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * REST Controller for Blueprint API operations.
 * This controller provides RESTful endpoints for managing architectural
 * blueprints,
 * including operations to retrieve all blueprints, blueprints by author, and
 * specific blueprints.
 *
 * @author Jesús Pinzón & David Velásquez
 * @version 1.0
 * @since 2025-09-12
 */
@RestController
@RequestMapping(value = "/blueprints")
public class BlueprintAPIController {

  @Autowired
  private BlueprintsServices blueprintsServices;

  /**
   * Handles GET requests to retrieve all blueprints.
   * Returns all blueprints in the system with applied filtering.
   *
   * @return ResponseEntity containing all blueprints or error message
   */
  @RequestMapping(method = RequestMethod.GET)
  public ResponseEntity<?> getAllBlueprints() {
    try {
      Set<Blueprint> blueprints = blueprintsServices.getAllBlueprints();
      return new ResponseEntity<>(blueprints, HttpStatus.ACCEPTED);
    } catch (Exception ex) {
      Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
      return new ResponseEntity<>("Error retrieving all blueprints", HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  /**
   * Handles GET requests to retrieve all blueprints by a specific author.
   * Returns all blueprints created by the specified author with applied
   * filtering.
   *
   * @param author the author whose blueprints are to be retrieved
   * @return ResponseEntity containing author's blueprints or error message
   */
  @RequestMapping(value = "/{author}", method = RequestMethod.GET)
  public ResponseEntity<?> getBlueprintsByAuthor(@PathVariable String author) {
    try {
      Set<Blueprint> blueprints = blueprintsServices.getBlueprintsByAuthor(author);
      return new ResponseEntity<>(blueprints, HttpStatus.ACCEPTED);
    } catch (BlueprintNotFoundException ex) {
      Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
      return new ResponseEntity<>("Author not found: " + author, HttpStatus.NOT_FOUND);
    } catch (Exception ex) {
      Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
      return new ResponseEntity<>("Error retrieving blueprints for author: " + author,
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  /**
   * Handles GET requests to retrieve a specific blueprint by author and blueprint
   * name.
   * Returns the specific blueprint created by the author with the given name,
   * with applied filtering.
   *
   * @param author the author of the blueprint
   * @param bpname the name of the blueprint
   * @return ResponseEntity containing the specific blueprint or error message
   */
  @RequestMapping(value = "/{author}/{bpname}", method = RequestMethod.GET)
  public ResponseEntity<?> getBlueprint(@PathVariable String author, @PathVariable String bpname) {
    try {
      Blueprint blueprint = blueprintsServices.getBlueprint(author, bpname);
      return new ResponseEntity<>(blueprint, HttpStatus.ACCEPTED);
    } catch (BlueprintNotFoundException ex) {
      Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
      return new ResponseEntity<>("Blueprint not found: " + author + "/" + bpname, HttpStatus.NOT_FOUND);
    } catch (Exception ex) {
      Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
      return new ResponseEntity<>("Error retrieving blueprint: " + author + "/" + bpname,
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  /**
   * Handles POST requests to create a new blueprint.
   * Accepts a JSON representation of a blueprint in the request body and
   * creates a new blueprint in the system.
   *
   * @param blueprint the blueprint data from the request body
   * @return ResponseEntity with HTTP 201 CREATED if successful, or error status
   */
  @RequestMapping(method = RequestMethod.POST)
  public ResponseEntity<?> createBlueprint(@RequestBody Blueprint blueprint) {
    try {
      blueprintsServices.addNewBlueprint(blueprint);
      return new ResponseEntity<>(HttpStatus.CREATED);
    } catch (BlueprintPersistenceException ex) {
      Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
      return new ResponseEntity<>("Blueprint already exists: " + blueprint.getAuthor() + "/" + blueprint.getName(),
          HttpStatus.FORBIDDEN);
    } catch (Exception ex) {
      Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
      return new ResponseEntity<>("Error creating blueprint: " + ex.getMessage(),
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  /**
   * Handles PUT requests to update an existing blueprint.
   * Accepts a JSON representation of a blueprint in the request body and
   * updates the blueprint identified by author and blueprint name.
   *
   * @param author    the author of the blueprint to update
   * @param bpname    the name of the blueprint to update
   * @param blueprint the updated blueprint data from the request body
   * @return ResponseEntity with HTTP 202 ACCEPTED if successful, or error status
   */
  @RequestMapping(value = "/{author}/{bpname}", method = RequestMethod.PUT)
  public ResponseEntity<?> updateBlueprint(@PathVariable String author, @PathVariable String bpname,
      @RequestBody Blueprint blueprint) {
    try {
      // Ensure the blueprint author and name match the path variables
      if (!blueprint.getAuthor().equals(author) || !blueprint.getName().equals(bpname)) {
        return new ResponseEntity<>("Blueprint author/name mismatch with URL path", HttpStatus.BAD_REQUEST);
      }

      blueprintsServices.updateBlueprint(blueprint);
      return new ResponseEntity<>(HttpStatus.ACCEPTED);
    } catch (BlueprintNotFoundException ex) {
      Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
      return new ResponseEntity<>("Blueprint not found: " + author + "/" + bpname, HttpStatus.NOT_FOUND);
    } catch (BlueprintPersistenceException ex) {
      Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
      return new ResponseEntity<>("Error updating blueprint: " + ex.getMessage(), HttpStatus.FORBIDDEN);
    } catch (Exception ex) {
      Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
      return new ResponseEntity<>("Error updating blueprint: " + ex.getMessage(),
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
