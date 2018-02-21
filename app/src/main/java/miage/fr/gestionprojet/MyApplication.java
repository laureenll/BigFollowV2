package miage.fr.gestionprojet;

import android.app.Application;

import com.reactiveandroid.ReActiveAndroid;
import com.reactiveandroid.ReActiveConfig;
import com.reactiveandroid.internal.database.DatabaseConfig;

import miage.fr.gestionprojet.models.Action;
import miage.fr.gestionprojet.models.Domaine;
import miage.fr.gestionprojet.models.EtapeFormation;
import miage.fr.gestionprojet.models.Formation;
import miage.fr.gestionprojet.models.Mesure;
import miage.fr.gestionprojet.models.Projet;
import miage.fr.gestionprojet.models.Ressource;
import miage.fr.gestionprojet.models.SaisieCharge;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        DatabaseConfig appDatabase = new DatabaseConfig.Builder(AppDatabase.class)
                .addModelClasses(
                        Action.class,
                        Domaine.class,
                        EtapeFormation.class,
                        Formation.class,
                        Mesure.class,
                        Projet.class,
                        Ressource.class,
                        SaisieCharge.class
                )
                .build();

        ReActiveAndroid.init(new ReActiveConfig.Builder(this)
                .addDatabaseConfigs(appDatabase)
                .build());
    }
}
