package details;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class RealCommand {
    public static String eval(List<String> commands) {
        try {
            Process process = null;
            if (commands.size() == 1) {
                ProcessBuilder processBuilder = new ProcessBuilder(commands.get(0).split("\\s+"));
                processBuilder.redirectErrorStream(true);
                process = processBuilder.start();
            } else {
                List<ProcessBuilder> processBuilder = new ArrayList<ProcessBuilder>(commands.size());
                for (String command : commands) {
                    processBuilder.add(new ProcessBuilder(command.split("\\s+")));
                }
                List<Process> processes = ProcessBuilder.startPipeline(processBuilder);
                process = processes.get(commands.size() - 1);
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
            if (!output.isEmpty()) {
                output.deleteCharAt(output.length() - 1);
            }
            process.waitFor();
            return output.toString();
        } catch (IOException | InterruptedException e) {
            System.out.println(e.getMessage());
            return null;
        }

    }
}
