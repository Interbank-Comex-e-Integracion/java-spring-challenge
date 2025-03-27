package ibk.challenge.transaction.service;

import ibk.challenge.transaction.messages.RegisterMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaPublisherService {

    @Autowired
    private KafkaTemplate<String, RegisterMessage> kafkaTemplate;

    public void sendValueInRegisterEvent(String topic, RegisterMessage value) {
        kafkaTemplate.send(topic, value);
    }
}
