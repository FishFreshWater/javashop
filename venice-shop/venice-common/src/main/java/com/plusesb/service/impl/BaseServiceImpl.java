package com.plusesb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Sets;
import com.plusesb.dto.PageDTO;
import com.plusesb.dto.SearchFieldDTO;
import com.plusesb.dto.SearchSortDTO;
import com.plusesb.dto.SearchDTO;
import com.plusesb.entity.BaseEntity;
import com.plusesb.service.BaseService;
import com.plusesb.utils.BaseUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.util.*;


/**
 * Service基类,处理通用的crud的方法
 * @param <T>
 */
@SuppressWarnings("unchecked")
public class BaseServiceImpl<X extends BaseMapper<T>, T extends BaseEntity> extends ServiceImpl<X , T> implements BaseService<T>  {

	private static Logger logger = LoggerFactory.getLogger(BaseServiceImpl.class);

    /**
     * 通用的查询方法
     * @param simpleSearchDTO 入参dto
     * @param <T>
     * @return
     */
    @Override
    public  <T> PageDTO<T> findPageBySimpleSearch(SearchDTO simpleSearchDTO){

        IPage ipage =new Page(simpleSearchDTO.getPageIndex(),simpleSearchDTO.getPageSize());

        QueryWrapper wrapper = createQueryWrapper(simpleSearchDTO);
        IPage<T> mypage = this.page(ipage,wrapper);
        return new PageDTO(mypage);

    }

    public QueryWrapper<T> createQueryWrapper(SearchDTO simpleSearchDTO) {

        T t = null;
        if (BaseUtils.isNotEmpty(simpleSearchDTO.getEntityClass())){
            try {
                t = (T) simpleSearchDTO.getEntityClass().newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        QueryWrapper<T> wrapper = new QueryWrapper<T>(t);

        if (BaseUtils.isNotEmpty(simpleSearchDTO.getSqlSelect())){
            wrapper.select(simpleSearchDTO.getSqlSelect());
        }
        for(SearchFieldDTO fieldDTO:simpleSearchDTO.getFields()){
            if(StringUtils.isNotBlank(fieldDTO.getFiled()) && StringUtils.isNotBlank(fieldDTO.getOp())
                    && fieldDTO.getData() != null && StringUtils.isNotBlank(fieldDTO.getData().toString()))
            {
                wrapper = addQueryWrapper(fieldDTO,wrapper);
            }
        }
        if(simpleSearchDTO.getSorts().size()>0){
            for(SearchSortDTO sort:simpleSearchDTO.getSorts()){
                if(sort.isAsc()){
                    wrapper.orderByAsc(sort.getFiled());
                }else{
                    wrapper.orderByDesc(sort.getFiled());
                }
            }
        }else{
            wrapper.orderByDesc("id");
        }
        if (!simpleSearchDTO.getLimit().equals(0)){
            wrapper.last("LIMIT " + simpleSearchDTO.getLimit());
        }
        return wrapper;
    }

    @Override
    public <T> List<T> findAllBySimpleSearch(SearchDTO simpleSearchDTO) {

        QueryWrapper wrapper = createQueryWrapper(simpleSearchDTO);
        return this.list(wrapper);
    }

    @Override
    public Integer countBySimpleSearch(SearchDTO simpleSearchDTO) {
        QueryWrapper wrapper = createQueryWrapper(simpleSearchDTO);
        return this.count(wrapper);
    }

    @Override
    public List<T> findAll() {
        return this.list(new QueryWrapper<T>());
    }

    @Override
    public List<T> findByIds(List<Long> longs) {
        return this.list(new QueryWrapper<T>().in("id",longs));
    }

    private QueryWrapper addQueryWrapper(SearchFieldDTO fieldDTO, QueryWrapper wrapper) {


        String operation = fieldDTO.getOp();
        String filed = fieldDTO.getFiled();
        Object fieldValue = fieldDTO.getData();

        if ("between".equals(fieldDTO.getOp())) {
           throw new RuntimeException("between in grid query isn't implement.");
        } else if ("equals".equals(operation) || "eq".equals(operation)) {
            wrapper.eq(filed, fieldValue);
        } else if ("notEqual".equals(operation) || "ne".equals(operation)) {
            wrapper.ne(filed, fieldValue);
        } else if ("greaterThan".equals(operation) || "gt".equals(operation)) {
            wrapper.gt(filed, fieldValue);
        } else if ("greaterThanEqualTo".equals(operation) || "ge".equals(operation)) {
            wrapper.ge(filed, fieldValue);
        } else if ("in".equals(operation)) {
            wrapper.in(filed,StringUtils.split((String) fieldValue, ","));
        } else if ("notIn".equals(operation)) {
            wrapper.notIn(filed,StringUtils.split((String) fieldValue, ","));
        } else if ("lessThan".equals(operation) || "lt".equals(operation)) {
            wrapper.lt(filed, fieldValue);
        } else if ("lessThanEqualTo".equals(operation) || "le".equals(operation)) {
            wrapper.le(filed, fieldValue);
        } else if ("like".equals(operation) || "cn".equals(operation)) {
            wrapper.like(filed, fieldValue);
        } else if ("isNull".equals(operation)) {
            wrapper.isNull(filed);
        } else if ("isNotNull".equals(operation)) {
            wrapper.isNotNull(filed);
        }
        return wrapper;
    }
    private static final Set<String> PARSE_PATTERNS = Sets.newHashSet();
    static{
        PARSE_PATTERNS.add("yyyy-MM-dd");
        PARSE_PATTERNS.add("yyyy-MM-dd HH:mm:ss");
        PARSE_PATTERNS.add("yyyy-MM-dd HH:mm:ss.SSS");
        PARSE_PATTERNS.add("yyyyMMddHHmmss");
    }
    private static Date parseDate(String str) {
        try {
            return DateUtils.parseDate(str, PARSE_PATTERNS.toArray(new String[PARSE_PATTERNS.size()]));
        } catch (ParseException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException("无法解析日期：" + str, e);
        }
    }


    public  Map createSqlMap(SearchDTO simpleSearchDTO) {


        QueryWrapper<T> queryWrapper = this.createQueryWrapper(simpleSearchDTO);
        Map map = new HashMap();
        map.put("ew",queryWrapper);
        return map;
    }
}
