/*
 * @(#)DataEnvInitializer.java 0.1 Jul 10, 2009
 * Copyright 2004-2007  Co., Ltd. All rights reserved.
 */

package com.taole.portal.setup;

import java.util.Date;

import javax.annotation.Resource;

import com.taole.page.template.manager.PageTemplateManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.taole.framework.protocol.ProtocolRegistry;
import com.taole.framework.setup.Initializer;
import com.taole.usersystem.Constants;
import com.taole.usersystem.domain.Account;
import com.taole.usersystem.domain.Role;
import com.taole.usersystem.domain.User;
import com.taole.usersystem.manager.AccountManager;
import com.taole.usersystem.manager.RoleManager;
//import com.taole.usersystem.manager.AclManager;
import com.taole.usersystem.manager.UserSystemManager;

@Component
public class SetupInitializer extends Initializer {

    @Autowired
    private UserSystemManager userSystemManager;

    @Autowired
    private RoleManager roleManager;

    private PasswordEncoder passwordEncoder;

    @Autowired
    private ProtocolRegistry protocolRegistry;

    @Autowired
    private AccountManager accountManager;

    @Autowired
    PageTemplateManager pageTemplateManager;

    @Resource(name = "us.passwordEncoder")
    public void setPasswordEncoder(Object injectedPasswordEncoder) {
        if (injectedPasswordEncoder instanceof PasswordEncoder) {
            this.passwordEncoder = (PasswordEncoder) injectedPasswordEncoder;
        } else if (injectedPasswordEncoder instanceof org.springframework.security.crypto.password.PasswordEncoder) {
            final org.springframework.security.crypto.password.PasswordEncoder delegate =
                    (org.springframework.security.crypto.password.PasswordEncoder) injectedPasswordEncoder;
            this.passwordEncoder = new PasswordEncoder() {
                public String encodePassword(String rawPass, Object salt) {
                    checkSalt(salt);
                    return delegate.encode(rawPass);
                }

                public boolean isPasswordValid(String encPass, String rawPass, Object salt) {
                    checkSalt(salt);
                    return delegate.matches(rawPass, encPass);
                }

                private void checkSalt(Object salt) {
                    Assert.isNull(salt, "Salt value must be null when used with crypto module PasswordEncoder");
                }
            };
        }
    }

    private void checkAndInitializeSystemGroupAndRole() {

        // check or create role: Admin
        Role roleAdmin = roleManager.getRole(Role.ADMIN_ROLE_ID);
        if (roleAdmin == null) {
            roleAdmin = new Role();
            roleAdmin.setType(Role.Type.System);
            roleAdmin.setId(Role.ADMIN_ROLE_ID);
            roleAdmin.setName("Admin");
            roleAdmin.setDescription("默认超级管理员角色，对系统有不受限制的完全控制权");
            roleManager.createRole(roleAdmin);
        }

        User user = userSystemManager.getUser(Constants.ADMINISTRATOR_USERID);
        if (user == null) {
            user = new User();
            user.setId(Constants.ADMINISTRATOR_USERID);
            user.setEmployeeId("000");
            user.setCardId("000000000000000000");
            user.setPassword(passwordEncoder.encodePassword(Constants.ADMINISTRATOR_PASSWORD, null));
            user.setNickName(Constants.ADMINISTRATOR_NICKNAME);
            user.setRealName(Constants.ADMINISTRATOR_USERNAME);
            user.setDescription(Constants.ADMINISTRATOR_DESCRIPTION);
            user.setRegDate(new Date());
            user.setEmail(Constants.ADMINISTRATOR_EMAIL);
            user.setPersonId(Constants.ADMINISTRATOR_PERSONID);
            user.addRole(roleAdmin);

            String userId = userSystemManager.createUser(user);

            Account account = new Account();
            account.setLoginId(Constants.ADMINISTRATOR_ACCOUNT);
            account.setUserId(userId);
            accountManager.createAccount(account);
        }
    }

    public void initialize() {
        checkAndInitializeSystemGroupAndRole();
        //initResToTemplate();
    }

    public void initResToTemplate() {
        pageTemplateManager.copyResourcesToPoratl();
    }


}
