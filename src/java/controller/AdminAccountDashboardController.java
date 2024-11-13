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
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.Date;
import java.util.Vector;
import model.Account;
import model.AccountType;
import model.Role;
import model.Warehouse;
import sendEmail.EmailService;
import sendEmail.IJavaMail;
import util.CreateRandom;
import util.DataConvert;
import util.OTPGenerator;
import util.GetTodayDate;
import util.PasswordEncryption;

/**
 *
 * @author 84941
 */
public class AdminAccountDashboardController extends HttpServlet {

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
        AccountTypeDAO accountTypeDAO = new AccountTypeDAO();
        RoleDAO roleDAO = new RoleDAO();
        WarehouseDAO warehouseDAO = new WarehouseDAO();
        DataConvert dataConvert = new DataConvert();
        String service = request.getParameter("service");
        if (service == null) {
            service = "viewAllAccount";
        }
        if (service.equals("viewAllAccount")) {
            String fillterSubmit = request.getParameter("fillterSubmit");

            Vector<Account> accountVector = null;
            Vector<Role> roleVector = null;
            Vector<AccountType> accountTypeVector = null;
            Vector<Warehouse> warehouseVector = null;
            String sql = "select*from account";
            String checked = "sortById"; //by default
            //Display all fields by default
            request.setAttribute("displayId", "true");
            request.setAttribute("displayEmail", "true");
            request.setAttribute("displayRole", "true");
            request.setAttribute("displayStatus", "true");
            request.setAttribute("displayWarehouseId", "true");
            request.setAttribute("displayAccountType", "true");
            if (fillterSubmit != null) {
                //get all filter
                String searchById = request.getParameter("searchById");
                String searchByEmail = request.getParameter("searchByEmail");
                String filterByRole = request.getParameter("filterByRole");
                String filterByStatus = request.getParameter("filterByStatus");
                String filterByWarehouseId = request.getParameter("filterByWarehouseId");
                String filterByAccountType = request.getParameter("filterByAccountType");
                //get sort
                String sortBy = request.getParameter("sortBy");
                //reset all display fields
                request.setAttribute("displayId", request.getParameter("displayId"));
                request.setAttribute("displayEmail", request.getParameter("displayEmail"));
                request.setAttribute("displayRole", request.getParameter("displayRole"));
                request.setAttribute("displayStatus", request.getParameter("displayStatus"));
                request.setAttribute("displayWarehouseId", request.getParameter("displayWarehouseId"));
                request.setAttribute("displayAccountType", request.getParameter("displayAccountType"));
                sql += " where id like '%" + searchById + "%' and email like '%" + searchByEmail + "%'";
                if (!filterByRole.equals("all")) {
                    sql += " and role_id = " + filterByRole;
                }
                if (!filterByStatus.equals("all")) {
                    sql += " and status = " + filterByStatus;
                }
                if (!filterByWarehouseId.equals("all")) {
                    sql += " and warehouse_id = " + filterByWarehouseId;
                }
                if (!filterByAccountType.equals("all")) {
                    sql += " and account_type_id = " + filterByAccountType;
                }
                if (sortBy != null) {
                    switch (sortBy) {
                        case "sortById":
                            sql += " order by id asc ";
                            break;
                        case "sortByEmail":
                            sql += " order by email asc ";
                            checked = "sortByEmail";
                            break;
                        case "sortByRole":
                            sql += " order by role_id asc ";
                            checked = "sortByRole";
                            break;
                        case "sortByStatus":
                            sql += " order by status asc ";
                            checked = "sortByStatus";
                            break;
                        case "sortByWarehouseId":
                            sql += " order by warehouse_id asc ";
                            checked = "sortByWarehouseId";
                            break;
                        case "sortByAccountType":
                            sql += " order by account_type_id asc ";
                            checked = "sortByAccountType";
                            break;

                    }
                }

            }
            //paging
            int nrpp = 10;
            accountVector = accountDAO.getAccounts(sql);
            int totalPage = (accountVector.size() + nrpp - 1) / nrpp;
            String indexRaw = request.getParameter("index");
            int index = 1;
            if (indexRaw != null) {
                index = Integer.parseInt(indexRaw);
            }
            sql += " limit " + (index - 1) * nrpp + "," + nrpp;
            accountVector = accountDAO.getAccounts(sql);
            roleVector = roleDAO.getRoles("select*from role");
            accountTypeVector = accountTypeDAO.getAccountTypes("select*from accounttype");
            warehouseVector = warehouseDAO.getAllWarehouses();
            //set data for view 
            request.setAttribute("accountVector", accountVector);
            request.setAttribute("roleVector", roleVector);
            request.setAttribute("accountTypeVector", accountTypeVector);
            request.setAttribute("warehouseVector", warehouseVector);
            request.setAttribute("checked", checked);
            request.setAttribute("totalPage", totalPage);
            // select view
            RequestDispatcher dispath = request.getRequestDispatcher("jsp/ViewAccountList.jsp");
            //run
            dispath.forward(request, response);

        }
        if (service.equals("viewAccountDetails")) {
            String idRaw = request.getParameter("id");
            //User user = userDAO.getUserById(Integer.parseInt(idRaw));
            Account account = accountDAO.getAccountById(Integer.parseInt(idRaw));
            Vector<Role> roleVector = roleDAO.getAll();
            Vector<AccountType> accountTypeVector = accountTypeDAO.getAccountTypes("select * from accounttype");
            Vector<Warehouse> warehouseVector = warehouseDAO.getAllWarehouses();

            //set data for views
            request.setAttribute("account", account);
            request.setAttribute("roleVector", roleVector);
            request.setAttribute("accountTypeVector", accountTypeVector);
            request.setAttribute("warehouseVector", warehouseVector);
            // select view
            RequestDispatcher dispath = request.getRequestDispatcher("jsp/ViewAccountDetail.jsp");
            //run
            dispath.forward(request, response);
        }
        if (service.equals("updateAccount")) {
            int accountId = Integer.parseInt(request.getParameter("accountId"));
            String roleIdRaw = request.getParameter("roleId");
            String statusRaw = request.getParameter("status");
            if (roleIdRaw != null) {
                int roleId = Integer.parseInt(roleIdRaw);
                Account account = accountDAO.getAccountById(accountId);
                account.setRoleId(roleId);
                accountDAO.updateAccount(account);
                String message = "Update account role successfuly";
                //set data for views
                request.setAttribute("message", message);
                // select view
                RequestDispatcher dispath = request.getRequestDispatcher("AdminAccountDashboardController?service=viewAllAccount");
                //run
                dispath.forward(request, response);

            }
            if (statusRaw != null) {
                int status = Integer.parseInt(statusRaw);
                Account account = accountDAO.getAccountById(accountId);
                account.setStatus(status);
                accountDAO.updateAccount(account);
                String message = "Update account status successfuly";
                //set data for views
                request.setAttribute("message", message);
                // select view
                RequestDispatcher dispath = request.getRequestDispatcher("AdminAccountDashboardController?service=viewAllAccount");
                //run
                dispath.forward(request, response);
            }

        }
        if (service.equals("addNewAccount")) {
            String submit = request.getParameter("addNewAccountSubmit");
            if (submit == null) {
                Vector<Warehouse> warehouseVector = warehouseDAO.getAllWarehouses();
                //set data for views
                request.setAttribute("warehouseVector", warehouseVector);
                // select view
                RequestDispatcher dispath = request.getRequestDispatcher("jsp/AddNewAccount.jsp");
                //run
                dispath.forward(request, response);
            }
            if (submit != null) {
                String email = request.getParameter("email");
                String accountType = request.getParameter("accountType");
                String accountRole = request.getParameter("accountRole");
                String adminNote = request.getParameter("adminNote");
                int warehouseId = Integer.parseInt(request.getParameter("warehouseId"));
                if (accountDAO.getAccountByEmail(email) != null) {
                    request.setAttribute("message", "Email already exist in the system");
                    RequestDispatcher dispath = request.getRequestDispatcher("AdminAccountDashboardController?service=viewAllAccount");
                    //run
                    dispath.forward(request, response);
                }
                int roleId = 3;
                if (accountRole.equals("employee")) {
                    roleId = 4;
                }
                String password = CreateRandom.generate6DigitCode();
                Date createdDate = GetTodayDate.getTodayDate();
                String message="";
                if (accountType.equals("system")) {
                    Account account = new Account(0, email, roleId, PasswordEncryption.EncryptBySHA256(password), 2, createdDate, null, warehouseId, "", "", "", "", 1, true);
                    accountDAO.insertAccount(account);
                    String subject = "New user successfully created. Please login with your default password";
                    String emailContent = "Your default password is " + password + "\n" + adminNote;
                    IJavaMail emailService = new EmailService();
                    emailService.send(email, subject, emailContent);
                    message = "Add new user successfuly";
                }
                if (accountType.equals("google")) {
                    Account account = new Account(0, email, roleId, PasswordEncryption.EncryptBySHA256(password), 2, createdDate, null, warehouseId, "", "", "", "", 2, true);
                    accountDAO.insertAccount(account);
                    String subject = "New user successfully created";
                    String emailContent = adminNote;
                    IJavaMail emailService = new EmailService();
                    emailService.send(email, subject, emailContent);
                     message = "Add new user successfuly";
                }
                //set data for views
                request.setAttribute("message", message);
                // select view
                RequestDispatcher dispath = request.getRequestDispatcher("AdminAccountDashboardController?service=viewAllAccount");
                //run
                dispath.forward(request, response);
            }
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
