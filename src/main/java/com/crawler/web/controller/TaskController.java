package com.crawler.web.controller;

import com.crawler.web.service.AnalyseService;
import com.crawler.bean.Info;
import com.crawler.bean.Task;
import com.crawler.web.service.TaskService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
public class TaskController {

    @Autowired
    private TaskService taskService;


    @Autowired
    private AnalyseService analyseService;


    @RequestMapping("createTask.json")
    public String createTask(String seedUrl, Integer dayNum){

        if(StringUtils.isBlank(seedUrl) || dayNum == null || dayNum < 0){
            return "任务创建失败，请重试";
        } else {
            if(!seedUrl.startsWith("http")){
                return "请输入正确的url";
            }
            List<Task> taskList = taskService.queryUnfinishTask();
            if(CollectionUtils.isNotEmpty(taskList)){
                return "还有未完成的任务，任务完成后才能创建任务";
            }
            Task task = new Task();
            task.setDayNum(dayNum);
            task.setSeedUrl(seedUrl);
            task.setFinish(false);
            task.setCreateTime(new Date());
            //创建task并开始执行
            int count = taskService.createTask(task);
            if(count > 0){
                return "创建成功";
            } else {
                return "创建失败";
            }
        }
    }


    @RequestMapping("test.json")
    public String a(){
        List<Info> infoList = analyseService.getInfoList(0);
        Task task = new Task();
        task.setId(1);
        try {
            analyseService.writeToExcel(task, infoList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }
}
