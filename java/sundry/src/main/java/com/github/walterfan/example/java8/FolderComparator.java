package com.github.walterfan.example.java8;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by walter on 19/02/2017.
 */
public class FolderComparator {
    private static final Logger logger =  LoggerFactory.getLogger(FolderComparator.class);

    private static boolean debugFlag = true;

    List<Path> listSourceFiles(Path dir) throws IOException {
        List<Path> result = new ArrayList<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir, "*.{c,h,cpp,hpp,java}")) {
            for (Path entry: stream) {
                result.add(entry);
            }
        } catch (DirectoryIteratorException ex) {
            // I/O error encounted during the iteration, the cause is an IOException
            throw ex.getCause();
        }
        return result;
    }

    public void launchTool(Map<String, String> paraMap) throws IOException {
        //Files.list(Paths.get(".")).forEach(System.out::println);

        listSourceFiles(Paths.get(".")).forEach(System.out::println);
    }


    public static void main(String[] args) {
        {

            Options options = new Options();

            // Option(String opt, String longOpt, boolean hasArg, String description)
            Option opt = new Option("s", "src", true, "specify the src folder");
            opt.setRequired(false);
            options.addOption(opt);

            opt = new Option("d", "dest", true, "specify the dest folder");
            opt.setRequired(false);
            options.addOption(opt);

            opt = new Option("h", "help", false, "optional, print help");
            opt.setRequired(false);
            options.addOption(opt);

            opt = new Option("v", "version", false, "optional, print version");
            opt.setRequired(false);
            options.addOption(opt);

            Map<String, String> paraMap = new HashMap<String,String>();

            HelpFormatter hf = new HelpFormatter();
            hf.setWidth(110);
            CommandLine commandLine = null;
            CommandLineParser parser = new BasicParser();
            try {

                commandLine = parser.parse(options, args);
                if (commandLine.hasOption('h')) {
                    hf.printHelp("helper", options, true);
                }


                Option[] opts = commandLine.getOptions();
                if (opts != null) {
                    for (Option opt1 : opts) {
                        String name = opt1.getLongOpt();
                        String value = commandLine.getOptionValue(name);
                        logger.debug(name + "=>" + value);
                        paraMap.put(name, value);
                    }
                }

                if(debugFlag ) {
                    new FolderComparator().launchTool(paraMap);
                }

            } catch (Exception e) {
                logger.error("launch error: ", e);

            }
        }
    }
}
