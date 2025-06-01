package pl.edu.pja.tpo_12.data;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.edu.pja.tpo_12.model.Role;
import pl.edu.pja.tpo_12.repository.RoleRepository;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initRoles(RoleRepository roleRepository) {
        return args -> {
            if (roleRepository.count() == 0) {
                Role adminRole = new Role("ROLE_ADMIN");
                roleRepository.save(adminRole);

                Role librarianRole = new Role("ROLE_LIBRARIAN");
                roleRepository.save(librarianRole);

                Role publisherRole = new Role("ROLE_PUBLISHER");
                roleRepository.save(publisherRole);

                Role readerRole = new Role("ROLE_READER");
                roleRepository.save(readerRole);
            }
        };
    }
}