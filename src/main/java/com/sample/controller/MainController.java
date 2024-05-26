package com.sample.controller;

import com.sample.config.mqtt.MqttGateway;
import com.sample.records.Message;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    private final MqttGateway mqttGateway;

    public MainController(MqttGateway mqttGateway) {
        this.mqttGateway = mqttGateway;
    }

    @PostMapping("/publish-message")
    public ResponseEntity<?> sendMessageToMqtt(@RequestBody Message message) {
        try {
            mqttGateway.sendToMqtt(message.content(), message.topic());
            return ResponseEntity.ok("Message sent successfully");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Message not sent. Check Mosquitto is running!");
        }
    }

}
