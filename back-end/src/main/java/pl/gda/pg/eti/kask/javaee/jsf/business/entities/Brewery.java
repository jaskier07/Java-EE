package pl.gda.pg.eti.kask.javaee.jsf.business.entities;

import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.Set;

@EqualsAndHashCode(of = "id")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Brewery {
    private Long id;
    private String name;
    private Integer employees;
    private Date dateEstablished;
    private Set<Beer> beers;
}
