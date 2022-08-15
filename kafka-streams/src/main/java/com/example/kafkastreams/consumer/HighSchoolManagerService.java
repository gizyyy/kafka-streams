package com.example.kafkastreams.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import com.example.kafkastreams.model.Student;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class HighSchoolManagerService {

	@KafkaListener(topics = "high-school-topic", groupId = "group-high", concurrency = "2")
	public void listen(@Payload Student kafkaMessage, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
		System.out.println(String.format("Student is registered for High School  %s  %s - %s", topic,
				kafkaMessage.getAge(), kafkaMessage.getName()));
	}

}