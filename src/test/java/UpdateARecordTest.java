
import Util.ConnectionUtil;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UpdateARecordTest {
    UpdateARecord updateARecord = new UpdateARecord();

    /**
     * In this test we are querying everything in the users table to ensure that Alexa was successfully updated to "Rush".
     */
    @Test
    public void problem1Test(){
        //arrange
        User user1 = new User(1,"Steve","Garcia");
        User user2 = new User(2,"Alexa", "Rush");
        User user3 = new User(3,"Steve","Jones");
        User user4 = new User(4, "Brandon", "Smith");
        User user5 = new User(5,"Adam","Jones");
        List<User> expectedResult = new ArrayList<>();
        expectedResult.add(user1);
        expectedResult.add(user2);
        expectedResult.add(user3);
        expectedResult.add(user4);
        expectedResult.add(user5);


        //act
        updateARecord.problem1();

        List<User> actualResult = new ArrayList<>();

        try {
            Connection connection = ConnectionUtil.getConnection();

            String sql = "SELECT * FROM site_user;";

            PreparedStatement ps = connection.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                actualResult.add(new User(rs.getInt(1), rs.getString(2), rs.getString(3)));
            }

        } catch (SQLException e) {
            System.out.println("problem1: " + e.getMessage() + '\n');
        }

        //assert
        Assert.assertEquals(expectedResult, actualResult);
    }

    /**
     * The @Before annotation runs before every test so that way we create the tables required prior to running the test
     */
    @Before
    public void beforeTest(){

        try {

            Connection connection = ConnectionUtil.getConnection();

            //Write SQL logic here
            String sql1 = "CREATE TABLE site_user (id SERIAL PRIMARY KEY, firstname varchar(100), lastname varchar(100));";
            String sql2 = "INSERT INTO site_user (firstname, lastname) VALUES ('Steve', 'Garcia');";
            String sql3 = "INSERT INTO site_user (firstname, lastname) VALUES ('Alexa', 'Rush');";
            String sql4 = "INSERT INTO site_user (firstname, lastname) VALUES ('Steve', 'Jones');";
            String sql5 = "INSERT INTO site_user (firstname, lastname) VALUES ('Brandon', 'Smith');";
            String sql6 = "INSERT INTO site_user (firstname, lastname) VALUES ('Adam', 'Jones');";

            PreparedStatement ps = connection.prepareStatement(sql1 + sql2 + sql3 + sql4 + sql5 + sql6);

            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("failed creating table");
            e.printStackTrace();
        }
    }

    /**
     * The @After annotation runs after every test so that way we drop the tables to avoid conflicts in future tests
     */
    @After
    public void cleanup(){

        try {

            Connection connection = ConnectionUtil.getConnection();

            String sql = "DROP TABLE site_user;";

            PreparedStatement ps = connection.prepareStatement(sql);

            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("dropping table");
        }
    }

}
