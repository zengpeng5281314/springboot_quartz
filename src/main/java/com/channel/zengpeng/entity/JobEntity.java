package com.channel.zengpeng.entity;


import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
/**
 * Created by EalenXie on 2018/6/4 14:09
 * 这里个人示例,可自定义相关属性
 */
@Entity
@Table(name = "JOB_ENTITY")
@Getter
@Setter
public class JobEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "class_path")
    private String classPath;	//执行类名
    private String name;          //job名称
    private String group;         //job组名
    private String cron;          //执行的cron
    private String parameter;     //job的参数
    private String description;   //job描述信息
    @Column(name = "vm_param")
    private String vmParam;       //vm参数
    @Column(name = "jar_path")
    private String jarPath;       //job的jar路径,在这里我选择的是定时执行一些可执行的jar包
    private String status;        //job的执行状态,这里我设置为OPEN/CLOSE且只有该值为OPEN才会执行该Job
    public JobEntity() {
    }
  
    @Override
    public String toString() {
        return "JobEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", group='" + group + '\'' +
                ", cron='" + cron + '\'' +
                ", parameter='" + parameter + '\'' +
                ", description='" + description + '\'' +
                ", vmParam='" + vmParam + '\'' +
                ", jarPath='" + jarPath + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
    //新增Builder模式,可选,选择设置任意属性初始化对象
    public JobEntity(Builder builder) {
        id = builder.id;
        classPath = builder.classPath;
        name = builder.name;
        group = builder.group;
        cron = builder.cron;
        parameter = builder.parameter;
        description = builder.description;
        vmParam = builder.vmParam;
        jarPath = builder.jarPath;
        status = builder.status;
    }
    
    @Getter
    @Setter
    public static class Builder {
        private Integer id;
        private String name = "";          //job名称
        private String group = "";         //job组名
        private String classPath="";		//类路径
        private String cron = "";          //执行的cron
        private String parameter = "";     //job的参数
        private String description = "";   //job描述信息
        private String vmParam = "";       //vm参数
        private String jarPath = "";       //job的jar路径
        private String status = "";        //job的执行状态,只有该值为OPEN才会执行该Job
    }
}