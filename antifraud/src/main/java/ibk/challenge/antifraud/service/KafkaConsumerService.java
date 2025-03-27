package ibk.challenge.antifraud.service;

import com.google.gson.Gson;
import ibk.challenge.antifraud.definition.StatusDefinition;
import ibk.challenge.antifraud.messages.RegisterMessage;
import ibk.challenge.antifraud.messages.UpdateStatusMessage;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {
    static Logger LOGGER = LoggerFactory.getLogger(KafkaConsumerService.class);
    static Gson GSON = new Gson();

    @Autowired
    private KafkaPublisherService kafkaPublisherService;

    @KafkaListener(topics = "antifraud-register", groupId = "antifraud-group")
    public Integer listen(ConsumerRecord message)
    {
        RegisterMessage registerMessage = GSON.fromJson(message.value().toString(),RegisterMessage.class);
        UpdateStatusMessage updateMessage = new UpdateStatusMessage();
        updateMessage.setTransactionId(registerMessage.getTransactionId());
        if (registerMessage.getValue() > 1000)
        {
            LOGGER.info("Transaction [{}] should be denied",registerMessage.getTransactionId());
            updateMessage.setTransactionalStatus(StatusDefinition.DENIED_STATUS);
        }
        else
        {
            LOGGER.info("Transaction [{}] should be approved",registerMessage.getTransactionId());
            updateMessage.setTransactionalStatus(StatusDefinition.APPROVED_STATUS);
        }
        kafkaPublisherService.sendValueChangeStatusEvent("antifraud-update-status",updateMessage);
        return updateMessage.getTransactionalStatus();
    }

}
