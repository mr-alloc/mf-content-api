package com.cofixer.mf.mfcontentapi.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Objects;

@Slf4j
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "com.cofixer.mf.mfcontentapi.repository",
        entityManagerFactoryRef = "entityManagerFactory"
)
public class DataSourceConfiguration {


    @Bean
    @ConfigurationProperties("datasource.mission")
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource dataSource(
            @Qualifier("dataSourceProperties")
            DataSourceProperties dataSourceProperties
    ) {

        log.info("url: {}", dataSourceProperties.getUrl());
        log.info("username: {}", dataSourceProperties.getUsername());
        log.info("password: {}", dataSourceProperties.getPassword());
        return dataSourceProperties
                .initializeDataSourceBuilder()
                .build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            @Qualifier("dataSource") DataSource dataSource,
            EntityManagerFactoryBuilder builder
    ) {
        return builder
                .dataSource(dataSource)
                .persistenceUnit("mission")
                .packages("com.cofixer.mf.mfcontentapi.domain")
                .build();
    }

    @Bean
    public PlatformTransactionManager transactionManager(
            LocalContainerEntityManagerFactoryBean entityManagerFactory
    ) {
        return new JpaTransactionManager(Objects.requireNonNull(entityManagerFactory.getObject()));
    }

}
