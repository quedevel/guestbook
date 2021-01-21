package org.zerock.guestbook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class GuestbookApplication {

    // AuditingEntityListener.class 를
    // 활성화시키기 위해서 프로젝트에 @EnableJpaAuditing 설정을 추가한다.
    public static void main(String[] args) {
        SpringApplication.run(GuestbookApplication.class, args);
    }

}
