
package Data;
import Object.DocGia;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class DocGiaData {
    public static PreparedStatement ps;
    public static ResultSet rs;
    private String table1 = "docgia";
    public DocGia dangNhap(String taiKhoan, String pass) {
        DocGia dg = null;
        try{
            ps = Connect.getConnect().prepareStatement("SELECT * FROM BangDocGia where MaDocGia = ? and MaDocGia=?");
            ps.setString(1, taiKhoan);
            ps.setString(2, pass);
            rs = ps.executeQuery();
            while(rs.next()) {
                dg= new DocGia();
               // kh.setMaKH(rs.getString("Ma_Khach_hang"));
               // kh.setPass(rs.getString("Password"));
                //dg.setMaDocGia(rs.getString("Mã Độc Giả"));
                dg.setTenDG(rs.getString("TenDocGia"));
                dg.setNgaysinh(rs.getDate("Ngaysinh"));
                dg.setGioitinh(rs.getString("Gioitinh"));
                dg.setCMND(rs.getString("CMND"));
                dg.setEmail(rs.getString("Email"));
                dg.setSDT(rs.getString("Sodienthoai"));
                dg.setDiachi(rs.getString("Diachi"));
                
            }
        }
        catch(Exception e) {
            return dg = null;
        }
        return dg;
    }
    
    
    public static ResultSet showTextfield(String sql) {
        try {
            ps = Connect.getConnect().prepareStatement(sql);
            return ps.executeQuery();
        } catch (Exception e) {
            return null;
        }
    }
    
     public static void InsertDocGia(DocGia dg) {
        String sql = "insert into BangDocGia values(?,?,?,?,?,?,?,?)";
        try {
            ps = Connect.getConnect().prepareStatement(sql);
            ps.setString(1, dg.getMaDocGia());
            ps.setString(2, dg.getTenDG());
            ps.setDate(3, dg.getNgaysinh());
            ps.setString(4, dg.getGioitinh());
            ps.setString(5, dg.getCMND());
            ps.setString(6, dg.getEmail());
            ps.setString(7, dg.getSDT() );
            ps.setString(8, dg.getDiachi() );
            
            ps.execute();
            JOptionPane.showMessageDialog(null, "Đã thêm Độc giả thành công!" , "Thông báo", 1);
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, "Độc giả không được thêm!" , "Thông báo", 1);
        }
    }
    
    public boolean UpdateDocGia(DocGia dg) {
        try {
            ps = Connect.getConnect().prepareStatement("UPDATE BangDocGia SET TenDocGia = ?, Ngaysinh = ?,"
                    + "Gioitinh = ?, CMND= ?, Email = ?, Sodienthoai = ?, Diachi = ? where MaDocGia = ?");
            ps.setString(8, dg.getMaDocGia());
            ps.setString(1, dg.getTenDG());
            ps.setDate(2, dg.getNgaysinh());
            ps.setString(3, dg.getGioitinh());
            ps.setString(4, dg.getCMND());
            ps.setString(5, dg.getEmail());
            ps.setString(6, dg.getSDT());
            ps.setString(7, dg.getDiachi());
            return ps.executeUpdate() >0;
        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean DeleteDocGia(String maKH) {
        try {
            ps = Connect.getConnect().prepareStatement("DELETE FROM BangDocGia WHERE MaDocGia = ?");
            ps.setString(1, maKH);
            return ps.executeUpdate() >0;
        } catch(Exception e) {
            return false;
        }
    }
    
}
