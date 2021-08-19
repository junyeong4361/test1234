package dev.yhp.fin.vos.user;

import dev.yhp.fin.entities.UserEntity;
import dev.yhp.fin.enums.user.EmailCheckResult;
import dev.yhp.fin.interfaces.IResult;

public class EmailCheckVo extends UserEntity implements IResult<EmailCheckResult> {
    private EmailCheckResult result;

    @Override
    public EmailCheckResult getResult() {
        return this.result;
    }

    @Override
    public void setResult(EmailCheckResult result) {
        this.result = result;
    }
}