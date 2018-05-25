package miage.fr.gestionprojet.vues;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import miage.fr.gestionprojet.R;
import miage.fr.gestionprojet.adapter.AdapterInitiales;
import miage.fr.gestionprojet.models.Ressource;
import miage.fr.gestionprojet.models.dao.DaoRessource;

/**
 * Created by gamouzou on 01/03/2017.
 */

public class ActivityGestionDesInitials extends AppCompatActivity {

    private List<Ressource> lstRessourceInitials = null;
    public static final String EXTRA_INITIAL = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_des_initials);

        //on récupère la liste des ressources
        lstRessourceInitials = DaoRessource.loadAll();
        // liste de ressources tmp
        List<Ressource> listeTmpRessources = new ArrayList<>();
        for (Ressource ressource :lstRessourceInitials) {
            if (!ressource.getInitiales().equals("")) {
                listeTmpRessources.add(ressource);
            }
        }
        lstRessourceInitials = listeTmpRessources;
        ListView liste = findViewById(R.id.listViewInitials);

        // si le nombre de ressource est supérieur à 1 on affiche une liste
        if(!lstRessourceInitials.isEmpty()) {
            //on affiche cette liste
            final ArrayAdapter<Ressource> adapter2 = new AdapterInitiales(this, R.layout.list_view_initiales, lstRessourceInitials);
            liste.setAdapter(adapter2);

            liste.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    Intent intent = new Intent(ActivityGestionDesInitials.this, MainActivity.class);
                    intent.putExtra(EXTRA_INITIAL, (lstRessourceInitials.get(position).getInitiales()));
                    startActivity(intent);
                }
            });
        }else{
                // sinon on affiche un message indiquand qu'il n'y a aucun projet en cours
                ArrayList<String> list = new ArrayList<>(1);
                list.add("Cliquez ici !!");
                final ArrayAdapter<String> adapter_2 = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,list);
                liste.setAdapter(adapter_2);

            liste.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    Intent intent = new Intent(ActivityGestionDesInitials.this, MainActivity.class);
                    intent.putExtra(EXTRA_INITIAL, "");
                    startActivity(intent);
                }
            });
        }
        }



    }

