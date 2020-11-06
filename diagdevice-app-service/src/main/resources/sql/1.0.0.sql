
/** updateBy liping 20190115 */
-- alter table sys_user MODIFY COLUMN password varchar(100) comment '密码';

/** updateBy liping 20190115 -- 用户角色表添加主外关联 */
-- alter table sys_user_role add CONSTRAINT ur_fk_user_id FOREIGN key (user_id) REFERENCES sys_user(id);
-- ALTER TABLE sys_user_role add CONSTRAINT ur_fk_role_id FOREIGN KEY (role_id) REFERENCES sys_role(id); 
/** updateBy liping 20190115 -- 角色权限表添加主外关联 */
-- ALTER TABLE sys_role_authority add CONSTRAINT ra_fk_role_id FOREIGN KEY(role_id) REFERENCES sys_role(id); 
-- ALTER TABLE sys_role_authority add CONSTRAINT ra_fk_resource_id FOREIGN KEY(resource_id) REFERENCES sys_authority(id); 

-- 未修改正式环境数据库
-- 诊断软件表添加pdt_type_id[产品类型ID]，先删除表数据
-- diag_soft表数据记得调用DiagSoftController的synchronizedMyCarDiagSoft方法
truncate table diag_soft;
truncate table diag_soft_price;
alter table diag_soft add pdt_type_id BIGINT(20)comment '产品类型ID' after soft_id;
-- 用户订单表添加 币种 字段
alter table user_order add COLUMN currency varchar(10) comment '币种，RMB,USD,EUR' after price;

---##### 智能合约相关表修改 #####---
-- 分配表中的name[序列号]必须是唯一的
-- alter table znhy_allocation add constraint uk_znhy_allo_name unique (name);
-- 智能合约账户表添加 paypal账户 字段
-- alter table znhy_account add COLUMN paypal varchar(50) comment 'paypal账号' after alipay;


-- czl 相关sql脚本
ALTER TABLE czl_asset_record DROP COLUMN table_name;
ALTER TABLE czl_asset DROP COLUMN table_name;
ALTER TABLE czl_asset DROP COLUMN field_name;







