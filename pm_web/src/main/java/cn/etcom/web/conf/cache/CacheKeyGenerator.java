package cn.etcom.web.conf.cache;
import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CacheKeyGenerator implements KeyGenerator {
	private static final Logger logger = LoggerFactory.getLogger(CacheKeyGenerator.class);
	@Override
	public Object generate(Object target, Method method, Object... params) {
		Object key=new BaseCacheKey(target,method,params);
		String genKey = key.toString();
		logger.debug("自定义key："+genKey);
		return genKey ;
	}
}