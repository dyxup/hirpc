package cn.hi.awesome.hirpc.common.parser;

import org.apache.commons.lang3.StringUtils;

public class PackageNameParser {

    public static String append(String packageName, String appendSuffix) {
        if(StringUtils.isEmpty(packageName)) {
            return appendSuffix;
        }
        if(StringUtils.isEmpty(appendSuffix)) {
            return packageName;
        }
        return packageName + '.' + appendSuffix;
    }

}
