const registerForm = window.document.body.querySelector('[rel="register-form"]');
const cover = window.document.body.querySelector('[rel="cover"]');
const addressContainer = window.document.body.querySelector('[rel="address-container"]');

const emailRegex = new RegExp('^(?=.{8,50}$)([0-9a-z_]{4,})@([0-9a-z][0-9a-z\\-]*[0-9a-z]\\.)?([0-9a-z][0-9a-z\\-]*[0-9a-z])\\.([a-z]{2,15})(\\.[a-z]{2})?$');
const passwordRegex = new RegExp('^([0-9a-zA-Z`~!@#$%^&*()\\-_=+\\[{\\]}\\\\|;:\'",<.>/?]{8,50})$');
const nicknameRegex = new RegExp('^([0-9a-zA-Z가-힣]{1,10})$');
const nameRegex = new RegExp('^([가-힣]{2,10})$');
const birthRegex = new RegExp('^([0-9]{6})$');
const genderRegex = new RegExp('^([1-4])$');
const contactSecondRegex = new RegExp('^([0-9]{4})$');
const contactThirdRegex = new RegExp('^([0-9]{4})$');
const addressPostalRegex = new RegExp('^([0-9]{5})$');
const addressPrimaryRegex = new RegExp('^([0-9a-zA-Z가-힣()\\-, ]{8,100})$');
const addressSecondaryRegex = new RegExp('^([0-9a-zA-Z가-힣()\\-, ]{0,100})$');

let isEmailChecked = false;
let isNicknameChecked = false;
let addressPostal;
let addressPrimary;

cover.addEventListener('click', () => {
    registerForm.querySelectorAll('input').forEach(input => {
        input.removeAttribute('disabled');
    });
    cover.style.display = 'none';
    addressContainer.style.display = 'none';
    window.document.body.style.overflowY = 'auto';
});

window.document.body.querySelector('[rel="address-find-button"]').addEventListener('click', () => {
    registerForm.querySelectorAll('input').forEach(input => {
        input.setAttribute('disabled', 'disabled');
    });
    cover.style.display = 'block';
    addressContainer.style.display = 'block';
    window.document.body.style.overflowY = 'hidden';
    const address = new daum.Postcode({
        oncomplete: (data) => {
            registerForm.querySelectorAll('input').forEach(input => {
                input.removeAttribute('disabled');
            });
            cover.style.display = 'none';
            addressContainer.style.display = 'none';
            window.document.body.style.overflowY = 'auto';
            addressPostal = data['zonecode'];
            addressPrimary = data['roadAddress'];
            registerForm['addressPostal'].value = addressPostal;
            registerForm['addressPrimary'].value = addressPrimary;
            registerForm['addressSecondary'].focus();
            registerForm['addressSecondary'].select();
        }
    });
    address.embed(addressContainer);
});

registerForm['email'].addEventListener('input', () => {
    const emailMessage = window.document.body.querySelector('[rel="email-message"]');
    emailMessage.innerText = '';
    emailMessage.classList.remove('good');
    emailMessage.classList.remove('warning');
    isEmailChecked = false;
});
registerForm['email'].addEventListener('focusout', () => {
    const emailMessage = window.document.body.querySelector('[rel="email-message"]');
    emailMessage.innerText = '';
    emailMessage.classList.remove('good');
    emailMessage.classList.remove('warning');
    if (!emailRegex.test(registerForm['email'].value)) {
        emailMessage.innerText = '올바른 이메일을 입력해주세요.';
        emailMessage.classList.add('warning');
        return false;
    }
    registerForm['email'].setAttribute('disabled', 'disabled');

    Ajax.request({
        method: Ajax.METHOD_POST,
        url: '/user/check-email',
        data: {
            email: registerForm['email'].value
        }
    }, (status, responseText) => {
        const responseJson = JSON.parse(responseText);
        switch (responseJson['result']) {
            case 'existing':
                emailMessage.innerText = '이미 사용 중인 이메일입니다.';
                emailMessage.classList.add('warning');
                break;
            case 'non_existing':
                isEmailChecked = true;
                emailMessage.innerText = '사용 가능한 이메일입니다.';
                emailMessage.classList.add('good');
                break;
            default:
                emailMessage.innerText = '이메일의 유효성을 검사하지 못하였습니다.';
                emailMessage.classList.add('warning');
                break;
        }
        registerForm['email'].removeAttribute('disabled');
    }, (status) => {
        emailMessage.innerText = '이메일의 유효성을 검사하지 못하였습니다.';
        emailMessage.classList.add('warning');
        registerForm['email'].removeAttribute('disabled');
    });
});

registerForm['passwordCheck'].addEventListener('focusout', () => {
    const passwordMessage = window.document.body.querySelector('[rel="password-message"]');
    passwordMessage.innerText = '';
    passwordMessage.classList.remove('warning');
    if (!passwordRegex.test(registerForm['password'].value)) {
        passwordMessage.innerText = '올바른 비밀번호를 입력해주세요.';
        passwordMessage.classList.add('warning');
        return;
    }
    if (registerForm['password'].value !== registerForm['passwordCheck'].value) {
        passwordMessage.innerText = '비밀번호가 일치하지 않습니다.';
        passwordMessage.classList.add('warning');
    }
});

registerForm['nickname'].addEventListener('input', () => {
    const nicknameMessage = window.document.body.querySelector('[rel="nickname-message"]');
    nicknameMessage.innerText = '';
    nicknameMessage.classList.remove('good');
    nicknameMessage.classList.remove('warning');
    isNicknameChecked = false;
});
registerForm['nickname'].addEventListener('focusout', () => {
    const nicknameMessage = window.document.body.querySelector('[rel="nickname-message"]');
    nicknameMessage.innerText = '';
    nicknameMessage.classList.remove('good');
    nicknameMessage.classList.remove('warning');
    if (!nicknameRegex.test(registerForm['nickname'].value)) {
        nicknameMessage.innerText = '올바른 별명을 입력해주세요.';
        nicknameMessage.classList.add('warning');
        return false;
    }
    registerForm['nickname'].setAttribute('disabled', 'disabled');

    Ajax.request({
        method: Ajax.METHOD_POST,
        url: '/user/check-nickname',
        data: {
            nickname: registerForm['nickname'].value
        }
    }, (status, responseText) => {
        const responseJson = JSON.parse(responseText);
        switch (responseJson['result']) {
            case 'existing':
                nicknameMessage.innerText = '이미 사용 중인 별명입니다.';
                nicknameMessage.classList.add('warning');
                break;
            case 'non_existing':
                isNicknameChecked = true;
                nicknameMessage.innerText = '사용 가능한 별명입니다.';
                nicknameMessage.classList.add('good');
                break;
            default:
                nicknameMessage.innerText = '별명의 유효성을 검사하지 못하였습니다.';
                nicknameMessage.classList.add('warning');
                break;
        }
        registerForm['nickname'].removeAttribute('disabled');
    }, (status) => {
        nicknameMessage.innerText = '별명의 유효성을 검사하지 못하였습니다.';
        nicknameMessage.classList.add('warning');
        registerForm['nickname'].removeAttribute('disabled');
    });
});

registerForm.onsubmit = () => {
    if (!isEmailChecked) {
        registerForm['email'].focus();
        registerForm['email'].select();
        alert('이메일 유효성 검사에 실패하였습니다. 잠시 후 다시 시도해주세요.');
        return false;
    }
    if (!isNicknameChecked) {
        registerForm['nickname'].focus();
        registerForm['nickname'].select();
        alert('별명 유효성 검사에 실패하였습니다. 잠시 후 다시 시도해주세요.');
        return false;
    }
    if (!emailRegex.test(registerForm['email'].value)) {
        alert('올바른 이메일을 입력해주세요.');
        registerForm['email'].focus();
        registerForm['email'].select();
        return false;
    }
    if (!passwordRegex.test(registerForm['password'].value)) {
        alert('올바른 비밀번호를 입력해주세요.');
        registerForm['password'].focus();
        registerForm['password'].select();
        return false;
    }
    if (registerForm['password'].value !== registerForm['passwordCheck'].value) {
        alert('비밀번호가 일치하지 않습니다.');
        registerForm['passwordCheck'].focus();
        registerForm['passwordCheck'].select();
        return false;
    }
    if (!nicknameRegex.test(registerForm['nickname'].value)) {
        alert('올바른 별명을 입력해주세요.');
        registerForm['nickname'].focus();
        registerForm['nickname'].select();
        return false;
    }
    if (!nameRegex.test(registerForm['name'].value)) {
        alert('올바른 실명을 입력해주세요.');
        registerForm['name'].focus();
        registerForm['name'].select();
        return false;
    }
    if (!birthRegex.test(registerForm['birth'].value)) {
        alert('올바른 생년월일을 입력해주세요.');
        registerForm['birth'].focus();
        registerForm['birth'].select();
        return false;
    }
    if (!genderRegex.test(registerForm['gender'].value)) {
        alert('올바른 성별을 입력해주세요.');
        registerForm['gender'].focus();
        registerForm['gender'].select();
        return false;
    }
    if (!contactSecondRegex.test(registerForm['contactSecond'].value)) {
        alert('올바른 연락처를 입력해주세요.');
        registerForm['contactSecond'].focus();
        registerForm['contactSecond'].select();
        return false;
    }
    if (!contactThirdRegex.test(registerForm['contactThird'].value)) {
        alert('올바른 연락처를 입력해주세요.');
        registerForm['contactThird'].focus();
        registerForm['contactThird'].select();
        return false;
    }
    if (!addressPostalRegex.test(registerForm['addressPostal'].value) ||
        !addressPrimaryRegex.test(registerForm['addressPrimary'].value)) {
        alert('주소 찾기를 통해 주소를 선택해주세요.');
        return false;
    }
    if (!addressSecondaryRegex.test(registerForm['addressSecondary'].value)) {
        alert('상세 주소가 올바르지 않습니다.');
        registerForm['addressSecondary'].focus();
        registerForm['addressSecondary'].select();
        return false;
    }
    if (!registerForm['agreePrivacy'].checked) {
        alert('개인정보처리방침을 읽고 동의해주세요.');
        registerForm['agreePrivacy'].focus();
        return false;
    }
    if (!registerForm['agreeTerm'].checked) {
        alert('이용약관을 읽고 동의해주세요.');
        registerForm['agreeTerm'].focus();
        return false;
    }
    registerForm['addressPostal'].value = addressPostal;
    registerForm['addressPrimary'].value = addressPrimary;
    return true;
};