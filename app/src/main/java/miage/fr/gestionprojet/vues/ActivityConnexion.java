package miage.fr.gestionprojet.vues;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.util.ExponentialBackOff;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.api.services.sheets.v4.model.ValueRange;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import miage.fr.gestionprojet.R;

import static miage.fr.gestionprojet.vues.ActivityGestionDesInitials.EXTRA_INITIAL;
import static miage.fr.gestionprojet.vues.ChargementDonnees.REQUEST_GOOGLE_PLAY_SERVICES;

public class ActivityConnexion extends AppCompatActivity implements View.OnClickListener {

    private GoogleAccountCredential mCredential;
    private GoogleSignInClient mGoogleSignInClient;
    private static final int SIGN_IN_CODE = 0;
    private static final int PROFILE_PIC_SIZE = 120;
    private ProgressDialog progressDialog;

    private static final String[] SCOPES = {SheetsScopes.SPREADSHEETS};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_connexion);
        //Customize sign-in button.a red button may be displayed when Google+ scopes are requested
        setBtnClickListeners();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Chargement....");

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // Initialize credentials and service object.
        mCredential = GoogleAccountCredential.usingOAuth2(
                getApplicationContext(), Arrays.asList(SCOPES))
                .setBackOff(new ExponentialBackOff());
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Check for existing Google Sign In account
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        // if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        if (account != null) {
            changeActivity(account);
        }
    }

    private boolean isDeviceOnline() {
        ConnectivityManager connMgr =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    private boolean isGooglePlayServicesAvailable() {
        GoogleApiAvailability apiAvailability =
                GoogleApiAvailability.getInstance();
        final int connectionStatusCode =
                apiAvailability.isGooglePlayServicesAvailable(this);
        return connectionStatusCode == ConnectionResult.SUCCESS;
    }

    private void acquireGooglePlayServices() {
        GoogleApiAvailability apiAvailability =
                GoogleApiAvailability.getInstance();
        final int connectionStatusCode =
                apiAvailability.isGooglePlayServicesAvailable(this);
        if (apiAvailability.isUserResolvableError(connectionStatusCode)) {
            showGooglePlayServicesAvailabilityErrorDialog(connectionStatusCode);
        }
    }

    void showGooglePlayServicesAvailabilityErrorDialog(
            final int connectionStatusCode) {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        Dialog dialog = apiAvailability.getErrorDialog(this, connectionStatusCode, REQUEST_GOOGLE_PLAY_SERVICES);
        dialog.show();
    }

    /*
      Set on click Listeners on the sign-in sign-out and disconnect buttons
     */

    private void setBtnClickListeners(){
        // Button listeners
        findViewById(R.id.sign_in_button).setOnClickListener(this);
        findViewById(R.id.sign_out_button).setOnClickListener(this);
        findViewById(R.id.frnd_button).setOnClickListener(this);
        findViewById(R.id.disconnect_button).setOnClickListener(this);
    }

    /*
      Will receive the activity result and check which request we are responding to
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        // Check which request we're responding to
        if (requestCode == SIGN_IN_CODE) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        } else {
            progressDialog.dismiss();
        }

    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Update the UI after signin
            changeUI();

            // Change activity
            changeActivity(account);

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            System.out.println(e.getMessage());
            changeUI();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                progressDialog.show();
                signIn();
                break;

            case R.id.sign_out_button:
                progressDialog.show();
                signOut();
                break;

            case R.id.disconnect_button:
                progressDialog.show();
                revokeAccess();
            default:
                break;
        }
    }

    /*
      Sign-in into the Google + account
     */
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, SIGN_IN_CODE);
    }

    /*
      Sign-out from Google+ account
     */
    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                    }
                });
    }

    /*
      Disconnect accounts
     */
    private void revokeAccess() {
        mGoogleSignInClient.revokeAccess()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                    }
                });
    }

    private void changeActivity(GoogleSignInAccount account) {
        String initial = "";
        if (account.getGivenName().length() > 0 && account.getFamilyName().length() > 0) {
            initial = account.getGivenName().substring(0, 1) + account.getFamilyName().substring(0, 1);
        }
        mCredential.setSelectedAccountName(account.getDisplayName());

        // écriture du nouvel utilisateur dans le fichier google spread sheet
        List<List<Object>> values = new ArrayList<>();
        List<Object> data1 = new ArrayList<>();
        data1.add(initial);
        data1.add(account.getGivenName());
        data1.add(account.getFamilyName());
        data1.add("test");
        data1.add("test");
        data1.add(account.getEmail());
        values.add(data1);
        ValueRange body = new ValueRange().setValues(values);

        Log.d("user connected","connected");
        Intent intent = new Intent(ActivityConnexion.this, ActivityGestionDesInitials.class);
        intent.putExtra(EXTRA_INITIAL, initial);
        startActivity(intent);
        progressDialog.hide();
    }

    /*
     set the User information into the views defined in the layout
     */
    private void setPersonalInfo(GoogleSignInAccount currentPerson){

        String personName = currentPerson.getDisplayName();
        String personPhotoUrl = currentPerson.getPhotoUrl().toString();
        TextView user_name = findViewById(R.id.userName);
        user_name.setText("Name: " + personName);
        progressDialog.dismiss();
        setProfilePic(personPhotoUrl);
    }

    /*
     By default the profile pic url gives 50x50 px image.
     If you need a bigger image we have to change the query parameter value from 50 to the size you want
    */

    private void setProfilePic(String profile_pic){
        profile_pic = profile_pic.substring(0,
                profile_pic.length() - 2)
                + PROFILE_PIC_SIZE;
        ImageView user_picture = findViewById(R.id.profile_pic);
        new ActivityConnexion.LoadProfilePic(user_picture).execute(profile_pic);
    }

    /*
     Show and hide of the Views according to the user login status
     */

    private void changeUI() {
            findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
            findViewById(R.id.sign_out_and_disconnect).setVisibility(View.GONE);
    }

   /*
    Perform background operation asynchronously, to load user profile picture with new dimensions from the modified url
    */

    private class LoadProfilePic extends AsyncTask<String, Void, Bitmap> {
        ImageView bitmap_img;

        LoadProfilePic(ImageView bitmap_img) {
            this.bitmap_img = bitmap_img;
        }


        @Override
        protected Bitmap doInBackground(String... urls) {
            String url = urls[0];
            Bitmap newIcon = null;
            try {
                InputStream inStream = new java.net.URL(url).openStream();
                newIcon = BitmapFactory.decodeStream(inStream);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
            }
            return newIcon;
        }

        protected void onPostExecute(Bitmap result_img) {

            bitmap_img.setImageBitmap(result_img);
        }
    }
}
