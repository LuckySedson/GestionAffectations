package model;

import jakarta.persistence.*;

@Entity
@Table(name = "affecter")
public class Affecter {

    @EmbeddedId
    private AffecterId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("codeemp")
    @JoinColumn(name = "codeemp")
    private Employe employe;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("codelieu")
    @JoinColumn(name = "codelieu")
    private Lieu lieu;

    public Affecter() {}

    public Affecter(Employe employe, Lieu lieu, java.time.LocalDate date) {
        this.employe = employe;
        this.lieu = lieu;
        this.id = new AffecterId(employe.getCodeemp(), lieu.getCodelieu(), date);
    }

    public AffecterId getId() { return id; }
    public void setId(AffecterId id) { this.id = id; }

    public Employe getEmploye() { return employe; }
    public void setEmploye(Employe employe) { this.employe = employe; }

    public Lieu getLieu() { return lieu; }
    public void setLieu(Lieu lieu) { this.lieu = lieu; }

    @Override
    public String toString() {
        return "Affecter [id=" + id + "]";
    }
}