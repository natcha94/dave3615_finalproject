package no.oslomet.userservice;

import no.oslomet.userservice.model.Role;
import no.oslomet.userservice.model.User;
import no.oslomet.userservice.repository.RoleRepository;
import no.oslomet.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

@SpringBootApplication
public class UserServiceApplication implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }

    @Override
    public void run(String...args) throws Exception {
        roleRepository.save(new Role(1, "User"));
        roleRepository.save(new Role(2, "Admin"));
    }
}
