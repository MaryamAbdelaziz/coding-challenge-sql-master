package exercise.mappers;

import exercise.model.Purchase;
import exercise.model.User;
import exercise.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;


@Component
public class PurchaseMapper {

    @Autowired
    private UsersRepository usersRepository;

    public Purchase mapPurchase(String line) {
        String[] fields = line.split(",");
        User user = usersRepository.findById(Long.valueOf(fields[2])).orElseThrow(EntityNotFoundException::new);
        return new Purchase(Long.valueOf(fields[0]), fields[1], user);
    }
}
