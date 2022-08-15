package com.example.kafkastreams.producer;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.kafkastreams.model.Student;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class StudentPublisher {

	private final KafkaTemplate<String, Student> kafkaTemplate;
	private final NewTopic registerTopic;
	private final AtomicInteger counter = new AtomicInteger();

	@Scheduled(fixedRate = 20000)
	public void produce() {
		Random random = new Random();
		int randomAge = random.nextInt(7, 17);
		kafkaTemplate.send(registerTopic.name(), "registiration",
				new Student(randomAge, "Hans Muller" + counter.getAndIncrement()));
	}
}