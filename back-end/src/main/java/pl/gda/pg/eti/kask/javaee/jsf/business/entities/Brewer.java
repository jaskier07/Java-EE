package pl.gda.pg.eti.kask.javaee.jsf.business.entities;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
import javax.validation.constraints.Size;
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
        @NamedQuery(name = Brewer.Queries.FIND_ALL, query = "select b from Brewer b")
)
public class Brewer {
    public static class Queries {
        public static final String FIND_ALL = "BREWER_FIND_ALL";
    }

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    @Size(min = 5, max = 80)
    private String name;

    @NotNull
    @Min(value = 18)
    @Max(value = 120)
    private Integer age;

    @JoinColumn(name = "brewer_id")
    @OneToMany(cascade = { MERGE, REFRESH, DETACH})
    private Set<Beer> beers = new HashSet<>();

    public Brewer(@NotBlank @Size(min = 5, max = 80) String name, @NotNull @Min(value = 10) @Max(value = 120) Integer age, Set<Beer> beers) {
        this.name = name;
        this.age = age;
        this.beers = beers;
    }
}
