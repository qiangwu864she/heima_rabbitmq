package cn.itcast.mq.spring;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringAmpqTest {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void testSendMessage2SimpleQueue(){
        String queueName = "simple.queue";
        String message =  "Hello,wuqiang queue amqp!";
        rabbitTemplate.convertAndSend(queueName,message);
    }

    @Test
    public void testSendMessage2WorkQueue() throws InterruptedException {
        String queueName = "simple.queue";
        String message =  "Hello,wuqiang queue amqp!---";
        for(int i=1;i<=50;i++){
            rabbitTemplate.convertAndSend(queueName,message + i );
            Thread.sleep(20);
        }

    }

    @Test
    public void testSendFanoutExchange(){
        //准备交换机名称
        String exchangeName = "itcast.fanout";
        //消息
        String message =  "Hello,Kris wu and everyone!";
        //发送消息
        rabbitTemplate.convertAndSend(exchangeName,"",message);
    }

    @Test
    public void testSendDirectExchange(){
        //准备交换机名称
        String exchangeName = "itcast.direct";
        //消息
        String message =  "Hello,yellow";
        //发送消息
        rabbitTemplate.convertAndSend(exchangeName,"yellow",message);
    }

    @Test
    public void testSendTopicExchange(){
        //准备交换机名称
        String exchangeName = "itcast.topic";
        //消息
        String message =  "众所周知，日本是一个岛国,这是一个添加测试！";
        //发送消息
        rabbitTemplate.convertAndSend(exchangeName,"japan.news",message);
    }

    @Test
    public void testSendObjectQueue(){
        Map<String, Object> msg = new HashMap<>();
        msg.put("name","吴强");
        msg.put("age", 21);
        rabbitTemplate.convertAndSend("object.queue",msg);
    }
}
