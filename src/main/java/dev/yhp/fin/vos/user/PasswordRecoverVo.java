package dev.yhp.fin.vos.user;

import dev.yhp.fin.entities.UserEntity;
import dev.yhp.fin.enums.user.PasswordRecoverResult;
import dev.yhp.fin.interfaces.IResult;

public class PasswordRecoverVo extends UserEntity implements IResult<PasswordRecoverResult> {
    private PasswordRecoverResult result;

    @Override
    public PasswordRecoverResult getResult() {
        return this.result;
    }

    @Override
    public void setResult(PasswordRecoverResult result) {
        this.result = result;
    }
}