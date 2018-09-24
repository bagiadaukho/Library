
package Data;
import Data.Connect;
import java.sql.*;
import javax.swing.*;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.io.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import com.itextpdf.text.Document;
 import com.itextpdf.text.Image;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase; 
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable; 
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.text.TextAlignment;
import javax.swing.table.DefaultTableModel;
import Process.MainThuThu;
import Data.MuonTraData;
import Data.DocGiaData;
public class ExportPDF {
    public static PreparedStatement ps = null;
    public static ResultSet rs = null;
    public static Connection con = Connect.getConnect();
    public static File fontFile = new File("C:\\Users\\Admin\\Documents\\đồ án 1\\FontVuarial/vuArial.ttf");
    public static File fontFile1 = new File("C:\\Users\\Admin\\Documents\\đồ án 1\\FontVuarial/vuArialBold.ttf");
    public static File fontFile2 = new File("C:\\Users\\Admin\\Documents\\đồ án 1\\FontVuarial/vuArialItalic.ttf");
    public static File fontFile3 = new File("C:\\Users\\Admin\\Documents\\đồ án 1\\FontVuarial/vuArialBoldItalic.ttf");
public static void XuatTK( JTable table, String name, String loaithongke, String col1, String col2, String link) throws IOException, DocumentException {
        Document document = new Document();
        BaseFont bf1 = BaseFont.createFont(fontFile1.getAbsolutePath(), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font font1 = new Font(bf1,13);
        Font font2 = new Font(bf1, 15);
        BaseFont bf = BaseFont.createFont(fontFile.getAbsolutePath(), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font font = new Font(bf,11);
        Font font8 = new Font(bf1, 11);
        Font font3 = new Font(bf, 9);
        BaseFont bf2 = BaseFont.createFont(fontFile3.getAbsolutePath(), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font font4 = new Font(bf2, 11);
        try {
            PdfWriter.getInstance (document, new FileOutputStream(link + "Thống kê " + name + ".pdf"));
            document.open();
            Paragraph tieude = new Paragraph("TRƯỜNG ĐẠI HỌC BÁCH KHOA HÀ NỘI   CỘNG HÒA XÃ HỘI CHỦ NGHĨA VIỆT NAM", font1);
            tieude.setAlignment(Element.ALIGN_CENTER);
            document.add(tieude);
            Paragraph tieude0 = new Paragraph("\n Thư viện Tạ Quang Bửu                                           Độc lập-Tự do-Hạnh phúc", font1);
            tieude0.setAlignment(Element.ALIGN_CENTER);
            document.add(tieude0);
           document.add(Image.getInstance("C:\\Users\\Admin\\Desktop\\ảnh đồ án\\bk1.PNG"));
            Paragraph tieude1 = new Paragraph("    Nguyễn Thị Hải Yến ", font3);
            tieude1.setAlignment(Element.ALIGN_LEFT);
            document.add(tieude1);
            Paragraph tieude2 = new Paragraph("    MSSV: 20167045\n\n ", font3);
            tieude2.setAlignment(Element.ALIGN_LEFT);
            document.add(tieude2);
            
            Paragraph tieude3 = new Paragraph("Thống kê theo " + name + "\n ", font2);
            tieude3.setAlignment(Element.ALIGN_CENTER);
            document.add(tieude3);
            
            Paragraph ketqua = new Paragraph("                Kết quả thống kê theo " + loaithongke + ": ", font4);
            ketqua.setAlignment(Element.ALIGN_LEFT);
            document.add(ketqua);
            
            PdfPTable pdfTable = new PdfPTable(table.getColumnCount());
            pdfTable.setSpacingBefore(30);
            pdfTable.setSpacingAfter(30);
            
            pdfTable.addCell(new PdfPCell(new Phrase(col1, font8)));
            pdfTable.addCell(new PdfPCell(new Phrase(col2, font8)));
            
            //extracting data from the JTable and inserting it to PdfPTable
            for (int rows = 0; rows < table.getRowCount(); rows++) {
                for (int cols = 0; cols < table.getColumnCount(); cols++) {
                    pdfTable.addCell(new PdfPCell(new Phrase(table.getModel().getValueAt(rows, cols).toString(), font)));
                }
            }
            document.add(pdfTable);
            
            BaseFont bf3 = BaseFont.createFont(fontFile2.getAbsolutePath(), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font font6 = new Font(bf3, 11);
            Font font5 = new Font(bf1, 11);
            Paragraph ngaythang = new Paragraph("Hà Nội, ngày         tháng        năm                        ", font6);
            ngaythang.setAlignment(Element.ALIGN_RIGHT);
            document.add(ngaythang);
            
            Paragraph nguoilapbang = new Paragraph("  Xác nhận của thủ thư                                                 Người lập bảng                                      \n\n\n", font5);
            nguoilapbang.setAlignment(Element.ALIGN_RIGHT);
            document.add(nguoilapbang);
            
            Paragraph kyten = new Paragraph("Nguyễn Thị Hải Yến                                           ", font);
            kyten.setAlignment(Element.ALIGN_RIGHT);
            document.add(kyten);
             
            document.close();
            
            JOptionPane.showMessageDialog(null, "Đã xuất phiếu thống kê thành công!","Thông báo",1);
        } catch (DocumentException | FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Lỗi!","Thông báo",1);
        }
    }
 public static void XuatTimKiem(JTable table, String name, String link) throws DocumentException, IOException {
        Document document = new Document();
        BaseFont bf1 = BaseFont.createFont(fontFile1.getAbsolutePath(), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font font1 = new Font(bf1,13);
        Font font2 = new Font(bf1, 15);
        BaseFont bf = BaseFont.createFont(fontFile.getAbsolutePath(), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font font = new Font(bf,11);
        Font font3 = new Font(bf, 9);
        Font font7 = new Font(bf1, 9);
        BaseFont bf2 = BaseFont.createFont(fontFile3.getAbsolutePath(), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font font4 = new Font(bf2, 11);
       
        try {
            PdfWriter.getInstance (document, new FileOutputStream(link + "Tìm kiếm " + name + ".pdf"));
            document.open();
            Paragraph tieude = new Paragraph("TRƯỜNG ĐẠI HỌC BÁCH KHOA HÀ NỘI   CỘNG HÒA XÃ HỘI CHỦ NGHĨA VIỆT NAM", font1);
            tieude.setAlignment(Element.ALIGN_LEFT);
            document.add(tieude);
            
            Paragraph tieude0 = new Paragraph("\n Thư viện Tạ Quang Bửu                                           Độc lập-Tự do-Hạnh phúc", font1);
            tieude0.setAlignment(Element.ALIGN_LEFT);
            document.add(tieude0);
            
            document.add(Image.getInstance("C:\\Users\\Admin\\Desktop\\ảnh đồ án\\bk1.PNG"));
            
            Paragraph tieude1 = new Paragraph("    Nguyễn Thị Hải Yến ", font3);
            tieude1.setAlignment(Element.ALIGN_LEFT);
            document.add(tieude1);
            Paragraph tieude2 = new Paragraph("    MSSV: 20167045\n\n ", font3);
            tieude2.setAlignment(Element.ALIGN_LEFT);
            document.add(tieude2);
            
            Paragraph tieude3 = new Paragraph("Tìm kiếm theo " + name + "\n ", font2);
            tieude3.setAlignment(Element.ALIGN_CENTER);
            document.add(tieude3);
            
            Paragraph ketqua = new Paragraph("                Kết quả tìm kiếm: ", font4);
            ketqua.setAlignment(Element.ALIGN_LEFT);
            document.add(ketqua);
            
            PdfPTable pdfTable = new PdfPTable(table.getColumnCount());
            pdfTable.setSpacingBefore(30);
            pdfTable.setSpacingAfter(30);
            
            for (int i = 0; i < table.getColumnCount(); i++) {
                pdfTable.addCell(new PdfPCell(new Phrase(table.getColumnName(i), font7)));
            }
            //extracting data from the JTable and inserting it to PdfPTable
            for (int rows = 0; rows < table.getRowCount(); rows++) {
                for (int cols = 0; cols < table.getColumnCount(); cols++) {
                    if (table.getModel().getValueAt(rows, cols) == null)
                        pdfTable.addCell(new PdfPCell(new Phrase("", font3)));
                    else 
                        pdfTable.addCell(new PdfPCell(new Phrase(table.getModel().getValueAt(rows, cols).toString(), font3)));
                }
            }
            document.add(pdfTable);
            
            BaseFont bf3 = BaseFont.createFont(fontFile2.getAbsolutePath(), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font font6 = new Font(bf3, 11);
            Font font5 = new Font(bf1, 11);
            Paragraph ngaythang = new Paragraph("Hà Nội, ngày         tháng        năm                        ", font6);
            ngaythang.setAlignment(Element.ALIGN_RIGHT);
            document.add(ngaythang);
            
            Paragraph nguoilapbang = new Paragraph("      Xác nhận của thủ thư                                                                      Người lập bảng                                      \n\n\n", font5);
            nguoilapbang.setAlignment(Element.ALIGN_LEFT);
            document.add(nguoilapbang);
            
            Paragraph kyten = new Paragraph("Nguyễn Thị Hải Yến                                ", font);
            kyten.setAlignment(Element.ALIGN_RIGHT);
            document.add(kyten);
            
            document.close();
            JOptionPane.showMessageDialog(null, "Lưu file thành công!");
            } catch (Exception e2) {
                JOptionPane.showMessageDialog(null, "Lỗi khi lưu file!");
            }
         
    }
    public static void XuatPhieuMuon(JTable table, String name, String link) throws DocumentException, IOException {
        
        Document document = new Document();
        BaseFont bf1 = BaseFont.createFont(fontFile1.getAbsolutePath(), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font font1 = new Font(bf1,13);
        Font font2 = new Font(bf1, 15);
        BaseFont bf = BaseFont.createFont(fontFile.getAbsolutePath(), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font font = new Font(bf,11);
        Font font3 = new Font(bf, 9);
        Font font7 = new Font(bf1, 9);
        BaseFont bf2 = BaseFont.createFont(fontFile3.getAbsolutePath(), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font font4 = new Font(bf2, 11);
       
        try {
            PdfWriter.getInstance (document, new FileOutputStream(link + " " + name + ".pdf"));
            document.open();
            Paragraph tieude = new Paragraph("TRƯỜNG ĐẠI HỌC BÁCH KHOA HÀ NỘI   CỘNG HÒA XÃ HỘI CHỦ NGHĨA VIỆT NAM", font1);
            tieude.setAlignment(Element.ALIGN_CENTER);
            document.add(tieude);
            Paragraph tieude0 = new Paragraph("\n Thư viện Tạ Quang Bửu                                           Độc lập-Tự do-Hạnh phúc", font1);
            tieude0.setAlignment(Element.ALIGN_CENTER);
            document.add(tieude0);
           
            document.add(Image.getInstance("C:\\Users\\Admin\\Desktop\\ảnh đồ án\\bk1.PNG"));
            Paragraph tieude1 = new Paragraph("Nguyễn Thị Hải Yến ", font3);
            tieude1.setAlignment(Element.ALIGN_LEFT);
            document.add(tieude1);
            Paragraph tieude2 = new Paragraph("MSSV: 20167045\n\n ", font3);
            tieude2.setAlignment(Element.ALIGN_LEFT);
            document.add(tieude2);
            
            Paragraph tieude3 = new Paragraph("PHIẾU MƯỢN TRẢ \n ", font2);
            tieude3.setAlignment(Element.ALIGN_CENTER);
            document.add(tieude3);
            //PreparedStatement ps = conn.prepareStatement("Select TheLoai from BangSach group by TheLoai");
           //Statement stmt = con.createStatement();
                 PreparedStatement ps = con.prepareStatement( "SELECT MaMuonTra, BangMuonTra.MaDocGia, BangNhanVien.MaNhanVien, NgayMuon, NgayHenTra, TienCoc, TenDocGia, TenNhanVien FROM BangMuonTra join BangDocGia on BangMuonTra.MaDocGia=BangDocGia.MaDocGia join BangNhanVien on BangMuonTra.MaNhanVien=BangNhanVien.MaNhanVien");
                ResultSet rs = ps.executeQuery();
                 //ResultSet rs1 = stmt.executeQuery( "SELECT TenSach from BangChitietMuonTra join BangSach on BangChitietMuonTra.MaSach=BangSach.MaSach");
                //String sql1= "SELECT MaMuonTra, BangMuonTra.MaDocGia, BangNhanVien.MaNhanVien, NgayMuon, NgayHenTra, TienCoc, TenDocGia, TenNhanVien FROM BangMuonTra join BangDocGia on BangMuonTra.MaDocGia=BangDocGia.MaDocGia join BangNhanVien on BangMuonTra.MaNhanVien=BangNhanVien.MaNhanVien";
                 //ps = con.prepareStatement(sql1);
                // rs=ps.executeQuery(sql1);
                if(rs.next()){
                    String MaMuonTra = rs.getString(1);
                    String MaDocGia = rs.getString(2);
                    String MaNhanVien = rs.getString(3);
                    String NgayMuon = rs.getString(4);
                    String NgayHenTra = rs.getString(5);
                    String TienCoc = rs.getString(6);
                    String TenDocGia = rs.getString(7);
                    String TenNhanVien = rs.getString(8);
            Paragraph ketqua = new Paragraph("               Mã mượn trả:          "+MaMuonTra+ " \n", font4);
            ketqua.setAlignment(Element.ALIGN_LEFT);
            document.add(ketqua);
            Paragraph ketqua1 = new Paragraph("              Mã độc giả:             "+MaDocGia+"\n" , font4);
            ketqua1.setAlignment(Element.ALIGN_LEFT);
            document.add(ketqua1);
            
            Paragraph ketqua6 = new Paragraph("              Tên độc giả:            "+TenDocGia+"\n" , font4);
            ketqua6.setAlignment(Element.ALIGN_LEFT);
            document.add(ketqua6);
/*
            Paragraph ketqua11 = new Paragraph("              Tên sách:               "+TenSach+"\n" , font4);
            ketqua11.setAlignment(Element.ALIGN_LEFT);
            document.add(ketqua11);
*/
            Paragraph ketqua2 = new Paragraph("              Mã nhân viên:           "+MaNhanVien+"\n" , font4);
            ketqua2.setAlignment(Element.ALIGN_LEFT);
            document.add(ketqua2);
            Paragraph ketqua7 = new Paragraph("              Tên nhân viên:          "+TenNhanVien+"\n" , font4);
            ketqua7.setAlignment(Element.ALIGN_LEFT);
            document.add(ketqua7);
            Paragraph ketqua3 = new Paragraph("              Ngày mượn:              "+NgayMuon+"\n", font4);
            ketqua3.setAlignment(Element.ALIGN_LEFT);
            document.add(ketqua3);
            Paragraph ketqua4 = new Paragraph("              Ngày hẹn trả:           "+NgayHenTra+"\n", font4);
            ketqua4.setAlignment(Element.ALIGN_LEFT);
            document.add(ketqua4);

            Paragraph ketqua5 = new Paragraph("              Tiền cọc:              "+TienCoc+"\n", font4);
            ketqua5.setAlignment(Element.ALIGN_LEFT);
            document.add(ketqua5);}
           
           
                    
            PdfPTable pdfTable = new PdfPTable(table.getColumnCount());
            pdfTable.setSpacingBefore(30);
            pdfTable.setSpacingAfter(30);
            
            for (int i = 0; i < table.getColumnCount(); i++) {
                pdfTable.addCell(new PdfPCell(new Phrase(table.getColumnName(i), font7)));
            }
            //extracting data from the JTable and inserting it to PdfPTable
            for (int rows = 0; rows < table.getRowCount(); rows++) {
                for (int cols = 0; cols < table.getColumnCount(); cols++) {
                    if (table.getModel().getValueAt(rows, cols) == null)
                        pdfTable.addCell(new PdfPCell(new Phrase("", font3)));
                    else 
                        pdfTable.addCell(new PdfPCell(new Phrase(table.getModel().getValueAt(rows, cols).toString(), font3)));
                }
            }
            document.add(pdfTable);
            
            BaseFont bf3 = BaseFont.createFont(fontFile2.getAbsolutePath(), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font font6 = new Font(bf3, 11);
            Font font5 = new Font(bf1, 11);
            Paragraph ngaythang = new Paragraph("Hà Nội, ngày         tháng        năm                        ", font6);
            ngaythang.setAlignment(Element.ALIGN_RIGHT);
            document.add(ngaythang);
            
            Paragraph nguoimuon = new Paragraph("                            Người mượn                                     ", font5);
            nguoimuon.setAlignment(Element.ALIGN_LEFT);
            document.add(nguoimuon);
            
            Paragraph nguoilapbang = new Paragraph("Người lập bảng                                      \n\n\n", font5);
            nguoilapbang.setAlignment(Element.ALIGN_RIGHT);
            document.add(nguoilapbang);
            
            Paragraph kyten = new Paragraph("Nguyễn Thị Hải Yến                                ", font);
            kyten.setAlignment(Element.ALIGN_RIGHT);
            document.add(kyten);
            
            document.close();
            JOptionPane.showMessageDialog(null, "Lưu file thành công!");
            } catch (Exception e2) {
                JOptionPane.showMessageDialog(null, "Lỗi khi lưu file!");
            }
         
    }
    
    
      
}

