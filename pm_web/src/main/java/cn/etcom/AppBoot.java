package cn.etcom;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author admin
 *
 */
@EnableScheduling
@EnableSwagger2
@MapperScan("cn.etcom.dao")
@EnableCaching
@EnableTransactionManagement(order=10)
@SpringBootApplication
public class AppBoot {
	private static final Logger logger = LoggerFactory.getLogger(AppBoot.class);
	public static void main(String[] args) {
		  SpringApplication application = new SpringApplication(AppBoot.class);
	      application.run(args);
	      logger.info("***********************项目启动*********************************");
	}
}

