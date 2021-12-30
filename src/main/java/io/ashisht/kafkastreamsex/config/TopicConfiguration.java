package io.ashisht.kafkastreamsex.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

/**
 * @author ashish.thakur on 19-11-2021
 */
@Configuration
public class TopicConfiguration {

    @Bean
    NewTopic person() {
        return TopicBuilder.name("person").partitions(3).replicas(1).build();
    }

    @Bean
    NewTopic marriedPerson() {
        return TopicBuilder.name("married-person").partitions(3).replicas(1).build();
    }
}
