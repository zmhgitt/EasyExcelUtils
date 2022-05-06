package data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Miles(miles @ kastking.com)
 * @date: 2022/4/23 11:54
 * @description:
 */
public class DataUtils {

    public static List<User> data(){
        return data(10);
    }

    public static List<User> data(Integer size){
        List<User> userList = new ArrayList<User>(10);
        User user = null;
        for (int i=0; i < size ;i++){
            user = new User();
            user.setName("name"+i);
            user.setSex(1);
            user.setStatus(1);
//            user.setDept(new Dept());
            userList.add(user);
        }
        return userList;
    }
}
