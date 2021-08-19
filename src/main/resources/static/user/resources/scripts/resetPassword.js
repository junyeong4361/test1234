const resetForm = window.document.body.querySelector('[rel="reset-form"]');
const passwordRegex = new RegExp('^([0-9a-zA-Z`~!@#$%^&*()\\-_=+\\[{\\]}\\\\|;:\'",<.>/?]{8,50})$');

resetForm.onsubmit = () => {
    if (!passwordRegex.test(resetForm['password'].value)) {
        alert('올바른 비밀번호를 입력해주세요.');
        resetForm['password'].focus();
        resetForm['password'].select();
        return false;
    }
    if (resetForm['password'].value !== resetForm['passwordCheck'].value) {
        alert('재입력한 비밀번호가 일치하지 않습니다.');
        resetForm['passwordCheck'].focus();
        resetForm['passwordCheck'].select();
        return false;
    }
    return true;
};