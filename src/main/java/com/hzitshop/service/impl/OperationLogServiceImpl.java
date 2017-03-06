package com.hzitshop.service.impl;

import com.hzitshop.entity.OperationLog;
import com.hzitshop.mapper.OperationLogMapper;
import com.hzitshop.service.IOperationLogService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 冼耀基
 * @since 2017-02-12
 */
@Service
public class OperationLogServiceImpl extends ServiceImpl<OperationLogMapper, OperationLog> implements IOperationLogService {
	
}
