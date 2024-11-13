/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.AccountDAO;
import dal.AccountTypeDAO;
import dal.RoleDAO;
import dal.UserlogDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Vector;
import model.Account;
import model.AccountType;
import model.Userlog;
import util.GetTodayDate;
import util.PasswordEncryption;

/**
 *
 * @author 84941
 */
public class LoginController extends HttpServlet {

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
        UserlogDAO userlogDAO = new UserlogDAO();
        HttpSession session = request.getSession();
        String service = request.getParameter("service");
        if (service == null) {
            service = "login";
        }
        if (service.equals("login")) {
            String submit = request.getParameter("loginSubmit");
            //First time transfered to login screen
            if (submit == null) {
                response.sendRedirect("jsp/Login.jsp");
            } else {
                //get entered email, password and remember me from user
                String enteredEmail = request.getParameter("enteredEmail");
                String enteredPassword = request.getParameter("enteredPassword");
                String rememberMe = request.getParameter("rememberMe");
                //Encrypt entered password
                String encryptedPassword = PasswordEncryption.EncryptBySHA256(enteredPassword);
                //Verify entered email and password
                Vector<Account> vector = accountDAO.getAccounts("select * from account where email like '" + enteredEmail + "' and password like '" + encryptedPassword + "'");
                if (vector.isEmpty()) {
                    String errorMessage = "Incorrect username or password";
                    request.setAttribute("errorMessage", errorMessage);
                    request.getRequestDispatcher("jsp/Login.jsp").forward(request, response);
                } else {
                    Account account = vector.get(0);
                    //check if account is deactivated
                    if (account.getStatus() == 0) {
                        String errorMessage = "Your account has been deactivated. Please contact admin/manager for more information";
                        request.setAttribute("errorMessage", errorMessage);
                        request.getRequestDispatcher("jsp/Login.jsp").forward(request, response);
                    }
                    //push account on session
                    session.setAttribute("account", account);
                    //check if account is using default password
                    if (account.getStatus() == 2) {
                        String message = "You must change your password before you can access the system";
                        request.setAttribute("message", message);
                        request.getRequestDispatcher("ChangePasswordController?service=changePassword").forward(request, response);
                    }
                    if (account.getStatus() == 1) {
                        // create 3 cookie email, password, rememberMe
                        Cookie cookieEmail = new Cookie("cookieEmail", enteredEmail);
                        Cookie cookiePassword = new Cookie("cookiePassword", enteredPassword);
                        Cookie cookieRememberMe = new Cookie("cookieRememberMe", rememberMe);
                        // click on remember 
                        if (rememberMe != null) {

                            cookieEmail.setMaxAge(60 * 60 * 24 * 7); // 7 ngay
                            cookiePassword.setMaxAge(60 * 60 * 24 * 7);
                            cookieRememberMe.setMaxAge(60 * 60 * 24 * 7);
                            // khong click 
                        } else {
                            cookieEmail.setMaxAge(0);
                            cookiePassword.setMaxAge(0);
                            cookieRememberMe.setMaxAge(0);
                        }
                        Userlog userlog= new Userlog(0, account.getId(), GetTodayDate.getTodayDate(), false);
                        userlogDAO.insertUserLog(userlog);
                        // redirect user to proper pages
                        if (account.getRoleId() == roleDAO.getRoleByTitle("System Admin").getId()) {
                            response.sendRedirect("AdminAccountDashboardController");
                        }
                        if (account.getRoleId() == roleDAO.getRoleByTitle("Business Owner").getId()) {
                            response.sendRedirect("WarehouseManagerDashboardController");
                        }
                        if (account.getRoleId() == roleDAO.getRoleByTitle("Warehouse Manager").getId()) {
                            response.sendRedirect("ImportProduct");
                        }
                        if (account.getRoleId() == roleDAO.getRoleByTitle("Employee").getId()) {
                            response.sendRedirect("homeController");
                        }

                    }
                }

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
