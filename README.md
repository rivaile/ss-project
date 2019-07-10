"#ss-project"
-------------------------------------------
1. restful

2. swigger2

SpringSecurity

表单的认证
auth2认证


 

restful api

1. 用url描述资源   / 传统的api ---url描述行为
2. 使用http方法描述行为,使用http状态嘛表示不同的结果.get(查) post(增) delete(删) put(改)
3. 使用json交互数据
4. 这是一种风格


测试用例方便重构.

//异常的处理顺序:
Filter--->Interceptor--->ControllerAdvice--->Aspect--->controller

异步处理restful api 
runnable 
deferredResult

swigger2

ApiOperation

ApiModelProperty


springSecurity开发基于表单的认证:

SecurityContentPersisternceFilter--->UserPasswordAuthenticationFilter
-->BaseAuthenticationFilter--->RemoemberMeAuthenticationFilter--->ExceotionTranslationFilter-->

处理用户信息获取: UserDetailService

处理用户校验逻辑: UserDetails

处理密码加密解密: PasswordEncoder


自定义登录页面   Http.formLogin().loginPage("/")

自定义登录成功处理 AuthentictationSuccessHandler

自定义登录失败处理   AuthentictationFailureHandler


源码详解:

1. 认证流程说明: 

 登录请求-->UserPasswordAuthenticationFilter----->AuthenticationManager--->AuthenticationProvider
---->UserDetailService--->UserDetail--->
Authentication--->SecurityContent-->SecurityContentHolder-->SecurityContentPersisternceFilter


1-1:
	同时支持多种认证方式.
	同时支持多种前端渠道.
	支持集群环境,跨应用工作,Session控制,控制用户权限,防护与身份认证相关的攻击.
	
	
spring security:认证和授权的核心机制!--基于服务器session 

spring socail:第三方认证--基于服务器session 

spring security oauth -->app端--创建分发管理token用的!.
--------------------------------------------------------
-
第一章::

第二章:
	spring boot-->

第三章:
	jsonpath-->

3-6 :
restful 错误处理!!!------>BasicErrorController
	springboot 
		浏览器-->html
		client-->json
@RequestMapping(produces = "text/html")---->请求头中accept 带没带text/html的值!
 
 
自定义的异常处理!!!---->@ControllerAdvice----->处理其他控制器抛出的异常!!!
 
3-7:拦截处理:
	1. filter -->继承filter---->@Component  或者 新建一个配置文件 返回  FilterRegistrationBean  只能拿到请求
	
	
	2. springmvc 拦截器:--->实现HandlerInterceptor-->新建配置文件继承WebMvcConfigurerAdapter-->能拿到请求,和方法,不能拿到参数!!!
	
	
	3. 切片

3-10:多线程提高rest服务性能:
	配置继承WebMvcConfigurerAdapter-->复写configureAsyncSupport方法!!!-->用于拦截异步请求
	addInterceptors--->用于添加拦截同步请求!!
	
3-11:swigger2的使用!!
	@EnableSwagger2
	
	@ApiModelProperty
	@ApiModelProperty
	@ApiParam
	

第四章:
	
	认证流程???
	

6-1:

	token 与 cookie 比较,,都是字符串
	1. token 直接http 传输
	2. cookie 是服务器完成,,,不能干涉,,token 可以自定义生成
	3. cookie 失效需要重新登录,,token失效可以刷新token 在登录.
	
	privider  url
	response_type --->
	client_id-->那个应用
	那个用户-->
	scope-->
	state-->
	
	
	select t1.pcode attachSubType, t1.code attachSsType, t1.ptext catalog, t1.text description,  t1.need_upload required, t1.can_upload canUpload, max(t2.insert_time) lastUpload, count(t2.id)  fileCount from ( select rownum num, t.* from ( select a.code pcode, a.text ptext, b.code code,  b.text text, b.need_upload, b.can_upload from (select * from attachment_config where type =  'CHAIN_STORE_CAR_LOAN' and display_flag = '1') a, (select * from attachment_config where type  = 'CHAIN_STORE_CAR_LOAN' and display_flag = '1') b where a.code = b.parent_id order by a.order_by,  b.order_by ) t where 1=1 ) t1 left outer join ( select id, attach_sub_type, attach_ss_type,  insert_time, apply_order_id from attachment where item_id = 140813 and attach_type = 'CHAIN_STORE_CAR_LOAN'  and status = 'Y' and delete_status = 'N' ) t2 on t1.pcode = t2.attach_sub_type and t1.code  = t2.attach_ss_type group by t1.num, t1.pcode, t1.ptext, t1.code, t1.text, t1.need_upload,  t1.can_upload order by t1.num
	