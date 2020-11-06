package com.launch.diagdevice.common.enums;

/**
 * 
 * 产品类型枚举类
 * @author LIPING
 * @date 20190215
 *
 */
public enum PdtTypeEnum {
	
	// 本地及测试环境
	X431HDIII(1133L,"X431HDIII"),
    XTEST(1122L,"XTEST"),
	X431PADIII(843L,"X431PADIII");
	
	// 生产环境支持的产品类型
//	X431PADIII(843L,"X431PADIII"),
//	X431PADIIIPLUS(1563L,"X431PADIII plus"),
//	THROTTLE(1623L,"Throttle");

   
    
    private PdtTypeEnum(long pdtTypeId,String pdtType) {
        this.pdtTypeId = pdtTypeId;
        this.pdtType = pdtType;
    }

    /** 产品类型ID */
    private long pdtTypeId;
    /** 产品类型名称 */
    private String pdtType;
    
    
	public long getPdtTypeId() {
		return pdtTypeId;
	}
	public String getPdtType() {
		return pdtType;
	}

    

    /**
     * 根据type获取枚举
     * LIPING
     */
    public static PdtTypeEnum getPdtType(long pdtTypeId) {
        for(PdtTypeEnum wk : values()) {
            if (wk.pdtTypeId == pdtTypeId) {
                return wk;
            }
        }
        return null;
    }


    /*public static void main(String[] args) {
        System.out.println("打印-1>:"+PdtTypeEnum.X431HDIII.getPdtTypeId());
        System.out.println("打印-2>:"+PdtTypeEnum.X431HDIII.getPdtType());
        System.out.println("打印-33>:"+PdtTypeEnum.getPdtType(1133L).getPdtType());
        System.out.println("打印-44>:"+PdtTypeEnum.getPdtType(1133L).pdtTypeId);
    }*/
    
    // 打印的结果
    //  打印-1>:5
    //  打印-2>:星期五
    //  打印-3>:FRIYDAY
    //  打印-4>:FRIYDAY
    //  打印-5>:开车
}
