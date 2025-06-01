package pl.edu.pja.tpo_12.data;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.management.relation.Role;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initRoles(RoleRepository roleRepository) {
        return args -> {
            if (roleRepository.count() == 0) {
                Role adminRole = new Role();
                adminRole.setName("ROLE_ADMIN");
                roleRepository.save(adminRole);

                Role librarianRole = new Role();
                librarianRole.setName("ROLE_LIBRARIAN");
                roleRepository.save(librarianRole);

                Role publisherRole = new Role();
                publisherRole.setName("ROLE_PUBLISHER");
                roleRepository.save(publisherRole);

                Role readerRole = new Role();
                readerRole.setName("ROLE_READER");
                roleRepository.save(readerRole);
            }
        };
    }
}