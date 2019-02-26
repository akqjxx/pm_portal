package cn.etcom.util.web.conf;
  
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;  

/**
 * 
 * @author admin
 *
 */
@Configuration  
public class I18NConfig {  
    @Bean  
    public ResourceBundleMessageSource messageSource() {  
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();  
        messageSource.setUseCodeAsDefaultMessage(true);  
        messageSource.setFallbackToSystemLocale(false);  
        //根据系统语言，会自动读取application_zh_CN.properties文件
        messageSource.setBasename("application_zh_CN");  
        messageSource.setDefaultEncoding("UTF-8");  
        messageSource.setCacheSeconds(2);  
        return messageSource;  
    }  
}  