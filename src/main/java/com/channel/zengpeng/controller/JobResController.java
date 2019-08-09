package com.channel.zengpeng.controller;

import org.apache.commons.lang.StringUtils;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.channel.zengpeng.job.TestQuartz;
import com.channel.zengpeng.primary.entity.JobEntity;
import com.channel.zengpeng.primary.service.DynamicJobService;
import com.channel.zengpeng.primary.service.QuartzJobManager;

import net.sf.json.JSONObject;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;


@RestController
public class JobResController {

	private static final Logger logger = LoggerFactory.getLogger(JobResController.class);
	@Autowired
	private SchedulerFactoryBean schedulerFactoryBean;
	@Autowired
	private DynamicJobService jobService;
	@Autowired
	QuartzJobManager quartzJobManager;

	@PostMapping("/doAddJob")
	public String doAddJob(@RequestBody JobEntity jobEntity, HttpServletRequest request, HttpServletResponse response,
			ModelMap model) {
		JSONObject json = new JSONObject();
		if (StringUtils.isNotBlank(jobEntity.getName()) && StringUtils.isNotBlank(jobEntity.getClassPath())
				&& StringUtils.isNotBlank(jobEntity.getCron()) && StringUtils.isNotBlank(jobEntity.getGroup())) {

			JobEntity jobEntityNew = jobService.saveJobEntity(jobEntity);
			try {
				FlushJob(jobEntityNew);
				// reStartAllJobs();
			} catch (SchedulerException e) {
				e.printStackTrace();
			}

			json.put("success", true);
			json.put("msg", "成功");
		} else {
			json.put("success", false);
			json.put("msg", "必填参数为不能为空！");
		}
		return json.toString();
	}

	@PostMapping("/updateJobStatus")
	public String updateJobStatus(@RequestBody JobEntity jobEntity, HttpServletRequest request, HttpServletResponse response,
			ModelMap model) {
		JSONObject json = new JSONObject();
		if (jobEntity.getId()!=0 && StringUtils.isNotBlank(jobEntity.getStatus())) {
			JobEntity jobEntityOld =jobService.getJobEntityById(jobEntity.getId());
			jobEntityOld.setStatus(jobEntity.getStatus());
			JobEntity jobEntityNew = jobService.saveJobEntity(jobEntityOld);
			try {
				FlushJob(jobEntityNew);
			} catch (SchedulerException e) {
				e.printStackTrace();
			}

			json.put("success", true);
			json.put("msg", "成功");
		} else {
			json.put("success", false);
			json.put("msg", "必填参数为不能为空！");
		}
		return json.toString();
	}
	
	// 初始化启动所有的Job
	@PostConstruct
	public void initialize() {
		try {
			reStartAllJobs();
			logger.info("INIT SUCCESS");
		} catch (SchedulerException e) {
			logger.info("INIT EXCEPTION : " + e.getMessage());
			e.printStackTrace();
		}
	}

	// 根据ID重启某个Job
	@RequestMapping("/refresh/{id}")
	public String refresh(@PathVariable Integer id) throws SchedulerException {
		String result;
		JobEntity entity = jobService.getJobEntityById(id);
		if (entity == null)
			return "error: id is not exist ";
		result = FlushJob(entity);
		return result;
	}

	private String FlushJob(JobEntity entity) throws SchedulerException {
		String result;
		synchronized (logger) {
			JobKey jobKey = jobService.getJobKey(entity);
			Scheduler scheduler = schedulerFactoryBean.getScheduler();
			scheduler.pauseJob(jobKey);
			scheduler.unscheduleJob(TriggerKey.triggerKey(jobKey.getName(), jobKey.getGroup()));
			scheduler.deleteJob(jobKey);
			JobDataMap map = jobService.getJobDataMap(entity);
			JobDetail jobDetail = jobService.geJobDetail(jobKey, entity.getDescription(), map);
			if (entity.getStatus().equals("OPEN")) {
				scheduler.scheduleJob(jobDetail, jobService.getTrigger(entity));
				result = "Refresh Job : " + entity.getName() + "\t jarPath: " + entity.getJarPath() + " success !";
			} else {
				result = "Refresh Job : " + entity.getName() + "\t jarPath: " + entity.getJarPath() + " failed ! , "
						+ "Because the Job status is " + entity.getStatus();
			}
		}
		return result;
	}

	// 重启数据库中所有的Job
	@RequestMapping("/refresh/all")
	public String refreshAll() {
		String result;
		try {
			reStartAllJobs();
			result = "SUCCESS";
		} catch (SchedulerException e) {
			result = "EXCEPTION : " + e.getMessage();
		}
		return "refresh all jobs : " + result;
	}

	/**
	 * 重新启动所有的job
	 */
	private void reStartAllJobs() throws SchedulerException {
		synchronized (logger) { // 只允许一个线程进入操作
			Scheduler scheduler = schedulerFactoryBean.getScheduler();
			Set<JobKey> set = scheduler.getJobKeys(GroupMatcher.anyGroup());
			scheduler.pauseJobs(GroupMatcher.anyGroup()); // 暂停所有JOB
			for (JobKey jobKey : set) { // 删除从数据库中注册的所有JOB
				scheduler.unscheduleJob(TriggerKey.triggerKey(jobKey.getName(), jobKey.getGroup()));
				scheduler.deleteJob(jobKey);
			}
			for (JobEntity job : jobService.loadJobs()) { // 从数据库中注册的所有JOB
				logger.info("Job register name : {} , group : {} , cron : {}", job.getName(), job.getGroup(),
						job.getCron());
				JobDataMap map = jobService.getJobDataMap(job);
				JobKey jobKey = jobService.getJobKey(job);
				JobDetail jobDetail = jobService.geJobDetail(jobKey, job.getDescription(), map);
				if (job.getStatus().equals("OPEN"))
					scheduler.scheduleJob(jobDetail, jobService.getTrigger(job));
				else
					logger.info("Job jump name : {} , Because {} status is {}", job.getName(), job.getName(),
							job.getStatus());
			}
		}
	}
}