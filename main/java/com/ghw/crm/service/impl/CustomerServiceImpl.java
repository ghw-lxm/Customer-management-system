package com.ghw.crm.service.impl;

import com.ghw.crm.dao.CrmCustomerMapper;
import com.ghw.crm.dto.CustomerQuery;
import com.ghw.crm.pojo.CrmCustomer;
import com.ghw.crm.pojo.CrmCustomerExample;
import com.ghw.crm.pojo.CrmDict;
import com.ghw.crm.service.CustomerService;
import com.ghw.crm.service.DictService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    private List<CrmDict> fromType,industryType,levelType;

    @Autowired
    CrmCustomerMapper mapper;

    @Autowired
    DictService dictService;

    @Override
    public PageInfo<CrmCustomer> selectCustomerList(CustomerQuery customerQuery) {

        //根据查询对象  进行条件查询
        CrmCustomerExample crmCustomerExample = new CrmCustomerExample();
        CrmCustomerExample.Criteria criteria = crmCustomerExample.createCriteria();
//        if (queryDTO.getCustName()!=null && queryDTO.getCustName() .equals(""))
        //名称
        if (StringUtils.isNotEmpty(customerQuery.getCustName())){
            criteria.andCustNameLike("%"+customerQuery.getCustName()+"%");
        }
        //行业
        if (StringUtils.isNotEmpty(customerQuery.getCustIndustry())){
            criteria.andCustIndustryEqualTo(customerQuery.getCustIndustry());
        }
        //来源
        if (StringUtils.isNotEmpty(customerQuery.getCustSource())){
            criteria.andCustSourceEqualTo(customerQuery.getCustSource());
        }
        //级别
        if (StringUtils.isNotEmpty(customerQuery.getCustLevel())){
            criteria.andCustLevelEqualTo(customerQuery.getCustLevel());
        }

        //启用分页插件
        //该方法必须放在要进行查询的sql语句前面，即只对接下来要执行的第一条语句执行
        PageHelper.startPage(customerQuery.getPage(),customerQuery.getSize());

        //在返回customer之前，先从customer对象中查询对应属性名称

        List<CrmCustomer> customers = mapper.selectByExample(crmCustomerExample);
        //通过分页插件获取分页信息 以及分页相关的数据
        PageInfo<CrmCustomer> pageInfo = new PageInfo<>(customers);
        //将dict的id转为名称
        translateDictIDToName(customers);
        return pageInfo;
    }

    @Override
    public CrmCustomer selectByID(Integer id) {
          return  mapper.selectByPrimaryKey(id.longValue());

    }

    @Override
    public boolean updateCustomer(CrmCustomer customer) {
        return mapper.updateByPrimaryKey(customer) > 0;
    }

    @Override
    public void deleteCustomerByID(Integer id) {
        mapper.deleteByPrimaryKey(id.longValue());
    }


    private void translateDictIDToName(List<CrmCustomer> customers) {

        //仅在第一次查询数据库
        if(levelType == null){
            fromType = dictService.selectDictByTypeCode("002");
            industryType = dictService.selectDictByTypeCode("001");
            levelType = dictService.selectDictByTypeCode("006");
        }
        //为了避免数据库的多次访问，可以先把需要的东西查询出来
        for (CrmCustomer customer: customers) {
            for(CrmDict dict:levelType){
                if(dict.getDictId().equals(customer.getCustLevel())){
                    customer.setCustLevel(dict.getDictItemName());
                }
            }
            for(CrmDict dict:fromType){
                if(dict.getDictId().equals(customer.getCustSource())){
                    customer.setCustSource(dict.getDictItemName());
                }
            }
            for(CrmDict dict:industryType){
                if(dict.getDictId().equals(customer.getCustIndustry())){
                    customer.setCustIndustry(dict.getDictItemName());
                }
            }
        }
    }
}
