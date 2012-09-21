package controllers;

import java.util.List;

import models.FamillePoisson;
import play.mvc.Controller;
import play.mvc.With;

/**
 * @author kbenhdech
 * 
 */
@With(Secure.class)
@Check(Security.ADMINISTRATEUR)
@CRUD.For(models.Poisson.class)
public class PoissonCRUD extends CRUD {

}