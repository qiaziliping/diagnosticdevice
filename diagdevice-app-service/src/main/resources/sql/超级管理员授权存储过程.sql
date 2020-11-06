-- roleId是超级管理员的ID，授予所有权限
create PROCEDURE insert_ra_pro (IN roleId long)
BEGIN
	DECLARE text_sql text;  -- 执行的SQL语句
	DECLARE temp_id int(11) default 0;

	DECLARE cur_end int(11) default 1;
  -- 定义一个游标
  declare cur_reson cursor for select id from sys_authority where resource_type = 5;
	DECLARE EXIT HANDLER FOR NOT FOUND SET cur_end = 0;


	-- 打开游标
  open cur_reson;
			while cur_end > 0 DO
					-- 迭代游标
          fetch cur_reson into temp_id;
					
				set text_sql = CONCAT('insert into sys_role_authority (role_id,resource_id) values (',roleId,',',temp_id,')');              
                 
                select text_sql;    

                SET @sql_text = text_sql;
                PREPARE stmt FROM @sql_text;
                EXECUTE stmt;
                DEALLOCATE PREPARE stmt;

			end while;
	
	close cur_reson;
	

end;


set @roleId = 1;
call insert_ra_pro(@roleId);