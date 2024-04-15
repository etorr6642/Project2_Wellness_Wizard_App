package com.example.project2_wellness_wizard_app;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import com.example.project2_wellness_wizard_app.database.entities.User;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    User user1 = null;
    String defaultUsername ="defaultUsername";
    String defaultPassword = "defaultPassword";

    @Before
    public void setup(){
        user1 = new User(defaultUsername,defaultPassword);
    }

    @After
    public void teardown(){
        user1 = null;
    }
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void user_test(){
        User user2 = null;
        user2 = new User("Eddie", "awesome");
        assertNotNull(user2);
        assertNotEquals(user1,user2);
    }

    @Test
    public void getUser_test(){
        assertEquals(defaultUsername,user1.getUsername());
    }
}