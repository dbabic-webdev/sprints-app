package sprintovi.model;

import javax.persistence.*;

@Entity
public class Task {

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY)
	Long Id;
	
	@Column( nullable = false, unique = true)
	String name;
	
	@Column( nullable = true)
	String employee;
	
	@Column
	Integer points;
	
	@ManyToOne
	Sprint sprint;
	
	@ManyToOne
	State state;

	public Task() {
		super();
	}

	public Task(Task task) {
		this.Id = task.getId();
		this.name = task.getName();
		this.employee = task.getEmployee();
		this.points = task.getPoints();
		this.sprint = task.getSprint();
		this.state = task.getState();
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

	public String getEmployee() {
		return employee;
	}

	public void setEmployee(String empolyee) {
		this.employee = empolyee;
	}

	public Integer getPoints() {
		return points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}

	public Sprint getSprint() {
		return sprint;
	}

	public void setSprint(Sprint sprint) {
		this.sprint = sprint;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}
	
	
}
