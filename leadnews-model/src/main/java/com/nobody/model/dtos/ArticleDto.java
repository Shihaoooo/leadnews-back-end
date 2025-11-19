package com.nobody.model.dtos;

import com.nobody.model.pojos.ApArticle;
import lombok.Data;

@Data
public class ArticleDto extends ApArticle {

    private String content;
}
