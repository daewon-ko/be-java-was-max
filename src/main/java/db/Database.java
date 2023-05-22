package db;

import com.google.common.collect.Maps;

import model.User;

import java.util.Collection;
import java.util.Map;

public class Database {
    private static Map<String, User> users = Maps.newHashMap();

    public static void addUser(User user) {
        users.put(user.getUserId(), user);
    }

    public static User findUserById(String userId) {
        return users.get(userId);
    }


    // TODO: 예외처리 필요. Equal 메서드를 Overriding 하느네 맞을까?
    // TODO : runtime Exception을 throw하면 회원가입 페이지로 redirect 하게끔 해줘야 할까? 또, Database에서 확인하는건 부적절하지 않을까?
    // TODO : 또, password를 requestBody 등에 보내는데.. 이것이 보안과 관련지어 생각할때 과연 최선일까?
    public static boolean isExistUser(String userId, String password) {
        for (String u : users.keySet()) {
            User user = users.get(u);
            if (user.getUserId().equals(userId) && user.getPassword().equals(password)) {
                return true;
            }
        }
        throw new RuntimeException("존재하지 않는 회원입니다.");
    }

    public static Collection<User> findAll() {
        return users.values();
    }
}
