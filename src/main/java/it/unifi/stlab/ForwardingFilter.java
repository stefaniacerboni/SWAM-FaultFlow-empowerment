package it.unifi.stlab;

import it.unifi.stlab.faultflow.model.utils.JwtUtil;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/*")
public class ForwardingFilter implements Filter {

    private static final String AUTH_HEADER = "Authorization";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String path = req.getRequestURI();
        String contextPath = req.getContextPath();

        // Check if the request is for an API endpoint or a static file
        if (!path.startsWith(contextPath + "/rest") && !path.contains(".") && !path.equals(contextPath + "/index.html")) {
            // Forward the request to index.html for Angular routing
            request.getRequestDispatcher("/index.html").forward(request, response);
            return;
        }

        // Skip token validation for login endpoint
        if (path.startsWith(contextPath + "/rest/login")) {
            chain.doFilter(request, response);
            return;
        }

        // Check for valid JWT token for other API endpoints
        if (path.startsWith(contextPath + "/rest")) {
            String authHeader = req.getHeader(AUTH_HEADER);
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing or invalid Authorization header");
                return;
            }

            String token = authHeader.substring(7);
            try {
                // Validate the token
                String username = JwtUtil.extractUsername(token);
                if (username == null || !JwtUtil.isTokenValid(token, username)) {
                    res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
                    return;
                }
            } catch (Exception e) {
                res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
                return;
            }
        }

        // Continue with the filter chain for other requests
        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}
