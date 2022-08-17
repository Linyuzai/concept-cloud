package com.github.linyuzai.concept.cloud.web;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("concept.cloud.web")
public class WebConceptProperties {

    private AdviceProperties advice = new AdviceProperties();

    @Data
    public static class AdviceProperties {

        private boolean enabled = true;
    }
}
