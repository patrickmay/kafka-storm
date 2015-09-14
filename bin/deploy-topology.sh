#!/bin/bash

#java -jar `dirname $0`/../lib/topology.jar

${HOME}/packages/apache-storm-0.9.5/bin/storm jar \
    `dirname $0`/../lib/topology.jar \
    org.softwarematters.storm.echo.EchoTopology \
    -c nimbus.host=docker-machine \
    -c nimbus.thrift.port=6627
