package com.loloao.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.loloao.entity.Log;
import com.loloao.mapper.LogMapper;
import com.loloao.service.LogService;
import org.springframework.stereotype.Service;

/**
 * (Log)表服务实现类
 *
 * @author makejava
 * @since 2023-12-09 19:29:53
 */
@Service("logService")
public class LogServiceImpl extends ServiceImpl<LogMapper, Log> implements LogService {

}
