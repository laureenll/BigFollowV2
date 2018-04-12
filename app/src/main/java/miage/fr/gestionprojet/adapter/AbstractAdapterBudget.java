package miage.fr.gestionprojet.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import miage.fr.gestionprojet.R;
import miage.fr.gestionprojet.outils.Outils;
import miage.fr.gestionprojet.vues.ActivityBudget;

/**
 * Created by Khira on 12/04/2018.
 */

public abstract class AbstractAdapterBudget<T> extends ArrayAdapter<T> {

    private List<T> listBudget;
    private ActivityBudget activity;
    private ArrayList<Integer> lstNbActionsRealisees;
    private ArrayList<Integer> lstNbActions;

    public AbstractAdapterBudget(ActivityBudget context, int resource, List<T> objects) {
        super(context, resource, objects);
        this.activity = context;
        this.listBudget = objects;
        chargerNbAction();
    }

    public List<T> getListBudget() {
        return this.listBudget;
    }

    public List<Integer> getActionsRealisees() {
        return this.lstNbActionsRealisees;
    }

    public List<Integer> getActions() {
        return this.lstNbActions;
    }

    public void setLstNbActionsRealisees(ArrayList<Integer> lstNbActionsRealisees) {
        this.lstNbActionsRealisees = lstNbActionsRealisees;
    }

    public void setLstNbActions(ArrayList<Integer> lstNbActions) {
        this.lstNbActions = lstNbActions;
    }

    public void addActionsRealisees(Integer integer) {
        lstNbActionsRealisees.add(integer);
    }

    public void addActionsRealisees(int indice, Integer integer) {
        lstNbActionsRealisees.add(indice, integer);
    }

    public void addActions(Integer integer) {
        lstNbActions.add(integer);
    }

    public void addActions(int indice, Integer integer) {
        lstNbActions.add(indice, integer);
    }

    @Override
    public int getCount() {
        return listBudget.size();
    }

    @Override
    public T getItem(int position) {
        return listBudget.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AbstractAdapterBudget.ViewHolder holder;
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        // on récupère la vue à laquelle doit être ajouter l'image
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.lst_view_budget, parent, false);
            holder = new AbstractAdapterBudget.ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (AbstractAdapterBudget.ViewHolder) convertView.getTag();
        }

        // on définit le texte à afficher
        holder.domaine.setText(getItem(position).toString());

        int nbActionsRealisees = Integer.valueOf(this.lstNbActionsRealisees.get(position));
        int nbActionsTot = Integer.valueOf(this.lstNbActions.get(position));
        String nbActions = nbActionsRealisees + " €" + " / " + nbActionsTot+" €";
        holder.nbActionRealisees.setText(nbActions);

        holder.avancement.setProgress(Outils.calculerPourcentage(this.lstNbActionsRealisees.get(position),this.lstNbActions.get(position)));
        return convertView;
    }

    protected abstract void chargerNbAction();

    class ViewHolder {
        private TextView domaine;
        private TextView nbActionRealisees;
        private ProgressBar avancement;

        public ViewHolder(View v) {
            domaine = (TextView) v.findViewById(R.id.typeAffiche);
            nbActionRealisees = (TextView) v.findViewById(R.id.nbActionRealisees);
            avancement = (ProgressBar) v.findViewById(R.id.progress_bar_budget);
        }
    }

}
