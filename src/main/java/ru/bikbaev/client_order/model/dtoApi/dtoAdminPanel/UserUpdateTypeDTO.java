package ru.bikbaev.client_order.model.dtoApi.dtoAdminPanel;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserUpdateTypeDTO {
    private int userId;
    private int typeAccountId;
}
