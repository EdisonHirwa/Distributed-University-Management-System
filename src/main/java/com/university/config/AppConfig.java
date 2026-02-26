package com.university.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
// Scan everything EXCEPT @Controller classes (those are loaded by the web child context)
@ComponentScan(
    basePackages = "com.university",
    excludeFilters = @ComponentScan.Filter(
        type = FilterType.ANNOTATION,
        classes = org.springframework.stereotype.Controller.class
    )
)
@EnableTransactionManagement
public class AppConfig {

    @Bean
    public DataSource dataSource() {
        HikariDataSource ds = new HikariDataSource();
        ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
        ds.setJdbcUrl("jdbc:mysql://localhost:3306/university_db"
                + "?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true");
        ds.setUsername("root");
        ds.setPassword("");          // ← change to your MySQL password if needed
        ds.setMaximumPoolSize(10);
        ds.setMinimumIdle(2);
        ds.setConnectionTimeout(30000);
        return ds;
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sf = new LocalSessionFactoryBean();
        sf.setDataSource(dataSource());
        sf.setPackagesToScan("com.university.model");
        sf.setHibernateProperties(hibernateProperties());
        return sf;
    }

    private Properties hibernateProperties() {
        Properties props = new Properties();
        props.setProperty("hibernate.dialect",          "org.hibernate.dialect.MySQLDialect");
        props.setProperty("hibernate.hbm2ddl.auto",    "update");
        props.setProperty("hibernate.show_sql",        "true");
        props.setProperty("hibernate.format_sql",      "true");
        props.setProperty("hibernate.connection.handling_mode",
                          "DELAYED_ACQUISITION_AND_RELEASE_AFTER_TRANSACTION");
        return props;
    }

    @Bean
    public HibernateTransactionManager transactionManager() {
        HibernateTransactionManager tm = new HibernateTransactionManager();
        tm.setSessionFactory(sessionFactory().getObject());
        return tm;
    }
}
