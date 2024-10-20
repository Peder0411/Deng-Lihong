package com.practice.mapper;

import com.practice.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author liruirui
 * @since 2024-10-16
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    User selectOne(@Param("username") String username,@Param("password") String password);
}
