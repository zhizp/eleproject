package com.ele.project.common;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ele.project.sysmanager.user.pojo.UserDTO;


/**
 * LoginFilter
 */
@WebFilter("/LoginFilter")
public class LoginFilter implements Filter {

	private String[] excludedPageArray;

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = ((HttpServletResponse) response);

		String uri = req.getRequestURI();
		String baseUrl = req.getContextPath();
		if (excludedPageArray != null) {
			for (String page : excludedPageArray) {//判断是否在过滤url之外
				String excludeUrl = baseUrl + page;
				if (uri.startsWith(excludeUrl)) {
					chain.doFilter(request, response);
					return;
				}
			}
		}

		String loginUrl = baseUrl + "/toLogin";
		UserDTO user=(UserDTO)req.getSession().getAttribute("user");
		
		try {
			if (user == null ) {
				// Not Login
				res.sendRedirect(loginUrl);
				return;
			}
		}  catch (Exception e) {
			e.printStackTrace();
			response.getWriter().println("Check session Error");
			return;
		}
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {

	}

	@Override
	public void init(FilterConfig fConfig) throws ServletException {
		String excludedPages = fConfig.getInitParameter("excludedPages");
		if (excludedPages != null && excludedPages.trim().length() > 0) {
			excludedPageArray = excludedPages.split(",");
		}
	}
}
