package com.rainbow.web.controller;

import com.rainbow.dto.User;
import com.rainbow.dto.UserQueryCondition;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * @author denglin
 * @version V1.0
 * @Description:Filter--->Interceptor--->ControllerAdvice--->Aspect--->controller restful 错误处理!!!------>BasicErrorController
 * @ClassName: UserController-
 * @date 2018/9/15 15:26
 */

@RestController
@RequestMapping("/user")
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 前后端分离,使用时间戳来传递.尽量不使用带格式的日期..
     * spring boot2 需要加上spring.jackson.serialization.write-dates-as-timestamps = true 进行转换...
     *
     * @param user
     * @param errors
     * @return
     */
    @PostMapping
    @ApiOperation(value = "创建用户")
    public User create(
            @Valid
            @RequestBody User user
            , BindingResult errors  //没有这个BindingResult,直接报400,不进入方法.与Valid一起用!
    ) {
        logger.info("--create--" + user);

        if (errors.hasErrors()) {
            errors.getAllErrors().stream().forEach(error -> {
                FieldError fieldError = (FieldError) error;
                String message = fieldError.getField() + " " + error.getDefaultMessage();
                System.out.println(message);
            });
        }
        user.setId("1");
        System.out.println(user);
        return user;
    }

    @DeleteMapping("/{id:\\d+}")
    public void delete(@PathVariable String id) {
        logger.info("--delete--id--" + id);
    }

    @PutMapping("/{id:\\d+}")
    public User update(@RequestBody User user) {
        logger.info("--update--id--" + user);
        user.setId("1");
        return user;
    }

    @GetMapping("/{id:\\d+}")
    public User get(@ApiParam("用户id") @PathVariable String id) {
        logger.info("--get--id--" + id);
        User user = new User();
        user.setUsername("tom");
        user.setBirthday(new Date());
        return user;
    }

    @GetMapping
    public List<User> query(@RequestParam(required = false, name = "username", defaultValue = "default") String username,
                            UserQueryCondition condition,
                            @PageableDefault(page = 2, size = 17, sort = "username,asc") Pageable pageable) {
        logger.info("--query--UserQueryCondition--" + ReflectionToStringBuilder.toString(condition, ToStringStyle.MULTI_LINE_STYLE));

        System.out.println("nickname:" + username);
        System.out.println(pageable.getPageSize());
        System.out.println(pageable.getPageNumber());
        System.out.println(pageable.getSort());

        List<User> users = new ArrayList<>();
        users.add(new User());
        users.add(new User());
        users.add(new User());
        return users;
    }


    @Autowired
    private ProviderSignInUtils providerSignInUtils;

    @PostMapping("/regist")
    public void regist(User user, HttpServletRequest request) {
        //不管是注册用户还是绑定用户，都会拿到一个用户唯一标识。
        String userId = user.getUsername();
        providerSignInUtils.doPostSignUp(userId, new ServletWebRequest(request)); //brower 注册
//		appSingUpUtils.doPostSignUp(new ServletWebRequest(request), userId);//app 注册
    }

    /**
     * 解析jwt信息
     * @param user
     * @return
     */
    @GetMapping("/me")
    public Object getCurrentUser(Authentication user)
//            throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException, IllegalArgumentException, UnsupportedEncodingException
    {

//		String token = StringUtils.substringAfter(request.getHeader("Authorization"), "bearer ");
//
//		Claims claims = Jwts.parser().setSigningKey(securityProperties.getOauth2().getJwtSigningKey().getBytes("UTF-8"))
//					.parseClaimsJws(token).getBody();
//
//		String company = (String) claims.get("company");

//		System.out.println(company);

        return user;
    }


}