package com.channel.zengpeng.job;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

import com.channel.zengpeng.util.HttpClient4;

import net.sf.json.JSONObject;

@Component
public class RequestUrlQuartz implements Job {

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			// 获取任务名
			String taskName = context.getJobDetail().getKey().getName();
			System.out.println(taskName + "   " + sdf.format(new Date()));
			JobDataMap jobMap = context.getJobDetail().getJobDataMap();
			Map<String, Object> map = jobMap.getWrappedMap();
			String parameter = (String) map.get("parameter");
			JSONObject json = JSONObject.fromObject(parameter);
			System.out.println(json.getString("url"));
			String url = json.getString("url");
			String response = HttpClient4.doGet(url);
			System.out.println("---" + response);
			// 处理执行任务之后的业务
			System.out.println(taskName + "   " + sdf.format(new Date()));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
