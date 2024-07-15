package it.unifi.stlab.faultflow.dao;

import it.unifi.stlab.faultflow.model.user.User;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Default;
import java.util.List;
import java.util.UUID;

@Dependent
@Default
public class UserDao extends BaseDao<User> {

    public UserDao() {
        super(User.class);
    }

    public List<User> getAll() {
        return entityManager.createQuery("SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.roles", User.class).getResultList();
    }

    public User getUserById(UUID userUUID){
        return entityManager.createQuery("SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.roles WHERE u.uuid = :uuid", User.class)
                .setParameter("uuid", userUUID).getSingleResult();
    }

    public User getUserByUsername(String username){
        return entityManager.createQuery("SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.roles WHERE u.username = :username", User.class)
                .setParameter("username", username).getSingleResult();
    }
}
