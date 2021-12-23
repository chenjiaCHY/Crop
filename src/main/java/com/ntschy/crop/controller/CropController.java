package com.ntschy.crop.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import com.ntschy.crop.entity.base.PageQuery;
import com.ntschy.crop.entity.base.Result;
import com.ntschy.crop.entity.dto.*;
import com.ntschy.crop.entity.vo.AnalyzeResult;
import com.ntschy.crop.entity.vo.HouseholdExportVO;
import com.ntschy.crop.entity.vo.ManageCropExportVO;
import com.ntschy.crop.entity.vo.VillageExportVO;
import com.ntschy.crop.service.CropService;
import com.ntschy.crop.utils.ExcelStyleUtil;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/crop")
@Validated
public class CropController {

    @Autowired
    private CropService cropService;


    /**
     * 管理页面-查询作物列表
     * @param queryCropRequest
     * @return
     */
    @PostMapping("/getManageCropList")
    @ResponseBody
    public Result getManageCropList(@RequestBody @Validated QueryCropRequest queryCropRequest) {
        try {
            PageQuery pageQuery = cropService.getManageCropPage(queryCropRequest);
            return new Result<>(pageQuery);
        } catch (Exception e) {
            return new Result(false, e.getMessage());
        }
    }


    /**
     * 管理页面-导出作物列表
     * @param exportCropRequest
     * @param response
     * @throws IOException
     */
    @PostMapping("/exportManageCropList")
    public void exportManageCropList(@RequestBody @Validated ExportCropRequest exportCropRequest, HttpServletResponse response) throws IOException {
        List<ManageCropExportVO> cropList = cropService.getManageCropList(exportCropRequest);

        String title = "在田作物";
        if (exportCropRequest.getOrigin().equals("MANAGE")) {
            title += "清单";
        } else {
            title = title + "统计_" + exportCropRequest.getVillage() + "_" + exportCropRequest.getHousehold() + "_" + exportCropRequest.getSeason();
        }

        String fileName = title + ".xlsx";

        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(title,"sheet1", ExcelType.XSSF), ManageCropExportVO.class, cropList);
        response.setHeader("Content-Type", "application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("gb2312"), "ISO8859-1"));

        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();

        workbook.write(byteArrayOutputStream);

        byte[] bytes = byteArrayOutputStream.toByteArray();

        OutputStream fileOutputStream = response.getOutputStream();
        fileOutputStream.write(bytes);
        fileOutputStream.flush();
        fileOutputStream.close();
    }


    /**
     * 一张图导出，获取村的统计
     * @param villageStatisticsRequest
     * @return
     */
    @PostMapping("/getVillageStatistics")
    @ResponseBody
    public Result getVillageStatistics(@RequestBody @Validated VillageStatisticsRequest villageStatisticsRequest) {
        try {
            PageQuery pageQuery = cropService.getVillageStatisticsPage(villageStatisticsRequest);
            return new Result<>(pageQuery);
        } catch (Exception e) {
            return new Result(false, e.getMessage());
        }
    }

    /**
     * 一张图导出，村统计
     * @param season
     * @param response
     * @throws IOException
     */
    @GetMapping("/exportVillageStatistics")
    public void exportVillageStatistics(@RequestParam("season") String season, HttpServletResponse response) throws IOException {
        List<VillageExportVO> villageExportVOList = cropService.getVillageStatisticsList(season);

        String title = "在田作物统计_地区_" + season;
        String fileName = title + ".xlsx";

        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(title,"sheet1", ExcelType.XSSF), VillageExportVO.class, villageExportVOList);
        response.setHeader("Content-Type", "application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("gb2312"), "ISO8859-1"));

        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();

        workbook.write(byteArrayOutputStream);

        byte[] bytes = byteArrayOutputStream.toByteArray();

        OutputStream fileOutputStream = response.getOutputStream();
        fileOutputStream.write(bytes);
        fileOutputStream.flush();
        fileOutputStream.close();
    }

    /**
     * 一张图导出，获取户的统计
     * @param householdStatisticsRequest
     * @return
     */
    @PostMapping("/getHouseholdStatistics")
    @ResponseBody
    public Result getHouseholdStatistics(@RequestBody HouseholdStatisticsRequest householdStatisticsRequest) {
        try {
            PageQuery pageQuery = cropService.getHouseholdStatisticsPage(householdStatisticsRequest);
            return new Result<>(pageQuery);
        } catch (Exception e) {
            return new Result(false, e.getMessage());
        }
    }

    /**
     * 一张图导出，户统计
     * @param season
     * @param response
     * @throws IOException
     */
    @GetMapping("/exportHouseholdStatistics")
    public void exportHouseholdStatistics(@RequestParam("season") String season, @RequestParam("village") String village, HttpServletResponse response) throws IOException {
        List<HouseholdExportVO> householdExportVOList = cropService.getHouseholdStatisticsList(season, village);

        String title = "在田作物统计_" + village + "_" + season;
        String fileName = title + ".xlsx";

        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(title,"sheet1", ExcelType.XSSF), HouseholdExportVO.class, householdExportVOList);
        response.setHeader("Content-Type", "application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("gb2312"), "ISO8859-1"));

        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();

        workbook.write(byteArrayOutputStream);

        byte[] bytes = byteArrayOutputStream.toByteArray();

        OutputStream fileOutputStream = response.getOutputStream();
        fileOutputStream.write(bytes);
        fileOutputStream.flush();
        fileOutputStream.close();
    }


    /**
     * 获取字典数据，村、组、品种
     * @return
     */
    @GetMapping("/dictData")
    @ResponseBody
    public Result getDictData() {
        try {
            Result result = cropService.getDictData();
            return result;
        } catch (Exception e) {
            return new Result(false, e.getMessage());
        }
    }

    /**
     * 搜索所属户
     * @param household
     * @return
     */
    @GetMapping("/searchAddress")
    @ResponseBody
    public Result searchAddress(@RequestParam("household") String household) {
        try {
            Result result = cropService.searchAddress(household);
            return result;
        } catch (Exception e) {
            return new Result(false, e.getMessage());
        }
    }

    /**
     * 分析接口
     * @param analyzeRequest
     * @return
     */
    @PostMapping("/analyze")
    @ResponseBody
    public Result analyze(@RequestBody AnalyzeRequest analyzeRequest) {
        try {
            List<AnalyzeResult> analyzeResultList = cropService.analyze(analyzeRequest);
            return new Result<>(analyzeResultList);
        } catch (Exception e) {
            return new Result(false, e.getMessage());
        }
    }

    /**
     * 导出分析结果
     * @param analyzeRequest
     * @param response
     * @throws IOException
     */
    @PostMapping("/exportAnalyze")
    public void exportAnalyze(@RequestBody AnalyzeRequest analyzeRequest, HttpServletResponse response) throws IOException {
        List<AnalyzeResult> analyzeResultList = cropService.analyze(analyzeRequest);

        List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
        for (AnalyzeResult analyzeResult : analyzeResultList) {
            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("cropAttribute", analyzeResult.getCropAttribute());
            dataMap.put("layerAttribute", analyzeResult.getLayerAttribute());
            dataMap.put("area", analyzeResult.getArea());

            dataList.add(dataMap);
        }

        List<ExcelExportEntity> columnList = new ArrayList<>();
        ExcelExportEntity cropEntity = new ExcelExportEntity(analyzeRequest.getCropAttribute(), "cropAttribute", 20);
        columnList.add(cropEntity);

        String layerAttributeName = "地类名称";
        if ("DLBM".equals(analyzeRequest.getLayerAttribute())) {
            layerAttributeName = "地类编码";
        }
        ExcelExportEntity layerEntity = new ExcelExportEntity(layerAttributeName, "layerAttribute", 20);
        columnList.add(layerEntity);

        ExcelExportEntity areaEntity = new ExcelExportEntity("面积（亩）", "area", 20);
        columnList.add(areaEntity);

        ExportParams exportParams = new ExportParams("分析结果","sheet1");
        exportParams.setStyle(ExcelStyleUtil.class);

        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, columnList, dataList);
        response.setHeader("Content-Type", "application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" + new String("分析结果.xls".getBytes("gb2312"), "ISO8859-1"));

        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();

        workbook.write(byteArrayOutputStream);

        byte[] bytes = byteArrayOutputStream.toByteArray();

        OutputStream fileOutputStream = response.getOutputStream();
        fileOutputStream.write(bytes);
        fileOutputStream.flush();
        fileOutputStream.close();
    }

    /**
     * 获取不同种类占地面积的统计
     * @param season
     * @return
     */
    @GetMapping("/statistics")
    @ResponseBody
    public Result statistics(@RequestParam("season") String season, @RequestParam("type") Integer type) {
        try {
            Result result = cropService.statistics(season, type);
            return result;
        } catch (Exception e) {
            return new Result(false, e.getMessage());
        }
    }

    /**
     * 获取反馈信息，分页
     * @param feedbackRequest
     * @return
     */
    @PostMapping("/getFeedback")
    @ResponseBody
    public Result getFeedback(@RequestBody @Validated FeedbackRequest feedbackRequest) {
        try {
            PageQuery pageQuery = cropService.getFeedback(feedbackRequest);
            return new Result<>(pageQuery);
        } catch (Exception e) {
            return new Result(false, e.getMessage());
        }
    }

}
