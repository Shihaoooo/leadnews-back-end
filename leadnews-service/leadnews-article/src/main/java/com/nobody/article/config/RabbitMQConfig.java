package com.nobody.article.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
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



    /*
    * 声明队列
    * */

    @Bean
    public Queue userQueue(){
        return new Queue("article.q1",true,false,false);
    }

    /*
    * 队列与交换机绑定
    * */

    @Bean
    public Binding wmNewsBinding(Queue wmNews,TopicExchange topicExchange){
        return BindingBuilder.bind(wmNews).to(topicExchange).with("article.upordown");
    }


}
