package org.beangle.website.system.model;

/**
 * 字典工具类
 * @author GOKU
 *
 */
public class DictDataUtils {

	public final static Long CONTENT_STYLE_URL = 1L; //链接类型
	public final static Long CONTENT_STYLE_RICHTEXT = 2L; //富文本
	public final static Long CONTENT_PROPERTY_INDEX = 3L; //推荐到首页
	public final static Long CONTENT_PROPERTY_COLUMN = 4L; //推荐到栏目
	public final static Long CONTENT_PROPERTY_HOT = 5L; //热点
	public final static Long CONTENT_PROPERTY_IMPORTANT = 6L; //重要
	public final static Long CONTENT_STATE_DRAFT = 7L; //起草
	public final static Long CONTENT_STATE_WAITAUDIT = 8L; //待审核
	public final static Long CONTENT_STATE_WAITPUBLISH = 9L; //待发布
	public final static Long CONTENT_STATE_PUBLISHED = 10L; //已发布
	public final static Long CONTENT_STATE_BACK = 11L; //退回
	public final static Long CONTENT_STATE_DELETE = 12L; //删除
	public final static Long CONTENT_STATE_END = 13L; //不通过
	public final static Long READ_PURVIEW_SCHOOL = 14L; //校内
	public final static Long READ_PURVIEW_ALL = 15L; //社会
	public final static Long COLUMN_TYPE_CONTENT = 16L; //信息发布类型
	public final static Long COLUMN_TYPE_MENU = 17L; //功能模块类型
	public final static Long COLUMN_TYPE_URL = 18L; //链接类型
	public final static Long CONTENT_SOURCE_COLUMN = 20L; //关联栏目
	public final static Long CONTENT_SOURCE_SELF = 21L; //本身
	public final static Long ACCESS_PUBLIC = 22L; //公开
	public final static Long ACCESS_RESTRICTED = 23L; //受限
	public final static Long BUILD_MODE_MOVE = 24L; //动态
	public final static Long BUILD_MODE_STATIC = 25L; //静态
	public final static Long EXT_PROPERTY_SOURCE_SELF = 26L; //自定义
	public final static Long EXT_PROPERTY_SOURCE_PARENT = 27L; //继承上级栏目
	public final static Long EXT_PROPERTY_TYPE_TEXT = 41L; //文本
	public final static Long EXT_PROPERTY_TYPE_DROPBOX = 42L; //下拉框
	public final static Long CONTENT_STYLE_TEXT = 61L; //文本
	public final static Long OPEN_MODE_NEW = 81L; //新窗口
	public final static Long Paper = 101L; //纸
	public final static Long ELECTRON = 102L; //电子文本
	public final static Long CD = 103L; //光盘
	public final static Long DISK = 104L; //磁盘
	public final static Long POST = 105L; //邮寄
	public final static Long DELIVERY = 106L; //快递
	public final static Long EMAIL = 107L; //电子邮件
	public final static Long FAX = 108L; //传真
	public final static Long SELF = 109L; //自行领取/当场阅读、抄录
	public final static Long OPEN2_TYPE_YSQ = 121L; //依申请公开
	public final static Long OPEN_TYPE_ZD = 122L; //主动公开
	public final static Long COLUMN_TYPE_SITE = 141L; //站点根目录
	public final static Long COLUMN_TYPE_ONLYCOLUMN = 142L; //节点类型
	public final static Long COLUMN_TYPE_INDEX = 161L; //首页
	public final static Long RECORD_TYPE_TEXT = 181L; //文本
	public final static Long RECORD_TYPE_CHART = 182L; //图表
	public final static Long RECORD_TYPE_PHOTO = 183L; //照片
	public final static Long RECORD_TYPE_VIDEO = 184L; //影音
	public final static Long CARRIER_TYPE_PAPER = 185L; //纸质
	public final static Long CARRIER_TYPE_FILM = 186L; //胶卷
	public final static Long CARRIER_TYPE_TAPE = 187L; //磁带
	public final static Long CARRIER_TYPE_DISK = 188L; //磁盘
	public final static Long CARRIER_TYPE_CD = 189L; //光盘
	public final static Long OPEN2_TYPE_MY = 190L; //免予公开
	public final static Long CARRIER_TYPE_OTHER = 191L; //其他
	public final static Long XXTGFS_ZM = 201L; //纸面
	public final static Long XXTGFS_EMAIL = 202L; //电子邮件
	public final static Long XXTGFS_CD = 203L; //光盘
	public final static Long XXHQFS_YJ = 204L; //邮寄
	public final static Long XXHQFS_KD = 205L; //快递
	public final static Long XXHQFS_EMAIL = 206L; //电子邮件
	public final static Long XXHQFS_FAX = 207L; //传真
	public final static Long XXHQFS_SELF = 208L; //自行领取/当场阅读、录
	public final static Long XXGKSQ_ZT_DCL = 221L; //待处理
	public final static Long XXGKSQ_ZT_DBMCL = 222L; //待部门处理
	public final static Long XXGKSQ_ZT_BMYCL = 223L; //部门已处理
	public final static Long NEWS_TYPE_01 = 224L; //普通新闻

	public final static String GENDER_01_MALE = "GENDER_01_MALE";//性别：男
	public final static String GENDER_02_FEMALE = "GENDER_02_FEMALE";//性别：女
	public final static String PTSHZT_01_WTJ = "PTSHZT_01_WTJ";//普通审核状态：未提交
	public final static String PTSHZT_02_WTG = "PTSHZT_02_WTG";//普通审核状态：未通过
	public final static String PTSHZT_03_DSH = "PTSHZT_03_DSH";//普通审核状态：待审核
	public final static String PTSHZT_04_YTG = "PTSHZT_04_YTG";//普通审核状态：已通过
	
	public static final String ZSTZLX_01_BS ="ZSTZLX_01_BS";//笔试通知
	public static final String ZSTZLX_02_MS ="ZSTZLX_02_MS";//面试通知
	
	public static final String TZZT_01_WFS ="TZZT_01_WFS";//未发送
	public static final String TZZT_02_YFS ="TZZT_02_YFS";//已发送
}
