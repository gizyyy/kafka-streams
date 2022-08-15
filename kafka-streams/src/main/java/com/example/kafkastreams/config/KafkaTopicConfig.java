package com.example.kafkastreams.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

	@Value("general-student-topic")
	private String registerTopic;

	@Value("middle-school-topic")
	private String middleSchoolTopic;

	@Value("high-school-topic")
	private String highScoolTopic;

	@Bean
	public NewTopic registerTopic() {
		return TopicBuilder.name(registerTopic).partitions(Integer.valueOf(1)).replicas(1).build();
	}

	@Bean
	public NewTopic middleSchoolTopic() {
		return TopicBuilder.name(middleSchoolTopic).partitions(Integer.valueOf(1)).replicas(1).build();
	}

	@Bean
	public NewTopic highSchoolTopic() {
		return TopicBuilder.name(highScoolTopic).partitions(Integer.valueOf(1)).replicas(1).build();
	}
}