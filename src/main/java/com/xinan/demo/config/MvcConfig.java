package com.xinan.demo.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.Formatter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Locale;

@Configuration
//@EnableWebFlux
public class MvcConfig implements WebFluxConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatter(new Formatter<BigDecimal>() {
            @Override
            public BigDecimal parse(String s, Locale locale) throws ParseException {
                return new BigDecimal(s);
            }

            @Override
            public String print(BigDecimal bigDecimal, Locale locale) {
                return "bd:" + bigDecimal.toString();
            }
        });
    }

//          return db.connection()
//          .map(connection -> {
//              connection.setAutoCommit(false);
//              PreparedStatement pstmt = connection.prepareStatement(sql);
//              pstmt.setString(1, post.getContent());
//              pstmt.setString(2, post.getContent());
//              int i = pstmt.executeUpdate();
//              pstmt.close();
//              connection.commit();
//              return i;
//          });

}
