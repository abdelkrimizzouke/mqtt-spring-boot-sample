#### 🎯 Overview

![image](https://github.com/abdelkrimizzouke/mqtt-spring-boot-sample/assets/170672297/043979d8-ae24-4550-8197-cca772553cdf)

#### 📝 Prerequisites
```
✣ Java 17 ☕
✣ Spring Boot 3.3.0 🌱
✣ Mosquitto message broker installed and running 📡
✣ Reactjs ⚛️
```

#### ⚙️ How It Works

The following steps outline how the project works to send and receive messages using MQTT with Mosquitto as the broker in a Java 17 Spring Boot 3 environment, and how those messages are displayed in real-time using WebSockets and an ***embeded*** ReactJS frontend :


###### 1. Setup and Initialization 🛠️
```
▫️ Java 17 and Spring Boot 3 are used to create the backend of the application.

▫️ Mosquitto serves as the MQTT broker, handling the messaging infrastructure.

▫️ ReactJS is used for the frontend to interact with the backend via WebSocket and REST endpoints.
```
###### 2. Configuration of MQTT Broker 📡
```
▫️ Mosquitto is installed and running on a specified host (e.g., localhost:1883).

▫️ The broker listens for incoming MQTT messages and forwards them to the appropriate topics.
```

###### 3. Spring Boot MQTT Configuration 🌱
```
▫️ A configuration class in Spring Boot sets up the connection to the Mosquitto broker using the MqttPahoClientFactory.

▫️ Message channels (DirectChannel) are configured for inbound and outbound messages.

▫️ An inbound adapter (MqttPahoMessageDrivenChannelAdapter) listens for messages on specific topics.

▫️ An outbound handler (MqttPahoMessageHandler) sends messages to the broker.
```
###### 4. Spring Boot WebSocket Configuration 📡
```
▫️ A WebSocket configuration class sets up WebSocket endpoints and message brokers using STOMP.

▫️ Messages received from the MQTT broker are sent to WebSocket clients subscribed to the /ws-mqtt-topic endpoint.
```
###### 5. Publishing Messages to MQTT Broker ✉️
```
▫️ A REST controller in Spring Boot provides an endpoint (/send) to send messages to the MQTT broker.

▫️ When a POST request is made to this endpoint from the ReactJS frontend, the message is sent to the configured MQTT topic via the outbound message channel.
```
###### 6. Receiving Messages from MQTT Broker 📬
```
▫️ The inbound adapter listens for messages on specified topics.

▫️ When a message is received, it is processed by a message handler which logs the message and forwards it to WebSocket clients.
```
###### 7. Frontend Interaction ⚛️
```
▫️ A ReactJS frontend connects to the WebSocket endpoint (/ws) using sockjs-client and stompjs.

▫️ The frontend subscribes to the /topic/messages topic to receive real-time updates of MQTT messages.

▫️ Messages received via WebSocket are displayed in a list on the frontend.
```
#### Contributing

Pull requests are welcome. For major changes, please open an issue first
to discuss what you would like to change.

Please make sure to update tests as appropriate.

#### License

[MIT](https://choosealicense.com/licenses/mit/)
