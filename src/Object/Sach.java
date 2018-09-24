
package Object;


public class Sach {
    private String MaSach;
    private String TenSach;
    private String Tacgia;
    private String Nhaxuatban;
    private String TheLoai;
    private int Soluong;
    private int NamXB;
    private int DonGia;
    
    public Sach() {
        
    }
    
    public Sach(String masach, String tensach, String tacgia, String nhaxuatban , String theloai, int soluong,int namxb,int dongia) {
        this.MaSach = masach;
        this.TenSach= tensach;
        this.Tacgia = tacgia;
        this.Nhaxuatban = nhaxuatban;
        this.TheLoai = theloai;
        this.Soluong = soluong;
        this.NamXB= namxb;
        this.DonGia = dongia;
    }
    
    public String getMaSach() {
        return MaSach;
    }
    
    public void setMaSach(String ms) {
        this.MaSach = ms;
    }
    
    public String getTenSach() {
        return TenSach;
    }
    
    public void setTenSach(String ts) {
        this.TenSach = ts;
    }
    
    public String getTenTacGia() {
        return Tacgia;
    }
    
    public void setTenTacGia(String ttg) {
        this.Tacgia = ttg;
    }
    
    public String getNhaXB() {
        return Nhaxuatban;
    }
    
    public void setNhaXB(String nxb) {
        this.Nhaxuatban = nxb;
    }
    
    public String getTheLoai() {
        return TheLoai;
    }
    
    public void setTheLoai(String tl) {
        this.TheLoai = tl;
    }
    
    public int getSoLuong() {
        return Soluong;
    }
    
    public void setSoLuong(int sl) {
        this.Soluong = sl;
    }
     public int getNamXB() {
        return NamXB;
    }
    
    public void setNamXB(int nx) {
        this.NamXB = nx;
    }
     public int getDonGia() {
        return DonGia;
    }
    
    public void setDonGia(int dongia) {
        this.DonGia = dongia;
    }
}
