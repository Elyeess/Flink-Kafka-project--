# Flink-Kafka Project

Ce projet illustre la configuration d'un pipeline entre Apache Flink et Kafka pour le traitement en temps réel de flux de données, notamment des données de prix de cryptomonnaies obtenues depuis l'API CoinCap (https://api.coincap.io) et publiées sur le topic Kafka "crypto_prices".

---

## Configuration du Projet

### 1. Créer un Projet Maven dans VSCode

1. **Ouvrez VSCode.**
2. **Tapez dans la palette de commande :**
   ```
   Maven: Create Maven Project
   ```
3. **Choisissez un archetype :**
   - Sélectionnez `maven-archetype-quickstart`.
4. **Configurez le projet :**
   - Entrez le `groupId` (par exemple, `org.example`).
   - Entrez le `artifactId` (par exemple, `flink-job`).
5. Le répertoire du projet sera créé avec ce nom.

   ![vsss](https://github.com/user-attachments/assets/bd3a402c-f68b-450b-bcfc-cf3a9c55393d)

---

### 2. Ajouter les Dépendances Flink

1. **Ouvrez le fichier `pom.xml`.**
2. **Ajoutez les dépendances nécessaires à votre projet Flink.**
   - Assurez-vous d'utiliser la version de Flink compatible (par exemple, `1.20.0`).

---

## Configuration de Flink

### Étapes pour Configurer Flink

1. **Accédez à WSL :**
   ```bash
   wsl -d Ubuntu
   ```
2. **Passez en mode root :**
   ```bash
   su -
   ```
3. **Naviguez vers le répertoire Flink :**
   ```bash
   cd /home/elyes/flink/flink-1.20.0
   ```
4. **Démarrez le cluster Flink :**
   ```bash
   ./bin/stop-cluster.sh  # Optionnel, pour arrêter un cluster existant
   ./bin/start-cluster.sh
   ```
5. **Accédez au tableau de bord :**
   - Ouvrez votre navigateur et accédez à :
     ```
     http://localhost:8081
     ```
![flnik host](https://github.com/user-attachments/assets/65a1954a-4b44-450d-8223-3bcdfca19ba3)

---

## Configuration de Kafka

### Étapes pour Configurer Kafka

1. **Démarrez ZooKeeper dans un terminal :**
   ```cmd
   C:\kafka\bin\windows\zookeeper-server-start.bat C:\kafka\config\zookeeper.properties
   ```
2. **Démarrez Kafka dans un autre terminal :**
   ```cmd
   C:\kafka\bin\windows\kafka-server-start.bat C:\kafka\config\server.properties
   ```
3. **Créez un topic Kafka :**
   ```cmd
   C:\kafka\bin\windows\kafka-topics.bat --create --topic crypto_prices --bootstrap-server localhost:9092
   ```

---

## Compilation et Exécution du Projet

### Étapes pour Compiler et Exécuter le Projet

1. **Nettoyez et compilez le projet :**
   ```bash
   mvn clean install
   ```
2. **Exécutez la classe principale :**
   ```bash
   mvn exec:java
   ```

### Consommation des Messages

1. **Observez les logs pour vérifier que les messages sont envoyés au topic Kafka :**
   ```cmd
   C:\kafka\bin\windows\kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic crypto_prices --from-beginning
   ```

   ![consumer kafka](https://github.com/user-attachments/assets/217bf9ef-1f57-42ae-b991-77af53a8a22a)


---

## Exécution avec un Nouveau `pom.xml`

1. **Recompilez le projet :**
   ```bash
   mvn clean package
   ```
2. **Exécutez le Producteur Kafka :**
   ```bash
   mvn exec:java "-Dexec.mainClass=org.example.CryptoDataProducer"
   ```
   ![consumer](https://github.com/user-attachments/assets/0e34cda5-fe74-43e8-bc3e-cf67dac4f792)
   

4. **Exécutez le Job Flink :**
   ```bash
   ./bin/flink run -c org.example.Main target/cryptoproducer-1.0-SNAPSHOT.jar

   ### Analyse des Cryptomonnaies
```bash
### Analyse des Cryptomonnaies

#### Analyse pour la monnaie : Chainlink
- Prix moyen : 30.26 USD
- Volume moyen : 546,804,478.36 USD
- Volume total : 5,468,044,783.60 USD
- Nombre d'enregistrements : 10

----------------------------------

#### Analyse pour la monnaie : TRON
- Prix moyen : 0.30 USD
- Volume moyen : 249,120,518.11 USD
- Volume total : 1,743,843,626.77 USD
- Nombre d'enregistrements : 7

-----------------------------------

#### Analyse pour la monnaie : Akash Network
- Prix moyen : 3.75 USD
- Volume moyen : 1,855,592.27 USD
- Volume total : 22,267,107.24 USD
- Nombre d'enregistrements : 12

-----------------------------------

#### Analyse pour la monnaie : Zilliqa
- Prix moyen : 0.02 USD
- Volume moyen : 15,090,689.52 USD
- Volume total : 105,634,826.64 USD
- Nombre d'enregistrements : 7

-----------------------------------

#### Analyse pour la monnaie : Autre Monnaie
- Prix moyen : 2.39 USD
- Volume moyen : 1,658,640,673.85 USD
- Volume total : 18,245,047,412.35 USD
- Nombre d'enregistrements : 11

   
