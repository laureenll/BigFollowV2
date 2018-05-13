package miage.fr.gestionprojet.vues;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.drive.Drive;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import miage.fr.gestionprojet.R;
import miage.fr.gestionprojet.adapter.AdapterInitialesMultipleSelect;
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
        List<Ressource> listeTmp = new ArrayList<>();
        for (Ressource ressource : allRessources) {
            if (!ressource.getInitiales().isEmpty() && !ressource.getEmail().isEmpty()) {
                listeTmp.add(ressource);
            }
        }
        allRessources = listeTmp;
        final AdapterInitialesMultipleSelect usersAdapter = new AdapterInitialesMultipleSelect(this, R.layout.lst_view_users, allRessources);
        userList.setAdapter(usersAdapter);
        usersAdapter.notifyDataSetChanged();
        System.out.println("remplissage de la liste");

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
                if (usersAdapter.getEmailSelected().size() == 0) {
                    Toast.makeText(getBaseContext(), "Aucun destinataire selectionné", Toast.LENGTH_LONG).show();
                    return;
                }
                Intent intent = new Intent(Intent.ACTION_SEND);
                final String[] listeEmail = usersAdapter.getEmailSelected().toArray(new String[0]);
                intent.putExtra(Intent.EXTRA_EMAIL, listeEmail);
                intent.putExtra(Intent.EXTRA_SUBJECT, "test");
                intent.putExtra(Intent.EXTRA_TEXT, spreadsheetIdParDefaut);

                intent.setType("message/rfc822");

                startActivity(Intent.createChooser(intent, "Select Email Sending App :"));
            }
        });
    }

    /*public void exportSpreadsheet() {
        OutputStream outputStream = new ByteArrayOutputStream();
        Drive driveService = new Drive.Builder(TRANSPORT, JSON_FACTORY, credential).build();
        driveService.files().export(spreadsheetIdParDefaut, "application/pdf")
                .executeMediaAndDownloadTo(outputStream);

    }*/

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.initial_utilisateur, menu);
        menu.findItem(R.id.initial_utilisateur).setTitle(initialUtilisateur);
        return true;
    }
}
