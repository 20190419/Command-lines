
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 *
 * @author mohamed Eslam Amin 20190419 Moaz Gamal Mostafa Mahmoud
 */
public class CLI_Main {

    public static void main(String[] args) throws Exception {
        boolean flag = false;
        Integer pipe = 0;
        String input = "";
        Parser cmds = new Parser();
        Terminal term = new Terminal();
        Scanner In = new Scanner(System.in);

        while (!flag) {

            System.out.print(("> "));
            input = In.nextLine();
            String[] pipes = input.split(Pattern.quote(" | "));
            pipe = pipes.length;

            for (int i = 0; i < pipe; ++i) {
                if (!cmds.parse(pipes[i])) {
                    continue;                       //check if cmd is True.////////////////
                }
                flag = cmds.getCommandName().matches("exit") && cmds.getArgs().isEmpty();
                Options(cmds, term);
            }
        }
    }

    /**
     * @return Choosed option.
     */
    public static void Options(Parser cmds, Terminal term) throws Exception {

        if (cmds.getCommandName().matches("pwd") && cmds.getArgs().isEmpty()) {
            term.pwd();
        } else if (cmds.getCommandName().matches("mkdir")) {
            int begin = 0;
            Boolean v = cmds.getArgs().contains("-v");
            if (v) {
                begin++;
            }
            if (cmds.getArgs().contains("-p")) {
                begin++;
                String[] lastargsarr;
                lastargsarr = (cmds.getArgs().get(cmds.getArgs().size() - 1)).split("/|\\\\");
                String first = lastargsarr[0];
                for (int counter = 0; counter < lastargsarr.length; ++counter) {
                    term.mkdir(first, v);
                    if (counter < lastargsarr.length - 1) {
                        first += "/" + lastargsarr[counter + 1];
                    }
                }
            } else {
                for (int counter = begin; counter < cmds.getArgs().size(); ++counter) {
                    term.mkdir(cmds.getArgs().get(counter), v);
                }
            }
        } else if (cmds.getCommandName().matches("rmdir") && cmds.getArgs().size() == 1) {
            term.rmdir(cmds.getArgs().get(0));
//        } else if (cmds.getCommandName().matches("ls_r")) {
//            term.ls_r(cmds.getArgs());
        } else if (cmds.getCommandName().matches("ls")) {
            ArrayList<String> Arr = new ArrayList<String>();
            if (cmds.getArgs().contains(">")) {
                String path = cmds.getArgs().get(cmds.getArgs().size() - 1);
                cmds.getArgs().remove(cmds.getArgs().size() - 1);
                cmds.getArgs().remove(cmds.getArgs().size() - 1);
                if (cmds.getArgs().isEmpty()) {
                    String[] r = term.ls(term.getDefault_dir());
                    Collections.addAll(Arr, r);
                    term.R1Command(path, Arr);
                } else {
                    String[] r = term.ls(cmds.getArgs().get(0));
                    Collections.addAll(Arr, r);
                    term.R1Command(path, Arr);
                }
            } else if (cmds.getArgs().contains(">>")) {
                String path = cmds.getArgs().get(cmds.getArgs().size() - 1);
                cmds.getArgs().remove(cmds.getArgs().size() - 1);
                cmds.getArgs().remove(cmds.getArgs().size() - 1);
                if (cmds.getArgs().isEmpty()) {
                    String[] r = term.ls(term.getDefault_dir());
                    Collections.addAll(Arr, r);
                    term.R2Command(path, Arr);
                } else {
                    String[] r = term.ls(cmds.getArgs().get(0));
                    Collections.addAll(Arr, r);
                    term.R2Command(path, Arr);
                }
            } else {
                if (cmds.getArgs().isEmpty()) {
                    String[] r = term.ls(term.getDefault_dir());
                    for (String i : r) {
                        System.out.println(i);
                    }
                } else {
                    String[] r = term.ls(cmds.getArgs().get(0));
                    for (String i : r) {
                        System.out.println(i);
                    }
                }
            }
        } else if (cmds.getCommandName().matches("rm") && cmds.getArgs().size() == 1) {
            term.rm(cmds.getArgs().get(0));
        } else if (cmds.getCommandName().matches("cp") && cmds.getArgs().size() == 2) {
            term.cp(cmds.getArgs().get(0), cmds.getArgs().get(1));
        } else if (cmds.getCommandName().matches("Touch") && cmds.getArgs().size() == 1) {
            term.Touch(cmds.getArgs());
        } else if (cmds.getCommandName().matches("cp_r") && cmds.getArgs().size() == 2) {
            System.out.print("Enter TWO Dirs >>");
            Scanner src = new Scanner(System.in);
            String src_Dir = src.nextLine();
            Scanner Dest = new Scanner(System.in);
            String Dest_Dir = src.nextLine();
            Terminal.cp_r(src_Dir, Dest_Dir);
        } else if (cmds.getCommandName().matches("cat") && cmds.getArgs().size() > 0) {
            if (cmds.getArgs().contains(">")) {
                String path = cmds.getArgs().get(cmds.getArgs().size() - 1);
                cmds.getArgs().remove(cmds.getArgs().size() - 1);
                cmds.getArgs().remove(cmds.getArgs().size() - 1);
                term.R1Command(path, term.cat(cmds.getArgs()));
            } else if (cmds.getArgs().contains(">>")) {
                String path = cmds.getArgs().get(cmds.getArgs().size() - 1);
                cmds.getArgs().remove(cmds.getArgs().size() - 1);
                cmds.getArgs().remove(cmds.getArgs().size() - 1);
                term.R2Command(path, term.cat(cmds.getArgs()));
            } else {
                for (String n : term.cat(cmds.getArgs())) {
                    System.out.println(n);
                }
            }
        } else if (cmds.getCommandName().matches("cd") && cmds.getArgs().size() == 1) {
            term.setDefault_dir(term.cd(cmds.getArgs().get(0)));
        } else if (cmds.getCommandName().matches("echo") && cmds.getArgs().size() == 1) {
            term.echo(cmds.getArgs().get(0));
        } else if ((cmds.getCommandName().matches("exit") && cmds.getArgs().isEmpty()) || ((cmds.getCommandName().matches("exit")) && (cmds.getArgs().size() == 1))) {
            if (((cmds.getCommandName().matches("exit")) && (cmds.getArgs().size() == 1))) {
                System.out.print("exit: exit [n]\n" + "    Exit the shell.\n" + "\n" + "    Exits the shell with a status of N.  If N is ignored, the exit status\n" + "    is that of the last command executed.\n" + "\n");
            }
        }
        cmds.Clear_All();
    }
}
