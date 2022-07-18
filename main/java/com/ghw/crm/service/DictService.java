package com.ghw.crm.service;

import com.ghw.crm.pojo.CrmDict;

import java.util.List;

public interface DictService {
    //根据type_code 查询属性数据Dict
    List<CrmDict> selectDictByTypeCode(String code);
}
