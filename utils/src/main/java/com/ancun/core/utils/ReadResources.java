package com.ancun.core.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.Properties;


/**
 * 读取资源工具类
 * @author <a href="mailto:shenwei@ancun.com">ShenWei</a>
 * @version  Date: 2010-9-6 上午11:24:55

 * @since

 */
@SuppressWarnings({"rawtypes"})
public abstract class ReadResources {
	private static ClassLoader defaultClassLoader;

	/**
	 * Charset to use when calling getResourceAsReader. null means use the system default.
	 */
	private static Charset charset;

	private ReadResources() {
	}

	/**
	 * Returns the default classloader (may be null).
	 * 
	 *  The default classloader
	 */
	public static ClassLoader getDefaultClassLoader() {
		return defaultClassLoader;
	}

	/**
	 * Sets the default classloader
	 * 
	 * @param defaultClassLoader -
	 *            the new default ClassLoader
	 */
	public static void setDefaultClassLoader(ClassLoader defaultClassLoader) {
		ReadResources.defaultClassLoader = defaultClassLoader;
	}

	/**
	 * Returns the URL of the resource on the classpath
	 * 
	 * @param resource
	 *            The resource to find
	 *  The resource
	 * @throws IOException
	 *             If the resource cannot be found or read
	 */
	public static URL getResourceURL(String resource) throws IOException {
		return getResourceURL(getClassLoader(), resource);
	}

	/**
	 * Returns the URL of the resource on the classpath
	 * 
	 * @param loader
	 *            The classloader used to load the resource
	 * @param resource
	 *            The resource to find
	 *  The resource
	 * @throws IOException
	 *             If the resource cannot be found or read
	 */
	public static URL getResourceURL(ClassLoader loader, String resource) throws IOException {
		URL url = null;
		if (loader != null)
			url = loader.getResource(resource);
		if (url == null)
			url = ClassLoader.getSystemResource(resource);
		if (url == null)
			throw new IOException("Could not find resource " + resource);
		return url;
	}

	/**
	 * Returns a resource on the classpath as a Stream object
	 * 
	 * @param resource
	 *            The resource to find
	 *  The resource
	 * @throws IOException
	 *             If the resource cannot be found or read
	 */
	public static InputStream getResourceAsStream(String resource) throws IOException {
		return getResourceAsStream(getClassLoader(), resource);
	}

	/**
	 * Returns a resource on the classpath as a Stream object
	 * 
	 * @param loader
	 *            The classloader used to load the resource
	 * @param resource
	 *            The resource to find
	 *  The resource
	 * @throws IOException
	 *             If the resource cannot be found or read
	 */
	public static InputStream getResourceAsStream(ClassLoader loader, String resource) throws IOException {
		InputStream in = null;
		if (loader != null)
			in = loader.getResourceAsStream(resource);
		if (in == null)
			in = ClassLoader.getSystemResourceAsStream(resource);
		if (in == null)
			throw new IOException("Could not find resource " + resource);
		return in;
	}

	/**
	 * Returns a resource on the classpath as a Properties object
	 * 
	 * @param resource
	 *            The resource to find
	 *  The resource
	 * @throws IOException
	 *             If the resource cannot be found or read
	 */
	public static Properties getResourceAsProperties(String resource) throws IOException {
		Properties props = new Properties();
		InputStream in = null;
		try{
			String propfile = resource;
			in = getResourceAsStream(propfile);
			props.load(in);
		}finally{
			try{
				if(in!=null){
					in.close();
				}
			}catch(Exception e){
				//"ReadResources:getResourceAsProperties()关闭错误");
			}
		}
		return props;
	}

	/**
	 * Returns a resource on the classpath as a Properties object
	 * 
	 * @param loader
	 *            The classloader used to load the resource
	 * @param resource
	 *            The resource to find
	 *  The resource
	 * @throws IOException
	 *             If the resource cannot be found or read
	 */
	public static Properties getResourceAsProperties(ClassLoader loader, String resource) throws IOException {
		Properties props = new Properties();
		InputStream in = null;
		try{
		String propfile = resource;
		in = getResourceAsStream(loader, propfile);
		props.load(in);
		}finally{
			try{
				if(in!=null){
					in.close();
				}
			}catch(Exception e){
				//"ReadResources:getResourceAsProperties(ClassLoader loader, String resource)关闭错误");
			}
		}
		return props;
	}

	/**
	 * Returns a resource on the classpath as a Reader object
	 * 
	 * @param resource
	 *            The resource to find
	 *  The resource
	 * @throws IOException
	 *             If the resource cannot be found or read
	 */
	public static Reader getResourceAsReader(String resource) throws IOException {
		Reader reader;
		if (charset == null) {
			reader = new InputStreamReader(getResourceAsStream(resource));
		} else {
			reader = new InputStreamReader(getResourceAsStream(resource), charset);
		}

		return reader;
	}

	/**
	 * Returns a resource on the classpath as a Reader object
	 * 
	 * @param loader
	 *            The classloader used to load the resource
	 * @param resource
	 *            The resource to find
	 *  The resource
	 * @throws IOException
	 *             If the resource cannot be found or read
	 */
	public static Reader getResourceAsReader(ClassLoader loader, String resource) throws IOException {
		Reader reader;
		if (charset == null) {
			reader = new InputStreamReader(getResourceAsStream(loader, resource));
		} else {
			reader = new InputStreamReader(getResourceAsStream(loader, resource), charset);
		}

		return reader;
	}

	/**
	 * Returns a resource on the classpath as a File object
	 * 
	 * @param resource
	 *            The resource to find
	 *  The resource
	 * @throws IOException
	 *             If the resource cannot be found or read
	 */
	public static File getResourceAsFile(String resource) throws IOException {
		return new File(getResourceURL(resource).getFile());
	}

	/**
	 * Returns a resource on the classpath as a File object
	 * 
	 * @param loader -
	 *            the classloader used to load the resource
	 * @param resource -
	 *            the resource to find
	 *  The resource
	 * @throws IOException
	 *             If the resource cannot be found or read
	 */
	public static File getResourceAsFile(ClassLoader loader, String resource) throws IOException {
		return new File(getResourceURL(loader, resource).getFile());
	}

	/**
	 * Gets a URL as an input stream
	 * 
	 * @param urlString -
	 *            the URL to get
	 *  An input stream with the data from the URL
	 * @throws IOException
	 *             If the resource cannot be found or read
	 */
	public static InputStream getUrlAsStream(String urlString) throws IOException {
		URL url = new URL(urlString);
		URLConnection conn = url.openConnection();
		return conn.getInputStream();
	}

	/**
	 * Gets a URL as a Reader
	 * 
	 * @param urlString -
	 *            the URL to get
	 *  A Reader with the data from the URL
	 * @throws IOException
	 *             If the resource cannot be found or read
	 */
	public static Reader getUrlAsReader(String urlString) throws IOException {
		return new InputStreamReader(getUrlAsStream(urlString));
	}

	/**
	 * Gets a URL as a Properties object
	 * 
	 * @param urlString -
	 *            the URL to get
	 *  A Properties object with the data from the URL
	 * @throws IOException
	 *             If the resource cannot be found or read
	 */
	public static Properties getUrlAsProperties(String urlString) throws IOException {
		Properties props = new Properties();
		InputStream in = null;
		try{
			String propfile = urlString;
			in = getUrlAsStream(propfile);
			props.load(in);
		}finally{
			try{
				if(in!=null){
					in.close();
				}
			}catch(Exception e){
				//"ReadResources:getResourceAsProperties()关闭错误");
			}
		}
		in.close();
		return props;
	}

	/**
	 * Loads a class
	 * 
	 * @param className -
	 *            the class to load
	 *  The loaded class
	 * @throws ClassNotFoundException
	 */
	public static Class classForName(String className) throws ClassNotFoundException {
		Class clazz = null;
		try {
			clazz = getClassLoader().loadClass(className);
		} catch (Exception e) {
			// Ignore. Failsafe below.
		}
		if (clazz == null) {
			clazz = Class.forName(className);
		}
		return clazz;
	}

	/**
	 * Creates an instance of a class
	 * 
	 * @param className -
	 *            the class to create
	 *  An instance of the class
	 * @throws ClassNotFoundException
	 *             If the class cannot be found (duh!)
	 * @throws InstantiationException
	 *             If the class cannot be instantiaed
	 * @throws IllegalAccessException
	 *             If the class is not public, or other access problems arise
	 */
	public static Object instantiate(String className) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		return instantiate(classForName(className));
	}

	/**
	 * Creates an instance of a class
	 * 
	 * @param clazz -
	 *            the class to create
	 *  An instance of the class
	 * @throws InstantiationException
	 *             If the class cannot be instantiaed
	 * @throws IllegalAccessException
	 *             If the class is not public, or other access problems arise
	 */
	public static Object instantiate(Class clazz) throws InstantiationException, IllegalAccessException {
		return clazz.newInstance();
	}

	private static ClassLoader getClassLoader() {
		if (defaultClassLoader != null) {
			return defaultClassLoader;
		} else {
			return Thread.currentThread().getContextClassLoader();
		}
	}

	public static Charset getCharset() {
		return charset;
	}

	/**
	 * Use this method to set the Charset to be used when calling the getResourceAsReader methods. 
	 * 
	 * @param charset
	 */
	public static void setCharset(Charset charset) {
		ReadResources.charset = charset;
	}
}
