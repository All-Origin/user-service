package com.brainz.user_service.entity;

import com.brainz.user_service.converter.JsonEnumListConverter;
import com.brainz.user_service.enums.Roles;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "users",
    uniqueConstraints = {
    @UniqueConstraint(columnNames = "email"),
    @UniqueConstraint(columnNames = "userName")
    }
    )
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = true)
// @Data - this will add all expect NoArgs+AllArgs
@Builder
public class User extends BaseEntity {
    /**
     * before creating it i think we need a base entity also!! to make oop work !!
     */
    //@Email
   // @NotBlank
    private String email;
    private String name; // eg: jeet solanki
    @NotBlank
    @Column(name = "user_name", nullable = false, unique = true)
    @Size(min = 4)
    private String userName; // eg: jeet1234
    // password limited to 4 digits
    @Column(name = "password_hash")
    private String passwordHash;  // 1234 or sola or #@@#  etc
    @Convert(converter = JsonEnumListConverter.class)
    @Column(columnDefinition = "TEXT") // optional, to avoid length issues
    private List<Roles> roles; // can be multiple like user , admin | user , Ba. etc
    private Boolean enabled = true;
    @Column(name = "account_non_locked")
    private Boolean accountNonLocked = true;
    @Column(name = "credentials_non_expired")
    private Boolean credentialsNonExpired = true;
    @Column(name = "account_non_expired")
    private Boolean accountNonExpired = true;
}
