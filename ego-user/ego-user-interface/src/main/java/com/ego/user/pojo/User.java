package com.ego.user.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

/**
 * @author YangYao
 * @date 2020/9/20 16:00
 * @Description
 */
@Data
@Table(name = "tb_user")
public class User {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;

    @Length(min = 6,max = 20,message = "用户名长度必须在6-20之间")
    private String username;// 用户名

    @Length(min = 4,max = 30,message = "密码长度必须在4-30之间")
    @JsonIgnore//转json的时候忽略密码
    private String password;// 密码

    @NotEmpty
    @Pattern(regexp = "^1[35678]\\d{9}$", message = "手机号格式不正确")
    private String phone;// 电话
    private Date created;// 创建时间
}
