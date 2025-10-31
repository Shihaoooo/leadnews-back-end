package com.nobody.wemedia.service.impl;

import com.nobody.model.dtos.Result;
import com.nobody.model.enums.AppHttpCodeEnum;
import com.nobody.model.wemedia.dtos.WmLoginDto;
import com.nobody.model.wemedia.pojos.WmUser;
import com.nobody.utils.JWTUtils;
import com.nobody.wemedia.mapper.WmUserMapper;
import com.nobody.wemedia.service.WmUserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class WmUserServiceImpl implements WmUserService {

    private final WmUserMapper wmUserMapper;

    private final JWTUtils jwtUtils;

    @Override
    public Result login(WmLoginDto dto) {
        //1.检查参数
        if(StringUtils.isBlank(dto.getName()) || StringUtils.isBlank(dto.getPassword())){
            return Result.error(AppHttpCodeEnum.PARAM_INVALID);
        }

        //2.查询用户
        WmUser wmUser = wmUserMapper.getOneByName(dto.getName());
        if(wmUser == null){
            return Result.error(AppHttpCodeEnum.DATA_NOT_EXIST);
        }

        //3.比对密码
        String salt = wmUser.getSalt();
        String pswd = dto.getPassword();
        pswd = DigestUtils.md5DigestAsHex((pswd + salt).getBytes());
        if(pswd.equals(wmUser.getPassword())){
            //4.返回数据  jwt
            Map<String,Object> map  = new HashMap<>();
            map.put("token", jwtUtils.generateToken(wmUser.getId().longValue()));
            wmUser.setSalt("");
            wmUser.setPassword("");
            map.put("user",wmUser);
            return Result.success(map);

        }else {
            return Result.error(AppHttpCodeEnum.LOGIN_PASSWORD_ERROR);
        }
    }
}