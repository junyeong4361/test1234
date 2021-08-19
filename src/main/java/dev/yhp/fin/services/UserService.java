package dev.yhp.fin.services;

import dev.yhp.fin.entities.PasswordResetKeyEntity;
import dev.yhp.fin.entities.UserEntity;
import dev.yhp.fin.enums.user.*;
import dev.yhp.fin.mappers.IUserMapper;
import dev.yhp.fin.models.ClientModel;
import dev.yhp.fin.utils.CryptoUtil;
import dev.yhp.fin.vos.user.*;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service("dev.yhp.fin.services.UserService")
public class UserService {
    public static class Config {
        public static final int PASSWORD_RESET_KEY_VALID_MINUTES = 30;
    }

    public static class RegExp {
        public static final String EMAIL = "^(?=.{8,50}$)([0-9a-z_]{4,})@([0-9a-z][0-9a-z\\-]*[0-9a-z]\\.)?([0-9a-z][0-9a-z\\-]*[0-9a-z])\\.([a-z]{2,15})(\\.[a-z]{2})?$";
        public static final String PASSWORD = "^([0-9a-zA-Z`~!@#$%^&*()\\-_=+\\[{\\]}\\\\|;:'\",<.>/?]{8,50})$";
        public static final String NICKNAME = "^([0-9a-zA-Z가-힣]{1,10})$";
        public static final String NAME = "^([가-힣]{2,10})$";
        public static final String BIRTH = "^([0-9]{6})$";
        public static final String GENDER = "^([1-4])$";
        public static final String CONTACT_COMPANY = "^(skt|kt|lgu|etc)$";
        public static final String CONTACT_FIRST = "^([0-9]{3})$";
        public static final String CONTACT_SECOND = "^([0-9]{4})$";
        public static final String CONTACT_THIRD = "^([0-9]{4})$";
        public static final String ADDRESS_POSTAL = "^([0-9]{5})$";
        public static final String ADDRESS_PRIMARY = "^([0-9a-zA-Z가-힣()\\-, ]{8,100})$";
        public static final String ADDRESS_SECONDARY = "^([0-9a-zA-Z가-힣()\\-, ]{0,100})$";

        public static final String EMAIL_VERIFICATION_CODE = "^([0-9a-z]{128})$";

        public static final String PASSWORD_RESET_KEY = "^([0-9a-z]{128})$";

        private RegExp() {
        }
    }

    private final JavaMailSender mailSender;
    private final IUserMapper userMapper;

    @Autowired
    public UserService(JavaMailSender mailSender, IUserMapper userMapper) {
        this.mailSender = mailSender;
        this.userMapper = userMapper;
    }

    public static boolean checkEmail(String s) {
        return s != null && s.matches(RegExp.EMAIL);
    }

    public static boolean checkPassword(String s) {
        return s != null && s.matches(RegExp.PASSWORD);
    }

    public static boolean checkNickname(String s) {
        return s != null && s.matches(RegExp.NICKNAME);
    }

    public static boolean checkName(String s) {
        return s != null && s.matches(RegExp.NAME);
    }

    public static boolean checkBirth(String s) {
        return s != null && s.matches(RegExp.BIRTH);
    }

    public static boolean checkGender(String s) {
        return s != null && s.matches(RegExp.GENDER);
    }

    public static boolean checkContactCompany(String s) {
        return s != null && s.matches(RegExp.CONTACT_COMPANY);
    }

    public static boolean checkContactFirst(String s) {
        return s != null && s.matches(RegExp.CONTACT_FIRST);
    }

    public static boolean checkContactSecond(String s) {
        return s != null && s.matches(RegExp.CONTACT_SECOND);
    }

    public static boolean checkContactThird(String s) {
        return s != null && s.matches(RegExp.CONTACT_THIRD);
    }

    public static boolean checkAddressPostal(String s) {
        return s != null && s.matches(RegExp.ADDRESS_POSTAL);
    }

    public static boolean checkAddressPrimary(String s) {
        return s != null && s.matches(RegExp.ADDRESS_PRIMARY);
    }

    public static boolean checkAddressSecondary(String s) {
        return s != null && s.matches(RegExp.ADDRESS_SECONDARY);
    }

    public static boolean checkEmailVerificationCode(String s) {
        return s != null && s.matches(RegExp.EMAIL_VERIFICATION_CODE);
    }

    public static boolean checkPasswordResetKey(String s) {
        return s != null && s.matches(RegExp.PASSWORD_RESET_KEY);
    }

    public void checkEmail(EmailCheckVo emailCheckVo) {
        if (!UserService.checkEmail(emailCheckVo.getEmail())) {
            emailCheckVo.setResult(EmailCheckResult.NORMALIZATION_FAILURE);
            return;
        }
        emailCheckVo.setResult(this.userMapper.selectEmailCount(emailCheckVo.getEmail()) > 0 ?
                EmailCheckResult.EXISTING :
                EmailCheckResult.NON_EXISTING);
    }

    public void checkNickname(NicknameCheckVo nicknameCheckVo) {
        if (!UserService.checkNickname(nicknameCheckVo.getNickname())) {
            nicknameCheckVo.setResult(NicknameCheckResult.NORMALIZATION_FAILURE);
            return;
        }
        nicknameCheckVo.setResult(this.userMapper.selectNicknameCount(nicknameCheckVo.getNickname()) > 0 ?
                NicknameCheckResult.EXISTING :
                NicknameCheckResult.NON_EXISTING);
    }

    public void login(LoginVo loginVo) {
        if (!UserService.checkEmail(loginVo.getEmail()) ||
                !UserService.checkPassword(loginVo.getPassword())) {
            loginVo.setResult(LoginResult.NORMALIZATION_FAILURE);
            return;
        }
        loginVo.setUserEntity(this.userMapper.selectUserByLogin(loginVo));
        if (loginVo.getUserEntity() == null) {
            loginVo.setResult(LoginResult.FAILURE);
            return;
        }
        if (loginVo.getUserEntity().isDeleted()) {
            loginVo.setResult(LoginResult.DELETED);
            return;
        }
        if (!loginVo.getUserEntity().isEmailVerified()) {
            loginVo.setResult(LoginResult.EMAIL_NOT_VERIFIED);
            return;
        }
        if (loginVo.getUserEntity().isSuspended()) {
            loginVo.setResult(LoginResult.SUSPENDED);
            return;
        }
        loginVo.setResult(LoginResult.SUCCESS);
    }

    public void modify(UserEntity userEntity, ModifyVo modifyVo) {
        if (!UserService.checkPassword(modifyVo.getCurrentPassword()) ||
                (!modifyVo.getPassword().equals("") && !UserService.checkPassword(modifyVo.getPassword())) ||
                !UserService.checkNickname(modifyVo.getNickname()) ||
                !UserService.checkName(modifyVo.getName()) ||
                !UserService.checkBirth(modifyVo.getBirth()) ||
                !UserService.checkGender(String.valueOf(modifyVo.getGender())) ||
                !UserService.checkContactCompany(modifyVo.getContactCompany()) ||
                !UserService.checkContactFirst(modifyVo.getContactFirst()) ||
                !UserService.checkContactSecond(modifyVo.getContactSecond()) ||
                !UserService.checkContactThird(modifyVo.getContactThird()) ||
                !UserService.checkAddressPostal(modifyVo.getAddressPostal()) ||
                !UserService.checkAddressPrimary(modifyVo.getAddressPrimary()) ||
                !UserService.checkAddressSecondary(modifyVo.getAddressSecondary())) {
            modifyVo.setResult(ModifyResult.NORMALIZATION_FAILURE);
            return;
        }
        if (!modifyVo.getHashedCurrentPassword().equals(userEntity.getPassword())) {
            modifyVo.setResult(ModifyResult.FAILURE);
            return;
        }
        int nicknameCount = this.userMapper.selectNicknameCount(modifyVo.getNickname());
        if (nicknameCount > 0 && !modifyVo.getNickname().equals(userEntity.getNickname())) {
            modifyVo.setResult(ModifyResult.NICKNAME_DUPLICATE);
            return;
        }
        if (!modifyVo.getPassword().equals("")) {
            userEntity.setPassword(modifyVo.getHashedPassword());
        }
        userEntity.setNickname(modifyVo.getNickname());
        userEntity.setName(modifyVo.getName());
        userEntity.setBirth(modifyVo.getBirth());
        userEntity.setGender(modifyVo.getGender());
        userEntity.setContactCompany(modifyVo.getContactCompany());
        userEntity.setContactFirst(modifyVo.getContactFirst());
        userEntity.setContactSecond(modifyVo.getContactSecond());
        userEntity.setContactThird(modifyVo.getContactThird());
        userEntity.setAddressPostal(modifyVo.getAddressPostal());
        userEntity.setAddressPrimary(modifyVo.getAddressPrimary());
        userEntity.setAddressSecondary(modifyVo.getAddressSecondary());
        this.userMapper.updateUser(userEntity);
        modifyVo.setResult(ModifyResult.SUCCESS);
    }

    public void putLoginLog(ClientModel clientModel, LoginVo loginVo) {
        this.userMapper.insertLoginLog(clientModel, loginVo);
    }

    public void recoverEmail(EmailRecoverVo emailRecoverVo) {
        if (!UserService.checkName(emailRecoverVo.getName()) ||
                !UserService.checkBirth(emailRecoverVo.getBirth()) ||
                !UserService.checkGender(String.valueOf(emailRecoverVo.getGender())) ||
                !UserService.checkContactCompany(emailRecoverVo.getContactCompany()) ||
                !UserService.checkContactFirst(emailRecoverVo.getContactFirst()) ||
                !UserService.checkContactSecond(emailRecoverVo.getContactSecond()) ||
                !UserService.checkContactThird(emailRecoverVo.getContactThird())) {
            emailRecoverVo.setResult(EmailRecoverResult.NORMALIZATION_FAILURE);
            return;
        }
        UserEntity userEntity = this.userMapper.selectUserByEmailRecover(emailRecoverVo);
        if (userEntity == null) {
            emailRecoverVo.setResult(EmailRecoverResult.FAILURE);
            return;
        }
        emailRecoverVo.setEmail(userEntity.getEmail());
        emailRecoverVo.setResult(EmailRecoverResult.SUCCESS);
    }

    public void recoverPassword(PasswordRecoverVo passwordRecoverVo) throws MessagingException {
        if (!UserService.checkEmail(passwordRecoverVo.getEmail()) ||
                !UserService.checkName(passwordRecoverVo.getName()) ||
                !UserService.checkBirth(passwordRecoverVo.getBirth()) ||
                !UserService.checkGender(String.valueOf(passwordRecoverVo.getGender())) ||
                !UserService.checkContactCompany(passwordRecoverVo.getContactCompany()) ||
                !UserService.checkContactFirst(passwordRecoverVo.getContactFirst()) ||
                !UserService.checkContactSecond(passwordRecoverVo.getContactSecond()) ||
                !UserService.checkContactThird(passwordRecoverVo.getContactThird())) {
            passwordRecoverVo.setResult(PasswordRecoverResult.NORMALIZATION_FAILURE);
            return;
        }
        UserEntity userEntity = this.userMapper.selectUserByPasswordRecover(passwordRecoverVo);
        if (userEntity == null) {
            passwordRecoverVo.setResult(PasswordRecoverResult.FAILURE);
            return;
        }
        PasswordResetKeyEntity passwordResetKeyEntity = new PasswordResetKeyEntity();
        passwordResetKeyEntity.setExpiresAt(DateUtils.addMinutes(
                passwordResetKeyEntity.getCreatedAt(),
                Config.PASSWORD_RESET_KEY_VALID_MINUTES));
        passwordResetKeyEntity.setKey(CryptoUtil.Sha512.hash(String.format("%s%s%f%f",
                userEntity.getEmail(),
                new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()),
                Math.random(),
                Math.random())));
        passwordResetKeyEntity.setUserEmail(userEntity.getEmail());
        this.userMapper.insertPasswordResetKey(passwordResetKeyEntity);

//        MimeMessage message = this.mailSender.createMimeMessage();
//        MimeMessageHelper helper = new MimeMessageHelper(message);
//        helper.setTo(passwordResetKeyEntity.getUserEmail());
//        helper.setSubject("[FIN] 비밀번호 재설정 인증 이메일");
//        helper.setText(String.format("<a href=\"http://127.0.0.1/user/reset-password?key=%s\" target=\"_blank\">인증하기</a>", passwordResetKeyEntity.getKey()), true);
//        this.mailSender.send(message);
        System.out.println(String.format("http://127.0.0.1/user/reset-password?key=%s", passwordResetKeyEntity.getKey()));

        passwordRecoverVo.setResult(PasswordRecoverResult.SUCCESS);
    }

    public void register(RegisterVo registerVo) throws MessagingException {
        if (!UserService.checkEmail(registerVo.getEmail()) ||
                !UserService.checkPassword(registerVo.getPassword()) ||
                !UserService.checkNickname(registerVo.getNickname()) ||
                !UserService.checkName(registerVo.getName()) ||
                !UserService.checkBirth(registerVo.getBirth()) ||
                !UserService.checkGender(String.valueOf(registerVo.getGender())) ||
                !UserService.checkContactCompany(registerVo.getContactCompany()) ||
                !UserService.checkContactFirst(registerVo.getContactFirst()) ||
                !UserService.checkContactSecond(registerVo.getContactSecond()) ||
                !UserService.checkContactThird(registerVo.getContactThird()) ||
                !UserService.checkAddressPostal(registerVo.getAddressPostal()) ||
                !UserService.checkAddressPrimary(registerVo.getAddressPrimary()) ||
                !UserService.checkAddressSecondary(registerVo.getAddressSecondary())) {
            registerVo.setResult(RegisterResult.NORMALIZATION_FAILURE);
            return;
        }
        if (this.userMapper.selectEmailCount(registerVo.getEmail()) > 0) {
            registerVo.setResult(RegisterResult.EMAIL_DUPLICATE);
            return;
        }
        if (this.userMapper.selectNicknameCount(registerVo.getNickname()) > 0) {
            registerVo.setResult(RegisterResult.NICKNAME_DUPLICATE);
            return;
        }
        if (this.userMapper.insertUser(registerVo) == 0) {
            registerVo.setResult(RegisterResult.FAILURE);
            return;
        }
        String verificationCode = CryptoUtil.Sha512.hash(String.format("%s%s%f%f",
                registerVo.getEmail(),
                registerVo.getHashedPassword(),
                Math.random(),
                Math.random()));
        registerVo.setVerificationCode(verificationCode);
        this.userMapper.insertVerificationCode(registerVo);

        MimeMessage message = this.mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setTo(registerVo.getEmail());
        helper.setSubject("[FIN] 회원가입 인증 이메일");
        helper.setText(String.format("<a href=\"http://127.0.0.1/user/verify-email?code=%s\" target=\"_blank\">인증하기</a>", verificationCode), true);
        this.mailSender.send(message);

        registerVo.setResult(RegisterResult.SUCCESS);
    }

    public void resetPassword(PasswordResetVo passwordResetVo) {
        System.out.println("Password : " + passwordResetVo.getPassword());
        System.out.println("Key : " + passwordResetVo.getKey());
        if (!UserService.checkPassword(passwordResetVo.getPassword()) ||
                !UserService.checkPasswordResetKey(passwordResetVo.getKey())) {
            passwordResetVo.setResult(PasswordResetResult.NORMALIZATION_FAILURE);
            return;
        }
        UserEntity userEntity = this.userMapper.selectUserByPasswordReset(passwordResetVo);
        if (userEntity == null) {
            passwordResetVo.setResult(PasswordResetResult.FAILURE);
            return;
        }
        userEntity.setPassword(passwordResetVo.getHashedPassword());
        this.userMapper.updateUser(userEntity);
        this.userMapper.updatePasswordResetKeyExpired(passwordResetVo);
        passwordResetVo.setResult(PasswordResetResult.SUCCESS);
    }

    public void resetPasswordForVerification(PasswordResetVo passwordResetVo) {
        if (!UserService.checkPasswordResetKey(passwordResetVo.getKey())) {
            passwordResetVo.setResult(PasswordResetResult.NORMALIZATION_FAILURE);
            return;
        }
        if (this.userMapper.updatePasswordResetKeyVerified(passwordResetVo) == 0) {
            passwordResetVo.setResult(PasswordResetResult.KEY_INVALID);
        } else {
            passwordResetVo.setResult(PasswordResetResult.KEY_VALID);
        }
    }

    public void verifyEmail(EmailVerificationVo emailVerificationVo) {
        if (!UserService.checkEmailVerificationCode(emailVerificationVo.getCode())) {
            emailVerificationVo.setResult(EmailVerificationResult.NORMALIZATION_FAILURE);
            return;
        }
        UserEntity userEntity = this.userMapper.selectUserByEmailVerification(emailVerificationVo);
        if (userEntity == null) {
            emailVerificationVo.setResult(EmailVerificationResult.FAILURE);
            return;
        }
        userEntity.setEmailVerified(true);
        this.userMapper.updateEmailVerificationCodeExpired(emailVerificationVo);
        this.userMapper.updateUser(userEntity);
        emailVerificationVo.setResult(EmailVerificationResult.SUCCESS);
    }
}