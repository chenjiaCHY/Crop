package com.ntschy.crop;

import com.ntschy.crop.datasource.aop.DynamicDataSourceAnnotationAdvisor;
import com.ntschy.crop.datasource.aop.DynamicDataSourceAnnotationInterceptor;
import com.ntschy.crop.datasource.dynamic.DynamicDatasourceRegister;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableAspectJAutoProxy
@Import(DynamicDatasourceRegister.class)
@SpringBootApplication
@EnableTransactionManagement
@EnableZuulProxy
public class CropApplication {

	public static void main(String[] args) {
		SpringApplication.run(CropApplication.class, args);
	}

	@Bean
	public DynamicDataSourceAnnotationAdvisor dynamicDataSourceAnnotationAdvisor() {
		return new DynamicDataSourceAnnotationAdvisor(new DynamicDataSourceAnnotationInterceptor());
	}
}
