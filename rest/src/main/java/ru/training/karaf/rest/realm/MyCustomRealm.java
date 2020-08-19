package ru.training.karaf.rest.realm;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import ru.training.karaf.model.User;
import ru.training.karaf.repo.UserRepo;
import ru.training.karaf.rest.ServiceUtils;
import ru.training.karaf.rest.dto.UserDTO;

import java.util.*;

public class MyCustomRealm extends AuthorizingRealm {



    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String username = (String) principalCollection.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.setStringPermissions(Collections.singleton("*"));
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String) token.getPrincipal();

        UserRepo repo=  ServiceUtils.getRepo();
        User user=repo.get(username).get();
        System.out.println(user);
        if (user == null) {
            throw new UnknownAccountException();
        }

        SimplePrincipalCollection principals = new SimplePrincipalCollection();
        principals.add(user.getLogin(), getName());
        principals.add(user, getName());
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(principals, user.getPassword());
        return authenticationInfo;
    }
}
