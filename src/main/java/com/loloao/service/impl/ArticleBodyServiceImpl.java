package com.loloao.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.loloao.entity.ArticleBody;
import com.loloao.mapper.ArticleBodyMapper;
import com.loloao.service.ArticleBodyService;
import org.springframework.stereotype.Service;

/**
 * (ArticleBody)表服务实现类
 *
 * @author makejava
 * @since 2023-12-09 19:11:44
 */
@Service("articleBodyService")
public class ArticleBodyServiceImpl extends ServiceImpl<ArticleBodyMapper, ArticleBody> implements ArticleBodyService {

}
