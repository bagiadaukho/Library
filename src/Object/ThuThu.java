
package Object;

import java.sql.Date;


public class ThuThu {
   // private String usenameThuThu;
   // private String passwordThuThu;
    private String MaThuThu;
    private String TenThuThu;
    private Date Ngaysinh ;
    private String Gioitinh;
    
    private String Email;
    private String Sodienthoai;
    private String Diachi;
    
    public ThuThu() {
        
    }
    
    public ThuThu( String MaThuThu, String TenThuThu, Date Ngaysinh , String Gioitinh,String Email,String Sodienthoai,String Diachi) {
       // this.usenameThuThu = usenameThuThu;
       // this.passwordThuThu = passwordThuThu;
        this.MaThuThu = MaThuThu;
        this.TenThuThu = TenThuThu;
        this.Ngaysinh  = Ngaysinh ;
        this.Gioitinh = Gioitinh;
        
          this.Email = Email;
           this.Sodienthoai = Sodienthoai;
            this.Diachi = Diachi;
   // }
   // public String getusenameThuThu() {
       // return usenameThuThu;
    //}
  // public void setusenamThuThu(String usenameThuThu) {
       // this.usenameThuThu = usenameThuThu;
   // }
    
    //public String getPassThuThu() {
        //return passwordThuThu;
    }
   // public void setPassThuThu(String pass) {
       // this.passwordThuThu= pass;
   // }
    
    public String getMaThuThu() {
        return MaThuThu;
    }
    public void setMaThuThu(String MaThuThu) {
        this.MaThuThu = MaThuThu;
    }
    
    public String getTenTT() {
        return TenThuThu;
    }
    public void setTenTT(String TenTT) {
        this.TenThuThu = TenTT;
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
