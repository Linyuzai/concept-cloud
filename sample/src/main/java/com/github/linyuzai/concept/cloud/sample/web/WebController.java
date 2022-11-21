package com.github.linyuzai.concept.cloud.sample.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/web")
public class WebController {

    @GetMapping("/string")
    public String string() {
        return "String";
    }

    @GetMapping("/view")
    public WebView view() {
        WebView wv = new WebView();
        wv.setId("id");
        wv.setName("name");
        return wv;
    }

    @GetMapping("/error")
    public void error() {
        throw new RuntimeException("Error");
    }
}
