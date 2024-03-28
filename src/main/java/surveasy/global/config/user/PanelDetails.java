package surveasy.global.config.user;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import surveasy.domain.panel.domain.Panel;

import java.util.ArrayList;
import java.util.Collection;

@Data
public class PanelDetails implements UserDetails {

    private Panel panel;

    private boolean duplicateEmail = false;

    public PanelDetails(Panel panel) {
        this.panel = panel;
    }

    public Long getId() {
        return panel.getId();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add((GrantedAuthority) () -> String.valueOf(panel.getRole()));
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
}
