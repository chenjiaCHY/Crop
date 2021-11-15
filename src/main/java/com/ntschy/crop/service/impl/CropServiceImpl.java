package com.ntschy.crop.service.impl;

import com.ntschy.crop.dao.CropDao;
import com.ntschy.crop.entity.base.PageQuery;
import com.ntschy.crop.entity.base.Result;
import com.ntschy.crop.entity.dto.*;
import com.ntschy.crop.entity.vo.*;
import com.ntschy.crop.service.CropService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CropServiceImpl implements CropService {

    @Resource
    private CropDao cropDao;

    @Override
    public List<ManageCropExportVO> getManageCropList(ExportCropRequest exportCropRequest) throws RuntimeException {
        List<ManageCropExportVO> cropList = cropDao.getManageCropExportList(exportCropRequest);

        return cropList;
    }

    @Override
    public PageQuery getVillageStatisticsPage(VillageStatisticsRequest villageStatisticsRequest) throws RuntimeException {

        Integer startNo = PageQuery.startLine(villageStatisticsRequest.getCurrPage(), villageStatisticsRequest.getPageSize());
        Integer endNo = PageQuery.endLine(villageStatisticsRequest.getCurrPage(), villageStatisticsRequest.getPageSize());

        Integer total = cropDao.getVillageStatisticsCount(villageStatisticsRequest.getSeason());

        List<VillageExportVO> villageExportVOList = cropDao.getVillageStatisticsPage(villageStatisticsRequest.getSeason(), startNo, endNo);

        PageQuery pageQuery = new PageQuery(villageStatisticsRequest.getCurrPage(), villageStatisticsRequest.getPageSize(),
                total, Optional.ofNullable(villageExportVOList).orElse(Collections.emptyList()));

        return pageQuery;

    }

    @Override
    public List<VillageExportVO> getVillageStatisticsList(String season) throws RuntimeException {
        List<VillageExportVO> villageExportVOList = cropDao.getVillageStatisticsList(season);

        return villageExportVOList;
    }

    @Override
    public PageQuery getHouseholdStatisticsPage(HouseholdStatisticsRequest householdStatisticsRequest) throws RuntimeException {
        Integer startNo = PageQuery.startLine(householdStatisticsRequest.getCurrPage(), householdStatisticsRequest.getPageSize());
        Integer endNo = PageQuery.endLine(householdStatisticsRequest.getCurrPage(), householdStatisticsRequest.getPageSize());

        Integer total = cropDao.getHouseholdStatisticsCount(householdStatisticsRequest.getSeason(), householdStatisticsRequest.getVillage());

        List<HouseholdExportVO> householdExportVOList = cropDao.getHouseholdStatisticsPage(householdStatisticsRequest.getSeason(), householdStatisticsRequest.getVillage(), startNo, endNo);

        PageQuery pageQuery = new PageQuery(householdStatisticsRequest.getCurrPage(), householdStatisticsRequest.getPageSize(),
                total, Optional.ofNullable(householdExportVOList).orElse(Collections.emptyList()));

        return pageQuery;
    }

    @Override
    public List<HouseholdExportVO> getHouseholdStatisticsList(String season, String village) throws RuntimeException {
        List<HouseholdExportVO> householdExportVOList = cropDao.getHouseholdStatisticsList(season, village);

        return householdExportVOList;
    }

    @Override
    public PageQuery getManageCropPage(QueryCropRequest queryCropRequest) throws RuntimeException {

        Integer startNo = PageQuery.startLine(queryCropRequest.getCurrPage(), queryCropRequest.getPageSize());
        Integer endNo = PageQuery.endLine(queryCropRequest.getCurrPage(), queryCropRequest.getPageSize());

        Integer total = cropDao.getManageCropCount(queryCropRequest);

        List<CropVO> cropVOList = cropDao.getManageCropList(queryCropRequest, startNo, endNo);

        int rowNumber = (queryCropRequest.getCurrPage() - 1) * queryCropRequest.getPageSize() + 1;

        if (!CollectionUtils.isEmpty(cropVOList)) {
            for (CropVO cropVO : cropVOList) {
                cropVO.setRowNumber(rowNumber);
                rowNumber ++;
            }
        }

        PageQuery pageQuery = new PageQuery(queryCropRequest.getCurrPage(), queryCropRequest.getPageSize(),
                total, Optional.ofNullable(cropVOList).orElse(Collections.emptyList()));

        return pageQuery;
    }

    @Override
    public Result getDictData() throws RuntimeException {

        List<String> villageList = cropDao.getVillageList();

        List<String> breedList = cropDao.getBreedList();

        DictData dictData = new DictData();

        dictData.setVillageList(villageList);
        dictData.setBreedList(breedList);

        return new Result<>(dictData);
    }

    @Override
    public Result searchAddress(String household) throws RuntimeException {

        List<SearchResult> searchResultList = cropDao.searchAddress(household);

        if (!CollectionUtils.isEmpty(searchResultList)) {

            for (SearchResult searchResult : searchResultList) {
                StringBuilder sb = new StringBuilder();
                sb.append(Optional.ofNullable(searchResult.getVillage()).orElse(""));
                sb.append(",");
                sb.append(Optional.ofNullable(searchResult.getHousehold()).orElse(""));
                sb.append(",");
                sb.append(Optional.ofNullable(searchResult.getBreed()).orElse(""));
                sb.append(",");
                sb.append(Optional.ofNullable(searchResult.getAcreage()).orElse(0.0));
                sb.append("亩");

                searchResult.setName(sb.toString());
            }
        }

        return new Result<>(searchResultList);
    }

    @Override
    public List<AnalyzeResult> analyze(AnalyzeRequest analyzeRequest) throws RuntimeException {

        if (CollectionUtils.isEmpty(analyzeRequest.getCropArea())) {
            throw new RuntimeException("选择区域为空，不能进行分析！！！");
        }

        List<AnalyzeResult> analyzeResultList = cropDao.getAnalyzeResult(analyzeRequest);

        return analyzeResultList;
    }

    @Override
    public Result statistics(String season, Integer type) throws RuntimeException {

        List<StatisticsResult> statisticsResultList = cropDao.getStatistics(season);

        // 如果是直接输出
        if (type == 1) {
            return new Result<>(statisticsResultList);
        }

        // 如果是需要将比例小于1%的归类到其他中
        Double others = 0.0;
        if (!CollectionUtils.isEmpty(statisticsResultList)) {
            for (StatisticsResult statisticsResult : statisticsResultList) {
                if (!statisticsResult.getBreed().equals("其它") && statisticsResult.getArea() < 50) {
                    others += statisticsResult.getArea();
                }
            }

            for (StatisticsResult statisticsResult : statisticsResultList) {
                if (statisticsResult.getBreed().equals("其它")) {
                    statisticsResult.setArea(statisticsResult.getArea() + others);
                    break;
                }
            }

            statisticsResultList = statisticsResultList.stream().filter(item -> item.getArea() > 50).collect(Collectors.toList());
        }


        return new Result<>(statisticsResultList);
    }
}
