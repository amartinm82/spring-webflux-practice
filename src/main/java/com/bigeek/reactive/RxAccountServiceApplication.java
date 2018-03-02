package com.bigeek.reactive;

import com.bigeek.reactive.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static com.bigeek.reactive.constants.Accounts.*;

/**
 * Reactive service to perform account operations.
 */
@SpringBootApplication
public class RxAccountServiceApplication implements CommandLineRunner {

    @Autowired
    private AccountRepository accountRepository;

    public static void main(String[] args) {
        SpringApplication.run(RxAccountServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        accountRepository.deleteAll().block();
        accountRepository.save(ACCOUNT_1).block();
        accountRepository.save(ACCOUNT_2).block();
        accountRepository.save(ACCOUNT_3).block();
        accountRepository.save(ACCOUNT_4).block();
    }
}
