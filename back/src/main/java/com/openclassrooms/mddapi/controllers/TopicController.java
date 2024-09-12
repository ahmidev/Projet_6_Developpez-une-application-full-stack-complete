package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dtos.TopicDTO;
import com.openclassrooms.mddapi.services.interfaces.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


@CrossOrigin
@RestController
@RequestMapping("/api/topics")
public class TopicController {

    @Autowired
    TopicService topicService;

    @GetMapping("")
    Map<String, List<TopicDTO>> getAllThemes(){

        return topicService.getAllTopics();
    }
}
