package io.ashisht.kafkastreamsex.producer;

import com.github.javafaker.Faker;
import io.ashisht.kafkastreamsex.domain.Address;
import io.ashisht.kafkastreamsex.domain.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.stream.Stream;

/**
 * @author ashish.thakur on 19-11-2021
 */
@Component
@RequiredArgsConstructor
public class PersonProducer {
    private final KafkaTemplate<String, Person> kafkaTemplate;

    private Faker faker;

    @Value("${kafka.topic.person}")
    private String topic;

    @EventListener(ApplicationStartedEvent.class)
    public void generate() {
        faker = Faker.instance();
        Flux.fromStream(Stream.generate(() -> Person.builder()
                        .name(faker.name().name())
                        .age((int) (Math.random() * 100))
                        .maritalStatus(faker.demographic().maritalStatus())
                        .address(Address.builder()
                                .city(faker.address().cityName())
                                .state(faker.address().state())
                                .street(faker.address().streetAddress())
                                .zip(faker.address().zipCode()).build())
                        .build()))
                .delayElements(Duration.ofSeconds(1))
                .subscribe(person -> kafkaTemplate.send(topic, person.getName(), person));
    }
}
