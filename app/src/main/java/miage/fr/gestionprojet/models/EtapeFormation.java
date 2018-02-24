package miage.fr.gestionprojet.models;

import com.reactiveandroid.Model;
import com.reactiveandroid.annotation.Column;
import com.reactiveandroid.annotation.PrimaryKey;
import com.reactiveandroid.annotation.Table;

import miage.fr.gestionprojet.AppDatabase;

/**
 * Created by Audrey on 25/02/2017.
 */

@Table(name="EtapeFormation", database = AppDatabase.class)
public class EtapeFormation extends Model{

    @PrimaryKey
    private Long id;

    @Column(name="formation")
    private Formation formation;

    @Column(name="type_element")
    private String typeElement;

    @Column(name="description")
    private String description;

    @Column(name="type_acteur")
    private String typeActeur;

    @Column(name="commentaire")
    private String commentaire;

    @Column(name="objectif_atteint")
    private boolean objectifAtteint;

    public Formation getFormation() {
        return formation;
    }

    public Long getId() {
        return id;
    }

    public void setFormation(Formation formation) {
        this.formation = formation;
    }

    public String getTypeElement() {
        return typeElement;
    }

    public void setTypeElement(String typeElement) {
        this.typeElement = typeElement;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTypeActeur() {
        return typeActeur;
    }

    public void setTypeActeur(String typeActeur) {
        this.typeActeur = typeActeur;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public boolean isObjectifAtteint() {
        return objectifAtteint;
    }

    public void setObjectifAtteint(boolean objectifAtteint) {
        this.objectifAtteint = objectifAtteint;
    }
}
