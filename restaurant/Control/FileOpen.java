

import java.io.FileReader;
import java.io.PrintStream;
import java.util.Properties;
import javax.swing.JOptionPane;

public class FileOpen {
	private static String fileName="customer.inc";
    private static Properties pps;
    static {
        pps=new Properties();
        FileReader reader=null;
        try {
            reader=new FileReader(fileName);
            pps.load(reader);
        }
        catch (Exception ex) {
            JOptionPane.showMessageDialog(null,"no file");
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
            JOptionPane.showMessageDialog(null,"no file");
            System.exit(0);
        }
        finally {
            try {
                ps.close();
            }
            catch (Exception ex) {}
        }
    }
    public static void getInfoByAccount(String membershipnumber) {
        String cusInfo=pps.getProperty(membershipnumber);
        if(cusInfo!=null) {
            String[] infos=cusInfo.split("#");
            Member.membershipnumber=membershipnumber;
            Member.firstname=infos[0];
            Member.surname=infos[1];
            Member.mobilenumber=infos[2];
            Member.email=infos[3];
            Member.stampnum=infos[4];
        }
    }
    public static void updateCustomer(String membershipnumber,String firstname,
            String surname,String mobilenumber,String email,String stampnum) {
    		pps.setProperty(membershipnumber,firstname+"#"+surname+"#"+mobilenumber+"#"+email+"#"+stampnum);
    		listInfo();
    }
}