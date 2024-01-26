package com.bookStore.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bookStore.pojo.Logs;
import com.bookStore.service.LogsService;
import com.bookStore.mapper.LogsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author 邓桂材
* @description 针对表【logs(日志表)】的数据库操作Service实现
* @createDate 2024-01-14 16:56:54
*/
@Service
public class LogsServiceImpl extends ServiceImpl<LogsMapper, Logs>
    implements LogsService{
@Autowired
private LogsMapper logsMapper;
    @Override
    public void saveLog(String logInfo) {
        Logs logs = new Logs();
        logs.setContent(logInfo);
        logsMapper.insert(logs);
    }
}




