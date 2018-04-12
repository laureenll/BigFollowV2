package miage.fr.gestionprojet.vues;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import miage.fr.gestionprojet.R;
import miage.fr.gestionprojet.adapter.AdapterInitiales;
import miage.fr.gestionprojet.adapter.AdapterInitialesMultipleSelect;
import miage.fr.gestionprojet.adapter.FormationsAdapter;
import miage.fr.gestionprojet.models.Ressource;
import miage.fr.gestionprojet.models.dao.DaoRessource;

public class ActivityRestitution extends AppCompatActivity {

    private String initialUtilisateur;
    public final static String EXTRA_INITIAL = "initial";
    private ListView userList;
    private List<Ressource> allRessources;
    public static String spreadsheetIdParDefaut= "10JKhVbqrwQ8oKufdBXRoSLN6hGIDqtOsbbIKsLfipO4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restitution);
        Intent intentInitial = getIntent();
        initialUtilisateur = intentInitial.getStringExtra(EXTRA_INITIAL);
        userList = (ListView) findViewById(R.id.listeUsers);
        userList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        allRessources = DaoRessource.loadAll();
        AdapterInitialesMultipleSelect usersAdapter = new AdapterInitialesMultipleSelect(this, R.layout.lst_view_users, allRessources);
        userList.setAdapter(usersAdapter);
        usersAdapter.notifyDataSetChanged();
        System.out.println("remplissage de la liste");

        userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String initiales = allRessources.get(position).getInitiales();

            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.initial_utilisateur, menu);
        menu.findItem(R.id.initial_utilisateur).setTitle(initialUtilisateur);
        return true;
    }
}
