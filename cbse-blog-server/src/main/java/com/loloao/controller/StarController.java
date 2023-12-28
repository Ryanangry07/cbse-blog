package com.loloao.controller;

import com.loloao.common.Base;
import com.loloao.common.Result;
import com.loloao.entity.Star;
import com.loloao.enums.ResultCode;
import com.loloao.service.StarService;
import com.loloao.utils.UserUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/star")
public class StarController {

    @Resource
    public StarService starService;

    @GetMapping()
    public Result star(Star star){
        if(UserUtils.getCurrentUser() == null){
            return Result.error(ResultCode.USER_NOT_LOGGED_IN);
        }
        Map<String, Object> result = starService.starArticle(star);
        return Result.success(result);
    }

    @GetMapping("/loadStar")
    public Result loadStar(Star star){
        String status = starService.loadStar(star);
        Result result = Result.success();
        result.simple().put("starStatus", status);
        return result;
    }
}
