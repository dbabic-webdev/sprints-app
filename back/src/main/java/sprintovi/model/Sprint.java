package sprintovi.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
public class Sprint {

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY)
	Long Id;
	
	@Column( nullable = false, unique = true)
	String name;
	
	@Column
	String points;

	@OneToMany(mappedBy = "sprint", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	List<Task> tasks = new ArrayList<Task>();
	
	
	public Sprint() {
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

	public String getPoints() {
		return points;
	}

	public void setPoints(String points) {
		this.points = points;
	}

	public void removeTask(Long id) {
		for(Task t : this.tasks) {
			if (t.getId()==id){
				this.tasks.remove(t);
				this.setPoints(Integer.parseInt(this.getPoints())-t.getPoints()+"");
				return;
			}
		}
	}

	public void addTask(Task task) {
		this.tasks.add(task);
		this.setPoints(Integer.parseInt(this.getPoints())+task.getPoints()+"");		
	}
	
}
