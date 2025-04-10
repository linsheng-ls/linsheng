package com.example.awswebdemo.service;

import com.example.awswebdemo.entity.SuumoHouseInfo;
import com.example.awswebdemo.mapper.SuumoHouseInfoMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class SuumoScraperService {

    @Autowired
    private SuumoHouseInfoMapper suumoHouseInfoMapper;


    public void scrapeHouseInfo(String urlRaw) {

        String houseId = "";
        Pattern pattern = Pattern.compile("nc_(\\d+)");
        Matcher matcher = pattern.matcher(urlRaw);


        if (matcher.find()) {
            houseId = matcher.group(1);
            System.out.println("提取到ID: " + houseId);
        } else {
            System.out.println("没有找到ID");
        }
        var existing = suumoHouseInfoMapper.selectByUrl(urlRaw);
        if (!CollectionUtils.isEmpty(existing)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "房源已存在，不能重复添加: url = " + urlRaw);

        }

        try {
            Document doc = Jsoup.connect(urlRaw)
                    .userAgent("Mozilla/5.0")
                    .get();
            System.out.println("抓取到页面：" + doc.html());
            System.out.println("抓取到页面：" + urlRaw);

            // 核心字段
            String houseName = "";
            String price = "";
            String layout = "";
            String landArea = "";
            String buildingArea = "";
            String buildYearMonth = "";
            String address = "";

            // 找第一个 summary="表" 的表
            Elements tables = doc.select("table[summary=表]");
            if (tables.size() > 0) {
                Element infoTable = tables.get(0); // 只取第一个表

                Elements rows = infoTable.select("tr");

                for (Element row : rows) {
                    Element th = row.selectFirst("th");
                    Elements tds = row.select("td");

                    if (th != null && tds.size() > 0) {
                        String header = th.text().trim();

                        // 物件名
                        if (header.contains("物件名")) {
                            houseName = tds.get(0).text().trim();
                        }

                        // 价格 和 間取り 同一行
                        if (header.contains("価格") && tds.size() >= 2) {
                            String priceRaw = tds.get(0).text().trim();
                            price = priceRaw.split("円")[0] + "円";  // 截到“円”为止

                            layout = tds.get(1).text().trim();
                        }

                        // 土地面积 和 建物面积 同一行
                        if (header.contains("土地面積") && tds.size() >= 2) {
                            landArea = tds.get(0).text().trim();
                            buildingArea = tds.get(1).text().trim();
                        }

                        // 完成时期/築年月
                        if (header.contains("道路") || header.contains("築年月")) {
                            buildYearMonth = tds.get(tds.size() - 1).text().trim();
                        }

                        // 地址
                        if (header.contains("住所")) {
                            String addressRaw = tds.get(1).text().trim();
                            address = addressRaw.split("分")[0] + "分";  // 截到“円”为止

                        }

                    }
                }
            }

            // 输出
            System.out.println("房源ID: " + houseId);
            System.out.println("物件名: " + houseName);
            System.out.println("価格: " + price);
            System.out.println("間取り: " + layout);
            System.out.println("土地面积: " + landArea);
            System.out.println("建物面积: " + buildingArea);
            System.out.println("建成年月: " + buildYearMonth);
            System.out.println("交通: " + address);
            System.out.println("url: " + urlRaw);

            SuumoHouseInfo houseInfo = new SuumoHouseInfo();
            houseInfo.setHouseId(houseId);
            houseInfo.setHouseName(houseName);
            houseInfo.setPrice(price);
            houseInfo.setLayout(layout);
            houseInfo.setLandArea(landArea);
            houseInfo.setBuildingArea(buildingArea);
            houseInfo.setBuildYearMonth(buildYearMonth);
            houseInfo.setAddress(address); // address 实际上是交通
            houseInfo.setUrl(urlRaw);

            // ✅ 插入数据库
            int rows = suumoHouseInfoMapper.insert(houseInfo);
            System.out.println("插入数据库成功，影响行数：" + rows);


        } catch (IOException e) {

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "无法访问 " + urlRaw);

        }
    }


    public List<SuumoHouseInfo> getHouseInfoByUrl() {
        var houseList = suumoHouseInfoMapper.SelectBycrawlDate();
        return houseList;
    }

    public List<SuumoHouseInfo> getHouseInfo(String urlRaw) {
        var HouseInfo = suumoHouseInfoMapper.selectByUrl(urlRaw);
        return houseList;
    }

}
