package bdm.soap.endpoint;

import bdm.soap.entity.User;
import bdm.soap.mapper.Mapper;
import bdm.soap.service.UserService;
import bdm.soap.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import test.users.*;

import java.util.List;
import java.util.stream.Collectors;

@Endpoint
public class UserEndpoint {

    private static final String NAMESPACE_URI = "http://test/users";
    private final UserService userService;
    private final Mapper mapper;
    private final Validator validator;

    @Autowired
    public UserEndpoint(UserService userService, Mapper userMapper, Validator validator) {
        this.userService = userService;
        this.mapper = userMapper;
        this.validator = validator;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getUsersRequest")
    @ResponsePayload
    public GetUsersResponse getUsersResponse() {
        List<User> users = userService.getAll();
        List<UserDTO> userDTOs = users.stream().map(mapper::mapToUserDTO).collect(Collectors.toList());

        GetUsersResponse response = new GetUsersResponse();
        response.getUsers().addAll(userDTOs);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getUserRequest")
    @ResponsePayload
    public GetUserResponse getUser(@RequestPayload GetUserRequest request) {
        User user = userService.getUser(request.getLogin());
        UserDTO userDTO = mapper.mapToUserDTO(user);

        GetUserResponse response = new GetUserResponse();
        response.setUserDTO(userDTO);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "createUserRequest")
    @ResponsePayload
    public Response createUserResponse(@RequestPayload CreateUserRequest request) {
        Response response = new Response();
        UserDTO userDTO = request.getUserDTO();
        try {
            validator.checkInputParams(userDTO);
            userService.createUser(mapper.mapToUser(userDTO));
            response.setSuccess(true);
        } catch (Exception ex) {
            response.setSuccess(false);
            response.getMessages().add(ex.getMessage());
            return response;
        }
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateUserRequest")
    @ResponsePayload
    public Response updateUserResponse(@RequestPayload UpdateUserRequest request) {
        Response response = new Response();
        UserDTO userDTO = request.getUserDTO();
        try {
            validator.checkInputParams(userDTO);
            userService.updateUser(mapper.mapToUser(userDTO));
            response.setSuccess(true);
        } catch (Exception ex) {
            response.setSuccess(false);
            response.getMessages().add(ex.getMessage());
            return response;
        }
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteUserRequest")
    @ResponsePayload
    public Response deleteUserResponse(@RequestPayload DeleteUserRequest request) {
        Response response = new Response();
        try {
            userService.deleteUser(request.getLogin());
            response.setSuccess(true);
        } catch (Exception ex) {
            response.setSuccess(false);
            response.getMessages().add(ex.getMessage());
        }
        return response;
    }
}
