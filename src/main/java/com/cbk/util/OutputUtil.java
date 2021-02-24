package com.cbk.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class OutputUtil {
    public static String formatSize(Long size) {
        if (size < 1024) {
            return size + "Byte";
        } else if (size < 1024 * 1024){
            return (size / 1024) + "KB";
        } else if (size < 1024 * 1024 * 1024) {
            return (size /1024 / 1024) + "MB";
        } else {
            return (size / 1024 / 1024 / 1024) + "GB";
        }
    }

    public static String formatTimestamp(Long timestamp) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = new Date(timestamp);
        return format.format(date);
    }
}
