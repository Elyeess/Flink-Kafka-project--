# Flink-Kafka Project

Ce projet montre comment configurer un pipeline entre Apache Flink et Kafka pour traiter des flux de données en temps réel.

---

## Configuration du Projet

### 1. Créer un Projet Maven dans VSCode
1. Ouvrez VSCode.
2. Tapez dans la palette de commande : `Maven: Create Maven Project`.
3. Choisissez un *archetype* :
   - Sélectionnez `maven-archetype-quickstart`.
4. Configurez le projet :
   - Entrez le `groupId` (par exemple, `org.example`).
   - Entrez le `artifactId` (par exemple, `flink-job`).
5. Le répertoire du projet sera créé avec ce nom.
![vsss](https://github.com/user-attachments/assets/bd3a402c-f68b-450b-bcfc-cf3a9c55393d)

---

### 2. Ajouter les Dépendances Flink
1. Ouvrez le fichier `pom.xml`.
2. Dans la liste des versions, cliquez sur `1.4` pour ajouter les dépendances nécessaires.

---

## Configuration de Flink

### Étapes pour Configurer Flink
# Accédez à WSL :
wsl -d Ubuntu

# Passez en mode root :
su -

# Naviguez vers le répertoire Flink :
cd /home/elyes/flink/flink-1.20.0

# Démarrez le cluster Flink :
./bin/stop-cluster.sh  # Optionnel, pour arrêter un cluster existant
./bin/start-cluster.sh

# Accédez au tableau de bord :
# Ouvrez votre navigateur et accédez à http://localhost:8081.

-------------------------------

## Configuration de Kafka

## Étapes pour Configurer Kafka
# Démarrez ZooKeeper dans un terminal :
C:\kafka\bin\windows\zookeeper-server-start.bat C:\kafka\config\zookeeper.properties

# Démarrez Kafka dans un autre terminal :
C:\kafka\bin\windows\kafka-server-start.bat C:\kafka\config\server.properties

# Créez un topic Kafka :
C:\kafka\bin\windows\kafka-topics.bat --create --topic crypto_prices --bootstrap-server localhost:9092

---

## Compilation et Exécution du Projet

## Étapes pour Compiler et Exécuter le Projet
# Nettoyez et compilez le projet :
mvn clean install

# Exécutez la classe principale :
mvn exec:java

## Consommation des Messages
# Observez les logs pour vérifier que les messages sont envoyés au topic Kafka :
C:\kafka\bin\windows\kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic crypto_prices --from-beginning

## Exécution avec un Nouveau pom.xml
```bash
# Recompilez le projet :
mvn clean package

# Exécutez le Producteur Kafka :
mvn exec:java "-Dexec.mainClass=org.example.CryptoDataProducer"

# Exécutez le Job Flink :
./bin/flink run -c org.example.Main target/cryptoproducer-1.0-SNAPSHOT.jar
