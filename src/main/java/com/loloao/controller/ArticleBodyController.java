package com.loloao.controller;



import com.loloao.service.ArticleBodyService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (ArticleBody)表控制层
 *
 * @author makejava
 * @since 2023-12-09 19:11:44
 */
@RestController
@RequestMapping("articleBody")
public class ArticleBodyController {
    /**
     * 服务对象
     */
    @Resource
    private ArticleBodyService articleBodyService;
}

