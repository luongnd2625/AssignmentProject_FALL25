<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!-- ...existing code... -->
<!doctype html>
<html lang="vi">
<head>
  <meta charset="utf-8" />
  <meta name="viewport" content="width=device-width,initial-scale=1" />
  <title>Login</title>
  <link rel="stylesheet" href="style.css" />
</head>

<body>
<div class="wrapper">
    <div class="form-box login"> 
        <h2>Login</h2>
        <form action="#">
            <div class="input-box">
                <span class="icon"><ion-icon name="person-circle"></ion-icon></span>
                <input type="username" required />
                <label for="">Tên đăng nhập</label>
            </div>
            <div class="input-box">
                <span class="icon"><ion-icon name="lock-closed"></ion-icon></span>
                <input type="password" required />
                <label for="">Mật khẩu</label>

            </div>
            <div class="remember-forgot">
                <label><input type="checkbox" />Lưu đăng nhập</label>
                <a href="#">Quên mật khẩu?</a>
            </div>
            <button type="submit" class="btnLogin">Đăng nhập</button>
            <div class="login-register">
                <p>Chưa có tài khoản 
                    <a href="#" class="register-link">Đăng kí</a>
                </p>
            </div>
        </form>
    </div>

    <div class="form-box register"> 
        <h2>Register</h2>
        
            <div class="input-box">
                <span class="icon"><ion-icon name="person-circle"></ion-icon></span>
                <input type="username" required />
                <label for="">Tên đăng nhập</label>
            </div>
            <div class="input-box">
                <span class="icon"><ion-icon name="lock-closed"></ion-icon></span>
                <input type="password" required />
                <label for="">Mật khẩu</label>
            </div>
            <div class="input-box">
                <span class="icon"><ion-icon name="person-add"></ion-icon></span>
                <input type="fullname" required />
                <label for="">Họ và tên</label>
            </div>
            <div class="input-box">
                <span class="icon"><ion-icon name="mail"></ion-icon></span>
                <input type="email" required />
                <label for="">Email</label>
            </div>
            <div class="input-box">
                <span class="icon"><ion-icon name="call"></ion-icon></span>
                <input type="phone" required />
                <label for="">Số điện thoại</label>
            </div>
            <div class="input-box">
                <span class="icon"><ion-icon name="briefcase"></ion-icon></span>
                <select name="department" id="department" required>
                    <option value="" disabled selected>Chọn phòng ban</option>
                    <option value="IT">1. IT</option>
                    <option value="QA">2. QA</option>
                    <option value="Sale">3. Sale</option>
                </select>
            </div>
            <div class="input-box">
                <span class="icon"><ion-icon name="people"></ion-icon></span>
                <select name="role" id="role" required>
                    <option value="" disabled selected>Chọn quyền hạn</option>
                    <option value="Admin">0. Admin</option>
                    <option value="Department Manager">1. Department Manager</option>
                    <option value="Group Leader">2. Group Leader</option>
                    <option value="Employee">3. Employee</option>
                </select>
            </div>
            <div class="remember-forgot">
                <label><input type="checkbox" />Đồng ý với điều khoản sử dụng</label>
                
            </div>
            <button type="submit" class="btnRegister">Đăng kí</button>
            <div class="login-register">
                <p>Bạn đã có tài khoản ?
                    <a href="#" class="login-link">Đăng nhập</a>
                </p>
            </div>
        
    </div>

</div>
    <script>
        const wrapper = document.querySelector(".wrapper");
        const loginLink = document.querySelector(".login-link");
        const registerLink = document.querySelector(".register-link");

        registerLink.addEventListener("click", () => {
        wrapper.classList.add("active");
        });

        loginLink.addEventListener("click", () => {
            wrapper.classList.remove("active");
        });
    </script>
    <script type="module" src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.esm.js"></script>
    <script nomodule src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.js"></script>
</body>
</html>

<style>
    @import url('https://fonts.googleapis.com/css2?family=Noto+Sans:wght@300;400;500;600;700;800&display=swap');

:root {
    --font-sans: 'Poppins', system-ui, -apple-system, 'Segoe UI', Roboto, 'Helvetica Neue', Arial, sans-serif;
}

html {
    font-family: var(--font-sans);
}
body {
    display: flex;
    justify-content: center;
    align-items: center;
    min-height: 100vh;
    background: url(Background/Bg.png) no-repeat;
    background-size: cover;
    background-position: center;
}

.wrapper {
    position: relative;
    width: 400px;
    height: 440px;
    background: transparent;
    border: 2px solid rgba(255, 255, 255, 0.5);
    border-radius: 20px;
    backdrop-filter: blur(20px);
    box-shadow: 0 0 30px rgba(0, 0, 0, 0.5);
    display: flex;
    justify-content: center;
    align-items: center;
    overflow: hidden;
    transition: 0.5s ease;
}
.wrapper.active {
    height: 820px;
    
}
.wrapper .form-box.login {
    width: 100%;
    padding: 40px;
}

.wrapper .form-box.register {
    width: 80%;
    padding: 40px;
}

.wrapper .form-box.login {
    transition: transform 0.5s ease;
    transform: translateX(0px);
}

.wrapper.active .form-box.login {
    transition: none;
    transform: translateX(-500px);

}

.wrapper .form-box.register {
    position: absolute;
    transform: translateX(500px);
}

.wrapper.active .form-box.register {
    transition: transform 0.5s ease;
    transform: translateX(0px);
}


.form-box h2 {
    font-size: 2em;
    color: #162938;
    text-align: center;

}

.input-box {
    position: relative;
    width: 100%;
    height: 50px;
    border-bottom: 2px solid #162938;
    margin: 30px 0;
}

.input-box label {
    position: absolute;
    top: 50%;
    left: 5px;
    transform: translateY(-50%);
    font-size: 1em;
    color: #162938;
    font-weight: 500;
    pointer-events: none;
    transition: .5s;
}

.input-box input:focus ~ label,
.input-box input:valid ~ label {
    top: -5px;
    left: 5px;
    font-size: 0.8em;
    color: #162938;
}

.input-box input,
.input-box select {
    width: 100%;
    height: 100%;
    background: transparent;
    border: none;
    outline: none;
    font-size: 1em;
    color: #162938;
    font-weight: 600;
    padding: 0 35px 0 5px;
    cursor: pointer;
}

.input-box select {
    appearance: none;
    -webkit-appearance: none;
    -moz-appearance: none;
}

.input-box select option {
    background: rgba(255, 255, 255, 0.95);
    color: #162938;
    padding: 10px;
    border-radius: 3px;
    margin: 2px;
}

.input-box .icon {
    position: absolute;
    right: 8px;
    font-size: 1.2em;
    color: #162938;
    line-height: 57px;
    pointer-events: none;
}

.remember-forgot {
    font-size: 0.9em;
    color: #162938 ;
    font-weight: 500;
    margin: -15px 0 15px;
    display: flex;
    justify-content: space-between;
}

.remember-forgot label input {
    accent-color: #162938;
    margin-right: 3px;
}

.remember-forgot a {
    color: #162938;
    text-decoration: none;
}

.remember-forgot a:hover {
    text-decoration: underline;

}

.btnLogin {
    width: 100%;
    height: 45px;
    background: #162938;
    border: none;
    outline: none;
    border-radius: 10px;
    color: #ffffff;
    font-size: 1em;
    font-weight: 600;
    cursor: pointer;
    transition: .3s;
}
.btnRegister {
    width: 100%;
    height: 45px;
    background: #162938;
    border: none;
    outline: none;
    border-radius: 10px;
    color: #ffffff;
    font-size: 1em;
    font-weight: 600;
    cursor: pointer;
    transition: .3s;
}
</style>