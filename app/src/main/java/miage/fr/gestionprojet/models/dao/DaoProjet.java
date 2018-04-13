package miage.fr.gestionprojet.models.dao;

import com.activeandroid.query.Select;
import java.util.Date;
import java.util.List;

import miage.fr.gestionprojet.models.Action;
import miage.fr.gestionprojet.models.Domaine;
import miage.fr.gestionprojet.models.Projet;

/**
 * Created by Audrey on 23/01/2017.
 */

public class DaoProjet {

    public static List<Projet> getProjetEnCours(Date dateDuJour){
        return new Select()
                .from(Projet.class)
                .where("date_fin_initiale is null or date_fin_initiale>? or date_fin_reelle is null or date_fin_reelle>?", dateDuJour.getTime(),dateDuJour.getTime())
                .execute();
    }

    public static Projet loadById(long idProjet) {
        return new Select().from(Projet.class).where("id=?", idProjet).executeSingle();
    }

    public static List<Projet> loadAll(){
        return new Select().from(Projet.class).execute();
    }

    public static Date getDateFin(long idProjet){
        Date dateFinPrevue = null;
        List<Action> fetch = new Select().from(Action.class).as("Action")
                .join(Domaine.class).as("Domaine")
                .on("Domaine.id = Action.domaine")
                .join(Projet.class).as("Projet")
                .on("Projet.id = Domaine.projet")
                .where("Projet.id = ?", idProjet)
                .execute();
        for (Action action :fetch) {
            Date dtFinPrevue = action.getDtFinPrevue();
            if (dateFinPrevue == null || dtFinPrevue == null) {
                dateFinPrevue = dtFinPrevue;
            } else if (dateFinPrevue.compareTo(dtFinPrevue) == 0){
                dateFinPrevue = dtFinPrevue;
            }
        }
        return dateFinPrevue;
    }

    public static Date getDateDebut(long idProjet){
        Date dateDebutPrevue = null;
        List<Action> fetch = new Select().from(Action.class).as("Action")
                .join(Domaine.class).as("Domaine")
                .on("Domaine.id = Action.domaine")
                .join(Projet.class).as("Projet")
                .on("Projet.id = Domaine.projet")
                .where("Projet.id = ?", idProjet)
                .execute();
        for (Action action :fetch) {
            Date dtDebut = action.getDtDeb();
            if (dateDebutPrevue == null || dtDebut == null) {
                dateDebutPrevue = dtDebut;
            } else if (dtDebut.compareTo(dateDebutPrevue) == 0){
                dateDebutPrevue = dtDebut;
            }
        }
        return dateDebutPrevue;
    }
}