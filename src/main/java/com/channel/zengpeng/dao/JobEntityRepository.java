package com.channel.zengpeng.dao;


import org.springframework.data.repository.CrudRepository;

import com.channel.zengpeng.entity.JobEntity;
/**
 * Created by EalenXie on 2018/6/4 14:27
 */
public interface JobEntityRepository extends CrudRepository<JobEntity, Long> {
    JobEntity getById(Integer id);
}