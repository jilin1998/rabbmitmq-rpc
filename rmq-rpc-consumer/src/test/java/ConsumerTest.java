import com.rmq.rpc.common.mq.enums.EncodeTypeEnum;
import com.rmq.rpc.consumer.app.RmqConsumer;
import com.rmq.rpc.consumer.config.Configuration;
import com.rmq.rpc.consumer.config.RmqConsumerConfig;
import com.rmq.rpc.consumer.instance.impl.ReflectInstanceFactory;

/**
 * @author wusifan
 * @version 1.0
 * @project rabbit-rpc
 * @description
 * @date 2023/5/26 16:06:28
 */
public class ConsumerTest {
    public static void main(String[] args) {
        Configuration configuration = new Configuration();
        RmqConsumerConfig config = new RmqConsumerConfig();
        config.setAddress("localhost");
        config.setClusterName("test_1");
        config.setAppId("test");
        config.setEncodeType(EncodeTypeEnum.JSON.type);
        configuration.addFactoryChain(new ReflectInstanceFactory());
        RmqConsumer rmqConsumer = new RmqConsumer(configuration,config,"com.rmq.rpc.consumer.test");
        rmqConsumer.start();

    }
}
