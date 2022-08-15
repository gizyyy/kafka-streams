package com.example.kafkastreams.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.streams.StreamsConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;
import org.springframework.kafka.config.KafkaStreamsConfiguration;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerde;

import com.example.kafkastreams.model.Student;

import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
public class KafkaBaseConfig {

	private final KafkaAdmin admin;
	private final JsonSerde<Student> entityJsonSerde;

	@Bean
	public ProducerFactory<String, Student> producerFactory() {
		HashMap<String, Object> configMap = new HashMap<>();
		configMap.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
				admin.getConfigurationProperties().get(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG));
		configMap.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		configMap.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, entityJsonSerde.serializer().getClass());
		return new DefaultKafkaProducerFactory<String, Student>(configMap);
	}

	@Bean
	public ConsumerFactory<String, Student> consumerFactory() {
		Map<String, Object> configMap = new HashMap<String, Object>();
		configMap.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
				admin.getConfigurationProperties().get(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG));
		configMap.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
		configMap.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
		configMap.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		configMap.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, entityJsonSerde.deserializer().getClass());
		configMap.put(JsonDeserializer.TRUSTED_PACKAGES, "com.example.kafkastreams.model");
		return new DefaultKafkaConsumerFactory<String, Student>(configMap);
	}

	@Bean(name = KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME)
	public KafkaStreamsConfiguration kStreamsConfigs() {
		Map<String, Object> configMap = new HashMap<String, Object>();
		configMap.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG,
				admin.getConfigurationProperties().get(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG));
		configMap.put(StreamsConfig.APPLICATION_ID_CONFIG, "app");
		configMap.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
		configMap.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, entityJsonSerde.getClass());
		return new KafkaStreamsConfiguration(configMap);
	}

}
