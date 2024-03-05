package controllers;

import akka.io.Tcp;
import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import play.mvc.*;

import java.sql.*;
import java.time.Duration;
import java.util.Map;

public class HomeController extends Controller {

    public Result index() {
        return ok(views.html.login.render());
    }

    public Result helloUserView(Http.Request request) {
        SQLServerDataSource ds = new SQLServerDataSource();
        ds.setUser("sa");
        ds.setPassword("123");
        ds.setServerName("DESKTOP-FR1QTFI\\SQLEXPRESS");
        ds.setPortNumber(1433);
        ds.setDatabaseName("ManagementStudent");

        // Cấu hình để bỏ qua chứng chỉ SSL
        ds.setTrustServerCertificate(true);

        String username;

        StringBuilder result = new StringBuilder();

        try (Connection conn = ds.getConnection()) {
            // Tạo câu truy vấn SQL để lấy dữ liệu từ bảng Student
            String sqlQuery = "SELECT ID, FullName, Age, Address, Score FROM Student";

            try (Statement statement = conn.createStatement();
                 ResultSet resultSet = statement.executeQuery(sqlQuery)) {

                // Xử lý kết quả
                while (resultSet.next()) {
                    int id = resultSet.getInt("ID");
                    String fullName = resultSet.getString("FullName");
                    int age = resultSet.getInt("Age");
                    String address = resultSet.getString("Address");
                    float score = resultSet.getFloat("Score");

                    // Thực hiện các thao tác với dữ liệu lấy được
                    result.append("ID: ").append(id)
                            .append(", FullName: ").append(fullName)
                            .append(", Age: ").append(age)
                            .append(", Address: ").append(address)
                            .append(", Score: ").append(score)
                            .append("<br>");
                }
            }
        } catch (SQLServerException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        username = result.toString();

        return ok(views.html.hello_user.render(username));
    }

    public Result submitLoginForm(Http.Request request) {
        Map<String, String[]> formParams = request.body().asFormUrlEncoded();

        String username = formParams.get("username")[0];
        String password = formParams.get("password")[0];

        // Set cookie
        Http.Cookie usernameCookie = Http.Cookie.builder("username", username)
                .withMaxAge(Duration.ofDays(1)).build();
        Http.Cookie passwordCookie = Http.Cookie.builder("password", password)
                .withMaxAge(Duration.ofDays(1)).build();

        // Set session
        Http.Session usernameSession = request.session().adding("username", username);

        // Add cookie and session

        return redirect("/helloUser").withSession(usernameSession)
                .withCookies(usernameCookie, passwordCookie);
    }

}