package it.unifi.stlab.faultflow.model.user;

import it.unifi.stlab.faultflow.model.knowledge.BaseEntity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User extends BaseEntity {
    private String username;
    private String password;
    private Date lastUpdate;
    private String name;
    private String surname;
    @ManyToMany
    @JoinTable(
            name="users_roles",
            joinColumns = @JoinColumn( name="user_uuid"),
            inverseJoinColumns = @JoinColumn( name="role_fk")
    )
    private List<Role> roles;

    public User(){

    }

    public User(String username, String password){
        this.uuid = UUID.randomUUID();
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
