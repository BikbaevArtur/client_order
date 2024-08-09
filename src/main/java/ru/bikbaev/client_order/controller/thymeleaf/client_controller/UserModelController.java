package ru.bikbaev.client_order.controller.thymeleaf.client_controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.bikbaev.client_order.mapped.UserMapper;
import ru.bikbaev.client_order.model.dtoApi.dtoClientPanel.UserCreatDTO;
import ru.bikbaev.client_order.model.dtoThym.UserRegistrationDTOModel;
import ru.bikbaev.client_order.service.admin.UserService;

@Controller
public class UserModelController {

    private final UserService userService;
    private final UserMapper userMapper;

    public UserModelController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }


    @GetMapping("/signup")
    public String registration(Model model){
        model.addAttribute("user",new UserRegistrationDTOModel());
        return "/sign-up";
    }

    @PostMapping("/signup")
    public String creatNewUser(UserRegistrationDTOModel user){
        UserCreatDTO userCreatDTO = userMapper.userRegistrationDTOModelConvertUserCreatDTO(user);
        userService.creatNewUser(userCreatDTO);
        return "redirect:/";
    }

}
