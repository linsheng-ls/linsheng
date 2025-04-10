package com.example.awswebdemo.Controller;

import com.example.awswebdemo.entity.SuumoHouseInfo;
import com.example.awswebdemo.service.SuumoScraperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SuumoScraperController {

    @Autowired
    private SuumoScraperService suumoScraperService;

    @GetMapping("/scrape-house")
    public String scrapeHouse(@RequestParam String urlRaw) {
        suumoScraperService.scrapeHouseInfo(urlRaw);
        return "Scraping done for urlRaw: " + urlRaw;
    }

    @GetMapping("/get-house")
    public List<SuumoHouseInfo> getHouse() {
        return suumoScraperService.getHouseInfoByUrl();
    }

    @GetMapping("/get-houseInfo")
    public List<SuumoHouseInfo> houseInfo(@RequestParam String urlRaw) {
        return suumoScraperService.getHouseInfoByUrl(urlRaw);
    }

}
