package com.plusesb.service.impl;

import org.springframework.stereotype.Service;
import com.plusesb.entity.ShCommentEntity;
import com.plusesb.service.ShCommentService;
import com.plusesb.mapper.ShCommentMapper;


@Service("shCommentService")
public class ShCommentServiceImpl extends BaseServiceImpl<ShCommentMapper, ShCommentEntity> implements ShCommentService {


}
