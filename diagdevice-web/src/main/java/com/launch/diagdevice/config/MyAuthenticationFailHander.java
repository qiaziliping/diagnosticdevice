package com.launch.diagdevice.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.launch.diagdevice.common.constant.WebConstant;
import com.launch.diagdevice.common.result.AppResult;
import com.launch.diagdevice.common.util.JsonHelper;

@Component("myAuthenticationFailHander")
public class MyAuthenticationFailHander extends SimpleUrlAuthenticationFailureHandler {
      
//	  @Autowired
//      private ObjectMapper objectMapper;
	
      private Logger logger = LoggerFactory.getLogger(getClass());
      
      @Override
      public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                  AuthenticationException exception) throws IOException, ServletException {
            logger.info("--------login fail---------");
            JsonHelper jsonh = new JsonHelper();
    		AppResult appResult = new AppResult();
    		
            //super.onAuthenticationFailure(request, response, exception);
            
            String mesg = exception.getMessage();
            if (StringUtils.isNumeric(mesg)) {
            	int code = Integer.valueOf(String.valueOf(mesg));
            	appResult.setCode(code);
            	appResult.setMessage(WebConstant.checkCode(code));
            } else {
            	appResult.setCode(-1);
            	appResult.setMessage(mesg);
            }
            
            logger.error("mesg>:"+mesg);
            
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");   
            response.getWriter().write(jsonh.toJsonStr(appResult));

      }
}