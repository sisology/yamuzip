package team.trillion.yamuzip.auth.model;

import lombok.Getter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import team.trillion.yamuzip.login.dto.UserDTO;

import java.util.Collection;

@Getter
@ToString
public class CustomUser extends User {

    private int userCode;
    private String userName;



    public CustomUser(UserDTO user, Collection<? extends GrantedAuthority> authorities) {
        super(user.getUserName(), user.getUserPwd(), authorities);
        this.userCode = user.getUserCode();
        this.userName = user.getUserName();
    }

    @Override
    public String toString() {
        return super.toString() + ", userCode :" + userCode + ", userId : " + userName;
    }


}

