package CanTuna.CanTunaacon3d.domain;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

public class ExRate {


    public static final Long CURRENCY_TYPE_KOR = 0L;
    public static final Long CURRENCY_TYPE_ENG = 1L;
    public static final Long CURRENCY_TYPE_CHN = 2L;

    public static final Map<String, Long> currencyDic = new HashMap<String, Long>(){{
        put("KOR",CURRENCY_TYPE_KOR);
        put("ENG", CURRENCY_TYPE_ENG);
        put("CHN", CURRENCY_TYPE_CHN);
    }};




    private String country;
    private Double ExRate;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Double getExRate() {
        return ExRate;
    }

    public void setExRate(Double exRate) {
        ExRate = exRate;
    }
}
