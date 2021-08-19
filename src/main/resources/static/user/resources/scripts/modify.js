const modifyForm = window.document.body.querySelector('[rel="modify-form"]');
const cover = window.document.body.querySelector('[rel="cover"]');
const addressContainer = window.document.body.querySelector('[rel="address-container"]');

const passwordRegex = new RegExp('^([0-9a-zA-Z`~!@#$%^&*()\\-_=+\\[{\\]}\\\\|;:\'",<.>/?]{8,50})$');
const optionalPasswordRegex = new RegExp(`^$|${passwordRegex.source}`);
const nicknameRegex = new RegExp('^([0-9a-zA-Z가-힣]{1,10})$');
const nameRegex = new RegExp('^([가-힣]{2,10})$');
const birthRegex = new RegExp('^([0-9]{6})$');
const genderRegex = new RegExp('^([1-4])$');
const contactSecondRegex = new RegExp('^([0-9]{4})$');
const contactThirdRegex = new RegExp('^([0-9]{4})$');
const addressPostalRegex = new RegExp('^([0-9]{5})$');
const addressPrimaryRegex = new RegExp('^([0-9a-zA-Z가-힣()\\-, ]{8,100})$');
const addressSecondaryRegex = new RegExp('^([0-9a-zA-Z가-힣()\\-, ]{0,100})$');

let isNicknameChecked = true;
let addressPostal = modifyForm['addressPostal'].value;
let addressPrimary = modifyForm['addressPrimary'].value;

cover.addEventListener('click', () => {
    modifyForm.querySelectorAll('input').forEach(input => {
        input.removeAttribute('disabled');
    });
    cover.style.display = 'none';
    addressContainer.style.display = 'none';
    window.document.body.style.overflowY = 'auto';
});

window.document.body.querySelector('[rel="address-find-button"]').addEventListener('click', () => {
    modifyForm.querySelectorAll('input').forEach(input => {
        input.setAttribute('disabled', 'disabled');
    });
    cover.style.display = 'block';
    addressContainer.style.display = 'block';
    window.document.body.style.overflowY = 'hidden';
    const address = new daum.Postcode({
        oncomplete: (data) => {
            modifyForm.querySelectorAll('input').forEach(input => {
                input.removeAttribute('disabled');
            });
            cover.style.display = 'none';
            addressContainer.style.display = 'none';
            window.document.body.style.overflowY = 'auto';
            addressPostal = data['zonecode'];
            addressPrimary = data['roadAddress'];
            modifyForm['addressPostal'].value = addressPostal;
            modifyForm['addressPrimary'].value = addressPrimary;
            modifyForm['addressSecondary'].focus();
            modifyForm['addressSecondary'].select();
        }
    });
    address.embed(addressContainer);
});

modifyForm['passwordCheck'].addEventListener('focusout', () => {
    const passwordMessage = window.document.body.querySelector('[rel="password-message"]');
    passwordMessage.innerText = '';
    passwordMessage.classList.remove('warning');
    if (!optionalPasswordRegex.test(modifyForm['password'].value)) {
        passwordMessage.innerText = '올바른 비밀번호를 입력해주세요.';
        passwordMessage.classList.add('warning');
        return;
    }
    if (modifyForm['password'].value !== modifyForm['passwordCheck'].value) {
        passwordMessage.innerText = '비밀번호가 일치하지 않습니다.';
        passwordMessage.classList.add('warning');
        return;
    }
    if (modifyForm['password'].value === '') {
        passwordMessage.innerText = '항목을 비어두면 비밀번호를 변경하지 않습니다.';
        passwordMessage.classList.add('good');
    }
});

modifyForm['nickname'].addEventListener('input', () => {
    const nicknameMessage = window.document.body.querySelector('[rel="nickname-message"]');
    nicknameMessage.innerText = '';
    nicknameMessage.classList.remove('good');
    nicknameMessage.classList.remove('warning');
    isNicknameChecked = false;
});
modifyForm['nickname'].addEventListener('focusout', () => {
    const nicknameMessage = window.document.body.querySelector('[rel="nickname-message"]');
    nicknameMessage.innerText = '';
    nicknameMessage.classList.remove('good');
    nicknameMessage.classList.remove('warning');
    if (!nicknameRegex.test(modifyForm['nickname'].value)) {
        nicknameMessage.innerText = '올바른 별명을 입력해주세요.';
        nicknameMessage.classList.add('warning');
        return false;
    }
    modifyForm['nickname'].setAttribute('disabled', 'disabled');

    Ajax.request({
        method: Ajax.METHOD_POST,
        url: '/user/check-nickname',
        data: {
            nickname: modifyForm['nickname'].value
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
            case 'yours':
                isNicknameChecked = true;
                nicknameMessage.innerText = '현재 회원님의 별명입니다.';
                nicknameMessage.classList.add('good');
                break;
            default:
                nicknameMessage.innerText = '별명의 유효성을 검사하지 못하였습니다.';
                nicknameMessage.classList.add('warning');
                break;
        }
        modifyForm['nickname'].removeAttribute('disabled');
    }, (status) => {
        nicknameMessage.innerText = '별명의 유효성을 검사하지 못하였습니다.';
        nicknameMessage.classList.add('warning');
        modifyForm['nickname'].removeAttribute('disabled');
    });
});

modifyForm.onsubmit = () => {
    if (!isNicknameChecked) {
        modifyForm['nickname'].focus();
        modifyForm['nickname'].select();
        alert('별명 유효성 검사에 실패하였습니다. 잠시 후 다시 시도해주세요.');
        return false;
    }
    if (!passwordRegex.test(modifyForm['currentPassword'].value)) {
        alert('올바른 현재 비밀번호를 입력해주세요.');
        modifyForm['currentPassword'].focus();
        modifyForm['currentPassword'].select();
        return false;
    }
    if (!optionalPasswordRegex.test(modifyForm['password'].value)) {
        alert('올바른 비밀번호를 입력해주세요.');
        modifyForm['password'].focus();
        modifyForm['password'].select();
        return false;
    }
    if (modifyForm['password'].value !== modifyForm['passwordCheck'].value) {
        alert('비밀번호가 일치하지 않습니다.');
        modifyForm['passwordCheck'].focus();
        modifyForm['passwordCheck'].select();
        return false;
    }
    if (!nicknameRegex.test(modifyForm['nickname'].value)) {
        alert('올바른 별명을 입력해주세요.');
        modifyForm['nickname'].focus();
        modifyForm['nickname'].select();
        return false;
    }
    if (!nameRegex.test(modifyForm['name'].value)) {
        alert('올바른 실명을 입력해주세요.');
        modifyForm['name'].focus();
        modifyForm['name'].select();
        return false;
    }
    if (!birthRegex.test(modifyForm['birth'].value)) {
        alert('올바른 생년월일을 입력해주세요.');
        modifyForm['birth'].focus();
        modifyForm['birth'].select();
        return false;
    }
    if (!genderRegex.test(modifyForm['gender'].value)) {
        alert('올바른 성별을 입력해주세요.');
        modifyForm['gender'].focus();
        modifyForm['gender'].select();
        return false;
    }
    if (!contactSecondRegex.test(modifyForm['contactSecond'].value)) {
        alert('올바른 연락처를 입력해주세요.');
        modifyForm['contactSecond'].focus();
        modifyForm['contactSecond'].select();
        return false;
    }
    if (!contactThirdRegex.test(modifyForm['contactThird'].value)) {
        alert('올바른 연락처를 입력해주세요.');
        modifyForm['contactThird'].focus();
        modifyForm['contactThird'].select();
        return false;
    }
    if (!addressPostalRegex.test(modifyForm['addressPostal'].value) ||
        !addressPrimaryRegex.test(modifyForm['addressPrimary'].value)) {
        alert('주소 찾기를 통해 주소를 선택해주세요.');
        return false;
    }
    if (!addressSecondaryRegex.test(modifyForm['addressSecondary'].value)) {
        alert('상세 주소가 올바르지 않습니다.');
        modifyForm['addressSecondary'].focus();
        modifyForm['addressSecondary'].select();
        return false;
    }
    modifyForm['addressPostal'].value = addressPostal;
    modifyForm['addressPrimary'].value = addressPrimary;
    return true;
};