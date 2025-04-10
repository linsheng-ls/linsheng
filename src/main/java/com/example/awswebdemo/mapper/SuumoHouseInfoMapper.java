package com.example.awswebdemo.mapper;

import com.example.awswebdemo.entity.SuumoHouseInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * SUUMO房源信息表 Mapper 接口
 * </p>
 *
 * @author linsheng
 * @since 2025-03-25
 */
@Mapper
public interface SuumoHouseInfoMapper extends BaseMapper<SuumoHouseInfo> {
    SuumoHouseInfo selectById(Long id);
    List<SuumoHouseInfo> selectAll();
    int insert(SuumoHouseInfo info);

    List<SuumoHouseInfo> selectByHouseId(String houseId);
    List<SuumoHouseInfo> selectByUrl(String url);

    List<SuumoHouseInfo> SelectBycrawlDate();
}

