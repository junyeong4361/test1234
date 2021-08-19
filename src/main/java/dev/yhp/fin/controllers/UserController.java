package dev.yhp.fin.controllers;

import dev.yhp.fin.entities.UserEntity;
import dev.yhp.fin.enums.user.*;
import dev.yhp.fin.models.ClientModel;
import dev.yhp.fin.services.SecurityService;
import dev.yhp.fin.services.UserService;
import dev.yhp.fin.vos.user.*;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller("dev.yhp.fin.controllers.UserController")
@RequestMapping(value = "/user")
public class UserController {
    private final SecurityService securityService;
    private final UserService userService;

    @Autowired
    public UserController(SecurityService securityService, UserService userService) {
        this.securityService = securityService;
        this.userService = userService;
    }

    @ResponseBody
    @RequestMapping(value = "/check-email",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String checkEmailPost(@RequestBody EmailCheckVo emailCheckVo) {
        this.userService.checkEmail(emailCheckVo);
        JSONObject responseJson = new JSONObject();
        responseJson.put("result", emailCheckVo.getResult().name().toLowerCase());
        return responseJson.toString();
    }

    @ResponseBody
    @RequestMapping(value = "/check-nickname",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String checkNicknamePost(
            @SessionAttribute(value = "userEntity", required = false) UserEntity userEntity,
            @RequestBody NicknameCheckVo nicknameCheckVo) {
        this.userService.checkNickname(nicknameCheckVo);
        if (userEntity != null &&
                nicknameCheckVo.getResult() == NicknameCheckResult.EXISTING &&
                nicknameCheckVo.getNickname().equals(userEntity.getNickname())) {
            nicknameCheckVo.setResult(NicknameCheckResult.YOURS);
        }
        JSONObject responseJson = new JSONObject();
        responseJson.put("result", nicknameCheckVo.getResult().name().toLowerCase());
        return responseJson.toString();
    }

    @RequestMapping(value = "/login",
            method = RequestMethod.GET,
            produces = MediaType.TEXT_HTML_VALUE)
    public String loginGet(@SessionAttribute(value = "userEntity", required = false) UserEntity userEntity) {
        if (userEntity != null) {
            return "redirect:/";
        }
        return "user/login";
    }

    @RequestMapping(value = "/login",
            method = RequestMethod.POST,
            produces = MediaType.TEXT_HTML_VALUE)
    public String loginPost(@RequestAttribute("clientModel") ClientModel clientModel,
                            @SessionAttribute(value = "userEntity", required = false) UserEntity userEntity,
                            LoginVo loginVo,
                            HttpSession session,
                            Model model) {
        if (userEntity != null) {
            return "redirect:/";
        }
        this.userService.login(loginVo);
        this.userService.putLoginLog(clientModel, loginVo);
        if (loginVo.getResult() == LoginResult.SUCCESS) {
            session.setAttribute("userEntity", loginVo.getUserEntity());
            return "redirect:/";
        } else {
            this.securityService.putIllegalLog(clientModel, loginVo);
        }
        model.addAttribute("loginResult", loginVo.getResult());
        return "user/login";
    }

    @RequestMapping(value = "/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }


    @RequestMapping(value = "/modify",
            method = RequestMethod.GET,
            produces = MediaType.TEXT_HTML_VALUE)
    public String modifyGet(@SessionAttribute(value = "userEntity", required = false) UserEntity userEntity) {
        if (userEntity == null) {
            return "redirect:/";
        }
        return "user/modify";
    }

    @RequestMapping(value = "/modify",
            method = RequestMethod.POST,
            produces = MediaType.TEXT_HTML_VALUE)
    public String modifyPost(
            @RequestAttribute("clientModel") ClientModel clientModel,
            @SessionAttribute(value = "userEntity", required = false) UserEntity userEntity,
            HttpServletRequest request,
            ModifyVo modifyVo) {
        if (userEntity == null) {
            return "redirect:/";
        }
        this.userService.modify(userEntity, modifyVo);
        if (modifyVo.getResult() != ModifyResult.SUCCESS) {
            this.securityService.putIllegalLog(clientModel, modifyVo);
        }
        request.setAttribute("modifyResult", modifyVo.getResult());
        return "user/modify";
    }

    @RequestMapping(value = "/recover",
            method = RequestMethod.GET,
            produces = MediaType.TEXT_HTML_VALUE)
    public String recoverGet(@SessionAttribute(value = "userEntity", required = false) UserEntity userEntity) {
        if (userEntity != null) {
            return "redirect:/";
        }
        return "user/recover";
    }

    @RequestMapping(value = "/recover-email",
            method = RequestMethod.POST,
            produces = MediaType.TEXT_HTML_VALUE)
    public String recoverEmailPost(
            @RequestAttribute("clientModel") ClientModel clientModel,
            @SessionAttribute(value = "userEntity", required = false) UserEntity userEntity,
            EmailRecoverVo emailRecoverVo,
            HttpServletRequest request) {
        if (userEntity != null) {
            return "redirect:/";
        }
        this.userService.recoverEmail(emailRecoverVo);
        if (emailRecoverVo.getResult() != EmailRecoverResult.SUCCESS) {
            this.securityService.putIllegalLog(clientModel, emailRecoverVo);
        }
        request.setAttribute("emailRecoverVo", emailRecoverVo);
        return "user/recoverEmail";
    }

    @RequestMapping(value = "/recover-password",
            method = RequestMethod.POST,
            produces = MediaType.TEXT_HTML_VALUE)
    public String recoverPasswordPost(
            @RequestAttribute("clientModel") ClientModel clientModel,
            @SessionAttribute(value = "userEntity", required = false) UserEntity userEntity,
            PasswordRecoverVo passwordRecoverVo,
            HttpServletRequest request) throws MessagingException {
        if (userEntity != null) {
            return "redirect:/";
        }
        this.userService.recoverPassword(passwordRecoverVo);
        if (passwordRecoverVo.getResult() != PasswordRecoverResult.SUCCESS) {
            this.securityService.putIllegalLog(clientModel, passwordRecoverVo);
        }
        request.setAttribute("passwordRecoverResult", passwordRecoverVo.getResult());
        return "user/recoverPassword";
    }

    @RequestMapping(value = "/register",
            method = RequestMethod.GET,
            produces = MediaType.TEXT_HTML_VALUE)
    public String registerGet(@SessionAttribute(value = "userEntity", required = false) UserEntity userEntity) {
        if (userEntity != null) {
            return "redirect:/";
        }
        return "user/register";
    }

    @RequestMapping(value = "/register",
            method = RequestMethod.POST,
            produces = MediaType.TEXT_HTML_VALUE)
    public String registerPost(@SessionAttribute(value = "userEntity", required = false) UserEntity userEntity,
                               RegisterVo registerVo,
                               Model model) throws MessagingException {
        if (userEntity != null) {
            return "redirect:/";
        }
        this.userService.register(registerVo);
        model.addAttribute("registerResult", registerVo.getResult());
        System.out.println(registerVo.getResult());
        return "user/register";
    }

    @RequestMapping(value = "/reset-password",
            method = RequestMethod.GET,
            produces = MediaType.TEXT_HTML_VALUE)
    public String resetPasswordGet(
            @RequestAttribute("clientModel") ClientModel clientModel,
            @SessionAttribute(value = "userEntity", required = false) UserEntity userEntity,
            PasswordResetVo passwordResetVo,
            HttpServletRequest request) {
        if (userEntity != null) {
            return "redirect:/";
        }
        this.userService.resetPasswordForVerification(passwordResetVo);
        if (passwordResetVo.getResult() != PasswordResetResult.KEY_VALID) {
            this.securityService.putIllegalLog(clientModel, passwordResetVo);
        }
        request.setAttribute("passwordResetVo", passwordResetVo);
        return "user/resetPassword";
    }

    @RequestMapping(value = "/reset-password",
            method = RequestMethod.POST,
            produces = MediaType.TEXT_HTML_VALUE)
    public String resetPasswordPost(
            @RequestAttribute("clientModel") ClientModel clientModel,
            @SessionAttribute(value = "userEntity", required = false) UserEntity userEntity,
            PasswordResetVo passwordResetVo,
            HttpServletRequest request) {
        if (userEntity != null) {
            return "redirect:/";
        }
        this.userService.resetPassword(passwordResetVo);
        if (passwordResetVo.getResult() != PasswordResetResult.SUCCESS) {
            this.securityService.putIllegalLog(clientModel, passwordResetVo);
        }
        System.out.println(passwordResetVo.getResult());
        request.setAttribute("passwordResetVo", passwordResetVo);
        return "user/resetPassword";
    }

    @RequestMapping(value = "/verify-email",
            method = RequestMethod.GET,
            produces = MediaType.TEXT_HTML_VALUE)
    public String verifyEmailGet(@RequestAttribute("clientModel") ClientModel clientModel,
                                 EmailVerificationVo emailVerificationVo,
                                 Model model) {
        this.userService.verifyEmail(emailVerificationVo);
        if (emailVerificationVo.getResult() != EmailVerificationResult.SUCCESS) {
            this.securityService.putIllegalLog(clientModel, emailVerificationVo);
        }
        model.addAttribute("emailVerificationResult", emailVerificationVo.getResult());
        return "user/verifyEmail";
    }
}