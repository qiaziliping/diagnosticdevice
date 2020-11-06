//package com.launch.diagdevice.blockchain.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.launch.diagdevice.blockchain.client.service.CzlClientService;
//
///**
// * @Author:Hmemb
// * @Description:
// * @Date:Created in 18:24 2018/1/18
// */
//@RestController
//// @Api(value = "Testcontroller", description = "测试swagger")
//public class Testcontroller {
//
//	private String assetId;
//	private String assetName;
//	private String assetContent;
//	private String assetHash;
//
//	@Autowired
//	private CzlClientService userGrpcClientService;
//
////	@Autowired
////	private HelloSender1 helloSender1;
////	@Autowired
////	private HelloSender1 helloSender2;
////	@Autowired
////	private TopicSender topicSender;
//	
//	
//
//	@RequestMapping("/test")
//	// @ApiOperation(value = "test", httpMethod = "GET", notes = "测试grpc插入")
//	public String test(@RequestParam(defaultValue = "Hmemb") String name) {
//		assetId = "diagdevice_device_9876532145632";
//		assetName = "device_9876532145632";
//		assetContent = "diagdevice";
//		return userGrpcClientService.createAsset(assetId, assetName, assetContent, assetHash);
//	}
//
////	@RequestMapping("/hello")
////	public void hello() {
////		helloSender1.send();
////	}
////
////	/**
////	 * 单生产者-多消费者
////	 */
////	@RequestMapping("/oneToMany")
////	public void oneToMany() {
////		for (int i = 0; i < 10; i++) {
////			 helloSender1.send("hellomsg:"+i);
////	         helloSender2.send("hellomsg:"+i);
////		}
////
////	}
////	
//////	@RequestMapping("/userTest")
//////	    public void userTest() {
//////	           userSender.send();
//////	    }
////	
////	@RequestMapping("/topicTest")
////	    public void topicTest() {
////	           topicSender.send();
////	    }
//}
