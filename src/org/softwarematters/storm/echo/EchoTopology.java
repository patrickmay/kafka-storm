/**
 * EchoTopology is a simple Storm topology.
 *
 * @author Patrick May (patrick@softwarematters.org)
 * @author &copy; 2015 Patrick May.  All rights reserved.
 * @version 1
 */

package org.softwarematters.storm.echo;

import backtype.storm.Config;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.spout.SchemeAsMultiScheme;
import backtype.storm.topology.TopologyBuilder;

import storm.kafka.KafkaSpout;
import storm.kafka.SpoutConfig;
import storm.kafka.StringScheme;
import storm.kafka.ZkHosts;

import java.util.Properties;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EchoTopology
{
  // TO DO: replace with properties
  private static final String ZOOKEEPER = "192.168.99.100:2181";
  private static final String MESSAGE_TOPIC = "test.echo";

  private static Logger logger_
    = LoggerFactory.getLogger(EchoTopology.class);

  private TopologyBuilder builder_;
  private Config topologyConfig_;
  
  /**
   * The full constructor for the EchoTopology class.
   *
   */
  public EchoTopology()
    {
    SpoutConfig spoutConfig
      = new SpoutConfig(new ZkHosts(ZOOKEEPER,"/brokers"),
                        MESSAGE_TOPIC,
                        "/" + MESSAGE_TOPIC,
                        UUID.randomUUID().toString());
    //    spoutConfig.forceStartOffsetTime(-1);
    spoutConfig.scheme = new SchemeAsMultiScheme(new StringScheme());
    spoutConfig.forceFromStart = true;
    
    KafkaSpout kafkaSpout = new KafkaSpout(spoutConfig);
    EchoBolt echoBolt = new EchoBolt();
    
    //Topology definition
    builder_ = new TopologyBuilder();
    builder_.setSpout("kafka-spout",kafkaSpout,1);
    builder_.setBolt("echo-bolt",echoBolt,3)
            .shuffleGrouping("kafka-spout");

    //Configuration
    topologyConfig_ = new Config();
    }


  /**
   * Run the topology.
   */
  private void submit()
    {
    try
      {
      StormSubmitter.submitTopology("Echo",
                                    topologyConfig_,
                                    builder_.createTopology());
      }
    catch (InvalidTopologyException ite)
      {
      }
    catch (AlreadyAliveException aae)
      {
      }
    }


  /**
   * The main harness for the EchoTopology class.
   *
   * @param args The command line arguments passed in.
   */
  public static void main(String args[])
    {
    EchoTopology topology = new EchoTopology();
    topology.submit();
    }
}  // end EchoTopology
