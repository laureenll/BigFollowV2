package miage.fr.gestionprojet.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import miage.fr.gestionprojet.models.Domaine;
import miage.fr.gestionprojet.models.dao.DaoAction;
import miage.fr.gestionprojet.vues.ActivityBudget;

/**
 * Created by Audrey on 25/04/2017.
 */

public class AdapterBudgetDomaine extends AbstractAdapterBudget<Domaine> {

    public AdapterBudgetDomaine(ActivityBudget context,  int resource,  List<Domaine> objects) {
        super(context, resource, objects);
    }

  @Override
    protected void chargerNbAction(){
        setLstNbActions(new ArrayList<Integer>());
        setLstNbActionsRealisees(new ArrayList<Integer>());
        HashMap<String, Integer> results= DaoAction.getBudgetTotalByActionRealiseeGroupByDomaine();
        if(results.size()>0){
            for(Domaine d : getListBudget()){
                if(results.get(String.valueOf(d.getId()))!=null) {
                    addActionsRealisees(results.get(String.valueOf(d.getId())));
                }else{
                    addActionsRealisees(0);
                }
            }

        }

        results= DaoAction.getBudgetTotalByActionTotalGroupByDomaine();
        if(results.size()>0){
            for(Domaine d : getListBudget()){
                if(results.get(String.valueOf(d.getId()))!=null) {
                    addActions(results.get(String.valueOf(d.getId())));
                }else{
                    addActions(0);
                }
            }

        }
    }

}

