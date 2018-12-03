package pl.gda.pg.eti.kask.javaee.jsf.business.model.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.gda.pg.eti.kask.javaee.jsf.business.model.queries.BeerQueries;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@NoArgsConstructor
@NamedQueries({
        @NamedQuery(name = BeerQueries.FIND_ALL, query = "select b from Beer b"),
        @NamedQuery(name = BeerQueries.FIND_ONE, query = "select b from Beer b where b.id = :id")
})
public class Beer {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank(message = "Podaj nazwę piwa.")
    @Size(min = 4, max = 250, message = "Nazwa piwa powinna mieć od 4 do 250 znaków.")
    private String name;

    @NotNull(message = "Podaj woltejdż piwa.")
    @Digits(integer = 2, fraction = 2, message = "Woltejdż piwa musi być w formacie XX.XX%.")
    @Max(value = 100, message = "Woltejdż piwa nie może być wyższy niż 100.")
    @Min(value = 0, message = "Woltejdż piwa nie może być mniejszy niż 0.")
    private Double voltage;

    @NotNull(message = "Podaj IBU piwa.")
    @Max(value = 120, message = "IBU piwa nie może być większe niż 120.")
    @Min(value = 0, message = "IBU piwa nie może być mniejsze niż 0.")
    private Integer IBU;

    @NotNull(message = "Każde piwo musi być powiązane z jakimś użytkownikiem.")
    @ManyToOne
    private User owner;

    public Beer(String name, Double voltage, Integer IBU, User owner) {
        this.name = name;
        this.voltage = voltage;
        this.IBU = IBU;
        this.owner = owner;
    }
}
