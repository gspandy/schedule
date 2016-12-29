package com.kanven.schedual;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kanven.schedual.exactor.TaskExactor;
import com.kanven.schedual.register.Constants;
import com.kanven.schedual.register.Register;

public class ExactorBootstrap {

	private static final Logger log = LoggerFactory.getLogger(ExactorBootstrap.class);

	private static final String EXACTOR_CONFIG_PATH = "exactor.properties";

	public static Properties getProperties(String file) throws IOException {
		Properties properties = new Properties();
		InputStream input = ClassLoader.getSystemClassLoader().getResourceAsStream(EXACTOR_CONFIG_PATH);
		properties.load(input);
		return properties;
	}

	public static void main(String[] args) {
		try {
			if (log.isDebugEnabled()) {
				log.debug("服务启动开始...");
			}
			Properties properties = ExactorBootstrap.getProperties(EXACTOR_CONFIG_PATH);
			if (log.isDebugEnabled()) {
				log.debug("服务配置信息加载完成...");
			}
			int sessionTimeout = StringUtils.isNotEmpty(properties.getProperty("zk.sessionTimeout"))
					? Integer.parseInt(properties.getProperty("zk.sessionTimeout")) : Register.DEFAULT_SESION_TIMEOUT;
			Register register = new Register(properties.getProperty("zk.address"), sessionTimeout);
			TaskExactor exactor = new TaskExactor();
			exactor.setIp(properties.getProperty("exactor.ip"));
			int port = StringUtils.isNotEmpty(properties.getProperty("exactor.port"))
					? Integer.parseInt(properties.getProperty("exactor.port")) : TaskExactor.DEFAULT_EXACTOR_PORT;
			exactor.setPort(port);
			exactor.setRoot(Constants.EXECUTOR_ROOT);
			exactor.setRegister(register);
			exactor.init();
			if (log.isDebugEnabled()) {
				log.debug("服务启动完成...");
			}
		} catch (Exception e) {
			log.error("任务执行器启动出现异常！", e);
			System.exit(1);
		}
	}

}
