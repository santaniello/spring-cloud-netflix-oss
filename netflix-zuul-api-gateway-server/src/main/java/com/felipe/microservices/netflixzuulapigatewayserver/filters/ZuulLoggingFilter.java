package com.felipe.microservices.netflixzuulapigatewayserver.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * Filtro que irá gravar um logg para todas as requisições que passarem pelo Zuul.
 **/
@Component
public class ZuulLoggingFilter extends ZuulFilter {

    private Logger logger= LoggerFactory.getLogger(this.getClass());

    /**
     * Nesse método indicamos se queremos que o nosso filtro seja chamado
     * antes da requisição (pre) depois da requisição (post) ou se somente acontecer
     * algum erro na requisição (error).
     *
     * @return String
     */

    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * Se você tem multiplos filtros (Logging, Segurança e etc...),
     * vocẽ pode setar a ordem de prioridade entre eles...
     * no exemplo abaixo, nós setamos a prioridade como 1.
     * @return int
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * Método que indica se o filtro dev ser executado ou não...
     *
     * @return boolean que indica se o filtro deve ser executado ou não
     */

    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * Método que contém a ação que queremos executar no filtro...
     * @return Object
     * @throws ZuulException
     */

    @Override
    public Object run() throws ZuulException {
        // Obtendo a requisição atual...
        HttpServletRequest request = RequestContext.getCurrentContext().getRequest();
        // Gravando um simples logg...
        logger.info("request -> {} request uri -> {}",request, request.getRequestURI());

        return null;
    }
}
