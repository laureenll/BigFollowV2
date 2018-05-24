package miage.fr.gestionprojet.models.dao;

import com.activeandroid.query.Select;

import java.util.List;

import miage.fr.gestionprojet.models.Mesure;

/**
 * Created by Audrey on 27/02/2017.
 */

public class DaoMesure {

    private DaoMesure() {
    }

    public static Mesure getLastMesureBySaisieCharge(long idSaisieCharge){
        List<Mesure> lstMesures =
                new Select()
                .from(Mesure.class)
                .where("action=?", idSaisieCharge)
                .orderBy("dt_mesure DESC")
                .execute();
        if (!lstMesures.isEmpty()) {
            return  lstMesures.get(0);
        } else {
            return new Mesure();
        }
    }

    public static List<Mesure> getListtMesureByAction(long idSaisieCharge) {
        return new Select()
                .from(Mesure.class)
                .where("action=?", idSaisieCharge)
                .execute();
    }

    public static List<Mesure> loadAll() {
        return new Select().from(Mesure.class).execute();

    }

}
