package cn.etcom.util.web.advice;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import cn.etcom.util.ErrorMassage;
import cn.etcom.util.MineException;

/**
 * 创建全局异常处理类：通过使用@ControllerAdvice定义统一的异常处理类，而不是在每个Controller中逐个定义。
 * 
 * @ControllerAdvice统一定义不同Exception映射到不同错误处理页面
 */
@ControllerAdvice
public class GlobalExceptionController {
	/**
	 * 去掉请求参数的首尾空格	
	 * @param webDataBinder
	 * @param webRequest
	 */
	@InitBinder
	public void initBinder(WebDataBinder webDataBinder, WebRequest webRequest) {
		webDataBinder.registerCustomEditor(String.class,
				new StringTrimmerEditor(true));
	}

	/**
	 * 在@ControllerAdvice类中，根据抛出的具体Exception类型匹配@ExceptionHandler中配置的异常类型来匹配错误映射和处理
	 */
	public static final String DEFAULT_ERROR_VIEW = "error";

	/**
	 * 捕获自定义异常，返回json信息
	 */
	@ExceptionHandler(value = MineException.class)
	@ResponseBody
	public ErrorMassage<Object> ErrorHandler(HttpServletRequest req, MineException e) throws Exception {
		ErrorMassage<Object> error = new ErrorMassage<>();
		e.printStackTrace();
		error.setCode(404);
		error.setMessage("Not Found Exception ！！！");
		error.setUrl(req.getRequestURL().toString());
		error.setData(e);
		return error;
	}
	
	/**
	 * @ExceptionHandler用来定义函数针对的异常类型，最后将Exception对象和请求URL映射到error.html中
	 */
	@ExceptionHandler(value = Exception.class)
	public ModelAndView ErrorHandler(HttpServletRequest req, Exception e) throws Exception {
		ModelAndView mav = new ModelAndView();
		ErrorMassage<Exception> error = new ErrorMassage<Exception>();
		e.printStackTrace();
		error.setCode(400);
		error.setMessage("系统异常");
		error.setUrl(req.getRequestURI());
		error.setData(e);
		mav.addObject("error",error);
		mav.setViewName(DEFAULT_ERROR_VIEW);
		return mav;
	}
}