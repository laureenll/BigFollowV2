package miage.fr.gestionprojet.models.dao;

import com.reactiveandroid.query.Select;

import java.util.List;

import miage.fr.gestionprojet.models.Mesure;

/**
 * Created by Audrey on 27/02/2017.
 */

public class DaoMesure {

    public static Mesure getLastMesureBySaisieCharge(long idSaisieCharge){
        List<Mesure> lstMesures =
                Select
                .from(Mesure.class)
                .where("action=?", idSaisieCharge)
                .orderBy("dt_mesure DESC")
                .fetch();
        if (lstMesures.size() > 0) {
            return  lstMesures.get(0);
        } else {
            return new Mesure();
        }
    }

    public static List<Mesure> getListtMesureByAction(long idSaisieCharge) {
        return Select
                .from(Mesure.class)
                .where("action=?", idSaisieCharge)
                .fetch();
    }

    public static List<Mesure> loadAll() {
        return Select.from(Mesure.class).fetch();

    }

}
