package CanTuna.CanTunaacon3d.domain;

import javax.persistence.*;

@Entity
public class User {

    public static final Long USER_TYPE_CREATOR = 0L;
    public static final Long USER_TYPE_EDITOR = 1L;
    public static final Long USER_TYPE_CUSTOMER = 2L;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "name")
    private String userName;

    @Column(name = "type")
    private Long userType;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getUserType() {
        return userType;
    }

    public void setUserType(Long userType) {
        this.userType = userType;
    }
}
