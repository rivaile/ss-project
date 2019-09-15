package com.rainbow;

import com.rainbow.dto.City;
import com.rainbow.mapper.CityMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.connect.web.ConnectController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@RestController
@MapperScan("com.rainbow.mapper")
public class SsProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(SsProjectApplication.class, args);
    }

    @Autowired
    private CityMapper cityMapper;

    @GetMapping("/test")
    public void test(){
        City city = new City();
        city.setName("San Francisco");
        city.setState("CA");
        city.setCountry("US");
        cityMapper.insert(city);
        System.out.println(this.cityMapper.findById(city.getId()));
    }
}
