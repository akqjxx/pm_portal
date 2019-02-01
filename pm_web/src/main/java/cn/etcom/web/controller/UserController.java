package cn.etcom.web.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;

import cn.etcom.entity.model.ResponseObject;
import cn.etcom.entity.model.user.UserDomain;
import cn.etcom.service.user.UserService;
import io.swagger.annotations.Api;

/**
 * Created by Administrator on 2017/8/16.
 */
@Api(tags="系统测试")
@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;
    
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping("/addUser")
    //@LogControllerAnnotation(action=ActionEnum.ADD,desc="用户新增")
    public ResponseObject addUser(@RequestBody @Valid UserDomain user,  BindingResult  bindingResult){
    	ResponseObject res = ResponseObject.builder().build();
    	if(bindingResult.hasErrors()){
    		bindingResult.getFieldErrors()
    		             .forEach(x->logger.error(x.getDefaultMessage()));
    		List<String> err = bindingResult.getFieldErrors()
    				                        .stream()
    				                        .map(x->x.getDefaultMessage())
    				                        .collect(Collectors.toList());
    		res.setReturnMsg("数据绑定失败").setReturnCode(-1).setData(err);
			return res;
		}
    	int ret = userService.addUser(user);
    	
    	res.setReturnMsg("操作成功").setReturnCode(0).setData(user).setRows(ret);
        return res;
    }

    @GetMapping("/all")
    //@LogControllerAnnotation(action=ActionEnum.QUERY,desc="用户查询")
    public Object findAllUser(
            @RequestParam(name = "pageNum", required = false, defaultValue = "1")
                    int pageNum,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10")
                    int pageSize){
    	ResponseObject res = ResponseObject.builder().build();
    	//int i =1/0;
    	PageInfo<UserDomain> pageInfo = userService.findAllUser(pageNum,pageSize);
    	res.setData(pageInfo).setReturnMsg("操作成功").setReturnCode(0);
    	return res;
        
    }
}
