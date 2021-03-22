package converter.Model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Conversion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    private Valute valuteFrom;

    @ManyToOne(fetch = FetchType.EAGER)
    private Valute valuteTo;

    private Double valueFrom;
    private Double valueTo;
    private LocalDate date;

    public Conversion() {
    }

    public Conversion(Valute valuteFrom, Valute valuteTo, Double valueFrom, Double valueTo, LocalDate date) {
        this.valuteFrom = valuteFrom;
        this.valuteTo = valuteTo;
        this.valueFrom = valueFrom;
        this.valueTo = valueTo;
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Valute getValuteFrom() {
        return valuteFrom;
    }

    public void setValuteFrom(Valute valuteFrom) {
        this.valuteFrom = valuteFrom;
    }

    public Valute getValuteTo() {
        return valuteTo;
    }

    public void setValuteTo(Valute valuteTo) {
        this.valuteTo = valuteTo;
    }

    public Double getValueFrom() {
        return valueFrom;
    }

    public void setValueFrom(Double valueFrom) {
        this.valueFrom = valueFrom;
    }

    public Double getValueTo() {
        return valueTo;
    }

    public void setValueTo(Double valueTo) {
        this.valueTo = valueTo;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
