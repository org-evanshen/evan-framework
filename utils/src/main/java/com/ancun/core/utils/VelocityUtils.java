package com.ancun.core.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * velocity工具
 * 
 * @author <a href="mailto:shenwei@ancun.com">ShenWei</a>
 * @version Date: 2010-9-6 上午11:24:43
 * @since
 */

public class VelocityUtils {
	private static final Logger logger = LoggerFactory.getLogger(VelocityUtils.class);

	private static final Map<String, VelocityEngine> velocityEngineCache = new ConcurrentHashMap<String, VelocityEngine>();

	private enum ResourceLoader {
		FILE("file"), CLASSPATH("class");

		private String value;

		ResourceLoader(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}

	/**
	 * 根据模板文件在classPath的位置来填充模板
	 * @param templateLocation
	 * @param model
	 * @param encoding
	 *  <p>
	 *         author: ShenWei<br>
	 *         create at 2015年6月28日 下午2:52:14
	 */
	public static String mergeTemplateFromClassPath(String templateLocation, Map<String, Object> model, String encoding) {
		StringWriter result = new StringWriter();
		mergeTemplate(ResourceLoader.CLASSPATH, templateLocation, encoding, model, result, null);
		return result.toString();
	}

	/**
	 * 根据模板文件在文件系统的位置来填充模板
	 * @param templateLocation
	 * @param model
	 * @param encoding
	 * @param baseDir
	 *  <p>
	 *         author: ShenWei<br>
	 *         create at 2015年6月28日 下午2:52:35
	 */
	public static String mergeTemplateFromFilePath(String baseDir, String templateLocation, Map<String, Object> model,
			String encoding) {
		StringWriter result = new StringWriter();
		mergeTemplate(ResourceLoader.FILE, templateLocation, encoding, model, result, baseDir);
		return result.toString();
	}

	/**
	 * 根据模板文件在文件系统的位置来填充模板并输出文件
	 * @param baseDir
	 * @param templateLocation
	 * @param model
	 * @param targetPath
	 * @param encoding
	 *            <p>
	 *            author: ShenWei<br>
	 *            create at 2015年6月28日 下午3:11:15
	 */
	public static void mergeTemplateFromFilePath(String baseDir, String templateLocation, Map<String, Object> model,
			String targetPath, String encoding) {
		FileWriter fileWriter;
		try {
			fileWriter = new FileWriter(targetPath);
		} catch (IOException e) {
			throw new UnsupportedOperationException(e.getMessage(), e);
		}

		try {
			mergeTemplate(ResourceLoader.FILE, templateLocation, encoding, model, fileWriter, baseDir);
		} finally {
			try {
				fileWriter.close();
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

	/**
	 * 根据模板文件在classPath的位置来填充模板并输出文件
	 * @param templateLocation
	 * @param model
	 * @param targetPath
	 * @param encoding
	 *            <p>
	 *            author: ShenWei<br>
	 *            create at 2015年6月28日 下午3:11:19
	 */
	public static void mergeTemplateFromClassPath(String templateLocation, Map<String, Object> model,
			String targetPath, String encoding) {
		FileWriter fileWriter;
		try {
			fileWriter = new FileWriter(targetPath);
		} catch (IOException e) {
			throw new UnsupportedOperationException(e.getMessage(), e);
		}

		try {
			mergeTemplate(ResourceLoader.CLASSPATH, templateLocation, encoding, model, fileWriter, null);
		} finally {
			try {
				fileWriter.close();
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

	/**
	 * 
	 * @param resourceLoader
	 * @param templateLocation
	 * @param encoding
	 * @param model
	 * @param writer
	 * @param baseDir
	 *            <p>
	 *            author: ShenWei<br>
	 *            create at 2015年6月28日 下午2:57:39
	 */
	private static void mergeTemplate(ResourceLoader resourceLoader, String templateLocation, String encoding,
			Map<String, Object> model, Writer writer, String baseDir) {
		VelocityContext velocityContext = new VelocityContext(model);

		VelocityEngine velocityEngine = initVelocityEngine(resourceLoader, baseDir);
		velocityEngine.mergeTemplate(templateLocation, encoding, velocityContext, writer);
	}

	/**
	 * 初始化ClassLoader类型的VelocityEngine
	 * 
	 * @param resourceLoader
	 *  <p>
	 *         author: <a href="mailto:maochaowu@ancun.com">MaoChaoWu</a><br>
	 *         create at: 2015年4月12日 下午4:09:45
	 */
	private static VelocityEngine initVelocityEngine(ResourceLoader resourceLoader, String baseDir) {
		VelocityEngine velocityEngine = velocityEngineCache.get(resourceLoader.name() + baseDir);

		if (velocityEngine != null) {
			return velocityEngine;
		}

		velocityEngine = new VelocityEngine();

		velocityEngine.setProperty(Velocity.RESOURCE_LOADER, resourceLoader.getValue());

		if (ResourceLoader.FILE.equals(resourceLoader)) {
			velocityEngine.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, baseDir);
		} else {
			velocityEngine.setProperty("class.resource.loader.class",
					"org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
		}

		velocityEngine.init();
		velocityEngineCache.put(resourceLoader.name(), velocityEngine);

		return velocityEngine;
	}	
}
