package miage.fr.gestionprojet.vues;

import android.Manifest;
import android.accounts.AccountManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.ExponentialBackOff;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.sheets.v4.SheetsScopes;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import miage.fr.gestionprojet.R;
import miage.fr.gestionprojet.adapter.AdapterInitialesMultipleSelect;
import miage.fr.gestionprojet.models.Ressource;
import miage.fr.gestionprojet.models.dao.DaoRessource;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class ActivityRestitution extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {

    private String initialUtilisateur;
    public final static String EXTRA_INITIAL = "initial";

    private static final String PREF_ACCOUNT_NAME = "accountName";
    private static final int REQUEST_ACCOUNT_PICKER = 1000;
    private static final int REQUEST_AUTHORIZATION = 1001;
    private static final int REQUEST_PERMISSION_WRITE_STORAGE = 1002;
    private static final String[] SCOPES = {DriveScopes.DRIVE, DriveScopes.DRIVE_FILE, DriveScopes.DRIVE_READONLY, SheetsScopes.SPREADSHEETS };
    private static final String spreadsheetIdParDefaut= "10JKhVbqrwQ8oKufdBXRoSLN6hGIDqtOsbbIKsLfipO4";
    private GoogleAccountCredential credential;
    private AdapterInitialesMultipleSelect usersAdapter;
    private ProgressDialog mProgress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restitution);
        Intent intentInitial = getIntent();
        initialUtilisateur = intentInitial.getStringExtra(EXTRA_INITIAL);
        ListView userList = findViewById(R.id.listeUsers);
        userList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        List<Ressource> allRessources = DaoRessource.loadAll();
        List<Ressource> listeTmp = new ArrayList<>();
        for (Ressource ressource : allRessources) {
            if (!ressource.getInitiales().isEmpty() && !ressource.getEmail().isEmpty()) {
                listeTmp.add(ressource);
            }
        }
        allRessources = listeTmp;
        usersAdapter = new AdapterInitialesMultipleSelect(this, R.layout.lst_view_users, allRessources);
        userList.setAdapter(usersAdapter);
        usersAdapter.notifyDataSetChanged();
        System.out.println("remplissage de la liste");

        Button buttonLoadIdFileDefault = findViewById(R.id.buttonIdParDefaut);
        buttonLoadIdFileDefault.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText inputFile = findViewById(R.id.editTextIdFile);
                inputFile.setText(spreadsheetIdParDefaut);
            }
        });

        Button buttonSendEmail = findViewById(R.id.buttonSendMail);
        buttonSendEmail.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getPDFAndSendMail();
            }
        });

        mProgress = new ProgressDialog(this);
        mProgress.setMessage("Chargement  ...");

        credential = GoogleAccountCredential.usingOAuth2(
                getApplicationContext(), Arrays.asList(SCOPES))
                .setBackOff(new ExponentialBackOff());
    }

    @Override
    protected void onActivityResult(
            int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_ACCOUNT_PICKER:
                if (resultCode == RESULT_OK && data != null && data.getExtras() != null) {
                    String accountName = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
                    if (accountName != null) {
                        SharedPreferences settings = getPreferences(Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString(PREF_ACCOUNT_NAME, accountName);
                        editor.apply();
                        credential.setSelectedAccountName(accountName);
                        getPDFAndSendMail();
                    }
                }
                break;
            case REQUEST_AUTHORIZATION:
                if (resultCode == RESULT_OK) {
                    getPDFAndSendMail();
                }
                break;
        }
    }

    @AfterPermissionGranted(REQUEST_PERMISSION_WRITE_STORAGE)
    private void getPDFAndSendMail() {
        if (usersAdapter.getEmailSelected().size() == 0) {
            Toast.makeText(getBaseContext(), "Aucun destinataire selectionné", Toast.LENGTH_LONG).show();
            return;
        }

        if (!EasyPermissions.hasPermissions(
                this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            // Request the GET_ACCOUNTS permission via a user dialog
            EasyPermissions.requestPermissions(
                    this,
                    "This app needs to write to your external storage to format the pdf.",
                    REQUEST_PERMISSION_WRITE_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        if (credential.getSelectedAccountName() == null) {
            // Start a dialog from which the user can choose an account
            startActivityForResult(
                    credential.newChooseAccountIntent(),
                    REQUEST_ACCOUNT_PICKER);
        } else {
            new DownloadPDFTask().execute();
        }
    }

    private void sendMail(File pdf) {
        try {
            Uri pdfUri = Uri.fromFile(pdf);
            Intent intent = new Intent(Intent.ACTION_SEND);
            final String[] listeEmail = usersAdapter.getEmailSelected().toArray(new String[0]);
            intent.putExtra(Intent.EXTRA_EMAIL, listeEmail);
            intent.putExtra(Intent.EXTRA_SUBJECT, "test");
            intent.putExtra(Intent.EXTRA_STREAM, pdfUri);
            intent.setType("message/rfc822");

            startActivity(Intent.createChooser(intent, "Select Email Sending App :"));
        } catch (Exception e) {
            Log.e("error", e.getMessage(), e);
            Toast.makeText(getBaseContext(), "Une erreur est survenue", Toast.LENGTH_LONG).show();
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.initial_utilisateur, menu);
        menu.findItem(R.id.initial_utilisateur).setTitle(initialUtilisateur);
        return true;
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

    }

    private class DownloadPDFTask extends AsyncTask<Void, Void, File> {
        private Exception mLastError = null;

        @Override
        protected void onPreExecute() {
            mProgress.show();
        }

        @Override
        protected File doInBackground(Void... voids) {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            try {
                HttpTransport HTTP_TRANSPORT = AndroidHttp.newCompatibleTransport();
                JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
                Drive driveService = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential).build();
                EditText inputFile = findViewById(R.id.editTextIdFile);
                String idSpreadSheet = !inputFile.getText().toString().equals("") ? inputFile.getText().toString() : spreadsheetIdParDefaut;
                Drive.Files.Export export = driveService.files().export(idSpreadSheet, "application/pdf");
                export.executeMediaAndDownloadTo(outputStream);
            } catch (IOException e) {
                Log.e("error", e.getMessage(), e);
                mLastError = e;
                cancel(true);
            }

            FileOutputStream fos = null;
            File pdf = null;
            try {
                pdf = new File(Environment.getExternalStorageDirectory() + File.separator + UUID.randomUUID() + "_export.pdf");
                if (pdf.createNewFile()) {
                    fos = new FileOutputStream(pdf);
                    outputStream.writeTo(fos);
                }
            } catch(IOException ioe) {
                // Handle exception here
                Log.e("error", ioe.getMessage(), ioe);
            } finally {
                try {
                    if (fos != null) {
                        fos.close();
                    }
                } catch (IOException e) {
                    Log.e("error", e.getMessage(), e);
                }
            }

            return pdf;
        }

        @Override
        protected void onPostExecute(File file) {
            mProgress.hide();

            if (file == null) {
                Toast.makeText(getBaseContext(), "Une erreur est survenue", Toast.LENGTH_LONG).show();
            } else {
                sendMail(file);
            }
        }

        @Override
        protected void onCancelled() {
            mProgress.hide();

            if (mLastError != null) {
                if (mLastError instanceof UserRecoverableAuthIOException) {
                    startActivityForResult(
                            ((UserRecoverableAuthIOException) mLastError).getIntent(),
                            REQUEST_AUTHORIZATION);
                } else {
                    Toast.makeText(getBaseContext(), "Une erreur est survenue", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
