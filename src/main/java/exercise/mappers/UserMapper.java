package exercise.mappers;

import exercise.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User mapPurchase(String line) {
        String[] fields = line.split(",");
        return new User(Long.valueOf(fields[0]), fields[1], fields[2]);
    }
}
