package yuxiang.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@ToString
@Setter
public class User {
    private String username;
    private String password;
    private Date birthday;
}
