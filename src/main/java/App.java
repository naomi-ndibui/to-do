import java.util.HashMap;
import java.util.Map;

//import java.util.List;
//import java.util.ArrayList;
import spark.ModelAndView;
//import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class App {
    public static void main(String[] args) {
        staticFileLocation("/public");
        String layout = "templates/layout.vtl";

        ProcessBuilder process = new ProcessBuilder();
        Integer port;
        if (process.environment().get("PORT") != null) {
            port = Integer.parseInt(process.environment().get("PORT"));
        } else {
            port = 4567;
        }

        port(port);

        get("/", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("template", "templates/index.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        get("tasks/new", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("template", "templates/task-form.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        get("/tasks", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("tasks", Task.all());
            model.put("template", "templates/tasks.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        post("/tasks", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            String description = request.queryParams("description");
            Task newTask = new Task(description);
            model.put("template", "templates/success.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        get("/tasks/:id", (request, response) -> {
            HashMap<String, Object> model = new HashMap<String, Object>();
            Task task = Task.find(Integer.parseInt(request.params(":id")));
            model.put("task", task);
            model.put("template", "templates/task.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        get("/categories/new", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("template", "templates/category-form.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        post("/categories", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            String name = request.queryParams("name");
            Category newCategory = new Category(name);
            model.put("template", "templates/category-success.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        get("/categories", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("categories", Category.all());
            model.put("template", "templates/categories.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        get("/categories/:id", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            Category category = Category.find(Integer.parseInt(request.params(":id")));
            model.put("category", category);
            model.put("template", "templates/category.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());
        get("categories/:id/tasks/new", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            Category category = Category.find(Integer.parseInt(request.params(":id")));
            model.put("category", category);
            model.put("template", "templates/category-tasks-form.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        post("/tasks", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();

            Category category = Category.find(Integer.parseInt(request.queryParams("categoryId")));

            String description = request.queryParams("description");
            Task newTask = new Task(description);

            category.addTask(newTask);

            model.put("category", category);
            model.put("template", "templates/category-tasks-success.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());
    }
  }

//        post("/tasks", (request, response) -> {
//            Map<String, Object> model = new HashMap<String, Object>();
//
//            String description = request.queryParams("description");
//            Task newTask = new Task(description);
//            request.session().attribute("task", newTask);
//
//            model.put("template", "templates/success.vtl");
//            return new ModelAndView(model, layout);
//        }, new VelocityTemplateEngine());

//        get("/", (request, response) -> {
//            Map<String, Object> model = new HashMap<String, Object>();
//            model.put("task", request.session().attribute("task"));
//            model.put("template", "templates/index.vtl");
//            return new ModelAndView(model, layout);
//        }, new VelocityTemplateEngine());

//        post("/tasks", (request, response) -> {
//            Map<String, Object> model = new HashMap<String, Object>();
//
//            ArrayList<Task> tasks = request.session().attribute("tasks");
//            if (tasks == null) {
//                tasks = new ArrayList<Task>();
//                request.session().attribute("tasks", tasks);
//            }
//
//            String description = request.queryParams("description");
//            Task newTask = new Task(description);
//            tasks.add(newTask);
//
//            model.put("template", "templates/success.vtl");
//            return new ModelAndView(model, layout);
//        }, new VelocityTemplateEngine());


