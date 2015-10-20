## Overview

This is the simplest possible Storm topology that integrates with Kafka.  It consists of a single spout to read from Kafka and a single bolt that echos the message that is read.  The output ends up in the Storm worker log file.

A simple producer based on [the Kafka examples](TODO) is included.

## Docker

This example is configured to run in Docker on OS X with an ```/etc/hosts``` entry for ```docker-machine``` (see [this blog post](TODO) for more details on configuring this environment).  If it is running on a different configuration, the files ```bin/deploy-topology.sh``` and ```docker-config.yml``` must be modified to specify the correct Nimbus host and Kafka hosts, respectively.

## Build

The example depends on SLF4J, Zookeeper, Kafka, and Storm.  The directories for those packages are specified in ```build.properties```.  These must be set correctly for the build to succeed.

To build the jar files the first time, run ```ant build```.  To clean and build, run ```ant rebuild```.  This will create jar files in the ```lib``` directory.

## Run

If Docker is being used to run the example, the first step is to start the Docker containers running Zookeeper, Kafka, and Storm.  When Kafka and Storm are available, deploy the topology then feed messages to Kafka:

1. ```docker-compose up &``` (if using Docker)
2. ```bin/deploy-topology.sh```
3. ```bin/feed-kafka.sh```

By default the feed script sends a thousand messages.  They will be found in the Storm worker log file.
