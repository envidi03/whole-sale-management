/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.AccountDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Vector;
import model.Account;
import util.PasswordEncryption;
import validation.PasswordValidator;

/**
 *
 * @author 84941
 */
public class ChangePasswordController extends HttpServlet {

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
        PasswordValidator passwordValidator = new PasswordValidator();
        HttpSession session = request.getSession();
        String service = request.getParameter("service");
        if (service == null) {
            service = "changePassword";
        }
        if (service.equals("changePassword")) {
            String submit = request.getParameter("changePasswordSubmit");
            if (submit == null) {
                response.sendRedirect("jsp/ChangePassword.jsp");
            } else {
                String oldPassword = request.getParameter("oldPassword");
                String newPassword = request.getParameter("newPassword");
                String confPassword = request.getParameter("confPassword");
                Account account = (Account) session.getAttribute("account");
                if(!passwordValidator.validatePassword(newPassword)){
                    request.setAttribute("errorMessage", "Your new password is not validate. A password must be at least 8 characters long, contain at least one uppercase letter, one lowercase letter, one digit, and one special character (e.g., !, @, #, $, %, ^, &, *).");
                    request.getRequestDispatcher("jsp/ChangePassword.jsp").forward(request, response);
                }
                if (!PasswordEncryption.EncryptBySHA256(oldPassword).equals(account.getPassword())) {
                    request.setAttribute("errorMessage", "Your old password is wrong! Please enter again");
                    request.getRequestDispatcher("jsp/ChangePassword.jsp").forward(request, response);
                } else if (!newPassword.equals(confPassword)) {
                    request.setAttribute("errorMessage", "Your confirmation password does not match with your new password");
                    request.getRequestDispatcher("jsp/ChangePassword.jsp").forward(request, response);
                } else {
                    Vector<Account> vector = accountDAO.getAccounts("select*from account where id=" + account.getId());
                    Account acc = vector.get(0);
                    acc.setPassword(PasswordEncryption.EncryptBySHA256(newPassword));
                    acc.setStatus(1);
                    accountDAO.updateAccount(acc);
                    session.setAttribute("account", vector.get(0));
                    String message = "Change password successfully";
                    request.setAttribute("message", message);
                    request.getRequestDispatcher("jsp/Login.jsp").forward(request, response);
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
