package com.xg.w3.util;

import org.apache.log4j.Logger;

public class LogUtil {
    public static Logger getLogger(final Class cls) {
        return Logger.getLogger(cls.getCanonicalName());
    }
}
