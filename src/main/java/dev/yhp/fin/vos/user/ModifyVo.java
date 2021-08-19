package dev.yhp.fin.vos.user;

import dev.yhp.fin.entities.UserEntity;
import dev.yhp.fin.enums.user.ModifyResult;
import dev.yhp.fin.interfaces.IResult;
import dev.yhp.fin.utils.CryptoUtil;

public class ModifyVo extends UserEntity implements IResult<ModifyResult> {
    private String currentPassword;
    private String hashedCurrentPassword;
    private String hashedPassword;
    private ModifyResult result;

    public String getCurrentPassword() {
        return this.currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
        this.hashedCurrentPassword = CryptoUtil.Sha512.hash(currentPassword);
    }

    public String getHashedCurrentPassword() {
        return this.hashedCurrentPassword;
    }

    public String getHashedPassword() {
        return this.hashedPassword;
    }

    @Override
    public void setPassword(String password) {
        super.setPassword(password);
        this.hashedPassword = CryptoUtil.Sha512.hash(password);
    }

    @Override
    public ModifyResult getResult() {
        return this.result;
    }

    @Override
    public void setResult(ModifyResult result) {
        this.result = result;
    }
}