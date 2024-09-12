package com.openclassrooms.mddapi.services.interfaces;


import com.openclassrooms.mddapi.dtos.TopicDTO;

import java.util.List;
import java.util.Map;

public interface TopicService {

    public Map<String, List<TopicDTO>> getAllTopics();
}
