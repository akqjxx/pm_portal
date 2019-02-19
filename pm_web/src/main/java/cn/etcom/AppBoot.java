package cn.etcom;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import cn.etcom.web.conf.properties.PropertiesListener;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author admin
 *
 */
@EnableScheduling
@SpringBootApplication
@EnableSwagger2
@MapperScan("cn.etcom.dao")
@EnableCaching
@EnableTransactionManagement(order=10)
public class AppBoot {
	public static void main(String[] args) {
		  SpringApplication application = new SpringApplication(AppBoot.class);
		  application.addListeners(new PropertiesListener("mvc-redirect.properties"));
	      application.run(args);
	}
}

