package ru.bikbaev.client_order.mapped;

import org.springframework.stereotype.Component;
import ru.bikbaev.client_order.model.dtoApi.dtoClientPanel.UserCreatDTO;
import ru.bikbaev.client_order.model.dtoThym.UserRegistrationDTOModel;
import ru.bikbaev.client_order.model.entity.User;

@Component
public class UserMapper {


    public UserCreatDTO userRegistrationDTOModelConvertUserCreatDTO(UserRegistrationDTOModel userRegistrationDTOModel) {
        User user = new User();
        user.setId(userRegistrationDTOModel.getId());
        user.setLastName(userRegistrationDTOModel.getLastName());
        user.setFirstName(userRegistrationDTOModel.getFirstName());
        user.setMiddleName(userRegistrationDTOModel.getMiddleName());
        user.setPhone(userRegistrationDTOModel.getPhone());
        user.setEmail(userRegistrationDTOModel.getEmail());

        String password = userRegistrationDTOModel.getPassword();

        return new UserCreatDTO(user, password);
    }
}
