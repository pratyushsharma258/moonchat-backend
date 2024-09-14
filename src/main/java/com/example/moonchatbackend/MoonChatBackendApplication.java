package com.example.moonchatbackend;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MoonChatBackendApplication {
    @Autowired
    private org.springframework.jdbc.core.JdbcTemplate jdbcTemplate;

    private Logger logger = org.slf4j.LoggerFactory.getLogger(MoonChatBackendApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(MoonChatBackendApplication.class, args);
    }

//    @Override
//    public void run(String... args) throws Exception {
//        String sql = "DELETE FROM moonchat " +
//                "WHERE username = 'message'";
//
//        try {
//            jdbcTemplate.execute(sql);
//            logger.info("Deletion executed successfully.");
//        } catch (Exception e) {
//            e.printStackTrace();
//            logger.error("Error executing deletion: {}", e.getMessage());
//        }
//    }
}
