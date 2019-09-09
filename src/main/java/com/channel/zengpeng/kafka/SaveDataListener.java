package com.channel.zengpeng.kafka;

import java.io.IOException;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class SaveDataListener {

	/**
	* 实时获取kafka数据(生产一条，监听生产topic自动消费一条)
	* @param record
	* @throws IOException
	*/
	@KafkaListener(topics = {"${kafka.topic}"})
	public void listen(ConsumerRecord<?, ?> record) throws IOException {
		String value = (String) record.value();
		System.out.println(value);
	}
}
