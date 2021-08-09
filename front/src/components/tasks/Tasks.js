import React from "react";
import { Table, Button, Form, ButtonGroup, Collapse } from "react-bootstrap";

import SprintsAxios from "../../apis/SprintsAxios";

class Tasks extends React.Component {
  constructor(props) {
    super(props);

    let task = {
      name: "",
      employee: "",
      points: "",
      sprintId: -1,
    };

    this.state = {
      task: task,
      tasks: [],
      sprints: [],
      showSearch: false,
      search: { taskName: "", sprintId: -1 },
      pageNo: 0,
      totalPages: 1,
      sprintSum: "",
    };
  }

  componentDidMount() {
    this.getData();
  }

  async getData() {
    await this.getSprints();
    await this.getTasks(0);
  }

  async getTasks(page) {
    let config = {
      params: {
        pageNo: page,
      },
    };

    if (this.state.search.taskName != "") {
      config.params.taskName = this.state.search.taskName;
    }

    if (this.state.search.sprintId != -1) {
      config.params.sprintId = this.state.search.sprintId;
    }

    try {
      let result = await SprintsAxios.get("/tasks", config);
      if (result && result.status === 200) {
        const sprintSum = result.headers["sprint-total"]
          ? result.headers["sprint-total"]
          : "";
        this.setState({
          pageNo: page,
          tasks: result.data,
          totalPages: result.headers["total-pages"],
          sprintSum: sprintSum,
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

  goToEdit(taskId) {
    this.props.history.push("/tasks/edit/" + taskId);
  }

  async doAdd() {
    try {
      await SprintsAxios.post("/tasks/", this.state.task);

      let task = {
        name: "",
        employee: "",
        points: 0,
        sprintId: -1,
      };
      this.setState({ task: task });

      this.getTasks();
    } catch (error) {
      alert("Nije uspelo dodavanje.");
    }
  }

  async doDelete(taskId) {
    try {
      await SprintsAxios.delete("/tasks/" + taskId);
      var nextPage;
      if (
        this.state.pageNo == this.state.totalPages - 1 &&
        this.state.tasks.length == 1
      ) {
        nextPage = this.state.pageNo - 1;
      } else {
        nextPage = this.state.pageNo;
      }
      await this.getTasks(nextPage);
    } catch (error) {
      alert("Nije uspelo brisanje.");
    }
  }

  addValueInputChange(event) {
    let control = event.target;

    let name = control.name;
    let value = control.value;

    let task = this.state.task;
    task[name] = value;

    this.setState({ task: task });
  }

  searchValueInputChange(event) {
    let control = event.target;

    let name = control.name;
    let value = control.value;

    let search = this.state.search;
    search[name] = value;

    this.setState({ search: search });
  }

  doSearch() {
    this.getTasks(0);
  }

  canCreateTask() {
    const task = this.state.task;
    return (
      task.name != "" &&
      task.points != "" &&
      task.points >= 0 &&
      task.points <= 20 &&
      task.points % 1 == 0 &&
      task.sprintId != -1
    );
  }

  async changeState(taskId) {
    try {
      const ret = await SprintsAxios.post(`/tasks/${taskId}/change_state`);
      var tasks = this.state.tasks;
      tasks.forEach((element, index) => {
        if (element.id === taskId) {
          tasks.splice(index, 1, ret.data);
          this.setState({ tasks: tasks });
        }
      });
    } catch (error) {
      alert("Nije moguće promeniti stanje.");
    }
  }

  render() {
    return (
      <div>
        <h1>Tasks</h1>
        {window.localStorage["role"] == "ROLE_ADMIN" ? (
          <Form>
            <Form.Group>
              <Form.Label>Name</Form.Label>
              <Form.Control
                onChange={(event) => this.addValueInputChange(event)}
                name="name"
                value={this.state.task.name}
                as="input"
              ></Form.Control>
            </Form.Group>
            <Form.Group>
              <Form.Label>Employee</Form.Label>
              <Form.Control
                onChange={(event) => this.addValueInputChange(event)}
                name="employee"
                value={this.state.task.employee}
                as="input"
              ></Form.Control>
            </Form.Group>
            <Form.Group>
              <Form.Label>Points</Form.Label>
              <Form.Control
                onChange={(event) => this.addValueInputChange(event)}
                name="points"
                value={this.state.task.points}
                as="input"
                type="number"
                min="0"
                step="1"
              ></Form.Control>
            </Form.Group>
            <Form.Group>
              <Form.Label>Sprint</Form.Label>
              <Form.Control
                onChange={(event) => this.addValueInputChange(event)}
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
            <Button
              disabled={!this.canCreateTask()}
              variant="primary"
              onClick={() => this.doAdd()}
            >
              Add
            </Button>
          </Form>
        ) : null}

        <Form.Group style={{ marginTop: 35 }}>
          <Form.Check
            type="checkbox"
            label="Show search form"
            onClick={(event) =>
              this.setState({ showSearch: event.target.checked })
            }
          />
        </Form.Group>
        <Collapse in={this.state.showSearch}>
          <Form style={{ marginTop: 10 }}>
            <Form.Group>
              <Form.Label>Ime zadatka</Form.Label>
              <Form.Control
                value={this.state.search.taskName}
                name="taskName"
                as="input"
                onChange={(e) => this.searchValueInputChange(e)}
              ></Form.Control>
            </Form.Group>
            <Form.Group>
              <Form.Label>Sprint</Form.Label>
              <Form.Control
                onChange={(event) => this.searchValueInputChange(event)}
                name="sprintId"
                value={this.state.search.sprintId}
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
            <Button onClick={() => this.doSearch()}>Search</Button>
          </Form>
        </Collapse>

        <ButtonGroup style={{ marginTop: 25, float: "right" }}>
          <Button
            style={{ margin: 3, width: 90 }}
            disabled={this.state.pageNo == 0}
            onClick={() => this.getTasks(this.state.pageNo - 1)}
          >
            Previous
          </Button>
          <Button
            style={{ margin: 3, width: 90 }}
            disabled={this.state.pageNo == this.state.totalPages - 1}
            onClick={() => this.getTasks(this.state.pageNo + 1)}
          >
            Next
          </Button>
        </ButtonGroup>

        <Table bordered striped style={{ marginTop: 5 }}>
          <thead className="thead-dark">
            <tr>
              <th>Name</th>
              <th>Employees</th>
              <th>Points</th>
              <th>State</th>
              <th>Sprint</th>
              <th colSpan={2}>Actions</th>
            </tr>
          </thead>
          <tbody>
            {this.state.tasks.map((task) => {
              return (
                <tr key={task.id}>
                  <td>{task.name}</td>
                  <td>{task.employee}</td>
                  <td>{task.points}</td>
                  <td>{task.stateName}</td>
                  <td>{task.sprintName}</td>
                  <td>
                    <Button
                      disabled={task.stateId === 3}
                      variant="info"
                      onClick={() => this.changeState(task.id)}
                    >
                      Change State
                    </Button>
                    {window.localStorage["role"] == "ROLE_ADMIN"
                      ? [
                          <Button
                            variant="warning"
                            onClick={() => this.goToEdit(task.id)}
                            style={{ marginLeft: 5 }}
                          >
                            Edit
                          </Button>,

                          <Button
                            variant="danger"
                            onClick={() => this.doDelete(task.id)}
                            style={{ marginLeft: 5 }}
                          >
                            Delete
                          </Button>,
                        ]
                      : null}
                  </td>
                </tr>
              );
            })}
          </tbody>
        </Table>
        <h2 hidden={this.state.sprintSum == ""}>
          Suma bodova za sprint je {this.state.sprintSum}
        </h2>
      </div>
    );
  }
}

export default Tasks;
