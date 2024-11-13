/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.AccountDAO;
import dal.AccountTypeDAO;
import dal.RoleDAO;
import dal.WarehouseDAO;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.nio.file.Paths;
import java.util.Date;
import model.Account;
import util.DataConvert;

/**
 *
 * @author 84941
 */
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2 MB
        maxFileSize = 1024 * 1024 * 10, // 10 MB
        maxRequestSize = 1024 * 1024 * 50 // 50 MB
)
public class AccountProfileController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        AccountDAO accountDAO = new AccountDAO();
        RoleDAO roleDAO = new RoleDAO();
        AccountTypeDAO accountTypeDAO = new AccountTypeDAO();
        WarehouseDAO warehouseDAO = new WarehouseDAO();
        DataConvert dataConvert = new DataConvert();
        HttpSession session = request.getSession();
        String service = request.getParameter("service");
        if (service == null) {
            service = "viewAccountProfile";
        }
        if (service.equals("viewAccountProfile")) {
            //int accountId=Integer.parseInt(request.getParameter("accountId"));
            Account account = (Account)session.getAttribute("account");
            //Account account = accountDAO.getAccountById(accountId);
            //set data for views
            request.setAttribute("account", account);
            request.setAttribute("roleVector", roleDAO.getRoles("select * from role"));
            request.setAttribute("accountTypeVector", accountTypeDAO.getAccountTypes("select * from accounttype"));
            request.setAttribute("warehouseVector", warehouseDAO.getAllWarehouses());
            // select view
            RequestDispatcher dispath = request.getRequestDispatcher("jsp/ViewAccountProfile.jsp");
            //run
            dispath.forward(request, response);

        }
        if (service.equals("updateAccount")) {
            int accountId = Integer.parseInt(request.getParameter("accountId"));
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String phone = request.getParameter("phone");
            String dobRaw = request.getParameter("dob");
            Date dob = dataConvert.convertStringToDate(dobRaw);
            Account account = accountDAO.getAccountById(accountId);
            account.setFirstName(firstName);
            account.setLastName(lastName);
            account.setPhoneNumber(phone);
            account.setDob(dob);
            int result = accountDAO.updateAccount(account);

            if (result > 0) {
                String message = "Update profile sucessfully";
                request.setAttribute("message", message);
            }
            if (result < 0) {
                String errorMessage = "Update profile failed";
                request.setAttribute("errorMessage", errorMessage);
            }
            // select view
            RequestDispatcher dispath = request.getRequestDispatcher("AccountProfileController?service=viewAccountProfile");
            //run
            dispath.forward(request, response);

        }
        if (service.equals("updateAvatar")) {
            String realPath = request.getServletContext().getRealPath("/img");
            String avatarPath = null; // Khai báo biến để lưu đường dẫn hình ảnh
            for (Part part : request.getParts()) {
                if (part != null && part.getSubmittedFileName() != null) {
                    String filename = Paths.get(part.getSubmittedFileName()).getFileName().toString();
                    part.write(Paths.get(realPath, filename).toString()); // Lưu file

                    // Đặt đường dẫn hình ảnh để lưu vào cơ sở dữ liệu
                    avatarPath = "img/" + filename;
                    break; // Chỉ cần lấy một ảnh, thoát khỏi vòng lặp
                }
            }
            int accountId = Integer.parseInt(request.getParameter("accountId"));
            Account account = accountDAO.getAccountById(accountId);
            account.setImage(avatarPath);
            int result = accountDAO.updateAccount(account);

            if (result > 0) {
                String message = "Update avatar sucessfully";
                request.setAttribute("message", message);
            }
            if (result < 0) {
                String errorMessage = "Update avatar failed";
                request.setAttribute("errorMessage", errorMessage);
            }
            // select view
            RequestDispatcher dispath = request.getRequestDispatcher("AccountProfileController?service=viewAccountProfile");
            //run
            dispath.forward(request, response);
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
