package com.crawler.web.service;

import com.crawler.bean.Info;
import com.crawler.util.ConfigUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class InfoService {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public void writeInfo(Info info){
        String sql = "INSERT INTO info (title, solicitationNumber, agency, offerLocation, synopsis, noticeType, postedDate, responseDate, archivingPolicy, archiveDate, originalSetAside, setAside, classificationCode, naicsCode, contractingOfficeAddress, placeOfPerformance, primaryPointOfContractName, primaryPointOfContractEmail, primaryPointOfContractPhone, filePath, url, taskId) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            List<Object> argumentsList = new ArrayList<>();

            argumentsList.add(info.getTitle().replace("\t", "").replace("\n", "").replace("\r", ""));
            argumentsList.add(info.getSolicitationNumber().replace("\t", "").replace("\n", "").replace("\r", ""));
            argumentsList.add(info.getAgency().replace("\t", "").replace("\n", "").replace("\r", ""));
            argumentsList.add(info.getOfferLocation().replace("\t", "").replace("\n", "").replace("\r", ""));
            argumentsList.add(info.getSynopsis().replace("\t", "").replace("\n", "").replace("\r", ""));
            argumentsList.add(info.getNoticeType().replace("\t", "").replace("\n", "").replace("\r", ""));
            argumentsList.add(info.getPostedDate().replace("\t", "").replace("\n", "").replace("\r", ""));
            argumentsList.add(info.getResponseDate().replace("\t", "").replace("\n", "").replace("\r", ""));
            argumentsList.add(info.getArchivingPolicy().replace("\t", "").replace("\n", "").replace("\r", ""));
            argumentsList.add(info.getArchiveDate().replace("\t", "").replace("\n", "").replace("\r", ""));
            argumentsList.add(info.getOriginalSetAside().replace("\t", "").replace("\n", "").replace("\r", ""));
            argumentsList.add(info.getSetAside().replace("\t", "").replace("\n", "").replace("\r", ""));
            argumentsList.add(info.getClassificationCode().replace("\t", "").replace("\n", "").replace("\r", ""));
            argumentsList.add(info.getNaicsCode().replace("\t", "").replace("\n", "").replace("\r", ""));
            argumentsList.add(info.getContractingOfficeAddress().replace("\t", "").replace("\n", "").replace("\r", ""));
            argumentsList.add(info.getPlaceOfPerformance().replace("\t", "").replace("\n", "").replace("\r", ""));
            argumentsList.add(info.getPrimaryPointOfContractName().replace("\t", "").replace("\n", "").replace("\r", ""));
            argumentsList.add(info.getPrimaryPointOfContractEmail().replace("\t", "").replace("\n", "").replace("\r", ""));
            argumentsList.add(info.getPrimaryPointOfContractPhone().replace("\t", "").replace("\n", "").replace("\r", ""));
            argumentsList.add(info.getFilePath().toString().replace("\t", "").replace("\n", "").replace("\r", ""));
            argumentsList.add(info.getUrl().replace("\t", "").replace("\n", "").replace("\r", ""));
            if(ConfigUtil.CURRENT_TASK_ID <= 0){
                argumentsList.add(-1);
            } else {
                argumentsList.add(ConfigUtil.CURRENT_TASK_ID);
            }

            jdbcTemplate.update(sql, argumentsList.toArray());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
