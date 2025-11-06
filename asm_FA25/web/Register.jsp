<!doctype html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="vi">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width,initial-scale=1" />
        <title>Đăng kí</title>
        <link rel="stylesheet" href="Register.css" />
        <style>
            @import url('https://fonts.googleapis.com/css2?family=Be+Vietnam+Pro:wght@300;400;500;600;700;800&display=swap&subset=vietnamese');
            body {
                font-family: 'Be Vietnam Pro', sans-serif;
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
                height: 820px;
                background: transparent;
                border: 2px solid rgba(255, 255, 255, 0.5);
                border-radius: 20px;
                backdrop-filter: blur(20px);
                box-shadow: 0 0 30px rgba(0, 0, 0, 0.5);
                display: flex;
                justify-content: center;
                align-items: center;
            }
            .wrapper .form-box.register {
                width: 80%;
                padding: 40px;
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
    </head>

    <body>
        <div class="wrapper">
            <div class="form-box register"> 
                <h2>Đăng kí</h2>
                <form action="register" method="POST">
                    <div class="input-box">
                        <span class="icon"><ion-icon name="person-circle"></ion-icon></span>
                        <input type="username" name="username" required />
                        <label for="">Username</label>
                    </div>
                    <div class="input-box">
                        <span class="icon"><ion-icon name="lock-closed"></ion-icon></span>
                        <input type="password" name="password" required />
                        <label for="">Mật khẩu</label>
                    </div>
                    <div class="input-box">
                        <span class="icon"><ion-icon name="person-add"></ion-icon></span>
                        <input type="fullname" name="fullname" required />
                        <label for="">Họ và tên</label>
                    </div>
                    <div class="input-box">
                        <span class="icon"><ion-icon name="mail"></ion-icon></span>
                        <input type="email" name="email" required />
                        <label for="">Email</label>
                    </div>
                    <div class="input-box">
                        <span class="icon"><ion-icon name="call"></ion-icon></span>
                        <input type="phone" name="phone" required />
                        <label for="">Số điện thoại</label>
                    </div>
                    <div class="input-box">
                        <span class="icon"><ion-icon name="briefcase"></ion-icon></span>
                        <select name="deptID" id="department" required>
                            <option value="" disabled selected>Chọn phòng ban</option>
                            <option value="1">1. IT</option>
                            <option value="2">2. QA</option>
                            <option value="3">3. Sale</option>
                        </select>
                    </div>
                    <div class="input-box">
                        <span class="icon"><ion-icon name="people"></ion-icon></span>
                        <select name="roleID" id="role" required>
                            <option value="" disabled selected>Chọn Role</option>
                            <option value="0">0. Admin</option>
                            <option value="1">1. Department Manager</option>
                            <option value="2">2. Group Leader</option>
                            <option value="3">3. Employee</option>
                        </select>
                    </div>
                    <c:if test="${not empty error}">
                        <p style="color: red; text-align: center; margin-bottom: 10px;">${error}</p>
                    </c:if>
                    <button type="submit" class="btnRegister">Đăng ký</button>
                    <div class="login-register">
                        <p>Đã có tài khoản ?
                            <a href="login" class="login-link">Đăng nhập</a>
                        </p>
                    </div>
                </form>
            </div>

        </div>
        <script src="script.js" defer></script>
        <script type="module" src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.esm.js"></script>
        <script nomodule src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.js"></script>
    </body>
</html>