import React from "react";
import { Button, Form } from "react-bootstrap";

import SprintsAxios from "../../apis/SprintsAxios";

class EditTask extends React.Component {
  constructor(props) {
    super(props);

    let task = {
      name: "",
      employee: "",
      points: 0,
      stateId: -1,
      sprintId: -1,
    };

    this.state = {
      task: task,
      sprints: [],
      states: []
    };
  }

  componentDidMount() {
    this.getData();
  }

  async getData() {
    await this.getSprints();
    await this.getStates();
    await this.getTask();
  }

  async getTask() {
    
    try {
      let result = await SprintsAxios.get("/tasks/" + this.props.match.params.id);
      if (result && result.status === 200) {
        this.setState({
          task: result.data
        });
      }
    } catch (error) {
      alert("Nije uspelo dobavljanje.");
    }
  }

  async getSprints() {
    try {
      let result = await SprintsAxios.get("/sprints");
      if (result && result.status === 200) {
        this.setState({
          sprints: result.data,
        });
      }
    } catch (error) {
      alert("Nije uspelo dobavljanje.");
    }
  }

  async getStates() {
    try {
      let result = await SprintsAxios.get("/states");
      if (result && result.status === 200) {
        this.setState({
          states: result.data,
        });
      }
    } catch (error) {
      alert("Nije uspelo dobavljanje.");
    }
  }

  async doEdit() {
    try {
      await SprintsAxios.put("/tasks/" + this.props.match.params.id, this.state.task);
      this.props.history.push("/tasks");
    } catch (error) {
      alert("Nije uspelo čuvanje.");
    }
  }

  valueInputChange(event) {
    let control = event.target;

    let name = control.name;
    let value = control.value;

    let task = this.state.task;
    task[name] = value;

    this.setState({ task: task });
  }

  render() {
    return (
      <div>
        <h1>Task</h1>

        <Form>
          <Form.Group>
            <Form.Label>Name</Form.Label>
            <Form.Control
              onChange={(event) => this.valueInputChange(event)}
              name="name"
              value={this.state.task.name}
              as="input"
            ></Form.Control>
          </Form.Group>
          <Form.Group>
            <Form.Label>Employee</Form.Label>
            <Form.Control
              onChange={(event) => this.valueInputChange(event)}
              name="employee"
              value={this.state.task.employee}
              as="input"
            ></Form.Control>
          </Form.Group>
          <Form.Group>
            <Form.Label>Bodovi</Form.Label>
            <Form.Control
              onChange={(event) => this.valueInputChange(event)}
              name="points"
              value={this.state.task.points}
              as="input"
            ></Form.Control>
          </Form.Group>
          <Form.Group>
            <Form.Label>State</Form.Label>
            <Form.Control
              onChange={(event) => this.valueInputChange(event)}
              name="stateId"
              value={this.state.task.stateId}
              as="select"
            >
              <option value={-1}></option>
              {this.state.states.map((state) => {
                return (
                  <option value={state.id} key={state.id}>
                    {state.name}
                  </option>
                );
              })}
            </Form.Control>
          </Form.Group>
          <Form.Group>
            <Form.Label>Sprint</Form.Label>
            <Form.Control
              onChange={(event) => this.valueInputChange(event)}
              name="sprintId"
              value={this.state.task.sprintId}
              as="select"
            >
              <option value={-1}></option>
              {this.state.sprints.map((sprint) => {
                return (
                  <option value={sprint.id} key={sprint.id}>
                    {sprint.name}
                  </option>
                );
              })}
            </Form.Control>
          </Form.Group>
          <Button variant="primary" onClick={() => this.doEdit()}>
            Edit
          </Button>
        </Form>

      </div>
    );
  }
}

export default EditTask;
