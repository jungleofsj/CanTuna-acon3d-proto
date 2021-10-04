package CanTuna.CanTunaacon3d;

import CanTuna.CanTunaacon3d.Service.ItemService;
import CanTuna.CanTunaacon3d.Service.UserService;
import CanTuna.CanTunaacon3d.repository.*;
import net.bytebuddy.asm.MemberRemoval;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class SpringConfig {

    private final DataSource dataSource_user;
    private final DataSource dataSource_item;
    private final DataSource dataSource_rate;


    public SpringConfig(DataSource dataSource_user, DataSource dataSource_item, DataSource dataSource_rate) {
        this.dataSource_user = dataSource_user;
        this.dataSource_item = dataSource_item;
        this.dataSource_rate = dataSource_rate;
    }

    @Bean
    public UserService userService(){
        return new UserService(userRepository());
    }

    @Bean
    public UserRepository userRepository() {
        return new JdbcTempUserRepository(dataSource_user);
    }

    @Bean
    public ItemService itemService(){
        return new ItemService(itemRepository(), exRateRepository());
    }

    @Bean
    public ItemRepository itemRepository() {
        return new JdbcTempItemRepository(dataSource_item);
    }

    @Bean
    public ExRateRepository exRateRepository(){
        return new JdbcTempExRateRepository(dataSource_rate);
    }

}

