package org.personal;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Properties;
import java.util.TimeZone;
import org.personal.data.employee.converters.EmployeeConverter;
import org.personal.interceptors.FirstInterceptor;
import org.personal.interceptors.SecondInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.http.converter.xml.SourceHttpMessageConverter;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.view.BeanNameViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = { "org.personal" })
public class WebMvcConfig implements WebMvcConfigurer
{

    public static final String DISPLAY_DATETIME_FORMAT = "yyyy-MM-dd'T'HH:mm";

    @Autowired
    private LocaleChangeInterceptor localeChangeInterceptor;

    @Autowired
    private FirstInterceptor firstInterceptor;

    @Autowired
    private SecondInterceptor secondInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry)
    {
        registry.addInterceptor(localeChangeInterceptor);
        registry.addInterceptor(secondInterceptor).addPathPatterns("/employee");
        registry.addInterceptor(firstInterceptor);
    }

//    @Override
//    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers)
//    {
//        resolvers.add(simpleMappingExceptionResolver());
//    }

    /**
     * #错误解决#
     * 方法3, SimpleMappingExceptionResolver to do the mapping
     * 注意：如果配置了 SimpleMappingExceptionResolver, 那么这个 SystemExceptionHandler 就完全不起作用了
     */
//    @Bean
//    public SimpleMappingExceptionResolver simpleMappingExceptionResolver() {
//        SimpleMappingExceptionResolver resolver = new SimpleMappingExceptionResolver();
//        Properties properties = new Properties();
//
//        //Once have such exception, go to "error" page
//        properties.setProperty(ArrayIndexOutOfBoundsException.class.getCanonicalName(), "error");
//
//        resolver.setExceptionMappings(properties);
//        resolver.setDefaultErrorView("error");//Notice: Here will retrieve all the exception by default
//        resolver.setExceptionAttribute("customizeExceptionWrittenInWebMvcConfigClass");
//        return resolver;
//    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry)
    {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters)
    {
        converters.add(new ByteArrayHttpMessageConverter());
        converters.add(new StringHttpMessageConverter());
        converters.add(new ResourceHttpMessageConverter());
        converters.add(new SourceHttpMessageConverter<>());
        converters.add(new Jaxb2RootElementHttpMessageConverter());
        converters.add(new AllEncompassingFormHttpMessageConverter());
        converters.add(jackson2Converter());
    }

    @Bean
    public MappingJackson2HttpMessageConverter jackson2Converter()
    {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(objectMapper());
        return converter;
    }

    @Bean
    public MultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(1024 * 1024 * 1024); // 10MB
        multipartResolver.setMaxUploadSizePerFile(1024 * 1024 * 1024); // 1MB
        return multipartResolver;
    }

    @Bean
    public ObjectMapper objectMapper()
    {
        TimeZone timezone = TimeZone.getDefault();

        DateFormat dateFormat = new SimpleDateFormat(DISPLAY_DATETIME_FORMAT);
        dateFormat.setTimeZone(timezone);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.setTimeZone(timezone);
        objectMapper.getDateFormat().setTimeZone(timezone);

        return objectMapper;
    }

    @Autowired
    private EmployeeConverter employeeConverter;

    @Override
    public void addFormatters(FormatterRegistry registry)
    {
        registry.addConverter(employeeConverter);
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry)
    {
        //Order 没有什么作用，引入顺序才是关键
        registry.viewResolver(beanNameViewResolver());
        registry.viewResolver(internalResourceViewResolver());
    }

    private InternalResourceViewResolver internalResourceViewResolver()
    {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setViewClass(JstlView.class);
        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".jsp");
        return resolver;
    }

    private BeanNameViewResolver beanNameViewResolver()
    {
        return new BeanNameViewResolver();
    }
}
