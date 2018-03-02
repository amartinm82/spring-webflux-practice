package com.bigeek.reactive.controller;

import com.bigeek.reactive.domain.Account;
import com.bigeek.reactive.exception.AccountNotFoundException;
import com.bigeek.reactive.repository.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.time.Duration;

/**
 * Account controller. Exposes the resources of the service.
 */
@RestController
@RequestMapping("/account")
@Slf4j
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;

    /**
     * Create new accounts with the values of the request body.
     *
     * @param accountFlux flux of accounts to create.
     * @return a response with created accounts.
     */
    @PostMapping("/")
    Flux<Account> create(@RequestBody Flux<Account> accountFlux) {
        log.info("Creating accounts");
        // log the received flux of accounts
        return accountRepository.saveAll(accountFlux).log();
    }

    /**
     * Return all the accounts with the given person identifier.
     *
     * @param personId person identifier.
     * @return a response with all the accounts with that personId.
     */
    @GetMapping("/person/{personId}")
    Flux<Account> list(@PathVariable Integer personId) {
        log.debug("Getting accounts with personId {}", personId);
        return accountRepository.findByPersonId(personId);
    }

    /**
     * Return the account with the passed identifier.
     *
     * @param id account identifier.
     * @return a response with that identifier, or not found response.
     */
    @GetMapping("/{id}")
    Mono<Account> findById(@PathVariable Integer id) {
        log.debug("Getting account with id {}", id);
        return accountRepository.findById(id).switchIfEmpty(Mono.error(new AccountNotFoundException()));
    }

    /**
     * Return all the accounts.
     *
     * @return a response with all the accounts with an interval between accounts of 2 seconds.
     */
    @GetMapping(value = "/", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    Flux<Account> findAll() {
        log.debug("Getting all accounts with 2 seconds interval");
        Flux<Long> interval = Flux.interval(Duration.ofSeconds(2));
        return Flux.zip(interval, accountRepository.findAll()).map(Tuple2::getT2);
    }

    /**
     * Handle received AccountNotFoundException and return a not found response.
     *
     * @param exc an accountNotFoundException.
     * @return a not found response.
     */
    @ExceptionHandler
    public ResponseEntity handleNotFoundException(AccountNotFoundException exc) {
        log.debug("AccountNotFoundException received --> Responding with Not Found.");
        return ResponseEntity.notFound().build();
    }
}
