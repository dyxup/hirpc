package cn.hi.awesome.hirpc.scan;

import cn.hi.awesome.hirpc.common.exception.ScanException;
import cn.hi.awesome.hirpc.common.parser.ClassParser;
import cn.hi.awesome.hirpc.common.parser.PackageParser;

import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class JarClassScanner implements Scanner<List<String>> {
    private static final String PROTOCOL = "jar";

    private static final String SUFFIX = ".class";

    private final String scanPackage;

    public JarClassScanner(String scanPackage) {
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
                    scanClassFromJar(url, packageDirPath, classNameList);
                }
            }
        } catch (Exception e) {
            throw new ScanException(e);
        }
        return classNameList;
    }

    private void scanClassFromJar(URL jarUrl, String currentPackagePath, List<String> classNameList) throws IOException {
        if(!PROTOCOL.equals(jarUrl.getProtocol())) {
            System.out.println(jarUrl.getProtocol());
            return;
        }
        JarFile jarFile = ((JarURLConnection) jarUrl.openConnection()).getJarFile();
        Enumeration<JarEntry> entries = jarFile.entries();
        while(entries.hasMoreElements()) {
            JarEntry jarEntry = entries.nextElement();
            String fullPath = PackageParser.removeFirstSlashInPath(jarEntry.getName());
            if(!fullPath.startsWith(currentPackagePath)) {
                continue;
            }
            String packageName = PackageParser.convertFilePathToPackageName(fullPath);
            if(packageName != null) {
                String className = ClassParser.getClassNameFromFilePath(packageName.length(), fullPath);
                classNameList.add(ClassParser.getFullPackageClassName(packageName, className));
            }
        }
    }

}
