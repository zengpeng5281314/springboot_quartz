package com.channel.zengpeng.controller;


import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.channel.zengpeng.entity.JobEntity;
import com.channel.zengpeng.job.TestQuartz;
import com.channel.zengpeng.service.DynamicJobService;
import com.channel.zengpeng.service.QuartzJobManager;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


@Controller
public class JobMapController {

    private static final Logger logger = LoggerFactory.getLogger(JobMapController.class);
    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;
    @Autowired
    private DynamicJobService jobService;
    @Autowired
    QuartzJobManager quartzJobManager;


    @GetMapping("/joblist")
    public String joblist(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
    	List<JobEntity> joblist = jobService.loadJobs();
    	model.put("joblist", joblist);
    	return "/jobs/joblist";
    }
    
  
  

}