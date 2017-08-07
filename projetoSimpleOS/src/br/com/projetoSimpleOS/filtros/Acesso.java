package br.com.projetoSimpleOS.filtros;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.com.projetoSimpleOS.objetos.Usuario;

@WebFilter("/pages/*")
public class Acesso implements Filter {

    /**
     * Default constructor. 
     */
    public Acesso() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		

		HttpServletRequest httpReq = (HttpServletRequest) request;
	

		HttpSession sessao = httpReq.getSession();
		
		Usuario usuario = (Usuario)sessao.getAttribute("userlogado");
	
		
        if(usuario!=null )  {
       //httpReq.getRequestURI().endsWith("loginServlet") || httpReq.getRequestURI().endsWith("loginusuario.html")
        	
        	 chain.doFilter(request, response);
        	
        	
        }else{
      
        	((HttpServletResponse) response).sendRedirect("/projetoSimpleOS/loginusuario.html");
        	
        }
	}
	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
