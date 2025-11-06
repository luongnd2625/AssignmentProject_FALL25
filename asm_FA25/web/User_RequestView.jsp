<%@ page contentType="text-html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- THÊM 2 THƯ VIỆN JSTL: Core (c) và Formatting (fmt) --%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 

<!doctype html>
<html lang="vi">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width,initial-scale=1" />
        <title>Xem đơn của tôi</title>
        
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://cdn.datatables.net/2.0.8/css/dataTables.bootstrap5.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css" />
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.min.css">

        <style>
            /* (Toàn bộ CSS sidebar... Giữ nguyên) */
            @import url('https://fonts.googleapis.com/css2?family=Be+Vietnam+Pro:wght@300;400;500;600;700;800&display=swap&subset=vietnamese');
            * { margin: 0; padding: 0; box-sizing: border-box; font-family: 'Be Vietnam Pro', sans-serif; }
            :root{ --sidebar-width: 220px; }
            body { display: flex; justify-content: flex-start; align-items: flex-start; min-height: 100vh; background-color: #f8f9fa; padding-left: var(--sidebar-width); }
            header { background: url(Background/Bg.png) no-repeat; background-size: cover; position: fixed; top: 0; left: 0; width: var(--sidebar-width); height: 100vh; padding: 28px 16px; display: flex; flex-direction: column; align-items: center; justify-content: flex-start; gap: 18px; z-index: 99; }
            .logo { height:64px; width:auto; display:block; object-fit:contain; }
            .logo-wrap{ display:flex; align-items:center; justify-content:center; width:100%; overflow:visible; }
            .logo-wrap img{ transform: scale(2.4); transform-origin: center center; height:64px; width:auto; display:block; will-change: transform; }
            .navigation{ width:100%; display:flex; flex-direction:column; gap:6px; margin-top:8px; flex: 1 1 auto; align-items: stretch; }
            .navigation a{ position: relative; font-size: 1.05em; color: #fff; text-decoration: none; font-weight: 600; padding: 10px 12px; border-radius: 12px; transition: background 0.5s ease, transform 0.25s ease, box-shadow 0.5s ease; }
            .navigation a:hover{ background: rgba(255,255,255,0.06); box-shadow: 0 8px 20px rgba(0,0,0,0.25); transform: translateX(4px); }
            .navigation .btnLogin-popup{ width: 100%; height: 46px; background-color: transparent; border: 2px solid #fff; outline: none; border-radius: 8px; cursor: pointer; font-size: 1.05em; color: #fff; font-weight: 600; margin-top: auto; transition: background .18s ease, color .18s ease; }
            .navigation a::after{ display:none; }
            .navigation .btnLogin-popup:hover{ background-color:#fff; color:#162928; }
            .content { width: 100%; padding: 30px; }
        </style>
    </head>
    
    <body>
        
        <c:set var="userRole" value="${sessionScope.user.roleID}" />
        <header>
            <nav class="navigation">
                <c:choose>
                    <c:when test="${userRole == 0 || userRole == '0'}">
                        <a href="adminUserManagement"><i class="fa-solid fa-users-cog me-2"></i>Quản lý nhân sự</a>
                        <a href="adminRequestManagement"><i class="fa-solid fa-file-invoice me-2"></i>Quản lí đơn</a>
                        <a href="adminAgenda"><i class="fa-solid fa-calendar-alt me-2"></i>Theo dõi nghỉ phép</a>
                        <a href="userSettings"><i class="fa-solid fa-cog me-2"></i>Cài đặt</a>
                    </c:when>
                    <c:otherwise>
                        <c:if test="${userRole == 1 || userRole == 2}">
                            <a href="userAgenda"><i class="fa-solid fa-calendar-alt me-2"></i>Theo dõi nghỉ phép</a>
                        </c:if>
                        <a href="userRequest"><i class="fa-solid fa-file-pen me-2"></i>Tạo đơn xin nghỉ</a>
                        <a href="userRequestView" style="background: rgba(255,255,255,0.1);">
                            <i class="fa-solid fa-eye me-2"></i>Xem đơn của tôi
                        </a>
                        <c:if test="${userRole == 1 || userRole == 2}">
                            <a href="userRequestManagement"><i class="fa-solid fa-file-invoice me-2"></i>Quản lí đơn (Duyệt)</a>
                        </c:if>
                        <a href="userSettings"><i class="fa-solid fa-cog me-2"></i>Cài đặt</a> 
                    </c:otherwise>
                </c:choose>
                <a href="logout" class="mt-auto">
                    <button class="btnLogin-popup w-100"><i class="fa-solid fa-sign-out-alt me-2"></i>Đăng xuất</button>
                </a>
            </nav>
        </header>
        
        <div class="content">
            <div class="container-fluid">
                
                <h2 class="mb-4"><i class="fa-solid fa-eye me-2"></i>Danh sách đơn của tôi</h2>

                <c:if test="${not empty sessionScope.user_message_error}">
                    <div class="alert alert-danger alert-dismissible fade show" role="alert">
                        <strong>Thất bại!</strong> ${sessionScope.user_message_error}
                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                    </div>
                    <c:remove var="user_message_error" scope="session" />
                </c:if>
                <c:if test="${not empty sessionScope.user_message_success}">
                    <div class="alert alert-success alert-dismissible fade show" role="alert">
                        <strong>Thành công!</strong> ${sessionScope.user_message_success}
                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                    </div>
                    <c:remove var="user_message_success" scope="session" />
                </c:if>
                
                <div class="card shadow-sm">
                    <div class="card-body">
                        <table id="requestViewTable" class="table table-striped table-hover table-bordered align-middle" style="width:100%">
                            <thead>
                                <tr>
                                    <th>ID Đơn</th>
                                    <th>Tiêu đề</th>
                                    <th>Từ ngày</th>
                                    <th>Đến ngày</th>
                                    <th>Lý do</th>
                                    <th>Người duyệt</th>
                                    <th>Trạng thái</th>
                                    <th>Ghi chú (N.Duyệt)</th>
                                    <th>Hủy</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="req" items="${myRequests}">
                                    <tr>
                                        <td>${req.reqID}</td>
                                        <td>${req.title}</td>
                                        
                                        <td><fmt:formatDate value="${req.fromDate}" pattern="dd/MM/yyyy" /></td>
                                        <td><fmt:formatDate value="${req.toDate}" pattern="dd/MM/yyyy" /></td>
                                        <td>${req.reason}</td>
                                        
                                        <td>
                                            <c:if test="${empty req.approverID}">-</c:if>
                                            <c:forEach var="u" items="${allUsers}">
                                                <c:if test="${req.approverID == u.userID}">
                                                    ${u.fullname}
                                                </c:if>
                                            </c:forEach>
                                        </td>
                                        
                                        <td>
                                            <c:forEach var="s" items="${allStatus}">
                                                <c:if test="${req.statusID == s.statusID}">
                                                    <c:choose>
                                                        <c:when test="${s.statusID == 1}"><span class="badge bg-warning text-dark">${s.statusName}</span></c:when>
                                                        <c:when test="${s.statusID == 2}"><span class="badge bg-success">${s.statusName}</span></c:when>
                                                        <c:when test="${s.statusID == 3}"><span class="badge bg-danger">${s.statusName}</span></c:when>
                                                        <c:when test="${s.statusID == 4}"><span class="badge bg-secondary">${s.statusName}</span></c:when>
                                                        <c:otherwise><span class="badge bg-light text-dark">${s.statusName}</span></c:otherwise>
                                                    </c:choose>
                                                </c:if>
                                            </c:forEach>
                                        </td>
                                        
                                        <td>${empty req.approverNote ? '-' : req.approverNote}</td>
                                        
                                        <td>
                                            <c:if test="${req.statusID == 1}">
                                                <button class="btn btn-outline-danger btn-sm cancel-btn" 
                                                        data-reqid="${req.reqID}"
                                                        data-reqtitle="${req.title}">
                                                    <i class="fa-solid fa-times"></i> Hủy
                                                </button>
                                            </c:if>
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
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
        
        <script>
            $(document).ready(function () {
                $('#requestViewTable').DataTable({
                    language: { "url": "https://cdn.datatables.net/plug-ins/2.0.8/i18n/vi.json" },
                    "order": [[ 0, "desc" ]] // Sắp xếp theo ID Đơn (cột 0) giảm dần
                });

                // Xử lý nút Hủy đơn
                $('.cancel-btn').on('click', function () {
                    var button = $(this);
                    var reqID = button.data('reqid');
                    var reqTitle = button.data('reqtitle');

                    Swal.fire({
                        title: 'Bạn có chắc chắn?',
                        text: "Bạn sắp hủy đơn: '" + reqTitle + "'",
                        icon: 'warning',
                        showCancelButton: true,
                        confirmButtonColor: '#d33',
                        cancelButtonColor: '#3085d6',
                        confirmButtonText: 'Đồng ý, hủy!',
                        cancelButtonText: 'Không'
                    }).then((result) => {
                        if (result.isConfirmed) {
                            // Chuyển hướng đến servlet với action=cancel
                            window.location.href = "userRequestView?action=cancel&reqID=" + reqID;
                        }
                    });
                });
            });
        </script>
    </body>
</html>