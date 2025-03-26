package com.challenge.demo.config;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${kafka.topic.transaction-created}")
    private String transactionCreatedTopic;

    @Value("${kafka.topic.transaction-status-updated}")
    private String transactionStatusUpdatedTopic;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic transactionCreatedTopic() {
        return new NewTopic(transactionCreatedTopic, 1, (short) 1);
    }

    @Bean
    public NewTopic transactionStatusUpdatedTopic() {
        return new NewTopic(transactionStatusUpdatedTopic, 1, (short) 1);
    }
}
