/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.UserlogDAO;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Vector;
import model.Userlog;

/**
 *
 * @author 84941
 */
public class AdminUserlogDashboardController extends HttpServlet {

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
        UserlogDAO userlogDAO = new UserlogDAO();
        String service = request.getParameter("service");
        if (service == null) {
            service = "viewAllUserlog";
        }
        if (service.equals("viewAllUserlog")) {
            String fillterSubmit = request.getParameter("filterSubmit");
            Vector<Userlog> userlogVector = null;
            String sql = "select * from userlog";
            String checked = "sortById"; //by default
            //Display all fields by default
            request.setAttribute("displayId", "true");
            request.setAttribute("displayAccountId", "true");
            request.setAttribute("displayDate", "true");
            request.setAttribute("displayUserLogType", "true");
            if (fillterSubmit != null) {
                //get all filter
                String searchById = request.getParameter("searchById");
                String searchByAccountId = request.getParameter("searchByAccountId");
                String searchByDate = request.getParameter("searchByDate");
                String dateFrom = request.getParameter("dateFrom");
                String dateTo = request.getParameter("dateTo");
                String filterByUserLogType = request.getParameter("filterByUserLogType");
                //get sort
                String sortBy = request.getParameter("sortBy");
                //reset all display fields
                request.setAttribute("displayId", request.getParameter("displayId"));
                request.setAttribute("displayAccountId", request.getParameter("displayAccountId"));
                request.setAttribute("displayDate", request.getParameter("displayDate"));
                request.setAttribute("displayUserLogType", request.getParameter("displayUserLogType"));
                sql += " where id like'%" + searchById + "%' and account_id like '%" + searchByAccountId + "%' and date like '%" + searchByDate + "%' ";
                if (!filterByUserLogType.equals("all")) {
                    sql += " and user_log_type = " + filterByUserLogType;
                }
                if (!dateFrom.isEmpty() && !dateTo.isEmpty() && searchByDate.isEmpty()) {
                    sql += "and date between '" + dateFrom + "' and '" + dateTo + "'";
                }
                if (sortBy != null) {
                    switch (sortBy) {
                        case "sortById":
                            sql += " order by id asc ";
                            break;
                        case "sortByAccountId":
                            sql += " order by account_id asc ";
                            checked = "sortByAccountId";
                            break;
                        case "sortByDate":
                            sql += " order by date asc ";
                            checked = "sortByDate";
                            break;

                    }
                }
            }
            //paging
            int nrpp = 10;
            String nrppRaw = request.getParameter("nrpp");
            if(nrppRaw!=null){
                 nrpp = Integer.parseInt(nrppRaw);
            }
            userlogVector = userlogDAO.getUserLogs(sql);
            int totalPage = (userlogVector.size() + nrpp - 1) / nrpp;
            String indexRaw = request.getParameter("index");
            int index = 1;
            if (indexRaw != null) {
                index = Integer.parseInt(indexRaw);
            }
            sql += " limit " + (index - 1) * nrpp + "," + nrpp;
            userlogVector = userlogDAO.getUserLogs(sql);
            
            //set data for view 
            request.setAttribute("userlogVector", userlogVector);
            request.setAttribute("checked", checked);
            request.setAttribute("totalPage", totalPage);
            request.setAttribute("nrpp", nrpp);

            // select view
            RequestDispatcher dispath = request.getRequestDispatcher("jsp/ViewUserlogList.jsp");
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
