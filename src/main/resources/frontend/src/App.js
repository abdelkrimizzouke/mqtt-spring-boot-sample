import {useState} from "react";
import {Client} from '@stomp/stompjs';
import './App.css';

const App = () => {

    const [message, setMessage] = useState("");
    const [messages, setMessages] = useState([]);
    const [stompClient, setStompClient] = useState(null);
    const [connected, setConnected] = useState(false);
    const [config, setConfig] = useState({
        endpoint: '/publish-message',
        mqttTopic: 'myTopic',
        webSocketEndpoint: `ws://${window.location.host}/ws-mqtt`,
        webSocketTopic: '/ws-mqtt-topic'
    })

    const sendMessageToMqtt = (e) => {

        e.preventDefault();

        if(!message) return;

        const data = {
            content: message,
            topic: config.mqttTopic
        };

        fetch(config.endpoint, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(data)
        }).then(() => setMessage("")
        ).catch(r => console.error("Ok"))

    }

    const connectWebSocket = (e) => {
        e.preventDefault();
        const stompClient = new Client({
            brokerURL: config.webSocketEndpoint,
            onConnect: () => {
                console.log('Connected');
                setConnected(true);
                stompClient.subscribe(config.webSocketTopic, (msg) => {
                    setMessages(prev => [...prev, JSON.parse(msg.body)['payload']]);
                });
            },
            onDisconnect: () => {
                console.log('Disconnected');
                setConnected(false);
            },
            onStompError: (error) => {
                console.error('Error', error);
            },
        });
        stompClient.activate();
        setStompClient(stompClient);
    }

    const disconnectWebSocket = (e) => {
        e.preventDefault();
        stompClient.deactivate();
    }

    const onConfigChange = (e) => {
        setConfig(prev => ({...prev, [e.target.name]: e.target.value}))
    }

    return (
        <div className="app">
            <q className="title">Publishing and Receiving Messages with MQTT Using Mosquitto as broker</q>
            <div className="container">
                <form>
                    <input type="text" value={message} onChange={(e) => setMessage(e.target.value)}/>
                    <button onClick={sendMessageToMqtt}>Publish Message</button>
                </form>
            </div>
            <div className="container">
                <div className="left-side">
                    <p>Received Messages :</p>
                    <hr/>
                    <ul>
                        {messages.map((m, i) => <li key={i}>{m}</li>)}
                    </ul>
                </div>
                <div className="right-side">
                    <p style={{color: '#FF5B33'}}>These values should only be adjusted if you have modified the backend
                        configuration.</p>
                    <form>
                        <div className="field">
                            <label style={{fontSize: '11px'}}>RESTful Endpoint : </label>
                            <input className="field-text" type="text" value={config.endpoint} name="endpoint" onChange={onConfigChange}/>
                        </div>
                        <div className="field">
                            <label style={{fontSize: '11px'}}>MQTT Topic : </label>
                            <input className="field-text" type="text" value={config.mqttTopic} name="mqttTopic" onChange={onConfigChange}/>
                        </div>
                        <div className="field">
                            <label style={{fontSize: '11px'}}>WebSocket Endpoint : </label>
                            <input className="field-text" type="text" value={config.webSocketEndpoint} name="webSocketEndpoint" onChange={onConfigChange}/>
                        </div>
                        <div className="field">
                            <label style={{fontSize: '11px'}}>WebSocket Topic : </label>
                            <input className="field-text" type="text" value={config.webSocketTopic} name="webSocketTopic" onChange={onConfigChange}/>
                        </div>
                        <div style={{display: 'flex', justifyContent: 'center'}}>
                            {
                                connected ?
                                    <button style={{backgroundColor: '#FF9333', color: '#fff'}}
                                            onClick={disconnectWebSocket}>Disconnect WebSocket</button> :
                                    <button style={{backgroundColor: '#338AFF', color: '#fff'}}
                                            onClick={connectWebSocket}>Connect WebSocket</button>
                            }
                        </div>
                    </form>
                </div>
            </div>
        </div>
    );
}

export default App;
