package com.ntschy.crop.service;

import com.ntschy.crop.entity.base.PageQuery;
import com.ntschy.crop.entity.base.Result;
import com.ntschy.crop.entity.dto.*;
import com.ntschy.crop.entity.vo.AnalyzeResult;
import com.ntschy.crop.entity.vo.HouseholdExportVO;
import com.ntschy.crop.entity.vo.ManageCropExportVO;
import com.ntschy.crop.entity.vo.VillageExportVO;

import java.util.List;

public interface CropService {

    PageQuery getManageCropPage(QueryCropRequest queryCropRequest) throws RuntimeException;

    List<ManageCropExportVO> getManageCropList(ExportCropRequest exportCropRequest) throws RuntimeException;

    PageQuery getVillageStatisticsPage(VillageStatisticsRequest villageStatisticsRequest) throws RuntimeException;

    List<VillageExportVO> getVillageStatisticsList(String season) throws RuntimeException;

    PageQuery getHouseholdStatisticsPage(HouseholdStatisticsRequest householdStatisticsRequest) throws RuntimeException;

    List<HouseholdExportVO> getHouseholdStatisticsList(String season, String village) throws RuntimeException;

    Result getDictData() throws RuntimeException;

    Result searchAddress(String household) throws RuntimeException;

    List<AnalyzeResult> analyze(AnalyzeRequest analyzeRequest) throws RuntimeException;

    Result statistics(String season, Integer type) throws RuntimeException;
}
