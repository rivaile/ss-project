package com.rainbow;

import com.rainbow.dto.City;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@RestController
public class SsProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(SsProjectApplication.class, args);
    }

//    @Autowired
//    private CityMapper cityMapper;

    @GetMapping("/test")
    public void test() {
        City city = new City();
        city.setName("San Francisco");
        city.setState("CA");
        city.setCountry("US");
//        cityMapper.insert(city);
//        System.out.println(this.cityMapper.findById(city.getId()));
//        System.out.println(this.cityMapper.selectCityById(1));
    }
}
