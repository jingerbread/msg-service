# TODO: how to start
mvn spring-boot:run

# Start kafka with zookeper refer to https://kafka.apache.org/quickstart
# Create custom topic:
bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic msg_output
# Run listener for this topic:
bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic msg_output --from-beginning
# Run MessageSenderTest
# In console tab for listener there should be a new message
Test message
