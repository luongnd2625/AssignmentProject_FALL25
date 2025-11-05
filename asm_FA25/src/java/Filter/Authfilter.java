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

public class Authfilter implements Filter{
    
    private static final boolean debug = true;
    private FilterConfig filterConfig = null;

    protected void doBeforeProcessing(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (debug) {
            log("AuthFilter:DoBeforeProcessing");
        }
    }
    
    protected void doAfterProcessing(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (debug) {
            log("AuthFilter:DoAfterProcessing");
        }
    }
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);
        
        boolean loggedIn = (session != null && session.getAttribute("user") != null);
        
        String requestURI = httpRequest.getRequestURI();
        //Create all page that role > 0 can access, Admin page only access by roleID=0
        boolean LoginPage = requestURI.endsWith("/login");
        boolean RegisterPage = requestURI.endsWith("/register");
        boolean LogoutPage = requestURI.endsWith("/logout");
        
        boolean UserAgendaPage = requestURI.endsWith("/userAgenda");
        boolean UserRequestManagement = requestURI.endsWith("/userRequestManagement");
        boolean UserRequest = requestURI.endsWith("/userRequest");
        boolean UserRequestView = requestURI.endsWith("/userRequestView");
        boolean UserSettings = requestURI.endsWith("/userSettings");
        
        boolean allowedForRole3 = LoginPage || RegisterPage || LogoutPage || UserRequest || UserRequestView || UserSettings;
        boolean allowedForRole1And2 = allowedForRole3 || UserAgendaPage || UserRequestManagement;
        
        if (!loggedIn && !LoginPage && !RegisterPage) { //If user isn't logged in, navigate to login page
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
        }
        
        if(loggedIn) {
            Users user = (Users)session.getAttribute("user");
            int roleID = user.getRoleID();
            
            if(roleID == 3 && !allowedForRole3) {   //Navigate if role 3 
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/userRequest");
                return;
            } else if ((roleID == 1 || roleID == 2) && !allowedForRole1And2 ) { //Navigate if role 1 or 2
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/userRequestManagement");
                return;
            }
            //Role 0 can access all page
        }
        chain.doFilter(request, response);
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
