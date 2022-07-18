package com.ghw.crm.controller;

import com.ghw.crm.dto.CustomerQuery;
import com.ghw.crm.pojo.CrmCustomer;
import com.ghw.crm.pojo.CrmDict;
import com.ghw.crm.service.CustomerService;
import com.ghw.crm.service.DictService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class CustomerController {
    @Autowired
    CustomerService customerService;
    @Autowired
    DictService dictService;

    //从properties中注入typeCode
    @Value("${fromType}")
    private String fromTypes;

    @Value("${industryType}")
    private String industryTypes;

    @Value("${levelType}")
    private String levelTypes;

    //客户列表页面
    @RequestMapping("/customerList")
    public String showCustomerList(Model model, CustomerQuery customerQuery) {
        PageInfo<CrmCustomer> pageInfo = customerService.selectCustomerList(customerQuery);
        //客户列表信息
        List<CrmCustomer> customers = pageInfo.getList();
        //将总页数传给页面
        model.addAttribute("pages", pageInfo.getPages());

        model.addAttribute("customers", customers);
        //所有的客户来源
        List<CrmDict> fromType = dictService.selectDictByTypeCode(fromTypes);
        model.addAttribute("fromType", fromType);

        //客户行业
        List<CrmDict> industry = dictService.selectDictByTypeCode(industryTypes);
        model.addAttribute("industryType", industry);

        //客户级别
        List<CrmDict> level = dictService.selectDictByTypeCode(levelTypes);
        model.addAttribute("levelType", level);
        return "customer";
    }

    @ResponseBody
    @RequestMapping("/customer/edit")
    public CrmCustomer getCrmCustomerByID(Integer id) {
        return customerService.selectByID(id);
    }

    //根据主键更新客户
    @ResponseBody
    @RequestMapping("/customer/update")
    public String updateCustomer(CrmCustomer customer) {
        if (customerService.updateCustomer(customer)) {
            return "{\"msg\":\"更新成功\"}";
        }
        return "{\"msg\":\"更新失败\"}";
    }
    //根据主键删除客户
    @RequestMapping("/customer/delete")
    public String deleteCustomer(Integer id){
        customerService.deleteCustomerByID(id);
        return "customer";
    }
}
