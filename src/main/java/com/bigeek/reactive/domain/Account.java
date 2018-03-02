package com.bigeek.reactive.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;

/**
 * Account entity.
 */
@Document(collection = "accounts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {

    /**
     * Account identifier.
     */
    @Id
    @JsonProperty
    private Integer accountId;

    /**
     * Identifier of the person owner of the account.
     */
    @NotNull
    @JsonProperty
    private Integer personId;

    /**
     * Account number.
     */
    @NotNull
    @JsonProperty
    private String accountNumber;

    /**
     * Balance.
     */
    @NotNull
    @JsonProperty
    private Double balance;
}
