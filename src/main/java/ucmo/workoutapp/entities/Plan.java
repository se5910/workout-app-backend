package ucmo.workoutapp.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@MappedSuperclass
public class Plan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long planId;

    // Each plan has one client
    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    private Client client;

    @Transient
    private Date created_At;
    private Date updated_At;

    public Plan(){}

    @PrePersist
    protected void onCreate() {this.created_At = new Date();}

    @PreUpdate
    protected void onUpdate() {this.updated_At = new Date();}

    public Long getPlanId() {
        return planId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Date getCreated_At() {
        return created_At;
    }

    public void setCreated_At(Date created_At) {
        this.created_At = created_At;
    }

    public Date getUpdated_At() {
        return updated_At;
    }

    public void setUpdated_At(Date updated_At) {
        this.updated_At = updated_At;
    }
}
