
package Object;

import java.sql.Date;


public class DocGia {
    //  private String usenameDocGia;
   // private String passwordDocGia;
    private String MaDocGia;
    private String TenDocGia;
    private Date Ngaysinh ;
    private String Gioitinh;
    private String CMND;
    private String Email;
    private String Sodienthoai;
    private String Diachi;
    
    public DocGia() {
        
    }
    
    public DocGia( String MaDocGia, String TenDocGia, Date Ngaysinh , String Gioitinh,String CMND,String Email,String Sodienthoai,String Diachi) {
       // this.usenameDocGia = usenameDocGia;
        //this.passwordDocGia = passwordDocGia;
        this.MaDocGia = MaDocGia;
        this.TenDocGia = TenDocGia;
        this.Ngaysinh  = Ngaysinh ;
        this.Gioitinh = Gioitinh;
         this.CMND = CMND;
          this.Email = Email;
           this.Sodienthoai = Sodienthoai;
            this.Diachi = Diachi;
    //}
    //public String getusenameDocGia() {
       // return usenameDocGia;
   // }
    //public void setusenamDocGia(String usenameDocGia) {
      //  this.usenameDocGia = usenameDocGia;
   // }

    //public DocGia(String text, String text0, Date valueOf, String text1, String text2, String text3, String text4, String text5) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    //}
    
    //public String getPassDocGia() {
     //   return passwordDocGia;
    //}
    //public void setPassDocGia(String pass) {
     //   this.passwordDocGia = pass;
    }
    
    public String getMaDocGia() {
        return MaDocGia;
    }
    public void setMaDocGia(String MaDG) {
        this.MaDocGia = MaDG;
    }
    
    public String getTenDG() {
        return TenDocGia;
    }
    public void setTenDG(String TenDG) {
        this.TenDocGia = TenDG;
    }
    
    public Date getNgaysinh() {
        return Ngaysinh;
    }
    public void setNgaysinh(Date Ngaysinh) {
        this.Ngaysinh = Ngaysinh;
    }
    
    public String getGioitinh() {
        return Gioitinh;
    }
    public void setGioitinh(String GT) {
        this.Gioitinh = GT;
    }
     public String getCMND() {
        return CMND;
    }
    public void setCMND(String CMND) {
        this.CMND = CMND;
    }
    public String getEmail() {
        return Email;
    }
    public void setEmail(String EM) {
        this.Email = Email;
    }
    public String getSDT() {
        return Sodienthoai;
    }
    public void setSDT(String SDT) {
        this.Sodienthoai = SDT;
    }
    public String getDiachi() {
        return Diachi;
    }
    public void setDiachi(String Diachi) {
        this.Diachi = Diachi;
    }
}
