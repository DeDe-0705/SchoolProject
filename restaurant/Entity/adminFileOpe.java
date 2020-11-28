
import java.io.FileReader;
import java.io.PrintStream;
import java.util.Properties;
import javax.swing.JOptionPane;

public class adminFileOpe {
    private static String fileName="manager.inc";
    private static Properties pps;
    static {
        pps=new Properties();
        FileReader reader=null;
        try {
            reader=new FileReader(fileName);
            pps.load(reader);
        }
        catch (Exception ex) {
            JOptionPane.showMessageDialog(null,"File Error!");
            System.exit(0);
        }
        finally {
            try {
                reader.close();
            }
            catch (Exception ex) {}
        }
    }
    private static void listInfo() {
        PrintStream ps=null;
        try {
            ps=new PrintStream(fileName);
            pps.list(ps);
        }
        catch (Exception ex) {
            JOptionPane.showMessageDialog(null,"File Error!");
            System.exit(0);
        }
        finally {
            try {
                ps.close();
            }
            catch (Exception ex) {}
        }
    }
    public static void getInfoByadmin(String account) {
        String cusInfo=pps.getProperty(account);
        if(cusInfo!=null) { 
            String[] infos=cusInfo.split("#");
            Configure.adminid=account;
            Configure.firstname=infos[0];
            Configure.surname=infos[1];
        }
    }
    public static void updateadmin(String account,String firstname,String surname) {
        pps.setProperty(account,firstname+"#"+surname);
        listInfo();
    }
}
