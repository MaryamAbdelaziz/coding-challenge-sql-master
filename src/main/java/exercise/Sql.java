package exercise;

import exercise.enums.EntityType;
import exercise.exceptions.EmptyInputStreamException;
import exercise.exceptions.InvalidCsvException;
import exercise.mappers.PurchaseMapper;
import exercise.mappers.UserMapper;
import exercise.model.EntityBase;
import exercise.repositories.EntityBaseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class Sql {

    Logger logger = LoggerFactory.getLogger(Sql.class);

    private PurchaseMapper purchaseMapper;
    private UserMapper userMapper;

    private EntityBaseRepository entityBaseRepository;

    @Autowired
    public Sql(PurchaseMapper purchaseMapper, UserMapper userMapper,
               EntityBaseRepository entityBaseRepository) {
        this.purchaseMapper = purchaseMapper;
        this.userMapper = userMapper;
        this.entityBaseRepository = entityBaseRepository;
    }

    public Table init(InputStream csvContent) {

        Table result = null;
        List<EntityBase> itemList = new ArrayList<>();

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(csvContent));

            EntityType entityType = checkEntityType(br.lines().findFirst());

            if (entityType.equals(EntityType.PURCHASE)) {
                // skip the header of the csv
                itemList = br.lines().skip(1).map(x -> purchaseMapper.mapPurchase(x)).collect(Collectors.toList());
                result = new Table(itemList, EntityType.PURCHASE);
            } else {
                itemList = br.lines().skip(1).map(x -> userMapper.mapPurchase(x)).collect(Collectors.toList());
                result = new Table(itemList, EntityType.USERS);
            }

            br.close();
        } catch (IOException e) {
            logger.error("error while parsing input csv");
        }
        return result;
    }


    public Table orderByDesc(Table table, String columnName) {
        return new Table(entityBaseRepository.findAll(Sort.by(Sort.Direction.DESC, columnName)),
                table.getEntityType());
    }

    public Table join(Table left, Table right, String joinColumnTableLeft, String joinColumnTableRight) {
        // we will use specifications, but not sure if this code will run
        Specifications<EntityBase> where = Specifications.where(
                (root, query, builder) -> {
                    final Join<EntityBase, EntityBase> items = root.join(joinColumnTableLeft, JoinType.LEFT);
                    return builder.and(
                            builder.equal(items.get(joinColumnTableLeft), joinColumnTableRight)
                    );
                }
        );
        return new Table(entityBaseRepository.findAll(where), EntityType.NONE);

    }


    private EntityType checkEntityType(Optional<String> first) {

        String firstLine = first.orElseThrow(EmptyInputStreamException::new);
        String[] fields = firstLine.split(",");

        switch (fields[0]) {
            case "AD_ID":
                return EntityType.PURCHASE;
            case "USER_ID":
                return EntityType.USERS;
            default:
                throw new InvalidCsvException();
        }

    }
}
