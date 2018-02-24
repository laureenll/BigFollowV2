package miage.fr.gestionprojet.models;

import com.reactiveandroid.Model;
import com.reactiveandroid.annotation.Column;
import com.reactiveandroid.annotation.PrimaryKey;
import com.reactiveandroid.annotation.Table;
import com.reactiveandroid.query.Select;

import java.util.List;

import miage.fr.gestionprojet.AppDatabase;

/**
 * Created by Audrey on 23/01/2017.
 */
@Table(name="Domaine", database = AppDatabase.class)
public class Domaine extends Model{

    @PrimaryKey
    private Long id;

    @Column(name="nom")
    private String nom;

    @Column(name="description")
    private String description;

    @Column(name="projet")
    private Projet projet;

    public Domaine(String nom, String description, Projet projet) {
        super();
        this.nom = nom;
        this.description = description;
        this.projet = projet;

    }

    public Domaine() {
        super();
    }

    public Long getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Projet getProjet() {
        return projet;
    }

    public void setProjet(Projet projet) {
        this.projet = projet;
    }

    public List<Action> getLstActions() {
        return Select.from(Action.class).where("domaine = ?", getId()).fetch();
    }

    @Override
    public String toString() {
        return this.nom.toUpperCase();
    }
}




