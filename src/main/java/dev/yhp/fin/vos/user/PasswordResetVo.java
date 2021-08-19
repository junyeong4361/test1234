package dev.yhp.fin.vos.user;

import dev.yhp.fin.entities.PasswordResetKeyEntity;
import dev.yhp.fin.enums.user.PasswordResetResult;
import dev.yhp.fin.interfaces.IResult;
import dev.yhp.fin.utils.CryptoUtil;

public class PasswordResetVo extends PasswordResetKeyEntity implements IResult<PasswordResetResult> {
    private PasswordResetResult result;
    private String password;
    private String hashedPassword;

    public String getPassword() {
        return this.password;
    }

    public String getHashedPassword() {
        return this.hashedPassword;
    }

    public void setPassword(String password) {
        this.password = password;
        this.hashedPassword = CryptoUtil.Sha512.hash(password);
    }

    @Override
    public PasswordResetResult getResult() {
        return this.result;
    }

    @Override
    public void setResult(PasswordResetResult result) {
        this.result = result;
    }
}