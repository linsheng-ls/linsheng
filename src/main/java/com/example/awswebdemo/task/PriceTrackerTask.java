//package com.example.awswebdemo.task;
//
//import com.example.awswebdemo.Service.SuumoScraper;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//@Component
//public class PriceTrackerTask {
//
//    @Scheduled(cron = "0 0 9 * * ?")  // 每天上午9点执行
//    public void trackPrice() {
//        System.out.println("开始抓取房价...");
//        SuumoScraper.scrapeHouseInfo("76867925");
//    }
//}
