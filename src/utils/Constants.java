package utils;

/**
 * 爬虫的各种常量
 * 可以在此对各种参数进行设置
 * Created by ZQ on 2016/4/22.
 */
public abstract class Constants {
    /**
     * HTTP请求的USER_AGENT
     */
    public static final String USER_AGENT = "2016IR201330551365";
    /**
     * 作为爬取起点的种子URL
     */
    public static final String SEED_URL = "http://www.scut.edu.cn";
    /**
     * log记录中时间的显示格式
     */
    public static final String DATE_FORMAT = "yyyy-mm-dd hh:mm:ss.SSS";
    /**
     * 操作类型 连接
     */
    public static final String TYPE_CONNECTING = "Connecting";
    /**
     * 操作类型 获取数据
     */
    public static final String TYPE_FETCHING = "Fetching";
    /**
     * 操作类型 解析数据
     */
    public static final String TYPE_PARSING = "Parsing";
    /**
     * 操作结果 成功
     */
    public static final String TAG_SUCCESS = "Successful";
    /**
     * 操作结果 失败
     */
    public static final String TAG_ERROR = "Error";
    /**
     * log文件的文件名
     */
    public static final String LOG_FILE_NAME = "IR201330551365LOG.txt";
    /**
     * 设置要获取的文件类型
     */
    public static final String CONTENT_TYPE = "text/";
    /**
     * 设置要爬取的文件大小的下限
     * 单位：字节
     */
    public static final int CONTENT_LENGTH = 8000;
    /**
     * 设置爬取深度上限
     */
    public static final int SEARCH_DEPTH = 10;
}
