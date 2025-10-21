package com.itccloud;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.sql.DataSource;

@SpringBootApplication
public class StadiumRewardingSystemApplication {

    @Autowired
    private DataSource dataSource;

    public static void main(String[] args) {
        SpringApplication.run(StadiumRewardingSystemApplication.class, args);
    }

    @PostConstruct
    public void testConnection() throws Exception {
        System.out.println("Connected to: " + dataSource.getConnection().getMetaData().getURL());
    }

}
