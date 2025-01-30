package io.github.kiraruto.encurtadorurl;

import io.github.kiraruto.encurtadorurl.domain.users.User;
import io.github.kiraruto.encurtadorurl.domain.users.repository.UserRepository;
import io.github.kiraruto.encurtadorurl.domain.users.role.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.management.relation.RoleResult;

@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception{
        User adminAccount = userRepository.findByRole(UserRole.ADMIN);
        if (null == adminAccount) {
            User user = new User();

            user.setEmail("admin@gmail.com");
            user.setUsername("admin");
            user.setPassword(new BCryptPasswordEncoder().encode("admin"));
            user.setUserRole(UserRole.ADMIN);

            userRepository.save(user);
        }
    }
}
