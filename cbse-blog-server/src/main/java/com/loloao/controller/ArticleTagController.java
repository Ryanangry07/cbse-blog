package com.loloao.controller;


import com.loloao.service.ArticleTagService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (ArticleTag)表控制层
 *
 * @author makejava
 * @since 2023-12-09 19:12:09
 */
@RestController
@RequestMapping("articleTag")
public class ArticleTagController {
    /**
     * 服务对象
     */
    @Resource
    private ArticleTagService articleTagService;


}

