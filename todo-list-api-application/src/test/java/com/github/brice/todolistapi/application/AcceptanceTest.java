package com.github.brice.todolistapi.application;

import com.github.brice.todolistapi.application.in.ManagingTask;
import com.github.brice.todolistapi.application.in.UserService;
import com.github.brice.todolistapi.application.out.TaskNotFound;
import com.github.brice.todolistapi.application.out.Tasks;
import com.github.brice.todolistapi.application.out.Users;
import com.github.brice.todolistapi.application.out.stub.InMemoryTasks;
import com.github.brice.todolistapi.application.out.stub.InMemoryUsers;
import com.github.brice.todolistapi.application.task.Task;
import com.github.brice.todolistapi.application.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AcceptanceTest {
    private Users users;
    private Tasks tasks;
    private UserService userService;
    private ManagingTask authenticatedUser;

    @BeforeEach
    void setup() {
        users = new InMemoryUsers();
        tasks = new InMemoryTasks();
        userService = new UserServiceImpl(users);
        authenticatedUser = new AuthenticatedUser(users, tasks);
    }

    @Test
    void customerCanRegisterUser() {
        var userToRegister = new User("password", "name", "firstname.lastname@gmail.com");
        var user = userService.register(userToRegister);
        assertThat(user).isNotNull();
        assertThat(user.id()).isNotNull();
        assertThat(user.name()).isEqualTo("name");
        assertThat(user.password()).isEqualTo("password");
        assertThat(user.email()).isEqualTo("firstname.lastname@gmail.com");
    }

    @Test
    void customerCannotRegisterUserWithExistingEmail() {
        var userToRegister = new User("password", "name", "firstname.lastname@gmail.com");
        userService.register(userToRegister);
        assertThatThrownBy(() -> userService.register(userToRegister)).isInstanceOf(RuntimeException.class);
    }

    @Test
    void customerCanCreateTask() {
        var currentUser = new User("current.user@gmail.com", "current-user");
        userService.register(currentUser);
        var task = new Task("title", "description");
        var savedTask = authenticatedUser.createTask(task);
        assertThat(savedTask).isNotNull();
        assertThat(savedTask.id()).isNotNull();
        assertThat(savedTask.title()).isEqualTo("title");
        assertThat(savedTask.description()).isEqualTo("description");
        assertThat(savedTask.userId()).isNotNull();
    }

    @Test
    void customerCannotCreateTaskWithExistingTitle() {
        var currentUser = new User("current.user@gmail.com", "current-password");
        userService.register(currentUser);
        var task = new Task("title", "description");
        authenticatedUser.createTask(task);
        assertThatThrownBy(() -> authenticatedUser.createTask(task)).isInstanceOf(IllegalStateException.class);
    }

    @Test
    void customerCanUpdateTask() {
        var currentUser = new User("current.user@gmail.com", "current-password");
        userService.register(currentUser);
        var buyMilkTask = new Task("Buy milk", "cow milk");
        var buyEggsTask = new Task("Buy Eggs", "chicken eggs");
        var task = authenticatedUser.createTask(buyMilkTask);
        var updatedTask = authenticatedUser.updateTask(task.id(), buyEggsTask);
        assertThat(updatedTask).isNotNull();
        assertThat(updatedTask.title()).isEqualTo("Buy Eggs");
        assertThat(updatedTask.description()).isEqualTo("chicken eggs");
    }

    @Test
    void customerCannotUpdateNoExistingTask() {
        var currentUser = new User("current.user@gmail.com", "current-password");
        userService.register(currentUser);
        assertThatThrownBy(() -> authenticatedUser.updateTask(1L, new Task("new title", "new description"))).isInstanceOf(TaskNotFound.class);
    }

    @Test
    void customerCanDeleteExistingTask() {
        var currentUser = new User("current-password", "current name", "current.user@gmail.com");
        userService.register(currentUser);
        var task = new Task("title", "description");
        var createdTask = authenticatedUser.createTask(task);
        authenticatedUser.deleteTask(createdTask.id());
    }

    @Test
    void customerCannotDeleteNoExistingTask() {
        var currentUser = new User("current-password", "current name", "current.user@gmail.com");
        userService.register(currentUser);
        assertThatThrownBy(() -> authenticatedUser.deleteTask(1L)).isInstanceOf(TaskNotFound.class);
    }
}