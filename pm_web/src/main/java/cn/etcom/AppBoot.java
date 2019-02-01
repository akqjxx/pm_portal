package cn.etcom;

import org.mybatis.spring.annotation.MapperScan;
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
@SpringBootApplication
@EnableSwagger2
@MapperScan("cn.etcom.dao")
@EnableCaching
@EnableTransactionManagement(order=10)
public class AppBoot {
	public static void main(String[] args) {
		SpringApplication.run(AppBoot.class, args);
	}
}

