import java.sql.*;

public class LoginHandler {
    private Connection conn = null;
    private Statement stmt = null;
    private String password = null;

    public boolean login(String username, String password) {

        Driverloader();
        try {
            String sql = "SELECT Password FROM user WHERE Username = '" + username + "'";
            ResultSet resultSet = stmt.executeQuery(sql);

            if (resultSet.next()) {
                this.password = resultSet.getString("Password");
                if (password.equals(this.password)) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }

            // Rest of your code

        } catch (SQLException e) {
            String errorMessage = e.getMessage();
            System.out.println(errorMessage);
            return false;
            // Handle the SQLException appropriately
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    String errorMessage = e.getMessage();
                    System.out.println(errorMessage);
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    String errorMessage = e.getMessage();
                    System.out.println(errorMessage);
                }
            }
        }
    }

    private void Driverloader() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("driver loaded");
            conn = DriverManager.getConnection("jdbc:mysql://localhost/todo_list", "root", "");
            stmt = conn.createStatement();
            // Rest of your code
        } catch (ClassNotFoundException e) {
            String errorMessage = e.getMessage();
            System.out.println(errorMessage);
        } catch (SQLException ex){
            System.out.println(ex.getMessage());
        }

    }

    public boolean register(String first, String last, String username, String password) {
        Driverloader();
        try {
            String sql = "INSERT INTO user (FirstName, LastName, Username, Password) VALUES ('" + first + "', '" + last + "', '" + username + "', '" + password + "')";
            int rowsAffected = stmt.executeUpdate(sql);
            if (rowsAffected > 0) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {
            String errorMessage = e.getMessage();
            System.out.println(errorMessage);
            return false;
        } finally {
            // Close the statement and connection
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    String errorMessage = e.getMessage();
                    System.out.println(errorMessage);
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    String errorMessage = e.getMessage();
                    System.out.println(errorMessage);
                }
            }
        }
    }

    public String list(String username){
        Driverloader();

        try {
            String sql = "SELECT ID FROM user WHERE Username = '" + username + "'";
            ResultSet resultSet  = stmt.executeQuery(sql);
            String userID = null;
            if (resultSet.next())
                userID = resultSet.getString("ID");
            sql = "SELECT * FROM todo WHERE UserID = '" + userID + "'";
            resultSet  = stmt.executeQuery(sql);
            StringBuilder data = new StringBuilder();
            while (resultSet.next()) {
                data.append(resultSet.getString("Title"))
                        .append(",")
                        .append(resultSet.getString("Description"))
                        .append(",")
                        .append(resultSet.getString("DoneUndone"))
                        .append("\n");
            }
            return data.toString();

        } catch (SQLException e) {
            String errorMessage = e.getMessage();
            System.out.println(errorMessage);
            return "Error";
            // Handle the SQLException appropriately
        } finally {
            // Close the statement and connection
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    String errorMessage = e.getMessage();
                    System.out.println(errorMessage);
                    // Handle the SQLException appropriately
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    String errorMessage = e.getMessage();
                    System.out.println(errorMessage);
                }
            }
        }
    }

    public String CatList(String username, int Cat){
        Driverloader();

        try {
            String sql = "SELECT ID FROM user WHERE Username = '" + username + "'";
            ResultSet resultSet  = stmt.executeQuery(sql);
            String userID = null;
            if (resultSet.next())
                userID = resultSet.getString("ID");
            sql = "SELECT * FROM todo WHERE UserID = '" + userID + "' AND category_id = '" + Cat + "'";
            resultSet  = stmt.executeQuery(sql);
            StringBuilder data = new StringBuilder();
            while (resultSet.next()) {
                data.append(resultSet.getString("Title"))
                        .append(",")
                        .append(resultSet.getString("Description"))
                        .append(",")
                        .append(resultSet.getString("DoneUndone"))
                        .append("\n");
            }
            return data.toString();

        } catch (SQLException e) {
            String errorMessage = e.getMessage();
            System.out.println(errorMessage);
            return "Error";
            // Handle the SQLException appropriately
        } finally {
            // Close the statement and connection
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    String errorMessage = e.getMessage();
                    System.out.println(errorMessage);
                    // Handle the SQLException appropriately
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    String errorMessage = e.getMessage();
                    System.out.println(errorMessage);
                }
            }
        }
    }

    public int AddList(String username, String title, String Description, int Cat){
        Driverloader();

        try {
            String sql = "SELECT ID FROM user WHERE Username = '" + username + "'";
            ResultSet resultSet  = stmt.executeQuery(sql);
            String userID = null;
            if (resultSet.next())
                userID = resultSet.getString("ID");
            System.out.println(Cat);
            sql = "INSERT INTO todo (Title, Description, UserID, category_id) VALUES ('" + title + "', '" + Description + "', '" + userID + "','" + Cat + "')";
            int rowsAffected  = stmt.executeUpdate(sql);
            System.out.println(rowsAffected);
            return rowsAffected;

            // Rest of your code
        } catch (SQLException e) {
            String errorMessage = e.getMessage();
            System.out.println(errorMessage);
            return 0;
            // Handle the SQLException appropriately
        } finally {
            // Close the statement and connection
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    String errorMessage = e.getMessage();
                    System.out.println(errorMessage);
                    // Handle the SQLException appropriately
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    String errorMessage = e.getMessage();
                    System.out.println(errorMessage);
                    // Handle the SQLException appropriately
                }
            }
        }
    }

    public int EditList(String username, String title, String Description, String FinalTitle, String FinalDescription, int cat){
        Driverloader();

        try {
            String sql = "SELECT ID FROM user WHERE Username = '" + username + "'";
            ResultSet resultSet  = stmt.executeQuery(sql);
            String userID = null;
            if (resultSet.next())
                userID = resultSet.getString("ID");
            sql = "UPDATE todo SET Title = '" + FinalTitle + "', Description = '" + FinalDescription + "', category_id = '" + cat + "' WHERE UserID = '" + userID + "' AND Title = '" + title + "' AND Description = '" + Description + "'";
            int rowsAffected  = stmt.executeUpdate(sql);
            return rowsAffected;
            // Rest of your code
        } catch (SQLException e) {
            String errorMessage = e.getMessage();
            System.out.println(errorMessage);
            return 0;
            // Handle the SQLException appropriately
        } finally {
            // Close the statement and connection
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    String errorMessage = e.getMessage();
                    System.out.println(errorMessage);
                    // Handle the SQLException appropriately
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    String errorMessage = e.getMessage();
                    System.out.println(errorMessage);
                    // Handle the SQLException appropriately
                }
            }
        }
    }

    public int DeleteList(String username, String title, String Description){
        Driverloader();
        try {
            String sql = "SELECT ID FROM user WHERE Username = '" + username + "'";
            ResultSet resultSet  = stmt.executeQuery(sql);
            String userID = null;
            if (resultSet.next())
                userID = resultSet.getString("ID");
            sql = "DELETE FROM todo" +
                    " WHERE UserID = '" + userID + "' AND Title = '" + title + "' AND Description = '" + Description + "'";
            int rowsAffected  = stmt.executeUpdate(sql);
            return rowsAffected;
            // Rest of your code
        } catch (SQLException e) {
            String errorMessage = e.getMessage();
            System.out.println(errorMessage);
            return 0;
            // Handle the SQLException appropriately
        } finally {
            // Close the statement and connection
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    String errorMessage = e.getMessage();
                    System.out.println(errorMessage);
                    // Handle the SQLException appropriately
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    String errorMessage = e.getMessage();
                    System.out.println(errorMessage);
                    // Handle the SQLException appropriately
                }
            }
        }
    }
    public int Status(String username, String title, String Description, String DoneUndone){
        Driverloader();
        try {
            String sql = "SELECT ID FROM user WHERE Username = '" + username + "'";
            ResultSet resultSet  = stmt.executeQuery(sql);
            String userID = null;
            if (resultSet.next())
                userID = resultSet.getString("ID");
            sql = "UPDATE todo" +
                    " SET DoneUndone = '" + DoneUndone + "'" +
                    " WHERE UserID = '" + userID + "' AND Title = '" + title + "' AND Description = '" + Description + "'";
            int rowsAffected  = stmt.executeUpdate(sql);
            return rowsAffected;
            // Rest of your code
        } catch (SQLException e) {
            String errorMessage = e.getMessage();
            System.out.println(errorMessage);
            return 0;
            // Handle the SQLException appropriately
        } finally {
            // Close the statement and connection
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    String errorMessage = e.getMessage();
                    System.out.println(errorMessage);
                    // Handle the SQLException appropriately
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    String errorMessage = e.getMessage();
                    System.out.println(errorMessage);
                    // Handle the SQLException appropriately
                }
            }
        }
    }
}



