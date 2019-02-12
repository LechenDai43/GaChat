package Client;

import java.sql.*;
import java.util.ArrayList;

public class GaChatDataClient {
    static private Connection connection;
    static private Statement statement;
    static private ResultSet resultSet;

    /**to connect the database once this type is loaded
     * if this project is going to lunch, then the connection information need to be changed
     */
    static{
        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/gachat?useUnicode=true&characterEncoding=utf-8",
                    "gachat", "gatech");
            statement = connection.createStatement();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**receiving username and password from login panel
     * the username may be a real user name or an email address
     * if there are any problems with the username or the password
     * this method will return the error massage
     * otherwise, it will return an empty string
     * called by LogInPanel->LogIn()
     * */
    static String checkLogin(String unser, String passw){
        String result = "";
        String password = "";
        try{
            String query = "select * from users where username = \"" + unser + "\"";
            resultSet = statement.executeQuery(query);
            resultSet.beforeFirst();
            if(resultSet.next()){
                byte[] pw = resultSet.getBytes("password");
                password = new String(pw);
            }else{
                query = "select * from users where email = \"" + unser + "\"";
                resultSet = statement.executeQuery(query);
                resultSet.beforeFirst();
                if(resultSet.next()){
                    byte[] pw = resultSet.getBytes("password");
                    password = new String(pw);
                }
            }
        }catch (Exception e1){
            e1.printStackTrace();
        }
        if(password.equals("")){
            result = "Username/Email is invalid.";
        }else{
            if(!passw.equals(password)){
                result = "Password does not match.";
            }else{
                result = "";
            }
        }
        return result;
    }

    /**
     * this method receive a valid username/email as parameter
     * if the username/email exists in the database
     * it will return the user_id of the corresponding username/email
     * otherwise, it will return -1
     * called by LogInPanel->LogIn()
     */
    static int getLogInID(String key){
        int result = -1;
        String query = "select * from users where username = \"" + key + "\"";
        try{
            resultSet = statement.executeQuery(query);
            resultSet.beforeFirst();
            if(resultSet.next()){
                result = resultSet.getInt("user_id");
            }else{
                query = "select * from users where email = \"" + key + "\"";
                resultSet = statement.executeQuery(query);
                resultSet.beforeFirst();
                if(resultSet.next()){
                    result = resultSet.getInt("user_id");
                }
            }
        }catch (Exception e2){
            e2.printStackTrace();
        }
        return result;
    }

    static String getUsernameById(int id){
        String result = "";
        try{
            String query = "select username from users where user_id = " + id;
            resultSet = statement.executeQuery(query);
            resultSet.beforeFirst();
            resultSet.next();
            result = new String(resultSet.getBytes("username"));
        }catch (Exception e3){
            e3.printStackTrace();
        }
        return  result;
    }

    static ArrayList<String> getBasicProfile(int id){
        ArrayList<String> result = new ArrayList<>();
        try {
            String query = "select gender, age, email, sign from users right join profile on users.user_id = profile.user_id where users.user_id = " + id;
            resultSet = statement.executeQuery(query);
            resultSet.beforeFirst();
            if(resultSet.next()) {
                result.add(new String(resultSet.getBytes("gender")));
                result.add(new String(resultSet.getBytes("age")));
                result.add(new String(resultSet.getBytes("email")));
                result.add(new String(resultSet.getBytes("sign")));
            }else {
                query = "select email from users where user_id = " + id;
                resultSet = statement.executeQuery(query);
                resultSet = statement.executeQuery(query);
                resultSet.beforeFirst();
                resultSet.next();
                result.add("N/A");
                result.add("N/A");
                result.add(new String(resultSet.getBytes("email")));
                result.add("");
            }
        }catch (Exception e4){
            e4.printStackTrace();
        }
        return result;
    }

    /**
     * this function receive an email address and check if this email is in the database
     * if the email is not in database,it returns false
     * if the email is in database, it returns true
     * @param address the email address for checking
     * @return false: it is not duplicated in the database
     *         true: it is duplicated in the database
     */
    static boolean checkEmailDup(String address){
        boolean result = false;
        try{
            String query = "select * from users where email = \"" + address + "\"";
            resultSet = statement.executeQuery(query);
            resultSet.beforeFirst();
            if(resultSet.next()){
                result = true;
            }
        }catch (Exception e5){
            e5.printStackTrace();
        }

        return result;
    }

    /**
     * this function receive a username and check if this email is in the database
     * if the username is not in database,it returns false
     * if the username is in database, it returns true
     * @param name the username for checking
     * @return false: it is not duplicated in the database
     *         true: it is duplicated in the database
     */
    static boolean checkUserNameDup(String name){
        boolean result = false;
        try{
            String query = "select * from users where username = \"" + name + "\"";
            resultSet = statement.executeQuery(query);
            resultSet.beforeFirst();
            if(resultSet.next()){
                result = true;
            }
        }catch (Exception e6){
            e6.printStackTrace();
        }

        return result;
    }

    /**
     * this function add a new user into the database
     * this function must called after checking of the duplication of email address and username
     * after the new account has been created, the function should return the user_id of the new account
     * @param email the email address of the new account, cannot be duplicated
     * @param name the username of the new account, cannot be duplicated
     * @param password the password of the new account
     * @return the user_id of the new account
     */
    static int addUser(String email, String name, String password){
        int result = -1;
        try{
            String query = "insert into users (email,username,password) values (\"" + email + "\",\"" + name + "\",\"" + password + "\")";
            statement.execute(query);
            query = "select user_id from users where email = \"" + email + "\"";
            resultSet = statement.executeQuery(query);
            resultSet.beforeFirst();
            resultSet.next();
            result = resultSet.getInt("user_id");
        }catch (Exception e7){
            e7.printStackTrace();
        }

        return result;
    }

    static ArrayList<String> getProfile(int Id){
        ArrayList<String> result = new ArrayList<>();
        String query = "select * from profile where user_id = " + Id;
        try{
            resultSet = statement.executeQuery(query);
            resultSet.beforeFirst();
            if(resultSet.next()){
                result.add(resultSet.getDate("birthday").toString());
                result.add(new String(resultSet.getBytes("city")));
                result.add(new String(resultSet.getBytes("hometown")));
                result.add(new String(resultSet.getBytes("occupation")));
            }
        }catch (Exception e8){
            e8.printStackTrace();
        }
        return result;
    }

    static ArrayList<String> getInterest(int Id){
        ArrayList<String> result = new ArrayList<>();
        String query = "select * from interest where interest_id in (select interest_id from user_interest where user_id = " + Id + ")";
        try{
            resultSet = statement.executeQuery(query);
            resultSet.beforeFirst();
            while(resultSet.next()){
                result.add(new String(resultSet.getBytes("interest")));
            }
        }catch (Exception e9){
            e9.printStackTrace();
        }
        return result;
    }

    static ArrayList<ArrayList<String>> getSchoolInfoById(int Id){
        ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
        String query = "select * from school join user_school on school.school_id = user_school.school_id where user_id = " + Id;
        try{
            resultSet = statement.executeQuery(query);
            resultSet.beforeFirst();
            while(resultSet.next()){
                String school = new String(resultSet.getBytes("school"));
                String type = new String(resultSet.getBytes("type"));
                String first = school + "(" + type + ")";
                String second = "";
                switch (type){
                    case "Undergraduate": case "Master School": case "Doctor School":
                        second = new String(resultSet.getBytes("major"));
                        break;
                    default:
                        second = "";
                        break;
                }
                String third = "";
                try{
                    third = new String(resultSet.getBytes("graduation"));
                }catch (Exception ne){
                    third = "";
                }
                ArrayList<String> one_school = new ArrayList<>();
                one_school.add(first);
                one_school.add(second);
                one_school.add(third);
                result.add(one_school);
            }
        }catch (Exception e10){
            e10.printStackTrace();
        }
        return result;
    }

    static ArrayList<ArrayList<String>> getJobInfoById(int Id){
        ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
        String query = "select *  from employer join user_employer on employer.employer_id = user_employer.employer_id where user_employer.user_id = " + Id;
        try{
            resultSet = statement.executeQuery(query);
            resultSet.beforeFirst();
            while (resultSet.next()){
                ArrayList<String> one_job = new ArrayList<>();
                one_job.add(new String(resultSet.getBytes("title")));
                one_job.add(new String(resultSet.getBytes("employer")));
                one_job.add(resultSet.getDate("start").toString());
                one_job.add(resultSet.getDate("end").toString());
                result.add(one_job);
            }
        }catch (Exception e11){
            e11.printStackTrace();
        }
        return  result;
    }

}
