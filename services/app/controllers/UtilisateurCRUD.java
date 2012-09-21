package controllers;

import java.util.List;
import play.mvc.Controller;
import play.mvc.With;

/**
 * @author kbenhdech
 * 
 */
@With(Secure.class)
@Check(Security.ADMINISTRATEUR)
@CRUD.For(models.Utilisateur.class)
public class UtilisateurCRUD extends CRUD {

}