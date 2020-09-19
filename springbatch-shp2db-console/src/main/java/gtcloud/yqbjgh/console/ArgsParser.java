package gtcloud.yqbjgh.console;

import java.util.ArrayList;
import java.util.List;

public class ArgsParser {
    private static class ArgInfo {
        public ArgInfo(String argName, String argValue) {
            this.name = argName;
            this.value = argValue;
        }
        String name;
        String value;
    }

    private List<ArgInfo> args = new ArrayList<>();

    public ArgsParser(String[] args) {
        argsToArgInfos(args, this.args);
    }

    public String getArgByName(String name) {
        for (ArgInfo argInfo : this.args) {
            if (argInfo.name.equalsIgnoreCase(name)) {
                return argInfo.value;
            }
        }
        return null;
    }

    public List<String> getArgNames() {
        List<String> result = new ArrayList<String>();
        for (ArgInfo argInfo : this.args) {
            result.add(argInfo.name);
        }
        return result;
    }

    private static void argsToArgInfos(String[] args, List<ArgInfo> result) {
        for (String arg : args) {
            if (!arg.startsWith("--")) {
                continue;
            }
            arg = arg.substring(2).trim();
            int pos = arg.indexOf('=');
            if (pos <= 0) {
                result.add(new ArgInfo(arg, null));
            } else {
                String argName = arg.substring(0, pos).trim();
                String argValue = arg.substring(pos+1).trim();
                result.add(new ArgInfo(argName, strip(argValue)));
            }
        }
    }

    // 去掉两端可能的双引号
    private static String strip(String argValue) {
        if (argValue.startsWith("\"") && argValue.endsWith("\"")) {
            int endIndex = argValue.length() - 1;
            if (endIndex == 1) {
                argValue = null;
            } else {
                argValue = argValue.substring(1, endIndex);
            }
        }
        return argValue;
    }
}
