package com.ghw.crm.service;

import com.ghw.crm.dto.LoginResult;
import com.ghw.crm.pojo.CrmUser;

public interface UserService {

    public CrmUser getUserByID(Integer id);

    LoginResult login(String usercode, String password);
}
