package cn.hi.awesome.hirpc.common.parser;

import org.apache.commons.lang3.StringUtils;

public class PackageParser {

    public static String append(String packageName, String appendSuffix) {
        if(StringUtils.isEmpty(packageName)) {
            return appendSuffix;
        }
        if(StringUtils.isEmpty(appendSuffix)) {
            return packageName;
        }
        return packageName + '.' + appendSuffix;
    }

    public static String convertPackageNameToDirPath(String packagePath) {
        return packagePath.replace('.', '/');
    }

    public static String convertFilePathToPackageName(String fullPath) {
        int idx = fullPath.lastIndexOf('/');
        return idx == -1 ? null : fullPath.substring(0, idx).replace('/', '.');
    }

    public static String removeFirstSlashInPath(String path) {
        boolean begin = path.charAt(0) == '/';
        return begin ? path.substring(1) : path;
    }

}
