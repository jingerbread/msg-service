# Start kafka with zookeper refer to https://kafka.apache.org/quickstart
# Create custom topic:
bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic msg_output
# Run listener for this topic:
bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic msg_output --from-beginning
# Run MessageSenderTest
# In console tab for listener there should be a new message
Test message

# Start message server
cd server
mvn clean install -DskipTests=true
mvn spring-boot:run -Dserver.port=8081 -Dinstance.conf=file:conf/msg-service.default.properties -Dlog4j.configuration=file:conf/log4j.properties

# Send some messages
curl -H "Content-Type: application/json" -X POST -d '[{"title":"Hello","text":"Hello World!","author":"User","created":"2017-10-17"}]' http://localhost:8081/api/v1/messages
# response
# {"status":"SUCCESS","statusCode":0,"successful":true}
