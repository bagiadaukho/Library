
package Data;
import Object.DocGia;
import Object.QuanLy;
import Object.ThuThu;
import static Data.DocGiaData.ps;
import static Data.ThuThuData.ps;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class QuanLyData {
    public static PreparedStatement ps;
    public static ResultSet rs;
    
    public QuanLy dangNhap(String taiKhoan, String pass) {
        QuanLy ad = null;
        try{
            ps = Connect.getConnect().prepareStatement("SELECT * FROM LoginQuanLy1 where Username1 = ? and Password1=?");
            ps.setString(1, taiKhoan);
            ps.setString(2, pass);
            rs = ps.executeQuery();
            while(rs.next()) {
                ad = new QuanLy();
               // kh.setMaKH(rs.getString("Ma_Khach_hang"));
               // kh.setPass(rs.getString("Password"));
                
            }
        }
        catch(Exception e) {
            return ad = null;
        }
        return ad;
    }
    
    public static ResultSet showTextfield(String sql) {
        try {
            ps = Connect.getConnect().prepareStatement(sql);
            return ps.executeQuery();
        } catch (Exception e) {
            return null;
        }
        
    }
    
    public boolean UpdateAdmin(QuanLy ad) {
        try {
            ps = Connect.getConnect().prepareStatement("UPDATE LoginQuanLy SET Password1 = ? where usename1 = ?");
            ps.setString(2, ad.getusenameQuanLy());
            ps.setString(1, ad.getPasswordQuanLy());
            return ps.executeUpdate() >0;
        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean DeleteAdmin(String maAd) {
        try {
            ps = Connect.getConnect().prepareStatement("DELETE FROM LoginQuanLy WHERE usename1 = ?");
            ps.setString(1, maAd);
            return ps.executeUpdate() >0;
        } catch(Exception e) {
            return false;
        }
    }

  
}
