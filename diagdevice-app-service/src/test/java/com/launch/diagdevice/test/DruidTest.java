package com.launch.diagdevice.test;

import com.alibaba.druid.filter.config.ConfigTools;

public class DruidTest {

	public static void main(String[] args) throws Exception {
		// 密码明文
//		String password = "a123456";
		String password = "diag@2018!";

		System.out.println("密码[ " + password + " ]的加密信息如下：\n");

		String[] keyPair = ConfigTools.genKeyPair(512);
		// 私钥
		String privateKey = keyPair[0];
		// 公钥
		String publicKey = keyPair[1];
		// 用私钥加密后的密文
		password = ConfigTools.encrypt(privateKey, password);

		System.out.println("privateKey:" + privateKey);
		System.out.println("publicKey:" + publicKey);
		System.out.println("password:" + password);
		String decryptPassword = ConfigTools.decrypt(publicKey, password);
		System.out.println("decryptPassword：" + decryptPassword);
		
		System.out.println("-----------------test_0()----------");
//		test_0();
		
	}
	
	public static void test_0() throws Exception {
				
		String publickey = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBALgtN0soJa/M6CpSLrHIsjWObThwPSE2xMeJU67qyvSifOeeHrFVMtcF5vjBIcyw8EvcIhFTdMSou7VQ7dBWVgMCAwEAAQ==";
		String password = "n8L/VUyTwsoIRB0iIllpu3HfFrAt33VRt1Q1QHPL9Bq754SNhmGgniKVlYPjYnH0TanYCdOniC+ezv42a5OPiA==";   

		System.out.println( ConfigTools.decrypt(publickey, password) );
	} 
}
