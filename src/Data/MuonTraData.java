
package Data;

import Object.MuonTra;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
public class MuonTraData {
     public static Connection con = Connect.getConnect();
     public static PreparedStatement ps;
     public static ResultSet rs;

    

    
     private String table1 = "muontra";
      public static ResultSet showTextfield(String sql) {
        try {
            ps = Connect.getConnect().prepareStatement(sql);
            return ps.executeQuery();
        } catch (Exception e) {
            return null;
        }
      
    
    }
     public static void InsertMuonTra(String MaMuonTra,String MaDocGia, String MaNhanVien, Date NgayMuon, Date NgayHenTra, int TienCoc)
    {
        String sql="INSERT INTO BangMuonTra VALUES (?,?,?,?,?,?)";
        try{
           ps= con.prepareStatement(sql);
           ps.setString(1,MaMuonTra);
           ps.setString(2,MaDocGia);
           ps.setString(3,MaNhanVien);
           ps.setDate(4,NgayMuon);
           ps.setDate(5,NgayHenTra);
           ps.setInt(6,TienCoc);
          
           ps.execute();
           JOptionPane.showMessageDialog(null, "Đã thêm thành công!", "Thông báo", 1);
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "Phiếu mã " + MaMuonTra + "đã tồn tại!", "Thông báo", 1);
            System.err.println(e.toString());
        }
    }
    public static void UpdateMuonTra( String maphieu1 ,String MaPhieu,String MaDocGia, String MaNhanVien, Date NgayMuon, Date NgayHenTra, int TienCoc) {
        String sql = "UPDATE BangMuonTra SET MaMuonTra = '" + MaPhieu + "',MaDocGia = '" + MaDocGia + "',MaNhanVien = '" + MaNhanVien + "',NgayMuon = '" + NgayMuon + "',"
                + "NgayHenTra = '" + NgayHenTra + "',TienCoc = '" + TienCoc + "'  WHERE MaMuonTra= '" + maphieu1 + "'";
        try {
            ps = con.prepareStatement(sql);
            ps.execute();
            JOptionPane.showMessageDialog(null, "Đã sửa thông tin mượn trả thành công!", "Thông báo", 1);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Không thể sửa thông tin mượn trả!", "Thông báo", JOptionPane.ERROR_MESSAGE);
            System.err.println(ex.toString());
        }
    }
    
    public static void DeleteMuonTra(String MaPhieu) {
        String sql = "DELETE FROM BangMuonTra WHERE MaMuonTra = '" + MaPhieu + "'";
        try {
            ps = con.prepareStatement(sql);
            ps.execute();
            JOptionPane.showMessageDialog(null, "Đã xóa phiếu mã " + MaPhieu+ "thành công!", "Thông báo", 1);
           
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Không thể xóa phiếu mã " + MaPhieu + "!", "Thông báo", JOptionPane.ERROR_MESSAGE);
            System.err.println(ex.toString());
        }
    }
}