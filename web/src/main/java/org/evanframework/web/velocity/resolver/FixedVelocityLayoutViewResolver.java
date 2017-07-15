package org.evanframework.web.velocity.resolver;

import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.view.AbstractUrlBasedView;
import org.springframework.web.servlet.view.velocity.VelocityViewResolver;

public class FixedVelocityLayoutViewResolver extends VelocityViewResolver {
	private static final Logger log = LoggerFactory.getLogger(FixedVelocityLayoutViewResolver.class);

	private String layoutUrl;

	private String layoutKey;

	private String screenContentKey;

	private String templateEncoding = "UTF-8";

	private VelocityEngine velocityEngine = null;

	public VelocityEngine getVelocityEngine() {
		return velocityEngine;
	}

	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}

	public String getTemplateEncoding() {
		return templateEncoding;
	}

	public void setTemplateEncoding(String templateEncoding) {
		this.templateEncoding = templateEncoding;
	}

	public void setLayoutUrl(String layoutUrl) {
		this.layoutUrl = layoutUrl;
	}

	public void setLayoutKey(String layoutKey) {
		this.layoutKey = layoutKey;
	}

	public void setScreenContentKey(String screenContentKey) {
		this.screenContentKey = screenContentKey;
	}

	@Override
	protected AbstractUrlBasedView buildView(String viewName) throws Exception {
		if (!StringUtils.startsWith(viewName, "/") && !StringUtils.endsWith(this.getPrefix(), "/")) {
			viewName = "/" + viewName;
		}

		FixedVelocityLayoutView view = (FixedVelocityLayoutView) super.buildView(viewName);
		view.setContentType(this.getContentType());
		view.setEncoding(this.templateEncoding);
		if (this.velocityEngine != null) {
			view.setVelocityEngine(this.velocityEngine);
		}
		if (this.layoutUrl != null) {
			view.setLayoutUrl(this.layoutUrl);
		}
		if (this.layoutKey != null) {
			view.setLayoutKey(this.layoutKey);
		}
		if (this.screenContentKey != null) {
			view.setScreenContentKey(this.screenContentKey);
		}
		
		if(log.isDebugEnabled()){
			log.debug("View:{},", view);
		}
		
		return view;
	}

	@Override
	protected Class<?> requiredViewClass() {
		return FixedVelocityLayoutView.class;
	}

	@Override
	protected Class<?> getViewClass() {
		return FixedVelocityLayoutView.class;
	}

	//	public void setLayoutUrls(Map<String, String> layoutUrls) {
	//		this.layoutUrls = layoutUrls;
	//
	//		if (log.isDebugEnabled()) {
	//			for (Entry<String, String> entry : layoutUrls.entrySet()) {
	//				log.debug("Inited layout mapper [servlet:" + entry.getKey() + ", layout:" + entry.getValue()
	//						+ "]");
	//			}
	//		}
	//	}
}
