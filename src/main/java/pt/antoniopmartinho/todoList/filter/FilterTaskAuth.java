package pt.antoniopmartinho.todoList.filter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        System.out.println("Chegou no filtro");
        //Auth validation user and then password
        var authorization = request.getHeader("Authorization");
        filterChain.doFilter(request, response);

       var userPass =  authorization.substring("Basic ".length()).trim();

       byte[] userPassDecoded = Base64.getDecoder().decode(userPass);
       var userPassString = new String(userPassDecoded);
    String[] credentials = userPassString.split(":");
    String username = credentials[0];
    String password = credentials[1];
    System.out.println("username: " + username);
    System.out.println("password: " + password);
    }
}