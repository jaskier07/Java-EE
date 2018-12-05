package pl.gda.pg.eti.kask.javaee.jsf.utils;


import pl.gda.pg.eti.kask.javaee.jsf.api.interceptors.ExpectedRole;
import pl.gda.pg.eti.kask.javaee.jsf.business.model.entities.Role;
import pl.gda.pg.eti.kask.javaee.jsf.business.security.Permission;

public class UserUtils {

    public static Role createUserRole() {
        Role user = new Role();
        user.setRemoveBeer(Permission.IF_OWNER);
        user.setRemoveBrewer(Permission.DENIED);
        user.setRemoveBrewery(Permission.DENIED);
        user.setFindAllBeers(Permission.GRANTED);
        user.setFindAllBreweries(Permission.GRANTED);
        user.setFindAllBrewers(Permission.GRANTED);
        user.setFindBeer(Permission.GRANTED);
        user.setFindBrewer(Permission.GRANTED);
        user.setFindBrewery(Permission.GRANTED);
        user.setSaveBeer(Permission.GRANTED);
        user.setSaveBrewer(Permission.GRANTED);
        user.setSaveBrewery(Permission.GRANTED);
        user.setFindBrewersByAge(Permission.GRANTED);
        user.setRoleName(ExpectedRole.USER);
        user.setUpdateBeer(Permission.IF_OWNER);
        user.setUpdateBrewer(Permission.IF_OWNER);
        user.setUpdateBrewery(Permission.IF_OWNER);
        return user;
    }

    public static Role createAdminRole() {
        Role admin = new Role();
        admin.setRemoveBeer(Permission.GRANTED);
        admin.setRemoveBrewer(Permission.GRANTED);
        admin.setRemoveBrewery(Permission.GRANTED);
        admin.setFindAllBeers(Permission.GRANTED);
        admin.setFindAllBreweries(Permission.GRANTED);
        admin.setFindAllBrewers(Permission.GRANTED);
        admin.setFindBeer(Permission.GRANTED);
        admin.setFindBrewer(Permission.GRANTED);
        admin.setFindBrewery(Permission.GRANTED);
        admin.setSaveBeer(Permission.GRANTED);
        admin.setSaveBrewer(Permission.GRANTED);
        admin.setSaveBrewery(Permission.GRANTED);
        admin.setFindBrewersByAge(Permission.GRANTED);
        admin.setRoleName(ExpectedRole.ADMIN);
        admin.setUpdateBeer(Permission.GRANTED);
        admin.setUpdateBrewer(Permission.GRANTED);
        admin.setUpdateBrewery(Permission.GRANTED);
        return admin;
    }

}
