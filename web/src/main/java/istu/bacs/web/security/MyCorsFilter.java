package istu.bacs.web.security;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class MyCorsFilter implements Filter {

    public MyCorsFilter() {
        super();
    }

    @Override
    public final void doFilter(final ServletRequest req, final ServletResponse res, final FilterChain chain) throws IOException, ServletException {
        final HttpServletResponse response = (HttpServletResponse) res;
        response.setHeader("Access-Control-Allow-Origin", "*");

        response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");

        response.setHeader("Access-Control-Allow-Headers", "X-Requested-With, Authorization, Origin, Content-Type, Version");
        response.setHeader("Access-Control-Expose-Headers", "X-Requested-With, Authorization, Origin, Content-Type");

        chain.doFilter(req, res);
    }

    @Override
    public void destroy() {

    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }
}