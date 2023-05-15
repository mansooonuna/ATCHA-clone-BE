//package com.sparta.atchaclonecoding.crawling;
//
//import lombok.Getter;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//@Component
//@Getter
//public class CrawlingClass {
//    private final MovieCrawlingController movieCrawlingController;
//    private final TvCrawlingController tvCrawlingController;
//
//    @Autowired
//    public CrawlingClass(MovieCrawlingController movieCrawlingController,
//                         TvCrawlingController tvCrawlingController) {
//        this.movieCrawlingController = movieCrawlingController;
//        this.tvCrawlingController = tvCrawlingController;
//        System.out.println("-------------TV---------------");
//        tvCrawlingController.process();
//        System.out.println("-------------MOVIE---------------");
//        movieCrawlingController.process();
//    }
//}
