package com.example.kafkastreams.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import com.example.kafkastreams.model.Student;

import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
public class KafkaTemplateConfig {

	private final ProducerFactory<String, Student> producerFactory;

	@Bean
	public KafkaTemplate<String, Student> kafkaTemplate() {
		return new KafkaTemplate<String, Student>(producerFactory);
	}

}
