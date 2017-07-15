package org.evanframework.web;

import java.awt.Color;
import java.awt.Font;

/**
 * 系统公用常量
 * 
 * @author shen.wei created at 2010-11-28
 */
public interface WebCoreConstants {
    /**
     *
     */
    String SCREEN_MSG_KEY = "common.screen.msg";

    /**
     * 水印文字
     */
    String WATET_MARK_TEXT = "水印";

    /**
     * 水印颜色
     */
    Color WATET_MARK_COLOR = Color.blue;

    /**
     * 水印字体
     */
    Font WATET_MARK_FONT = new Font("黑体", Font.BOLD, 30);

    /**
     * page not find
     */
    String PAGE_NOT_FIND = "/pagenotfind";

    /**
     * 浏览器标题的key
     */
    String MODEL_KEY_TITLE = "screen_title";

    /**
     * 用于渲染script脚本的vm路径
     */
    String SCRIPT_VM_PATH = "/commons/script";

    /**
     * 用于渲染script脚本的vm中script脚本的key
     */
    String SCRIPT_VM_MODEL_KEY = "scriptcode";

    /**
     * 用于渲染页面提示的vm中key
     */

    String PAGE_MSG_MODEL_KEY = "page_msg";

    /**
     * 用于渲染页面提示的vm路径
     */
    String PAGE_MSG_VM_PATH = "/commons/msg";

    String PAGE_NOT_FIND_PATH = "/commons/404";

    /**
     * 第三方web资源url
     */
    String WEB_RESOURCES_REF_SUB_URL = "/_component";

    /**
     * 水印图片路径
     */
    String WATER_MARK_IMAGE_PATH = "/images/water_mark.gif";

    /**
     * 状态 已发布
     */
    int STATUS_PUBLISHED = 2;

    /**
     * 状态 未发布
     */
    int STATUS_NOPUBLISH = 1;

    /**
     * 状态 已删除
     */
    int STATUS_REMOVED = -1;

    /**
     * 状态 正常
     */
    int STATUS_NOMAIL = 1;

    String REMOVED_CSS = "removed";

    String EXCEPTION_RESPONSE_HEAD_KEY = "exception";

    /**
     * layout key
     */
    String LAYOUT_KEY = "layout";

    String LIST_BACK_ACTION_NAME = "listb";

    /**
     * 临时文件存储子目录
     */
    String UPLAOD_TEMP = "/temp";

    String URL_MSG = "msg";

    String APP_NAME = "app.name";
}
