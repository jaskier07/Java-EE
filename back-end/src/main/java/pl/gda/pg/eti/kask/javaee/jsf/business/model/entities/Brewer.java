package pl.gda.pg.eti.kask.javaee.jsf.business.model.entities;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.gda.pg.eti.kask.javaee.jsf.business.model.queries.BrewerQueries;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Version;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
@NamedQueries({
        @NamedQuery(name = BrewerQueries.FIND_ALL, query = "SELECT DISTINCT b FROM Brewer b LEFT JOIN FETCH b.beers beers"),
        @NamedQuery(name = BrewerQueries.FIND_ONE, query = "SELECT b FROM Brewer b LEFT JOIN FETCH b.beers beers where b.id = :id "),
        @NamedQuery(name = BrewerQueries.FIND_BY_AGE, query = "SELECT DISTINCT b FROM Brewer b LEFT JOIN FETCH b.beers beers where (b.age >= :from AND b.age < :to)")
})
public class Brewer {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank(message = "Podaj imię i nazwisko browarnika.")
    @Size(min = 5, max = 80, message = "Imię i nazwisko browarnika musi łącznie zawierać od 5 do 80 znaków.")
    private String name;

    @NotNull(message = "Podaj wiek.")
    @Min(value = 18, message = "Wiek browarnika nie może być niższy niż 18 lat. Dzwonię na policję.")
    @Max(value = 120, message = "Wiek browarnika nie może być wyższy niż 120 lat. Ludzie tak długo nie żyją.")
    private Integer age;

    @JoinColumn(name = "brewer_id")
    @OneToMany(cascade = {MERGE, REFRESH, DETACH})
    private Set<Beer> beers = new HashSet<>();

    @NotNull(message = "Każdy piwowar musi być powiązany z jakimś użytkownikiem.")
    @ManyToOne
    private User owner;

    @Version
    @Getter(AccessLevel.NONE)
    private int version;

    private Date lastUpdateDate;

    public Brewer(String name, Integer age, Set<Beer> beers, User owner) {
        this.name = name;
        this.age = age;
        this.beers = beers;
        this.owner = owner;
    }

    @PrePersist
    private void prePersist() {
        this.lastUpdateDate = new Date();
    }

    @PreUpdate
    private void preUpdate() {
        this.lastUpdateDate = new Date();
    }
}
