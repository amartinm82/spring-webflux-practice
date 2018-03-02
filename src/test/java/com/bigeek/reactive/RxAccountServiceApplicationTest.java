package com.bigeek.reactive;

import com.bigeek.reactive.domain.Account;
import com.bigeek.reactive.repository.AccountRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.test.StepVerifier;

import static com.bigeek.reactive.constants.Accounts.*;

/**
 * Application test.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RxAccountServiceApplicationTest {

    @Autowired
    private WebTestClient webClient;

    @Autowired
    private AccountRepository accountRepository;

    @Test
    public void getAccount() {
        webClient.get().uri("/account/1").accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Account.class)
                .isEqualTo(ACCOUNT_1);
    }

    @Test
    public void getAccountNotFound() {
        webClient.get().uri("/account/100").accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    public void getAccountsByPersonId() {
        webClient.get().uri("/account/person/1").accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Account.class)
                .hasSize(2)
                .contains(ACCOUNT_1, ACCOUNT_2);
    }

    @Test
    public void getAccounts() {

        FluxExchangeResult<Account> result = webClient.get().uri("/account/")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.TEXT_EVENT_STREAM)
                .returnResult(Account.class);

        StepVerifier.create(result.getResponseBody())
                .expectNext(ACCOUNT_1, ACCOUNT_2, ACCOUNT_3, ACCOUNT_4)
                .expectComplete()
                .verify();
    }


    @Test
    public void createAccount() {
        webClient.post().uri("/account/")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(ACCOUNT_5))
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Account.class)
                .contains(ACCOUNT_5);

        accountRepository.delete(ACCOUNT_5);
    }
}
