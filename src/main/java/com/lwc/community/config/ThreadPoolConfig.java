package com.lwc.community.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author Liu Wenchang
 */
@Configuration
@EnableScheduling
@EnableAsync
public class ThreadPoolConfig {
}
