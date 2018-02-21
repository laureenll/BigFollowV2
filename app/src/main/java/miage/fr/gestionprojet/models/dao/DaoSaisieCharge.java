package miage.fr.gestionprojet.models.dao;

import com.reactiveandroid.query.Select;

import java.util.ArrayList;
import java.util.List;

import miage.fr.gestionprojet.models.Action;
import miage.fr.gestionprojet.models.Domaine;
import miage.fr.gestionprojet.models.Mesure;
import miage.fr.gestionprojet.models.Projet;
import miage.fr.gestionprojet.models.SaisieCharge;

/**
 * Created by Audrey on 07/04/2017.
 */

public class DaoSaisieCharge {


    public static List<SaisieCharge> loadSaisiebyAction(Action action) {
        return Select.from(SaisieCharge.class).where("action=?",action.getId()).fetch();

    }
    public static List<SaisieCharge> loadAll() {
        return Select.from(SaisieCharge.class).fetch();
    }

    public static SaisieCharge loadById(long id) {
        return Select.from(SaisieCharge.class).where("id=?", id).fetchSingle();
    }

    public static List<SaisieCharge> loadSaisieChargesByDomaine(int idDomaine){
        List<SaisieCharge> lst = new ArrayList<>();
        List<Action> results = Select
                .from(Action.class)
                .where("domaine=?",idDomaine)
                .fetch();

        for(Action a : results) {
            if(a.getTypeTravail().equalsIgnoreCase("Saisie")||a.getTypeTravail().equalsIgnoreCase("Test")) {
                SaisieCharge result = Select
                        .from(SaisieCharge.class)
                        .where("domaine=?", a.getId())
                        .fetch().get(0);
                lst.add(result);
            }
        }

        return lst;
    }

    public static List<SaisieCharge> loadSaisieChargeByUtilisateur(int idUser){
        List<SaisieCharge> lst = new ArrayList<>();
        List<Action> results = Select
                .from(Action.class)
                .where("resp_ouv=? or resp_oeu=?",idUser,idUser)
                .fetch();
        for(Action a : results) {
            if(a.getTypeTravail().equalsIgnoreCase("Saisie")||a.getTypeTravail().equalsIgnoreCase("Test")) {
                SaisieCharge result = Select
                        .from(SaisieCharge.class)
                        .where("domaine=?", a.getId())
                        .fetch().get(0);
                lst.add(result);
            }

        }
        return lst;
    }

    public static SaisieCharge loadSaisieChargeByAction(long idAction){
        List<SaisieCharge> lst = Select
                .from(SaisieCharge.class)
                .where("action = ?", idAction)
                .fetch();
        if(lst.size()>0) {
            return lst.get(0);
        }else{
            return null;
        }
    }

    public static int getNbUnitesSaisies(long idProjet){
        Projet projet = Select.from(Projet.class).where("id=?",idProjet).fetchSingle();
        List<Domaine> doms = projet.getLstDomaines();
        ArrayList<Action> lstActions = new ArrayList<>();
        for(Domaine d : doms){
            lstActions.addAll(d.getLstActions());
        }
        int nbUnitesSaisies = 0;
        for(Action a : lstActions){
            if(a.getTypeTravail().equalsIgnoreCase("Saisie")||a.getTypeTravail().equalsIgnoreCase("Test")){
                SaisieCharge s = DaoSaisieCharge.loadSaisieChargeByAction(a.getId());
                if(s!=null) {
                    Mesure m = DaoMesure.getLastMesureBySaisieCharge(s.getId());
                    nbUnitesSaisies += m.getNbUnitesMesures();
                }
            }
        }
        return nbUnitesSaisies;
    }

    public static int getNbUnitesCibles(long idProjet){
        Projet projet = Select.from(Projet.class).where("id=?",idProjet).fetchSingle();
        List<Domaine> doms = projet.getLstDomaines();
        ArrayList<Action> lstActions = new ArrayList<>();
        for(Domaine d : doms){
            lstActions.addAll(d.getLstActions());
        }
        int nbUnitesCibles = 0;
        for(Action a : lstActions){
            if(a.getTypeTravail().equalsIgnoreCase("Saisie")||a.getTypeTravail().equalsIgnoreCase("Test")){
                SaisieCharge s = DaoSaisieCharge.loadSaisieChargeByAction(a.getId());
                if(s!=null) {
                    nbUnitesCibles += s.getNbUnitesCibles();
                }
            }
        }
        return nbUnitesCibles;
    }

}
