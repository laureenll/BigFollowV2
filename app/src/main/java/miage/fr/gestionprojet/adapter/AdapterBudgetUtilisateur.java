package miage.fr.gestionprojet.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import miage.fr.gestionprojet.models.Ressource;
import miage.fr.gestionprojet.models.dao.DaoAction;
import miage.fr.gestionprojet.vues.ActivityBudget;

/**
 * Created by Audrey on 25/04/2017.
 */

public class AdapterBudgetUtilisateur extends AbstractAdapterBudget<Ressource> {

    public AdapterBudgetUtilisateur(ActivityBudget context, int resource, List<Ressource> objects) {
        super(context, resource, objects);
    }

    @Override
    protected void chargerNbAction() {
        setLstNbActions(new ArrayList<Integer>());
        setLstNbActionsRealisees(new ArrayList<Integer>());
        Map<String, Integer> results = DaoAction.getBudgetTotalByActionRealiseeGroupByUtilisateurOeu();
        if (results.size() > 0) {
            for (Ressource r : getListBudget()) {
                if (results.get(String.valueOf(r.getId())) != null) {
                    addActionsRealisees(results.get(String.valueOf(r.getId())));
                } else {
                    addActionsRealisees(0);
                }
            }
        }

        results = DaoAction.getBudgetTotalByActionRealiseeGroupByUtilisateurOuv();
        if (results.size() > 0) {
            for (int i = 0; i < getListBudget().size(); i++) {
                if (results.get(String.valueOf(getListBudget().get(i).getId())) != null) {
                    addActionsRealisees(i, getActionsRealisees().get(i) + results.get(String.valueOf(getListBudget().get(i).getId())));
                } else {
                    addActionsRealisees(0);
                }
            }
        }

        results = DaoAction.getBudgetTotalByActionTotalGroupByUtilisateurOeu();
        if (results.size() > 0) {
            for (Ressource r : getListBudget()) {
                if (results.get(String.valueOf(r.getId())) != null) {
                    addActions(results.get(String.valueOf(r.getId())));
                } else {
                    addActions(0);
                }
            }
        }

        results = DaoAction.getBudgetTotalByActionTotalGroupByUtilisateurOuv();
        if (results.size() > 0) {
            for (int i = 0; i < getListBudget().size(); i++) {
                if (results.get(String.valueOf(getListBudget().get(i).getId())) != null) {
                    addActions(i, getActions().get(i) + results.get(String.valueOf(getListBudget().get(i).getId())));
                } else {
                    addActions(0);
                }
            }

        }
    }

}
