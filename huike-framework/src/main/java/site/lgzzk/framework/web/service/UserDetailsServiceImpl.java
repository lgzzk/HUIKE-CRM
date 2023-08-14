package site.lgzzk.framework.web.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import site.lgzzk.common.core.domain.entity.SysUser;
import site.lgzzk.common.core.domain.model.UserLogin;
import site.lgzzk.common.exception.CustomException;
import site.lgzzk.system.mapper.SysMenuMapper;
import site.lgzzk.system.mapper.SysUserMapper;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    public SysUserMapper sysUserMapper;
    @Autowired
    public SysMenuMapper sysMenuMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getUserName, username);
        SysUser sysUser = sysUserMapper.selectOne(wrapper);
        if (sysUser == null) {
            throw new CustomException("用户名不存在或密码错误");
        }
        List<String> perms = sysMenuMapper.selectPermsByUserId(sysUser.getUserId());
        return new UserLogin(sysUser, perms);
    }
}
