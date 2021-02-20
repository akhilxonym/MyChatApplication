package org.mychat.backend.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.mychat.backend.users.models.AccountStatus;
import org.mychat.backend.users.roles.Role;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("_id")
    private String id;

    private String userName;

    @Column
    @JsonIgnore
    @ToString.Exclude
    private String password;

    private Date created;

    private Date dateOfBirth;


    private LocalDateTime updated;

    private String firstName;

    private AccountStatus status;

    @Column(unique = true)
    private String email;


    private String lastName;


    @Column(unique = true)
    private String phoneNumber;
    private String address;

    private Integer pinCode;

    //CascadeType.PERSIST has issues with many to many which makes us not use CascadeType.ALL
    //So Using  other Cascades other than CascadeType.PERSIST
//    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE,CascadeType.REFRESH,CascadeType.DETACH})
//    @JoinTable(name = "USER_ROLES", joinColumns = {
//            @JoinColumn(name = "USER_ID") }, inverseJoinColumns = {
//            @JoinColumn(name = "ROLE_ID") })
//    private Set<Role> roles;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;


    public boolean doesRoleIsDoctor() {

        return doesUserHasRole("DOCTOR");


    }

    public boolean doesUserHasRole(String s) {
        return roles.stream()
                .filter(role -> {
                    return role.getName().equalsIgnoreCase(s);
                })
                .findFirst()
                .isPresent();
    }

    public boolean doesRoleIsUser() {
        return doesUserHasRole("USER");
    }

    public boolean doesRoleIsAuthority() {
        return doesUserHasRole("GOVERNMENT_AUTHORITY");
    }

    public boolean doesRoleIsTester() {
        return doesUserHasRole("TESTER");
    }

    public Integer getAge(){

        if(null != dateOfBirth)
            return LocalDate.now().getYear() - dateOfBirth.getYear();
        else
            return 0;
    }

	public AccountStatus getStatus() {
		return status;
	}

	public void setUserName(String userName2) {
        this.userName=userName2;
	}

	public void setPassword(String encrypted) {
        this.password=encrypted;
	}

	public void setRoles(Set<Role> rolesForUser) {
        this.roles=rolesForUser;
	}

	public void setUpdated(LocalDateTime now) {
        this.updated=now;
	}

	public void setCreated(Date now) {
        this.created=now;
	}

	public void setAddress(String address2) {
        this.address=address2;
	}

	public void setFirstName(String firstName2) {
        this.firstName=firstName2;
	}

	public void setEmail(String email2) {
        this.email=email2;
	}

	public void setLastName(String lastName2) {
        this.lastName=lastName2;
	}

	public void setPinCode(Integer pinCode2) {
        this.pinCode=pinCode2;
	}

	public void setPhoneNumber(String phoneNumber2) {
        this.phoneNumber=phoneNumber2;
	}


	public void setDateOfBirth(Date dateFromString) {
        this.dateOfBirth=dateFromString;
	}

	public void setStatus(AccountStatus status2) {
        this.status=status2;
	}

	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
        }
        
        public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id=id;;
        }

	public Collection<SimpleGrantedAuthority> getRoles() {
        List<SimpleGrantedAuthority> simpleGrantedAuthorityList=new ArrayList<>();
        for(Role rol:roles){
            simpleGrantedAuthorityList.add(new SimpleGrantedAuthority(rol.getName()));
        }
       return simpleGrantedAuthorityList;
        }
        

    @Override
    public String toString() {
        return "User [address=" + address + ", created=" + created + ", dateOfBirth=" + dateOfBirth + ", email=" + email
                + ", firstName=" + firstName + ", id=" + id + ", lastName=" + lastName
                + ", password=" + password + ", phoneNumber=" + phoneNumber + ", pinCode=" + pinCode + ", roles="
                + roles + ", status=" + status + ", updated=" + updated + ", userName=" + userName + "]";
    }

}
