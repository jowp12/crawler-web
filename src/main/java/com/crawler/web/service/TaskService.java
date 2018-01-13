package com.crawler.web.service;

import com.crawler.bean.Task;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 任务管理
 */
@Service
public class TaskService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private CollectService collectService;

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public int createTask(Task task){
        String seedUrl = task.getSeedUrl();
        task.setSeedUrl(seedUrl.replaceAll("&_posted_date=\\d+", ""));
        System.out.println(task.getSeedUrl());

        String sql = "INSERT INTO task(seed_url, day_num, is_finish, create_time) VALUES (?, ?, ?, ?)";
        List<Object> argumentList = new ArrayList<>();
        argumentList.add(task.getSeedUrl());
        argumentList.add(task.getDayNum());
        argumentList.add(0);
        argumentList.add(simpleDateFormat.format(new Date()));
        int count = jdbcTemplate.update(sql, argumentList.toArray());
        List<Task> tasks = queryUnfinishTask();
        //开始执行抓取任务
        if(CollectionUtils.isNotEmpty(tasks)){
            collectService.startWork(tasks.get(0));
        }
        return count;
    }

    public List<Task> queryUnfinishTask(){
        String sql = "select * from task where is_finish = 0 order by id desc";
        List<Task> taskList = jdbcTemplate.query(sql, (resultSet, i) -> {
            Task task = new Task();
            task.setSeedUrl(resultSet.getString("seed_url"));
            task.setFinish(resultSet.getBoolean("is_finish"));
            task.setId(resultSet.getInt("id"));
            task.setDayNum(resultSet.getInt("day_num"));
            return task;
        });
        return taskList;
    }

    public void setFinish(Integer id){
        String sql = "update task set is_finish = 1 where id=" + id;
        jdbcTemplate.execute(sql);
    }
}
