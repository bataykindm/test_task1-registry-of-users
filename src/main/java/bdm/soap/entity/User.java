package bdm.soap.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Table(name = "users")
public class User {

    @Id
    private String login;
    @Column(name = "userName")
    private String name;
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }
}
