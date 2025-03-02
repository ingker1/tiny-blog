package com.ingker.blogvue.security;

import com.ingker.blogvue.entity.User;
import com.ingker.blogvue.mapper.UserMapper;
import com.ingker.blogvue.util.PasswordUtils;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class AuthController {
    @Autowired
    UserMapper userMapper;

    @RequestMapping(value = "/admin/**", method = RequestMethod.OPTIONS)
    public ResponseEntity<?> handleOptions() {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody User userParam, HttpSession session) {
        // 根据登录方式或用户名查询用户
        User user = userMapper.getByLogin(userParam.getLogin());

        if (user != null && PasswordUtils.checkPassword(userParam.getPassword(), user.getPassword())) {
            // 如果密码验证成功，将用户信息存入 Session
            session.setAttribute("user", user);
            return ResponseEntity.ok("登录成功");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("错误的验证凭据");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        session.invalidate(); // 销毁 Session
        return ResponseEntity.ok("退出登录");
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody User userParam) {
        // 检查用户名是否已经存在
        User existingUser = userMapper.getByLogin(userParam.getLogin());
        if (existingUser != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("用户名已存在");
        }

        // 对密码进行哈希处理
        String hashedPassword = PasswordUtils.hashPassword(userParam.getPassword());

        // 保存用户信息到数据库
        userParam.setPassword(hashedPassword);
        userMapper.add(userParam);

        return ResponseEntity.status(HttpStatus.CREATED).body("注册成功");
    }

    @GetMapping("/check-session")
    public ResponseEntity<Object> checkSession(HttpSession session) {
        // 从 session 中获取用户信息
        Object user = session.getAttribute("user");

        if (user != null) {
            // session 中有用户信息，说明会话有效
            return ResponseEntity.ok().body(null);
        } else {
            // session 中没有用户信息，说明会话已过期
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("会话已过期，请重新登录");
        }
    }
}

