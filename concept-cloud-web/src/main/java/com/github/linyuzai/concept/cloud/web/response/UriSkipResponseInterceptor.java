package com.github.linyuzai.concept.cloud.web.response;

import com.github.linyuzai.concept.cloud.web.interceptor.UriSkipInterceptInterceptor;
import com.github.linyuzai.concept.cloud.web.interceptor.WebResponseInterceptor;

import java.util.Collection;

public class UriSkipResponseInterceptor extends UriSkipInterceptInterceptor implements WebResponseInterceptor {

    public UriSkipResponseInterceptor(Collection<String> patterns) {
        setPatterns(patterns);
    }
}
