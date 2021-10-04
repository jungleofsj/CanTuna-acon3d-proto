package CanTuna.CanTunaacon3d.repository;

import CanTuna.CanTunaacon3d.domain.ExRate;
import CanTuna.CanTunaacon3d.domain.Item;
import CanTuna.CanTunaacon3d.domain.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.text.SimpleDateFormat;
import java.util.*;

public class JdbcTempItemRepository implements ItemRepository{

    private final JdbcTemplate jdbcTemplate;

    public JdbcTempItemRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    @Override
    public Item createItem(Item item) {

        /*jdbcTemplate.update("INSERT INTO ITEM (name_kor = ?, text_kor = ?,price = ?,reg_date = ?, updt_date = ?) ", itemRowMapper(),
                                    item.getItemNameKor(), item.getItemTextKor(),item.getItemPrice(), getDate(), getDate());
        List<Item> result = jdbcTemplate.query("select * from ITEM where name_kor = ?", itemRowMapper(), item.getItemNameKor());
        item.setItemId(result.stream().findAny().get().getItemId());
        return item;*/



        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("ITEM").usingColumns("name_kor","text_kor","price","creator", "approved", "reg_date", "updt_date" ).usingGeneratedKeyColumns("id");

        Map<String, Object> param = new HashMap<>();

        param.put("name_kor", item.getItemNameKor());
        param.put("text_kor", item.getItemTextKor());
        param.put("price", item.getItemPrice());
        param.put("creator", item.getItemCreator());

        //상품의 approved 항목 default value
        param.put("approved", false);

        param.put("reg_date", getDate());
        param.put("updt_date", getDate());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(param));

        item.setItemId(key.longValue());

        return item;
    }
    @Override
    public Optional<Item> findItemById(Long itemId) {
        List<Item> result = jdbcTemplate.query("select * from ITEM where id = ?", itemRowMapper(), itemId);
        return result.stream().findAny();
    }

    @Override
    public Optional<Item> editItem(Long itemId , Item updateItem) {

        jdbcTemplate.update("update ITEM " +
                        "set name_kor = ?, name_eng = ?, name_chn = ?, " +
                                "text_kor = ?, text_eng = ?, text_chn = ?, " +
                                "editor = ?, commission = ?, approved = ?, " +
                                "updt_date = ? " +
                                "where id = ?", /*itemRowMapper(),*/
                        updateItem.getItemNameKor(),  updateItem.getItemNameEng(),        updateItem.getItemNameChn(),
                        updateItem.getItemTextKor(),        updateItem.getItemTextEng(),        updateItem.getItemTextChn(),
                        updateItem.getItemEditor(),               updateItem.getItemCommissonPct(),   updateItem.getItemApproved(),
                        getDate(),
                        itemId);
        List<Item> targetResult = jdbcTemplate.query("select * from ITEM where id = ?", itemRowMapper(), itemId);
        return targetResult.stream().findAny();
    }



    @Override
    public Optional<Item> findItemByName(String itemName) {
        List<Item> result = jdbcTemplate.query("select * from ITEM where name_kor = ?", itemRowMapper(), itemName);

        Item resultItem  = result.stream().findAny().get();
        System.out.println("findItembyName 결과 ");
        System.out.println("findItembyName id ::: " +  resultItem.getItemId());
        System.out.println("findItembyName namekor ::: " +  resultItem.getItemNameKor());
        System.out.println("findItembyName creator ::: " +  resultItem.getItemCreator() );
        System.out.println("findItembyName approved ::: " +  resultItem.getItemApproved());
        System.out.println("findItembyName approved ::: " +  resultItem.getItemTextKor());
        System.out.println("-----------------------");
        return result.stream().findAny();
    }

    @Override
    public List<Item> findItemByCreator(String creatorName) {

        List<Item> result = jdbcTemplate.query("select * from ITEM where creator = ?", itemRowMapper(), creatorName);
        return result;
    }

    @Override
    public List<Item> findNonApprovedItem() {
        List<Item> result = jdbcTemplate.query("select * from ITEM where approved = ?", itemRowMapper(), false);
        return result;
    }

    @Override
    public List<Item> findApprovedItem() {
        List<Item> result = jdbcTemplate.query("select * from ITEM where approved = ?", itemRowMapper(), true);
        return jdbcTemplate.query("select * from ITEM where approved = ?", itemRowMapper(), true);
    }

    @Override
    public List<Item> findAllItem() {
        return jdbcTemplate.query("select * from ITEM", itemRowMapper());
    }

    @Override
    public Item purchaseLog(Item item, Long userId, Long quantity, String currencyType) {

        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("PURCHASED_LIST").usingGeneratedKeyColumns("id");

        Map<String, Object> param = new HashMap<>();

        List<ExRate> exRate = jdbcTemplate.query("select rate from EXCHANGE_RATE WHERE country = ? ", currencyRowMapper(), currencyType);

        Double rate = exRate.stream().findAny().get().getExRate();
        param.put("item_id", item.getItemId());
        param.put("customer_id", userId);
        param.put("quantity", quantity);
        param.put("price", item.getItemPrice());
        param.put("totalprice", item.getItemPrice() * quantity * rate);
        param.put("currency", currencyType);
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyyMMdd");
        Calendar calender = Calendar.getInstance();
        String strDate = sdfDate.format(calender.getTime());

        param.put("reg_date", getDate());
        param.put("updt_date", getDate());

        return item;
    }


    private RowMapper<Item> itemRowMapper() {
        return(rs, row) ->{
            Item item = new Item();
            item.setItemId(rs.getLong("id"));
            item.setItemNameKor(rs.getString("name_kor"));
            item.setItemNameEng(rs.getString("name_eng"));
            item.setItemNameChn(rs.getString("name_chn"));
            item.setItemCreator(rs.getString("creator"));
            item.setItemEditor(rs.getString("editor"));
            item.setItemPrice(rs.getDouble("price"));
            item.setItemCommissonPct(rs.getDouble("commission"));
            item.setItemApproved(rs.getBoolean("approved"));
            item.setItemTextKor(rs.getString("text_kor"));
            item.setItemTextEng(rs.getString("text_eng"));
            item.setItemTextChn(rs.getString("text_chn"));
            //System.out.println(item.getItemNameKor());
            //System.out.println(item.getItemApproved());

            return item;

        };
    }

    private RowMapper<ExRate> currencyRowMapper() {
        return(rs, row) ->{
            ExRate rate = new ExRate();
            rate.setExRate(rs.getDouble("rate"));
//            rate.setCountry(rs.getString("country"));
            System.out.println("rere");
            return rate;
        };
    }

    //DB 상에 등록일, 수정일을 남기기 위한 getDate함수
    private Long getDate(){
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyyMMdd");
        Calendar calender = Calendar.getInstance();
        String strDate = sdfDate.format(calender.getTime());

        return Long.parseLong(strDate);
    }


}
