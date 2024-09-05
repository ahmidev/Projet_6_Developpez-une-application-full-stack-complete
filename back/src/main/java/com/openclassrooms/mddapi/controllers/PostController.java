package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dtos.fromView.CreateArticleDto;
import com.openclassrooms.mddapi.dtos.toView.ArticleDto;
import com.openclassrooms.mddapi.services.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/articles")
public class ArticleController {

    @Autowired
    ArticleService articleService;

    @GetMapping("")
    List<ArticleDto> getAllUsersSubscribedArticles(@PathVariable Long userId){

        return articleService.getAllUsersSubscribedArticles(userId);
    }

    @PostMapping("/create")
    ArticleDto createAnArticle(@RequestBody CreateArticleDto createArticle){
        return articleService.createAnArticle(createArticle);
    }


}