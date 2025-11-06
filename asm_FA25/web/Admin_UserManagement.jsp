<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="vi">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width,initial-scale=1" />
        <title>Admin</title>
        <link rel="stylesheet" href="style.css" />
        <style>
            /* Import Noto Sans from Google Fonts */
            @import url('https://fonts.googleapis.com/css2?family=Be+Vietnam+Pro:wght@300;400;500;600;700;800&display=swap&subset=vietnamese');

            * {
                margin: 0;
                padding: 0;
                box-sizing: border-box;
                font-family: 'Be Vietnam Pro', sans-serif;
            }

            :root{
                --sidebar-width: 220px;
            }

            body {
                display: flex;
                justify-content: flex-start;
                align-items: flex-start;
                min-height: 100vh;
                background-size: cover;
                background-position: center;
                padding-left: var(--sidebar-width);
            }

            header {
                background: url(Background/Bg.png) no-repeat;
                background-size: cover;
                position: fixed;
                top: 0;
                left: 0;
                width: var(--sidebar-width);
                height: 100vh;
                padding: 28px 16px;
                display: flex;
                flex-direction: column;
                align-items: center;
                justify-content: flex-start;
                gap: 18px;
                z-index: 99;
            }



            /* Logo image sizing */
            .logo {
                height:64px; /* actual visible logo height */
                width:auto;
                display:block;
                object-fit:contain;
            }
            .logo-wrap{
                display:flex;
                align-items:center;
                justify-content:center; /* center the logo horizontally in the sidebar */
                width:100%;
                overflow:visible;
            }
            .logo-wrap img{
                /* keep the logo element size but visually scale the image larger
                   so the wrapper (.logo or .logo-wrap) dimensions don't change */
                transform: scale(2.4);
                transform-origin: center center;
                height:64px;
                width:auto;
                display:block;
                will-change: transform;
            }

            .navigation{
                width:100%;
                display:flex;
                flex-direction:column;
                gap:6px;
                margin-top:8px;
                flex: 1 1 auto; /* let nav grow so logout can be pushed to bottom */
                align-items: stretch;
            }

            .navigation a{
                position: relative;
                font-size: 1.05em;
                color: #fff;
                text-decoration: none;
                font-weight: 600;
                padding: 10px 12px;
                border-radius: 12px;
                transition: background 0.5s ease, transform 0.25s ease, box-shadow 0.5s ease;
            }

            .navigation a:hover{
                background: rgba(255,255,255,0.06);
                box-shadow: 0 8px 20px rgba(0,0,0,0.25);
                transform: translateX(4px);
            }

            .navigation .btnLogin-popup{
                width: 100%;
                height: 46px;
                background-color: transparent;
                border: 2px solid #fff;
                outline: none;
                border-radius: 8px;
                cursor: pointer;
                font-size: 1.05em;
                color: #fff;
                font-weight: 600;
                margin-top: auto; /* push to bottom */
                transition: background .18s ease, color .18s ease;
            }

            .navigation a::after{
                display:none;
            }
            .navigation .btnLogin-popup:hover{
                background-color:#fff;
                color:#162928;
            }
            .content {
                margin-left: 270px;
                padding: 20px;
                width: 100%;
            }

        </style>
    </head>
    <body>
        <header>
            <nav class="navigation">
                <a href="adminUserManagement">Quản lý nhân sự</a>
                <a href="adminRequestManagement">Quản lí đơn</a>
                <a href="adminAgenda">Theo dõi nghỉ phép</a>
                <a href="userSettings">Cài đặt</a>                
                <button class="btnLogin-popup">
                    <a href="logout" class="logout-btn">Đăng xuất</a>
                </button>
            </nav>
        </header>
    </body>
    <div class="content">
        <div class="container-fluid" style="padding-top: 20px;">
            <h2 class="mb-4">Quản lý Nhân sự</h2>

            <a href="adminUserManagement?action=add" class="btn btn-primary mb-3">Thêm nhân viên mới</a>

            <table id="userTable" class="table table-striped table-bordered" style="width:100%">
                <thead>
                    <tr>
                        <th>User ID</th>
                        <th>Username</th>
                        <th>Mật khẩu</th>
                        <th>Họ và Tên</th>
                        <th>Email</th>
                        <th>Số điện thoại</th>
                        <th>Phòng ban</th>
                        <th>Chức vụ</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="user" items="${ulist}">
                        <tr>
                            <td>${user.userID}</td>
                            <td>${user.username}</td>
                            <td>${user.password}</td>
                            <td>${user.fullname}</td>
                            <td>${user.email}</td>
                            <td>${user.phone}</td>

                            <td>
                                <c:forEach var="dept" items="${dlist}">
                                    <c:if test="${dept.deptID == user.deptID}">
                                        ${dept.deptName}
                                    </c:if>
                                </c:forEach>
                            </td>

                            <td>
                                <c:forEach var="r" items="${rlist}">
                                    <c:if test="${r.roleID == user.roleID}">
                                        ${r.roleName}
                                    </c:if>
                                </c:forEach>
                            </td>

                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.datatables.net/1.13.4/js/jquery.dataTables.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        $(document).ready(function () {
            $('#userTable').DataTable();
        });
        // Hàm xử lý xóa người dùng với SweetAlert
        $(document).ready(function () {
            $(document).on('click', '.del', function () {
                let userId = $(this).val(); // Lấy userId từ button
                console.log("User ID cần xóa:", userId); // Kiểm tra xem có lấy đúng userId không

                if (!userId) {
                    alert("Lỗi: Không lấy được userId.");
                    return;
                }

                swal({
                    title: "Cảnh báo!",
                    text: "Bạn có chắc chắn muốn xóa người dùng này?",
                    icon: "warning",
                    buttons: ["Hủy bỏ", "Đồng ý"],
                    dangerMode: true,
                }).then((confirmDelete) => {
                    if (confirmDelete) {
                        swal("Đã xóa thành công!", {
                            icon: "success",
                            timer: 1500,
                            buttons: false
                        }).then(() => {
                            window.location.href = "adminUserManagement?action=delete&userId=" + userId;
                        });
                    }
                });
            });
        });

    </script>
</html>
