package com.ghw.crm.service;

import com.ghw.crm.dto.CustomerQuery;
import com.ghw.crm.pojo.CrmCustomer;
import com.github.pagehelper.PageInfo;

public interface CustomerService {
    PageInfo<CrmCustomer> selectCustomerList(CustomerQuery customerQuery);

    CrmCustomer selectByID(Integer id);

    boolean updateCustomer(CrmCustomer customer);

    void deleteCustomerByID(Integer id);
}
