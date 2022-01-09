package com.nitesh.springbootapachekafkaconsumer.config;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.config.SslConfigs;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.HashMap;
import java.util.Map;


@Configuration
@EnableKafka
public class KafkaConsumerConfig {

    @Value("${kafka.bootstrap-server}")
    private String bootstrapServer;

    @Value("${kafka.consumer.ssl.keystore-location}")
    private String keystoreLocation;

    @Value("${kafka.consumer.ssl.keystore-password}")
    private String keystorePassword;

    @Value("${kafka.consumer.ssl.keypassword}")
    private String keyPassword;

    @Value("${kafka.consumer.ssl.truststore-location}")
    private String truststoreLocation;

    @Value("${kafka.consumer.ssl.truststore-password}")
    private String truststorePassword;

    @Value("${kafka.consumer.ssl.group-id}")
    private String groupId;

    public ConsumerFactory<String, String> consumerFactory() {
        Map<String, String> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        config.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, String.valueOf(StringDeserializer.class));
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, String.valueOf(StringDeserializer.class));
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        config.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, "5000000");
        config.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "100000");
        config.put(SslConfigs.SSL_KEYSTORE_LOCATION_CONFIG, keystoreLocation);
        config.put(SslConfigs.SSL_KEYSTORE_PASSWORD_CONFIG, keystorePassword);
        config.put(SslConfigs.SSL_KEY_PASSWORD_CONFIG, keyPassword);
        config.put(SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG, truststoreLocation);
        config.put(SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG, truststorePassword);
        config.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "SSL");
        return new DefaultKafkaConsumerFactory(config);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}
