package model;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by ribra on 11/8/2015.
 */
public class Excel {
    private File path;
    private List<Person> list;
    private final static String [] values = {"Creation Date", "Full name", "Date of Birth", "City", "Phone Number",
                                            "Email Address", "Which University/College do you attend?",
                                            "Where did you hear about AIESEC (Global Leader)?",
                                            "Why do you want to be a member?"};
    private static Logger logger = Logger.getLogger(Excel.class.getName());

    public Excel(File path) {
        this.path = path;
        list = new LinkedList<Person>();
    }

    public List<Person> build() {
        try {
            FileInputStream in = new FileInputStream(path);
            XSSFWorkbook workbook = new XSSFWorkbook(in);
            XSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            Row row = null;

            // to remove first info row
            rowIterator.next();

            while (rowIterator.hasNext()){
                row = (XSSFRow) rowIterator.next();

                String creationDate = row.getCell(0) == null? "" : row.getCell(0).toString();
                String name         = row.getCell(1) == null? "" : row.getCell(1).toString();
                String birthDate    = row.getCell(2) == null? "" : row.getCell(2).toString();
                String city         = row.getCell(3) == null? "" : row.getCell(3).toString();
                String phoneNumber  = row.getCell(4) == null? "" : row.getCell(4).toString();
                String email        = row.getCell(5) == null? "" : row.getCell(5).toString();
                String university   = row.getCell(6) == null? "" : row.getCell(6).toString();
                String place        = row.getCell(7) == null? "" : row.getCell(7).toString();
                String reason       = row.getCell(8) == null? "" : row.getCell(8).toString();

                Person person = new Person(creationDate, name, birthDate, city, phoneNumber, email, university, place, reason);
                list.add(person);
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void export(List<Person> list, String filePath) {
        filePath += "\\applicants.xlsx";
        File file = new File(filePath);
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("New Applicants");
        XSSFRow row = null;

        row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);

        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 16);
        font.setFontName("Arial Narrow");
        style.setFont(font);

        for (int i = 0; i < values.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(values[i]);
            cell.setCellStyle(style);
        }

        for (int i = 0; i < list.size(); i++) {
            row = sheet.createRow(i + 1);

            Cell [] cells = new Cell[values.length];
            String [] strings = list.get(i).getAllValues();
            for (int j = 0; j < values.length; j++) {
                cells[j] = row.createCell(j);
                cells[j].setCellValue(strings[j]);
            }
        }

        try {
            FileOutputStream out = new FileOutputStream(file);
            workbook.write(out);
            out.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("The file is exported to " + filePath);
    }


    public List<Person> getList() {
        return list;
    }

    public void setList(List<Person> list) {
        this.list = list;
    }

    public File getPath() {
        return path;
    }

    public void setPath(File path) {
        this.path = path;
    }
}
