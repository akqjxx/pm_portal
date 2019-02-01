package cn.etcom.dao.user;


import java.util.List;

import cn.etcom.entity.model.user.UserDomain;


public interface UserDao {


    int insert(UserDomain record);

    List<UserDomain> selectUsers();
}