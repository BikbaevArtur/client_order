package ru.bikbaev.client_order.mapped;

import org.springframework.stereotype.Component;
import ru.bikbaev.client_order.model.dtoApi.dtoAdminPanel.CompanyDTO;
import ru.bikbaev.client_order.model.dtoThym.CompanyCreatDTOModel;
import ru.bikbaev.client_order.model.entity.User;

@Component
public class CompanyMapper {

    public CompanyDTO convertCompanyDToToCompany(CompanyCreatDTOModel companyDTO, User user) {
        return new CompanyDTO(companyDTO.getId(),
                companyDTO.getNameCompany(),
                companyDTO.getInn(),
                companyDTO.getPaymentAccountNumbers(),
                companyDTO.getBikBank(),
                user.getId());
    }
}
