/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Process;

import Data.DocGhiExel;

import Data.ExportPDF;
import Data.MuonTraData;
import Data.ChitietmuontraData;
import Object.MuonTra;

import Data.DocGiaData;
import Object.DocGia;
import Data.SachData;
import Object.Sach;
import Data.Connect;
import Data.MoFile;
import Data.ThuThuData;
import static Process.UpdateTable.con;
import com.itextpdf.text.Image;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.table.DefaultTableModel;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

/**
 *
 * @author Admin
 */
public class MainThuThu extends javax.swing.JFrame {
     public static PreparedStatement ps = null;
     public static int click =0;
    
String sql1="Select*from BangSach order by MaSach asc";
String sql2="select *from BangDocGia order  by MaDocGia asc";
  public static String sql3="select*from BangMuonTra";
    public static String sql4="select*from BangChitietMuonTra";
SachData sach=new SachData();
DocGiaData docgia=new DocGiaData();

 
public static  String mamuontra, madocgia ;
    private boolean isAdd= true;
    DefaultTableModel tbn = new DefaultTableModel();
    /**
     * Creates new form MainThuThu
     */
 public static String TenBang1, TenCot1;
    public MainThuThu() {
        initComponents();
      UpdateTable.LoadData(sql1, tbS);
      UpdateTable.LoadData(sql1, tbTKS);
      UpdateTable.LoadData(sql2, tbDG);
      UpdateTable.LoadData(sql2, tbTKDG);
      UpdateTable.LoadData(sql3,tbMuonTra);
        UpdateTable.LoadData(sql4,tbChitiet);
        String sqlcbMaDocGia = "SELECT * FROM BangDocGia";
        LoadDataCombobox(sqlcbMaDocGia, this.cbMDG, "MaDocGia");
        String sqlcbMaNhanVien = "SELECT * FROM BangNhanVien";
        LoadDataCombobox(sqlcbMaNhanVien, this.cbMNV, "MaNhanVien");
        String sqlcbMaPhieu = "SELECT * FROM BangMuonTra";
        LoadDataCombobox(sqlcbMaPhieu, this.cbMMT, "MaMuonTra");
        String sqlcbMaSach = "SELECT * FROM BangSach";
        LoadDataCombobox(sqlcbMaSach, this.cbMS, "MaSach");
        ProcessCtrMuonTra(false);
        ProcessCtrThongTin(false);
     UpdateTable.LoadData(sql3, tbTKMT);
       
      ProcessCtr(false);
      ProcessCtr1(false);
    
     
      //ProcessCtr1(false);
       
    }
     public void LoadDataCombobox(String sql1, JComboBox cb, String bien) 
    {
        try
        {
            PreparedStatement pst = con.prepareStatement(sql1);
            ResultSet rs = pst.executeQuery();
            while (rs.next())
            {
                cb.addItem(rs.getString(bien));
            }
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e, "Thông báo lỗi", 1);
        }
    }
public void ProcessCtr( boolean b){
    this.btThem.setEnabled(b);
    this.btSua.setEnabled(b);
    this.btXoa.setEnabled(b);
}
public void ProcessCtr1( boolean b){
    this.btThemDG.setEnabled(b);
    this.btSuaDG.setEnabled(b);
    this.btXoaDG.setEnabled(b);
}
public void ProcessCtrMuonTra(boolean b){
        this.btSuaMT.setEnabled(b);
        this.btXoaMT.setEnabled(b);
        this.btOKMT.setEnabled(b);
        this.btHuyMT.setEnabled(b);
        this.txtMMT.setEnabled(b);
        this.cbMDG.setEnabled(b);
        this.cbMNV.setEnabled(b);
        this.txtNM.setEnabled(b);
        this.txtNT.setEnabled(b);
        this.txtTC.setEnabled(b);
     }
     public void ProcessCtrThongTin(boolean b){
        this.btSuaTT.setEnabled(b);
        this.btXoaTT.setEnabled(b);
        this.btOKTT.setEnabled(b);
        this.btHuyTT.setEnabled(b);
        this.cbMMT.setEnabled(b);
        this.cbMS.setEnabled(b);
        this.txtNgayTra.setEnabled(b);
        this.txtTP.setEnabled(b);
        this.txtGC.setEnabled(b);
        
    }
      public void clearMuonTra(){
        this.txtMMT.setText(null);
        this.txtNM.setText(null);
        this.txtNT.setText(null);
        this.txtTC.setText(null);
    }
    public void clearChiTiet(){
        this.txtNgayTra.setText(null);
        //this.txtTienPhat.setText(null);
        }
     private int Tienphat(Date NgayHenTra, Date NgayTra)
    {
        long x = NgayHenTra.getTime() - NgayTra.getTime();
        if (x >0) 
            return 0;
        else 
            return (-(int)x)/86400;
        
    }
      public void setText()
    {
        this.txtMMT.setText(null);
        this.txtNM.setText(null);
        this.txtNT.setText(null);
        this.txtTC.setText(null);
        this.txtNgayTra.setText(null);
        //this.txtTienPhat.setText(null);
        
    }
    private boolean testMuonTra() {
        if(this.txtMMT.getText().length() == 0 || this.txtNM.getText().length() == 0 || this.txtNT.getText().length() == 0|| this.txtTC.getText().length() == 0)
            return false;
        if ( this.txtMMT.getText().length() > 20 ||this.txtNM.getText().length() > 15 || this.txtNT.getText().length() > 15 || this.txtTC.getText().length() > 20)
            return false;
        return true;}
    private boolean testChiTiet() {
        if(this.txtNgayTra.getText().length() == 0 )
            return false;
        if ( this.txtNgayTra.getText().length() > 15 )//||this.txtTienPhat.getText().length() > 11 
            return false;
        return true;
    }
//}


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel13 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbS = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtMS = new javax.swing.JTextField();
        txtTS = new javax.swing.JTextField();
        txtTG = new javax.swing.JTextField();
        txtNXB = new javax.swing.JTextField();
        txtTL = new javax.swing.JTextField();
        txtSL = new javax.swing.JTextField();
        txtNB = new javax.swing.JTextField();
        txtDG = new javax.swing.JTextField();
        btThem = new javax.swing.JButton();
        btSua = new javax.swing.JButton();
        btXoa = new javax.swing.JButton();
        btThemFileSach = new javax.swing.JButton();
        btXuatFileSach = new javax.swing.JButton();
        btNewS = new javax.swing.JButton();
        jLabel21 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbTKS = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        txtTKMS = new javax.swing.JTextField();
        txtTKTS = new javax.swing.JTextField();
        txtTKTG = new javax.swing.JTextField();
        txtTKNXB = new javax.swing.JTextField();
        txtTKTL = new javax.swing.JTextField();
        txtTKSL = new javax.swing.JTextField();
        txtTKNB = new javax.swing.JTextField();
        txtTKDG = new javax.swing.JTextField();
        btTK = new javax.swing.JButton();
        btIntimkiemsach = new javax.swing.JButton();
        btReset = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbTKSach = new javax.swing.JTable();
        jLabel20 = new javax.swing.JLabel();
        btThongkeNB = new javax.swing.JButton();
        btThongkeMS = new javax.swing.JButton();
        btThongkeNXB = new javax.swing.JButton();
        btThongkeTS = new javax.swing.JButton();
        btThongkeTL = new javax.swing.JButton();
        btThongkeDG = new javax.swing.JButton();
        btThongkeTG = new javax.swing.JButton();
        btInThongKeSach = new javax.swing.JButton();
        jLabel53 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jTabbedPane3 = new javax.swing.JTabbedPane();
        jPanel6 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tbDG = new javax.swing.JTable();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        txtMDG = new javax.swing.JTextField();
        txtTDG = new javax.swing.JTextField();
        txtNS = new javax.swing.JTextField();
        txtGT = new javax.swing.JTextField();
        txtCMND = new javax.swing.JTextField();
        txtEM = new javax.swing.JTextField();
        txtSDT = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        txtDC = new javax.swing.JTextField();
        btThemDG = new javax.swing.JButton();
        btSuaDG = new javax.swing.JButton();
        btXoaDG = new javax.swing.JButton();
        btNewDG = new javax.swing.JButton();
        btNhapFileDG = new javax.swing.JButton();
        btXuatFileDG = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jLabel32 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tbTKDG = new javax.swing.JTable();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        txtTKMDG = new javax.swing.JTextField();
        txtTKTDG = new javax.swing.JTextField();
        txtTKNS = new javax.swing.JTextField();
        txtTKCMND = new javax.swing.JTextField();
        txtTKEM = new javax.swing.JTextField();
        txtTKSDT = new javax.swing.JTextField();
        txtTKDC = new javax.swing.JTextField();
        btTKDG = new javax.swing.JButton();
        btInTKDG = new javax.swing.JButton();
        jLabel40 = new javax.swing.JLabel();
        txtTKGT = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jPanel11 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tbThongKeDG = new javax.swing.JTable();
        jLabel41 = new javax.swing.JLabel();
        btThongkeGT = new javax.swing.JButton();
        btThongKeDC = new javax.swing.JButton();
        btThongKeNS = new javax.swing.JButton();
        btThongKeTDG = new javax.swing.JButton();
        jLabel55 = new javax.swing.JLabel();
        btThongKeDG = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jTabbedPane4 = new javax.swing.JTabbedPane();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        tbMuonTra = new javax.swing.JTable();
        jScrollPane10 = new javax.swing.JScrollPane();
        tbChitiet = new javax.swing.JTable();
        jLabel57 = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        jLabel61 = new javax.swing.JLabel();
        jLabel62 = new javax.swing.JLabel();
        jLabel63 = new javax.swing.JLabel();
        txtMMT = new javax.swing.JTextField();
        txtNM = new javax.swing.JTextField();
        txtNT = new javax.swing.JTextField();
        txtTC = new javax.swing.JTextField();
        cbMDG = new javax.swing.JComboBox<>();
        cbMNV = new javax.swing.JComboBox<>();
        jLabel64 = new javax.swing.JLabel();
        jLabel65 = new javax.swing.JLabel();
        jLabel66 = new javax.swing.JLabel();
        jLabel67 = new javax.swing.JLabel();
        jLabel68 = new javax.swing.JLabel();
        jLabel69 = new javax.swing.JLabel();
        cbMMT = new javax.swing.JComboBox<>();
        cbMS = new javax.swing.JComboBox<>();
        txtNgayTra = new javax.swing.JTextField();
        txtTP = new javax.swing.JTextField();
        txtGC = new javax.swing.JTextField();
        btThemMT = new javax.swing.JButton();
        btSuaMT = new javax.swing.JButton();
        btXoaMT = new javax.swing.JButton();
        btOKMT = new javax.swing.JButton();
        btHuyMT = new javax.swing.JButton();
        btThemFileMT = new javax.swing.JButton();
        btInPhieuCTMT = new javax.swing.JButton();
        btThemTT = new javax.swing.JButton();
        btSuaTT = new javax.swing.JButton();
        btXoaTT = new javax.swing.JButton();
        btOKTT = new javax.swing.JButton();
        btHuyTT = new javax.swing.JButton();
        btThemFileTT = new javax.swing.JButton();
        jPanel12 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tbTKMT = new javax.swing.JTable();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        txtMaMT = new javax.swing.JTextField();
        txtMaDG = new javax.swing.JTextField();
        txtMaNV = new javax.swing.JTextField();
        txtNgayMuon = new javax.swing.JTextField();
        txtNHT = new javax.swing.JTextField();
        txtTCoc = new javax.swing.JTextField();
        btTKMT = new javax.swing.JButton();
        btNewTKMT = new javax.swing.JButton();
        btInTKMT = new javax.swing.JButton();
        jPanel14 = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        tbThongKeMuonTra = new javax.swing.JTable();
        jLabel49 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        btThongkeMDG = new javax.swing.JButton();
        btThongKeMNV = new javax.swing.JButton();
        btThongKeNM = new javax.swing.JButton();
        btThongkeNHT = new javax.swing.JButton();
        btThongkeTC = new javax.swing.JButton();
        btInThongKeMT = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Quản lý thư viện");
        setBackground(new java.awt.Color(0, 255, 51));
        setForeground(new java.awt.Color(255, 255, 204));
        setSize(new java.awt.Dimension(1000, 950));

        jTabbedPane1.setTabPlacement(javax.swing.JTabbedPane.LEFT);
        jTabbedPane1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N

        jLabel22.setIcon(new javax.swing.ImageIcon("C:\\Users\\Admin\\Desktop\\ảnh đồ án\\main thủ thư.jpg")); // NOI18N
        jLabel22.setText("jLabel22");

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 1247, Short.MAX_VALUE)
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 557, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Giới thiệu", jPanel13);

        jTabbedPane2.setBackground(new java.awt.Color(255, 204, 255));
        jTabbedPane2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        jPanel4.setBackground(new java.awt.Color(255, 255, 204));

        tbS.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tbS.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbSMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbS);

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Mã sách:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Tên sách:");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("Tác giả:");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("Nhà xuất bản:");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("Thể loại:");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel9.setText("Số lượng:");

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel10.setText("Năm xuất bản:");

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel11.setText("Đơn giá:");

        btThem.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btThem.setIcon(new javax.swing.ImageIcon("C:\\Users\\Admin\\Desktop\\ảnh đồ án\\nhập lại (5).png")); // NOI18N
        btThem.setText("Thêm");
        btThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btThemActionPerformed(evt);
            }
        });

        btSua.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btSua.setIcon(new javax.swing.ImageIcon("C:\\Users\\Admin\\Desktop\\ảnh đồ án\\sửa.png")); // NOI18N
        btSua.setText("Sửa");
        btSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSuaActionPerformed(evt);
            }
        });

        btXoa.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btXoa.setIcon(new javax.swing.ImageIcon("C:\\Users\\Admin\\Desktop\\ảnh đồ án\\xóa.png")); // NOI18N
        btXoa.setText("Xóa");
        btXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btXoaActionPerformed(evt);
            }
        });

        btThemFileSach.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btThemFileSach.setIcon(new javax.swing.ImageIcon("C:\\Users\\Admin\\Desktop\\ảnh đồ án\\thêm file.png")); // NOI18N
        btThemFileSach.setText("Thêm File");
        btThemFileSach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btThemFileSachActionPerformed(evt);
            }
        });

        btXuatFileSach.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btXuatFileSach.setIcon(new javax.swing.ImageIcon("C:\\Users\\Admin\\Desktop\\ảnh đồ án\\xuất file.png")); // NOI18N
        btXuatFileSach.setText("Xuất file");
        btXuatFileSach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btXuatFileSachActionPerformed(evt);
            }
        });

        btNewS.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btNewS.setIcon(new javax.swing.ImageIcon("C:\\Users\\Admin\\Desktop\\ảnh đồ án\\nhập lại (1).png")); // NOI18N
        btNewS.setText("Nhập mới");
        btNewS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btNewSActionPerformed(evt);
            }
        });

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel21.setIcon(new javax.swing.ImageIcon("C:\\Users\\Admin\\Desktop\\ảnh đồ án\\Info.png")); // NOI18N
        jLabel21.setText("Cập nhật thông tin sách");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addComponent(jLabel10)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel9))
                                .addGap(30, 30, 30)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtMS, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtTS, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtTG, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtNXB, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtTL, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtSL, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtNB, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtDG, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGap(74, 74, 74)
                                        .addComponent(btThem, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(7, 7, 7))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                        .addComponent(btNewS)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btSua, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btThemFileSach))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(btXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btXuatFileSach))))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 691, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(26, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addGap(452, 452, 452))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel21)
                .addGap(38, 38, 38)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtMS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtTS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtTG, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtNXB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtTL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtNB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addComponent(txtDG, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btXoa)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btThem, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btSua)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btThemFileSach)
                    .addComponent(btXuatFileSach, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btNewS, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(446, 446, 446))
        );

        jTabbedPane2.addTab("Thêm, sửa, xóa", jPanel4);

        jPanel5.setBackground(new java.awt.Color(255, 255, 204));

        tbTKS.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(tbTKS);

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Mã sách:");

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel12.setText("Tên sách:");

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel13.setText("Tác giả:");

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel14.setText("Nhà xuất bản:");

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel15.setText("Thể loại:");

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel16.setText("Số lượng:");

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel17.setText("Năm xuất bản:");

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel18.setText("Đơn giá:");

        btTK.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btTK.setIcon(new javax.swing.ImageIcon("C:\\Users\\Admin\\Desktop\\ảnh đồ án\\timkiem2.png")); // NOI18N
        btTK.setText("Tìm kiếm");
        btTK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btTKActionPerformed(evt);
            }
        });

        btIntimkiemsach.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btIntimkiemsach.setIcon(new javax.swing.ImageIcon("C:\\Users\\Admin\\Desktop\\ảnh đồ án\\xuất file.png")); // NOI18N
        btIntimkiemsach.setText("In");
        btIntimkiemsach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btIntimkiemsachActionPerformed(evt);
            }
        });

        btReset.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btReset.setIcon(new javax.swing.ImageIcon("C:\\Users\\Admin\\Desktop\\ảnh đồ án\\nhập lại (1).png")); // NOI18N
        btReset.setText("Nhập lại");
        btReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btResetActionPerformed(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel19.setIcon(new javax.swing.ImageIcon("C:\\Users\\Admin\\Desktop\\ảnh đồ án\\Info.png")); // NOI18N
        jLabel19.setText("Tìm kiếm thông tin sách");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(65, 65, 65)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTKMS, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtTKTG)
                                .addComponent(txtTKNXB)
                                .addComponent(txtTKTL)
                                .addComponent(txtTKSL)
                                .addComponent(txtTKNB)
                                .addComponent(txtTKDG, javax.swing.GroupLayout.DEFAULT_SIZE, 277, Short.MAX_VALUE)
                                .addComponent(txtTKTS, javax.swing.GroupLayout.Alignment.TRAILING))))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(btTK)
                        .addGap(18, 18, 18)
                        .addComponent(btReset)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btIntimkiemsach, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 630, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel19)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtTKMS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(24, 24, 24)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12)
                            .addComponent(txtTKTS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(19, 19, 19)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(txtTKTG, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(txtTKNXB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15)
                            .addComponent(txtTKTL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(24, 24, 24)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16)
                            .addComponent(txtTKSL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(24, 24, 24)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel17)
                            .addComponent(txtTKNB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(21, 21, 21)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel18)
                            .addComponent(txtTKDG, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btTK, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btReset)
                            .addComponent(btIntimkiemsach, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(66, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Tìm kiếm", jPanel5);

        jPanel10.setBackground(new java.awt.Color(255, 255, 204));

        tbTKSach.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane3.setViewportView(tbTKSach);

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel20.setText("Thống kê sách");

        btThongkeNB.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btThongkeNB.setText("Theo năm xuất bản ");
        btThongkeNB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btThongkeNBActionPerformed(evt);
            }
        });

        btThongkeMS.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btThongkeMS.setText("Theo mã sách");
        btThongkeMS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btThongkeMSActionPerformed(evt);
            }
        });

        btThongkeNXB.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btThongkeNXB.setText("Theo nhà xuất bản");
        btThongkeNXB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btThongkeNXBActionPerformed(evt);
            }
        });

        btThongkeTS.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btThongkeTS.setText("Theo tên sách");
        btThongkeTS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btThongkeTSActionPerformed(evt);
            }
        });

        btThongkeTL.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btThongkeTL.setText("Theo thể loại");
        btThongkeTL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btThongkeTLActionPerformed(evt);
            }
        });

        btThongkeDG.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btThongkeDG.setText("Theo đơn giá");
        btThongkeDG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btThongkeDGActionPerformed(evt);
            }
        });

        btThongkeTG.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btThongkeTG.setText("Theo tác giả");
        btThongkeTG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btThongkeTGActionPerformed(evt);
            }
        });

        btInThongKeSach.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btInThongKeSach.setIcon(new javax.swing.ImageIcon("C:\\Users\\Admin\\Desktop\\ảnh đồ án\\xuất file.png")); // NOI18N
        btInThongKeSach.setText("In ");
        btInThongKeSach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btInThongKeSachActionPerformed(evt);
            }
        });

        jLabel53.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel53.setIcon(new javax.swing.ImageIcon("C:\\Users\\Admin\\Desktop\\ảnh đồ án\\thống kê.png")); // NOI18N
        jLabel53.setText("Thống kê sách theo:");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(btThongkeNB, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE)
                            .addComponent(btThongkeMS, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btThongkeNXB, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btThongkeTS, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btThongkeTL, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btThongkeDG, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btThongkeTG, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(49, 49, 49)
                        .addComponent(btInThongKeSach, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 782, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(21, Short.MAX_VALUE))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel53, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel20)
                        .addGap(367, 367, 367))))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel53)
                        .addGap(61, 61, 61)
                        .addComponent(btThongkeMS, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btThongkeTS)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addGap(17, 17, 17)
                                .addComponent(btThongkeTG)
                                .addGap(15, 15, 15)
                                .addComponent(btThongkeNXB))
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addGap(26, 26, 26)
                                .addComponent(btInThongKeSach, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addComponent(btThongkeTL)
                        .addGap(18, 18, 18)
                        .addComponent(btThongkeNB, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btThongkeDG))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel20)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 376, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(67, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Thống kê", jPanel10);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane2)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 557, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Sách", jPanel1);

        jTabbedPane3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        jPanel6.setBackground(new java.awt.Color(255, 255, 204));

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel23.setIcon(new javax.swing.ImageIcon("C:\\Users\\Admin\\Desktop\\ảnh đồ án\\Info.png")); // NOI18N
        jLabel23.setText("Cập nhật thông tin độc giả");

        tbDG.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tbDG.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDGMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tbDG);

        jLabel24.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel24.setText("Mã độc giả: ");

        jLabel25.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel25.setText("Tên độc giả:");

        jLabel26.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel26.setText("Ngày sinh:");

        jLabel27.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel27.setText("Giới tính:");

        jLabel28.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel28.setText("CMND:");

        jLabel29.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel29.setText("Email:");

        jLabel30.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel30.setText("Số điện thoại:");

        jLabel31.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel31.setText("Địa chỉ:");

        btThemDG.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btThemDG.setIcon(new javax.swing.ImageIcon("C:\\Users\\Admin\\Desktop\\ảnh đồ án\\nhập lại (5).png")); // NOI18N
        btThemDG.setText("Thêm");
        btThemDG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btThemDGActionPerformed(evt);
            }
        });

        btSuaDG.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btSuaDG.setIcon(new javax.swing.ImageIcon("C:\\Users\\Admin\\Desktop\\ảnh đồ án\\sửa.png")); // NOI18N
        btSuaDG.setText("Sửa");
        btSuaDG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSuaDGActionPerformed(evt);
            }
        });

        btXoaDG.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btXoaDG.setIcon(new javax.swing.ImageIcon("C:\\Users\\Admin\\Desktop\\ảnh đồ án\\xóa.png")); // NOI18N
        btXoaDG.setText("Xóa");
        btXoaDG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btXoaDGActionPerformed(evt);
            }
        });

        btNewDG.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btNewDG.setIcon(new javax.swing.ImageIcon("C:\\Users\\Admin\\Desktop\\ảnh đồ án\\nhập lại (1).png")); // NOI18N
        btNewDG.setText("Nhập mới");
        btNewDG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btNewDGActionPerformed(evt);
            }
        });

        btNhapFileDG.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btNhapFileDG.setIcon(new javax.swing.ImageIcon("C:\\Users\\Admin\\Desktop\\ảnh đồ án\\thêm file.png")); // NOI18N
        btNhapFileDG.setText("Nhập file");
        btNhapFileDG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btNhapFileDGActionPerformed(evt);
            }
        });

        btXuatFileDG.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btXuatFileDG.setIcon(new javax.swing.ImageIcon("C:\\Users\\Admin\\Desktop\\ảnh đồ án\\xuất file.png")); // NOI18N
        btXuatFileDG.setText("Xuất file");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addComponent(jLabel23)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel24)
                            .addComponent(jLabel25)
                            .addComponent(jLabel26)
                            .addComponent(jLabel27)
                            .addComponent(jLabel28)
                            .addComponent(jLabel29)
                            .addComponent(jLabel31))
                        .addGap(20, 20, 20))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel30)
                        .addGap(18, 18, 18)))
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtSDT, javax.swing.GroupLayout.DEFAULT_SIZE, 331, Short.MAX_VALUE)
                            .addComponent(txtCMND)
                            .addComponent(txtGT)
                            .addComponent(txtMDG)
                            .addComponent(txtTDG)
                            .addComponent(txtNS)
                            .addComponent(txtDC, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtEM))
                        .addGap(60, 60, 60))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btThemDG, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btNewDG))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btSuaDG, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btNhapFileDG))
                        .addGap(33, 33, 33)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btXoaDG, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btXuatFileDG, javax.swing.GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 613, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(51, 51, 51))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 413, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel23)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtMDG, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel24))
                        .addGap(26, 26, 26)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel25)
                            .addComponent(txtTDG, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel26)
                            .addComponent(txtNS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel27)
                            .addComponent(txtGT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel28)
                            .addComponent(txtCMND, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel29)
                            .addComponent(txtEM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(25, 25, 25)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel30)
                            .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(23, 23, 23)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel31)
                            .addComponent(txtDC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(8, 8, 8)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btThemDG, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btSuaDG)
                                .addComponent(btXoaDG)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btXuatFileDG)
                            .addComponent(btNhapFileDG, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btNewDG, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(61, Short.MAX_VALUE))))
        );

        jTabbedPane3.addTab("Thêm, sửa, xóa", jPanel6);

        jPanel7.setBackground(new java.awt.Color(255, 255, 204));

        jLabel32.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel32.setIcon(new javax.swing.ImageIcon("C:\\Users\\Admin\\Desktop\\ảnh đồ án\\Info.png")); // NOI18N
        jLabel32.setText("Tìm kiếm thông tin độc giả");

        tbTKDG.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane5.setViewportView(tbTKDG);

        jLabel33.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel33.setText("Mã độc giả:");

        jLabel34.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel34.setText("Tên độc giả:");

        jLabel35.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel35.setText("Ngày sinh:");

        jLabel36.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel36.setText("CMND:");

        jLabel37.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel37.setText("Email:");

        jLabel38.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel38.setText("Số điện thoại:");

        jLabel39.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel39.setText("Địa chỉ: ");

        btTKDG.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btTKDG.setIcon(new javax.swing.ImageIcon("C:\\Users\\Admin\\Desktop\\ảnh đồ án\\timkiem2.png")); // NOI18N
        btTKDG.setText("Tìm kiếm");
        btTKDG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btTKDGActionPerformed(evt);
            }
        });

        btInTKDG.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btInTKDG.setIcon(new javax.swing.ImageIcon("C:\\Users\\Admin\\Desktop\\ảnh đồ án\\xuất file.png")); // NOI18N
        btInTKDG.setText("In");
        btInTKDG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btInTKDGActionPerformed(evt);
            }
        });

        jLabel40.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel40.setText("Giới tính:");

        jButton3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton3.setIcon(new javax.swing.ImageIcon("C:\\Users\\Admin\\Desktop\\ảnh đồ án\\nhập lại (1).png")); // NOI18N
        jButton3.setText("Nhập lại");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 401, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel33, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel34, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel35, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel40, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel36, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel37, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel38, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel39, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(10, 10, 10)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtTKMDG)
                                    .addComponent(txtTKTDG)
                                    .addComponent(txtTKNS)
                                    .addComponent(txtTKCMND)
                                    .addComponent(txtTKEM)
                                    .addComponent(txtTKSDT)
                                    .addComponent(txtTKDC)
                                    .addComponent(txtTKGT, javax.swing.GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE))
                                .addGap(33, 33, 33))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(btTKDG, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(27, 27, 27)
                                .addComponent(jButton3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btInTKDG)
                                .addGap(18, 18, 18)))
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 701, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(81, 81, 81))))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jLabel32)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel33)
                            .addComponent(txtTKMDG, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(22, 22, 22)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel34)
                            .addComponent(txtTKTDG, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel35)
                            .addComponent(txtTKNS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(25, 25, 25)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel40)
                            .addComponent(txtTKGT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel36)
                            .addComponent(txtTKCMND, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(27, 27, 27)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel37)
                            .addComponent(txtTKEM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel38)
                            .addComponent(txtTKSDT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel39)
                            .addComponent(txtTKDC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btInTKDG)
                                .addComponent(jButton3))
                            .addComponent(btTKDG, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 362, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(66, Short.MAX_VALUE))
        );

        jTabbedPane3.addTab("Tìm kiếm", jPanel7);

        jPanel11.setBackground(new java.awt.Color(255, 255, 204));

        tbThongKeDG.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane6.setViewportView(tbThongKeDG);

        jLabel41.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel41.setText("Thống kê độc giả");

        btThongkeGT.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btThongkeGT.setText("Theo giới tính ");
        btThongkeGT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btThongkeGTActionPerformed(evt);
            }
        });

        btThongKeDC.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btThongKeDC.setText("Theo địa chỉ");
        btThongKeDC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btThongKeDCActionPerformed(evt);
            }
        });

        btThongKeNS.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btThongKeNS.setText("Theo ngày sinh");
        btThongKeNS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btThongKeNSActionPerformed(evt);
            }
        });

        btThongKeTDG.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btThongKeTDG.setText("Theo tên độc giả");
        btThongKeTDG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btThongKeTDGActionPerformed(evt);
            }
        });

        jLabel55.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel55.setIcon(new javax.swing.ImageIcon("C:\\Users\\Admin\\Desktop\\ảnh đồ án\\thống kê.png")); // NOI18N
        jLabel55.setText("Thống kê độc giả theo:");

        btThongKeDG.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btThongKeDG.setIcon(new javax.swing.ImageIcon("C:\\Users\\Admin\\Desktop\\ảnh đồ án\\xuất file.png")); // NOI18N
        btThongKeDG.setText("In ");
        btThongKeDG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btThongKeDGActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel55)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(476, 476, 476))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btThongKeTDG, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btThongKeNS, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btThongKeDC, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btThongkeGT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btThongKeDG)
                        .addGap(62, 62, 62)
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 832, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30))))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel41)
                            .addComponent(jLabel55))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 368, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(165, 165, 165)
                        .addComponent(btThongkeGT)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btThongKeDC)
                            .addComponent(btThongKeDG))
                        .addGap(18, 18, 18)
                        .addComponent(btThongKeNS)
                        .addGap(18, 18, 18)
                        .addComponent(btThongKeTDG)))
                .addContainerGap(49, Short.MAX_VALUE))
        );

        jTabbedPane3.addTab("Thống kê", jPanel11);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 1186, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane3)
        );

        jTabbedPane1.addTab("Độc Giả", jPanel2);

        jTabbedPane4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        jPanel9.setBackground(new java.awt.Color(255, 255, 204));

        tbMuonTra.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tbMuonTra.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbMuonTraMouseClicked(evt);
            }
        });
        jScrollPane9.setViewportView(tbMuonTra);

        tbChitiet.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tbChitiet.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbChitietMouseClicked(evt);
            }
        });
        jScrollPane10.setViewportView(tbChitiet);

        jLabel57.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel57.setText("Mượn trả ");

        jLabel58.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel58.setText("Mã mượn trả:");

        jLabel59.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel59.setText("Mã độc giả:");

        jLabel60.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel60.setText("Mã nhân viên: ");

        jLabel61.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel61.setText("Ngày mượn:");

        jLabel62.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel62.setText("Ngày hẹn trả:");

        jLabel63.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel63.setText("Tiền cọc:");

        jLabel64.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel64.setIcon(new javax.swing.ImageIcon("C:\\Users\\Admin\\Desktop\\ảnh đồ án\\Info.png")); // NOI18N
        jLabel64.setText("Thông tin chi tiết ");

        jLabel65.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel65.setText("Mã mượn trả:");

        jLabel66.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel66.setText("Mã sách:");

        jLabel67.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel67.setText("Ngày trả:");

        jLabel68.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel68.setText("Tiền phạt:");

        jLabel69.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel69.setText("Ghi chú:");

        btThemMT.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btThemMT.setIcon(new javax.swing.ImageIcon("C:\\Users\\Admin\\Desktop\\ảnh đồ án\\nhập lại (5).png")); // NOI18N
        btThemMT.setText("Thêm");
        btThemMT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btThemMTActionPerformed(evt);
            }
        });

        btSuaMT.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btSuaMT.setIcon(new javax.swing.ImageIcon("C:\\Users\\Admin\\Desktop\\ảnh đồ án\\sửa.png")); // NOI18N
        btSuaMT.setText("Sửa");
        btSuaMT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSuaMTActionPerformed(evt);
            }
        });

        btXoaMT.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btXoaMT.setIcon(new javax.swing.ImageIcon("C:\\Users\\Admin\\Desktop\\ảnh đồ án\\xóa.png")); // NOI18N
        btXoaMT.setText("Xóa");
        btXoaMT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btXoaMTActionPerformed(evt);
            }
        });

        btOKMT.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btOKMT.setIcon(new javax.swing.ImageIcon("C:\\Users\\Admin\\Desktop\\ảnh đồ án\\Ok (1).png")); // NOI18N
        btOKMT.setText("OK");
        btOKMT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btOKMTActionPerformed(evt);
            }
        });

        btHuyMT.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btHuyMT.setIcon(new javax.swing.ImageIcon("C:\\Users\\Admin\\Desktop\\ảnh đồ án\\hủy.png")); // NOI18N
        btHuyMT.setText("Hủy");
        btHuyMT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btHuyMTActionPerformed(evt);
            }
        });

        btThemFileMT.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btThemFileMT.setIcon(new javax.swing.ImageIcon("C:\\Users\\Admin\\Desktop\\ảnh đồ án\\thêm file.png")); // NOI18N
        btThemFileMT.setText("Thêm File");
        btThemFileMT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btThemFileMTActionPerformed(evt);
            }
        });

        btInPhieuCTMT.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btInPhieuCTMT.setIcon(new javax.swing.ImageIcon("C:\\Users\\Admin\\Desktop\\ảnh đồ án\\xuất file.png")); // NOI18N
        btInPhieuCTMT.setText("In phiếu");
        btInPhieuCTMT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btInPhieuCTMTActionPerformed(evt);
            }
        });

        btThemTT.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btThemTT.setIcon(new javax.swing.ImageIcon("C:\\Users\\Admin\\Desktop\\ảnh đồ án\\nhập lại (5).png")); // NOI18N
        btThemTT.setText("Thêm");
        btThemTT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btThemTTActionPerformed(evt);
            }
        });

        btSuaTT.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btSuaTT.setIcon(new javax.swing.ImageIcon("C:\\Users\\Admin\\Desktop\\ảnh đồ án\\sửa.png")); // NOI18N
        btSuaTT.setText("Sửa");
        btSuaTT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSuaTTActionPerformed(evt);
            }
        });

        btXoaTT.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btXoaTT.setIcon(new javax.swing.ImageIcon("C:\\Users\\Admin\\Desktop\\ảnh đồ án\\xóa.png")); // NOI18N
        btXoaTT.setText("Xóa");
        btXoaTT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btXoaTTActionPerformed(evt);
            }
        });

        btOKTT.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btOKTT.setIcon(new javax.swing.ImageIcon("C:\\Users\\Admin\\Desktop\\ảnh đồ án\\Ok (1).png")); // NOI18N
        btOKTT.setText("OK");
        btOKTT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btOKTTActionPerformed(evt);
            }
        });

        btHuyTT.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btHuyTT.setIcon(new javax.swing.ImageIcon("C:\\Users\\Admin\\Desktop\\ảnh đồ án\\hủy.png")); // NOI18N
        btHuyTT.setText("Hủy");
        btHuyTT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btHuyTTActionPerformed(evt);
            }
        });

        btThemFileTT.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btThemFileTT.setIcon(new javax.swing.ImageIcon("C:\\Users\\Admin\\Desktop\\ảnh đồ án\\thêm file.png")); // NOI18N
        btThemFileTT.setText("Thêm File");
        btThemFileTT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btThemFileTTActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel57, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel64, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel9Layout.createSequentialGroup()
                                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(jPanel9Layout.createSequentialGroup()
                                                .addComponent(jLabel58)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(cbMDG, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(txtMMT)))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                                                .addComponent(jLabel61)
                                                .addGap(18, 18, 18)
                                                .addComponent(txtNM))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addComponent(txtNT, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel9Layout.createSequentialGroup()
                                                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(jPanel9Layout.createSequentialGroup()
                                                            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                .addComponent(jLabel62, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addComponent(jLabel63))
                                                            .addGap(9, 9, 9))
                                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                                                            .addComponent(jLabel60)
                                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                                                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(cbMNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(txtTC, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(btXoaMT, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(btSuaMT, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(btThemMT, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(btThemFileMT)))
                                    .addComponent(btThemTT, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btOKMT)
                                    .addComponent(btHuyMT)
                                    .addComponent(btOKTT, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btInPhieuCTMT, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel59)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addGroup(jPanel9Layout.createSequentialGroup()
                                            .addComponent(jLabel66, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(cbMS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel9Layout.createSequentialGroup()
                                            .addComponent(jLabel65, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(18, 18, 18)
                                            .addComponent(cbMMT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(jPanel9Layout.createSequentialGroup()
                                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel67)
                                            .addComponent(jLabel68)
                                            .addComponent(jLabel69))
                                        .addGap(37, 37, 37)
                                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtNgayTra, javax.swing.GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
                                            .addComponent(txtTP)
                                            .addComponent(txtGC))))
                                .addGap(22, 22, 22)
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(btXoaTT, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btSuaTT))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btHuyTT, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btThemFileTT))))
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 568, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 568, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(101, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel57)
                .addGap(4, 4, 4)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel58)
                                    .addComponent(txtMMT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel59)
                                    .addComponent(cbMDG, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel60)
                                    .addComponent(cbMNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel61)
                                    .addComponent(txtNM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btThemMT)
                                    .addComponent(btOKMT, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btHuyMT, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btSuaMT))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btXoaMT)
                                    .addComponent(btInPhieuCTMT))))
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel62)
                                    .addComponent(txtNT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel63)
                                    .addComponent(txtTC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addGap(11, 11, 11)
                                .addComponent(btThemFileMT)))
                        .addGap(0, 60, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addComponent(jLabel64)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btOKTT)
                                    .addComponent(btThemTT)))
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel65)
                                    .addComponent(cbMMT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel66)
                                    .addComponent(cbMS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtNgayTra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel67))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel68)
                                    .addComponent(txtTP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel69, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtGC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btHuyTT)
                                    .addComponent(btSuaTT))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btXoaTT)
                                    .addComponent(btThemFileTT))
                                .addGap(57, 57, 57)))
                        .addContainerGap())
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        jPanel9Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {txtMMT, txtNM, txtNT, txtTC});

        jPanel9Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {cbMDG, cbMNV, txtGC, txtNgayTra, txtTP});

        jTabbedPane4.addTab("Thêm, sửa,xóa", jPanel9);

        jPanel12.setBackground(new java.awt.Color(255, 255, 204));

        tbTKMT.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane7.setViewportView(tbTKMT);

        jLabel42.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel42.setIcon(new javax.swing.ImageIcon("C:\\Users\\Admin\\Desktop\\ảnh đồ án\\Info.png")); // NOI18N
        jLabel42.setText("Tìm kiếm thông tin mượn trả");

        jLabel43.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel43.setText("Mã mượn trả:");

        jLabel44.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel44.setText("Mã độc giả:");

        jLabel45.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel45.setText("Mã nhân viên:");

        jLabel46.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel46.setText("Ngày mượn:");

        jLabel47.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel47.setText("Ngày hẹn trả:");

        jLabel48.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel48.setText("Tiền cọc:");

        btTKMT.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btTKMT.setIcon(new javax.swing.ImageIcon("C:\\Users\\Admin\\Desktop\\ảnh đồ án\\timkiem2.png")); // NOI18N
        btTKMT.setText("Tìm kiếm");
        btTKMT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btTKMTActionPerformed(evt);
            }
        });

        btNewTKMT.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btNewTKMT.setIcon(new javax.swing.ImageIcon("C:\\Users\\Admin\\Desktop\\ảnh đồ án\\nhập lại (1).png")); // NOI18N
        btNewTKMT.setText("Nhập lại");
        btNewTKMT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btNewTKMTActionPerformed(evt);
            }
        });

        btInTKMT.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btInTKMT.setIcon(new javax.swing.ImageIcon("C:\\Users\\Admin\\Desktop\\ảnh đồ án\\xuất file.png")); // NOI18N
        btInTKMT.setText("In");
        btInTKMT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btInTKMTActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel44, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel45, javax.swing.GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE)
                            .addComponent(jLabel48, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel47, javax.swing.GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE)
                            .addComponent(jLabel46, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtMaMT)
                            .addComponent(txtMaDG)
                            .addComponent(txtMaNV)
                            .addComponent(txtNgayMuon)
                            .addComponent(txtNHT)
                            .addComponent(txtTCoc, javax.swing.GroupLayout.DEFAULT_SIZE, 255, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(58, 58, 58)
                        .addComponent(btTKMT)
                        .addGap(57, 57, 57)
                        .addComponent(btNewTKMT, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(143, 143, 143)
                        .addComponent(btInTKMT, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 761, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 342, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel42)
                        .addGap(19, 19, 19)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel43)
                            .addComponent(txtMaMT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel44)
                            .addComponent(txtMaDG, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel45)
                            .addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel46)
                            .addComponent(txtNgayMuon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel47)
                            .addComponent(txtNHT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel48)
                            .addComponent(txtTCoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btTKMT)
                            .addComponent(btNewTKMT))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btInTKMT, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane4.addTab("Tìm kiếm", jPanel12);

        jPanel14.setBackground(new java.awt.Color(255, 255, 204));

        tbThongKeMuonTra.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane8.setViewportView(tbThongKeMuonTra);

        jLabel49.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel49.setIcon(new javax.swing.ImageIcon("C:\\Users\\Admin\\Desktop\\ảnh đồ án\\thống kê.png")); // NOI18N
        jLabel49.setText("Thống kê mượn trả theo:");

        jLabel50.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel50.setText("Bảng thống kê mượn trả");

        btThongkeMDG.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btThongkeMDG.setText("Theo mã độc giả");
        btThongkeMDG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btThongkeMDGActionPerformed(evt);
            }
        });

        btThongKeMNV.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btThongKeMNV.setText("Theo mã nhân viên");
        btThongKeMNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btThongKeMNVActionPerformed(evt);
            }
        });

        btThongKeNM.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btThongKeNM.setText("Theo ngày mượn");
        btThongKeNM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btThongKeNMActionPerformed(evt);
            }
        });

        btThongkeNHT.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btThongkeNHT.setText("Theo ngày hẹn trả");
        btThongkeNHT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btThongkeNHTActionPerformed(evt);
            }
        });

        btThongkeTC.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btThongkeTC.setText("Theo tiền cọc");
        btThongkeTC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btThongkeTCActionPerformed(evt);
            }
        });

        btInThongKeMT.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btInThongKeMT.setIcon(new javax.swing.ImageIcon("C:\\Users\\Admin\\Desktop\\ảnh đồ án\\xuất file.png")); // NOI18N
        btInThongKeMT.setText("In");
        btInThongKeMT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btInThongKeMTActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addGap(66, 66, 66)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btThongkeTC, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btThongkeNHT, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btThongKeMNV, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 164, Short.MAX_VALUE)
                    .addComponent(btThongKeNM, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btThongkeMDG, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                .addComponent(btInThongKeMT, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(53, 53, 53)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 726, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50))
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addComponent(jLabel49)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel50)
                .addGap(323, 323, 323))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel49)
                    .addComponent(jLabel50))
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel14Layout.createSequentialGroup()
                                .addGap(41, 41, 41)
                                .addComponent(btThongkeMDG)
                                .addGap(27, 27, 27)
                                .addComponent(btThongKeMNV)
                                .addGap(18, 18, 18)
                                .addComponent(btThongKeNM))
                            .addGroup(jPanel14Layout.createSequentialGroup()
                                .addGap(115, 115, 115)
                                .addComponent(btInThongKeMT)))
                        .addGap(18, 18, 18)
                        .addComponent(btThongkeNHT)
                        .addGap(18, 18, 18)
                        .addComponent(btThongkeTC))
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(87, Short.MAX_VALUE))
        );

        jTabbedPane4.addTab("Thống kê", jPanel14);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 1186, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jTabbedPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 555, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 2, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Mượn Trả", jPanel3);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 0, 0));
        jLabel1.setText("Thực hiện bởi: Nguyễn Thị Hải Yến-20167045");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setIcon(new javax.swing.ImageIcon("C:\\Users\\Admin\\Desktop\\ảnh đồ án\\p.png")); // NOI18N
        jLabel2.setText("Xin chào thủ thư!");

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon("C:\\Users\\Admin\\Desktop\\ảnh đồ án\\Exit.png")); // NOI18N
        jButton1.setText("Đăng xuất");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(791, Short.MAX_VALUE)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(jButton1)
                .addGap(136, 136, 136))
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(13, 13, 13)))
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 562, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
         if(JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn đăng xuất không?","Xác nhận",
                  JOptionPane.YES_NO_OPTION)== JOptionPane.YES_NO_OPTION){
              Login t= new Login();
              t.setVisible(true);
              dispose();
              JOptionPane.showMessageDialog(this, "Bạn đã đăng xuất thành công!");
         }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btThongkeTGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btThongkeTGActionPerformed
        // TODO add your handling code here:
        click=3;
        String tktg="Select Tacgia as 'Tác giả', sum(Soluong) as 'Số lượng' from BangSach group by Tacgia";
        UpdateTable.LoadData(tktg, tbTKSach);
    }//GEN-LAST:event_btThongkeTGActionPerformed

    private void btThongkeDGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btThongkeDGActionPerformed
        // TODO add your handling code here:
        click=7;
        String tkdg="Select DonGia as 'Đơn Giá', sum(Soluong) as 'Số lượng' from BangSach group by DonGia";
        UpdateTable.LoadData(tkdg, tbTKSach);
    }//GEN-LAST:event_btThongkeDGActionPerformed

    private void btThongkeTLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btThongkeTLActionPerformed
        // TODO add your handling code here:
        click=5;
        String tktl="Select TheLoai as 'Thể loại', sum(Soluong) as 'Số lượng' from BangSach group by TheLoai";
        UpdateTable.LoadData(tktl, tbTKSach);
    }//GEN-LAST:event_btThongkeTLActionPerformed

    private void btThongkeTSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btThongkeTSActionPerformed
        // TODO add your handling code here:
        click=2;
        String tkts="Select TenSach as 'Tên sách', sum(Soluong) as 'Số lượng' from BangSach group by TenSach";
        UpdateTable.LoadData(tkts, tbTKSach);
    }//GEN-LAST:event_btThongkeTSActionPerformed

    private void btThongkeNXBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btThongkeNXBActionPerformed
        // TODO add your handling code here:
        click=4;
        String tknxb="Select Nhaxuatban as 'Nhà xuất bản', sum(Soluong) as 'Số lượng' from BangSach group by Nhaxuatban";
        UpdateTable.LoadData(tknxb, tbTKSach);
    }//GEN-LAST:event_btThongkeNXBActionPerformed

    private void btThongkeMSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btThongkeMSActionPerformed
        // TODO add your handling code here:
        click=1;
        String tkms="Select MaSach as 'Mã sách', sum(Soluong) as 'Số lượng' from BangSach group by MaSach";
        UpdateTable.LoadData(tkms, tbTKSach);
    }//GEN-LAST:event_btThongkeMSActionPerformed

    private void btThongkeNBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btThongkeNBActionPerformed
        // TODO add your handling code here:
        click=6;
        String tknb="Select NamXB as ' Năm xuất bản', sum(Soluong) as 'Số lượng' from BangSach group by NamXB";
        UpdateTable.LoadData(tknb, tbTKSach);
    }//GEN-LAST:event_btThongkeNBActionPerformed

    private void btResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btResetActionPerformed

        this.txtTKMS.setText(null);
        this.txtTKTS.setText(null);
        this.txtTKTG.setText(null);

        this.txtTKNXB.setText(null);
        this.txtTKTL.setText(null);
        this.txtTKSL.setText(null);
        this.txtTKNB.setText(null);

        this.txtTKDG.setText(null);
    }//GEN-LAST:event_btResetActionPerformed

    private void btTKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btTKActionPerformed
        String sql = "SELECT * FROM BangSach WHERE ";
        int kiemtra = 0;
        String MaSach = this.txtTKMS.getText();
        String TenSach = this.txtTKTS.getText();
        String TacGia = this.txtTKTG.getText();
        String NhaXuatBan = this.txtTKNXB.getText();
        String TheLoai = this.txtTKTL.getText();
        String SoLuong = this.txtTKSL.getText();
        String NamXuatBan = this.txtTKNB.getText();
        String dongia = this.txtTKDG.getText();

        if (MaSach.length() == 0 && TenSach.length() == 0 && TacGia.length() == 0
            && NhaXuatBan.length() == 0 && TheLoai.length() == 0 && NamXuatBan.length() == 0 && SoLuong.length()==0&&dongia.length()==0)

        JOptionPane.showMessageDialog(null, "Bạn chưa nhập thông tin tìm kiếm!", "Thông báo", 1);
        else
        {
            if (MaSach.length() > 0)
            {
                sql = sql + "MaSach LIKE '%"+MaSach+"%'";
                kiemtra = 1;
            }
            if (TenSach.length() > 0)
            {
                if (kiemtra == 0)
                {
                    sql = sql + "TenSach LIKE N'%"+TenSach+"%'";
                    kiemtra = 1;
                }
                else
                sql = sql + " AND TenSach LIKE N'%"+TenSach+"%'";
            }
            if (TacGia.length() > 0)
            {
                if (kiemtra == 0)
                {
                    sql = sql + "Tacgia LIKE N'%"+TacGia+"%'";
                    kiemtra = 1;
                }
                else
                sql = sql + " AND Tacgia LIKE N'%"+TacGia+"%'";
            }
            if (NhaXuatBan.length() > 0)
            {
                if (kiemtra == 0)
                {
                    sql = sql + "Nhaxuatban LIKE N'%"+NhaXuatBan+"%'";
                    kiemtra = 1;
                }
                else
                sql = sql + " AND Nhaxuatban LIKE N'%"+NhaXuatBan+"%'";
            }

            if (TheLoai.length() > 0)
            {
                if (kiemtra == 0)
                {
                    sql = sql + "TheLoai LIKE N'%"+TheLoai+"%'";
                    kiemtra = 1;
                }
                else
                sql = sql + " AND TheLoai LIKE N'%"+TheLoai+"%'";
            }
            if (SoLuong.length() > 0)
            {
                if (kiemtra == 0)
                {
                    sql = sql + "Soluong LIKE '%"+SoLuong+"%'";
                    kiemtra = 1;
                }
                else
                sql = sql + " AND Soluong LIKE '%"+SoLuong+"%'";
            }
            if (NamXuatBan.length() > 0)
            {
                if (kiemtra == 0)
                {
                    sql = sql + "NamXB LIKE '%"+NamXuatBan+"%'";
                    kiemtra = 1;
                }
                else
                sql = sql + " AND NamXB LIKE '%"+NamXuatBan+"%'";
            }
            if (dongia.length() > 0)
            {
                if (kiemtra == 0)
                {
                    sql = sql + "DonGia LIKE '%"+dongia+"%'";
                    kiemtra = 1;
                }
                else
                sql = sql + " AND DonGia LIKE '%"+dongia+"%'";
            }

            UpdateTable.LoadData(sql, tbTKS);
        }
    }//GEN-LAST:event_btTKActionPerformed
 
    private void btNewSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btNewSActionPerformed
        ProcessCtr(false);
        this.btThem.setEnabled(true);
        this.txtMS.setText(null);
        this.txtTS.setText(null);
        this.txtTG.setText(null);
        this.txtNXB.setText(null);
        this.txtTL.setText(null);
        this.txtSL.setText(null);
        this.txtNB.setText(null);
        this.txtDG.setText(null);

    }//GEN-LAST:event_btNewSActionPerformed

    private void btXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btXoaActionPerformed
        if (this.txtMS.getText().length()==0) JOptionPane.showMessageDialog(null, "Mã nhân viên không thể bỏ trống", "thông báo", 2);
        //else if(this.txtMS.getText().length()>50) JOptionPane.showMessageDialog(null, "Mã nhân viên không được lớn hơn 50 ký tự", "thông báo", 2);
        else {
            if(sach.DeleteSach(this.txtMS.getText())) {
                if(JOptionPane.showConfirmDialog(this, "Bạn có muốn xóa?","Xác nhận",
                    JOptionPane.YES_NO_OPTION)== JOptionPane.YES_NO_OPTION){
                JOptionPane.showMessageDialog(this, "Bạn đã xóa thành công!");
                //JOptionPane.showMessageDialog(null, "Bạn đã xóa thành công", "Thông báo", 1);
            }
            else JOptionPane.showMessageDialog(null, "Có lỗi xảy ra", "Thông báo", 2);
            UpdateTable.LoadData(sql1, tbS);
            //this.btTK.doClick();
        }
        }
    }//GEN-LAST:event_btXoaActionPerformed

    private void btSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSuaActionPerformed
        //ProcessCtr(false);
        if (this.txtMS.getText().length()==0) JOptionPane.showMessageDialog(null, "Mã sách không thể bỏ trống", "thông báo", 2);
        //else if(this.txtMNV.getText().length()>50) JOptionPane.showMessageDialog(null, "Mã nhân viên không được lớn hơn 50 ký tự", "thông báo", 2);
        else {
            Sach s = new Sach(this.txtMS.getText(),this.txtTS.getText(),this.txtTG.getText(),this.txtNXB.getText(),
                this.txtTL.getText(),Integer.parseInt(this.txtSL.getText()),Integer.parseInt(this.txtNB.getText()),Integer.parseInt(this.txtDG.getText()));
            if(sach.UpdateSach(s)) {
                JOptionPane.showMessageDialog(null, "Bạn đã sửa thành công", "Thông báo", 1);
                String sql3="select*from BangSach";
                UpdateTable.LoadData(sql3, tbS);
            }
            else JOptionPane.showMessageDialog(null, "Có lỗi xảy ra", "Thông báo", 2);
            //this.btTK.doClick();
        }
    }//GEN-LAST:event_btSuaActionPerformed

    private void btThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btThemActionPerformed
        //ProcessCtr(false);
        try{
            if (this.txtMS.getText().length()==0) JOptionPane.showMessageDialog(null, "Mã sách không thể bỏ trống", "thông báo", 2);
            //else if(this.txtMNV.getText().length()>50) JOptionPane.showMessageDialog(null, "Mã nhân viên không được lớn hơn 50 ký tự", "thông báo", 2);
            else {
                Sach s = new Sach(this.txtMS.getText(),this.txtTS.getText(),this.txtTG.getText(),this.txtNXB.getText(),
                    this.txtTL.getText(),Integer.parseInt(this.txtSL.getText()),Integer.parseInt(this.txtNB.getText()),Integer.parseInt(this.txtDG.getText()));
                SachData.InsertSach(s);
                String sql2="Select*from BangSach";
                UpdateTable.LoadData(sql2, tbS);
                //  this.btTK.doClick();
            }
        }catch(Exception e) {
            JOptionPane.showMessageDialog(null, "Có lỗi xảy ra", "Thông báo", 2);
        }

    }//GEN-LAST:event_btThemActionPerformed

    private void tbSMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbSMouseClicked
        ProcessCtr(true);
        this.btThem.setEnabled(false);
        try{
            int row = this.tbS.getSelectedRow();
            String IDrow = (this.tbS.getModel().getValueAt(row, 0)).toString();
            String sql1 = "SELECT * FROM BangSach where MaSach='"+IDrow+"'";
            ResultSet rs = UpdateTable.ShowTextField(sql1);
            if(rs.next()) {
                this.txtMS.setText(rs.getString("MaSach"));
                this.txtTS.setText(rs.getString("TenSach"));
                this.txtTG.setText(rs.getString("Tacgia"));
                this.txtNXB.setText(rs.getString("Nhaxuatban"));
                this.txtTL.setText((rs.getString("TheLoai")));
                this.txtSL.setText(rs.getString("Soluong"));
                this.txtNB.setText(rs.getString("NamXB"));
                this.txtDG.setText(rs.getString("DonGia"));
            }
        }catch(Exception e) {

        }
    }//GEN-LAST:event_tbSMouseClicked

    private void tbDGMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDGMouseClicked
        ProcessCtr1(true);
        this.btThemDG.setEnabled(false);
        try{
            int row = this.tbDG.getSelectedRow();
            String IDrow = (this.tbDG.getModel().getValueAt(row, 0)).toString();
            String sql1 = "SELECT * FROM BangDocGia where MaDocGia='"+IDrow+"'";
            ResultSet rs = UpdateTable.ShowTextField(sql1);
            if(rs.next()) {
                this.txtMDG.setText(rs.getString("MaDocGia"));
                this.txtTDG.setText(rs.getString("TenDocGia"));
                this.txtNS.setText(rs.getString("Ngaysinh"));
                this.txtGT.setText(rs.getString("Gioitinh"));
                this.txtCMND.setText((rs.getString("CMND")));
                this.txtEM.setText(rs.getString("Email"));
                this.txtSDT.setText(rs.getString("Sodienthoai"));
                this.txtDC.setText(rs.getString("Diachi"));
            }
        }catch(Exception e) {

        }
        
    }//GEN-LAST:event_tbDGMouseClicked

    private void btThemDGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btThemDGActionPerformed
        try{
            if(this.txtMDG.getText().length()==0)
                JOptionPane.showMessageDialog(null, "Mã độc giả không được bỏ trống");
            else{
                DocGia s= new DocGia(this.txtMDG.getText(),this.txtTDG.getText(),Date.valueOf(this.txtNS.getText()),
                this.txtGT.getText(),this.txtCMND.getText(),this.txtEM.getText(),this.txtSDT.getText(),this.txtDC.getText());
                DocGiaData.InsertDocGia(s);
                String sql="select*from BangDocGia order by MaDocGia asc ";
                UpdateTable.LoadData(sql, tbDG);
                
            }
        }catch(Exception e){
            
        }
    }//GEN-LAST:event_btThemDGActionPerformed

    private void btNewDGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btNewDGActionPerformed
        ProcessCtr1(false);
        this.btThemDG.setEnabled(true);
        this.txtMDG.setText(null);
        this.txtTDG.setText(null);
        this.txtNS.setText(null);
        this.txtGT.setText(null);
        this.txtCMND.setText(null);
        this.txtEM.setText(null);
        this.txtSDT.setText(null);
        this.txtDC.setText(null);
    }//GEN-LAST:event_btNewDGActionPerformed

    private void btSuaDGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSuaDGActionPerformed
        // TODO add your handling code here:
        try{
            if(this.txtMDG.getText().length()==0)
                JOptionPane.showMessageDialog(null, "Mã độc giả không được bỏ trống!");
            else{
                DocGia s=new DocGia(this.txtMDG.getText(),this.txtTDG.getText(),Date.valueOf(this.txtNS.getText()),
                this.txtGT.getText(),this.txtCMND.getText(),this.txtEM.getText(),this.txtSDT.getText(),this.txtDC.getText());
                docgia.UpdateDocGia(s);
                 JOptionPane.showMessageDialog(null, "Đã sửa độc giả thành công!");
                String sql="select*from BangDocGia order by MaDocGia asc";
                UpdateTable.LoadData(sql, tbDG);
                
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Độc giả không được sửa!");
        }
    }//GEN-LAST:event_btSuaDGActionPerformed

    private void btXoaDGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btXoaDGActionPerformed
        // TODO add your handling code here:
         if (this.txtMDG.getText().length()==0) JOptionPane.showMessageDialog(null, "Mã độc giả không thể bỏ trống", "thông báo", 2);
        //else if(this.txtMS.getText().length()>50) JOptionPane.showMessageDialog(null, "Mã nhân viên không được lớn hơn 50 ký tự", "thông báo", 2);
        else {
            if(docgia.DeleteDocGia(this.txtMDG.getText())) {
                if(JOptionPane.showConfirmDialog(this, "Bạn có muốn xóa?","Xác nhận",
                    JOptionPane.YES_NO_OPTION)== JOptionPane.YES_NO_OPTION){
                JOptionPane.showMessageDialog(this, "Bạn đã xóa thành công!");
                //JOptionPane.showMessageDialog(null, "Bạn đã xóa thành công", "Thông báo", 1);
            }
            else JOptionPane.showMessageDialog(null, "Có lỗi xảy ra", "Thông báo", 2);
                String sql1="select*from BangDocGia order by MaDocGia asc";
            UpdateTable.LoadData(sql1, tbDG);
            //this.btTK.doClick();
        }
        }
    }//GEN-LAST:event_btXoaDGActionPerformed

    private void btTKDGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btTKDGActionPerformed
        // TODO add your handling code here:
       String sql = "SELECT * FROM BangDocGia WHERE ";
       int kiemtra=0;
       String madocgia= this.txtTKMDG.getText();
       String tendocgia=this.txtTKTDG.getText();
       String ngaysinh =this.txtTKNS.getText();
       String gioitinh=this.txtTKGT.getText();
       String cmnd=this.txtTKCMND.getText();
       String Email=this.txtTKEM.getText();
       String sdt=this.txtTKSDT.getText();
       String diachi=this.txtTKDC.getText();
       
       if(madocgia.length()==0&&tendocgia.length()==0&&ngaysinh.length()==0&&gioitinh.length()==0
               &&cmnd.length()==0&&Email.length()==0&&sdt.length()==0&&diachi.length()==0)
           JOptionPane.showMessageDialog(null,"Bạn chưa nhập thông tin tìm kiếm!");
       else{
            if (madocgia.length() > 0)
            {
                sql = sql + "MaDocGia LIKE '%"+madocgia+"%'";
                kiemtra = 1;
            }
            if (tendocgia.length() > 0)
            {
                if (kiemtra == 0)
                {
                    sql = sql + "TenDocGia LIKE N'%"+tendocgia+"%'";
                    kiemtra = 1;
                }
                else
                sql = sql + " AND TenDocGia LIKE N'%"+tendocgia+"%'";
            }
            if (ngaysinh.length() > 0)
            {
                if (kiemtra == 0)
                {
                    sql = sql + "Ngaysinh LIKE '%"+ngaysinh+"%'";
                    kiemtra = 1;
                }
                else
                sql = sql + " AND Ngaysinh LIKE '%"+ngaysinh+"%'";
            }
            if (gioitinh.length() > 0)
            {
                if (kiemtra == 0)
                {
                    sql = sql + "Gioitinh LIKE N'%"+gioitinh+"%'";
                    kiemtra = 1;
                }
                else
                sql = sql + " AND Gioitinh LIKE N'%"+gioitinh+"%'";
            }

            if (cmnd.length() > 0)
            {
                if (kiemtra == 0)
                {
                    sql = sql + "CMND LIKE '%"+cmnd+"%'";
                    kiemtra = 1;
                }
                else
                sql = sql + " AND CMND LIKE '%"+cmnd+"%'";
            }
            if (Email.length() > 0)
            {
                if (kiemtra == 0)
                {
                    sql = sql + "Email LIKE '%"+Email+"%'";
                    kiemtra = 1;
                }
                else
                sql = sql + " AND Email LIKE '%"+Email+"%'";
            }
            if (sdt.length() > 0)
            {
                if (kiemtra == 0)
                {
                    sql = sql + "Sodienthoai LIKE '%"+sdt+"%'";
                    kiemtra = 1;
                }
                else
                sql = sql + " AND Sodienthoai LIKE '%"+sdt+"%'";
            }
            if (diachi.length() > 0)
            {
                if (kiemtra == 0)
                {
                    sql = sql + "Diachi LIKE N'%"+diachi+"%'";
                    kiemtra = 1;
                }
                else
                sql = sql + " AND Diachi LIKE N'%"+diachi+"%'";
            }

            UpdateTable.LoadData(sql, tbTKDG);
       }
       /* private void btNewSActionPerformed(java.awt.event.ActionEvent evt) {     */                                  
       
    }                                      
/*
     private void btXoaActionPerformed(java.awt.event.ActionEvent evt) {*/                                      
     
    /*  
    }//GEN-LAST:event_btTKDGActionPerformed
*/
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
       this.txtTKMDG.setText(null);
       this.txtTKTDG.setText(null);
       this.txtTKNS.setText(null);
       this.txtTKCMND.setText(null);
       this.txtTKGT.setText(null);
       this.txtTKEM.setText(null);
       this.txtTKDC.setText(null);
       this.txtTKSDT.setText(null);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void btThongkeGTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btThongkeGTActionPerformed
        // TODO add your handling code here:
        click=1;
        String sql=" Select Gioitinh as 'Giới tính', count(Gioitinh) as 'Số lượng' from BangDocGia group by Gioitinh";
        UpdateTable.LoadData(sql, tbThongKeDG);
    }//GEN-LAST:event_btThongkeGTActionPerformed

    private void btThongKeDCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btThongKeDCActionPerformed
        // TODO add your handling code here:
        click=2;
        String sql="select Diachi as 'Địa chỉ', count(Diachi) as 'Số lượng' from BangDocGia group by Diachi";
        UpdateTable.LoadData(sql, tbThongKeDG);
    }//GEN-LAST:event_btThongKeDCActionPerformed

    private void btThongKeNSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btThongKeNSActionPerformed
        // TODO add your handling code here:
        click=3;
        String sql="select Ngaysinh as 'Ngày sinh', count(Ngaysinh) as 'Số lượng' from BangDocGia group by Ngaysinh";
        UpdateTable.LoadData(sql, tbThongKeDG);
    }//GEN-LAST:event_btThongKeNSActionPerformed

    private void btThongKeTDGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btThongKeTDGActionPerformed
        // TODO add your handling code here:
        click=4;
        String sql="select TenDocGia as 'Tên độc giả', count(TenDocGia) as 'Số lượng' from BangDocGia group by TenDocGia";
        UpdateTable.LoadData(sql, tbThongKeDG);
    }//GEN-LAST:event_btThongKeTDGActionPerformed

    private void btInThongKeSachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btInThongKeSachActionPerformed
        // TODO add your handling code here:
        switch (click) {
            case  1:
            try {
                ExportPDF.XuatTK(tbTKSach, "BangSach", "Mã sách", "Mã sách", "Số lượng","C:\\Users\\Admin\\Documents\\đồ án 1\\Xuất file biểu mẫu/");
            } catch (Exception ex) {
            }   break;
            case 2:
            try {
                ExportPDF.XuatTK(tbTKSach, "bảng sách", "Tên sách", "Tên sách", "Số lượng","C:\\Users\\Admin\\Documents\\đồ án 1\\Xuất file biểu mẫu/");
            } catch (Exception ex) {
            }   break;
            case 3:
            try {
                ExportPDF.XuatTK(tbTKSach, "bảng sách", "Tác giả", "Tác giả", "Số lượng","C:\\Users\\Admin\\Documents\\đồ án 1\\Xuất file biểu mẫu/");
            } catch (Exception ex) {
            }   break;

            case 4:
            try {
                ExportPDF.XuatTK(tbTKSach, "bảng sách", "Nhà xuất bản", "Nhà xuất bản", "Số lượng","C:\\Users\\Admin\\Documents\\đồ án 1\\Xuất file biểu mẫu/");
            } catch (Exception ex) {
            }   break;
            case 6:
            try {
                ExportPDF.XuatTK(tbTKSach, "bảng sách", "Năm xuất bản", "Năm xuất bản", "Số lượng","C:\\Users\\Admin\\Documents\\đồ án 1\\Xuất file biểu mẫu/");
            } catch (Exception ex) {
            }   break;
            case 5:
            try {
                ExportPDF.XuatTK(tbTKSach, "bảng sách", "Thể loại", "Thể loại", "Số lượng","C:\\Users\\Admin\\Documents\\đồ án 1\\Xuất file biểu mẫu/");
            } catch (Exception ex) {
            }   break;
            case 7:
            try {
                ExportPDF.XuatTK(tbTKSach, "bảng sách", "Đơn Giá", "Đơn Giá", "Số lượng","C:\\Users\\Admin\\Documents\\đồ án 1\\Xuất file biểu mẫu/");
            } catch (Exception ex) {
            }   break;
           
        }
    }//GEN-LAST:event_btInThongKeSachActionPerformed

    private void btIntimkiemsachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btIntimkiemsachActionPerformed
        // TODO add your handling code here:
          try {
            ExportPDF.XuatTimKiem(tbTKS, "Sách", "C:\\Users\\Admin\\Documents\\đồ án 1\\Xuất file biểu mẫu/");
        } catch (Exception e) {
        }    
    }//GEN-LAST:event_btIntimkiemsachActionPerformed

    private void btThemFileSachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btThemFileSachActionPerformed
        // TODO add your handling code here:
       String MaSach="";
        String TenSach="";
        String Tacgia="";
        String Nhaxuatban="";
        String TheLoai="";
        String Soluong="";
        String NamXB="";
        String DonGia="";
        try {
            File f=new File("C:\\Users\\Admin\\Documents\\đồ án 1\\Thêm file\\Thêm sách.xls");
            Workbook wb=Workbook.getWorkbook(f);
            Sheet s=wb.getSheet(0);
            int row=s.getRows();
            int col=s.getColumns();
            for(int i = 0;i<row;i++){
                Cell c= s. getCell(0,i);
                MaSach=c.getContents();
                c= s. getCell(1,i);
                TenSach=c.getContents();
                c= s. getCell(2,i);
                Tacgia=c.getContents();
                c= s. getCell(3,i);
                Nhaxuatban=c.getContents();
                c= s. getCell(4,i);
                TheLoai=c.getContents();
                c= s. getCell(5,i);
                Soluong=c.getContents();
                c= s. getCell(6,i);
                NamXB=c.getContents();
                c= s. getCell(7,i);
                DonGia=c.getContents();
        String query="insert into BangSach values('"+MaSach+"','"+TenSach+"','"+Tacgia+"','"+Nhaxuatban+"','"+TheLoai+"','"+Soluong+"','"+NamXB+"','"+DonGia+"')";
        if (txtMS.getText().equals(s)) JOptionPane.showMessageDialog(this, "Mã sách đã bị trùng");
        try{
            Data.Connect a=new Data.Connect();
            Connection conn=a.getConnect();
            Statement st=conn.createStatement();
            if(st.executeUpdate(query)==1){
                //JOptionPane.showMessageDialog(this, "Thêm File thành công!");
                 
               tbn.setRowCount(0);
                UpdateTable.LoadData(sql1, tbS);
            }
             //JOptionPane.showMessageDialog(this, "Thêm File thành công!");
            
        }catch(Exception e){
            System.out.println(e.toString());
        }
                
            }
        } catch (IOException ex) {
           Logger.getLogger(MainThuThu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BiffException ex) {
            Logger.getLogger(MainThuThu.class.getName()).log(Level.SEVERE, null, ex);
        }
   JOptionPane.showMessageDialog(this, "Thêm File thành công!");
             


    }//GEN-LAST:event_btThemFileSachActionPerformed

    private void btXuatFileSachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btXuatFileSachActionPerformed
        // TODO add your handling code here:
     JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Save file");
        int choose = chooser.showSaveDialog(null);
        File file = chooser.getSelectedFile();
         DefaultTableModel model1 = (DefaultTableModel) tbS.getModel();
         if (choose == JFileChooser.APPROVE_OPTION) {
                        DocGhiExel rp = new DocGhiExel();
                        rp.getExcel("DANH SÁCH SÁCH HIỆN TẠI", model1, file);
                        JOptionPane.showMessageDialog(getParent(), " Đã lưu !!!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    }
    }//GEN-LAST:event_btXuatFileSachActionPerformed

    private void btThongKeDGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btThongKeDGActionPerformed
        // TODO add your handling code here:
         switch (click) {
            case  1:
            try {
                ExportPDF.XuatTK(tbThongKeDG, "bảng độc giả", "Giới tính", "Giới tính", "Số lượng","C:\\Users\\Admin\\Documents\\đồ án 1\\Xuất file biểu mẫu/");
            } catch (Exception ex) {
            }   break;
            case 2:
            try {
                ExportPDF.XuatTK(tbThongKeDG, "bảng độc giả", "Địa chỉ", "Địa chỉ", "Số lượng","C:\\Users\\Admin\\Documents\\đồ án 1\\Xuất file biểu mẫu/");
            } catch (Exception ex) {
            }   break;
            case 3:
            try {
                ExportPDF.XuatTK(tbThongKeDG, "bảng độc giả", "Ngày sinh", "Ngày sinh", "Số lượng","C:\\Users\\Admin\\Documents\\đồ án 1\\Xuất file biểu mẫu/");
            } catch (Exception ex) {
            }   break;

            case 4:
            try {
                ExportPDF.XuatTK(tbThongKeDG, "bảng độc giả", "Tên độc giả", "Tên độc giả", "Số lượng","C:\\Users\\Admin\\Documents\\đồ án 1\\Xuất file biểu mẫu/");
            } catch (Exception ex) {
            }   break;
         }
    }//GEN-LAST:event_btThongKeDGActionPerformed

    private void btNhapFileDGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btNhapFileDGActionPerformed
       String MaDocGia="";
        String TenDocGia="";
        String Ngaysinh="";
        String Gioitinh="";
        String CMND="";
        String Email="";
        String Sodienthoai="";
        String Diachi="";
        try {
            File f=new File("C:\\Users\\Admin\\Documents\\đồ án 1\\Thêm file\\Thêm độc giả.xls");
            Workbook wb=Workbook.getWorkbook(f);
            Sheet s=wb.getSheet(0);
            int row=s.getRows();
            int col=s.getColumns();
            for(int i = 0;i<row;i++){
                Cell c= s. getCell(0,i);
                MaDocGia=c.getContents();
                c= s. getCell(1,i);
                TenDocGia=c.getContents();
                c= s. getCell(2,i);
                Ngaysinh =c.getContents().toString();
                c= s. getCell(3,i);
                Gioitinh=c.getContents();
                c= s. getCell(4,i);
                CMND=c.getContents();
                c= s. getCell(5,i);
                Email=c.getContents();
                c= s. getCell(6,i);
                Sodienthoai=c.getContents();
                c= s. getCell(7,i);
                Diachi=c.getContents();
        String query="insert into BangDocGia values('"+MaDocGia+"','"+TenDocGia+"','"+Ngaysinh+"','"+Gioitinh+"','"+CMND+"','"+Email+"','"+Sodienthoai+"','"+Diachi+"')";
        if (txtMDG.getText().equals(s)) JOptionPane.showMessageDialog(this, "Mã độc giả đã bị trùng");
        try{
            Data.Connect a=new Data.Connect();
            Connection conn=a.getConnect();
            Statement st=conn.createStatement();
            if(st.executeUpdate(query)==1){
                //JOptionPane.showMessageDialog(this, "Thêm File thành công!");
                 
               tbn.setRowCount(0);
                UpdateTable.LoadData(sql2, tbDG);
            }
             //JOptionPane.showMessageDialog(this, "Thêm File thành công!");
            
        }catch(Exception e){
            System.out.println(e.toString());
        }
                
            }
        } catch (IOException ex) {
           Logger.getLogger(MainThuThu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BiffException ex) {
            Logger.getLogger(MainThuThu.class.getName()).log(Level.SEVERE, null, ex);
        }
   JOptionPane.showMessageDialog(this, "Thêm File thành công!");
             
    }//GEN-LAST:event_btNhapFileDGActionPerformed

    private void btXoaTTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btXoaTTActionPerformed
        // TODO add your handling code here:
        if(this.txtMMT.getText().length()==0)
        JOptionPane.showMessageDialog(null, "Bạn cần chọn một dòng để xóa","Thông báo",1);
        else
        {
            if(JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xóa ?","Thông báo",2)==0)
            ChitietmuontraData.DeleteChiTiet(mamuontra);
            UpdateTable.LoadData(sql4, tbChitiet);
            setText();
            ProcessCtrThongTin(false);

        }
    }//GEN-LAST:event_btXoaTTActionPerformed

    private void btSuaTTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSuaTTActionPerformed
        // TODO add your handling code here:
         ProcessCtrThongTin(true);
        isAdd = false;
        
    }//GEN-LAST:event_btSuaTTActionPerformed

    private void btThemTTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btThemTTActionPerformed
        // TODO add your handling code here:
        this.btSuaTT.setEnabled(false);
        this.btXoaTT.setEnabled(false);
        clearChiTiet();
        ProcessCtrThongTin(true);
        this.btThemTT.setEnabled(true);
        this.txtNgayTra.setText(null);
        this.txtGC.setText(null);
    }//GEN-LAST:event_btThemTTActionPerformed

    private void btHuyTTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btHuyTTActionPerformed
        // TODO add your handling code here:
         UpdateTable.LoadData(sql4, tbChitiet);
        ProcessCtrThongTin(false);
        setText();
    }//GEN-LAST:event_btHuyTTActionPerformed

    private void btOKTTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btOKTTActionPerformed
        // TODO add your handling code here:
        if(testChiTiet() == false) {
            
            JOptionPane.showMessageDialog(null, "Thông tin nhập vào bị lỗi!", "Thông báo", JOptionPane.ERROR_MESSAGE);

        }
        else {

            String MaMuonTra= (String)cbMMT.getSelectedItem();
            String MaSach = (String)cbMS.getSelectedItem();
            Date NgayTra = Date.valueOf(txtNgayTra.getText());
            int TienPhat = Integer.parseInt(txtTC.getText().trim());
            String GhiChu = (String) txtGC.getText().trim();
            String sql = "SELECT * FROM BangMuonTra where MaMuonTra = '"+MaMuonTra+"' ";
           ResultSet rs = UpdateTable.ShowTextField(sql);
            
            try {
                
                if (rs.next()) // nếu có dữ liệu
                {
                    
                    Date NgayHenTra = rs.getDate("NgayHenTra");
                    TienPhat = Tienphat(NgayHenTra, NgayTra);
                   // ChitietmuontraData.InsertChiTiet(MaMuonTra, MaSach, NgayTra, TienPhat,GhiChu);
                    //UpdateTable.LoadData(sql, tbChitiet);
                    clearChiTiet();
                }
                else 
                JOptionPane.showMessageDialog(null, "Mã phiếu mượn không tồn tại!", "Thông báo", 1);
                   // ChitietmuontraData.UpdateChiTiet(mamuontra, MaMuonTra, MaSach, NgayTra, TienPhat,GhiChu);
            }
            catch (SQLException ex) {
            
            
            }
            
          
            if(isAdd == true) {
               
               ChitietmuontraData.InsertChiTiet(mamuontra, MaSach, NgayTra, TienPhat,GhiChu);
            }
            else {
                  ChitietmuontraData.UpdateChiTiet(mamuontra, MaMuonTra, MaSach, NgayTra, TienPhat,GhiChu);
            }
          
            UpdateTable.LoadData(sql4, tbChitiet);
            clearChiTiet();
            ProcessCtrThongTin(false);
        }
    }//GEN-LAST:event_btOKTTActionPerformed

    private void btOKMTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btOKMTActionPerformed
        // TODO add your handling code here:
         if(testMuonTra() == false) {
            JOptionPane.showMessageDialog(null, "Thông tin nhập bị lỗi!", "Thông báo", JOptionPane.ERROR_MESSAGE);
        }
        else {
            String MaPhieu = txtMMT.getText().trim();
            String madocgia = (String)cbMDG.getSelectedItem();
            String manhanvien = (String)cbMNV.getSelectedItem();
            Date NgayMuon = Date.valueOf(txtNM.getText());
            Date NgayHenTra = Date.valueOf(txtNT.getText());
            int TienCuoc = Integer.parseInt(txtTC.getText().trim());
            if(isAdd == true) {
                MuonTraData.InsertMuonTra(MaPhieu, madocgia, manhanvien, NgayMuon, NgayHenTra, TienCuoc);
            }
            else {
                MuonTraData.UpdateMuonTra(mamuontra, MaPhieu, madocgia, manhanvien, NgayMuon, NgayHenTra, TienCuoc);

            }
            UpdateTable.LoadData(sql3, tbMuonTra);
            clearMuonTra();
            ProcessCtrMuonTra(false);
        }
    }//GEN-LAST:event_btOKMTActionPerformed

    private void btXoaMTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btXoaMTActionPerformed
        // TODO add your handling code here:
         if(this.txtMMT.getText().length()==0)
        JOptionPane.showMessageDialog(null, "Bạn cần chọn một dòng để xóa","Thông báo",1);
        else
        {
            if(JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xóa ?","Thông báo",2)==0)
            MuonTraData.DeleteMuonTra(mamuontra);
            UpdateTable.LoadData(sql3, tbMuonTra);
            setText();
            ProcessCtrMuonTra(false);

        }
    }//GEN-LAST:event_btXoaMTActionPerformed

    private void btSuaMTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSuaMTActionPerformed
        // TODO add your handling code here:
        
        ProcessCtrMuonTra(true);
        isAdd = false;
    }//GEN-LAST:event_btSuaMTActionPerformed

    private void btThemMTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btThemMTActionPerformed
        // TODO add your handling code here:
         this.btSuaMT.setEnabled(false);
        this.btXoaMT.setEnabled(false);
        clearMuonTra();
        ProcessCtrMuonTra(true);
        this.btThemMT.setEnabled(true);
        this.txtMMT.setText(null);
        this.txtNM.setText(null);
        this.txtNT.setText(null);
        this.txtTC.setText(null);
    }//GEN-LAST:event_btThemMTActionPerformed

    private void tbMuonTraMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbMuonTraMouseClicked
        // TODO add your handling code here:
         this.btSuaMT.setEnabled(true);
        this.btXoaMT.setEnabled(true);
        //this.btOk.setEnabled(false);

        try {
            int row = this.tbMuonTra.getSelectedRow();
            String IDrow = this.tbMuonTra.getModel().getValueAt(row, 0).toString();
            String IDrow1 = this.tbMuonTra.getModel().getValueAt(row, 1).toString();
            String sql = "SELECT * FROM BangMuonTra WHERE MaMuonTra = '" + IDrow + "' and MaDocGia= '"+IDrow1+"'";
            String sql2= "SELECT * FROM BangChitietMuonTra WHERE MaMuonTra='"+IDrow+"'";
            ResultSet rs = UpdateTable.ShowTextField(sql);
            UpdateTable.LoadData(sql2, tbChitiet);

            if(rs.next()) {
                mamuontra = rs.getString("MaMuonTra");
                madocgia = rs.getString("MaDocGia");
                this.txtMMT.setText(rs.getString("MaMuonTra"));
                this.cbMDG.setSelectedItem(rs.getString("MaDocGia"));
                this.cbMNV.setSelectedItem(rs.getString("MaNhanVien"));
                this.txtNM.setText(rs.getString(("NgayMuon")));
                this.txtNT.setText(rs.getString(("NgayHenTra")));
                this.txtTC.setText(rs.getString(("TienCoc")));
                this.cbMMT.setSelectedItem(rs.getString("MaMuonTra"));

            }
        }
        catch (Exception ex) {
            System.err.println(ex.toString());
            JOptionPane.showMessageDialog(null,"Error !", "Thông báo",1);

        }
    }//GEN-LAST:event_tbMuonTraMouseClicked

    private void tbChitietMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbChitietMouseClicked
        // TODO add your handling code here:
         //ProcessCtrThongTin(true);
        this.btSuaTT.setEnabled(true);
        this.btXoaTT.setEnabled(true);
        //this.btOkTT.setEnabled(false);

        try {
            int row = this.tbChitiet.getSelectedRow();
            String IDrow = this.tbChitiet.getModel().getValueAt(row,0 ).toString();
            String IDrow1 = this.tbChitiet.getModel().getValueAt(row, 1).toString();
            String sql1 = "SELECT * FROM BangChitietMuonTra WHERE MaMuonTra= '" + IDrow + "' and MaSach='"+IDrow1+"'";

            ResultSet rs = UpdateTable.ShowTextField(sql1);
            UpdateTable.LoadData(sql1, tbChitiet);

            if(rs.next()) {
                this.cbMMT.setSelectedItem(rs.getString("MaMuonTra"));
                this.cbMS.setSelectedItem(rs.getString("MaSach"));
                this.txtNgayTra.setText(rs.getString(("Ngaytra")));
                this.txtTP.setText(rs.getString(("TienPhat")));
                this.txtGC.setText(rs.getString(("GhiChu")));

            }
        }
        catch (Exception ex) {
            System.err.println(ex.toString());
            JOptionPane.showMessageDialog(null,"Error !", "Thông báo",1);

        }
    }//GEN-LAST:event_tbChitietMouseClicked

    private void btHuyMTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btHuyMTActionPerformed
        // TODO add your handling code here:
         UpdateTable.LoadData(sql3, tbMuonTra);
        ProcessCtrMuonTra(false);
        setText();
    }//GEN-LAST:event_btHuyMTActionPerformed

    private void btInTKDGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btInTKDGActionPerformed
        // TODO add your handling code here:
         try {
            ExportPDF.XuatTimKiem(tbTKDG, "Độc giả", "C:\\Users\\Admin\\Documents\\đồ án 1\\Xuất file biểu mẫu/");
        } catch (Exception e) {
        }    
    }//GEN-LAST:event_btInTKDGActionPerformed

    private void btThemFileMTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btThemFileMTActionPerformed
        // TODO add your handling code here:
        String MaMuonTra="";
        String MaDocGia="";
        String MaNhanVien="";
        String NgayMuon="";
        String NgayHenTra="";
        String TienCoc="";
       
        try {
            File f=new File("C:\\Users\\Admin\\Documents\\đồ án 1\\Thêm file\\Thêm mượn trả.xls");
            Workbook wb=Workbook.getWorkbook(f);
            Sheet s=wb.getSheet(0);
            int row=s.getRows();
            int col=s.getColumns();
            for(int i = 0;i<row;i++){
                Cell c= s. getCell(0,i);
                MaMuonTra=c.getContents();
                c= s. getCell(1,i);
                MaDocGia=c.getContents();
                c= s. getCell(2,i);
                MaNhanVien=c.getContents();
                c= s. getCell(3,i);
               NgayMuon=c.getContents().toString();
                c= s. getCell(4,i);
                NgayHenTra=c.getContents().toString();
                c= s. getCell(5,i);
               TienCoc=c.getContents();
               
              
        String query="insert into BangMuonTra values('"+MaMuonTra+"','"+MaDocGia+"','"+MaNhanVien+"','"+NgayMuon+"','"+NgayHenTra+"','"+TienCoc+"')";
        if (txtMS.getText().equals(s)) JOptionPane.showMessageDialog(this, "Mã sách đã bị trùng");
        try{
            Data.Connect a=new Data.Connect();
            Connection conn=a.getConnect();
            Statement st=conn.createStatement();
            if(st.executeUpdate(query)==1){
                //JOptionPane.showMessageDialog(this, "Thêm File thành công!");
                 
               tbn.setRowCount(0);
                UpdateTable.LoadData(sql3, tbMuonTra);
            }
             //JOptionPane.showMessageDialog(this, "Thêm File thành công!");
            
        }catch(Exception e){
            System.out.println(e.toString());
        }
                
            }
        } catch (IOException ex) {
           Logger.getLogger(MainThuThu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BiffException ex) {
            Logger.getLogger(MainThuThu.class.getName()).log(Level.SEVERE, null, ex);
        }
   JOptionPane.showMessageDialog(this, "Thêm File thành công!");
             


    }//GEN-LAST:event_btThemFileMTActionPerformed

    private void btThemFileTTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btThemFileTTActionPerformed
        // TODO add your handling code here:
        String MaMuonTra="";
        String MaSach="";
        String Ngaytra="";
        String TienPhat="";
        String GhiChu="";
       
       
        try {
            File f=new File("C:\\Users\\Admin\\Documents\\đồ án 1\\Thêm file\\Thêm chi tiết mượn trả.xls");
            Workbook wb=Workbook.getWorkbook(f);
            Sheet s=wb.getSheet(0);
            int row=s.getRows();
            int col=s.getColumns();
            for(int i = 0;i<row;i++){
                Cell c= s. getCell(0,i);
                MaMuonTra=c.getContents();
                c= s. getCell(1,i);
                MaSach=c.getContents();
                c= s. getCell(2,i);
                Ngaytra=c.getContents().toString();
                c= s. getCell(3,i);
               TienPhat=c.getContents();
                c= s. getCell(4,i);
                GhiChu=c.getContents();
                
               
              
        String query="insert into BangChitietMuonTra values('"+MaMuonTra+"','"+MaSach+"','"+Ngaytra+"','"+TienPhat+"','"+GhiChu+"')";
        if (txtMMT.getText().equals(s)) JOptionPane.showMessageDialog(this, "Mã mượn trả đã bị trùng");
        try{
            Data.Connect a=new Data.Connect();
            Connection conn=a.getConnect();
            Statement st=conn.createStatement();
            if(st.executeUpdate(query)==1){
                //JOptionPane.showMessageDialog(this, "Thêm File thành công!");
                 
               tbn.setRowCount(0);
                UpdateTable.LoadData(sql4, tbChitiet);
            }
             //JOptionPane.showMessageDialog(this, "Thêm File thành công!");
            
        }catch(Exception e){
            System.out.println(e.toString());
        }
                
            }
        } catch (IOException ex) {
           Logger.getLogger(MainThuThu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BiffException ex) {
            Logger.getLogger(MainThuThu.class.getName()).log(Level.SEVERE, null, ex);
        }
   JOptionPane.showMessageDialog(this, "Thêm File thành công!");
             
    }//GEN-LAST:event_btThemFileTTActionPerformed

    private void btInPhieuCTMTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btInPhieuCTMTActionPerformed
        // TODO add your handling code here:
        try {
            ExportPDF.XuatPhieuMuon(tbChitiet, "Phiếu mượn", "C:\\Users\\Admin\\Documents\\đồ án 1\\Xuất file biểu mẫu/");
        } catch (Exception e) {
        }
    }//GEN-LAST:event_btInPhieuCTMTActionPerformed

    private void btTKMTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btTKMTActionPerformed
        // TODO add your handling code here:
        String sql = "SELECT * FROM BangMuonTra WHERE ";
        int kiemtra = 0;
        String MaMT = this.txtMaMT.getText();
        String MaDG = this.txtMaDG.getText();
        String MaNV = this.txtMaNV.getText();
        String NgayMuon = this.txtNgayMuon.getText();
        String NHT = this.txtNHT.getText();
        String TCoc = this.txtTCoc.getText();
        

        if (MaMT.length() == 0 && MaDG.length() == 0 && MaNV.length() == 0
            && NgayMuon.length() == 0 && NHT.length() == 0 && TCoc.length() == 0)

        JOptionPane.showMessageDialog(null, "Bạn chưa nhập thông tin tìm kiếm!", "Thông báo", 1);
        else
        {
            if (MaMT.length() > 0)
            {
                sql = sql + "MaMuonTra LIKE '%"+MaMT+"%'";
                kiemtra = 1;
            }
            if (MaDG.length() > 0)
            {
                if (kiemtra == 0)
                {
                    sql = sql + "MaDocGia LIKE '%"+MaDG+"%'";
                    kiemtra = 1;
                }
                else
                sql = sql + " AND MaDocGia LIKE '%"+MaDG+"%'";
            }
            if (MaNV.length() > 0)
            {
                if (kiemtra == 0)
                {
                    sql = sql + "MaNhanVien LIKE '%"+MaNV+"%'";
                    kiemtra = 1;
                }
                else
                sql = sql + " AND MaNhanVien LIKE '%"+MaNV+"%'";
            }
            if (NgayMuon.length() > 0)
            {
                if (kiemtra == 0)
                {
                    sql = sql + "NgayMuon LIKE '%"+NgayMuon+"%'";
                    kiemtra = 1;
                }
                else
                sql = sql + " AND NgayMuon LIKE '%"+NgayMuon+"%'";
            }

            if (NHT.length() > 0)
            {
                if (kiemtra == 0)
                {
                    sql = sql + "NgayHenTra LIKE '%"+NHT+"%'";
                    kiemtra = 1;
                }
                else
                sql = sql + " AND NgayHenTra LIKE '%"+NHT+"%'";
            }
            if (TCoc.length() > 0)
            {
                if (kiemtra == 0)
                {
                    sql = sql + "TienCoc LIKE '%"+TCoc+"%'";
                    kiemtra = 1;
                }
                else
                sql = sql + " AND TienCoc LIKE '%"+TCoc+"%'";
            }
           
            UpdateTable.LoadData(sql, tbTKMT);
        }
    }//GEN-LAST:event_btTKMTActionPerformed

    private void btNewTKMTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btNewTKMTActionPerformed
        // TODO add your handling code here:
        this.txtMaMT.setText(null);
        this.txtMaDG.setText(null);
        this.txtMaNV.setText(null);
        this.txtNgayMuon.setText(null);
        this.txtNHT.setText(null);
        this.txtTCoc.setText(null);
    }//GEN-LAST:event_btNewTKMTActionPerformed

    private void btInTKMTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btInTKMTActionPerformed
        // TODO add your handling code here:
        try {
            ExportPDF.XuatTimKiem(tbTKMT, "Mượn trả", "C:\\Users\\Admin\\Documents\\đồ án 1\\Xuất file biểu mẫu/");
        } catch (Exception e) {
        }    
    }//GEN-LAST:event_btInTKMTActionPerformed

    private void btThongkeMDGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btThongkeMDGActionPerformed
        // TODO add your handling code here:
        click=1;
        String sql="select MaDocGia as 'Mã độc giả',count(MaDocGia) as 'Số lượng' from BangMuonTra group by MaDocGia";
        UpdateTable.LoadData(sql, tbThongKeMuonTra);
    }//GEN-LAST:event_btThongkeMDGActionPerformed

    private void btThongKeMNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btThongKeMNVActionPerformed
        // TODO add your handling code here:
        click=2;
        String sql="select MaNhanVien as 'Mã nhân viên',count(MaNhanVien) as 'Số lượng' from BangMuonTra group by MaNhanVien";
        UpdateTable.LoadData(sql, tbThongKeMuonTra);
    }//GEN-LAST:event_btThongKeMNVActionPerformed

    private void btThongKeNMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btThongKeNMActionPerformed
        // TODO add your handling code here:
        click=3;
        String sql="select NgayMuon as 'Ngày mượn',count(NgayMuon) as 'Số lượng' from BangMuonTra group by NgayMuon";
        UpdateTable.LoadData(sql, tbThongKeMuonTra);
    }//GEN-LAST:event_btThongKeNMActionPerformed

    private void btThongkeNHTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btThongkeNHTActionPerformed
        // TODO add your handling code here:
        click=4;
        String sql="select NgayHenTra as 'Ngày hẹn trả',count(NgayHenTra) as 'Số lượng' from BangMuonTra group by NgayHenTra";
        UpdateTable.LoadData(sql, tbThongKeMuonTra);
    }//GEN-LAST:event_btThongkeNHTActionPerformed

    private void btThongkeTCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btThongkeTCActionPerformed
        // TODO add your handling code here:
        click=5;
        String sql="select TienCoc as 'Tiền cọc',count(TienCoc) as 'Số lượng' from BangMuonTra group by TienCoc";
        UpdateTable.LoadData(sql, tbThongKeMuonTra);
    }//GEN-LAST:event_btThongkeTCActionPerformed

    private void btInThongKeMTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btInThongKeMTActionPerformed
        // TODO add your handling code here:
         switch (click) {
            case  1:
            try {
                ExportPDF.XuatTK(tbThongKeMuonTra, "bảng mượn trả", "Mã độc giả", "Mã độc giả", "Số lượng","C:\\Users\\Admin\\Documents\\đồ án 1\\Xuất file biểu mẫu/");
            } catch (Exception ex) {
            }   break;
            case  2:
            try {
                ExportPDF.XuatTK(tbThongKeMuonTra, "bảng mượn trả", "Mã nhân viên", "Mã nhân viên", "Số lượng","C:\\Users\\Admin\\Documents\\đồ án 1\\Xuất file biểu mẫu/");
            } catch (Exception ex) {
            }   break;
            case  3:
            try {
                ExportPDF.XuatTK(tbThongKeMuonTra, "bảng mượn trả", "Ngày mượn", "Ngày mượn", "Số lượng","C:\\Users\\Admin\\Documents\\đồ án 1\\Xuất file biểu mẫu/");
            } catch (Exception ex) {
            }   break;
            case 4:
            try {
                ExportPDF.XuatTK(tbThongKeMuonTra, "bảng mượn trả", "Ngày hẹn trả", "Ngày hẹn trả", "Số lượng","C:\\Users\\Admin\\Documents\\đồ án 1\\Xuất file biểu mẫu/");
            } catch (Exception ex) {
            }   break;
            case  5:
            try {
                ExportPDF.XuatTK(tbThongKeMuonTra, "bảng mượn trả", "Tiền cọc", "Tiền cọc", "Số lượng","C:\\Users\\Admin\\Documents\\đồ án 1\\Xuất file biểu mẫu/");
            } catch (Exception ex) {
            }   break;
         }
    }//GEN-LAST:event_btInThongKeMTActionPerformed
 
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainThuThu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainThuThu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainThuThu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainThuThu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainThuThu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btHuyMT;
    private javax.swing.JButton btHuyTT;
    private javax.swing.JButton btInPhieuCTMT;
    private javax.swing.JButton btInTKDG;
    private javax.swing.JButton btInTKMT;
    private javax.swing.JButton btInThongKeMT;
    private javax.swing.JButton btInThongKeSach;
    private javax.swing.JButton btIntimkiemsach;
    private javax.swing.JButton btNewDG;
    private javax.swing.JButton btNewS;
    private javax.swing.JButton btNewTKMT;
    private javax.swing.JButton btNhapFileDG;
    private javax.swing.JButton btOKMT;
    private javax.swing.JButton btOKTT;
    private javax.swing.JButton btReset;
    private javax.swing.JButton btSua;
    private javax.swing.JButton btSuaDG;
    private javax.swing.JButton btSuaMT;
    private javax.swing.JButton btSuaTT;
    private javax.swing.JButton btTK;
    private javax.swing.JButton btTKDG;
    private javax.swing.JButton btTKMT;
    private javax.swing.JButton btThem;
    private javax.swing.JButton btThemDG;
    private javax.swing.JButton btThemFileMT;
    private javax.swing.JButton btThemFileSach;
    private javax.swing.JButton btThemFileTT;
    private javax.swing.JButton btThemMT;
    private javax.swing.JButton btThemTT;
    private javax.swing.JButton btThongKeDC;
    private javax.swing.JButton btThongKeDG;
    private javax.swing.JButton btThongKeMNV;
    private javax.swing.JButton btThongKeNM;
    private javax.swing.JButton btThongKeNS;
    private javax.swing.JButton btThongKeTDG;
    private javax.swing.JButton btThongkeDG;
    private javax.swing.JButton btThongkeGT;
    private javax.swing.JButton btThongkeMDG;
    private javax.swing.JButton btThongkeMS;
    private javax.swing.JButton btThongkeNB;
    private javax.swing.JButton btThongkeNHT;
    private javax.swing.JButton btThongkeNXB;
    private javax.swing.JButton btThongkeTC;
    private javax.swing.JButton btThongkeTG;
    private javax.swing.JButton btThongkeTL;
    private javax.swing.JButton btThongkeTS;
    private javax.swing.JButton btXoa;
    private javax.swing.JButton btXoaDG;
    private javax.swing.JButton btXoaMT;
    private javax.swing.JButton btXoaTT;
    private javax.swing.JButton btXuatFileDG;
    private javax.swing.JButton btXuatFileSach;
    private javax.swing.JComboBox<String> cbMDG;
    private javax.swing.JComboBox<String> cbMMT;
    private javax.swing.JComboBox<String> cbMNV;
    private javax.swing.JComboBox<String> cbMS;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JTabbedPane jTabbedPane4;
    private javax.swing.JTable tbChitiet;
    private javax.swing.JTable tbDG;
    private javax.swing.JTable tbMuonTra;
    private javax.swing.JTable tbS;
    private javax.swing.JTable tbTKDG;
    private javax.swing.JTable tbTKMT;
    private javax.swing.JTable tbTKS;
    private javax.swing.JTable tbTKSach;
    private javax.swing.JTable tbThongKeDG;
    private javax.swing.JTable tbThongKeMuonTra;
    private javax.swing.JTextField txtCMND;
    private javax.swing.JTextField txtDC;
    private javax.swing.JTextField txtDG;
    private javax.swing.JTextField txtEM;
    private javax.swing.JTextField txtGC;
    private javax.swing.JTextField txtGT;
    private javax.swing.JTextField txtMDG;
    private javax.swing.JTextField txtMMT;
    private javax.swing.JTextField txtMS;
    private javax.swing.JTextField txtMaDG;
    private javax.swing.JTextField txtMaMT;
    private javax.swing.JTextField txtMaNV;
    private javax.swing.JTextField txtNB;
    private javax.swing.JTextField txtNHT;
    private javax.swing.JTextField txtNM;
    private javax.swing.JTextField txtNS;
    private javax.swing.JTextField txtNT;
    private javax.swing.JTextField txtNXB;
    private javax.swing.JTextField txtNgayMuon;
    private javax.swing.JTextField txtNgayTra;
    private javax.swing.JTextField txtSDT;
    private javax.swing.JTextField txtSL;
    private javax.swing.JTextField txtTC;
    private javax.swing.JTextField txtTCoc;
    private javax.swing.JTextField txtTDG;
    private javax.swing.JTextField txtTG;
    private javax.swing.JTextField txtTKCMND;
    private javax.swing.JTextField txtTKDC;
    private javax.swing.JTextField txtTKDG;
    private javax.swing.JTextField txtTKEM;
    private javax.swing.JTextField txtTKGT;
    private javax.swing.JTextField txtTKMDG;
    private javax.swing.JTextField txtTKMS;
    private javax.swing.JTextField txtTKNB;
    private javax.swing.JTextField txtTKNS;
    private javax.swing.JTextField txtTKNXB;
    private javax.swing.JTextField txtTKSDT;
    private javax.swing.JTextField txtTKSL;
    private javax.swing.JTextField txtTKTDG;
    private javax.swing.JTextField txtTKTG;
    private javax.swing.JTextField txtTKTL;
    private javax.swing.JTextField txtTKTS;
    private javax.swing.JTextField txtTL;
    private javax.swing.JTextField txtTP;
    private javax.swing.JTextField txtTS;
    // End of variables declaration//GEN-END:variables
}
