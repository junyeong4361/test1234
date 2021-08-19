package dev.yhp.fin.vos.user;

import dev.yhp.fin.entities.UserEntity;
import dev.yhp.fin.enums.user.RegisterResult;
import dev.yhp.fin.interfaces.IResult;
import dev.yhp.fin.utils.CryptoUtil;

public class RegisterVo extends UserEntity implements IResult<RegisterResult> {
    private RegisterResult result;
    private String hashedPassword;
    private boolean agreeMarketing;
    private String verificationCode;

    public String getHashedPassword() {
        return this.hashedPassword;
    }

    public boolean isAgreeMarketing() {
        return this.agreeMarketing;
    }

    public void setAgreeMarketing(boolean agreeMarketing) {
        this.agreeMarketing = agreeMarketing;
        super.isMarketing = agreeMarketing;
    }

    public String getVerificationCode() {
        return this.verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    @Override
    public void setPassword(String password) {
        super.setPassword(password);
        this.hashedPassword = CryptoUtil.Sha512.hash(password);
    }

    @Override
    public RegisterResult getResult() {
        return this.result;
    }

    @Override
    public void setResult(RegisterResult result) {
        this.result = result;
    }
}