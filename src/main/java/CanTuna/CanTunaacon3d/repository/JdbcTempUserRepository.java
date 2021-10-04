package CanTuna.CanTunaacon3d.repository;

import CanTuna.CanTunaacon3d.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.text.SimpleDateFormat;
import java.util.*;

public class JdbcTempUserRepository implements UserRepository{

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcTempUserRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public User createUser(User user) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert((jdbcTemplate));
        jdbcInsert.withTableName("user").usingColumns("name", "type", "reg_date", "updt_date").usingGeneratedKeyColumns("id");

        Map<String, Object> param = new HashMap<>();

        param.put("name", user.getUserName());
        param.put("type", user.getUserType());

        param.put("reg_date", getDate());
        param.put("updt_date", getDate());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource((param)));

        user.setUserId(key.longValue());

        return user;
    }

    @Override
    public Optional<User> findUserById(Long userId) {
        List<User> result = jdbcTemplate.query("select * from user where id = ? ", userRowMapper(), userId);
        return result.stream().findAny();
    }

    @Override
    public Optional<User> findUserByName(String userName) {
        List<User> result = jdbcTemplate.query("select * from user where name = ? ", userRowMapper(), userName);
        return result.stream().findAny();
    }

    @Override
    public List<User> findByUserType(Long userType) {
        List<User> result = jdbcTemplate.query("select * from user where type = ? ", userRowMapper(), userType);
        return result;
    }

    @Override
    public List<User> findAllUser() {
        List<User> result = jdbcTemplate.query("select * from user ", userRowMapper());
        return result;
    }

    @Override
    public Long authUser(User user) {
        Boolean auth = null;
        if (jdbcTemplate.query("select * from user where name = ? AND type = ?", userRowMapper(), user.getUserName(), user.getUserType()).isEmpty()) {
            return null;
        } else {
            return user.getUserType();
        }

    }

    private RowMapper<User> userRowMapper() {
        return (rs, rowNum) -> {
            User user = new User();
            user.setUserId(rs.getLong("id"));
            user.setUserName(rs.getString("name"));
            user.setUserType(rs.getLong("type"));

            return user;
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
