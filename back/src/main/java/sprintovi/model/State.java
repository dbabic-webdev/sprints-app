package sprintovi.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
public class State {

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY)
	Long Id;
	
	@Column( nullable = false, unique = true)
	String name;

	@OneToMany(mappedBy = "state", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	List<Task> tasks = new ArrayList<Task>();
	
	public State() {
		super();
	}

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void removeTask(Long id) {
		for(Task t : this.tasks) {
			if (t.getId()==id){
				this.tasks.remove(t);
				return;
			}
		}		
	}
	

}
