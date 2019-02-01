package cn.etcom.service.user.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.etcom.dao.user.UserDao;
import cn.etcom.entity.model.user.UserDomain;
import cn.etcom.service.user.UserService;

/**
 * Created by Administrator on 2017/8/16.
 */
@Service(value = "userService")
public class UserServiceImpl implements UserService {
	
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;//这里会报错，但是并不会影响

    @Override
 //   @Transactional
    public int addUser(UserDomain user) {

        return userDao.insert(user);
    }

    /*
    * 这个方法中用到了我们开头配置依赖的分页插件pagehelper
    * 很简单，只需要在service层传入参数，然后将参数传递给一个插件的一个静态方法即可；
    * pageNum 开始页数
    * pageSize 每页显示的数据条数
    * */
    @Override
    @Cacheable(value = "user-cache",keyGenerator="cacheKeyGenerator")
    public PageInfo<UserDomain> findAllUser(int pageNum, int pageSize) {
    	  logger.info("没有走缓存！");
        //将参数传给这个方法就可以实现物理分页了，非常简单。
        PageHelper.startPage(pageNum, pageSize);
        List<UserDomain> userDomains = userDao.selectUsers();
        PageInfo<UserDomain> result = new PageInfo<UserDomain>(userDomains);
        return result;
    }
}
