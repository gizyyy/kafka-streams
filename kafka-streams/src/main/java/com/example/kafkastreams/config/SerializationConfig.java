package com.example.kafkastreams.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;
import org.springframework.kafka.support.serializer.JsonSerde;

import com.example.kafkastreams.model.Student;

@Configuration
public class SerializationConfig {

	@Bean
	public JsonSerde<Student> entityJsonSerde(com.fasterxml.jackson.databind.ObjectMapper objectMapper) {
		JsonSerde<Student> serde = new JsonSerde<>(Student.class, objectMapper);
		serde.deserializer().addTrustedPackages("*");
		return serde;
	}

	@Bean
	public StringJsonMessageConverter jsonConverter() {
		return new StringJsonMessageConverter();
	}
}
