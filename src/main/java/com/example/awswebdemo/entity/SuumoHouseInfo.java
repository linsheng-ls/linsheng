package com.example.awswebdemo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * SUUMO房源信息表
 * </p>
 *
 * @author linsheng
 * @since 2025-03-25
 */
@Getter
@Setter
@TableName("suumo_house_info")
public class SuumoHouseInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 房源ID
     */
    private String houseId;

    /**
     * 物件名
     */
    private String houseName;

    /**
     * 价格
     */
    private String price;

    /**
     * 間取り
     */
    private String layout;

    /**
     * 土地面积
     */
    private String landArea;

    /**
     * 建物面积
     */
    private String buildingArea;

    /**
     * 建成年月
     */
    private String buildYearMonth;

    /**
     * 住所
     */
    private String address;

    /**
     * 原始URL
     */
    private String url;

    /**
     * 爬取时间
     */
    private LocalDateTime crawlDate;
}
