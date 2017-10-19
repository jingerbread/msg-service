# Start kafka with zookeper 
# refer to [kafka quickstart](https://kafka.apache.org/quickstart)

# Create custom topic "msg_output":
```bash
bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic msg_output
```
# Run listener for this topic:
```bash
bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic msg_output --from-beginning
```
# Run server test MessageSenderTest
# In console tab for kafka listener new message should appear
Test message

# Start message server
```bash
cd server
mvn clean install -DskipTests=true
mvn spring-boot:run -Dserver.port=8081 -Dinstance.conf=file:conf/server.default.properties -Dlog4j.configuration=file:conf/log4j.properties
```
# Create postgres db 'messages'
```
spring.datasource.url=jdbc:postgresql://localhost:5432/messages
spring.datasource.username=postgres
spring.datasource.password=postgres
```
# Start message client
```bash
cd client
mvn clean install -DskipTests=true
mvn spring-boot:run -Dinstance.conf=file:src/main/resources/application.properties -Dlog4j.configuration=file:conf/log4j.properties
```

# Send some messages
```bash
curl -H "Content-Type: application/json" -X POST -d '[{"title":"Hello","text":"Hello World!","author":"User","created":"2017-10-17"}]' http://localhost:8081/api/v1/messages
```
# response
```
 {"status":"SUCCESS","statusCode":0,"successful":true}
```

