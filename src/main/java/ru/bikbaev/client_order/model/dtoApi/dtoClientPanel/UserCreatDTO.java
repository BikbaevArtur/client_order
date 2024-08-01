package ru.bikbaev.client_order.model.dtoApi.dtoClientPanel;

import lombok.Data;
import ru.bikbaev.client_order.model.entity.User;

@Data
public class UserCreatDTO {
    private final User user;
    private final String password;
}
