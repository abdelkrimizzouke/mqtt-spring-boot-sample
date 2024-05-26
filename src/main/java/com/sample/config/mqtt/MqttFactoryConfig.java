package com.sample.config.mqtt;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;

@Configuration
public class MqttFactoryConfig {

    @Value("${app.mqtt.uri}")
    private String uri;

    @Bean
    public MqttPahoClientFactory mqttClientFactory() {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        MqttConnectOptions options = new MqttConnectOptions();
        options.setServerURIs(new String[]{uri});
        /*
            In case of a secured mosquitto you should set credentials
            options.setUserName("xyz");
            options.setPassword("xyz".toCharArray());
         */
        factory.setConnectionOptions(options);
        options.setAutomaticReconnect(true);
        options.setCleanSession(true);
        return factory;
    }

}
