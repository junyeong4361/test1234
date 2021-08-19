const loginForm = window.document.body.querySelector('[rel="login-form"]');

const emailRegex = new RegExp('^(?=.{8,50}$)([0-9a-z_]{4,})@([0-9a-z][0-9a-z\\-]*[0-9a-z]\\.)?([0-9a-z][0-9a-z\\-]*[0-9a-z])\\.([a-z]{2,15})(\\.[a-z]{2})?$');
const passwordRegex = new RegExp('^([0-9a-zA-Z`~!@#$%^&*()\\-_=+\\[{\\]}\\\\|;:\'",<.>/?]{8,50})$');

loginForm.onsubmit = () => {
    if (!emailRegex.test(loginForm['email'].value)) {
        alert('올바른 이메일을 입력해주세요.');
        loginForm['email'].focus();
        loginForm['email'].select();
        return false;
    }
    if (!passwordRegex.test(loginForm['password'].value)) {
        alert('올바른 비밀번호를 입력해주세요.');
        loginForm['password'].focus();
        loginForm['password'].select();
        return false;
    }
    return true;
};

const searchParams = new URL(window.document.location).searchParams;
const recoveredEmail = searchParams.get('re');
if (recoveredEmail !== null) {
    loginForm['email'].value = recoveredEmail;
    loginForm['password'].focus();
}










