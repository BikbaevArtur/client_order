package ru.bikbaev.client_order.service.admin;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bikbaev.client_order.model.dtoApi.dtoClientPanel.UserCreatDTO;
import ru.bikbaev.client_order.model.dtoApi.dtoClientPanel.UserDTO;
import ru.bikbaev.client_order.model.entity.Login;
import ru.bikbaev.client_order.model.entity.RoleUser;
import ru.bikbaev.client_order.model.entity.TypeAccount;
import ru.bikbaev.client_order.model.entity.User;
import ru.bikbaev.client_order.repository.requestRepository.LoginRequest;
import ru.bikbaev.client_order.repository.requestRepository.RoleUserRequest;
import ru.bikbaev.client_order.repository.requestRepository.TypeAccountRequest;
import ru.bikbaev.client_order.repository.requestRepository.UserRequest;

import java.util.List;

@Service
public class UserService {
    private final UserRequest userRequest;
    private final TypeAccountRequest typeAccountRequest;
    private final RoleUserRequest roleUserRequest;
    private final LoginRequest loginRequest;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    public UserService(UserRequest userRequest, TypeAccountRequest typeAccountRequest, RoleUserRequest roleUserRequest, LoginRequest loginRequest) {
        this.userRequest = userRequest;
        this.typeAccountRequest = typeAccountRequest;
        this.roleUserRequest = roleUserRequest;
        this.loginRequest = loginRequest;
    }


    @Transactional
    public User creatNewUser(UserCreatDTO userCreatDTO) {
        User user = userCreatDTO.getUser();

        TypeAccount typeAccount = typeAccountRequest.findById(1);

        RoleUser roleUser = roleUserRequest.findById(1).orElseThrow(
                ()-> new RuntimeException("role note found"));

        user.setTypeAccount(typeAccount);

        user = userRequest.creatNewUser(user);

        System.out.println(user.getId());

        Login login = new Login();

        login.setEmail(user.getEmail());
        login.setPassword(passwordEncoder.encode(userCreatDTO.getPassword()));
        login.setRole(roleUser);
        login.setUser(user);

        loginRequest.creatNewLogin(login);

        return user ;
    }


    public List<User> findAllUsers() {

        return userRequest.getAll();
    }

    public User findUserById(int id) {
        return userRequest.findById(id);
    }


    public User findByEmail(String email){
        return userRequest.findByEmail(email).orElseThrow(()-> new RuntimeException("User not found" + email));
    }

    public void deleteUser(int id) {

        userRequest.deleteUser(id);
        loginRequest.delete(id);
    }

    public User updateTypeAccountUser(int userId, int typeAccountId) {
        User user = userRequest.findById(userId);
        TypeAccount typeAccount = typeAccountRequest.findById(typeAccountId);

        user.setTypeAccount(typeAccount);

        return userRequest.creatNewUser(user);
    }



    private UserDTO userConvertToUserDTO(User user){
        return new UserDTO(
                user.getId(),
                user.getLastName(),
                user.getFirstName(),
                user.getMiddleName(),
                user.getPhone(),
                user.getEmail(),
                user.getTypeAccount()
        );
    }
}
