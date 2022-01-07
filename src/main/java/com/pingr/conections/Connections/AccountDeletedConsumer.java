package com.pingr.conections.Connections;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AccountDeletedConsumer {
    private final AccountService service;

    @Autowired
    public AccountDeletedConsumer(AccountService service) {
        this.service = service;
    }

    @KafkaListener(
            topics = "${topic.delete}",
            groupId = "connection_account_deleted"
    )
    public void consume(/*String message*/Account account) throws IOException {
        /*ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(message, Account.class);
        System.out.println(account.toString());
        this.service.deleteAccount(account);*/
        this.service.deleteAccount(account);
    }
}
