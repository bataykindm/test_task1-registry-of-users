package bdm.soap.entity;

import lombok.*;
import test.users.RoleDTO;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "roles")
@ToString
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "roleName")
    private String name;

    public Role(String name) {
        this.name = name;
    }

    public Role(RoleDTO roleDTO) {
        this.name = roleDTO.getName();
    }

}
