package ibk.challenge.antifraud.service;

import ibk.challenge.antifraud.messages.UpdateStatusMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaPublisherService {

    @Autowired
    private KafkaTemplate<String, UpdateStatusMessage> kafkaTemplate;

    public void sendValueChangeStatusEvent(String topic, UpdateStatusMessage value) {
        kafkaTemplate.send(topic, value);
    }
}
