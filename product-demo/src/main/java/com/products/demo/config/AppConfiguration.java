package com.products.demo.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.r2dbc.connectionfactory.init.CompositeDatabasePopulator;
import org.springframework.data.r2dbc.connectionfactory.init.ConnectionFactoryInitializer;
import org.springframework.data.r2dbc.connectionfactory.init.ResourceDatabasePopulator;
import io.r2dbc.spi.ConnectionFactory;


/**
 * Class to initialize any configurations or beans needed for application.
 *
 */
@Configuration
public class AppConfiguration {

  /**
   * When using R2DBC, there is no support in Spring Boot to for initialising a database using
   * schema.sql or data.sql. Database cannot be initialized with schema or seed data by annotating
   * the configuration class with @EnableAutoConfiguration.
   * 
   * @param connectionFactory
   * @return connectionFactoryInitializer
   */
  @Bean
  public ConnectionFactoryInitializer databaseInitializer(ConnectionFactory connectionFactory) {

    ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
    initializer.setConnectionFactory(connectionFactory);

    CompositeDatabasePopulator populator = new CompositeDatabasePopulator();
    populator.addPopulators(new ResourceDatabasePopulator(new ClassPathResource("schema.sql")));
    populator.addPopulators(new ResourceDatabasePopulator(new ClassPathResource("data.sql")));
    initializer.setDatabasePopulator(populator);

    return initializer;
  }

}
