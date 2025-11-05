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

@WebFilter(urlPatterns = "/adminUserManagement")
public class Authfilter implements Filter{
    
    private static final boolean debug = true;
    private FilterConfig filterConfig = null;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);
        
        boolean loggedIn = (session != null && session.getAttribute("user") != null);
        
        String requestURI = httpRequest.getRequestURI();
        
        boolean LoginPage = requestURI.endsWith("/login");
        boolean RegisterPage = requestURI.endsWith("/register");
        boolean LogoutPage = requestURI.endsWith("/logout");
        
        boolean UserAgendaPage = requestURI.endsWith("/userAgenda");
        boolean UserRequestManagement = requestURI.endsWith("/userRequestManagement");
        boolean UserRequest = requestURI.endsWith("/userRequest");
        boolean UserRequestView = requestURI.endsWith("/userRequestView");
        boolean UserSettings = requestURI.endsWith("/userSettings");
        
        
    }

    @Override
    public void destroy() {
        Filter.super.destroy(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    public String toString() {
        return super.toString(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }
    
}
