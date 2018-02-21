package miage.fr.gestionprojet.models.dao;

import com.reactiveandroid.query.Select;

import java.util.ArrayList;
import java.util.List;

import miage.fr.gestionprojet.models.Action;
import miage.fr.gestionprojet.models.Domaine;
import miage.fr.gestionprojet.models.Formation;
import miage.fr.gestionprojet.models.Projet;

/**
 * Created by Romain on 27/04/2017.
 */
public class DaoFormation {

    public static List<Formation> getFormations() {
        return Select.from(Formation.class).fetch();
    }

    public static Formation getFormation(long id) {
        return Select.from(Formation.class).where("id = ?", id).fetchSingle();
    }

    public static float getAvancementTotal (long idProjet){
        Projet proj = Select.from(Projet.class).where("id = ?", idProjet).fetchSingle();
        List<Domaine> lstDoms = proj.getLstDomaines();
        List<Action> lstActions = new ArrayList<>();
        for(Domaine d: lstDoms){
            lstActions.addAll(d.getLstActions());
        }
        List<Formation> lstFormation = new ArrayList<>();
        float avancementTotal = 0;
        for(Action a : lstActions){
            if(a.getTypeTravail().equalsIgnoreCase("Formation")){
                Formation form = Select.from(Formation.class).where("action = ?",a.getId()).fetchSingle();
                lstFormation.add(form);
                if(form!=null) {
                    avancementTotal += form.getAvancementTotal();
                }
            }
        }
        if(lstFormation.size()>0){
            avancementTotal /= lstFormation.size();
        }
        return avancementTotal;

    }
    public static List<Formation> loadAll() {
        return Select.from(Formation.class).fetch();
    }

}
