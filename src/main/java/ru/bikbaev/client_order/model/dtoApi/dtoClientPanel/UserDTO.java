package ru.bikbaev.client_order.model.dtoApi.dtoClientPanel;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.bikbaev.client_order.model.entity.TypeAccount;

@Data
@AllArgsConstructor
public class UserDTO {
    private int id;
    private String lastName;
    private String firstName;
    private String middleName;
    private String phone;
    private String email;
    private TypeAccount typeAccount;
}
