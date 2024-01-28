package com.nivorbit.customer.service.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nivorbit.customer.service.mutitenancy.context.TenantContext;
import com.nivorbit.customer.service.user.User;
import com.nivorbit.customer.service.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomerListener {

    private final ObjectMapper objectMapper;
    private final UserRepository userRepository;

    @KafkaListener(topics = "customers", groupId = "customer-ms", batch = "true")
    public void receive(ConsumerRecords<String, String> records) throws JsonProcessingException {
        TenantContext.setTenantId("target");
        try{
            var users = new ArrayList<User>();
            for (ConsumerRecord<String, String> cr : records) {
                var message = cr.value();
                var event = objectMapper.readValue(message, JsonNode.class);
                var customer = objectMapper.treeToValue(event.get("after"), User.class);
                users.add(customer);
            }
            userRepository.saveAll(users);
        } finally{
            TenantContext.clear();
        }
    }
}
