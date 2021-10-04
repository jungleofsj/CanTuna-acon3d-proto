package CanTuna.CanTunaacon3d.repository;

import CanTuna.CanTunaacon3d.domain.ExRate;
import CanTuna.CanTunaacon3d.domain.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;



public class JdbcTempExRateRepository implements ExRateRepository{

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcTempExRateRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private final Integer CURRENCY_TYPE_KOR = 0;
    private final Integer CURRENCY_TYPE_ENG = 1;
    private final Integer CURRENCY_TYPE_CHN = 2;


    @Override
    public Optional<ExRate> getRatebyCountry(Integer currencyType) {
        String country = null;
        switch (currencyType){
            //case for country
            case 0:
                country = "KOR";
                break;
            case 1:
                country = "ENG";
                break;
            case 2:
                country = "KOR";
                break;
        }

        List<ExRate> result = jdbcTemplate.query("select * from ITEM where country = ? ", exRateRowMapper(), country);
        return result.stream().findFirst();

    }

    /*private RowMapper<Item> itemRowMapper() {
        return(rs, row) ->{
            Item item = new Item();
            item.setItemId(rs.getLong("id"));
            item.setItemNameKor(rs.getString("name_kor"));
            item.setItemCreator(rs.getString("creator"));
            item.setItemApproved(rs.getBoolean("approved"));
            return item;
        };
    }*/

    private RowMapper<ExRate> exRateRowMapper() {
        return(rs, row) ->{
            ExRate exRate= new ExRate();
            exRate.setCountry(rs.getString("country"));
            exRate.setExRate(rs.getDouble("rate"));
            return exRate;
        };

    }
}
