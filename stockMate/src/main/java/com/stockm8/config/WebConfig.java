package com.stockm8.config;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.validation.MessageCodesResolver;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import com.stockm8.interceptor.AdminInterceptor;
import com.stockm8.interceptor.AuthorizationInterceptor;
import com.stockm8.interceptor.FlashMessageInterceptor;
import com.stockm8.interceptor.ManagerInterceptor;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration // Spring 설정 클래스임을 나타냅니다.
@EnableWebMvc
@ComponentScan(basePackages = "com.stockm8")
@MapperScan(basePackages = "com.stockm8.mapper") // 지정된 패키지에서 MyBatis Mapper 인터페이스를 스캔하여 등록합니다.
public class WebConfig implements WebMvcConfigurer {

    /**
     * 인증 및 권한 확인을 위한 AuthorizationInterceptor 주입
     */
    @Autowired
    private AuthorizationInterceptor authorizationInterceptor;
    
    @Autowired
    private FlashMessageInterceptor flashMessageInterceptor;
    
    @Autowired
    private AdminInterceptor adminInterceptor;
    
    @Autowired
    private ManagerInterceptor managerInterceptor;
    
    /**
     * 인터셉터 설정
     * 특정 URL 패턴에 대해 AuthorizationInterceptor를 적용하며, 일부 URL은 제외합니다.
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
    	
        registry.addInterceptor(flashMessageInterceptor).addPathPatterns("/**");
    	
    	List<String> excludedPaths = Arrays.asList(
    		    "/", "/favicon.ico", "/resources/**", "/user/**", "/static/**",
    		    "/api/**", "/dashboard", "/business", "/category/**", "/product/**",
    		    "/warehouse/**", "/stock/**", "/order/**"
    		);
    	
        registry.addInterceptor(adminInterceptor)
        		.addPathPatterns("/admin/**") // /admin/** 경로에만 적용
        		.excludePathPatterns(excludedPaths.toArray(new String[0]));
        
        registry.addInterceptor(managerInterceptor)
		.addPathPatterns("/manager/**") // /admin/** 경로에만 적용
		.excludePathPatterns(excludedPaths.toArray(new String[0]));
        		
    	// Intercepter 적용
        registry.addInterceptor(authorizationInterceptor)
				.addPathPatterns("/dashboard", "/business", "/category/**", "/product/**", "/warehouse/**", "/stock/**", "/order/**") // 인터셉터를 적용할 경로
                .excludePathPatterns(                  // 제외할 경로들
                        "/",                           // 홈 경로 제외
                        "/favicon.ico",                // 브라우저 기본 요청 제외
                        "/resources/**",               // 정적 리소스 제외
                        "/user/**",                    // 로그인, 회원가입 관련 제외
                        "/static/**",                  // 정적 리소스 제외
                        "/api/**");
  
        // LocaleChangeInterceptor를 추가하면, 요청 파라미터 (예: ?lang=ko)로 Locale을 변경할 수 있음
        LocaleChangeInterceptor localeInterceptor = new LocaleChangeInterceptor();
        localeInterceptor.setParamName("lang");
        registry.addInterceptor(localeInterceptor);
    }

    /**
     * 정적 리소스 핸들러 설정
     * 특정 경로에 있는 정적 리소스를 처리합니다.
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 기존 정적 리소스 핸들러
        registry.addResourceHandler("/resources/**")
                .addResourceLocations("/resources/");
        
        // QR 코드 업로드 디렉터리 매핑 추가
        registry.addResourceHandler("/upload/**")
                .addResourceLocations("file:/Users/Insung/Documents/upload/");
//        		.addResourceLocations("file:/usr/local/tomcat/webapps/upload/");
    }
    
    /**
     * 뷰 리졸버 설정
     * 컨트롤러에서 반환된 뷰 이름에 대해 JSP 파일을 찾는 경로와 확장자를 설정합니다.
     */
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        // JSP 파일 위치와 확장자 설정
        registry.jsp("/WEB-INF/views/", ".jsp");
    }
    
    // LocaleResolver 빈 등록
    // AcceptHeaderLocaleResolver: 브라우저의 Accept-Language 헤더를 기반으로 Locale 결정
    @Bean
    public LocaleResolver localeResolver() {
        AcceptHeaderLocaleResolver localeResolver = new AcceptHeaderLocaleResolver();
        // 기본 로케일 설정 (예: 한국어)
        localeResolver.setDefaultLocale(Locale.KOREAN);
        return localeResolver;
    }
    
    // MessageSource 빈 등록
    // messages.properties, messages_ko.properties, messages_en.properties 등을 사용
    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        // classpath 상의 messages로 시작하는 프로퍼티 파일들을 로딩
        messageSource.setBasename("messages");
        messageSource.setDefaultEncoding("UTF-8");
        // 필요하다면 CacheSeconds 등 추가 설정 가능
        return messageSource;
    }
    
    /**
     * 데이터 소스 빈 생성 (HikariCP를 사용한 커넥션 풀 설정)
     */
    @Bean
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName("net.sf.log4jdbc.sql.jdbcapi.DriverSpy");
        config.setJdbcUrl("jdbc:log4jdbc:mysql://itwillbs.com:3306/c7d2408t1p1");
        config.setUsername("c7d2408t1p1");
        config.setPassword("1234");
        
        // HikariCP 추가 설정
        config.setMaximumPoolSize(10); // 최대 커넥션 풀 크기
        config.setMinimumIdle(5); // 최소 유휴 커넥션 수
        config.setIdleTimeout(30000); // 유휴 커넥션 종료 시간 (밀리초)
        config.setConnectionTimeout(30000); // 연결 타임아웃 (밀리초)
        config.setLeakDetectionThreshold(2000); // 누수 감지 시간 (밀리초)
        
        return new HikariDataSource(config);
    }
    
    /**
     * 트랜잭션 관리자 빈 생성
     * Spring의 트랜잭션 관리 기능을 사용하기 위해 DataSourceTransactionManager를 빈으로 등록합니다.
     */
    
    @Bean
    public org.apache.ibatis.session.Configuration myBatisConfiguration() {
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setMapUnderscoreToCamelCase(true); // 언더스코어 -> 카멜케이스 매핑
        configuration.setDefaultStatementTimeout(30);  // 타임아웃 설정 (초)
        return configuration;
    }

    /**
     * MyBatis SqlSessionFactory 빈 생성
     */
    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setConfiguration(myBatisConfiguration());
        return sqlSessionFactoryBean.getObject();
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
    
    @MapperScan(basePackages = "com.stockm8.mapper")
    public class MyBatisConfig {
        // SqlSessionFactory와 DataSourceTransactionManager는 위에 작성된 코드 사용
    }
    
	@Override
	public Validator getValidator() {
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        // Bean Validation 메시지 국제화 지원을 위해 messageSource를 등록
        validator.setValidationMessageSource(messageSource());
        return validator;
	}
	
    
    // 이하 WebMvcConfigurer의 기타 메서드는 필요에 따라 구현
	@Override
	public void configurePathMatch(PathMatchConfigurer configurer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addFormatters(FormatterRegistry registry) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public MessageCodesResolver getMessageCodesResolver() {
		// TODO Auto-generated method stub
		return null;
	}
	


}