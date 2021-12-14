package com.autohome.frostmourne.spi.config;

import com.autohome.frostmourne.core.contract.MailConfig;
import com.autohome.frostmourne.spi.dao.IEmailSender;
import com.autohome.frostmourne.spi.dao.IFeiShuSender;
import com.autohome.frostmourne.spi.dao.IWeChatSender;
import com.autohome.frostmourne.spi.dao.impl.EmailSender;
import com.autohome.frostmourne.spi.dao.impl.FeiShuSender;
import com.autohome.frostmourne.spi.dao.impl.WeChatSender;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class BeanConfig {

    @Value("${email.smtp.host}")
    private String smtpHost;

    @Value("${email.smtp.port}")
    private String smtpPort;

    @Value("${email.smtp.auth}")
    private String smtpAuth;

    @Value("${email.sender}")
    private String sender;

    @Value("${email.sender.password}")
    private String senderPassword;

    @Value("${email.tls.enable}")
    private String tlsEnable;

    @Value("${wechat.corpid}")
    private String weChatCorpId;

    @Value("${wechat.agentid}")
    private String weChatAgentId;

    @Value("${wechat.secret}")
    private String weChatSecret;

    @Bean
    public MailConfig mailConfig() {
        return new MailConfig(smtpHost, smtpPort, smtpAuth, tlsEnable, sender, senderPassword);
    }

    @Bean
    public IEmailSender emailSender(MailConfig mailConfig) {
        return new EmailSender(mailConfig);
    }

    @Bean
    public IWeChatSender weChatSender() {
        return new WeChatSender(weChatCorpId, weChatAgentId, weChatSecret);
    }

    @Bean
    public IFeiShuSender feiShuSender(RestTemplate restTemplate) {
        return new FeiShuSender(restTemplate);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
