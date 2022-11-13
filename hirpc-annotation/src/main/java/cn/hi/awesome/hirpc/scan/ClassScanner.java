package cn.hi.awesome.hirpc.scan;


import cn.hi.awesome.hirpc.annotation.RpcConsumer;
import cn.hi.awesome.hirpc.annotation.RpcProvider;
import cn.hi.awesome.hirpc.common.parser.ClassNameParser;
import cn.hi.awesome.hirpc.common.parser.PackageNameParser;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ClassScanner {

    private static final String PROTOCOL_FILE = "file";
    private static final String PROTOCOL_JAR = "jar";
    private static final String CLASS_FILE_SUFFIX = ".class";

    private static void scanClassInPackageToList(String packageName, String packagePath,
                                         boolean recursive, List<String> classNameList) {
        File dir = new File(packagePath);
        if(!dir.exists() || !dir.isDirectory()) {
            return;
        }
        File[] files = dir.listFiles(file -> (recursive && file.isDirectory()) || file.getName().endsWith(CLASS_FILE_SUFFIX));
        (files == null ? new ArrayList<File>() : Arrays.asList(files)).forEach(file -> {
            if(file.isDirectory()) {
                scanClassInPackageToList(PackageNameParser.append(packageName, file.getName()),
                        file.getAbsolutePath(),
                        recursive,
                        classNameList);
            } else {
                String className = ClassNameParser.getClassNameFromClassFileName(file.getName());
                classNameList.add(ClassNameParser.getFullPackageClassName(packageName, className));
            }
        });
    }

    private static String scanClassInJarToList(String packageName, String packageDir,
                                     boolean recursive, List<String> classNameList, URL url) throws IOException {
        JarFile jarFile = ((JarURLConnection) url.openConnection()).getJarFile();
        Enumeration<JarEntry> entries = jarFile.entries();
        while(entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement();
            String name = entry.getName().charAt(0) == '/' ?  entry.getName().substring(1) : entry.getName();
            if(name.startsWith(packageDir)) {
                int idx = name.lastIndexOf('/');
                if(idx != -1) {
                    packageName = name.substring(0, idx).replace('/', '.');
                }
                if((idx != -1) || recursive) {
                    if(name.endsWith(".class") && !entry.isDirectory()) {
                        String className = name.substring(packageName.length() + 1, name.length() - 6);
                        classNameList.add(ClassNameParser.getFullPackageClassName(packageName, className));
                    }
                }
            }
        }
        return packageName;
    }

    public static List<String> getClassNameList(String packageName) throws Exception {
        List<String> classNameList = new ArrayList<>();
        boolean rec = true;
        String packageDirName = packageName.replace('.', '/');
        Enumeration<URL> dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
        while(dirs.hasMoreElements()) {
            URL url = dirs.nextElement();
            String protocol = url.getProtocol();
            if(PROTOCOL_FILE.equals(protocol)) {
                String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                scanClassInPackageToList(packageName, filePath, rec, classNameList);
            } else if(PROTOCOL_JAR.equals(protocol)) {
                packageName = scanClassInJarToList(packageName, packageDirName, rec, classNameList, url);
            }
        }
        return classNameList;
    }


}
