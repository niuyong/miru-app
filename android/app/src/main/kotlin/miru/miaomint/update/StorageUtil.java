package miru.miaomint.update;

import android.content.Context;

public class StorageUtil {

    /**
     * 创建和获取当前App的下载路径
     * @param context
     * @return
     */
    public static String getDownloadPath(Context context) {
        return context.getFilesDir().getAbsolutePath();
    }

}