package com.sample.config.mqtt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@Configuration
public class MqttInboundConfig {

    private final MqttPahoClientFactory mqttClientFactory;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @Value("${app.mqtt.topic}")
    private String topic;

    @Value("${app.websocket.topic}")
    private String webSocketTopic;

    public MqttInboundConfig(MqttPahoClientFactory mqttClientFactory, SimpMessagingTemplate simpMessagingTemplate) {
        this.mqttClientFactory = mqttClientFactory;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @Bean
    public MessageChannel mqttInputChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageProducer inbound() {
        MqttPahoMessageDrivenChannelAdapter adapter = new MqttPahoMessageDrivenChannelAdapter(
            "mqttClientIn",
            mqttClientFactory,
            topic
        );
        adapter.setCompletionTimeout(5000);
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(1);
        adapter.setOutputChannel(mqttInputChannel());
        return adapter;
    }

    @Bean
    @ServiceActivator(inputChannel = "mqttInputChannel")
    public MessageHandler handler() {
        return message -> simpMessagingTemplate.convertAndSend(webSocketTopic, message);
    }
}
