package com.pratyushsharma258.moonchatbackend;

import com.pratyushsharma258.moonchatbackend.model.users.Role;
import com.pratyushsharma258.moonchatbackend.model.users.UserRole;
import com.pratyushsharma258.moonchatbackend.repository.RoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MoonChatBackendApplication {
    private Logger logger = LoggerFactory.getLogger(MoonChatBackendApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(MoonChatBackendApplication.class, args);
    }

    @Bean
    CommandLineRunner initRoles(RoleRepository roleRepository) {
        return args -> {
            if (roleRepository.findByName(Role.ROLE_USER).isEmpty()) {
                UserRole userRole = new UserRole(Role.ROLE_USER);
                roleRepository.save(userRole);
                logger.info("ROLE_USER added to the database.");
            }

            if (roleRepository.findByName(Role.ROLE_ADMIN).isEmpty()) {
                UserRole adminRole = new UserRole(Role.ROLE_ADMIN);
                roleRepository.save(adminRole);
                logger.info("ROLE_ADMIN added to the database.");
            }
        };
    }
}