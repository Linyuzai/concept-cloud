package com.github.linyuzai.concept.domain.system.module.user.mbp;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.linyuzai.concept.domain.system.module.user.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@TableName("t_user")
public class UserPO {

    @TableId(type = IdType.INPUT)
    private String id;

    public UserPO(User user) {

    }
}
