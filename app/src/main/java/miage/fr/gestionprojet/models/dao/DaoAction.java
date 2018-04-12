package miage.fr.gestionprojet.models.dao;

import android.database.Cursor;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import miage.fr.gestionprojet.models.Action;
import miage.fr.gestionprojet.models.Domaine;
import miage.fr.gestionprojet.models.Projet;

/**
 * Created by Audrey on 07/04/2017.
 */

public class DaoAction {
    public static List<Action> loadActionsByCode(String code) {
        //getAll
        return new Select().from(Action.class)
                .where("code=?",code)
                .execute();
    }

    public static List<Action> loadActionsByType(String type, long idProjet) {
        Projet proj = new Select().from(Projet.class).where("id=?", idProjet).executeSingle();
        List<Action> lstActions = new ArrayList<>();

        for(Domaine d : proj.getLstDomaines()) {
            List<Action> actions = new Select().from(Action.class)
                    .where("typeTravail = ? and domaine=?", type, d.getId())
                    .execute();
            lstActions.addAll(actions);
        }

        return lstActions;
    }

    public static List<Action> loadActionsByPhaseAndDate(String phase,Date d, long idProjet) {
        Projet proj = new Select().from(Projet.class).where("id=?", idProjet).executeSingle();
        List<Action> lstActions = new ArrayList<>();
        for(Domaine dom : proj.getLstDomaines()) {
            List<Action> actions = new Select().from(Action.class)
                    .where("phase = ? and dt_fin_prevue>=? and dt_debut<=? and domaine=?", phase, d.getTime(), d.getTime(), dom.getId())
                    .execute();
            lstActions.addAll(actions);
        }
        return lstActions;
    }

    public static List<Action> loadActionsByDate(Date d, long idProjet) {
        Projet proj = new Select().from(Projet.class).where("id=?", idProjet).executeSingle();
        List<Action> lstActions = new ArrayList<>();
        for(Domaine dom : proj.getLstDomaines()) {
            List<Action> actions = new Select()
                    .from(Action.class)
                    .where("dt_fin_prevue>=? and dt_debut<=? and domaine = ?", d.getTime(), d.getTime(), dom.getId())
                    .execute();
            lstActions.addAll(actions);
        }
        return lstActions;
    }

    public static List<Action> loadAll(){
        return new Select().from(Action.class).execute();
    }

    public static List<Action> loadActionsOrderByNomAndDate(Date d, long idProjet){
        Projet proj = new Select().from(Projet.class).where("id=?", idProjet).executeSingle();
        List<Action> lstActions = new ArrayList<>();
        for(Domaine dom : proj.getLstDomaines()) {
            List<Action> actions = new Select()
                    .from(Action.class)
                    .where("dt_fin_prevue>=? and dt_debut<=? and domaine=?", d.getTime(), d.getTime(), dom.getId())
                    .orderBy("code ASC")
                    .execute();
            lstActions.addAll(actions);
        }
        return lstActions;
    }

    public static List<Action> loadActionsByDomaineAndDate(int idDomaine,Date d, long idProjet){
        Projet proj = new Select().from(Projet.class).where("id=?", idProjet).executeSingle();
        List<Action> lstActions = new ArrayList<>();
        for(Domaine dom : proj.getLstDomaines()) {
            List<Action> result = new Select()
                    .from(Action.class)
                    .where("domaine=? and dt_fin_prevue>=? and dt_debut<=? and domaine=?", idDomaine, d.getTime(), d.getTime(),dom.getId())
                    .execute();
            lstActions.addAll(result);
        }
        return lstActions;
    }
    public static List<Action> getActionbyCode(String id) {
        return new Select()
                .from(Action.class)
                .where("code = ?", id)
                .execute();
    }

    public static HashMap<String,Integer> getBudgetTotalByActionRealiseeGroupByDomaine(){
        Cursor c = ActiveAndroid
                .getDatabase()
                .rawQuery("SELECT SUM(cout_par_pour * nb_jours_prevus) as somme_cout_par_jour,domaine FROM 'Action' WHERE reste_a_faire=0 GROUP BY domaine", null);
        HashMap<String,Integer> lstResult = new HashMap<>();

        try {
            while (c.moveToNext()) {
                lstResult.put(c.getString(1),c.getInt(0));
            }
        } finally {
            c.close();
        }

        return lstResult;
    }

    public static HashMap<String,Integer> getBudgetTotalByActionTotalGroupByDomaine(){
        Cursor c = ActiveAndroid
                .getDatabase()
                .rawQuery("SELECT SUM(cout_par_pour * nb_jours_prevus) as somme_cout_par_jour,domaine FROM 'Action' GROUP BY domaine", null);
        HashMap<String,Integer> lstResult = new HashMap<>();

        try {
            while (c.moveToNext()) {
                lstResult.put(c.getString(1),c.getInt(0));
            }
        } finally {
            c.close();
        }

        return lstResult;
    }



    public static HashMap<String,Integer> getBudgetTotalByActionRealiseeGroupByTypeTravail(){
        Cursor c = ActiveAndroid
                .getDatabase()
                .rawQuery("SELECT SUM(cout_par_pour * nb_jours_prevus) as somme_cout_par_jour,typeTravail FROM 'Action' WHERE reste_a_faire=0 GROUP BY typeTravail", null);
        HashMap<String,Integer> lstResult = new HashMap<>();

        try {
            while (c.moveToNext()) {
                lstResult.put(c.getString(1),c.getInt(0));
            }
        } finally {
            c.close();
        }

        return lstResult;
    }

    public static HashMap<String,Integer> getBudgetTotalByActionTotalGroupByTypeTravail(){
        Cursor c = ActiveAndroid
                .getDatabase()
                .rawQuery("SELECT SUM(cout_par_pour * nb_jours_prevus) as somme_cout_par_jour,typeTravail FROM 'Action' GROUP BY typeTravail", null);
        HashMap<String,Integer> lstResult = new HashMap<>();

        try {
            while (c.moveToNext()) {
                lstResult.put(c.getString(1),c.getInt(0));
            }
        } finally {
            c.close();
        }

        return lstResult;
    }

    public static HashMap<String,Integer> getBudgetTotalByActionRealiseeGroupByActions(){
        Cursor c = ActiveAndroid
                .getDatabase()
                .rawQuery("SELECT SUM(cout_par_pour * nb_jours_prevus) as somme_cout_par_jour,code FROM 'Action' WHERE reste_a_faire=0 GROUP BY code", null);
        HashMap<String,Integer> lstResult = new HashMap<>();

        try {
            while (c.moveToNext()) {
                lstResult.put(c.getString(1),c.getInt(0));
            }
        } finally {
            c.close();
        }

        return lstResult;
    }

    public static HashMap<String,Integer> getBudgetTotalByActionTotalGroupByActions(){
        Cursor c = ActiveAndroid
                .getDatabase()
                .rawQuery("SELECT SUM(cout_par_pour * nb_jours_prevus) as somme_cout_par_jour,code FROM 'Action' GROUP BY code", null);
        HashMap<String,Integer> lstResult = new HashMap<>();

        try {
            while (c.moveToNext()) {
                lstResult.put(c.getString(1),c.getInt(0));
            }
        } finally {
            c.close();
        }

        return lstResult;
    }

    public static List<String> getLstTypeTravail(){
        Cursor c = ActiveAndroid
                .getDatabase()
                .rawQuery("SELECT DISTINCT(typeTravail) FROM 'Action'", null);
        List<String> lstResults = new ArrayList<>();

        try {
            while (c.moveToNext()) {
                lstResults.add(c.getString(0));
            }
        } finally {
            c.close();
        }

        return lstResults;
    }

    public static List<String> getAllCodes(){
        Cursor c = ActiveAndroid
                .getDatabase()
                .rawQuery("SELECT DISTINCT(code) FROM 'Action'", null);
        List<String> lstResults = new ArrayList<>();

        try {
            while (c.moveToNext()) {
                lstResults.add(c.getString(0));
            }
        } finally {
            c.close();
        }

        return lstResults;
    }

    public static HashMap<String,Integer> getBudgetTotalByActionRealiseeGroupByUtilisateurOeu(){
        Cursor c = ActiveAndroid
                .getDatabase()
                .rawQuery("SELECT SUM(cout_par_pour * nb_jours_prevus) as somme_cout_par_jour,resp_oeu FROM 'Action' WHERE reste_a_faire=0 GROUP BY resp_oeu", null);
        HashMap<String,Integer> lstResult = new HashMap<>();

        try {
            while (c.moveToNext()) {
                lstResult.put(c.getString(1),c.getInt(0));
            }
        } finally {
            c.close();
        }

        return lstResult;
    }

    public static HashMap<String,Integer> getBudgetTotalByActionTotalGroupByUtilisateurOeu(){
        Cursor c = ActiveAndroid
                .getDatabase()
                .rawQuery("SELECT SUM(cout_par_pour * nb_jours_prevus) as somme_cout_par_jour,resp_oeu FROM 'Action' GROUP BY resp_oeu", null);
        HashMap<String,Integer> lstResult = new HashMap<>();

        try {
            while (c.moveToNext()) {
                lstResult.put(c.getString(1),c.getInt(0));
            }
        } finally {
            c.close();
        }

        return lstResult;
    }

    public static HashMap<String,Integer> getBudgetTotalByActionRealiseeGroupByUtilisateurOuv(){
        Cursor c = ActiveAndroid
                .getDatabase()
                .rawQuery("SELECT SUM(cout_par_pour * nb_jours_prevus) as somme_cout_par_jour,resp_ouv FROM 'Action' WHERE reste_a_faire=0 GROUP BY resp_ouv", null);
        HashMap<String,Integer> lstResult = new HashMap<>();

        try {
            while (c.moveToNext()) {
                lstResult.put(c.getString(1),c.getInt(0));
            }
        } finally {
            c.close();
        }

        return lstResult;
    }

    public static HashMap<String,Integer> getBudgetTotalByActionTotalGroupByUtilisateurOuv(){
        Cursor c = ActiveAndroid
                .getDatabase()
                .rawQuery("SELECT SUM(cout_par_pour * nb_jours_prevus) as somme_cout_par_jour,resp_ouv FROM 'Action' GROUP BY resp_ouv", null);
        HashMap<String,Integer> lstResult = new HashMap<>();

        try {
            while (c.moveToNext()) {
                lstResult.put(c.getString(1),c.getInt(0));
            }
        } finally {
            c.close();
        }

        return lstResult;
    }

    public static List<Action> getActionRealiseesByProjet(long idProjet){
        Projet proj = new Select().from(Projet.class).where("id=?", idProjet).executeSingle();
        List<Domaine> lstDomaines = proj.getLstDomaines();
        List<Action> lstActionRealisees = new ArrayList<>();
        List<Action> lstActionRecuperees;

        for(Domaine d: lstDomaines){
            lstActionRecuperees =
                    new Select()
                    .from(Action.class)
                    .where("reste_a_faire=0 and domaine=?",d.getId())
                    .execute();
            lstActionRealisees.addAll(lstActionRecuperees);
        }
        return lstActionRealisees;
    }

    public static List<Action> getAllActionsByProjet(long idProjet){
        Projet proj = new Select().from(Projet.class).where("id=?", idProjet).executeSingle();
        List<Domaine> lstDomaines = proj.getLstDomaines();
        List<Action> lstAction = new ArrayList<>();
        List<Action> lstActionRecuperees;
        for(Domaine d: lstDomaines){
            lstActionRecuperees = new Select()
                    .from(Action.class)
                    .where("domaine=?",d.getId())
                    .execute();
            lstAction.addAll(lstActionRecuperees);
        }
        return lstAction;
    }




}
