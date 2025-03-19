package atm.machine;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class Otp {
    public void sendOtp(String email, int userid){
        Random ran = new Random();
        int otp = ran.nextInt(900000) + 100000;
        Conn c=null;
        try {
            c=new Conn();
            String q="select account_id from accounts where user_id=?";
            PreparedStatement pp=c.connection.prepareStatement(q);
            pp.setInt(1,userid);
            ResultSet ss= pp.executeQuery();
            if (ss.next()){
                int accounid=ss.getInt("account_id");
                String k="Insert into otp_verification (account_id,otp_code,expires_at,status) values(?,?,?,?)";
                PreparedStatement pt=c.connection.prepareStatement(k);
                pt.setInt(1,accounid);
                pt.setInt(2,otp);
                LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(2);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                pt.setString(3, expiresAt.format(formatter));
                pt.setString(4,"pending");
                pt.executeUpdate();
                pt.close();
            }
            pp.close();
            ss.close();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            c.closeConnection();
        }
        Email e=new Email();
        e.OtpEmail(otp,email);
    }

    public  boolean verifyOtp(int enteredOTP,int accountid){
        Conn c=null;
        try  {
             c=new Conn();
            String query = "SELECT otp_code, expires_at FROM otp_verification WHERE account_id = ? ORDER BY expires_at DESC LIMIT 1";

            PreparedStatement ps = c.connection.prepareStatement(query);
            ps.setInt(1, accountid);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int storedOTP = rs.getInt("otp_code");
                Timestamp expiresAt = rs.getTimestamp("expires_at");

                if (LocalDateTime.now().isBefore(expiresAt.toLocalDateTime()) && storedOTP==enteredOTP) {
                    markOTPAsUsed(accountid);
                    return true;
                }
            }else {
                System.out.println("otp verify error");
            }
            ps.close();
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            c.closeConnection();
        }
        return false;
    }

    private void markOTPAsUsed(int accountid) {
        Conn c=null;
        try {
            c=new Conn();
            String query = "UPDATE otp_verification SET status = 'Used' WHERE account_id =?";
            PreparedStatement ps = c.connection.prepareStatement(query);
            ps.setInt(1, accountid);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (c!=null){
                c.closeConnection();
            }

    }
}
}
