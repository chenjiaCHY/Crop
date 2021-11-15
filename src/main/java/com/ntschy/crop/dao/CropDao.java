package com.ntschy.crop.dao;

import com.ntschy.crop.datasource.annotation.DataSource;
import com.ntschy.crop.entity.dto.AnalyzeRequest;
import com.ntschy.crop.entity.dto.ExportCropRequest;
import com.ntschy.crop.entity.dto.QueryCropRequest;
import com.ntschy.crop.entity.vo.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CropDao {

    @DataSource("slave1")
    List<ManageCropExportVO> getManageCropExportList(@Param("request") ExportCropRequest request) throws RuntimeException;

    @DataSource("slave1")
    Integer getVillageStatisticsCount(@Param("season") String season) throws RuntimeException;

    @DataSource("slave1")
    List<VillageExportVO> getVillageStatisticsPage(@Param("season") String season,
                                               @Param("startNo") Integer startNo,
                                               @Param("endNo") Integer endNo) throws RuntimeException;

    @DataSource("slave1")
    List<VillageExportVO> getVillageStatisticsList(@Param("season") String season) throws RuntimeException;

    @DataSource("slave1")
    Integer getHouseholdStatisticsCount(@Param("season") String season, @Param("village") String village) throws RuntimeException;

    @DataSource("slave1")
    List<HouseholdExportVO> getHouseholdStatisticsPage(@Param("season") String season,
                                               @Param("village") String village,
                                               @Param("startNo") Integer startNo,
                                               @Param("endNo") Integer endNo) throws RuntimeException;

    @DataSource("slave1")
    List<HouseholdExportVO> getHouseholdStatisticsList(@Param("season") String season,
                                                 @Param("village") String village) throws RuntimeException;

    @DataSource("slave1")
    Integer getManageCropCount(@Param("request") QueryCropRequest request) throws RuntimeException;

    @DataSource("slave1")
    List<CropVO> getManageCropList(@Param("request") QueryCropRequest request,
                                   @Param("startNo") Integer startNo,
                                   @Param("endNo") Integer endNo) throws RuntimeException;

    @DataSource("slave1")
    List<String> getVillageList() throws RuntimeException;

    @DataSource("slave1")
    List<String> getBreedList() throws RuntimeException;

    @DataSource("slave1")
    List<SearchResult> searchAddress(@Param("household") String household) throws RuntimeException;

    @DataSource("slave1")
    List<AnalyzeResult> getAnalyzeResult(@Param("request") AnalyzeRequest request) throws RuntimeException;

    @DataSource("slave1")
    List<StatisticsResult> getStatistics(@Param("season") String season) throws RuntimeException;

    @DataSource("slave1")
    Integer getFeedbackCount(@Param("status") String status) throws RuntimeException;

    @DataSource("slave1")
    List<FeedbackVO> getFeedbackList(@Param("status") String status,
                            @Param("startNo") Integer startNo,
                            @Param("endNo") Integer endNo) throws RuntimeException;
}
