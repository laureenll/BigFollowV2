package miage.fr.gestionprojet.vues;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;


import miage.fr.gestionprojet.R;
import miage.fr.gestionprojet.models.Formation;
import miage.fr.gestionprojet.models.dao.DaoFormation;

/**
 * Created by Romain on 28/04/2017.
 */

public class FormationActivity extends AppCompatActivity {
    protected Formation formationData;
    protected TextView formationName;
    protected TextView formationPhase;
    protected ListView formationDescriptionsList;
    protected ProgressBar formationTotalProgressBar;
    protected ProgressBar formationPreRequisProgressBar;
    protected ProgressBar formationObjectifProgressBar;
    protected ProgressBar formationPostFormatProgressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.formation_activity);

        getFormationData();
        getViewComponents();
        fillFormationData();
    }

    private void getViewComponents() {
        formationName                   = findViewById(R.id.formationName);
        formationPhase                  = findViewById(R.id.formationPhase);
        formationDescriptionsList       = findViewById(R.id.formationDescriptionsList);
        formationTotalProgressBar       = findViewById(R.id.formationTotalProgressBar);
        formationPreRequisProgressBar   = findViewById(R.id.formationPreRequisProgressBar);
        formationObjectifProgressBar    = findViewById(R.id.formationObjectifProgressBar);
        formationPostFormatProgressBar  = findViewById(R.id.formationPostFormatProgressBar);
    }

    protected void getFormationData() {
        Intent intent = getIntent();
        long id = intent.getLongExtra(FormationsActivity.FORMATION_SELECTED,0);
        formationData = DaoFormation.getFormation(id);
    }

    protected void fillFormationData() {
        formationName.setText(formationData.getAction().getCode());
        formationPhase.setText(formationData.getAction().getPhase());
        formationTotalProgressBar.setProgress((int) formationData.getAvancementTotal());
        formationPreRequisProgressBar.setProgress((int) formationData.getAvancementPreRequis());
        formationObjectifProgressBar.setProgress((int) formationData.getAvancementObjectif());
        formationPostFormatProgressBar.setProgress((int) formationData.getAvancementPostFormation());
    }
}
