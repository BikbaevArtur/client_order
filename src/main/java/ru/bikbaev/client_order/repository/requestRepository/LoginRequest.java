package ru.bikbaev.client_order.repository.requestRepository;

import org.springframework.stereotype.Repository;
import ru.bikbaev.client_order.model.entity.Login;
import ru.bikbaev.client_order.repository.JPA.JPALogin;

import java.util.Optional;

@Repository
public class LoginRequest {
    private final JPALogin jpaLogin;

    public LoginRequest(JPALogin jpaLogin) {
        this.jpaLogin = jpaLogin;
    }


    public Optional<Login> findByEmail(String email){
        return jpaLogin.findByEmail(email);
    }


    public void creatNewLogin(Login login){
        try{
            jpaLogin.save(login);
        }
        catch (Exception e){
            throw  new RuntimeException("Error creating new login: " + e.getMessage());
        }

    }


    public String updatePassword(String password,String email){

        try{
            Login login = findByEmail(email).orElseThrow(()-> new RuntimeException("login not found"));
            login.setPassword(password);
            return "OK";
        }
        catch (Exception e){
            throw  new RuntimeException("Error update login: " + e.getMessage());
        }


    }

    public String delete(int id){
        try{
            jpaLogin.deleteById(id);
            return "OK";
        }
        catch (Exception e){
            throw  new RuntimeException("Error delete login: " + e.getMessage());
        }

    }
}
