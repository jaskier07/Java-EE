package pl.gda.pg.eti.kask.javaee.jsf.api.security;

import java.security.Principal;
import java.security.acl.Group;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

public class RolesGroup implements Group {

    Set<Principal> roles = new HashSet<>();

    public RolesGroup(Collection<String> roleNames) {
        roleNames.forEach(role -> roles.add(() -> role));
    }

    @Override
    public String getName() {
        return "Roles";
    }

    @Override
    public boolean addMember(Principal principal) {
        return roles.add(principal);
    }

    @Override
    public boolean removeMember(Principal principal) {
        return roles.remove(principal);
    }

    @Override
    public boolean isMember(Principal principal) {
        return roles.contains(principal);
    }

    @Override
    public Enumeration<? extends Principal> members() {
        return Collections.enumeration(roles);
    }
}
