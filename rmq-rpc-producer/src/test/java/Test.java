import com.rmq.rpc.producer.app.RmqProducer;
import com.rmq.rpc.producer.config.Configuration;
import com.rmq.rpc.producer.config.RmqConfig;
import com.rmq.rpc.producer.mq.enums.EncodeTypeEnum;
import com.rmq.rpc.producer.proxy.ProxyBean;

/**
 * @author jilin
 * @version 1.0
 * @project rabbitmq-rpc
 * @description
 * @date 2023/5/22 16:31:22
 */
public class Test {
    public static void main(String[] args) {
        Configuration configuration = new Configuration();
        RmqConfig rmqConfig = new RmqConfig();
        rmqConfig.setAddress("localhost");
        rmqConfig.setClusterName("test_1");
        rmqConfig.setAppId("prc_test");
        rmqConfig.setEncodeType(EncodeTypeEnum.JSON.type);
        RmqProducer rmqProducer = new RmqProducer(configuration,rmqConfig,"com.rmq.rpc.producer.test");
        rmqProducer.start();
        ProxyBean<Test> test = configuration.getProxyBean("test", Test.class);
        String serviceName = configuration.getServiceName(test.getBean());
        com.rmq.rpc.producer.test.Test test1 = (com.rmq.rpc.producer.test.Test) test.getBean();
        test1.send("测试");
    }
}
