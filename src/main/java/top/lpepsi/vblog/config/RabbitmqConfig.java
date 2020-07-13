package top.lpepsi.vblog.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: v-blog
 * @description: RabbitMQ配置类
 * @author: 林北
 * @create: 2020-07-12 09:18
 **/
@Configuration
public class RabbitmqConfig {

    public static final String QUEUE = "lpepsi_email";
    public static final String EXCHANGE = "lpepsi_exchange";
    public static final String ROUTE_KEY = "lpepsi_route_key";

    /**
    * @Description: 创建队列
    * @Param: []
    * @return: org.springframework.amqp.core.Queue
    * @Author: 林北
    * @Date: 2020-07-12
    */
    @Bean
    Queue queue(){
        return new Queue(QUEUE, true,false,false);
    }
    
    /**
    * @Description: 创建Topic交换器
    * @Param: []
    * @return: org.springframework.amqp.core.TopicExchange
    * @Author: 林北
    * @Date: 2020-07-12
    */
    @Bean
    TopicExchange topicExchange(){
        return new TopicExchange(EXCHANGE,true,false);
    }

    /**
    * @Description: 绑定交换器和队列通过路由键
    * @Param: []
    * @return: org.springframework.amqp.core.Binding
    * @Author: 林北
    * @Date: 2020-07-12
    */
    @Bean
    Binding binding(){
        return BindingBuilder.bind(queue()).to(topicExchange()).with(ROUTE_KEY);
    }


    @Bean
    public MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }

}
