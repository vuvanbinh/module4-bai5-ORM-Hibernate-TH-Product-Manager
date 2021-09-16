package com.codegym.config;

import com.codegym.service.IProductService;
import com.codegym.service.ProductService;
import com.codegym.service.ProductServiceDB;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.DriverManager;
import java.util.Properties;

@Configuration
@EnableWebMvc
@ComponentScan("com.codegym.controller")
@PropertySource("classpath:upload_file.properties")
public class AppConfig implements WebMvcConfigurer,  ApplicationContextAware {

    @Value("${file-upload}")
    private String fileUpload;

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    //Cấu hình Thymleaf
    @Bean
    public SpringResourceTemplateResolver templateResolver() {
        System.out.println("config 4--------------------");
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(applicationContext);
        templateResolver.setPrefix("/WEB-INF/views/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCharacterEncoding("UTF-8");
        return templateResolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine() {
        System.out.println("config 5--------------------");

        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        return templateEngine;
    }

    @Bean
    public ThymeleafViewResolver viewResolver() {
        System.out.println("config 6--------------------");

        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(templateEngine());
        viewResolver.setCharacterEncoding("UTF-8");
        return viewResolver;
    }

    @Bean
    public IProductService productService(){
        return new ProductServiceDB();
    }

    // Cấu hình upload file;
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/image/**")
                .addResourceLocations("file:" + fileUpload);

        System.out.println("config 7--------------------");
    }

    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver getResolver() throws IOException {
        System.out.println("config 8--------------------");

        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setMaxUploadSizePerFile(52428800);
        return resolver;
    }

//     Cấu hình với msql;

    Properties additionalProperties (){
        System.out.println("config 8.5--------------------");
        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto","update" );
        properties.setProperty("hibernate.dialect","org.hibernate.dialect.MySQL5Dialect");
        return properties;
    }

    @Bean
    public DataSource dataSource(){
        System.out.println("config 9----------config to msql----------");

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/md4_b5_th_product_manager");
        dataSource.setUsername("root");
        dataSource.setPassword("vubinh111111");
        return dataSource;
    }

    @Bean
    public LocalSessionFactoryBean sessionFactoryBean(){
        System.out.println("config 10--------------------");

        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource());
        sessionFactoryBean.setPackagesToScan("com.codegym.model");
        sessionFactoryBean.setHibernateProperties(additionalProperties());
        return sessionFactoryBean;
    }

    @Bean
    public EntityManager entityManager (EntityManagerFactory entityManagerFactory){
        System.out.println("config 11--------------------");

        return entityManagerFactory.createEntityManager();
    }


}