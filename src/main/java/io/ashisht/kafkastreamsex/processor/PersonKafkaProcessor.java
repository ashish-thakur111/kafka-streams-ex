package io.ashisht.kafkastreamsex.processor;

import io.ashisht.kafkastreamsex.domain.Person;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

/**
 * @author ashish.thakur on 19-11-2021
 */

@Configuration
public class PersonKafkaProcessor {
    @Bean
    public Function<KStream<String, Person>, KStream<String, Person>> personProcessor() {
        return kStream -> kStream.filter((key, value) -> value.getMaritalStatus().equals("Married"));
    }
}
