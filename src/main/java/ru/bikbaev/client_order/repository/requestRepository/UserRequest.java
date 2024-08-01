package ru.bikbaev.client_order.repository.requestRepository;

import org.springframework.stereotype.Repository;
import ru.bikbaev.client_order.model.entity.User;
import ru.bikbaev.client_order.repository.JPA.JPAUser;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRequest {
    private final JPAUser jpaUser;

    public UserRequest(JPAUser jpaUser) {
        this.jpaUser = jpaUser;
    }

    public User findById(int id){
        return jpaUser.findById(id).orElseThrow();
    }

    public Optional<User> findByEmail(String email){
        return jpaUser.findUserByEmail(email);
    }

    public List<User> getAll(){
        return jpaUser.findAll();
    }

    public User creatNewUser(User user){
        return jpaUser.save(user);
    }

    public void deleteUser(int id){
        jpaUser.deleteById(id);
    }
}
