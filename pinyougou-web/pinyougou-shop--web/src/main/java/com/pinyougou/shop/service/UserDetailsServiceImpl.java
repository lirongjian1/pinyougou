package com.pinyougou.shop.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.Seller;
import com.pinyougou.service.SellerService;
import com.pinyougou.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

/** 用户认证服务类 */
public class UserDetailsServiceImpl implements UserDetailsService{

    //因为spring容器的加载顺序问题 security这里没办法调用的这个服务接口 我们用创建SellerService.bean 注入属性sellerService的方法实现
    @Reference(timeout = 10000)
    private SellerService sellerService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException  {

        System.out.println(sellerService);
        System.out.println("username" + username);
        /** 创建List集合封装角色 */
        List<GrantedAuthority> list = new ArrayList<>();
        /** 添加角色 */
        GrantedAuthority role_seller = new SimpleGrantedAuthority("ROLE_SELLER");
        list.add(role_seller);
        /** 返回用户信息对象 */
       //根据前段传入用户名查询所有的用户 在根据用户对象得到密码
        Seller seller = sellerService.findById(username);
        if (seller != null && seller.getStatus().equals("1")){
            return new User(username,seller.getPassword(),list);
        }
        return null;
    }

    public void setSellerService(SellerService sellerService) {
        this.sellerService = sellerService;
    }
}
