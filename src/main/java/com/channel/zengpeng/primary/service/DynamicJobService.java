package com.channel.zengpeng.primary.service;


import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.channel.zengpeng.primary.dao.JobEntityRepository;
import com.channel.zengpeng.primary.entity.JobEntity;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by EalenXie on 2018/6/4 14:25
 */
@Service
public class DynamicJobService {
    @Autowired
    private JobEntityRepository repository;
    //通过Id获取Job
    public JobEntity getJobEntityById(Integer id) {
        return repository.getById(id);
    }
    //从数据库中加载获取到所有Job
    public List<JobEntity> loadJobs() {
        List<JobEntity> list = new ArrayList<>();
        repository.findAll().forEach(list::add);
        return list;
    }
    
    //添加Job
    public JobEntity saveJobEntity(JobEntity jobEntity) {
        return repository.save(jobEntity);
    }
    
    //获取JobDataMap.(Job参数对象)
    public JobDataMap getJobDataMap(JobEntity job) {
        JobDataMap map = new JobDataMap();
        map.put("name", job.getName());
        map.put("group", job.getGroup());
        map.put("cronExpression", job.getCron());
        map.put("parameter", job.getParameter());
        map.put("JobDescription", job.getDescription());
        map.put("vmParam", job.getVmParam());
        map.put("jarPath", job.getJarPath());
        map.put("status", job.getStatus());
        map.put("classPath", job.getClassPath());
        return map;
    }
    //获取JobDetail,JobDetail是任务的定义,而Job是任务的执行逻辑,JobDetail里会引用一个Job Class来定义
    public JobDetail geJobDetail(JobKey jobKey, String description, JobDataMap map) {
        try {
			return JobBuilder.newJob(((Job)(Class.forName(map.getString("classPath")).newInstance())).getClass())
			        .withIdentity(jobKey)
			        .withDescription(description)
			        .setJobData(map)
			        .storeDurably()
			        .build();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
    }
    //获取Trigger (Job的触发器,执行规则)
    public Trigger getTrigger(JobEntity job) {
        return TriggerBuilder.newTrigger()
                .withIdentity(job.getName(), job.getGroup())
                .withSchedule(CronScheduleBuilder.cronSchedule(job.getCron()))
                .build();
    }
    //获取JobKey,包含Name和Group
    public JobKey getJobKey(JobEntity job) {
        return JobKey.jobKey(job.getName(), job.getGroup());
    }
}