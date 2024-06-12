var isDup = true;
var isMailDone = false;
let regForm = document.getElementById('form1');
let emailInputBox = document.getElementById('email');
let emailCheckBtn = document.getElementById('checkEmailBtn');
let checkEmailNum = document.getElementById('checkEmailNum');
let checkEmailNumBtn = document.getElementById('checkEmailNumBtn');
const idCheck = /^[A-a-zZ0-9_.]+$/;
function checkId() {
    isDup = true;
    if(regForm.id.value == "") {
        alert("아이디를 입력해주세요.");
        regForm.id.focus();
        return;
    }
    let id = document.getElementById('id').value;
    $.ajax({
        type: 'POST',
        url: '/checkId',
        contentType : 'text/plain',
        dataType : 'text',
        data : id,
        success : function(result) {
            if(result == "DUPLICATION") {
                alert("중복된 아이디입니다.");
                isDup = true;
            } else {
                alert("사용 가능한 아이디입니다.");
                isDup = false;
            }
        },
        err : function(request, status, error) {
            console.log(request, status, error);
        },
    });
}

function submitReg() {

    if(regForm.id.value == "") {
        alert("아이디를 입력해주세요.");
        regForm.id.focus();
        return;
    }

    if(regForm.id.value.includes(" ")) {
        alert("아이디의 공백을 확인해주세요.");
        regForm.id.focus();
        return;
    }

    if(regForm.id.value.length < 6 || regForm.id.value.length > 12) {
        alert("아이디를 6 ~ 12자로 입력해주세요.");
        regForm.id.focus();
        return;
    }

    if(idCheck.test(regForm.id.value)) {
        alert("아이디에는 영문, 언더바, 숫자, 마침표만 사용할 수 있습니다.");
        regForm.id.focus();
        return;
    }

    if(isDup) {
        alert("아이디 중복 확인을 해주세요.");
        regForm.distinctBtn.focus();
        return;
    }

    if(regForm.name.value == "") {
        alert("닉네임을 입력해주세요.");
        regForm.name.focus();
        return;
    }

    if(regForm.name.value.length > 16 || regForm.name.value.length< 3) {
        alert("닉네임을 3 ~ 16자로 입력해주세요.");
       regForm.name.focus();
       return;
    }

    if(isMailDone == false) {
        alert("메일 인증을 해주세요.");
        regForm.checkEmailNum.focus();
        return;
    }

    if(regForm.password.value == "") {
        alert("비밀번호를 입력해주세요.");
        regForm.password.focus();
        return;
    }

    if(regForm.password.value.includes(" ")) {
        alert("비밀번호에는 공백이 들어갈 수 없습니다.");
        regForm.password.focus();
        return;
    }

    if(regForm.password.value.length < 8) {
        alert("비밀번호 길이 : 8자 이상");
        regForm.password.focus();
        return;
    }

    if(regForm.password.value != regForm.confirm_password.value) {
        alert("비밀번호가 알맞는지 확인해주세요.");
        regForm.confirm_password.focus();
        return;
    }

    isDup = true;

    emailInputBox.disabled = false;

    try {
        regForm.submit();
        alert("회원가입 완료");
    } catch (e) {
        alert("회원가입 실패");
    }

    emailInputBox.disabled = true;
}

function sendMail() {
    let emailValue = document.getElementById('email').value;
    if(emailValue == "") {
        alert("메일을 입력해주세요.");
        return;
    }

    isMailDone = false;

    emailInputBox.disabled = true;

    let mail = document.getElementById('email').value;
    $.ajax({
            type: 'POST',
            url: '/mailSend',
            contentType : 'application/json',
            dataType : 'text',
            data : mail,
            success : function(result) {
                if(result == "Success") {
                    alert("메일전송 완료");
                    checkEmailNum.disabled = false;
                    emailInputBox.disabled = true;
                    emailCheckBtn.disabled = true;
                    checkEmailNumBtn.disabled = false;
                } else if(result == "Fail") {
                    alert("메일 전송 실패. 다시 입력해주세요");
                    emailInputBox.disabled = false;
                }
            },
            error : function(request, status, error) {
                alert("메일 전송 실패. 다시 입력해주세요");
            },
        });
}

function mailCheck() {
    let resultNum = (document.getElementById("checkEmailNum").value).toString();

    $.ajax({
            type: 'POST',
            url: '/mailCheck',
            contentType : 'application/json',
            dataType : 'text',
            data : resultNum,
            success : function(result) {
                if(result == "true") {
                    alert("인증 완료");
                    checkEmailNumBtn.disabled = true;
                    checkEmailNum.disabled = true;
                    isMailDone = true;
                } else if(result == "false") {
                    alert("인증 실패");
                    isMailDone = false;
                }
            },
            error : function(request, status, error) {
                alert("메일 전송 실패. 다시 입력해주세요");
            },
        });
}

function togglePassword() {
    var passwordInput = document.getElementById("password");
    var eyeIcon = document.getElementById("eye-icon");

    if (passwordInput.type === "password") {
      passwordInput.type = "text";
      eyeIcon.className = "fa fa-eye-slash";
    } else {
      passwordInput.type = "password";
      eyeIcon.className = "fa fa-eye";
    }
}