package com.example.data_trans.exception;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {
    @Bean
    public DataSource dataSource() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl("jdbc:oracle:thin:@localhost:1521:orcl");
        hikariConfig.setUsername("C##HCNC");
        hikariConfig.setPassword("1234");
        hikariConfig.setDriverClassName("oracle.jdbc.OracleDriver");

        // HikariCP 설정
        hikariConfig.setMaximumPoolSize(10);  // 최대 풀 크기
        hikariConfig.setMinimumIdle(5);       // 최소 유휴 커넥션 수
        hikariConfig.setIdleTimeout(30000);   // 유휴 커넥션 타임아웃 (ms)
        hikariConfig.setConnectionTimeout(30000);  // 커넥션 타임아웃 (ms)
        hikariConfig.setMaxLifetime(1800000);  // 커넥션 최대 수명 (ms)

        return new HikariDataSource(hikariConfig);
    }
}
