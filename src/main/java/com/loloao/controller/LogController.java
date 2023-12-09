package com.loloao.controller;

import com.loloao.service.LogService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (Log)表控制层
 *
 * @author makejava
 * @since 2023-12-09 19:29:53
 */
@RestController
@RequestMapping("log")
public class LogController {
    /**
     * 服务对象
     */
    @Resource
    private LogService logService;
}

