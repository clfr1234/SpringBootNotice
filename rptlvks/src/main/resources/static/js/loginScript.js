const idCheck = /^[A-a-zZ0-9_.]+$/;

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