package miage.fr.gestionprojet.models;

import com.reactiveandroid.Model;
import com.reactiveandroid.annotation.Column;
import com.reactiveandroid.annotation.PrimaryKey;
import com.reactiveandroid.annotation.Table;

import java.util.Date;

import miage.fr.gestionprojet.AppDatabase;

/**
 * Created by Audrey on 25/02/2017.
 */

@Table(name="Mesure", database = AppDatabase.class)
public class Mesure {

    @PrimaryKey
    private Long id;

    @Column(name="action")
    private SaisieCharge action;

    @Column(name="nb_unites_mesures")
    private int nbUnitesMesures;

    @Column(name="dt_mesure")
    private Date dtMesure;

    public Mesure() {
        super();
    }

    public Long getId() {
        return id;
    }

    public SaisieCharge getAction() {
        return action;
    }

    public void setAction(SaisieCharge action) {
        this.action = action;
    }

    public int getNbUnitesMesures() {
        return nbUnitesMesures;
    }

    public void setNbUnitesMesures(int nbUnitesMesures) {
        this.nbUnitesMesures = nbUnitesMesures;
    }

    public Date getDtMesure() {
        return dtMesure;
    }

    public void setDtMesure(Date dtMesure) {
        this.dtMesure = dtMesure;
    }
}
