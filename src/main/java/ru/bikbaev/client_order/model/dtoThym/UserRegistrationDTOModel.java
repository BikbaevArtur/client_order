package ru.bikbaev.client_order.model.dtoThym;

import lombok.Data;
import ru.bikbaev.client_order.model.entity.TypeAccount;

@Data
public class UserRegistrationDTOModel {
    private int id;
    private String lastName;
    private String firstName;
    private String middleName;
    private String phone;
    private String email;
    private TypeAccount typeAccount;
    private String password;
}
