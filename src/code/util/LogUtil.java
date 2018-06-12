package code.util;

import com.sun.media.jfxmedia.logging.Logger;

public class LogUtil {
    public static void logException(Exception ex) {
        ex.printStackTrace();
        Logger.logMsg(Logger.ERROR, ex.getMessage());
    }
}