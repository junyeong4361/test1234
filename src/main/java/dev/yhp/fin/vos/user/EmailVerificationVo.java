package dev.yhp.fin.vos.user;

import dev.yhp.fin.entities.VerificationCodeEntity;
import dev.yhp.fin.enums.user.EmailVerificationResult;
import dev.yhp.fin.interfaces.IResult;

public class EmailVerificationVo extends VerificationCodeEntity implements IResult<EmailVerificationResult> {
    private EmailVerificationResult result;

    @Override
    public EmailVerificationResult getResult() {
        return this.result;
    }

    @Override
    public void setResult(EmailVerificationResult result) {
        this.result = result;
    }
}