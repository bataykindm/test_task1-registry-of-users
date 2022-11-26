package bdm.soap.service;

import bdm.soap.entity.User;
import bdm.soap.exception.IllegalUserException;
import bdm.soap.exception.UserNotExistsException;
import bdm.soap.repository.RoleRepository;
import bdm.soap.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, RoleService roleService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User getUser(String login) {
        return userRepository.findByLogin(login).orElseThrow(UserNotExistsException::new);
    }

    public void createUser(User user) {
        if (userRepository.findByLogin(user.getLogin()).isPresent()) {
            throw new IllegalUserException("User with login " + user.getLogin() + " is already exists");
        }
        userRepository.save(roleService.checkRoles(user));
    }

    public void updateUser(User user) {
        if (!userRepository.findByLogin(user.getLogin()).isPresent()) {
            throw new UserNotExistsException();
        }
        userRepository.save(roleService.checkRoles(user));
    }

    public void deleteUser(String login) {
        if (!userRepository.findByLogin(login).isPresent()) {
            throw new UserNotExistsException();
        }
        userRepository.deleteById(login);
    }
}
