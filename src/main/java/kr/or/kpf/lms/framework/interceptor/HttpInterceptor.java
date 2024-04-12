package kr.or.kpf.lms.framework.interceptor;

import java.net.InetAddress;
import java.util.Date;
import java.util.Optional;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.MDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


/**
 * Http 통신 인터셉터
 */
@Component
public class HttpInterceptor extends HandlerInterceptorAdapter {

	private static final Logger logger = LoggerFactory.getLogger(HttpInterceptor.class);

	private final String MDC_CLIENT_TRACE_ID = "X-B3-TraceId";
	private final String MDC_CLIENT_SPAN_ID = "X-B3-SpanId";

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		super.afterCompletion(request, response, handler, ex);
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		String ipAddress = request.getRemoteAddr();
		if (request instanceof HttpServletRequest) {
			HttpServletRequest httpServletRequest = request;
			String xForwardedFor = httpServletRequest.getHeader("X-FORWARDED-FOR");
			if(xForwardedFor!=null && xForwardedFor.length()>0) {
				ipAddress = xForwardedFor; //client, proxy1, proxy2
				if(ipAddress.indexOf(',') >= 0) {
					ipAddress = ipAddress.substring(0, ipAddress.indexOf(','));
				}
			}
			MDC.put("client.httpMethod", httpServletRequest.getMethod());
			MDC.put("client.requestURI", httpServletRequest.getRequestURI());
		}

		if(ipAddress.equals("0:0:0:0:0:0:0:1")) {
			ipAddress = "127.0.0.1";
		}

		if(ipAddress.length()>0) {
			MDC.put("client.accessIP", ipAddress);
		}
		String spanId = Long.toHexString(new Random().nextLong());
		MDC.put(MDC_CLIENT_TRACE_ID, spanId);
		MDC.put(MDC_CLIENT_SPAN_ID, spanId);
		Optional.ofNullable(request.getHeader(MDC_CLIENT_TRACE_ID))
			.filter(StringUtils::isNotBlank)
			.ifPresent(traceId -> {
				MDC.put(MDC_CLIENT_TRACE_ID, traceId);
			});
		
		logger.debug("HostAddr: {}, CharacterEncoding: {}", InetAddress.getLocalHost(), response.getCharacterEncoding());
		super.postHandle(request, response, handler, modelAndView);
	}
}