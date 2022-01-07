package com.pingr.conections.Connections;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component  // prepara para injeção de dependência do @Autowired
public class NewAccountConsumer {
    private final AccountService service;

    @Autowired
    public NewAccountConsumer(AccountService service) {
        this.service = service;
    }

    @KafkaListener(
            topics = "${topic.accounts}",
            groupId = "connection_new_accounts"
    )
    public void consume(Account account/*String message*/) throws IOException {
        /* mapper = new ObjectMapper();
        Account account = mapper.readValue(message, Account.class);
        System.out.println(account.toString());
        this.service.storeAccount(account);*/
        this.service.storeAccount(account);
    }
}
