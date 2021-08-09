package sprintovi.model;


import javax.persistence.*;

import sprintovi.enumerations.KorisnickaUloga;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private KorisnickaUloga role;

    public User(){

    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public KorisnickaUloga getRole() {
		return role;
	}


	public void setRole(KorisnickaUloga role) {
		this.role = role;
	}

    

}
