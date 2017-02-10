package com.example.jferris.p2pmessaging;

/**
 * Created by jferris on 09/02/17.
 */

public class UserController {
    private static UserController instance = null;
    private static User currentUser = null;

    protected UserController() {
        //create one shared instance of user controller
    }

    public UserController getInstance() {
        if (instance == null) {
            instance = new UserController();
        }
        return instance;
    }

    public static User getCurrentUser() {
        if(currentUser == null) {
//            createUser();
        }
        return currentUser;
    }

    public static Boolean setCurrentUser(User user) {
        currentUser = user;
        return true;
    }

//    private static void createUser() {
//        currentUser = new User("placeholder");
//    }
}
