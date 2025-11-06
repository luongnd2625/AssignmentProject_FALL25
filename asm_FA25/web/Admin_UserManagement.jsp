<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="vi">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width,initial-scale=1" />
        <title>Admin - Quản lý Nhân sự</title>
        
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        
        <link href="https://cdn.datatables.net/2.0.8/css/dataTables.bootstrap5.min.css" rel="stylesheet">

        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css" />

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

            /* --- CSS CHO SIDEBAR CỦA BẠN (GIỮ NGUYÊN) --- */
            body {
                display: flex;
                justify-content: flex-start;
                align-items: flex-start;
                min-height: 100vh;
                /* Nền xám nhạt cho khu vực nội dung */
                background-color: #f8f9fa; 
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

            .logo { height:64px; width:auto; display:block; object-fit:contain; }
            .logo-wrap{ display:flex; align-items:center; justify-content:center; width:100%; overflow:visible; }
            .logo-wrap img{ transform: scale(2.4); transform-origin: center center; height:64px; width:auto; display:block; will-change: transform; }
            .navigation{ width:100%; display:flex; flex-direction:column; gap:6px; margin-top:8px; flex: 1 1 auto; align-items: stretch; }
            .navigation a{ position: relative; font-size: 1.05em; color: #fff; text-decoration: none; font-weight: 600; padding: 10px 12px; border-radius: 12px; transition: background 0.5s ease, transform 0.25s ease, box-shadow 0.5s ease; }
            .navigation a:hover{ background: rgba(255,255,255,0.06); box-shadow: 0 8px 20px rgba(0,0,0,0.25); transform: translateX(4px); }
            .navigation .btnLogin-popup{ width: 100%; height: 46px; background-color: transparent; border: 2px solid #fff; outline: none; border-radius: 8px; cursor: pointer; font-size: 1.05em; color: #fff; font-weight: 600; margin-top: auto; transition: background .18s ease, color .18s ease; }
            .navigation a::after{ display:none; }
            .navigation .btnLogin-popup:hover{ background-color:#fff; color:#162928; }
            
            .content {
                width: 100%;
                padding: 30px;
            }
            
            #userTable thead th {
                background-color: #343a40;
                color: #ffffff;
            }

            
            .password-cell {
                -webkit-text-security: disc;
                text-security: disc;
            }
        </style>
    </head>
    
    <body>
        <header>
            <nav class="navigation">
                <a href="adminUserManagement">
                    <i class="fa-solid fa-users-cog me-2"></i>Quản lý nhân sự
                </a>
                <a href="adminRequestManagement">
                    <i class="fa-solid fa-file-invoice me-2"></i>Quản lí đơn
                </a>
                <a href="adminAgenda">
                    <i class="fa-solid fa-calendar-alt me-2"></i>Theo dõi nghỉ phép
                </a>
                <a href="userSettings">
                    <i class="fa-solid fa-cog me-2"></i>Cài đặt
                </a>                
                <a href="logout" class="mt-auto"> <button class="btnLogin-popup w-100">
                        <i class="fa-solid fa-sign-out-alt me-2"></i>Đăng xuất
                    </button>
                </a>
            </nav>
        </header>
        
        <div class="content">
            <div class="container-fluid">
                
                <div class="d-flex justify-content-between align-items-center mb-4">
                    <h2 class="mb-0"><i class="fa-solid fa-users me-2"></i>Quản lý Nhân sự</h2>
                    <a href="adminUserManagement?action=add" class="btn btn-primary">
                        <i class="fa-solid fa-user-plus me-2"></i>Thêm nhân viên mới
                    </a>
                </div>

                <div class="card shadow-sm">
                    <div class="card-header bg-dark text-white">
                        <h5 class="mb-0">Danh sách nhân viên</h5>
                    </div>
                    <div class="card-body">
                        <table id="userTable" class="table table-striped table-hover table-bordered align-middle" style="width:100%">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Username</th>
                                    <th>Mật khẩu</th>
                                    <th>Họ và Tên</th>
                                    <th>Email</th>
                                    <th>Điện thoại</th>
                                    <th>Phòng ban</th>
                                    <th>Chức vụ</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="user" items="${ulist}">
                                    <tr>
                                        <td>${user.userID}</td>
                                        <td>${user.username}</td>
                                        
                                        <td class="password-cell">${user.password}</td>
                                        
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
                                                    <c:choose>
                                                        <c:when test="${r.roleID == 0}">
                                                            <span class="badge bg-danger">${r.roleName}</span>
                                                        </c:when>
                                                        <c:when test="${r.roleID == 1}">
                                                            <span class="badge bg-success">${r.roleName}</span>
                                                        </c:when>
                                                        <c:when test="${r.roleID == 2}">
                                                            <span class="badge bg-info">${r.roleName}</span>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <span class="badge bg-secondary">${r.roleName}</span>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:if>
                                            </c:forEach>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div> </div> </div> <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
        <script src="https://cdn.datatables.net/2.0.8/js/dataTables.min.js"></script>
        <script src="https://cdn.datatables.net/2.0.8/js/dataTables.bootstrap5.min.js"></script>
        <script>
            // Sửa lại cách gọi DataTables để tích hợp với Bootstrap 5
            $(document).ready(function () {
                $('#userTable').DataTable({
                    // Tùy chọn: Thêm ngôn ngữ Tiếng Việt cho DataTables
                    language: {
                        "url": "https://cdn.datatables.net/plug-ins/2.0.8/i18n/vi.json"
                    }
                });
            });

        </script>
        </body>
</html>