package com.tsystems.core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;



/**
 * webmvc配置
 * @author lichunleiS
 * 
 *
 */
public class WebMvcConfig extends WebMvcConfigurationSupport {

	//日志
	//private final static  Logger logger = LoggerFactory.getLogger(WebMvcConfig.class);


	//拦截器跳过的url
	private String[] intercepterExcludPaths = new  String[] {"/error","/actuator/**", "/v2/api-docs",  "/swagger-resources/configuration/ui","/swagger-resources","/swagger-resources","/swagger-resources/configuration/security", "/swagger-ui.html","/webjars/**"};


//	@Value("${cats.jwt.secretKey:tsystem_cat}") 
//	private String jwtSecretKey;
	
	//是否做权限校验
//	@Value("${cats.permissionCheck:false}") 
//	private boolean permissionCheck;
	
//	@Autowired
//	private UserService userService;

	/**
	 * 添加拦截器
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		//日志打印

		RequestLogInterceptor li = new RequestLogInterceptor();
		registry.addInterceptor(li).excludePathPatterns(this.intercepterExcludPaths);

//		AuthUserRoleinterceptor authUserRoleinterceptor = new AuthUserRoleinterceptor(jwtSecretKey);
//		authUserRoleinterceptor.setUserService(userService);
//		authUserRoleinterceptor.setPermissionCheck(permissionCheck);
//		registry.addInterceptor(authUserRoleinterceptor);
		super.addInterceptors(registry);
	}

	/**
	 * 静态资源处理
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
		registry.addResourceHandler("swagger-ui.html")
		.addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("/webjars/**")
		.addResourceLocations("classpath:/META-INF/resources/webjars/");
		super.addResourceHandlers(registry);
	}

	/**
	 * 配置servlet处理
	 */
//	@Override
//	public void configureDefaultServletHandling(
//			DefaultServletHandlerConfigurer configurer) {
//		configurer.enable();
//	}

//	@Override
//	public void configureContentNegotiation(
//			ContentNegotiationConfigurer configurer) {
//		configurer.favorPathExtension(false);
//	}

//	/**
//	 * 使用ali 的json 替换原始的
//	 */
//	@Override
//	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//		super.configureMessageConverters(converters);
//		FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
//		FastJsonConfig fastJsonConfig = new FastJsonConfig();
//		//fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
//		fastJsonConfig.setCharset(Charset.forName("UTF-8"));
//		List<MediaType> supportedMediaTypes = new ArrayList<>();
//		supportedMediaTypes.add(MediaType.APPLICATION_JSON);
//		supportedMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
//		supportedMediaTypes.add(MediaType.APPLICATION_PDF);
//		//supportedMediaTypes.add(MediaType.APPLICATION_XML);
//		supportedMediaTypes.add(MediaType.IMAGE_GIF);
//		supportedMediaTypes.add(MediaType.IMAGE_JPEG);
//		supportedMediaTypes.add(MediaType.IMAGE_PNG);
//		supportedMediaTypes.add(MediaType.TEXT_PLAIN);
//
//		//MediaType mm = MediaType.valueOf("application/vnd.spring-boot.actuator.v2+json");
//		supportedMediaTypes.add(MediaType.valueOf("application/vnd.spring-boot.actuator.v2+json"));
//		fastConverter.setSupportedMediaTypes(supportedMediaTypes);
//		fastConverter.setFastJsonConfig(fastJsonConfig);
//
//		converters.add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
//		converters.add(fastConverter);
//		//converters.add(new MappingJackson2XmlHttpMessageConverter());
//	}
}
