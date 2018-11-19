package pl.gda.pg.eti.kask.javaee.jsf.business.entities;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.awt.print.Book;

@Entity
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@NoArgsConstructor
@NamedQueries(
        @NamedQuery(name = Beer.Queries.FIND_ALL, query = "select b from Beer b")
)
public class Beer {
    public static class Queries {
        public static final String FIND_ALL = "BEER_FIND_ALL";
    }

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    @Size(min = 4, max = 250)
    private String name;

    @NotNull
    @Digits(integer = 2, fraction = 2)
    @Max(value = 100)
    private Double voltage;

    @NotNull
    @Max(value = 120)
    private Integer IBU;

    public Beer(@NotBlank @Size(min = 4, max = 250) String name, @NotNull @Digits(integer = 2, fraction = 2) @Max(value = 100) Double voltage, @NotNull @Max(value = 120) Integer IBU) {
        this.name = name;
        this.voltage = voltage;
        this.IBU = IBU;
    }
}
