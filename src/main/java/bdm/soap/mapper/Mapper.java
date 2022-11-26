package bdm.soap.mapper;

import bdm.soap.entity.Role;
import bdm.soap.entity.User;
import org.springframework.stereotype.Component;
import test.users.RoleDTO;
import test.users.UserDTO;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class Mapper {

    public UserDTO mapToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setLogin(user.getLogin());
        userDTO.setName(user.getName());
        userDTO.setPassword(user.getPassword());
        userDTO.getRoles().addAll(mapToRoleDTOs(user.getRoles()));

        return userDTO;
    }

    public List<RoleDTO> mapToRoleDTOs(Set<Role> roles) {
        return roles.stream().map(this::mapToRoleDTO).collect(Collectors.toList());
    }

    public RoleDTO mapToRoleDTO(Role role) {
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setName(role.getName());

        return roleDTO;
    }

    public User mapToUser(UserDTO userDTO) {
        User user = new User();
        user.setLogin(userDTO.getLogin());
        user.setName(userDTO.getName());
        user.setPassword(userDTO.getPassword());
        user.setRoles(mapToRoles(userDTO.getRoles()));

        return user;
    }

    public Set<Role> mapToRoles(List<RoleDTO> roleDTOs) {
        return roleDTOs.stream().map(this::mapToRole).collect(Collectors.toSet());
    }

    public Role mapToRole(RoleDTO roleDTO) {
        return new Role(roleDTO.getName());
    }
}
