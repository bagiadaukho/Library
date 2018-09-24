
package Object;

import java.sql.Date;
import java.lang.String;


public class MuonTra {
  private String table1 = "muontra";
     private String MaPhieu;
    private String MaDocGia;
    private String MaNhanVien;
    private String NgayMuon;
    private String NgayHenTra;
    private String TienCoc;
    public MuonTra(String MaMuonTra, String MaDocGia , String MaNhanVien, String NgayMuon, String NgayHenTra, String TienCoc) {
        this.MaPhieu = MaPhieu;
        this.MaDocGia = MaDocGia;
        this.MaNhanVien = MaNhanVien;
        this.NgayMuon = NgayMuon;
        this.NgayHenTra = NgayHenTra;
        this.TienCoc = TienCoc;
        
    }

    public String getMaPhieu() {
        return MaPhieu;
    }

    public void setMaPhieu(String MaPhieu) {
        this.MaPhieu = MaPhieu;
    }

    public String getMaDocGia() {
        return MaDocGia;
    }

    public void setMaDocGia(String MaDocGia) {
        this.MaDocGia= MaDocGia;
    }

    public String getMaNhanVien() {
        return MaNhanVien;
    }

    public void setMaNhanVien(String MaNhanVien) {
        this.MaNhanVien = MaNhanVien;
    }

    public String getNgayMuon() {
        return NgayMuon;
    }

    public void setNgayMuon(String NgayMuon) {
        this.NgayMuon = NgayMuon;
    }

    public String getNgayHenTra() {
        return NgayHenTra;
    }

    public void setNgayHenTra(String NgayHenTra) {
        this.NgayHenTra = NgayHenTra;
    }

    public String getTienCuoc() {
        return TienCoc;
    }

    public void setTienCuoc(String TienCoc) {
        this.TienCoc = TienCoc;
    }
}