package com.crawler.web.service;

import com.crawler.bean.Info;
import com.crawler.bean.Task;
import com.crawler.util.ConfigUtil;
import com.crawler.util.FileUtil;
import com.crawler.util.HttpUtil;
import com.crawler.util.PatternUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.*;

/**
 * @author dylan
 *
 */
@Service("collectService")
public class CollectService {


    private Logger logger = LoggerFactory.getLogger(CollectService.class);


//    private String seedUrl = "https://www.fbo.gov/index?s=opportunity&mode=list&tab=list&tabmode=list&pp=100";

    private static final String LIST_URL_PREFIX = "https://www.fbo.gov/index?s=opportunity&mode=list&tab=list&tabmode=list&pp=100&pageID=";

    private static final String DETAIL_URL_PREFIX = "https://www.fbo.gov/index";

    private static final String HOST = "https://www.fbo.gov";

    private Set<String> detailUrlSet = new HashSet<>();

    private static final Long DOWNLOAD_WAIT = 1000L;

    private static final String FILE_PATH = "/www/crawler/";


    @Autowired
    private InfoService dbService;




    public List<String> getListUrl(String seedUrl){
        List<String> listUrlList = new ArrayList<>();
        listUrlList.add(seedUrl);
        String content = HttpUtil.doGet(seedUrl);
        if(StringUtils.isBlank(content)){
            logger.error("seed page download error exit!!!");
            System.exit(-1);
        }

        String pageCount = PatternUtil.getMatch(content, "title=\"last page\">\\[(*)\\]", false);
        if(!NumberUtils.isParsable(pageCount)){
            logger.error("get total page error exit!!");
            return Collections.EMPTY_LIST;
        }
        for(int i = 2; i <= Integer.parseInt(pageCount); i++){
            listUrlList.add(seedUrl + "&pageID=" + i);
        }
        return listUrlList;
    }

    public List<String> getDetailUrl(String listUrl){
        List<String> detailUrlList = new ArrayList<>(128);

        String content = HttpUtil.doGet(listUrl);
        if(StringUtils.isBlank(content)){
            logger.error("list download error url:{}", listUrl);
        }

        List<String> detailUrlSuffixList = PatternUtil.getMatchList(content, "\\?s=opportunity&mode=form&id=.*?cview=\\d+");
        if(CollectionUtils.isNotEmpty(detailUrlSuffixList)){
            detailUrlSuffixList.forEach(urlSuffix -> {
                if(StringUtils.isBlank(urlSuffix)){
                    return;
                }
                detailUrlList.add(DETAIL_URL_PREFIX + urlSuffix);
            });
        }

        return detailUrlList;
    }


    public List<String> getAllDetailUrl(String seedUrl){
        List<String> listUrlList = getListUrl(seedUrl);
        List<String> allDetailList = new ArrayList<>();
        listUrlList.forEach(listUrl -> {
            try {
                Thread.sleep(DOWNLOAD_WAIT);
                List<String> detailUrlList = getDetailUrl(listUrl);
                allDetailList.addAll(detailUrlList);
            } catch (Exception e) {
                logger.error("loop get detail url fail url：{}", listUrl);
            }
        });
        return allDetailList;
    }

    public void execute(String seedUrl){
        logger.info("start task ...");
       List<String> allDetailList = getAllDetailUrl(seedUrl);
       logger.info("all detail url count:{}", allDetailList.size());
       for(String detailUrl : allDetailList) {
           try {
               if(detailUrlSet.contains(detailUrl)){
                   logger.info("detail already download url:{}", detailUrl);
                   continue;
               } else {
                   detailUrlSet.add(detailUrl);
               }
               logger.info(detailUrl);


               Thread.sleep(DOWNLOAD_WAIT);
               String content = HttpUtil.doGet(detailUrl);
               if(StringUtils.isBlank(content)){
                   logger.error("detail download fail url:{}", detailUrl);
               }

               String id = PatternUtil.getMatch(detailUrl, "id=(*)&");
               List<String> filePathList = processFile(content, id);
               Info info = AnalyseService.process(content, detailUrl, filePathList);
               dbService.writeInfo(info);
           } catch (Exception e) {
               logger.error("write file fail url:{}", detailUrl, e);
           }
       }
    }

    public List<String> processFile(String content, String id) {

        List<String> urlLinkAreaList = PatternUtil.getMatchList(content, "<dd><a href=.*?</a>");

        List<String> filePathList = new ArrayList<>();

        if(CollectionUtils.isNotEmpty(urlLinkAreaList)){
            urlLinkAreaList.forEach(linkArea -> {
                try {
                    String url = PatternUtil.getMatch(linkArea, "href=\"(*)\"");
                    String name;
                    if(StringUtils.isBlank(url)){
                        return;
                    } else {
                        if(!url.startsWith("http")){
                            url = HOST + url;
                            name = PatternUtil.getMatch(linkArea, "title=\"Download/View (*)\"");
                        } else {
                            return;
                        }
                        if(name.endsWith(".zip")){
                            return;
                        }
                        InputStream fileInputStream = HttpUtil.doGetInputStream(url);
                        String filePath = FILE_PATH + id + "/" + name;

                        FileUtil.writeFile(filePath, fileInputStream);
                        filePathList.add(filePath);
                    }
                } catch (Exception e){
                    logger.error("file down load id :{}", id, e);
                }
            });
        }
        return filePathList;

    }

    @Autowired
    private TaskService taskService;
    @Autowired
    private AnalyseService analyseService;
    public void startWork(Task task){
        String seedUrl = task.getSeedUrl();
        if(task.getDayNum() > 0){
            seedUrl += "&_posted_date=" + task.getDayNum();
        }
        String finalSeedUrl = seedUrl;
        ConfigUtil.CURRENT_TASK_ID = task.getId();
        Thread t = new Thread(() -> {
            try {
                this.execute(finalSeedUrl);
                List<Info> infoList = analyseService.getInfoList(task.getId());
                analyseService.writeToExcel(task, infoList);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                ConfigUtil.CURRENT_TASK_ID = -1;
                taskService.setFinish(task.getId());
            }
        });
        t.start();
    }
}
