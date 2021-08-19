package dev.yhp.fin.mappers;

import dev.yhp.fin.entities.PasswordResetKeyEntity;
import dev.yhp.fin.entities.UserEntity;
import dev.yhp.fin.entities.VerificationCodeEntity;
import dev.yhp.fin.models.ClientModel;
import dev.yhp.fin.vos.user.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
@SuppressWarnings("UnusedReturnValue")
public interface IUserMapper {
    int insertLoginLog(@Param("clientModel") ClientModel clientModel,
                       @Param("loginVo") LoginVo loginVo);

    int insertUser(RegisterVo registerVo);

    int insertPasswordResetKey(PasswordResetKeyEntity passwordResetKeyEntity);

    int insertVerificationCode(RegisterVo registerVo);

    int selectEmailCount(@Param("email") String email);

    int selectNicknameCount(@Param("nickname") String nickname);

    UserEntity selectUserByLogin(LoginVo loginVo);

    UserEntity selectUserByEmailRecover(EmailRecoverVo emailRecoverVo);

    UserEntity selectUserByEmailVerification(VerificationCodeEntity verificationCodeEntity);

    UserEntity selectUserByPasswordRecover(PasswordRecoverVo passwordRecoverVo);

    UserEntity selectUserByPasswordReset(PasswordResetVo passwordResetVo);

    int updateEmailVerificationCodeExpired(VerificationCodeEntity verificationCodeEntity);

    int updatePasswordResetKeyExpired(PasswordResetKeyEntity passwordResetKeyEntity);

    int updatePasswordResetKeyVerified(PasswordResetKeyEntity passwordResetKeyEntity);

    int updateUser(UserEntity userEntity);
}