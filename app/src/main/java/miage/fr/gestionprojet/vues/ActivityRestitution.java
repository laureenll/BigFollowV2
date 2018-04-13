package miage.fr.gestionprojet.vues;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
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
    private List<String> listeDestinataires = new ArrayList<>();
    Intent intent ;
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
        List<Ressource> listeTmp = new ArrayList<>();
        for (Ressource ressource : allRessources) {
            if (!ressource.getInitiales().equals("")) {
                listeTmp.add(ressource);
            }
        }
        allRessources = listeTmp;
        final AdapterInitialesMultipleSelect usersAdapter = new AdapterInitialesMultipleSelect(this, R.layout.lst_view_users, allRessources);
        userList.setAdapter(usersAdapter);
        usersAdapter.notifyDataSetChanged();
        System.out.println("remplissage de la liste");

        userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                View viewById = view.findViewById(R.id.checkedTextView);
                if (viewById.getVisibility() == View.VISIBLE) {
                    viewById.setVisibility(View.GONE);
                    listeDestinataires.remove(position);
                } else {
                    viewById.setVisibility(View.VISIBLE);
                    listeDestinataires.add(allRessources.get(position).getEmail());
                }


            }
        });

        Button buttonLoadIdFileDefault = (Button) findViewById(R.id.buttonIdParDefaut);
        buttonLoadIdFileDefault.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText inputFile = findViewById(R.id.editTextIdFile);
                inputFile.setText(spreadsheetIdParDefaut);
            }
        });

        Button buttonSendEmail = (Button) findViewById(R.id.buttonSendMail);
        buttonSendEmail.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                intent = new Intent(Intent.ACTION_SEND);

                intent.putExtra(Intent.EXTRA_EMAIL, listeDestinataires.toArray());
                intent.putExtra(Intent.EXTRA_SUBJECT, "test");
                intent.putExtra(Intent.EXTRA_TEXT, spreadsheetIdParDefaut);

                intent.setType("message/rfc822");

                startActivity(Intent.createChooser(intent, "Select Email Sending App :"));
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.initial_utilisateur, menu);
        menu.findItem(R.id.initial_utilisateur).setTitle(initialUtilisateur);
        return true;
    }
}
