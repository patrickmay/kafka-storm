This is a simple Storm topology that reads from Kafka and echos the
messages.

## Test Infrastructure
```
docker-machine start default
docker-compose up
docker-compose ps
```

## Kafka
List topics:
```
~/packages/kafka_2.11-0.8.2.1/bin/kafka-topics.sh --zookeeper docker-machine --list
```
Create topic:
```
~/packages/kafka_2.11-0.8.2.1/bin/kafka-topics.sh --create --zookeeper docker-machine --replication-factor 2 --partitions 3 --topic test.echo
```
Describe topic:
```
~/packages/kafka_2.11-0.8.2.1/bin/kafka-topics.sh --zookeeper docker-machine --describe --topic test.echo
```

## Build
```
ant build
```
```
ant rebuild
```
