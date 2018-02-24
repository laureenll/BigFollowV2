package miage.fr.gestionprojet.models;

import com.reactiveandroid.Model;
import com.reactiveandroid.annotation.Column;
import com.reactiveandroid.annotation.PrimaryKey;
import com.reactiveandroid.annotation.Table;
import com.reactiveandroid.query.Select;

import java.util.ArrayList;
import java.util.List;

import miage.fr.gestionprojet.AppDatabase;

/**
 * Created by Audrey on 25/02/2017.
 */
@Table(name = "Formation", database = AppDatabase.class)
public class Formation extends Model{


    @PrimaryKey
    private Long id;
    @Column(name="action")
    private Action action;

    @Column(name="avancement_total")
    private float avancementTotal;
    @Column(name="avancement_pre_requis")
    private float avancementPreRequis;
    @Column(name="avancement_objectif")
    private float avancementObjectif;
    @Column(name="avancement_post_formation")
    private float avancementPostFormation;

    private List<EtapeFormation> lstEtapesFormation;

    public Formation() {
        super();
    }

    public Long getId() {
        return id;
    }

    public List<EtapeFormation> getLstEtapesFormations() {
        return Select.from(EtapeFormation.class).where("formation = ?", getId()).fetch();
    }

    public float getAvancementTotal() {
        return avancementTotal;
    }

    public void setAvancementTotal(float avancementTotal) {
        this.avancementTotal = avancementTotal;
    }

    public float getAvancementPreRequis() {
        return avancementPreRequis;
    }

    public void setAvancementPreRequis(float avancementPreRequis) {
        this.avancementPreRequis = avancementPreRequis;
    }

    public float getAvancementObjectif() {
        return avancementObjectif;
    }

    public void setAvancementObjectif(float avancementObjectif) {
        this.avancementObjectif = avancementObjectif;
    }

    public float getAvancementPostFormation() {
        return avancementPostFormation;
    }

    public void setAvancementPostFormation(float avancementPostFormation) {
        this.avancementPostFormation = avancementPostFormation;
    }

    public List<EtapeFormation> getLstEtapesFormation() {
        return lstEtapesFormation;
    }

    public void setLstEtapesFormation(ArrayList<EtapeFormation> lstEtapesFormation) {
        this.lstEtapesFormation = lstEtapesFormation;
    }
    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }


}
