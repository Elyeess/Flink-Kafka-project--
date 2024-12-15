package org.example;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
import java.util.Properties;

public class CryptoDataProducer {
    private static final String KAFKA_TOPIC = "crypto_prices";
    private static final String API_URL = "https://api.coincap.io/v2/assets";

    public static void main(String[] args) {
        // Configuration du producteur Kafka
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        try (KafkaProducer<String, String> producer = new KafkaProducer<>(props)) {
            HttpClient client = HttpClient.newHttpClient();

            while (true) {
                try {
                    HttpRequest request = HttpRequest.newBuilder()
                            .uri(URI.create(API_URL))
                            .GET()
                            .build();

                    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                    // Parser la réponse JSON
                    ObjectMapper mapper = new ObjectMapper();
                    JsonNode assets = mapper.readTree(response.body()).get("data");

                    for (JsonNode asset : assets) {
                        String name = asset.get("name").asText();
                        String price = asset.get("priceUsd").asText();
                        String volume = asset.get("volumeUsd24Hr").asText();
                        long timestamp = System.currentTimeMillis();

                        String jsonMessage = String.format("{\"name\":\"%s\", \"price\":%s, \"volume\":%s, \"timestamp\":%d}",
                                name, price, volume, timestamp);

                        producer.send(new ProducerRecord<>(KAFKA_TOPIC, name, jsonMessage));
                        System.out.println("Données publiées : " + jsonMessage);
                    }

                    Thread.sleep(10000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
