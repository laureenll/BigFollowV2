package miage.fr.gestionprojet.models.dao;

import com.reactiveandroid.query.Select;

import java.util.Date;
import java.util.List;

import miage.fr.gestionprojet.models.Projet;

/**
 * Created by Audrey on 23/01/2017.
 */

public class DaoProjet {

    public List<Projet> getProjetEnCours(Date dateDuJour){
        return Select
                .from(Projet.class)
                .where("date_fin_initiale>? or date_fin_reelle>?", dateDuJour.getTime(),dateDuJour.getTime())
                .fetch();
    }

    public static Projet loadById(long idProjet) {
        return Select.from(Projet.class).where("id=?", idProjet).fetchSingle();
    }

    public static List<Projet> loadAll(){
        return Select.from(Projet.class).fetch();
    }

    public static Date getDateFin(long idProjet){
//        Cursor c = ActiveAndroid
//                .getDatabase()
//                .rawQuery("SELECT max(a.dt_fin_prevue) FROM " + new Action().getTableName()
//                        + " a INNER JOIN "+ new Domaine().getTableName() + " d ON a.domaine = d.id INNER JOIN "
//                        +new Projet().getTableName() +" p ON d.projet = p.id WHERE p.id = "+idProjet, null);
//        Date dateFinPrevu;
//        if(c.moveToFirst()){
//            Calendar.getInstance().setTimeInMillis(c.getLong(0));
//            dateFinPrevu = Calendar.getInstance().getTime();
//            return dateFinPrevu;
//        }
        // TODO
        return null;
    }

    public static Date getDateDebut(long idProjet){
//        Cursor c = ActiveAndroid
//                .getDatabase()
//                .rawQuery("SELECT min(a.dt_debut) FROM " + new Action().getTableName()
//                        + " a INNER JOIN "+ new Domaine().getTableName() + " d ON a.domaine = d.id INNER JOIN "
//                        +new Projet().getTableName() +" p ON d.projet = p.id WHERE p.id = "+idProjet, null);
// TODO
//        Date dateDebut;
//        if(c.moveToFirst()){
//            Calendar.getInstance().setTimeInMillis(c.getLong(0));
//            dateDebut = Calendar.getInstance().getTime();
//            return dateDebut;
//        }
        return null;
    }
}