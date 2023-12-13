package cn.itcast.mq.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FanoutConfig {
    //声明交换机itcast.fanout
    @Bean
    public FanoutExchange fanoutExchange(){
        return new FanoutExchange("itcast.fanout");
    }

    //队列1：fanout.queue1
    @Bean
    public Queue fanoutQueue1(){
        return new Queue("fanout.queue1");
    }

    //声明交换机与队列1的绑定关系
    @Bean
    public Binding fanoutBinding1(Queue fanoutQueue1,FanoutExchange fanoutExchange){
        return BindingBuilder
                .bind(fanoutQueue1)
                .to(fanoutExchange);
    }

    //队列2：fanout.queue2
    @Bean
    public Queue fanoutQueue2(){
        return new Queue("fanout.queue2");
    }
    //声明交换机与队列2的绑定关系
    @Bean
    public Binding fanoutBinding2(Queue fanoutQueue2,FanoutExchange fanoutExchange){
        return BindingBuilder
                .bind(fanoutQueue2)
                .to(fanoutExchange);
    }

    @Bean
    public Queue objectQueue(){
        return new Queue("object.queue");
    }
}
