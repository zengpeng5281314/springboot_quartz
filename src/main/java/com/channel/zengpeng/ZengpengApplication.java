package com.channel.zengpeng;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.channel.zengpeng.config.MyExceptionResolver;

@SpringBootApplication
public class ZengpengApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZengpengApplication.class, args);
	}
	
	// 注册统一异常处理bean
    @Bean
    public MyExceptionResolver myExceptionResolver() {
        return new MyExceptionResolver();
    }

}
