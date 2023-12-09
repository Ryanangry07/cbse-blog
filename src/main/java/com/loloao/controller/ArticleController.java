package com.loloao.controller;



import com.loloao.service.ArticleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (Article)表控制层
 *
 * @author makejava
 * @since 2023-12-09 18:12:50
 */
@RestController
@RequestMapping("articles")
public class ArticleController {
    /**
     * 服务对象
     */
    @Resource
    private ArticleService articleService;
}

