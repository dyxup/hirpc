package cn.hi.awesome.hirpc.meta;

import cn.hi.awesome.hirpc.annotation.RpcProvider;

import java.util.Objects;

public class RpcKey<T> {
    private String interfaceName;
    private String version;
    private String group;
    private T annotationReference;

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public T getProvider() {
        return annotationReference;
    }

    public void setProvider(T annotationReference) {
        this.annotationReference = annotationReference;
    }

    public RpcKey(String interfaceName, String version, String group, T annotationReference) {
        this.interfaceName = interfaceName;
        this.version = version;
        this.group = group;
        this.annotationReference = annotationReference;
    }

    public static <T> RpcKey<T> of(String className, String version, String group, T annotationReference) {
        return new RpcKey<>(className, version, group, annotationReference);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RpcKey that = (RpcKey) o;
        return interfaceName.equals(that.interfaceName) && version.equals(that.version) && group.equals(that.group);
    }

    @Override
    public int hashCode() {
        return Objects.hash(interfaceName, version, group);
    }

    @Override
    public String toString() {
        return "RpcKey{" +
                "interfaceName='" + interfaceName + '\'' +
                ", version='" + version + '\'' +
                ", group='" + group + '\'' +
                ", annotationReference=" + annotationReference +
                '}';
    }
}
