package ru.bikbaev.client_order.controller.api.admin;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bikbaev.client_order.model.dtoApi.dtoClientPanel.UserCreatDTO;
import ru.bikbaev.client_order.model.dtoApi.dtoAdminPanel.UserUpdateTypeDTO;
import ru.bikbaev.client_order.model.entity.User;
import ru.bikbaev.client_order.service.admin.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService service;

    public UserController(UserService userService) {
        this.service = userService;
    }


    @PostMapping
    public ResponseEntity<User> creatNewUser(@RequestBody UserCreatDTO userCreatDTO) {
        return new ResponseEntity<>(service.creatNewUser(userCreatDTO), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<User>> findAll() {
        return new ResponseEntity<>(service.findAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable int id) {
        return new ResponseEntity<>(service.findUserById(id), HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUserById(@PathVariable int id) {
        service.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<User> updateUserType(@RequestBody UserUpdateTypeDTO userUpdateTypeDTO) {

        return new ResponseEntity<>(service.updateTypeAccountUser(
                userUpdateTypeDTO.getUserId(),
                userUpdateTypeDTO.getTypeAccountId()), HttpStatus.OK);
    }


}
