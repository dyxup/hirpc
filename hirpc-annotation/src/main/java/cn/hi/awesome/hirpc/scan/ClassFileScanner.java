package cn.hi.awesome.hirpc.scan;

import cn.hi.awesome.hirpc.common.exception.ScanException;
import cn.hi.awesome.hirpc.common.parser.ClassParser;
import cn.hi.awesome.hirpc.common.parser.PackageParser;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Stream;

public class ClassFileScanner implements Scanner<List<String>> {

    private static final String PROTOCOL = "file";

    private static final String SUFFIX = ".class";

    private final String scanPackage;

    public ClassFileScanner(String scanPackage) {
        this.scanPackage = scanPackage;
    }

    @Override
    public List<String> scan() {
        List<String> classNameList = new ArrayList<>();
        if(scanPackage == null) {
            return classNameList;
        }
        try {
            String packageDirPath = PackageParser.convertPackageNameToDirPath(scanPackage);
            Enumeration<URL> resources = Thread.currentThread().getContextClassLoader().getResources(packageDirPath);
            while(resources.hasMoreElements()) {
                URL url = resources.nextElement();
                if(PROTOCOL.equals(url.getProtocol())) {
                    scanClassFromDirRecursively(url.getPath(), scanPackage, classNameList);
                }
            }
        } catch (Exception e) {
            throw new ScanException(e);
        }
        return classNameList;
    }

    private void scanClassFromDirRecursively(String currentPackagePath, String currentPackageName, List<String> classNameList) {
        File dir = new File(currentPackagePath);
        if(!dir.exists() || !dir.isDirectory()) {
            return;
        }
        File[] files = dir.listFiles(file -> file.getName().endsWith(SUFFIX) || file.isDirectory());
        (files == null ? Stream.<File>empty() : Arrays.stream(files)).forEach(file -> {
            if (file.isDirectory()) {
                scanClassFromDirRecursively(file.getPath(), PackageParser.append(currentPackageName, file.getName()), classNameList);
            } else {
                String className = ClassParser.getClassNameFromClassFileName(file.getName());
                classNameList.add(ClassParser.getFullPackageClassName(currentPackageName, className));
            }
        });
    }

}
