package gtcloud.yqbjgh.console;

import java.util.ArrayList;
import java.util.List;

public class ShpToolMain {

    public static void main(String[] args) {
        // 解析命令行
        for (String arg : args) {
            if (arg.equals("--help") || arg.equals("-h")) {
                printUsage();
                System.exit(0);
            }
        }

        CmdArguments argsObj = new CmdArguments();
        if (!parseCmdLine(args, argsObj)) {
            System.exit(1);
        }

        // 调用命令 java -jar gtcloud-tool-shp2db-1.0.0.jar
        callJar(argsObj);
    }

    private static void printUsage() {
        System.out.format("用法如下:%n");
        System.out.println("选项包括:");
        System.out.println("    --mapServerIp=地图服务IP地址, 如10.16.50.10");
        System.out.println("    --filePath=shp文件所在目录名, 如D:\\demo");
        System.out.println("    --prefix=shp数据中fid的前缀, 如hb");
        System.out.println("用法举例:");

        if (Utils.isWindows()) {
            System.out.format("%s --mapServerIp=10.16.50.10" +
                " --filePath=D:\\demo" +
                " --prefix=hb%n", "shp2db.exe");
        } else {
            System.out.format("%s --mapServerIp=10.16.50.10" +
                " --filePath=/tmp/demo" +
                " --prefix=hb%n", "shp2db.exe");
        }
    }

    private static boolean parseCmdLine(String[] args, CmdArguments argsObj) {
        final ArgsParser parser = new ArgsParser(args);
        final List<String> argNames = parser.getArgNames();
        for (String argName : argNames) {
            if (argName.equalsIgnoreCase("mapServerIp")) {
                argsObj.setMapServerIp(parser.getArgByName(argName));
                continue;
            }
            else if (argName.equalsIgnoreCase("filePath")) {
                argsObj.setFilePath(parser.getArgByName(argName));
                continue;
            }
            else if (argName.equalsIgnoreCase("prefix")) {
                argsObj.setPrefix(parser.getArgByName(argName));
                continue;
            }

            System.err.format("用法错误, 不支持的参数<%s>. 请使用--help选项查看用法", argName);
            return false;
        }

        if (argsObj.getMapServerIp() == null) {
            System.err.println("用法错误, 没有指定<mapServerIp>参数. 请使用--help选项查看用法");
            return false;
        }

        else if (argsObj.getFilePath() == null) {
            System.err.println("用法错误, 没有指定<filePath>参数. 请使用--help选项查看用法");
            return false;
        }

        else if (argsObj.getPrefix() == null) {
            System.err.println("用法错误, 没有指定<prefix>参数. 请使用--help选项查看用法");
            return false;
        }
        return true;
    }

    private static void callJar(CmdArguments argsObj) {
        List<String> commands = new ArrayList<>();
        commands.add("jre\\bin\\java");
        commands.add("-jar");
        commands.add("libs\\gtcloud-shp2db-core-1.0.0.jar");
        commands.add(argsObj.getMapServerIp());
        commands.add(argsObj.getFilePath());
        commands.add(argsObj.getPrefix());
        Utils.exec(commands);
    }
}
