package com.sidecar.customer;

import com.sidecar.customer.domain.AuthUser;
import com.sidecar.customer.repository.AuthUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@SpringBootApplication
@EnableCaching
public class CustomerServiceApplication  implements CommandLineRunner {

    private  final AuthUserRepository authUserRepository;
    private final PasswordEncoder passwordEncoder;

    public CustomerServiceApplication(AuthUserRepository authUserRepository, PasswordEncoder passwordEncoder){
        this.authUserRepository = authUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public static void main(String[] args) {
        SpringApplication.run(CustomerServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        // setup dummy admin user if already not present
        Optional<AuthUser> user = this.authUserRepository.findByUsername("admin");
        if(!user.isPresent()){
            String password = "admin123";
            String encodedPwd = this.passwordEncoder.encode(password);
            AuthUser adminUser = new AuthUser(null, encodedPwd, "admin", true, true, true, true);

            this.authUserRepository.save(adminUser);
        }
    }
}
