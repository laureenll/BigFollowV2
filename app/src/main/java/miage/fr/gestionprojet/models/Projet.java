package miage.fr.gestionprojet.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * Created by Audrey on 23/01/2017.
 */
@Table(name="Projet")
public class Projet extends Model {

    @Column(name = "ident", index = true, unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    private Long ident;
    @Column(name="nom")
    private String nom;

    @Column(name="description")
    private String description;

    @Column(name="date_debut")
    private Date dateDebut;

    @Column(name="date_fin_initiale")
    private Date dateFinInitiale;

    @Column(name="date_fin_reelle")
    private Date dateFinReelle;

    public Projet() {
        super();
    }

    public Projet(String nom, String description, Date dateDebut, Date dateFinInitiale, Date dateFinReelle) {
        super();
        this.nom = nom;
        this.description = description;
        this.dateDebut = dateDebut;
        this.dateFinInitiale = dateFinInitiale;
        this.dateFinReelle = dateFinReelle;
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

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFinInitiale() {
        return dateFinInitiale;
    }

    public void setDateFinInitiale(Date dateFinInitiale) {
        this.dateFinInitiale = dateFinInitiale;
    }

    public Date getDateFinReelle() {
        return dateFinReelle;
    }

    public void setDateFinReelle(Date dateFinReelle) {
        this.dateFinReelle = dateFinReelle;
    }

    public List<Domaine> getLstDomaines() {
        return new Select().from(Domaine.class).where("projet = ?", getId()).execute();
    }

    @Override
    public String toString() {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return this.nom+"            "+df.format(this.dateDebut)+"-"+df.format(this.dateFinReelle);
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
