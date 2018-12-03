package pl.gda.pg.eti.kask.javaee.jsf.business.model.entities;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import pl.gda.pg.eti.kask.javaee.jsf.api.interceptors.ExpectedRole;
import pl.gda.pg.eti.kask.javaee.jsf.business.security.Permission;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
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

    @NotNull private Permission getAllBeers;
    @NotNull private Permission getBeersUsingPagination;
    @NotNull private Permission saveBeer;
    @NotNull private Permission getBeer;
    @NotNull private Permission deleteBeer;
    @NotNull private Permission updateBeer;

    @NotNull private Permission getAllBrewers;
    @NotNull private Permission getBrewersByAge;
    @NotNull private Permission saveBrewer;
    @NotNull private Permission getBrewer;
    @NotNull private Permission deleteBrewer;
    @NotNull private Permission updateBrewer;

    @NotNull private Permission getAllBreweries;
    @NotNull private Permission saveBrewery;
    @NotNull private Permission getBrewery;
    @NotNull private Permission deleteBrewery;
    @NotNull private Permission updateBrewery;

    public Role() {}

}
