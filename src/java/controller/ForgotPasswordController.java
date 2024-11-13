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
import sendEmail.EmailService;
import sendEmail.IJavaMail;
import util.OTPGenerator;
import util.PasswordEncryption;
import validation.PasswordValidator;

/**
 *
 * @author 84941
 */
public class ForgotPasswordController extends HttpServlet {

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
        HttpSession session = request.getSession();
        PasswordValidator passwordValidator = new PasswordValidator();
        String service = request.getParameter("service");
        if (service == null) {
            service = "sendEmail";
        }
        if (service.equals("sendEmail")) {
            String submit = request.getParameter("forgotPasswordSendEmailSubmit");
            if (submit == null) {
                response.sendRedirect("jsp/ForgotPasswordSendEmail.jsp");
            } else {
                String email = request.getParameter("email");
                String sql = "select*from account where account.email like '" + email + "'";
                Vector<Account> vector = accountDAO.getAccounts(sql);
                if (vector.isEmpty()) {
                    request.setAttribute("errorMessage", "Email does not exist");
                    request.getRequestDispatcher("jsp/ForgotPasswordSendEmail.jsp").forward(request, response);
                } else {
                    String OTP = OTPGenerator.generateOTP();
                    String toEmail = email;
                    String subject = "OTP Verification";
                    String email_content = "Your OTP is " + OTP;
                    IJavaMail emailService = new EmailService();
                    emailService.send(toEmail, subject, email_content);
                    session.setAttribute("OTP", OTP);  // Save OTP into session
                    session.setAttribute("email", email);  // Save OTP into session
                    request.getRequestDispatcher("ForgotPasswordController?service=verifyOTP").forward(request, response);
                }
            }

        }
        if (service.equals("verifyOTP")) {
            //String OTP = request.getParameter("OTP");
            String OTP = (String)session.getAttribute("OTP");
            String email = (String)session.getAttribute("email");
            String submit = request.getParameter("ForgotPasswordEnterOTPSubmit");
            if (submit == null) {
                response.sendRedirect("jsp/ForgotPasswordEnterOTP.jsp");
            } else {
                String enteredOTP = request.getParameter("enteredOTP");
                if (!enteredOTP.equalsIgnoreCase(OTP)) {
                    request.setAttribute("errorMessage", "Wrong OTP");
                    request.getRequestDispatcher("jsp/ForgotPasswordEnterOTP.jsp").forward(request, response);
                } else {
                    request.setAttribute("email", email);
                    request.getRequestDispatcher("ForgotPasswordController?service=resetPassword").forward(request, response);
                }
            }
        }
        if (service.equals("resetPassword")) {
            String submit = request.getParameter("ForgotPasswordEnterNewPasswordSubmit");
            String email = (String)session.getAttribute("email");
            if (submit == null) {
                response.sendRedirect("jsp/ForgotPasswordEnterNewPassword.jsp");
            } else {
                String newPassword = request.getParameter("password");
                String confPassword = request.getParameter("confPassword");
                if(!passwordValidator.validatePassword(newPassword)){
                    request.setAttribute("errorMessage", "Your new password is not validate. A password must be at least 8 characters long, contain at least one uppercase letter, one lowercase letter, one digit, and one special character (e.g., !, @, #, $, %, ^, &, *).");
                    request.getRequestDispatcher("jsp/ForgotPasswordEnterNewPassword.jsp").forward(request, response);
                }
                if (!newPassword.equals(confPassword)) {
                    request.setAttribute("errorMessage", "Confirm password must be same new password");
                    request.getRequestDispatcher("jsp/ForgotPasswordEnterNewPassword.jsp").forward(request, response);

                }
                if (newPassword.equals(confPassword)) {
                    Vector<Account> vector = accountDAO.getAccounts("select*from account where email like '" + email + "'");
                    vector.get(0).setPassword(PasswordEncryption.EncryptBySHA256(newPassword));
                    accountDAO.updateAccount(vector.get(0));
                    request.setAttribute("message", "Reset password successfully");
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
