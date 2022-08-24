package mx.edu.utez.poeta.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.edu.utez.poeta.entity.Roles;
import mx.edu.utez.poeta.entity.User;
import mx.edu.utez.poeta.service.UserService;

@Service
public class AuthCheckPermission {
    
    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserService userService;

    public boolean checkPermission(String token, String roleAlias) {
        String requestTokenHeader = token.replace("[", "").replace("]", "");
        String username = null;
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer")) {
            String jwtToken = requestTokenHeader.substring(7);
            try {
                username = jwtTokenUtil.getUsernameFromToken(jwtToken);
                User user = userService.findByUsername(username);
                Roles role = user.getRoles();
                if (role.getAlias().equalsIgnoreCase(roleAlias)) return true;
            } catch (Exception e) {
                LOGGER.error(String.format("AuthCheckPermission: checkPermission %s", e.getMessage()));
            }
        }
        return false;
    }

    public User findUserByToken(String token) {
        String requestTokenHeader = token.replace("[", "").replace("]", "");
        User user = null;
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer")) {
            String jwtToken = requestTokenHeader.substring(7);
            try {
                user = userService.findByUsername(jwtTokenUtil.getUsernameFromToken(jwtToken));
                return user;
            } catch (Exception e) {
                LOGGER.error(String.format("AuthCheckPermission: findUSerByToken %s", e.getMessage()));
            }
        }
        return null;
    }

    public boolean isLoguedUser(String token, long id) {
        User tmp = userService.findUserById(id);
        User logued = findUserByToken(token);
        if (tmp.getId() == logued.getId()) return true;
        return false;
    }

    /* public boolean isLoguedUser(String token, long id) {
        User tmp = findUserById(id);
        User logued = findUserByToken(token);
        if (tmp.getId() == logued.getId()) return true;
        return false;
    } */

}
