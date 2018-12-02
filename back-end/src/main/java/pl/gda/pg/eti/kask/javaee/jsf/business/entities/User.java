package pl.gda.pg.eti.kask.javaee.jsf.business.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.gda.pg.eti.kask.javaee.jsf.business.entities.queries.UserQueries;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import java.util.ArrayList;
import java.util.List;

@Entity
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@NoArgsConstructor
@NamedQueries({
        @NamedQuery(name = UserQueries.FIND_ALL, query = "SELECT u FROM User u"),
        @NamedQuery(name = UserQueries.FIND_BY_LOGIN, query = "SELECT u FROM User u WHERE u.login = :login"),
        @NamedQuery(name = UserQueries.FIND_ALL_LOGINS, query = "SELECT u.login FROM User u")
})
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String login;

    @Column(nullable = false)
    private String password;

    @ElementCollection
    private List<String> roles = new ArrayList<>();

    public User(String login, String password, List<String> roles) {
        this.login = login;
        this.password = password;
        this.roles = roles;
    }
}
