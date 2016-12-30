package com.kanven.schedual;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kanven.schedual.core.clustor.Clustor;
import com.kanven.schedual.core.clustor.impl.ClustorFactory;
import com.kanven.schedual.core.config.ProtocolConfig;
import com.kanven.schedual.exactor.TaskExactor;
import com.kanven.schedual.network.protoc.RequestProto.TaskReportor;
import com.kanven.schedual.register.Constants;
import com.kanven.schedual.register.Register;
import com.kanven.schedual.report.JobReportor;

public class ExactorBootstrap {

	private static final Logger log = LoggerFactory.getLogger(ExactorBootstrap.class);

	private static final String EXACTOR_CONFIG_PATH = "exactor.properties";

	private static final String PROTOCOL_CONFIG_PATH = "protocol-config.properties";

	private static final String REGISTER_CONFIG_PATH = "register.properties";

	public static Properties getProperties(String file) throws IOException {
		Properties properties = new Properties();
		InputStream input = ClassLoader.getSystemClassLoader().getResourceAsStream(file);
		properties.load(input);
		return properties;
	}

	public static Register createRegister() throws IOException {
		Properties properties = getProperties(REGISTER_CONFIG_PATH);
		String address = properties.getProperty("zk.address");
		if (StringUtils.isEmpty(address)) {
			throw new RuntimeException("没有指定注册中心地址！");
		}
		int sessionTimeout = StringUtils.isNotEmpty(properties.getProperty("zk.sessionTimeout"))
				? Integer.parseInt(properties.getProperty("zk.sessionTimeout")) : Register.DEFAULT_SESION_TIMEOUT;
		return new Register(properties.getProperty("zk.address"), sessionTimeout);
	}

	public static TaskExactor createExactor(Register register) throws IOException {
		Properties properties = getProperties(EXACTOR_CONFIG_PATH);
		String ip = properties.getProperty("exactor.ip");
		if (StringUtils.isEmpty(ip)) {
			throw new RuntimeException("没有执行任务执行服务的ip地址！");
		}
		TaskExactor exactor = new TaskExactor();
		exactor.setIp(ip);
		int port = StringUtils.isNotEmpty(properties.getProperty("exactor.port"))
				? Integer.parseInt(properties.getProperty("exactor.port")) : TaskExactor.DEFAULT_EXACTOR_PORT;
		exactor.setPort(port);
		exactor.setRoot(Constants.EXECUTOR_ROOT);
		exactor.setRegister(register);
		return exactor;
	}

	public static ProtocolConfig createProtocol() throws IOException {
		ProtocolConfig config = new ProtocolConfig();
		Properties properties = getProperties(PROTOCOL_CONFIG_PATH);
		String connectionTimeout = properties.getProperty("protocol.connectTimeout");
		if (StringUtils.isNotEmpty(connectionTimeout)) {
			config.setConnectTimeout(Long.parseLong(connectionTimeout));
		}
		String threads = properties.getProperty("protocol.threads");
		if (StringUtils.isNotEmpty(threads)) {
			config.setThreads(Integer.parseInt(threads));
		}
		String minIdle = properties.getProperty("protocol.minIdle");
		if (StringUtils.isNotEmpty(minIdle)) {
			config.setMaxIdle(Integer.parseInt(minIdle));
		}
		String maxIdle = properties.getProperty("protocol.maxIdle");
		if (StringUtils.isNotEmpty(maxIdle)) {
			config.setMaxIdle(Integer.parseInt(maxIdle));
		}
		String maxTotal = properties.getProperty("protocol.maxTotal");
		if (StringUtils.isNotEmpty(maxTotal)) {
			config.setMaxTotal(Integer.parseInt(maxTotal));
		}
		return config;
	}

	public static void createReportor(Register register) throws IOException {
		ClustorFactory<TaskReportor> factory = new ClustorFactory<TaskReportor>();
		factory.setConfig(createProtocol());
		factory.setParent(Constants.CENTER_ROOT);
		factory.setRegister(register);
		Clustor<TaskReportor> clustor = factory.getClustor();
		JobReportor reportor = new JobReportor();
		reportor.setClustor(clustor);
	}

	public static void main(String[] args) {
		try {
			if (log.isDebugEnabled()) {
				log.debug("服务启动开始...");
			}
			Register register = createRegister();
			createReportor(register);
			TaskExactor exactor = createExactor(register);
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
