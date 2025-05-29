package mx.unam.aragon.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<NoCacheFilter> noCacheFilter() {
        FilterRegistrationBean<NoCacheFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new NoCacheFilter());
        registrationBean.addUrlPatterns("/admin/*", "/cajero/*"); // Filtra solo estas rutas
        registrationBean.setOrder(1); // Prioridad del filtro (opcional)

        return registrationBean;
    }
}
