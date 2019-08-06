package com.channel.zengpeng.dao;


import org.springframework.data.repository.CrudRepository;

import com.channel.zengpeng.entity.JobEntity;


public interface JobEntityRepository extends CrudRepository<JobEntity, Long> {
    JobEntity getById(Integer id);
}