package org.evanframework.web.velocity.resolver;

import org.evanframework.syslog.TimeProfiler;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.tools.ToolboxFactory;
import org.apache.velocity.tools.config.ToolboxConfiguration;
import org.apache.velocity.tools.config.XmlFactoryConfiguration;
import org.apache.velocity.tools.view.ViewToolContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.core.NestedIOException;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.view.velocity.VelocityToolboxView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.StringWriter;
import java.util.Collection;
import java.util.Locale;
import java.util.Map;


public class FixedVelocityLayoutView extends VelocityToolboxView {
	private static final Logger log = LoggerFactory.getLogger(FixedVelocityLayoutView.class);

	public static final String APPLICATIONCONTEXT_KEY =  "org.springframework.context.ApplicationContext";
	public static final String DEFAULT_LAYOUT_URL = "layout.vm";
	public static final String DEFAULT_LAYOUT_KEY = "layout";
	public static final String DEFAULT_SCREEN_CONTENT_KEY = "screen_content";
	private String layoutUrl = DEFAULT_LAYOUT_URL;
	private String layoutKey = DEFAULT_LAYOUT_KEY;
	private String screenContentKey = DEFAULT_SCREEN_CONTENT_KEY;

	/**
	 * Set the layout template to use. Default is {@link #DEFAULT_LAYOUT_URL
	 * "layout.vm"}.
	 * 
	 * @param layoutUrl
	 *            the template location (relative to the template root
	 *            directory)
	 */
	public void setLayoutUrl(String layoutUrl) {
		this.layoutUrl = layoutUrl;
	}

	/**
	 * Set the context key used to specify an alternate layout to be used
	 * instead of the default layout. Screen content templates can override the
	 * layout template that they wish to be wrapped with by setting this value
	 * in the template, for example:<br>
	 * <code>#set( $layout = "MyLayout.vm" )</code>
	 * <p>
	 * Default key is {@link #DEFAULT_LAYOUT_KEY "layout"}, as illustrated
	 * above.
	 * 
	 * @param layoutKey
	 *            the name of the key you wish to use in your screen content
	 *            templates to override the layout template
	 */
	public void setLayoutKey(String layoutKey) {
		this.layoutKey = layoutKey;
	}

	/**
	 * Set the name of the context key that will hold the content of the screen
	 * within the layout template. This key must be present in the layout
	 * template for the current screen to be rendered.
	 * <p>
	 * Default is {@link #DEFAULT_SCREEN_CONTENT_KEY "screen_content"}: accessed
	 * in VTL as <code>$screen_content</code>.
	 * 
	 * @param screenContentKey
	 *            the name of the screen content key to use
	 */
	public void setScreenContentKey(String screenContentKey) {
		this.screenContentKey = screenContentKey;
	}

	/**
	 * Overrides <code>VelocityView.checkTemplate()</code> to additionally check
	 * that both the layout template and the screen content template can be
	 * loaded. Note that during rendering of the screen content, the layout
	 * template can be changed which may invalidate any early checking done
	 * here.
	 */
	@Override
	public boolean checkResource(Locale locale) throws Exception {
		if (!super.checkResource(locale)) {
			return false;
		}

		try {
			// Check that we can get the template, even if we might subsequently
			// get it again.
			getTemplate(this.layoutUrl);
			return true;
		} catch (ResourceNotFoundException ex) {
			throw new NestedIOException("Cannot find Velocity template for URL [" + this.layoutUrl
					+ "]: Did you specify the correct resource loader path?", ex);
		} catch (Exception ex) {
			throw new NestedIOException("Could not load Velocity template for URL [" + this.layoutUrl + "]",
					ex);
		}
	}

	/**
	 * Overrides the normal rendering process in order to pre-process the
	 * Context, merging it with the screen template into a single value
	 * (identified by the value of screenContentKey). The layout template is
	 * then merged with the modified Context in the super class.
	 */
	@Override
	protected void doRender(Context context, HttpServletResponse response) throws Exception {
		renderScreenContent(context);

		// Velocity context now includes any mappings that were defined
		// (via #set) in screen content template.
		// The screen template can overrule the layout by doing
		// #set( $layout = "MyLayout.vm" )
		String layoutUrlToUse = (String) context.get(this.layoutKey);
		if (StringUtils.isNotBlank(layoutUrlToUse)) {
			if (logger.isDebugEnabled()) {
				logger.debug("Screen content template has requested layout [" + layoutUrlToUse + "]");
			}
			mergeTemplate(getTemplate(layoutUrlToUse), context, response);
		} else {
			if (layoutUrlToUse == null) {
				mergeTemplate(getTemplate(this.layoutUrl), context, response);
			} else {
				response.getWriter().print(context.get(this.screenContentKey));
			}

		}
	}

	/**
	 * The resulting context contains any mappings from render, plus screen
	 * content.
	 */
	private void renderScreenContent(Context velocityContext) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("Rendering screen content template [" + getUrl() + "]");
		}

		StringWriter sw = new StringWriter();
		Template screenContentTemplate = getTemplate(getUrl());
		if (TimeProfiler.isProfileEnable()) {
			TimeProfiler.beginTask("render screen:" + screenContentTemplate.getName());
		}
		try {
			screenContentTemplate.merge(velocityContext, sw);
		} finally {
			if (TimeProfiler.isProfileEnable()) {
				TimeProfiler.endTask();
			}
		}
		// Put rendered content into Velocity context.
		velocityContext.put(this.screenContentKey, sw.toString());
	}

	@Override
	protected void mergeTemplate(Template template, Context context, HttpServletResponse response)
			throws Exception {
		if (TimeProfiler.isProfileEnable()) {
			TimeProfiler.beginTask("merge template:" + template.getName());
		}
		try {
			super.mergeTemplate(template, context, response);
		} finally {
			if (TimeProfiler.isProfileEnable()) {
				TimeProfiler.endTask();
			}
		}
	}

	/**
	 * 加载velocitytool
	 */
	@Override
	protected Context createVelocityContext(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		VelocityEngine velocityEngine  = this.getVelocityEngine();
		ApplicationContext applicationContext = getApplicationContext();
		//velocityEngine.addProperty("applicationContext",applicationContext);
		ViewToolContext ctx = new ViewToolContext(velocityEngine, request, response, this.getServletContext());
		ctx.put(APPLICATIONCONTEXT_KEY,applicationContext);

		if (this.getToolboxConfigLocation() != null) {
			XmlFactoryConfiguration factory = new XmlFactoryConfiguration();
			factory.read(ResourceUtils.getURL(this.getToolboxConfigLocation()).openStream());
			ToolboxFactory toolboxFactory = factory.createFactory();
			toolboxFactory.configure(factory);
			Collection<ToolboxConfiguration> toolboxes = factory.getToolboxes();
			for (ToolboxConfiguration tc : toolboxes) {
				ctx.addToolbox(toolboxFactory.createToolbox(tc.getScope()));
			}
		}
		if (model != null && !model.isEmpty()) {
			ctx.putAll(model);
		}
		return ctx;
	}
}
