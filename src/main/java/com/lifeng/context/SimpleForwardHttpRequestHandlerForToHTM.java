package com.lifeng.context;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.resource.DefaultServletHttpRequestHandler;


public class SimpleForwardHttpRequestHandlerForToHTM extends DefaultServletHttpRequestHandler {
	private static final Logger logger = LoggerFactory.getLogger(SimpleForwardHttpRequestHandlerForToHTM.class);
	
	@Override
	public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getServletPath().endsWith(".htm")) {
			String forwardURL = "/WEB-INF/" + request.getServletPath().substring(0, request.getServletPath().length() - 4) + ".jsp";
			logger.debug("forward to {}", forwardURL);
			request.getRequestDispatcher(forwardURL).forward(request, response);
		}
	}

}
