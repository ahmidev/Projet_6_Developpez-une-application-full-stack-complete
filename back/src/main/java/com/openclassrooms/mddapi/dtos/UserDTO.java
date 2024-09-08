package com.openclassrooms.mddapi.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.openclassrooms.mddapi.models.Subscription;
import jakarta.persistence.Column;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.List;

@Data
public class UserDTO {
    private Long id;


    private String email;


    private String name;


    @Column(name = "created_at")
    @JsonProperty("created_at")
    @CreationTimestamp
    private Instant createdAt;

    @Column(name = "updated_at")
    @JsonProperty("updated_at")
    @UpdateTimestamp
    private Instant updatedAt;

    private List<Subscription> subscriptions;


}