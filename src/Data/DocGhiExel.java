
package Data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

import javax.swing.JOptionPane;
import javax.swing.table.TableModel;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFChildAnchor;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import java.awt.font.TextAttribute;
public class DocGhiExel {
    
     private HSSFRow row_name;
    private HSSFRow row_value;

    /**
     * @param model - Table cần in
     * @param file - đường dẫn
     * @return
     */
    public boolean getExcel(String title, TableModel model, File file) {

        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet();
        
        File filexls = new File(file.getPath() + ".xls");
		// 0. Tạo font
        //0.1 Tiêu đề
        Font font1 = wb.createFont();
        font1.setFontName("Cambria");
        font1.setBold(true);
        //font1.setBoldweight(Font.ANSI_CHARSET);
        font1.setFontHeight(Font.ANSI_CHARSET);
        font1.setUnderline((byte) 1);
        //0.2 Tên các trường
        Font font2 = wb.createFont();
        font2.setFontName("Cambria");
        font2.setBold(true);
        //font2.setBoldweight((short) 2);
        //1.2 Nội dung
        Font font3 = wb.createFont();
        font3.setFontName("Cambria");

		//2. Tạo CellStyle
        //2.1 Tiêu đề
        CellStyle style1 = wb.createCellStyle();
        style1.setFont(font1);
        //2.2 Tên trường
        CellStyle style2 = wb.createCellStyle();
        style2.setFont(font2);
        //2.3 Nội dung
        CellStyle style3 = wb.createCellStyle();
        style3.setFont(font3);
        //3. Tiêu đề
        HSSFCell cell0 = sheet.createRow(0).createCell(0);
        cell0.setCellValue(title);
        cell0.setCellStyle(style1);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, model.getColumnCount() - 1));
        //4. Tên các trường 
        HSSFRow row_name = sheet.createRow(2);
        for (int i = 0; i < model.getColumnCount(); i++) {
            HSSFCell cell = row_name.createCell(i);
            cell.setCellStyle(style2);
            cell.setCellValue(model.getColumnName(i));
        }

        //5. Giá trị
        for (int i = 0; i < model.getRowCount(); i++) {
            HSSFRow row_value = sheet.createRow(i + 3);
            for (int j = 0; j < model.getColumnCount(); j++) {
                HSSFCell cell = row_value.createCell(j);
                cell.setCellStyle(style3);
                cell.setCellValue(model.getValueAt(i, j).toString());
            }
        }

        // 6. xuất file
        try {
            // Bắt đầu ghi
            FileOutputStream output = new FileOutputStream(filexls);
            wb.write(output);
            output.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return true;
    }
}
