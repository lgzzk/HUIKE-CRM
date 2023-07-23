package site.lgzzk.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import site.lgzzk.system.entity.SysUser;

/**
* @author zzk17
* @description 针对表【sys_user(用户信息表)】的数据库操作Mapper
* @createDate 2023-07-18 22:27:21
* @Entity site.lgzzk.xommon.SysUser
*/
@Repository
public interface SysUserMapper extends BaseMapper<SysUser> {

}




