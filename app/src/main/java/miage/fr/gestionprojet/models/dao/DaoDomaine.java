package miage.fr.gestionprojet.models.dao;

import com.reactiveandroid.query.Select;

import java.util.List;

import miage.fr.gestionprojet.models.Domaine;

/**
 * Created by Audrey on 26/04/2017.
 */

public class DaoDomaine {

    public static List<Domaine> loadAll(){
        return Select
                .from(Domaine.class)
                .fetch();
    }

    public static Domaine getByName(String name){
        List<Domaine> lst = Select
                .from(Domaine.class)
                .where("nom = ?",name)
                .fetch();
        if(lst.size()>0){
            return lst.get(0);
        }else{
            return null;
        }
    }
}
