package ru.bikbaev.client_order.service.admin;

import org.springframework.stereotype.Service;
import ru.bikbaev.client_order.model.dto.CompanyDTO;
import ru.bikbaev.client_order.model.entity.Company;
import ru.bikbaev.client_order.model.entity.User;
import ru.bikbaev.client_order.repository.requestRepository.CompanyRequest;
import ru.bikbaev.client_order.repository.requestRepository.UserRequest;

import java.util.ArrayList;
import java.util.List;

@Service
public class CompanyService {
    private final CompanyRequest companyRequest;
    private final UserRequest userRequest;

    public CompanyService(CompanyRequest companyRequest, UserRequest userRequest) {
        this.companyRequest = companyRequest;
        this.userRequest = userRequest;
    }


    public CompanyDTO creatNewCompany(CompanyDTO companyDTO) {
        Company company = companyRequest.creatNewCompany(
                companyDTOConvertToCompany(
                        companyDTO
                ));
        companyDTO.setId(company.getId());
        return companyDTO;
    }


    public List<CompanyDTO> findAllCompany() {
        List<Company> companyList = companyRequest.getAll();
        List<CompanyDTO> companyDTOS = new ArrayList<>();
        companyList.forEach(company -> companyDTOS.add(companyConvertToCompanyDTO(company)));
        return companyDTOS;
    }

    public void deleteCompany(int id) {
        companyRequest.deleteCompany(id);
    }

    public CompanyDTO findById(int id){
        return companyConvertToCompanyDTO(companyRequest.findById(id).orElseThrow(
                ()-> new RuntimeException("company not found")));
    }


    /**
     * TODO  попробовтаь сделать через дженерики и if один метод для конверта companyDTO и company
     *
     * @param companyDTO
     * @return
     */
    private Company companyDTOConvertToCompany(CompanyDTO companyDTO) {
        User user = userRequest.findById(companyDTO.getUserId());

        Company company = new Company();

        company.setId(companyDTO.getId());
        company.setNameCompany(companyDTO.getNameCompany());
        company.setInn(companyDTO.getInn());
        company.setBikBank(companyDTO.getBikBank());
        company.setPaymentAccountNumbers(companyDTO.getPaymentAccountNumbers());
        company.setUser(user);

        return company;

    }


    private CompanyDTO companyConvertToCompanyDTO(Company company) {
        return new CompanyDTO(
                company.getId(),
                company.getNameCompany(),
                company.getInn(),
                company.getPaymentAccountNumbers(),
                company.getBikBank(),
                company.getUser().getId()
        );
    }


}
