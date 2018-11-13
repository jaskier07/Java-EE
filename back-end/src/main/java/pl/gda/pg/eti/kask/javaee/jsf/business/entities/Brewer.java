package pl.gda.pg.eti.kask.javaee.jsf.business.entities;

import lombok.*;

import java.util.List;
import java.util.Set;

@EqualsAndHashCode(of = "id")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Brewer {
    private Long id;
    private String name;
    private Integer age;
    private Set<Beer> beers;
}
