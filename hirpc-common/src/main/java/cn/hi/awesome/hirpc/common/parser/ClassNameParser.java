package cn.hi.awesome.hirpc.common.parser;

public class ClassNameParser {

    private static final String JAVA_SUFFIX_REGEX = "\\.(?i)java";
    private static final String CLASS_SUFFIX_REGEX = "\\.(?i)class";

    public static String getClassNameFromClassFileName(String fileName) {
        return fileName.replaceAll(CLASS_SUFFIX_REGEX, "");
    }

    public static String getClassNameFromSourceFileName(String fileName) {
        return fileName.replaceAll(JAVA_SUFFIX_REGEX, "");
    }

    public static String getFullPackageClassName(String packageName, String className) {
        return append(packageName, className);
    }

    public static String append(String a, String b) {
        return a + "." + b;
    }


    public static void main(String[] args) {
        System.out.println(getClassNameFromClassFileName("HelloWorld.java"));
        System.out.println(getClassNameFromClassFileName("HelloWorld.jAva"));
        System.out.println(getClassNameFromClassFileName("HelloWorld.JAva"));
        System.out.println(getClassNameFromClassFileName("HelloWorld.JAVA"));
        System.out.println(getClassNameFromClassFileName("HelloWorld.JAV"));
    }

}
