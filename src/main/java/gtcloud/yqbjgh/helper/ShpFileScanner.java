package gtcloud.yqbjgh.helper;

import java.io.File;
import java.util.List;

public class ShpFileScanner {

    public static void scanShp(File currentDir, List<File> result) {
        File[] subdirs = currentDir.listFiles();
        if (subdirs == null || subdirs.length == 0) {
            return;
        }
        for (File subdir : subdirs) {
            if (subdir.isDirectory()) {
                continue;
            }
            String path = subdir.getAbsolutePath();
            if (path.endsWith(".shp")) {
                result.add(subdir);
            } else {
                scanShp(subdir, result);
            }
        }
    }
}

