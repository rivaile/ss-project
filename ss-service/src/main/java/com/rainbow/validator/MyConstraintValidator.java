package com.rainbow.validator;


import com.rainbow.business.system.service.impl.HelloService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Object 可以放在任何类型上,这个类不用我们自己声明.
 */
public class MyConstraintValidator implements ConstraintValidator<MyConstraint, Object> {

    @Autowired
    private HelloService helloService;

    @Override
    public void initialize(MyConstraint myConstraint) {
        System.out.println("initialize init");
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        // fixme

        System.out.println("-------------" + o);
        helloService.greeting("tom");
        return true;
    }

}
