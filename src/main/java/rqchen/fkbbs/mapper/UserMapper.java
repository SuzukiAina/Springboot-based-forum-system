package rqchen.fkbbs.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import rqchen.fkbbs.model.User;

@Mapper
public interface UserMapper {
    @Insert("INSERT INTO USER (mail,name,age,sex,role) VALUES (#{mail},#{name},#{age},#{sex},#{role})")
    void insert(User user);
}
