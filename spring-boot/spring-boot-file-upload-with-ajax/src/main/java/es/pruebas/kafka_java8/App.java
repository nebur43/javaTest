package es.pruebas.kafka_java8;

import java.time.Duration;
import java.util.List;
import java.util.Map.Entry;

import org.apache.kafka.clients.ManualMetadataUpdater;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Hello world!
 *
 */
public class App 
{
	
	public static String TOPIC_NAME_1 = "cola1"; 
	public static String TOPIC_NAME_2 = "cola2";
	
	private static final Logger log = LoggerFactory.getLogger(App.class);
	
	
    static void runProducer(String conexion, String topic, int partition) {
    	
    	try (Producer<String, String> producer = ProducerCreator.createProducer(conexion)) {
    		
            for (int index = 0; index < 10; index++) {
            	log.info("########### EMPIEZA ITERACION ");
                ProducerRecord<String, String> record = new ProducerRecord<String, String>(topic, // topic
		                partition, // partition
		                String.valueOf(index), // key
		                "New -- RECORD " + index+ " partition:" + partition//value
		                );
                log.info("paso1");
                RecordMetadata metadata = producer.send(record).get();
                log.info("paso2");
//                System.out.println("Record sent with key " + index + " to partition " + metadata.partition() + " with offset " + metadata.offset());
                log.info("Record sent with key " + index + " to partition " + metadata.partition() + " with offset " + metadata.offset());
             }
            
    	} catch (Exception e) {
    		log.error("##########################        error al enviar: " , e);
		}
    	
    }
	
    static void runConsumer(String topic, int partition) throws Exception {
        Consumer<String, String> consumer = ConsumerCreator.createConsumer(topic);
        int noMessageFound = 0;
        while (true) {
        	ConsumerRecords<String, String> consumerRecords = consumer.poll(Duration.ofSeconds(1));
          
        	// 1000 is the time in milliseconds consumer will wait if no record is found at broker.
            if (consumerRecords.count() == 0) {
                noMessageFound++;
                if (noMessageFound > 10) // esperamos 10 segundos antes de terminar
                  // If no message found count is reached to threshold exit loop.  
                  break;
                else
                    continue;
            }
        	
        	
          //print each record. 
            consumerRecords.
            	records(new TopicPartition(topic, partition)).
            	forEach(record -> {
            		log.info("**** LEIDO REGISTRO ****");
            		log.info("Record Key " + record.key());
	                log.info("Record value " + record.value());
	                log.info("Record partition " + record.partition());
	                log.info("Record offset " + record.offset());
	                log.info("");
             });
          
          
            // commits the offset of record to broker. (los marca como leidos)
             consumer.commitAsync();
        }
    consumer.close();
    }
	
    /**
     * listar colas: kafka-topics.sh --list --zookeeper zookeeper:2181 .
     * 
     * ver mensajes en cola: kafka-console-consumer.sh --topic cola2 --bootstrap-server localhost:9092 --from-beginning
     * 
     * @param args
     * @throws Exception 
     */
    
    public static void main( String[] args ) throws Exception
    {
        log.info("  EMPEZAMOS " );
        log.trace("trace enabled !!!!");
        runProducer("naboo-int-kafka-a.shared-nonprod.cloud.si.orange.es:9092,naboo-int-kafka-b.shared-nonprod.cloud.si.orange.es:9092,naboo-int-kafka-c.shared-nonprod.cloud.si.orange.es:9092,naboo-int-kafka-d.shared-nonprod.cloud.si.orange.es:9092","Common.DocumentManagement.FraudDOIValidation",0);
//        runProducer("localhost:9093",TOPIC_NAME_1,0);
//        runProducer(TOPIC_NAME_2,1);
//        runConsumer(TOPIC_NAME_2,1);
        log.info("  FIN " );
    }
}
