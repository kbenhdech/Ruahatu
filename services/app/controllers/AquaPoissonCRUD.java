package controllers;

import play.mvc.With;

/**
 * @author kbenhdech
 * 
 */
@With(Secure.class)
@Check(Security.ADMINISTRATEUR)
@CRUD.For(models.AquaPoisson.class)
public class AquaPoissonCRUD extends CRUD {

}
