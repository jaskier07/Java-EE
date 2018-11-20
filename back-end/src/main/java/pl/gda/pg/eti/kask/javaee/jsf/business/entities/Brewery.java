package pl.gda.pg.eti.kask.javaee.jsf.business.entities;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.CascadeType.DETACH;
import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.REFRESH;

@Entity
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@NoArgsConstructor
@NamedQueries(
        @NamedQuery(name = Brewery.Queries.FIND_ALL, query = "select b from Brewery b")
)
public class Brewery {
    public static class Queries {
        public static final String FIND_ALL = "BREWERY_FIND_ALL";
    }

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank(message = "Podaj nazwę browaru.")
    @Size(min = 5, max = 80, message = "Nazwa browaru powinna mieć od 5 do 80 znaków.")
    private String name;

    @NotNull(message = "Podaj liczbę pracowników.")
    @Min(value = 1, message = "Liczba pracowników browaru powinna wynosić co najmniej 1.")
    private Integer employees;

    @NotNull(message = "Podaj datę w formacie RRRR-MM-DD")
    @Past(message = "Data założenia browaru powinna być datą przeszłą.")
    private Date dateEstablished;

    @JoinColumn(name = "brewery_id")
    @OneToMany(cascade = { MERGE, REFRESH, DETACH })
    private Set<Beer> beers = new HashSet<>();

    public Brewery(String name, Integer employees, Date dateEstablished, Set<Beer> beers) {
        this.name = name;
        this.employees = employees;
        this.dateEstablished = dateEstablished;
        this.beers = beers;
    }
}
