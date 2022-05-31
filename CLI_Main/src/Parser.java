import java.util.ArrayList;

/**
 *
 * @author moham
 */
public class Parser {

    /**
     * Array list for Arguments.
     */
    ArrayList<String> args = new ArrayList<>();

    /**
     * cmd
     */
    String Cmd_Line;

    /**
     *
     * @param In
     * @return divide the input into commandName and arguments.
     */
    public boolean parse(String In) {

        In = In.trim().replaceAll(" +", " ");  //To remove duplication .
        Fill(In);

        if (Cmd_Line.matches("echo") && args.size() == 1) {
            return true;
        } else if ((Cmd_Line.matches("exit") && args.isEmpty()) || ((Cmd_Line.matches("exit")) && (args.size() == 1))) {
            return true;
        } else if (Cmd_Line.matches("pwd") && args.isEmpty()) {
            return true;
        } else if (Cmd_Line.matches("cd") && args.size() == 1) {
            return true;
        } else if (Cmd_Line.matches("ls")) {
            return true;
//        } else if (Cmd_Line.matches("ls_r")) {
//            return true;
        } else if (Cmd_Line.matches("mkdir")) {
            return true;
        } else if (Cmd_Line.matches("rmdir") && args.size() == 1) {
            return true;
        } else if (Cmd_Line.matches("touch") && args.size() == 1) {
            return true;
        } else if (Cmd_Line.matches("cp") && args.size() == 2) {
            return true;
        } else if (Cmd_Line.matches("cp_r") && args.size() == 2) {
            return true;
        } else if (Cmd_Line.matches("rm") && args.size() == 1) {
            return true;
        } else if (Cmd_Line.matches("cat") && args.size() > 0) {
            return true;
        } else {
            System.out.print(In + ": command not found\n");
            Clear_All();
            return false;
        }
    }

    /**
     *
     * @param In
     * @return set Arguments in Args & command line in cmd_Lines .
     */
    public void Fill(String In) {
        String[] Args_arr;
        if (!In.contains(" ")) {
            Cmd_Line = In;
        } else {
            Cmd_Line = In.substring(0, In.indexOf(" "));
            In = In.substring(In.indexOf(" ") + 1, In.length());
            Args_arr = In.split(" ");
            String a = "";
            for (int n = 0; n < Args_arr.length; ++n) {
                if (Args_arr[n].charAt(0) == '"') {
                    a = Args_arr[n].substring(1, Args_arr[n].length()) + " ";
                    n++;
                    while (!(Args_arr[n].charAt(Args_arr[n].length() - 1) == '"')) {
                        a += Args_arr[n] + " ";
                        n++;
                    }
                    a += Args_arr[n].substring(0, Args_arr[n].length() - 1);
                    args.add(a);
                } else {
                    args.add(Args_arr[n]);
                }
            }
        }
    }

    /**
     * @return to clear Cmd_Line and the array of args after completing the
     * command.
     */
    public void Clear_All() {
        Cmd_Line = "";
        args.clear();
    }

    /**
     *
     * @return get cmd line.
     */
    public String getCommandName() {
        return Cmd_Line;
    }

    /**
     *
     * @return Get Arguments.
     */
    public ArrayList<String> getArgs() {
        return args;
    }

    /**
     *
     * @param Cm set cmd lines.
     */
    public void setCommandName(String Cm) {
        Cmd_Line = Cm;
    }

   
}
