package com.launch.diagdevice.blockchain.configure;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.launch.diagdevice.common.constant.Constants;

@Configuration
public class RabbitConfig {

	@Bean
	public Queue znhyAllocationCreateQueue() {
		return new Queue("znhyAllocationCreate"+Constants.getTestQueueFlag());
	}

	@Bean
	public Queue znhyAllocationUpdateQueue() {
		return new Queue("znhyAllocationUpdate"+Constants.getTestQueueFlag());
	}

	@Bean
	public Queue znhyAccountCreateQueue() {
		return new Queue("znhyAccountCreate"+Constants.getTestQueueFlag());
	}

	@Bean
	public Queue znhyAccountUpdateQueue() {
		return new Queue("znhyAccountUpdate"+Constants.getTestQueueFlag());
	}

	@Bean
	public Queue znhyOrderAllocateQueue() {
		return new Queue("znhyOrderAllocate"+Constants.getTestQueueFlag());
	}

	@Bean
	public Queue czlAssetCreateQueue() {
		return new Queue("czlAssetCreate"+Constants.getTestQueueFlag());
	}

	@Bean
	public Queue czlAssetRecordCreateQueue() {
		return new Queue("czlAssetRecordCreate"+Constants.getTestQueueFlag());
	}
}
