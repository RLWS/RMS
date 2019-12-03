package com.rlws.rms.websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * 注入ServerEndpointExporter，这个bean会自动注册使用了@ServerEndpoint注解声明的Websocket endpoint
 * 注意:如果使用独立的servlet容器，而不是直接使用springboot的内置容器，就不要注入ServerEndpointExporter， 因为它将由容器自己提供和管理。
 *
 * @author rlws
 * @date 2019/12/3  9:44
 */
@Configuration
public class WebSocketConfig {

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

}
