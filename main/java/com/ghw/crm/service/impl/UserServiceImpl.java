package com.ghw.crm.service.impl;

import com.ghw.crm.dao.CrmUserMapper;
import com.ghw.crm.dto.LoginResult;
import com.ghw.crm.pojo.CrmUser;
import com.ghw.crm.pojo.CrmUserExample;
import com.ghw.crm.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    CrmUserMapper  userMapper;

    @Override
    public CrmUser getUserByID(Integer id) {
        return userMapper.selectByPrimaryKey(Long.valueOf(id));
    }

    @Override
    public LoginResult login(String usercode, String password) {
        LoginResult result = new LoginResult();
        if(StringUtils.isNotEmpty(usercode) &&StringUtils.isNotEmpty((password))) {
            //根据账号查询
            CrmUserExample crmUserExample = new CrmUserExample();
            CrmUserExample.Criteria criteria = crmUserExample.createCriteria();
            criteria.andUserCodeEqualTo(usercode);
            List<CrmUser> crmUsers = userMapper.selectByExample(crmUserExample);
            if (crmUsers.size() < 1)
                result.setMsg("账号不存在！");
                //判断密码是否匹配
            else if (DigestUtils.md5DigestAsHex(password.getBytes()).equals(crmUsers.get(0).getUserPassword()))
                result.setUser(crmUsers.get(0));
            else
                result.setMsg("密码不匹配！");
        }else {
            result.setMsg("账号或密码不能为空！");
        }
        return result;
    }
}

