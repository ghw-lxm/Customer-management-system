package com.ghw.crm.service.impl;

import com.ghw.crm.dao.CrmDictMapper;
import com.ghw.crm.pojo.CrmDict;
import com.ghw.crm.pojo.CrmDictExample;
import com.ghw.crm.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DictServiceImpl implements DictService {

    @Autowired
    private CrmDictMapper dictMapper;

    @Override
    public List<CrmDict> selectDictByTypeCode(String code) {
        //构造一个条件对象
        CrmDictExample crmDictExample = new CrmDictExample();
        CrmDictExample.Criteria criteria = crmDictExample.createCriteria();
        //加了一个 and typeCode = ?
        criteria.andDictTypeCodeEqualTo(code);
        return dictMapper.selectByExample(crmDictExample);
    }
}