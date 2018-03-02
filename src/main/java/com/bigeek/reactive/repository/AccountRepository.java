package com.bigeek.reactive.repository;

import com.bigeek.reactive.domain.Account;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

/**
 * Reactive MongoDB repository.
 */
@Repository
public interface AccountRepository extends ReactiveMongoRepository<Account, Integer> {

    /**
     * Return a flux of accounts filtered by personId.
     *
     * @param personId person identifier.
     * @return a flux of accounts with that personId.
     */
    Flux<Account> findByPersonId(Integer personId);

}
