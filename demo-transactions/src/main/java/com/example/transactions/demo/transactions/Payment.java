package com.example.transactions.demo.transactions;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Payment {

    @Id
    @GeneratedValue
    private Long id;

    private Long amount;

    @Column(unique = true)
    private String referenceNumber;

    @Enumerated(EnumType.STRING)
    private State state;

    // getters and setters

    public enum State {
        STARTED, FAILED, SUCCESSFUL
    }
}