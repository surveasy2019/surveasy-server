package surveasy.global.config.user;

import lombok.Data;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import surveasy.domain.panel.domain.Panel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Data
public class PanelDetails implements UserDetails, OAuth2User {

    private Panel panel;

    private boolean duplicateEmail = false;

    @Setter
    private Map<String, Object> attributes;

    public PanelDetails(Panel panel) {
        this.panel = panel;
    }

    public Long getId() {
        return panel.getId();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return panel.getRole();
            }
        });

        return collection;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return panel.getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getName() {
        return null;
    }
}
