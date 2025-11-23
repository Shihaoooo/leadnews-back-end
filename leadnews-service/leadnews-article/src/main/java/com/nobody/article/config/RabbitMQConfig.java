package com.nobody.article.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    /*
    * 声明交换机
    * */

    @Bean
    public TopicExchange topicExchange(){
        return new TopicExchange("article_exchange",true,false);
    }

    // 延时交换机
    @Bean
    public DirectExchange delayExchange(){
        return ExchangeBuilder.directExchange("delay.direct")
                .delayed()
                .durable(true)
                .build();
    }



    /*
    * 声明队列
    * */

    @Bean
    public Queue wmNews(){
        return new Queue("article.q1",true,false,false);
    }

    @Bean
    public Queue delay(){
        return new Queue("delay.q1",true,false,false );
    }


    /*
    * 队列与交换机绑定
    * */

    @Bean
    public Binding wmNewsBinding(Queue wmNews,TopicExchange topicExchange){
        return BindingBuilder.bind(wmNews).to(topicExchange).with("article.upordown");
    }

    @Bean
    public Binding delayBinding(Queue delay,DirectExchange delayExchange){
        return BindingBuilder.bind(delay).to(delayExchange).with("delay.upordown");
    }


}
