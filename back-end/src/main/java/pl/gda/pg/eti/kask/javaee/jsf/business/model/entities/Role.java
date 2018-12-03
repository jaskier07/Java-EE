package pl.gda.pg.eti.kask.javaee.jsf.business.model.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import pl.gda.pg.eti.kask.javaee.jsf.api.interceptors.ExpectedRole;
import pl.gda.pg.eti.kask.javaee.jsf.business.security.Permission;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
@EqualsAndHashCode(of = "id")
@Getter
@Setter
public class Role {

    @Id
    @GeneratedValue
    private Long id;

    private ExpectedRole roleName;

    @NotNull private Permission findAllBeers;
    @NotNull private Permission saveBeer;
    @NotNull private Permission findBeer;
    @NotNull private Permission removeBeer;

    @NotNull private Permission findAllBrewers;
    @NotNull private Permission findBrewersByAge;
    @NotNull private Permission saveBrewer;
    @NotNull private Permission findBrewer;
    @NotNull private Permission removeBrewer;

    @NotNull private Permission findAllBreweries;
    @NotNull private Permission saveBrewery;
    @NotNull private Permission findBrewery;
    @NotNull private Permission removeBrewery;

    public Role() {}

}
