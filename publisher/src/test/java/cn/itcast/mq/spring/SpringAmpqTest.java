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

    //做这个测试的时候注意要首先有一个名称为“simple.queue”的队列，否则发送的消息是没有队列接收的，已经在这个上面出错两次了，大兄弟！
    @Test
    public void testSendMessage2SimpleQueue(){
        String queueName = "simple.queue";
        String message =  "Hello,wuqiang queue today is 12/14 22:21!";
        rabbitTemplate.convertAndSend(queueName,message);
    }

    //注意：如果希望在消息队列的console上看到消息，需要将消费者的监听器停止监听，否则消息就直接消费，然后就看不到了  2023.12.14
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
        String message =  "Hello,red";
        //发送消息
        rabbitTemplate.convertAndSend(exchangeName,"red",message);
    }

    @Test
    public void testSendTopicExchange(){
        //准备交换机名称
        String exchangeName = "itcast.topic";
        //消息
        String message =  "众所周知，日本又瞎蹦跶了！";
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
