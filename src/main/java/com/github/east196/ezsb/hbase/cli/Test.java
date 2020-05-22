package com.github.east196.ezsb.hbase.cli;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import org.apache.commons.cli.*;


/**
 * Created by Administrator on 2016/5/26.
 */
public class Test {
    public static void main(String[] args) {
        byte[] bytes = Hashing.md5().hashString("111",Charsets.UTF_8).asBytes();
        // create the command line parser
        CommandLineParser parser = new PosixParser();

// create the Options
        Options options = new Options();
        options.addOption("a", "all", false, "do not hide entries starting with .");
        options.addOption("A", "almost-all", false, "do not list implied . and ..");
        options.addOption("b", "escape", false, "print octal escapes for nongraphic "
                + "characters");
        options.addOption(OptionBuilder.withLongOpt("block-size")
                .withDescription("use SIZE-byte blocks")
                .hasArg()
                .withArgName("SIZE")
                .create());
        options.addOption("B", "ignore-backups", false, "do not list implied entried "
                + "ending with ~");
        options.addOption("c", false, "with -lt: sort by, and show, ctime (time of last "
                + "modification of file status information) with "
                + "-l:show ctime and sort by name otherwise: sort "
                + "by ctime");
        options.addOption("C", false, "list entries by columns");
        options.getOptions().stream().forEach(it -> System.out.println(it));

//        String[]
//                args = new String[]{ "--block-size=10","--all=00" };

        try {
            // parse the command line arguments
            CommandLine line = parser.parse(options, args);

            // validate that block-size has been set
            if (line.hasOption("block-size")) {
                // print the value of block-size
                System.out.println(line.getOptionValue("block-size"));
            }
        } catch (ParseException exp) {
            System.out.println("Unexpected exception:" + exp.getMessage());
        }
    }


}
