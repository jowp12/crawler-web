package com.test;

import com.crawler.bean.Task;
import com.crawler.web.service.TaskService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CollectTest {


    @Autowired
    public TaskService taskService;

    @Test
    public void testStart(){
        Task task = new Task();
        task.setSeedUrl("https://www.fbo.gov/index?s=opportunity&mode=list&tab=list&tabmode=list&_posted_date=14");
        task.setDayNum(14);
        taskService.createTask(task);
    }


}
