package com.openclassrooms.mddapi.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.List;

@Data
public class UserUpdateDTO {

    private Long id;

    @NotBlank
    private String email;

    @NotBlank
    private String name;

    @NotBlank
    private String token;

    @Column(name = "created_at")
    @JsonProperty("created_at")
    @CreationTimestamp
    private Instant createdAt;

    @Column(name = "updated_at")
    @JsonProperty("updated_at")
    @UpdateTimestamp
    private Instant updatedAt;

    private List<SubscriptionDTO> subscriptions;

}
