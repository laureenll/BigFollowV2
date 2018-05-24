package miage.fr.gestionprojet.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

/**
 * Created by Audrey on 23/01/2017.
 */
@Table(name="Domaine")
public class Domaine extends Model {

    @Column(name = "ident", index = true, unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    private Long ident;

    @Column(name="nom")
    private String nom;

    @Column(name="description")
    private String description;

    @Column(name="projet", onDelete = Column.ForeignKeyAction.SET_NULL)
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

    public Long getIdent() {
        return ident;
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
        return new Select().from(Action.class).where("domaine = ?", getId()).execute();
    }

    @Override
    public String toString() {
        return this.nom.toUpperCase();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}




