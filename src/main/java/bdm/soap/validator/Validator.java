package bdm.soap.validator;

import bdm.soap.exception.NoValidParametersException;
import org.springframework.stereotype.Component;
import test.users.UserDTO;

@Component
public class Validator {

    public void checkInputParams(UserDTO userDTO) {
        if (userDTO.getLogin().isEmpty() || userDTO.getName().isEmpty() || userDTO.getPassword().isEmpty()) {
            throw new NoValidParametersException("Fields 'login', 'name' and 'password' can't be empty.");
        }
        checkPassword(userDTO.getPassword());
    }

    private void checkPassword(String password) {
        String rule2 = "(.*)[0-9](.*)";
        String rule1 = "(.*)[A-Z](.*)";

        if (!password.matches(rule1) || !password.matches(rule2)) {
            throw new NoValidParametersException("Password must contain minimum one letter in upper registry and one figure.");
        }
    }
}
