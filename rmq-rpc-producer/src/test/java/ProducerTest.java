import com.rmq.rpc.common.mq.enums.EncodeTypeEnum;
import com.rmq.rpc.producer.app.RmqProducer;
import com.rmq.rpc.producer.config.Configuration;
import com.rmq.rpc.producer.config.RmqProducerConfig;
import com.rmq.rpc.producer.proxy.ProxyBean;

/**
 * @author jilin
 * @version 1.0
 * @project rabbitmq-rpc
 * @description
 * @date 2023/5/22 16:31:22
 */
public class ProducerTest {
    public static void main(String[] args) {
        Configuration configuration = new Configuration();
        RmqProducerConfig rmqProducerConfig = new RmqProducerConfig();
        rmqProducerConfig.setAddress("localhost");
        rmqProducerConfig.setClusterName("test_1");
        rmqProducerConfig.setAppId("rpc_test");
        rmqProducerConfig.setEncodeType(EncodeTypeEnum.JSON.type);
        RmqProducer rmqProducer = new RmqProducer(configuration, rmqProducerConfig,"com.rmq.rpc.producer.test");
        rmqProducer.start();
        ProxyBean<ProducerTest> test = configuration.getProxyBean("test", ProducerTest.class);
        String serviceName = configuration.getServiceName(test.getBean());
        com.rmq.rpc.producer.test.Test test1 = (com.rmq.rpc.producer.test.Test) test.getBean();
        System.out.println(test1.send("测试"));
    }
}
