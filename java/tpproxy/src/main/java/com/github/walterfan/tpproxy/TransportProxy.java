package com.github.walterfan.tpproxy;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.ParseException;

/**
 * TCP Proxy
 *
 */
public class TransportProxy 
{
    public static void main( String[] args )
    {
    	Options options = new Options();


        Option opt = new Option("p", "port", true, "required, sets the listen port");
        opt.setRequired(true);
        options.addOption(opt);
        
        opt = new Option("d", "destination", true, "required, sets the destination: host and port, e.g. 127.0.0.1:80");
        opt.setRequired(true);
        options.addOption(opt);

        opt = new Option("t", "transport", true, "optional, sets the transport type: tcp|udp");
        opt.setRequired(false);
        options.addOption(opt);

        opt = new Option("h", "help", false, "optional, print help");
        opt.setRequired(false);
        options.addOption(opt);
        
        opt = new Option("v", "version", false, "optional, print version");
        opt.setRequired(false);
        options.addOption(opt);
        
        
        HelpFormatter hf = new HelpFormatter();
        hf.setWidth(110);
        CommandLine commandLine = null;
        CommandLineParser parser = new DefaultParser();
        try {
            commandLine = parser.parse(options, args);
            if (commandLine.hasOption('h')) {
                hf.printHelp("tproxy", options, true);
            }

            System.out.println("--------------------------------------");
            Option[] opts = commandLine.getOptions();
            if (opts != null) {
                for (Option opt1 : opts) {
                    String name = opt1.getLongOpt();
                    String value = commandLine.getOptionValue(name);
                    System.out.println(name + "=>" + value);
                }
            }
        }
        catch (ParseException e) {
            hf.printHelp("tproxy", options, true);
        }
    }
}

