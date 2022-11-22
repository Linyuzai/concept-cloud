package com.github.linyuzai.concept.cloud.web.request;

import com.github.linyuzai.concept.cloud.web.interceptor.UriSkipInterceptInterceptor;
import com.github.linyuzai.concept.cloud.web.interceptor.WebRequestInterceptor;

import java.util.Collection;

public class UriSkipRequestInterceptor extends UriSkipInterceptInterceptor implements WebRequestInterceptor {

    public UriSkipRequestInterceptor(Collection<String> patterns) {
        setPatterns(patterns);
    }
}
