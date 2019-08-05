package com.channel.zengpeng.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.channel.zengpeng.entity.JobEntity;
import com.channel.zengpeng.service.DynamicJobService;
import com.channel.zengpeng.service.QuartzJobManager;

@Controller
public class JobController {

	private static final Logger logger = LoggerFactory.getLogger(JobController.class);
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

	@GetMapping("/addjob")
	public String addjob(@RequestParam(defaultValue="0",name="id") int id, HttpServletRequest request, HttpServletResponse response,
			ModelMap model) {
		int type=0;
		JobEntity jobEntity = new JobEntity();
		if(id!=0){
			jobEntity = jobService.getJobEntityById(id);
			type=1;
		}
		model.put("job", jobEntity);
		model.put("type", type);
		return "/jobs/addjob";
	}


}