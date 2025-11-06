package Filter;
import Model.Users;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import java.io.IOException;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class AuthFilter implements Filter{
    
    private static final boolean debug = true;
    private FilterConfig filterConfig = null;
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);
        String contextPath = httpRequest.getContextPath();
        String requestURI = httpRequest.getRequestURI();

        boolean loggedIn = (session != null && session.getAttribute("user") != null);
        // Page don't need login
        boolean isLoginPage = requestURI.endsWith("/login");
        boolean isRegisterPage = requestURI.endsWith("/register");

        // Skip element of UI
        boolean isStaticResource = requestURI.contains("/Background/")
                || requestURI.endsWith(".css")
                || requestURI.endsWith(".js")
                || requestURI.endsWith(".png")
                || requestURI.endsWith(".jpg")
                || requestURI.endsWith(".gif");

        // Check if user logged in
        if (loggedIn || isLoginPage || isRegisterPage || isStaticResource) {

            // Check authorization
            if (loggedIn) {
                Users user = (Users) session.getAttribute("user");
                int roleID = user.getRoleID();
                
                // Take URL that user's trying to go
                String targetPath = requestURI.substring(contextPath.length());

                // Area of each role
                boolean isAdminArea = targetPath.startsWith("/admin");
                boolean isManagerArea = targetPath.endsWith("/userAgenda") || targetPath.endsWith("/userRequestManagement");
                
                // --- Logic authorization ---

                // If role 3 try to go page that need higher role
                if (roleID == 3 && (isAdminArea || isManagerArea)) {
                    httpResponse.sendRedirect(contextPath + "/userRequest");
                    return;
                } 
                // If role 1,2 try to go admin
                else if ((roleID == 1 || roleID == 2) && isAdminArea) {
                    httpResponse.sendRedirect(contextPath + "/userRequestManagement");
                    return;
                }
            }

            // Admin can access all page
            chain.doFilter(request, response);

        } else {
            //If not login, go to login
            httpResponse.sendRedirect(contextPath + "/login");
            return; // THÊM RETURN
        }
    }

    public FilterConfig getFilterConfig() {
        return filterConfig;
    }

    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
        if(filterConfig != null) {
            if(debug) {
                log("Authfilter:Initializing filter");
            }
        }
    }
    
    

    @Override
    public void destroy() {
        Filter.super.destroy(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    public String toString() {
        if(filterConfig == null) {
            return ("AuthFilter()");
        }
        StringBuffer sb = new StringBuffer("AuthFilter(");
        sb.append(filterConfig);
        sb.append(")");
        return (sb.toString());
    }
    
    private void sendProcessingError(Throwable t, ServletResponse response) {
        String stackTrace = getStackTrace(t);        
        
        if (stackTrace != null && !stackTrace.equals("")) {
            try {
                response.setContentType("text/html");
                PrintStream ps = new PrintStream(response.getOutputStream());
                PrintWriter pw = new PrintWriter(ps);                
                pw.print("<html>\n<head>\n<title>Error</title>\n</head>\n<body>\n"); //NOI18N

                // PENDING! Localize this for next official release
                pw.print("<h1>The resource did not process correctly</h1>\n<pre>\n");                
                pw.print(stackTrace);                
                pw.print("</pre></body>\n</html>"); //NOI18N
                pw.close();
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        } else {
            try {
                PrintStream ps = new PrintStream(response.getOutputStream());
                t.printStackTrace(ps);
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        }
    }
    
    public static String getStackTrace(Throwable t) {
        String stackTrace = null;
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            t.printStackTrace(pw);
            pw.close();
            sw.close();
            stackTrace = sw.getBuffer().toString();
        } catch (Exception ex) {
        }
        return stackTrace;
    }
    
    public void log(String msg) {
        filterConfig.getServletContext().log(msg);
    }
}
