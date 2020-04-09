package ucmo.workoutapp.entities;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    @NotEmpty
    private String role;

    public Role(){ }

    public Role(Integer id){
        this.id = id;
    }

    public Role(String authority){
        this.role = authority;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}

