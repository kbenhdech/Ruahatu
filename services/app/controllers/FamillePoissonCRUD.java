package controllers;

import play.mvc.With;

/**
 * @author kbenhdech
 * 
 */
@With(Secure.class)
@Check(Security.ADMINISTRATEUR)
@CRUD.For(models.FamillePoisson.class)
public class FamillePoissonCRUD extends CRUD {

}
