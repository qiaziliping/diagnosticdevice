//package com.launch.diagdevice.blockchain.utils;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import com.launch.diagdevice.blockchain.model.CzlAsset;
//import com.launch.diagdevice.blockchain.model.CzlAssetRecord;
//
//public class MapConstants
//{
//    public static Map<String, Object> assetMap = new HashMap<String, Object>()
//    {
//        /**
//         * 
//         */
//        private static final long serialVersionUID = -2835760111957717445L;
//
//        {
//        	/**
//        	 * 诊断设备建立数字资产
//        	 */
//            put("设备信息", new CzlAsset("device", "serial_no", "设备信息"));
////            put("发票凭证", new CzlAsset("device", "serial_no", "发票凭证"));
//            /**
//             * 先建立用户资产，后数字资产记录（充值记录、消费记录）上链
//             */
//            put("客户编号", new CzlAsset("user", "id", "客户编号"));
//            /**
//             * 供软件价格上链
//             */
//            put("价格信息", new CzlAsset("diag_soft_price", "id", "价格信息"));
//        }
//    };
//
//    public static Map<String, Object> assetRecordMap = new HashMap<String, Object>()
//    {
//        /**
//         * 
//         */
//        private static final long serialVersionUID = -6286085793631800505L;
//
//        {
//            put("订单记录", new CzlAssetRecord("user_order", "id", "UserOrder"));
//            put("消费记录", new CzlAssetRecord("user_consumer_record", "order_id", "UserConsumerRecord"));
//            put("变更日志记录", new CzlAssetRecord("czl_operate_log", "id", "CzlOperateLog"));
////            put("价格信息变更记录", new CzlAssetRecord("diag_soft_price", "id", "SoftPrice"));
//        }
//    };
//
//}
