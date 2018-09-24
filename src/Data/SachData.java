
package Data;

import Object.Sach;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;
import javax.swing.JOptionPane;


public class SachData {
    public static PreparedStatement ps;
    public static ResultSet rs;
    
    public static ResultSet showTextfield(String sql) {
        try {
            ps = Connect.getConnect().prepareStatement(sql);
            return ps.executeQuery();
        } catch (Exception e) {
            return null;
        }
    }
    public static Vector print() {
        Vector listSach = new Vector();
        try {
            ps = Connect.getConnect().prepareStatement("select * from BangSach");
            rs = ps.executeQuery();
            while (rs.next()) {
                Vector book = new Vector();
                book.addElement(rs.getString("MaSach"));
                book.addElement(rs.getString("TenSach"));
                book.addElement(rs.getString("Tacgia"));
                book.addElement(rs.getString("Nhaxuatban"));
                book.addElement(rs.getString("TheLoai"));
                book.addElement(rs.getInt("SoLuong"));
                book.addElement(rs.getFloat("NamXB"));
                book.addElement(rs.getString("DonGia"));
               //book.addElement(rs.getString("Ten"));
                listSach.add(book);
            }
        } catch (Exception e) {
            return listSach = null;
        }

        return listSach;
    }
     public static void InsertSach(Sach s) {
        String sql = "insert into BangSach values(?,?,?,?,?,?,?,?)";
        try {
            ps = Connect.getConnect().prepareStatement(sql);
            ps.setString(1, s.getMaSach());
            ps.setString(2, s.getTenSach());
            ps.setString(3, s.getTenTacGia());
            ps.setString(4, s.getNhaXB());
            ps.setString(5, s.getTheLoai());
            ps.setInt(6, s.getSoLuong());
            ps.setInt(7, s.getNamXB());
            ps.setInt(8, s.getDonGia());
           
            ps.execute();
            JOptionPane.showMessageDialog(null, "Đã thêm sách thành công!" , "Thông báo", 1);
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, "Sách không được thêm" , "Thông báo", 1);
        }
    }
    
    public boolean UpdateSach(Sach s) {
        try {
            ps = Connect.getConnect().prepareStatement("UPDATE BangSach SET  TenSach = ?, Tacgia = ?,"
                    + "Nhaxuatban = ?, TheLoai = ?, Soluong = ?, NamXB=?, DonGia=? where MaSach = ?");
            ps.setString(1, s.getTenSach());
            ps.setString(2, s.getTenTacGia());
            ps.setString(3, s.getNhaXB());
            ps.setString(4, s.getTheLoai());
            ps.setInt(5, s.getSoLuong());
            ps.setInt(6, s.getNamXB());
            ps.setInt(7, s.getDonGia());
            ps.setString(8, s.getMaSach());
            return ps.executeUpdate() >0;
        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean DeleteSach(String ms) {
        try {
            ps = Connect.getConnect().prepareStatement("DELETE FROM BangSach WHERE MaSach = ?");
            ps.setString(1, ms);
            return ps.executeUpdate() >0;
        } catch(Exception e) {
            return false;
        }
    } 
}
