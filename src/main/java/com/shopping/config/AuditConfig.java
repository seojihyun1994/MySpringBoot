package com.shopping.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration // 설정용 파일로 쓰겠습니다.
@EnableJpaAuditing // 감시용 파일로 쓰겠습니다.
public class AuditConfig {

    @Bean // AuditorAware 구현체를 Bean으로 등록할게요.
    public AuditorAware<String> auditorProvider(){
        return new AuditorAwareImpl() ;
    }
}
