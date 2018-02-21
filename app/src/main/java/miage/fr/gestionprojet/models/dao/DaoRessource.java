package miage.fr.gestionprojet.models.dao;

import com.reactiveandroid.query.Select;

import java.util.ArrayList;
import java.util.List;

import miage.fr.gestionprojet.models.Ressource;

/**
 * Created by gamouzou on 01/03/2017.
 */

public class DaoRessource {


    public static List<Ressource> loadAll(){
        return Select
                .from(Ressource.class)
                .fetch();
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

    public List<String> getAllRessourceInitials(){
        List<Ressource> listeRessource = loadAll();
        List<String> listeInitials = new ArrayList<>();

        for ( int i=0; i < listeRessource.size();i++){
            if (!listeRessource.get(i).getInitiales().equals("") && listeRessource.get(i).getInitiales().length()>0) {
                listeInitials.add(listeRessource.get(i).getInitiales());
            }
        }
        return listeInitials;
    }

    public static Ressource getRessourceByInitial(String initiales){
        List<Ressource> lst = Select
                .from(Ressource.class)
                .where("initiales = ?", initiales)
                .fetch();
        if(lst.size()>0){
            return lst.get(0);
        }else{
            return null;
        }
    }
}
