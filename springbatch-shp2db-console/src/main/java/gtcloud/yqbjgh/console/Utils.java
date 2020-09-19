package gtcloud.yqbjgh.console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class Utils {

    public static boolean isWindows() {
        return System.getProperty("os.name").toLowerCase().startsWith("windows");
    }

    public static void exec(List<String> commandArray) {
        ProcessBuilder processBuilder = new ProcessBuilder();
        BufferedReader reader = null;
        try {
            Process process = processBuilder.command(commandArray)
                .redirectErrorStream(true)
                .start();
            String encoding = System.getProperty("sun.jnu.encoding");
            reader = new BufferedReader(
                new InputStreamReader(process.getInputStream(), encoding));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                System.err.println("Unexpected error occur, code=" + exitCode);
            }
            reader.close();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
