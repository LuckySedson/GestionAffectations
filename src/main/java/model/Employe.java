package model;

import jakarta.persistence.*;

@Entity
@Table(name = "employe")
public class Employe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codeemp")
    private Integer codeemp;

    @Column(name = "nom", nullable = false, length = 50)
    private String nom;

    @Column(name = "prenom", length = 50)
    private String prenom;

    @Column(name = "poste", length = 50)
    private String poste;

    public Employe() {}

    public Employe(String nom, String prenom, String poste) {
        this.nom = nom;
        this.prenom = prenom;
        this.poste = poste;
    }

    public Integer getCodeemp() { return codeemp; }
    public void setCodeemp(Integer codeemp) { this.codeemp = codeemp; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public String getPoste() { return poste; }
    public void setPoste(String poste) { this.poste = poste; }

    @Override
    public String toString() {
        return "Employe [codeemp=" + codeemp + ", nom=" + nom + ", prenom=" + prenom + ", poste=" + poste + "]";
    }
}