# Flink-Kafka Project

Ce projet montre comment configurer un pipeline entre Apache Flink et Kafka pour traiter des flux de données en temps réel.

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
3. **Exécutez le Job Flink :**
   ```bash
   ./bin/flink run -c org.example.Main target/cryptoproducer-1.0-SNAPSHOT.jar
   
