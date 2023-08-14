package site.lgzzk.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

public interface SysMenuMapper extends BaseMapper<String> {

    List<String> selectPermsByUserId(Long userId);
}
