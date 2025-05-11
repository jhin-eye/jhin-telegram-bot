package com.yanoos.global.config;

import com.yanoos.member.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
@RequiredArgsConstructor
public class KafkaConsumerConfig {
    private static final Logger log = LoggerFactory.getLogger(KafkaConsumerConfig.class);
    @Value("${spring.kafka.bootstrap-servers}")
    private String BOOTSTRAP_SERVERS;

    @Value("${spring.kafka.consumer.group-id}")
    private String CONSUMER_GROUP_ID;

    private final MemberRepository memberRepository;

    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS); // 카프카 서버 주소
        props.put(ConsumerConfig.GROUP_ID_CONFIG, CONSUMER_GROUP_ID); // 그룹 ID 설정
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName()); // 키 역직렬화 설정
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName()); // 값 역직렬화 설정
        return new DefaultKafkaConsumerFactory<>(props); // 컨슈머 팩토리 생성
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory()); // 컨슈머 팩토리 설정
        return factory; // 리스너 컨테이너 팩토리 생성
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> suggestionKafkaListenerContainerFactory() {
        int adminCount = memberRepository.countByIsAdmin(true);
        int concurrency = Math.max(adminCount, 1); // 최소 1 보장
        log.info("Admin count: {}", adminCount);

        ConcurrentKafkaListenerContainerFactory<String, String> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setConcurrency(concurrency);
        return factory;
    }

}
