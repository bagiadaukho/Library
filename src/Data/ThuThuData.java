
package Data;
import Object.ThuThu;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;
import javax.swing.JOptionPane;


public class ThuThuData {
   public static PreparedStatement ps;
    public static ResultSet rs;
    
    public ThuThu dangNhap(String taiKhoan, String pass) {
        ThuThu tt = null;
        try{
            ps = Connect.getConnect().prepareStatement("SELECT * FROM BangNhanVien where MaNhanVien = ? and MaNhanVien=?");
            ps.setString(1, taiKhoan);
            ps.setString(2, pass);
            rs = ps.executeQuery();
            while(rs.next()) {
                tt= new ThuThu();
                //kh.setMaKH(rs.getString("Ma_Khach_hang"));
               // kh.setPass(rs.getString("Password"));
                tt.setMaThuThu(rs.getString("MaNhanVien"));
                tt.setTenTT(rs.getString("TenNhanVien"));
                tt.setNgaysinh(rs.getDate("Ngaysinh"));
                tt.setGioitinh(rs.getString("Gioitinh"));
               
                tt.setEmail(rs.getString("Email"));
                tt.setSDT(rs.getString("Sodienthoai"));
               tt.setDiachi(rs.getString("Diachi"));
                
            }
        }
        catch(Exception e) {
            return tt = null;
        }
        return tt;
    }
    
    public Vector print() {
        Vector listthuthu = new Vector();
        try {
            ps = Connect.getConnect().prepareStatement("select * from BangNhanVien");
            rs = ps.executeQuery();
            while (rs.next()) {
                Vector thuthu = new Vector();
                thuthu.addElement(rs.getString("MaNhanVien"));
                thuthu.addElement(rs.getString("TenNhanVien"));
                thuthu.addElement(rs.getString("Ngaysinh"));
                thuthu.addElement(rs.getString("Gioitinh"));
                thuthu.addElement(rs.getString("Email"));
                thuthu.addElement(rs.getString("Sodienthoai"));
                thuthu.addElement(rs.getString("DiaChi"));
                listthuthu.add(thuthu);
            }
        } catch (Exception e) {
            return listthuthu = null;
        }
        return listthuthu;
    }
    public static ResultSet showTextfield(String sql) {
        try {
            ps = Connect.getConnect().prepareStatement(sql);
            return ps.executeQuery();
        } catch (Exception e) {
            return null;
        }
    }
    
     public static void InsertThuThu(ThuThu tt) {
        String sql = "insert into BangNhanVien values(?,?,?,?,?,?,?)";
        try {
    
            ps = Connect.getConnect().prepareStatement(sql);
            ps.setString(1, tt.getMaThuThu());
            ps.setString(2, tt.getTenTT());
            ps.setDate(3, tt.getNgaysinh());
            ps.setString(4, tt.getGioitinh());
            ps.setString(5, tt.getEmail());
            ps.setString(6, tt.getSDT() );
            ps.setString(7, tt.getDiachi() );
            
            ps.execute();
            
            JOptionPane.showMessageDialog(null, "Đã thêm Nhân viên thành công!" , "Thông báo", 1);
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, "Nhân viên không được thêm" , "Thông báo", 1);
        }
    }
    
    public boolean UpdateThuThu(ThuThu tt) {
        try {
            ps = Connect.getConnect().prepareStatement("UPDATE BangNhanVien SET TenNhanVien = ?, Ngaysinh = ?,"
                    + "Gioitinh = ?, Email = ?, Sodienthoai = ?, Diachi = ? where MaNhanVien = ?");
            ps.setString(7, tt.getMaThuThu());
            ps.setString(1, tt.getTenTT());
            ps.setDate(2, tt.getNgaysinh());
            ps.setString(3, tt.getGioitinh());
            
            ps.setString(4, tt.getEmail());
            ps.setString(5, tt.getSDT());
            ps.setString(6, tt.getDiachi());
            return ps.executeUpdate() >0;
        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean DeleteThuThu(String MaThuThu) {
        try {
            ps = Connect.getConnect().prepareStatement("DELETE FROM BangNhanVien WHERE MaNhanVien = ?");
            ps.setString(1, MaThuThu);
            return ps.executeUpdate() >0;
        } catch(Exception e) {
            return false;
        }
    }
}
    