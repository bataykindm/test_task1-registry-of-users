package bdm.soap.service;

import bdm.soap.entity.Role;
import bdm.soap.entity.User;
import bdm.soap.exception.RoleNotExistsException;
import bdm.soap.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public User checkRoles(User user) {
        Set<Role> checkedRoles = new HashSet<>();
        boolean flag = false;
        StringBuilder sbOfUnavailableRoles = new StringBuilder();

        for (Role role : user.getRoles()) {
            Role currentRole = roleRepository.findByName(role.getName()).orElse(null);
            if (currentRole == null) {
                flag = true;
                sbOfUnavailableRoles.append("'" + role.getName() + "' ");
            }
            checkedRoles.add(currentRole);
        }
        if (flag) {
            throw new RoleNotExistsException(sbOfUnavailableRoles);
        }
        user.setRoles(checkedRoles);
        return user;
    }

    @PostConstruct
    void initMethod(){
        roleRepository.initializeDatabase();
    }
}
