package controllers;

import play.mvc.With;

/**
 * @author kbenhdech
 * 
 */
@With(Secure.class)
@Check(Security.ADMINISTRATEUR)
@CRUD.For(models.Aquarium.class)
public class AquariumCRUD extends CRUD {

}
