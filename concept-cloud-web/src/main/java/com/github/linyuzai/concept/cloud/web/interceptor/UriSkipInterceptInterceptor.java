package com.github.linyuzai.concept.cloud.web.interceptor;

import com.github.linyuzai.concept.cloud.web.context.WebContext;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Collections;

public abstract class UriSkipInterceptInterceptor extends SkipInterceptInterceptor {

    private final AntPathMatcher matcher = new AntPathMatcher();

    @Getter
    @Setter
    private Collection<String> patterns = Collections.emptyList();

    @Override
    public boolean shouldSkip(WebContext context) {
        HttpServletRequest request = context.get(HttpServletRequest.class);
        String uri = request.getRequestURI();
        for (String pattern : patterns) {
            if (matcher.match(pattern, uri)) {
                return true;
            }
        }
        return false;
    }
}
