package  com.spring.springbootapplication.auth;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails implements UserDetails {

    private final String userName;  // 氏名
    private final String email;      // メールアドレス
    private final String password;   // パスワード
    private final Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(String userName, String email, String password,
                             Collection<? extends GrantedAuthority> authorities) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;  // 氏名をユーザー名として使用
    }

    public String getEmail() {
        return email; // メールアドレスを取得するメソッド
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // アカウントの有効期限がない場合はtrue
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // アカウントがロックされていない場合はtrue
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // 資格情報の有効期限がない場合はtrue
    }

    @Override
    public boolean isEnabled() {
        return true; // アカウントが有効な場合はtrue
    }
}