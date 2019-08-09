package com.channel.zengpeng.primary.dao;


import org.springframework.data.repository.CrudRepository;

import com.channel.zengpeng.primary.entity.JobEntity;


public interface JobEntityRepository extends CrudRepository<JobEntity, Long> {
    JobEntity getById(Integer id);
}