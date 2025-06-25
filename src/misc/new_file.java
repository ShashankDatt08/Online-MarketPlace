package misc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class NewFile {

    private Connection connection;

    public NewFile(Connection connection) {
        this.connection = connection;
    }

    public boolean changePassword(String oldPassword, String newPassword) {
        String sql = "UPDATE users SET password = ? WHERE password = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, newPassword);
            preparedStatement.setString(2, oldPassword);

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}