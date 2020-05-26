package it.polimi.tiw.project.filters;

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
import javax.servlet.http.HttpSession;

import it.polimi.tiw.project.beans.User;

@WebFilter("/ManagerChecker")
public class ManagerChecker implements Filter {

	public ManagerChecker() {
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("Manager checker filter executing...");

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		String loginPath = httpRequest.getServletContext().getContextPath() + "/index.html";

		HttpSession session = httpRequest.getSession();

		User user = null;
		user = (User) session.getAttribute("user");

		if (!user.getRole().equals("manager")) {
			httpResponse.sendRedirect(loginPath);
			return;
		}

		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
