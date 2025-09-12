package edu.eci.arsw.blueprints.controllers;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.services.BlueprintsServices;
import edu.eci.arsw.blueprints.exceptions.BlueprintNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
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
}
