package utils;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static utils.Constants.LOG_FILE_NAME;

/**
 * 用于线程安全地写入文件
 * 该类为单例
 * Created by ZQ on 2016/4/30.
 */
public class LogHelper {
    private List<String> logList = new CopyOnWriteArrayList<>();
    private ExecutorService exec;

    private LogHelper(){
        exec = Executors.newSingleThreadExecutor();
    }

    private static class Holder{
        private static LogHelper instance = new LogHelper();
        private static LogHelper getInstance(){
            return instance;
        }
    }

    public static LogHelper getInstance(){
        return Holder.getInstance();
    }

    public void addLog(String log){
        exec.execute(new Runnable() {
            @Override
            public void run() {
                FileUtils.writeFileAppend(LOG_FILE_NAME, log);
            }
        });
    }


}
