package model;

import jakarta.persistence.*;

@Entity
@Table(name = "lieu")
public class Lieu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codelieu")
    private Integer codelieu;

    @Column(name = "designation", nullable = false, length = 100)
    private String designation;

    @Column(name = "province", length = 50)
    private String province;

    public Lieu() {}

    public Lieu(String designation, String province) {
        this.designation = designation;
        this.province = province;
    }

    public Integer getCodelieu() { return codelieu; }
    public void setCodelieu(Integer codelieu) { this.codelieu = codelieu; }

    public String getDesignation() { return designation; }
    public void setDesignation(String designation) { this.designation = designation; }

    public String getProvince() { return province; }
    public void setProvince(String province) { this.province = province; }

    @Override
    public String toString() {
        return "Lieu [codelieu=" + codelieu + ", designation=" + designation + ", province=" + province + "]";
    }
}