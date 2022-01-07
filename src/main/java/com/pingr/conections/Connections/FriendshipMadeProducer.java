package com.pingr.conections.Connections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendshipMadeProducer {
    @Value(value = "${topic.friendship.made}")
    private String topic;

    @Autowired
    private KafkaTemplate<String, Object> template;

    public void sendMessage(List<Account> message) {
        this.template.send(this.topic, message);
        System.out.println("Enviou mensagem FriendShip Made: "+message.size() + " contas.");
    }
}
