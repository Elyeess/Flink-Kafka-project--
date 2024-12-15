package org.example;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

public class CryptoDataProducer {
    public static void main(String[] args) throws Exception {
        Properties kafkaProps = new Properties();
        kafkaProps.put("bootstrap.servers", "localhost:9092");
        kafkaProps.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        kafkaProps.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        KafkaProducer<String, String> producer = new KafkaProducer<>(kafkaProps);

        String apiUrl = "https://api.coincap.io/v2/assets";

        while (true) {
            String response = fetchCryptoData(apiUrl);
            JSONArray cryptoArray = parseCryptoData(response);

            for (int i = 0; i < cryptoArray.length(); i++) {
                JSONObject crypto = cryptoArray.getJSONObject(i);
                ProducerRecord<String, String> record = new ProducerRecord<>("crypto-prices", crypto.toString());
                producer.send(record);
                System.out.println("Sent: " + crypto);
            }

            Thread.sleep(5000); // Attendez 5 secondes avant la prochaine requête
        }
    }

    private static String fetchCryptoData(String urlString) throws Exception {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder content = new StringBuilder();
        String line;

        while ((line = in.readLine()) != null) {
            content.append(line);
        }

        in.close();
        conn.disconnect();

        return content.toString();
    }

    private static JSONArray parseCryptoData(String jsonResponse) {
        JSONObject json = new JSONObject(jsonResponse);
        return json.getJSONArray("data");
    }
}
