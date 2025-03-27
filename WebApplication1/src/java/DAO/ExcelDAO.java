///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//package DAO;
//
//import DBContext.DBContext;
//import Models.Order;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.sql.SQLException;
//import java.util.List;
//import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.ss.util.CellRangeAddressList;
//import org.apache.poi.xssf.usermodel.XSSFDataValidation;
//import org.apache.poi.xssf.usermodel.XSSFDataValidationConstraint;
//import org.apache.poi.xssf.usermodel.XSSFDataValidationHelper;
//import org.apache.poi.xssf.usermodel.XSSFSheet;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//
///**
// *
// * @author Acer
// */
//public class ExcelDAO extends DBContext {
//
//    public static void exportOrderToExcel(String filePath, int startColumn) throws FileNotFoundException, SQLException {
//
//        XSSFWorkbook workbook = new XSSFWorkbook();
//
//        FileOutputStream fileOut = new FileOutputStream(filePath);
//
//        List<Order> orderList = OrderDAO.getUnrefundedOrderList();
//        try {
//            XSSFSheet sheet = workbook.createSheet("Orders");
//            Row headerRow = sheet.createRow(0);
//            String[] columns = {"order_id", "payment_method", "payment_status"};
//
//            // Tạo tiêu đề
//            for (int i = 0; i < columns.length; i++) {
//                Cell cell = headerRow.createCell(i);
//                cell.setCellValue(columns[i]);
//            }
//
//            int rowNum = 1;
//
//            for (Order od : orderList) {
//                Row row = sheet.createRow(rowNum++);
//
//                row.createCell(startColumn + 1).setCellValue(od.getOrderId());
//
//                // Xử lý Payment Method (Cột thứ 2)
//                String paymentMethodText = (od.getPaymentmethod() == 1) ? "Online Banking" : "COD";
//                row.createCell(startColumn + 2).setCellValue(paymentMethodText);
//
//                // Xử lý Payment Status (Cột thứ 3) với dropdown
//                String paymentStatusText = (od.getPaymentStatus() == 2) ? "Unpaid" : "Refunded";
//                Cell paymentStatusCell = row.createCell(startColumn + 3);
//                paymentStatusCell.setCellValue(paymentStatusText);
//            }
//
//// Thêm dropdown cho cột "Payment Status" (Cột thứ 3)
//            createDropdown(sheet, startColumn + 3, 1, rowNum - 1, new String[]{"Unpaid", "Refunded"});
//
//// Thêm dropdown cho cột "Payment Method" (Cột thứ 2)
//            createDropdown(sheet, startColumn + 2, 1, rowNum - 1, new String[]{"Online Banking", "COD"});
//
//            workbook.write(fileOut);
//            System.out.println("Xuất dữ liệu thành công vào " + filePath);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private static void createDropdown(XSSFSheet sheet, int colIndex, int firstRow, int lastRow, String[] options) {
//        XSSFDataValidationHelper dvHelper = new XSSFDataValidationHelper(sheet);
//        XSSFDataValidationConstraint dvConstraint = (XSSFDataValidationConstraint) dvHelper.createExplicitListConstraint(options);
//
//        CellRangeAddressList addressList = new CellRangeAddressList(firstRow, lastRow, colIndex, colIndex);
//        XSSFDataValidation validation = (XSSFDataValidation) dvHelper.createValidation(dvConstraint, addressList);
//
//        validation.setShowErrorBox(true);
//        sheet.addValidationData(validation);
//    }
//
//    public static void main(String[] args) throws FileNotFoundException, SQLException {
//        exportOrderToExcel("M:\\Test.xlsx", 1);
//    }
//
//}
