package miage.fr.gestionprojet.vues;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.plus.People;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.google.android.gms.plus.model.people.PersonBuffer;
import com.google.android.gms.tasks.Task;

import java.io.InputStream;
import java.util.ArrayList;

import miage.fr.gestionprojet.R;

import static miage.fr.gestionprojet.vues.ActivityGestionDesInitials.EXTRA_INITIAL;

public class ActivityConnexion extends AppCompatActivity implements View.OnClickListener {

    private GoogleSignInClient mGoogleSignInClient;
    private static final int SIGN_IN_CODE = 0;
    private static final int PROFILE_PIC_SIZE = 120;
    ProgressDialog progress_dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);
        //Customize sign-in button.a red button may be displayed when Google+ scopes are requested
        setBtnClickListeners();
        progress_dialog = new ProgressDialog(this);
        progress_dialog.setMessage("Chargement....");

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
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
            progress_dialog.dismiss();
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
                progress_dialog.show();
                gPlusSignIn();
                break;
            case R.id.sign_out_button:
                progress_dialog.show();
                gPlusSignOut();

                break;
            case R.id.disconnect_button:
                progress_dialog.show();
//                gPlusRevokeAccess();

            case R.id.frnd_button:

                break;


        }
    }

    /*
      Sign-in into the Google + account
     */
    private void gPlusSignIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, SIGN_IN_CODE);
    }

    /*
      Sign-out from Google+ account
     */
    private void gPlusSignOut() {
//        if (google_api_client.isConnected()) {
//            Plus.AccountApi.clearDefaultAccount(google_api_client);
//            google_api_client.disconnect();
////            google_api_client.connect();
//            changeUI();
//        }
    }

    private void changeActivity(GoogleSignInAccount account) {
        String initial = "";
        if (account.getGivenName().length() > 0 && account.getFamilyName().length() > 0) {
            initial = account.getGivenName().substring(0, 1) + account.getFamilyName().substring(0, 1);
        }
        Log.d("user connected","connected");
        Intent intent = new Intent(ActivityConnexion.this,ActivityGestionDesInitials.class);
        intent.putExtra(EXTRA_INITIAL, initial);
        startActivity(intent);
        progress_dialog.hide();
    }

    /*
     set the User information into the views defined in the layout
     */

    private void setPersonalInfo(Person currentPerson){

        String personName = currentPerson.getDisplayName();
        String personPhotoUrl = currentPerson.getImage().getUrl();
        TextView user_name = (TextView) findViewById(R.id.userName);
        user_name.setText("Name: "+personName);
        TextView gemail_id = (TextView)findViewById(R.id.emailId);
        progress_dialog.dismiss();
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
        ImageView user_picture = (ImageView)findViewById(R.id.profile_pic);
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

        public LoadProfilePic(ImageView bitmap_img) {
            this.bitmap_img = bitmap_img;
        }

        protected Bitmap doInBackground(String... urls) {
            String url = urls[0];
            Bitmap new_icon = null;
            try {
                InputStream in_stream = new java.net.URL(url).openStream();
                new_icon = BitmapFactory.decodeStream(in_stream);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return new_icon;
        }

        protected void onPostExecute(Bitmap result_img) {

            bitmap_img.setImageBitmap(result_img);
        }
    }


}
