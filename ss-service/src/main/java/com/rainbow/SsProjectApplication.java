package com.rainbow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.social.connect.web.ConnectController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class SsProjectApplication {
    public static void main(String[] args) {
//        http://localhost/oauth/authorize?response_type=code&client_id=imooc&redirect_uri=http://example.com&scope=all
        SpringApplication.run(SsProjectApplication.class, args);
    }

}
