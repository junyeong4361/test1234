package dev.yhp.fin.vos.user;

import dev.yhp.fin.entities.UserEntity;
import dev.yhp.fin.enums.user.EmailRecoverResult;
import dev.yhp.fin.interfaces.IResult;

public class EmailRecoverVo extends UserEntity implements IResult<EmailRecoverResult> {
    private EmailRecoverResult result;

    @Override
    public EmailRecoverResult getResult() {
        return this.result;
    }

    @Override
    public void setResult(EmailRecoverResult result) {
        this.result = result;
    }
}