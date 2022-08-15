package com.example.kafkastreams.processor;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerde;

import com.example.kafkastreams.model.Student;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class HighSchoolRegistirationProcessor {

	private final NewTopic registerTopic;
	private final NewTopic highSchoolTopic;
	private final JsonSerde<Student> entityJsonSerde;

	@Bean
	public KStream<String, Student> processHighSchoolStudents(StreamsBuilder kStreamBuilder) {

		KStream<String, Student> inputStream = kStreamBuilder.stream(registerTopic.name(),
				Consumed.with(Serdes.String(), entityJsonSerde));

		KStream<String, Student> highSchoolStream = inputStream.filter((key, value) -> value.getAge() > 14)
				.peek((key, value) -> System.out.println("key:" + key + " value:" + value));

		highSchoolStream.to(highSchoolTopic.name());
		return highSchoolStream;
	}

}