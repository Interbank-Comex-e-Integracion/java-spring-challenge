package com.interbank.test.controller;

import com.interbank.test.service.KafkaMessageService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kafka-messages")
public class KafkaMessageController {

        @Autowired
        private KafkaMessageService kafkaMessageService;

        @GetMapping
        public List<String> getMessages() {
                return kafkaMessageService.getMessages();
        }
}
