package com.github.linyuzai.concept.cloud.web.request;

import com.github.linyuzai.concept.cloud.web.interceptor.UriSkipInterceptInterceptor;
import com.github.linyuzai.concept.cloud.web.interceptor.WebRequestInterceptor;

import java.util.Collection;
import java.util.Collections;

public class UriSkipRequestInterceptor extends UriSkipInterceptInterceptor implements WebRequestInterceptor {

    public UriSkipRequestInterceptor(String pattern) {
        setPatterns(Collections.singleton(pattern));
    }

    public UriSkipRequestInterceptor(Collection<String> patterns) {
        setPatterns(patterns);
    }
}
