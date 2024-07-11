package ru.bikbaev.client_order.service.admin;

import org.springframework.stereotype.Service;
import ru.bikbaev.client_order.model.entity.TypeAccount;
import ru.bikbaev.client_order.model.entity.User;
import ru.bikbaev.client_order.repository.requestRepository.TypeAccountRequest;
import ru.bikbaev.client_order.repository.requestRepository.UserRequest;

import java.util.List;

@Service
public class UserService {
    private final UserRequest userRequest;
    private final TypeAccountRequest typeAccountRequest;

    public UserService(UserRequest userRequest, TypeAccountRequest typeAccountRequest) {
        this.userRequest = userRequest;
        this.typeAccountRequest = typeAccountRequest;
    }


    public User creatNewUser(User user) {
        TypeAccount typeAccount = typeAccountRequest.findById(1);
        user.setTypeAccount(typeAccount);
        return userRequest.creatNewUser(user);
    }


    public List<User> findAllUsers() {
        return userRequest.getAll();
    }

    public User findUserById(int id) {
        return userRequest.findById(id);
    }

    public void deleteUser(int id) {
        userRequest.deleteUser(id);
    }

    public User updateTypeAccountUser(int userId, int typeAccountId) {
        User user = userRequest.findById(userId);
        TypeAccount typeAccount = typeAccountRequest.findById(typeAccountId);

        user.setTypeAccount(typeAccount);

        return userRequest.creatNewUser(user);
    }
}
