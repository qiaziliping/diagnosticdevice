package com.launch.diagdevice.system.service.impl;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.dubbo.config.annotation.Reference;
import com.launch.diagdevice.MyCarSerApp;
import com.launch.diagdevice.system.service.DiagSoftInfoManager;
import com.launch.diagdevice.system.vo.DiagSoftInfo;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MyCarSerApp.class)
// @SpringBootTest
public class DiagSoftInfoServiceTest
{

    @Reference(interfaceClass = DiagSoftInfoManager.class)
    private DiagSoftInfoManager diagSoftInfoManager;

    // @Ignore
    @Test
    public void testDiagSoftInfoManager() throws Exception
    {
        // SysProduct sysProduct=
        // productManager.getProductBySerialNoCC("963890001063",
        // "18926");
        List<DiagSoftInfo> list = diagSoftInfoManager.queryDiagSoftInfoList(1133, 1001);
        System.out.println("-------------test sysproduct dubbo service------------");
        System.out.println(list.toString());
        System.out.println("-------------test sysproduct dubbo service------------");
    }

}
