package es.pruebas.kafka_java8;

import java.util.Properties;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.config.SaslConfigs;
import org.apache.kafka.common.config.SslConfigs;
//import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

public class ProducerCreator {

	

    public static Producer<String, String> createProducer(String conexion) {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, conexion);
//        props.put(ProducerConfig.CLIENT_ID_CONFIG, "yoClienteInventado");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        //props.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, CustomPartitioner.class.getName());
        
//        props.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, 5000);
//        props.put(ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG, 5000);
//        props.put(ProducerConfig.TRANSACTION_TIMEOUT_CONFIG, 5000);
        
        props.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "SASL_SSL");
        
        props.put(SaslConfigs.SASL_MECHANISM, "SCRAM-SHA-256");
        props.put(SaslConfigs.SASL_JAAS_CONFIG, "org.apache.kafka.common.security.scram.ScramLoginModule required username=\"admin-econtrato\" password=\"YES5rJ5BNxdFSfTX\";");
        
        props.put(SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG, "/logs/econtprep/KyC/kafka/kafka_2.12-2.1.1/bin/naboo.truststore.jks"); // TODO: parametrizar
        props.put(SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG, "u7VJQtPyHp");
        props.put(SslConfigs.SSL_TRUSTSTORE_TYPE_CONFIG, "JKS");
        
        return new KafkaProducer<String, String>(props);
    }
    
	
}
