package dev.yhp.fin.vos.user;

import dev.yhp.fin.entities.UserEntity;
import dev.yhp.fin.enums.user.NicknameCheckResult;
import dev.yhp.fin.interfaces.IResult;

public class NicknameCheckVo extends UserEntity implements IResult<NicknameCheckResult> {
    private NicknameCheckResult result;

    @Override
    public NicknameCheckResult getResult() {
        return this.result;
    }

    @Override
    public void setResult(NicknameCheckResult result) {
        this.result = result;
    }
}