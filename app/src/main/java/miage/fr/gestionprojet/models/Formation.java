package miage.fr.gestionprojet.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Audrey on 25/02/2017.
 */
@Table(name = "Formation")
public class Formation extends Model {


    @Column(name = "ident", index = true, unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    private Long ident;
    @Column(name="action", onDelete = Column.ForeignKeyAction.CASCADE)
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

    public Long getIdent() {
        return ident;
    }

    public List<EtapeFormation> getLstEtapesFormations() {
        return new Select().from(EtapeFormation.class).where("formation = ?", getId()).execute();
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

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
