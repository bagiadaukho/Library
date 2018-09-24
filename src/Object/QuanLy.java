
package Object;


public class QuanLy {
     private String usenameQuanLy;
    private String passwordQuanLy;
    
    public QuanLy() {
        
    }
    public QuanLy(String usenameQuanLy, String passwordQuanLy) {
        this.usenameQuanLy = usenameQuanLy;
        this.passwordQuanLy = passwordQuanLy;
    }
    
    public String getusenameQuanLy() {
        return usenameQuanLy;
    }
    public void setusenameQuanLy(String usenameQuanLy) {
        this.usenameQuanLy= usenameQuanLy;
    }
    
    public String getPasswordQuanLy() {
        return passwordQuanLy;
    }
    public void setPasswordThuThu(String passQuanLy) {
        this.passwordQuanLy= passQuanLy;
    }
}
