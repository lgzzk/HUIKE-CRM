package site.lgzzk.framework.security;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import site.lgzzk.common.exception.CustomException;
import site.lgzzk.system.entity.SysUser;
import site.lgzzk.system.mapper.SysUserMapper;

import java.util.List;
import java.util.Objects;

import static site.lgzzk.common.constant.HttpStatus.ERROR;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    public SysUserMapper sysUserMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getUserName, username);
        SysUser sysUser = sysUserMapper.selectOne(wrapper);
        if (sysUser == null){
            throw new CustomException("用户名或密码错误", ERROR);
        }
        return new UserLogin(sysUser);
    }

}
