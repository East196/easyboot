package com.github.east196.ezsb.core;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

@Component
public class CommonFilter implements Filter {
	private static final Logger LOGGER = LoggerFactory.getLogger(CommonFilter.class);

	@Override
	public void destroy() {
		// DO NOTHING
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest requestHttp = (HttpServletRequest) request;
		LOGGER.debug(requestHttp.getRequestURI());
		Map<String, String[]> parameterMap = requestHttp.getParameterMap();
		if (isNormalUrl(requestHttp.getRequestURI())) {
			LOGGER.debug("Remote {}:{}, URL: {},Method:{},Paras:{}", requestHttp.getRemoteHost(),
					requestHttp.getRemotePort(), requestHttp.getRequestURI(), requestHttp.getMethod(),
					new Gson().toJson(parameterMap));
		}
		chain.doFilter(requestHttp, response);
	}

	private static boolean isNormalUrl(String requestURI) {
		if (requestURI.endsWith(".css") || requestURI.endsWith(".js") || requestURI.endsWith(".json")
				|| requestURI.endsWith(".html") || requestURI.endsWith(".png") || requestURI.endsWith(".jpg")
				|| requestURI.endsWith(".map") || requestURI.endsWith(".woff") || requestURI.endsWith(".woff2")) {
			return false;
		}
		return true;
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// DO NOTHING
	}

}