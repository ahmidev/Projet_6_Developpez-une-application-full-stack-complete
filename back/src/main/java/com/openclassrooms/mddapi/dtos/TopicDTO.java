package com.openclassrooms.mddapi.dtos;

import com.openclassrooms.mddapi.models.Subscription;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class TopicDTO {

    private Long id;
    private String name;
    private String description;
    private List<Subscription> subscriptions;
}
