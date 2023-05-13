package com.sparta.atchaclonecoding.crawling;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Getter
public class CrawlingClass {
    private final CrawlingSample crawlingSample;

    @Autowired
    public CrawlingClass(CrawlingSample crawlingSample) {
        this.crawlingSample = crawlingSample;
        System.out.println("크롤링돼랏");
        crawlingSample.process();
    }
}
