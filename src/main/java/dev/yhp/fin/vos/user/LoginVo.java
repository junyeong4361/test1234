package dev.yhp.fin.vos.user;

import dev.yhp.fin.entities.UserEntity;
import dev.yhp.fin.enums.user.LoginResult;
import dev.yhp.fin.interfaces.IResult;
import dev.yhp.fin.utils.CryptoUtil;

public class LoginVo extends UserEntity implements IResult<LoginResult> {
    private LoginResult result;
    private String hashedPassword;
    private UserEntity userEntity;

    public String getHashedPassword() {
        return this.hashedPassword;
    }

    public UserEntity getUserEntity() {
        return this.userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    @Override
    public void setPassword(String password) {
        super.setPassword(password);
        this.hashedPassword = CryptoUtil.Sha512.hash(password);
    }

    @Override
    public LoginResult getResult() {
        return this.result;
    }

    @Override
    public void setResult(LoginResult result) {
        this.result = result;
    }
}