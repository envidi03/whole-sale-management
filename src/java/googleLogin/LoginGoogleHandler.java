
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dal.AccountDAO;
import dal.RoleDAO;
import dal.UserlogDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Account;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Form;
import java.io.IOException;
import model.Userlog;
import util.GetTodayDate;

@WebServlet(urlPatterns = {"/LoginGoogleHandler"})
public class LoginGoogleHandler extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RoleDAO roleDAO = new RoleDAO();
        UserlogDAO userlogDAO = new UserlogDAO();
        String code = request.getParameter("code");
        if (code == null || code.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Google authorization code.");
            return;
        }

        try {
            String accessToken = getToken(code);
            UserGoogleDto googleUser = getUserInfo(accessToken);

            if (googleUser != null) {
                AccountDAO accountDAO = new AccountDAO();
                Account account = accountDAO.getAccountByEmail(googleUser.getEmail());

                if (account == null) {
                    // Create a new account if the user does not exist
                    account = new Account();
                    account.setEmail(googleUser.getEmail());
                    account.setFirstName(googleUser.getGiven_name());
                    account.setLastName(googleUser.getFamily_name());
                    account.setImage(googleUser.getPicture());
                    account.setStatus(1);  // 1-activated by default
                    account.setRoleId(2);  // Assume 2 is default role for new users
                    account.setAccountTypeId(1);  // 1 - Google Account type

                    // Insert new account into the database
                    accountDAO.addAccount(account);
                }

                // Store the account in session
                HttpSession session = request.getSession();
                session.setAttribute("account", account);
                Userlog userlog = new Userlog(0, account.getId(), GetTodayDate.getTodayDate(), true);
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
            } else {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to retrieve user information from Google.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error processing Google login.");
        }
    }

    public static String getToken(String code) throws ClientProtocolException, IOException {
        String response = Request.Post(Constants.GOOGLE_LINK_GET_TOKEN)
                .bodyForm(Form.form().add("client_id", Constants.GOOGLE_CLIENT_ID)
                        .add("client_secret", Constants.GOOGLE_CLIENT_SECRET)
                        .add("redirect_uri", Constants.GOOGLE_REDIRECT_URI)
                        .add("code", code)
                        .add("grant_type", Constants.GOOGLE_GRANT_TYPE)
                        .build())
                .execute().returnContent().asString();

        JsonObject jobj = new Gson().fromJson(response, JsonObject.class);

        if (jobj.has("access_token")) {
            return jobj.get("access_token").getAsString();
        } else {
            throw new IOException("Error retrieving access token: " + jobj.get("error").getAsString());
        }
    }

    public static UserGoogleDto getUserInfo(final String accessToken) throws ClientProtocolException, IOException {
        String link = Constants.GOOGLE_LINK_GET_USER_INFO + accessToken;
        String response = Request.Get(link).execute().returnContent().asString();

        return new Gson().fromJson(response, UserGoogleDto.class);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Google Login Handler Servlet";
    }
}
