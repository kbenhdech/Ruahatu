package job;
import play.*;
import play.i18n.Lang;
import play.jobs.*;
import play.test.*;
 
import models.*;
 
/**
 * @author kbenhdech
 * 
 */
@OnApplicationStart
public class Bootstrap extends Job {
 
    public void doJob() {
        // Check if the database is empty
        if(Utilisateur.count() == 0) {
            Fixtures.load("initial-data.yml");
            Lang.change("fr");
        }
    }
 
}

