package com.lifeng.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class GetAccessToken {

	private static Logger logger = LoggerFactory.getLogger(GetAccessToken.class);

	@Scheduled(cron = "0 0 */1 * * ?")
	public static void getAccessToken() {

	}

	public static void main(String[] args) {
		getAccessToken();
	}
}
