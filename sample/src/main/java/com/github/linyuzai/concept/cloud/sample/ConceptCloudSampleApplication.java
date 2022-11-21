package com.github.linyuzai.concept.cloud.sample;

import com.github.linyuzai.concept.cloud.web.EnableWebConcept;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@EnableWebConcept
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class ConceptCloudSampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConceptCloudSampleApplication.class, args);
    }
}
