package com.ancun.core.web.velocity.tools;

/**
 * velocity app common tool
 * 
 */
public class RegionTool {
//	private final static Logger log = LoggerFactory.getLogger(RegionTool.class);
//
//	private WebApplicationContext context;
//	private RegionService regionService;
//
//	public void init(Object obj) {
//		context = ContextLoaderListener.getCurrentWebApplicationContext();
//		regionService = context.getBean(RegionService.class);
//	}
//
//	/**
//	 * 根据地区代码获取地区名称
//	 * <p>
//	 * author: <a href="mailto:shenwei@ancun.com">ShenWei</a><br>
//	 * version: 2011-4-19 下午01:34:17 <br>
//	 * 
//	 * @param regionCode
//	 *
//	 */
//	public String getRegionName(HttpServletRequest request, String regionCode) {
//		String returnV = null;
//		Object name = request.getAttribute("region_" + regionCode);
//
//		if (name == null) {
//			PubRegion region = regionService.getByCode(regionCode);
//			if (region != null) {
//				returnV = region.getRegionName();
//				request.setAttribute("region_" + regionCode, returnV);
//			}
//		} else {
//			returnV = name.toString();
//			if (log.isTraceEnabled()) {
//				log.trace("get from request, Region [regionCode=" + regionCode + ", regionName=" + returnV + "]");
//			}
//		}
//
//		return returnV;
//	}
}
