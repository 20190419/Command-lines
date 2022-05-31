
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 *
 * @author moham
 */
public class Terminal {

    /**
     *
     */
    private String Default_dir = System.getProperty("user.home") + "\\";

    /**
     * Takes 1 argument and prints it.
     *
     * @param a
     */
    public void echo(String a) {
        System.out.println(a);
    }

    /**
     * print current directory.
     */
    public void pwd() {
        System.out.println((Default_dir));
    }

    /**
     *
     * @param path
     * @return change The dir to a spiefic Directory.
     */
    public String cd(String path) {
        //check if the directory is available then come back

        File file = new File(path);

        File fileSameDir = new File(Default_dir + path);

        if (path.equals("...")) {

            int counter = 0;
            int in = Default_dir.length();
            boolean found = false;

            for (int i = 0; i < Default_dir.length(); i++) {

                if ((Default_dir.charAt(i) == '\\' || Default_dir.charAt(i) == '/')) {
                    found = true;
                } else if (found) {
                    in = i - 1;
                    found = false;
                }

            }
            System.out.println("The Dir will change to " + (Default_dir.substring(0, in + 1)));
            return Default_dir.substring(0, in + 1);
        }

        if (path.toLowerCase().equals("desktop")) {

            System.out.println("The Dir will change to " + ("Desktop"));

            return (System.getProperty("user.home") + "\\Desktop" + "\\");
        }

        if (fileSameDir.exists()) {

            System.out.println("The Dir will change to " + (path));

            return (Default_dir + path + '\\');

        } else if (file.exists()) {

            System.out.println("The Dir will change to " + (path));

            return path + "\\";

        
        } else {
            System.out.println("The path may not be available using /mkdir/ command to directory creation prosses");
            return Default_dir;
        }

    }

    /**
     *
     * @param current_path
     * @return list all files in current directory.
     */
    public String[] ls(String current_path) {
        try {
            File Data_Of_Files = new File(current_path);
            if (Data_Of_Files.exists()) {
                String[] Arr2 = Data_Of_Files.list();
                if (Arr2.length <= 0) {
                    System.out.println("Dir is empty");
                } else {
                    return Arr2;
                }
            } else {
                System.out.print("The Path is not avail");
                String[] arr2 = {"Dir is empty"};
                return arr2;
            }
        } catch (Exception x) {
            System.out.println("Error in path");
        }
        String[] Arr2 = {"Dir is empty"};
        return Arr2;
    }

//    public void ls_r(String Path) throws FileNotFoundException {
//        String[] args = null;
//        String path = args[0];
//        File dir = new File(path);
//        FileInputStream fis = new FileInputStream(dir);
//        if ((dir).isDirectory()) {
//            File[] files = dir.listFiles();
//
//            reverse(files, files.length);
//            System.out.println(files);
//        }
//    }
    /**
     *
     * @param PaTh
     * @param bool Create a new folder.
     */
    public void mkdir(String PaTh, Boolean bool) {

        File file = new File(PaTh);
        File file2 = new File(Default_dir + PaTh);

        if (file2.exists()) {
            if (bool) {
                System.out.println(PaTh + (" Exists "));
            }

        } else if (file2.mkdir()) {
            if (bool) {
                System.out.println(PaTh + ("it has been created."));
            }

        } else if (file.exists()) {
            if (bool) {
                System.out.println(PaTh + (" Exists"));
            }

        } else if (file.mkdir()) {
            if (bool) {
                System.out.println(PaTh + (" Folder has been created."));
            }

        } else {
            System.out.println((" Error, path may not be correct"));
        }
    }

    /**
     *
     * @param path
     */
    public void rmdir(String path) {

        File file = new File(path);
        File file_Same_Dir = new File(Default_dir + path);
        String[] f1 = file.list();
        String[] f2 = file_Same_Dir.list();

        if (file_Same_Dir.exists()) {
            if (f2.length <= 0) {
                file_Same_Dir.delete();
                System.out.println(path + (" File has been deleted."));
            } else {
                System.out.println(("The Dir is not empty"));
            }
        } else if (file.exists() && f1.length <= 0) {
            if (f1.length <= 0) {
                file.delete();
                System.out.println(path + (" File has been deleted."));
            } else {
                System.out.println(("The Dir is not empty"));
            }
        } else {
            System.out.println(("Dir does not exist use ( mkdir " + path + " ) to create"));

        }
    }

    /**
     *
     * @param dir
     * @param file_name Takes full path or the relative path that ends with a
     * file name and creates this file.
     */
    public void touch(String path) throws IOException {
        File file = new File(path);
        file.createNewFile();
        System.out.println("File has been Created");
    }

    /**
     *
     * @param src_Path
     * @param dest_Path
     * @return copy the source to the destination.
     */
    public Boolean cp(String src_Path, String dest_Path) throws IOException {

        String a = "";
        File f = new File(src_Path);
        File f1 = new File(Default_dir + src_Path);
        Boolean copy = true;
        if (f1.exists()) {
            a = Default_dir + src_Path;
        } else if (f.exists()) {
            a = src_Path;
        } else {
            System.out.println("We can't find src to copy");
            return false;
        }

        FileWriter writer;
        Scanner read;
        try ( FileReader myReader = new FileReader(a)) {
            File file1 = new File(dest_Path);
            File file2 = new File(Default_dir + dest_Path);
            String b = "";
            if (file2.exists()) {
                b = Default_dir + dest_Path;
            } else if (file1.exists()) {
                b = dest_Path;
            } else {
                try {
                    if (file2.createNewFile()) {
                        b = Default_dir + dest_Path;
                        copy = false;
                    }
                } catch (IOException e) {
                }

                if (copy) {
                    try {
                        if (file1.createNewFile()) {
                            b = dest_Path;
                        }
                    } catch (IOException e) {
                        System.out.println("Error");
                        return false;
                    }
                }
            }
            writer = new FileWriter(b);
            read = new Scanner(myReader);
            String q;
            while (read.hasNextLine()) {
                q = read.nextLine();
                writer.write(q + "\n");
            }
        }
        writer.close();
        read.close();
        return true;

    }

    /**
     *
     * @param src_Dir_PATH
     * @param dest_Dir_PATH
     * @return copy the source dir to the destination Dir.
     */
    public static void cp_r(String src_Dir_PATH, String dest_Dir_PATH) throws IOException {
        File file = new File("destination_Dir_Path");
        if (file.exists()) {

            System.out.print("Folder in : " + file.getAbsolutePath() + " is already exist you must copy to a new directory\n");

        } else {
            Files.walk(Paths.get(src_Dir_PATH)).forEach(src -> {
                Path dest = Paths.get(dest_Dir_PATH, src.toString()
                        .substring(src_Dir_PATH.length()));
                try {
                    Files.copy(src, dest);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            System.out.print("Directory copied sucssfully\n");
        }
    }

    

    /**
     *
     * @param src_Path remove Data from file of current path.
     */
    public void rm(String src_Path) {
        File f = new File(src_Path);
        File f1 = new File(Default_dir + src_Path);
        if (f1.exists()) {
            if (f1.delete()) {
                System.out.println("it has been deleted");
            } else {
                System.out.println("it hasn't been deleted");
            }
        } else if (f.exists()) {
            if (!f.delete()) {
                System.out.println("it hasn't been deleted");
            } else {
                System.out.println("it has been deleted");
            }
        }

    }

    /**
     *
     * @param a
     * @return (1) cat [OPTION]... [FILE]... : The print contains these files.
     */
    public ArrayList<String> cat(ArrayList<String> a) throws FileNotFoundException {
        ArrayList<String> r = new ArrayList<>();
        String b;
        for (String n : a) {
            File f = new File(n);
            File f2 = new File(Default_dir + n);
            String x;
            if (f2.exists()) {
                x = Default_dir + n;
            } else if (f.exists()) {
                x = n;
            } else {
                System.out.println("File " + n + " Not existed");
                continue;
            }
            FileReader f1 = new FileReader(x);
            try ( Scanner in = new Scanner(f1)) {
                while (in.hasNextLine()) {
                    b = in.nextLine();
                    r.add(b);
                }
            }
        }
        return r;
    }

    /**
     *
     * @param newDefault_dir Set Default Dir
     */
    public void setDefault_dir(String newDefault_dir) {
        Default_dir = newDefault_dir;
    }

    public void R2Command(String path, ArrayList<String> content) throws IOException {
        File f = new File(path);
        File f1 = new File(Default_dir + path);
        String e;
        if (f1.exists()) {
            e = Default_dir + path;
        } else if (f.exists()) {
            e = path;
        } else {
            System.out.println("We couldn't find the src to copy");
            return;
        }
        try ( BufferedWriter file = new BufferedWriter(new FileWriter(e, true))) {
            for (String i : content) {
                file.write(i + "\n");
            }
        }
    }

    /**
     *
     * @return Get Default directory.
     */
    public String getDefault_dir() {
        return Default_dir;
    }

    /**
     *
     * @param args
     */
    void Touch(ArrayList<String> args) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void reverse(File[] files, int length) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    public void R1Command(String dest_Path, ArrayList<String> content) throws IOException {

        Boolean bool = true;
        File file1 = new File(dest_Path);
        File file2 = new File(Default_dir + dest_Path);
        String x = "";
        if (file2.exists()) {
            x = Default_dir + dest_Path;
        } else if (file1.exists()) {
            x = dest_Path;
        } else {
            try {
                if (file2.createNewFile()) {
                    x = Default_dir + dest_Path;
                    bool = false;
                }
            } catch (IOException e) {
            }

            if (bool) {
                try {
                    if (file1.createNewFile()) {
                        x = dest_Path;
                    }
                } catch (IOException e) {
                    System.out.println("Error");
                    return;
                }
            }
        }
        try ( FileWriter file = new FileWriter(x)) {
            for (String o : content) {
                file.write(o + "\n");
            }
        }
    }
}
