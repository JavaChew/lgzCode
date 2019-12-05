package com.archives.activiti;

import org.activiti.engine.*;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.zip.ZipInputStream;

/**
 * Created by lgz on 2019/12/3.
 */
public class ActivitiTest {
    /*ProcessEngine 

               1) 在Activiti中最核心的类，其他的类都是由他而来。

               2) 产生方式

       ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

               3）可以产生RepositoryService,管理流程定义

       RepositoryService repositoryService =processEngine.getRepositoryService(); 

               4) 可以产生 RuntimeService,执行管理，包括启动，推进，删除流程实例等操作

       RuntimeService runtimeService =processEngine.getRuntimeService(); 

               5) 可以产生TaskService,任务管理

       TaskService taskService =processEngine.getTaskService();*/

//    Logger logger = LogManager.getLogger(this.getClass());

    private static ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
    /**
     * 通过定义好的流程图文件部署，一次只能部署一个流程
     */
    public static void deploy() {
        RepositoryService repositoryService = processEngine.getRepositoryService();
        Deployment deployment = repositoryService.createDeployment()
                .name("firstActiviti")
                .addClasspathResource("activiti/leave.bpmn")
                .deploy();
        System.out.println("部署ID:"+deployment.getId());
        System.out.println("部署名称:"+deployment.getName());
    }
    /**
     * 将多个流程文件打包部署，一次可以部署多个流程
     */
    public void deployByZip() {
        InputStream is = this.getClass().getClassLoader().getResourceAsStream("diagrams/bpm.zip");
        ZipInputStream zip = new ZipInputStream(is);
        Deployment deployment = processEngine
                .getRepositoryService()
                .createDeployment()
                .addZipInputStream(zip)
                .deploy();
    }

    /**
     * 启动流程
     */
    public static ProcessInstance startInstanceByKey(String instanceByKey) {
        RuntimeService runtimeService = processEngine.getRuntimeService();
        ProcessInstance instance = runtimeService.startProcessInstanceByKey(instanceByKey);

        System.out.println("流程实例ID:" + instance.getId());
        System.out.println("流程定义ID:" + instance.getProcessDefinitionId());

        return instance;
    }
    /**
     * 查看任务
     */
    public static List<Task> findTaskByAssignee(String assignee) {
        TaskService taskService = processEngine.getTaskService();
        List<Task> taskList = taskService.createTaskQuery().taskAssignee(assignee).list();
        SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
        if (taskList != null && taskList.size() > 0) {
            for (Task task : taskList) {
                System.out.println("任务ID:" + task.getId());
                System.out.println("任务名称:" + task.getName());
                System.out.println("任务的创建时间:" + sdf.format(task.getCreateTime()));
                System.out.println("任务的办理人:" + task.getAssignee());
                System.out.println("流程实例ID:" + task.getProcessInstanceId());
                System.out.println("执行对象ID:" + task.getExecutionId());
                System.out.println("流程定义ID:" + task.getProcessDefinitionId());
                System.out.println("############################################");
            }
        }
        return taskList;
    }

}
