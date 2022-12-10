package com.dw.data.exporter.common;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;

public class AbstractIntegrationTest {

    public static MySQLContainer mySQL = (MySQLContainer) new MySQLContainer("mysql:8.0").withUsername("testUsername")
            .withPassword("testPassword")
            .withDatabaseName("testDatabase")
            .withInitScript("./Java-TestaufgabeMySql.sql")
            .self();

    static {
        mySQL.start();
    }

    @DynamicPropertySource
    static void postgresqlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mySQL::getJdbcUrl);
        registry.add("spring.datasource.username", mySQL::getUsername);
        registry.add("spring.datasource.password", mySQL::getPassword);
    }
}
