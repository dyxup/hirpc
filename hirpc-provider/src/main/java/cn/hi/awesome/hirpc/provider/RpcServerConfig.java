package cn.hi.awesome.hirpc.provider;

public class RpcServerConfig {

    private String listenAddr;
    private String registryAddr;
    private int registryPort;
    private String scanPackage;

    public RpcServerConfig(String listenAddr, String registryAddr, int registryPort, String scanPackage) {
        this.listenAddr = listenAddr;
        this.registryAddr = registryAddr;
        this.registryPort = registryPort;
        this.scanPackage = scanPackage;
    }

    public String getListenAddr() {
        return listenAddr;
    }

    public void setListenAddr(String listenAddr) {
        this.listenAddr = listenAddr;
    }

    public String getRegistryAddr() {
        return registryAddr;
    }

    public void setRegistryAddr(String registryAddr) {
        this.registryAddr = registryAddr;
    }

    public int getRegistryPort() {
        return registryPort;
    }

    public void setRegistryPort(int registryPort) {
        this.registryPort = registryPort;
    }

    public String getScanPackage() {
        return scanPackage;
    }

    public void setScanPackage(String scanPackage) {
        this.scanPackage = scanPackage;
    }

    public static RpcServerConfig read() {
        // TODO:
        return new RpcServerConfig(null, null, 0, null);
    }

}
