package ibk.challenge.antifraud.service;

import com.google.gson.Gson;
import ibk.challenge.antifraud.definition.StatusDefinition;
import ibk.challenge.antifraud.messages.RegisterMessage;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

public class KafkaConsumerServiceTest {
    @Mock
    KafkaPublisherService kafkaPublisherService;
    @InjectMocks
    KafkaConsumerService kafkaConsumerService;

    String topic = "test-topic";
    int partition = 0;
    long offset = 100;
    String key = "key";

    String transactionId;
    RegisterMessage registerMessageApproved;

    RegisterMessage registerMessageDenied;
    static Gson GSON = new Gson();

    @BeforeEach
    void setUp()
    {
        transactionId = UUID.randomUUID().toString();
        registerMessageApproved = RegisterMessage.builder()
                .transactionId(transactionId)
                .value(500l)
                .build();

        registerMessageDenied = RegisterMessage.builder()
                .transactionId(transactionId)
                .value(1500l)
                .build();


        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testApprovedTransaction()
    {
        ConsumerRecord<String, Object>  record=  new ConsumerRecord<>(topic, partition, offset, key, GSON.toJson(registerMessageApproved));
        Integer result = kafkaConsumerService.listen(record);
        Assertions.assertEquals(result, StatusDefinition.APPROVED_STATUS);
    }

    @Test
    void testDeniedTransaction()
    {
        ConsumerRecord<String, Object>  record=  new ConsumerRecord<>(topic, partition, offset, key, GSON.toJson(registerMessageDenied));
        Integer result = kafkaConsumerService.listen(record);
        Assertions.assertEquals(result, StatusDefinition.DENIED_STATUS);
    }

}
