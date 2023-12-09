package com.loloao.controller;

import com.loloao.service.TagService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (Tag)表控制层
 *
 * @author makejava
 * @since 2023-12-09 19:10:18
 */
@RestController
@RequestMapping("tag")
public class TagController {
    /**
     * 服务对象
     */
    @Resource
    private TagService tagService;
}

