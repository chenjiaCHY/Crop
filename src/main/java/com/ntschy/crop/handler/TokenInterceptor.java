package com.ntschy.crop.handler;

import com.ntschy.crop.entity.base.TokenInfo;
import com.ntschy.crop.entity.vo.LoginToken;
import com.ntschy.crop.entity.vo.UserInfoVO;
import com.ntschy.crop.service.AuthorityService;
import com.ntschy.crop.utils.JwtUtil;
import com.ntschy.crop.utils.Utils;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;
import java.util.Optional;

@Configuration
public class TokenInterceptor implements HandlerInterceptor, Ordered {

    private final AuthorityService authorityService;

    public TokenInterceptor(AuthorityService authorityService) {
        this.authorityService = authorityService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (HttpMethod.OPTIONS.toString().equals(request.getMethod())) {
            response.setStatus(HttpStatus.NO_CONTENT.value());
            return true;
        }

        String uri = request.getRequestURI();

        String token = request.getHeader("token");

        if (StringUtils.isBlank(token)) {
            token = request.getParameter("token");
        }

        if (StringUtils.isBlank(token)) {
            throw new CommonException(ExceptionEnum.TOKEN_EXCEPTION);
        }

        TokenInfo tokenInfo = null;
        try {
            tokenInfo = Utils.getTokenInfo(token);
        } catch (Exception e) {
            throw new CommonException(ExceptionEnum.TOKEN_EXCEPTION);
        }

        String time = Utils.GetCurrentDateTime();

        LoginToken loginToken = authorityService.getLoginToken(tokenInfo.getUserID(), time);

        if (loginToken == null) {
            throw new CommonException(ExceptionEnum.TOKEN_EXCEPTION);
        }

        if (!StringUtils.equals(loginToken.getToken(), token)) {
            throw new CommonException(ExceptionEnum.TOKEN_EXCEPTION);
        }

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR_OF_DAY, JwtUtil.EXPIRE_HOUR);

        authorityService.updateLoginTokenExpiresTime(token, Utils.ConvertDateToString(calendar, Utils.YYYYMMDDHH24MMSS));

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e) {

    }

    @Override
    public int getOrder() {
        return 1;
    }
}
