package com.s3ich4n.study.springautoconfig;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionEvaluationReport;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringAutoconfigApplication {

    /**
     * Spring Boot 가 Application 로드 후 Condition을 통과한 Bean을 살펴본다
     *
     * 1. Bean annotation을 붙여서 오브젝트 생성
     * 2. Conditional Evaluation 결과를 담는 객체를 꺼내쓰고
     * 3. 그걸 가공하는 내용을 람다식으로 해제한다
     *      1. Condition 통과목록
     *      2. 어떤 Condition을 패스했는지
     *      3. JMX는 제거
     *
     * @param report
     * @return
     */
    @Bean
    ApplicationRunner run(ConditionEvaluationReport report) {
        // Lambda 식으로 처리
        return args -> {
            System.out.println(report.getConditionAndOutcomesBySource().entrySet().stream()
                    .filter(co -> co.getValue().isFullMatch())
                    .filter(co -> co.getKey().indexOf("Jmx") < 0)
                    .map(co -> {
                        System.out.println(co.getKey());
                        co.getValue().forEach(c -> {
                            System.out.println("\t " + c.getOutcome());
                        });
                        System.out.println();
                        return co;
                    }).count());
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringAutoconfigApplication.class, args);
    }

}
