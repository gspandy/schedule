package com.kanven.schedual.utils.tmpl;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.kanven.schedual.utils.db.EntityMeta;
import com.kanven.schedual.utils.db.mysql.TableHandler;

import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.TemplateNotFoundException;

/**
 * 模版处理类
 * 
 * @author kanven
 *
 */
public class TmplMarker {

	private static final String DEFAULT_ENCODE = "UTF-8";

	private static final String DEFAULT_BASE_PACKAGE_PATH = "/tmpl";

	private String encode = DEFAULT_ENCODE;

	private String basePackagePath = DEFAULT_BASE_PACKAGE_PATH;

	private Configuration cfg;

	private boolean inited = false;

	private Lock lock = new ReentrantLock();

	public TmplMarker() {

	}

	public void process(String tmpl, Object model) throws TemplateNotFoundException, MalformedTemplateNameException,
			ParseException, IOException, TemplateException {
		init();
		Template template = cfg.getTemplate(tmpl);
		Writer out = new OutputStreamWriter(System.out);
		template.process(model, out);
	}

	private void init() {
		if (inited) {
			return;
		}
		lock.lock();
		try {
			if (!inited) {
				cfg = new Configuration(Configuration.VERSION_2_3_25);
				cfg.setDefaultEncoding(encode);
				cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
				cfg.setClassLoaderForTemplateLoading(Thread.currentThread().getContextClassLoader(), basePackagePath);
			}
		} finally {
			lock.unlock();
		}
	}

	public String getEncode() {
		return encode;
	}

	public void setEncode(String encode) {
		this.encode = encode;
	}

	public String getBasePackagePath() {
		return basePackagePath;
	}

	public void setBasePackagePath(String basePackagePath) {
		this.basePackagePath = basePackagePath;
	}

}
