
package Data;

//import static Data.MuonTraData.ps;
//import static Data.MuonTraData.ps;



import java.sql.Connection;
import java.sql.Date;
//import Object.MuonTra;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ChitietmuontraData {
   public static Connection con = Connect.getConnect();
     public static PreparedStatement ps;
     public static ResultSet rs;
     private String table1 = "thongtinchitietmuontra";
      public static ResultSet showTextfield(String sql) {
        try {
            ps = Connect.getConnect().prepareStatement(sql);
            return ps.executeQuery();
        } catch (Exception e) {
            return null;
        }
      }
       public static void InsertChiTiet(String MaMuonTra, String MaSach, Date NgayTra, int TienPhat,String GhiChu)
    {
        String sql="INSERT INTO BangChitietMuonTra VALUES (?,?,?,?,?)";
        try{
           ps= con.prepareStatement(sql);
           ps.setString(1,MaMuonTra);
           ps.setString(2,MaSach);
           ps.setDate(3,NgayTra);
           ps.setInt(4,TienPhat);
           ps.setString(5,GhiChu);
          
           ps.execute();
           JOptionPane.showMessageDialog(null, "Đã thêm thành công!", "Thông báo", 1);
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "Phiếu mã " + MaMuonTra + "đã tồn tại!", "Thông báo", 1);
            System.err.println(e.toString());
        }
    }
    public static void UpdateChiTiet( String maphieu1 ,String MaMuonTra,String MaSach,Date NgayTra, int TienPhat,String GhiChu) {
        String sql = "UPDATE BangChitietMuonTra SET MaMuonTra = '" + MaMuonTra + "',MaSach = '" + MaSach+ "',Ngaytra= '" + NgayTra + "',"
                + "TienPhat = '" + TienPhat + "',GhiChu = '" + GhiChu+ "'  WHERE MaMuonTra= '" + maphieu1 + "'and MaSach='"+MaSach+"'";
        try {
            ps = con.prepareStatement(sql);
            ps.execute();
            JOptionPane.showMessageDialog(null, "Đã sửa thông tin  thành công!", "Thông báo", 1);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Không thể sửa thông tin !", "Thông báo", JOptionPane.ERROR_MESSAGE);
            System.err.println(ex.toString());
        }
    }
    
    public static void DeleteChiTiet(String MaPhieu) {
        String sql = "DELETE FROM BangChitietMuonTra WHERE MaMuonTra = '" + MaPhieu + "'";
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