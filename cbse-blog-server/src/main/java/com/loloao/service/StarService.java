package com.loloao.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.loloao.entity.Star;
import com.loloao.entity.Tag;

import java.util.Map;

public interface StarService extends IService<Star> {
    String loadStar(Star star);

    Map<String, Object> starArticle(Star star);
}
