package com.amigoscode;

import com.amigoscode.customer.Customer;
import com.amigoscode.customer.CustomerRepository;
import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import com.github.javafaker.Number;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Random;

@SpringBootApplication

public class Main {
    public static void main(String[] args) {

        SpringApplication.run(Main.class, args);

    }

    @Bean
    CommandLineRunner runner(CustomerRepository customerRepository) {

        var faker = new Faker();
        Random random = new Random();
        Name name = faker.name();
        String firstName = name.firstName();
        String lastName = name.lastName();
        return args -> {
            Customer customer = new Customer(
                    firstName + " " + lastName,
                    firstName + "." + lastName + "@amigoscode.com",
                    random.nextInt(16,99)
            );

            customerRepository.save(customer);
        };
    }
}







