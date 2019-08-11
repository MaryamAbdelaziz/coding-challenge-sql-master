package exercise;

import exercise.mappers.PurchaseMapper;
import exercise.mappers.UserMapper;
import exercise.model.User;
import exercise.repositories.EntityBaseRepository;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@Ignore
@RunWith(MockitoJUnitRunner.class)
public class SqlTest {

    @Mock
    private PurchaseMapper purchaseMapper;
    @Mock
    private UserMapper userMapper;
    @Mock
    private EntityBaseRepository entityBaseRepository;

    @InjectMocks
    private Sql tested;

    @Test
    public void init() throws Exception {
        when(userMapper.mapPurchase(any(String.class)))
                .thenReturn(new User(1L, "TEST", "test@test.com"));

        File inputF = new File("/users.csv");
        InputStream inputFS = new FileInputStream(inputF);
        tested.init(inputFS);


    }
}