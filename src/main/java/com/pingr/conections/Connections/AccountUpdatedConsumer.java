package com.pingr.conections.Connections;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AccountUpdatedConsumer {
    private final AccountService service;

    @Autowired
    public AccountUpdatedConsumer(AccountService service) {
        this.service = service;
    }

    @KafkaListener(
            topics = "${topic.update}",
            groupId = "connection_account_updated"
    )
    public void consume(String message) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(message, Account.class);
        System.out.println(account.toString());
        this.service.updateAccount(account);
    }
}
