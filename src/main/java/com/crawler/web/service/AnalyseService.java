package com.crawler.web.service;

import com.crawler.bean.Info;
import com.crawler.bean.Task;
import com.google.gson.Gson;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

import static com.crawler.util.PatternUtil.extractForHTML;
import static com.crawler.util.PatternUtil.getMatch;

@Component
public class AnalyseService {

    Logger logger = LoggerFactory.getLogger(AnalyseService.class);


    private Gson gson = new Gson();


    private static String[] HEAD_INFO = {"Title","Solicitation Number","Agency","Office Location","Synopsis","Notice Type","Posted Date","Response Date","Archiving Policy","Archive Date","Original Set Aside","Set Aside","Classification Code","NAICS Code","Contracting Office Address","Place of Performance","Primary Point of Contact - Name","Primary Point of Contact - Email","Primary Point of Contact - Phone ","ALL FILES","PAGE 2 URL"};

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public static Info process(String content, String url, List<String> filePath){
        Info info = new Info();

        String title = formatNull(getMatch(content, "agency-header.*?<h2>(*)</h2>"));
        info.setTitle(title);

        String solicitationNumber = formatNull(getMatch(content,"<div class=\"sol-num\">Solicitation Number:(*)</div>"));
        info.setSolicitationNumber(solicitationNumber);

        String agency = formatNull(getMatch(content, "agency-name\">Agency:(*)<br>"));
        info.setAgency(agency);

        String office = formatNull(getMatch(content, "agency-name\">Agency:.*?Office:(*)<br>"));
        String location = formatNull(getMatch(content, "agency-name\">Agency.*?Location:(*)</div>"));
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(office).append(location);
        info.setOfferLocation(stringBuffer.toString());

        String synopsis = formatNull(getMatch(content, "dnf_class_values_procurement_notice__description_\">Synopsis.*?req-indicator.*?</div>(*)<div id=\"so_formfield_dnf"));
        synopsis = extractForHTML(synopsis, " ");
        info.setSynopsis(synopsis);

        String noticeType = formatNull(getMatch(content, "dnf_class_values_procurement_notice__procurement_type_\">Notice Type.*?<div.*?>(*)<"));
        info.setNoticeType(noticeType);

        String postedDate = formatNull(getMatch(content, "dnf_class_values_procurement_notice__packages__0__posted_date__field-label\">\n\tPosted Date.*?<div.*?>(*)<"));
        info.setPostedDate(postedDate);

        String responseDate = formatNull(getMatch(content, "dnf_class_values_procurement_notice__response_deadline__field-label\">\n\tResponse Date.*?<div.*?>(*)<"));
        info.setResponseDate(responseDate);

        String archivingPolicy = formatNull(getMatch(content, "dnf_class_values_procurement_notice__archive_type__field-label\">\n\tArchiving Policy.*?<div.*?>(*)<"));
        info.setArchivingPolicy(archivingPolicy);

        String archiveDate = formatNull(getMatch(content, "dnf_class_values_procurement_notice__archive_date__field-label\">\n\tArchive Date.*?<div.*?>(*)<"));
        info.setArchiveDate(archiveDate);

        String originalSetAside = formatNull(getMatch(content, "dnf_class_values_procurement_notice__original_set_aside__field-label\">\n\tOriginal Set Aside.*?<div.*?>(*)<"));
        info.setOriginalSetAside(originalSetAside);

        String setAside = formatNull(getMatch(content, "dnf_class_values_procurement_notice__set_aside__field-label\">\n\tSet Aside.*?<div.*?>(*)<"));
        info.setSetAside(setAside);

        String classificationCode = formatNull(getMatch(content, "dnf_class_values_procurement_notice__classification_code__field-label\">\n\tClassification Code.*?<div.*?>(*)<"));
        info.setClassificationCode(classificationCode);

        String naicsCode = formatNull(getMatch(content, "dnf_class_values_procurement_notice__naics_code__field-label\">\n\tNAICS Code.*?<div.*?>(*)<"));
        info.setNaicsCode(naicsCode);

        String contractingOfficeAddress = formatNull(getMatch(content, "Contracting Office Address<.*?<div.*?>(*)<"));
        info.setContractingOfficeAddress(contractingOfficeAddress);

        String placeOfPerformance = formatNull(getMatch(content, "Place of Performance<.*?<div.*?>(*)<"));
        info.setPlaceOfPerformance(placeOfPerformance);

        String primaryPointOfContractName = formatNull(getMatch(content, "dnf_class_values_procurement_notice__primary_poc__widget.*?<div>(*)<"));
        info.setPrimaryPointOfContractName(primaryPointOfContractName);

        String primaryPointOfContractEmail = formatNull(getMatch(content, "dnf_class_values_procurement_notice__primary_poc__widget.*?<div><a.*?>(*)<"));
        info.setPrimaryPointOfContractEmail(primaryPointOfContractEmail);

        String primaryPointOfContractPhone = formatNull(getMatch(content, "dnf_class_values_procurement_notice__primary_poc__widget.*?Phone:(*)<"));
        info.setPrimaryPointOfContractPhone(primaryPointOfContractPhone);

        info.setUrl(url);

        info.setFilePath(filePath);

        return info;
    }

    public static String formatNull(String str){
        str = str == null ? "" : str;
        return str;

    }


    public List<Info> getInfoList(int taskId){
//        String sql = "select * from info where taskId=" + taskId;
        String sql = "select * from info ";
        List<Info> infoList = jdbcTemplate.query(sql, (resultSet, i) -> {
            Info info = new Info();
            info.setTitle(resultSet.getString("title"));
            info.setSolicitationNumber(resultSet.getString("solicitationNumber"));
            info.setAgency(resultSet.getString("agency"));
            info.setOfferLocation(resultSet.getString("offerLocation"));
            info.setSynopsis(resultSet.getString("synopsis"));
            info.setNoticeType(resultSet.getString("noticeType"));
            info.setPostedDate(resultSet.getString("postedDate"));
            info.setResponseDate(resultSet.getString("responseDate"));
            info.setArchivingPolicy(resultSet.getString("archivingPolicy"));
            info.setArchiveDate(resultSet.getString("archiveDate"));
            info.setOriginalSetAside(resultSet.getString("originalSetAside"));
            info.setSetAside(resultSet.getString("setAside"));
            info.setClassificationCode(resultSet.getString("classificationCode"));
            info.setNaicsCode(resultSet.getString("naicsCode"));
            info.setContractingOfficeAddress(resultSet.getString("contractingOfficeAddress"));
            info.setPlaceOfPerformance(resultSet.getString("placeOfPerformance"));
            info.setPrimaryPointOfContractName(resultSet.getString("primaryPointOfContractName"));
            info.setPrimaryPointOfContractEmail(resultSet.getString("primaryPointOfContractEmail"));
            info.setPrimaryPointOfContractPhone(resultSet.getString("primaryPointOfContractPhone"));
            info.setFilePathStr(resultSet.getString("filePath"));
            info.setUrl(resultSet.getString("url"));
            return info;

        });
        return infoList;
    }

    public void writeToExcel(Task task, List<Info> infoList) throws Exception {
        String excelPath = "/www/crawler/" + task.getId() + ".xlsx";

        if(!createExcelFile(excelPath)){
            logger.error("文件创建失败。。。");
            return;
        }
        XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(excelPath));
        XSSFSheet sheet = workbook.getSheetAt(0);

        XSSFRow row;
        XSSFCell[] cells;

        int startRowNum = 1;

        for(Info info : infoList){
            if(info == null){
                continue;
            }
            row = sheet.createRow(startRowNum);
            cells = new XSSFCell[HEAD_INFO.length];
            startRowNum++;
            for(int i = 0; i < HEAD_INFO.length; i++){
                cells[i] = row.createCell(i);
            }

            cells[0].setCellValue(info.getTitle());
            cells[1].setCellValue(info.getSolicitationNumber());
            cells[2].setCellValue(info.getAgency());
            cells[3].setCellValue(info.getOfferLocation());
            cells[4].setCellValue(info.getSynopsis());
            cells[5].setCellValue(info.getNoticeType());
            cells[6].setCellValue(info.getPostedDate());
            cells[7].setCellValue(info.getResponseDate());
            cells[8].setCellValue(info.getArchivingPolicy());
            cells[9].setCellValue(info.getArchiveDate());
            cells[10].setCellValue(info.getOriginalSetAside());
            cells[11].setCellValue(info.getSetAside());
            cells[12].setCellValue(info.getClassificationCode());
            cells[13].setCellValue(info.getNaicsCode());
            cells[14].setCellValue(info.getContractingOfficeAddress());
            cells[15].setCellValue(info.getPlaceOfPerformance());
            cells[16].setCellValue(info.getPrimaryPointOfContractName());
            cells[17].setCellValue(info.getPrimaryPointOfContractEmail());
            cells[18].setCellValue(info.getPrimaryPointOfContractPhone());
            cells[19].setCellValue(info.getFilePathStr());
            cells[20].setCellValue(info.getUrl());
        }
        //创建文件流
        OutputStream stream = new FileOutputStream(excelPath);
        //写入数据
        workbook.write(stream);
        //关闭文件流
        stream.close();
    }


    public boolean createExcelFile(String excelPath) {
        boolean isCreateSuccess = false;
        Workbook workbook = null;
        try {
            workbook = new XSSFWorkbook();
        }catch(Exception e) {
            e.printStackTrace();
        }
        if(workbook != null) {
            Sheet sheet = workbook.createSheet("sheet");
            Row row = sheet.createRow(0);
            Cell[] cells = new XSSFCell[HEAD_INFO.length];
            for(int i = 0; i < HEAD_INFO.length; i++){
                cells[i] = row.createCell(i);
                cells[i].setCellValue(HEAD_INFO[i]);
            }

            try {
                FileOutputStream outputStream = new FileOutputStream(excelPath);
                workbook.write(outputStream);
                outputStream.flush();
                outputStream.close();
                isCreateSuccess = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        File sss = new File(excelPath);
        return isCreateSuccess;
    }

}
