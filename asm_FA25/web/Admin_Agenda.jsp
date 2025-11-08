<%@ page contentType="text-html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 

<!doctype html>
<html lang="vi">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width,initial-scale=1" />
        <title>Admin - Theo dõi nghỉ phép</title>

        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css" />

        <style>
            .content {
                width: 100%;
                padding: 30px;
            }

            /* CSS MỚI ĐỂ HIỂN THỊ THÔNG TIN USER */
            .user-info-header {
                font-weight: 500;
                color: #495057; /* Màu xám đậm */
                padding: 10px 15px;
                background-color: #e9ecef; /* Nền xám nhạt */
                border-radius: 8px;
            }
            /* (Toàn bộ CSS Sidebar... Giữ nguyên) */
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
            .navigation{
                width:100%;
                display:flex;
                flex-direction:column;
                gap:6px;
                margin-top:8px;
                flex: 1 1 auto;
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
                margin-top: auto;
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
                width: 100%;
                padding: 30px;
            }

            /* CSS MỚI CHO LỊCH (AGENDA) */
            .agenda-table {
                table-layout: fixed;
                border-color: #dee2e6;
            }
            .agenda-table th, .agenda-table td {
                vertical-align: top;
                height: 120px;
                padding: 8px;
            }
            .agenda-table thead th {
                height: auto;
                background-color: #343a40;
                color: white;
                text-align: center;
                vertical-align: middle;
            }
            .day-number {
                font-size: 1.2em;
                font-weight: 600;
                color: #6c757d;
            }
            .on-leave {
                display: block;
                background-color: #dc3545;
                color: white;
                padding: 2px 6px;
                border-radius: 4px;
                font-size: 0.8em;
                margin-top: 4px;
                white-space: nowrap;
                overflow: hidden;
                text-overflow: ellipsis;
            }
            .other-month {
                background-color: #f8f9fa;
            }
        </style>
    </head>

    <body>

        <header>
            <nav class="navigation">
                <a href="adminUserManagement"><i class="fa-solid fa-users-cog me-2"></i>Quản lý nhân sự</a>
                <a href="adminRequestManagement"><i class="fa-solid fa-file-invoice me-2"></i>Quản lí đơn</a>
                <a href="adminAgenda" style="background: rgba(255,255,255,0.1);">
                    <i class="fa-solid fa-calendar-alt me-2"></i>Theo dõi nghỉ phép
                </a>
                <a href="userSettings"><i class="fa-solid fa-cog me-2"></i>Cài đặt</a>                
                <a href="logout" class="mt-auto">
                    <button class="btnLogin-popup w-100">
                        <i class="fa-solid fa-sign-out-alt me-2"></i>Đăng xuất
                    </button>
                </a>
            </nav>
        </header>

        <div class="content">
            <div class="container-fluid">
                <c:set var="user" value="${sessionScope.user}" />
                <c:forEach var="r" items="${rlist}"><c:if test="${r.roleID == user.roleID}"><c:set var="roleName" value="${r.roleName}" /></c:if></c:forEach>
                <c:forEach var="d" items="${dlist}"><c:if test="${d.deptID == user.deptID}"><c:set var="deptName" value="${d.deptName}" /></c:if></c:forEach>

                        <h5 class="user-info-header mb-4">
                            <i class="fa-solid fa-user-shield me-2"></i>
                    <c:choose>
                        <%-- Nếu là Admin (Role 0) --%>
                        <c:when test="${user.roleID == 0}">
                            ${roleName}: ${user.fullname}
                        </c:when>
                        <%-- Nếu là Role 1, 2 (User đang ở trang Admin?) --%>
                        <c:otherwise>
                            ${roleName} ${deptName} department: ${user.fullname}
                        </c:otherwise>
                    </c:choose>
                </h5>
                <h2 class="mb-4"><i class="fa-solid fa-calendar-alt me-2"></i>Theo dõi nghỉ phép (Toàn công ty)</h2>

                <div class="card shadow-sm mb-4">
                    <div class="card-body">
                        <form action="adminAgenda" method="POST" class="row g-3 align-items-end">
                            <div class="col-md-3">
                                <label for="month" class="form-label">Chọn Tháng</label>
                                <select id="month" name="month" class="form-select">
                                    <c:forEach var="m" begin="1" end="12">
                                        <option value="${m}" ${selectedMonth == m ? 'selected' : ''}>Tháng ${m}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="col-md-3">
                                <label for="year" class="form-label">Chọn Năm</label>
                                <select id="year" name="year" class="form-select">
                                    <c:set var="currentYear" value="<%= java.util.Calendar.getInstance().get(java.util.Calendar.YEAR) %>" />
                                    <c:forEach var="y" begin="${currentYear - 2}" end="${currentYear + 2}">
                                        <option value="${y}" ${selectedYear == y ? 'selected' : ''}>Năm ${y}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="col-md-3">
                                <button type="submit" class="btn btn-primary">
                                    <i class="fa-solid fa-search me-2"></i>Xem lịch
                                </button>
                            </div>
                        </form>
                    </div>
                </div>

                <div class="card shadow-sm">
                    <div class="card-body">
                        <table class="table table-bordered agenda-table">
                            <thead>
                                <tr>
                                    <th>Chủ Nhật</th><th>Thứ Hai</th><th>Thứ Ba</th><th>Thứ Tư</th><th>Thứ Năm</th><th>Thứ Sáu</th><th>Thứ Bảy</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:set var="dayCounter" value="1" />
                                <c:set var="firstDay" value="${firstDayOfWeek}" />

                                <c:forEach var="row" begin="1" end="6">
                                    <c:if test="${dayCounter <= daysInMonth}">
                                        <tr>
                                            <c:forEach var="col" begin="0" end="6">
                                                <c:if test="${(row == 1 && col < firstDay) || (dayCounter > daysInMonth)}">
                                                    <td class="other-month"></td>
                                                </c:if>

                                                <c:if test="${row > 0 && !(row == 1 && col < firstDay) && (dayCounter <= daysInMonth)}">
                                                    <td>
                                                        <div class="day-number">${dayCounter}</div>

                                                        <c:forEach var="user" items="${viewableUsers}">
                                                            <c:forEach var="req" items="${approvedRequests}">
                                                                <c:if test="${user.userID == req.userID}">
                                                                    <fmt:formatDate value="${req.fromDate}" pattern="d" var="startDay"/>
                                                                    <fmt:formatDate value="${req.toDate}" pattern="d" var="endDay"/>
                                                                    <c:if test="${dayCounter >= startDay && dayCounter <= endDay}">
                                                                        <span class="on-leave" title="${user.fullname}: ${req.title}">
                                                                            <i class="fa-solid fa-user-slash me-1"></i>${user.fullname}
                                                                        </span>
                                                                    </c:if>
                                                                </c:if>
                                                            </c:forEach>
                                                        </c:forEach>
                                                    </td>
                                                    <c:set var="dayCounter" value="${dayCounter + 1}" />
                                                </c:if>
                                            </c:forEach>
                                        </tr>
                                    </c:if>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>

            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>