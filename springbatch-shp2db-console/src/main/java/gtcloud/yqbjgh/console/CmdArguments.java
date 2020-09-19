package gtcloud.yqbjgh.console;

public class CmdArguments {

    // 地图服务IP地址, 如10.16.50.10
    private String mapServerIp;

    // shp文件所在目录名, 如D:\\demo
    private String filePath;

    // shp数据中fid的前缀, 如hb
    private String prefix;

    public String getMapServerIp() {
        return mapServerIp;
    }

    public void setMapServerIp(String mapServerIp) {
        this.mapServerIp = mapServerIp;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}
