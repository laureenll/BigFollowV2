package miage.fr.gestionprojet.models.dao;

import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.List;

import miage.fr.gestionprojet.models.Ressource;

/**
 * Created by gamouzou on 01/03/2017.
 */

public class DaoRessource {


    public static List<Ressource> loadAll(){
        return new Select()
                .from(Ressource.class)
                .execute();
    }

    public  List<Ressource> loadAllWithInitialNotEmpty(){

        List<Ressource> listeRessource = loadAll();
        List<Ressource> listeRessourceFinal = new ArrayList<>();

        for ( int i=0; i < listeRessource.size();i++){
            if (!listeRessource.get(i).getInitiales().equals("") && listeRessource.get(i).getInitiales().length()>0) {
                listeRessourceFinal.add(listeRessource.get(i));
            }
        }
        return listeRessourceFinal;
    }

    public static boolean isRessourceExists(String email){

        List<Ressource> listeRessourceFind = new Select().from(Ressource.class).where("email = ?", email).execute();

        return !listeRessourceFind.isEmpty();
    }

    public static Ressource getRessourceByInitial(String initiales){
        List<Ressource> lst = new Select()
                .from(Ressource.class)
                .where("initiales = ?", initiales)
                .execute();
        if(!lst.isEmpty()){
            return lst.get(0);
        }else{
            return null;
        }
    }
}
