package model;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Embeddable
public class AffecterId implements Serializable {

    private Integer codeemp;
    private Integer codelieu;
    private LocalDate date;

    public AffecterId() {}

    public AffecterId(Integer codeemp, Integer codelieu, LocalDate date) {
        this.codeemp = codeemp;
        this.codelieu = codelieu;
        this.date = date;
    }

    public Integer getCodeemp() { return codeemp; }
    public void setCodeemp(Integer codeemp) { this.codeemp = codeemp; }

    public Integer getCodelieu() { return codelieu; }
    public void setCodelieu(Integer codelieu) { this.codelieu = codelieu; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AffecterId)) return false;
        AffecterId that = (AffecterId) o;
        return Objects.equals(codeemp, that.codeemp)
                && Objects.equals(codelieu, that.codelieu)
                && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codeemp, codelieu, date);
    }
}