package mx.edu.utez.poeta.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import mx.edu.utez.poeta.config.JwtResponse;
import mx.edu.utez.poeta.config.JwtTokenUtil;
import mx.edu.utez.poeta.entity.PostulantCV;
import mx.edu.utez.poeta.entity.User;
import mx.edu.utez.poeta.entity.UserConnection;
import mx.edu.utez.poeta.repository.IPostulantCVRepository;
import mx.edu.utez.poeta.repository.IUserConnectionRepository;
import mx.edu.utez.poeta.repository.IUserRepository;

@Service
// @Transactional
public class UserService implements UserDetailsService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private IUserConnectionRepository userConnectionRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IPostulantCVRepository postulantCVRepository;

    @Transactional(readOnly = true)
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public User findUserById(long id) {
        return userRepository.findById(id).get();
    }

    public boolean save(User obj) {
        UserConnection userConnection = new UserConnection();
        obj.setPassword(passwordEncoder.encode(obj.getPassword()));
        boolean flag = false;
        User tmp = userRepository.save(obj);
        if (tmp != null) {
            flag = true;
            userConnection.setUser(tmp);
            if (tmp.getRoles().getAlias().equalsIgnoreCase("candidato")) {
                PostulantCV tmpCv = new PostulantCV();
                tmpCv.setPostulant(tmp);
                postulantCVRepository.save(tmpCv);
            }
            userConnection.setStatus(1);
            userConnectionRepository.save(userConnection);
        }
        return flag;
    }

    public boolean delete(long id) {
        boolean flag = false;
        User tmp = findUserById(id);
        if (tmp != null) {
            userRepository.delete(tmp);
            flag = true;
        }
        return flag;
    }

    public ResponseEntity<?> createTokenAuthentication(@RequestBody User user) {
        if (authentication(user.getUsername(), user.getPassword())) {
            String token;
            try {
                UserDetails userDetails = loadUserByUsername(user.getUsername());
                token = jwtTokenUtil.generateToken(userDetails);
                if (userConnectionRepository.findByUser_Username(user.getUsername()) != null) {
                    user = userRepository.findByUsername(user.getUsername());
                    if (!user.isEnabled()) {
                        return ResponseEntity.ok(false);
                    }
                } else {
                    return ResponseEntity.ok(false);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity<>(false, HttpStatus.OK);
            }
            return ResponseEntity.ok(new JwtResponse(token));
        } else {
            return new ResponseEntity<>(false, HttpStatus.OK);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user != null) {
            List<User> employees = new ArrayList<>(Collections.singletonList(user));
            List<GrantedAuthority> authorities = getUserAuthority(employees);
            return buildUserForAuthentication(user, authorities);
        } else {
            throw new UsernameNotFoundException("Not found");
        }
    }

    public boolean authentication(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            return true;
        } catch (DisabledException | BadCredentialsException e) {
            return false;
        }
    }

    @Transactional(readOnly = true)
    private List<GrantedAuthority> getUserAuthority(List<User> users) {
        Set<GrantedAuthority> roles = new HashSet<>();
        users.forEach((user) -> roles.add(new SimpleGrantedAuthority(user.getRoles().getName())));
        return new ArrayList<>(roles);
    }

    private UserDetails buildUserForAuthentication(User user, List<GrantedAuthority> authorities) {
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                authorities);
    }

    public boolean verifySession(User user) {
        UserConnection userConnection = userConnectionRepository.findByUser_Username(user.getUsername());
        if (userConnection != null) {
            return userConnection.getStatus() == 1;
        } else {
            return false;
        }
    }

    @Transactional(readOnly = true)
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

}
