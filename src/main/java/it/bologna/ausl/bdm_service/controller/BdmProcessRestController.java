package it.bologna.ausl.bdm_service.controller;

import it.bologna.ausl.bdm.core.BdmProcess;
import it.bologna.ausl.bdm.core.Step;
import it.bologna.ausl.bdm.core.Step.StepLogic;
import it.bologna.ausl.bdm.exception.BdmExeption;
import it.bologna.ausl.bdm.exception.IllegalStepStateException;
import it.bologna.ausl.bdm.exception.ProcessWorkFlowException;
import it.bologna.ausl.bdm.exception.StorageException;
import it.bologna.ausl.bdm.processmanager.BdmProcessManager;
import it.bologna.ausl.bdm.processmanager.DbProcessStorageManager;
import it.bologna.ausl.bdm.utilities.Bag;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author andrea
 */
@RestController
public class BdmProcessRestController {

    private static BdmProcessManager bpm = null;

    private String dbURI; //= "jdbc:postgresql://gdml/bdm?user=bdm&password=siamofreschi";

//    @Bean
//    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
//        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.registerModule(new JodaModule());
//        mapper.setTimeZone(TimeZone.getDefault());
//        mapper.enable(SerializationFeature.INDENT_OUTPUT);
//        mapper.enable(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS);
//        mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.JAVA_LANG_OBJECT);
//        jsonConverter.setObjectMapper(mapper);
//        return jsonConverter;
//    }
    
    @Autowired
    public BdmProcessRestController(@Value("${dbURI}") String dbUri) throws StorageException {
        this.dbURI = dbUri;
        if (bpm == null) {
            synchronized (this) {
                if (bpm == null) {
                    bpm = new BdmProcessManager(new DbProcessStorageManager(dbURI));
                }
            }
        }
    }

    @RequestMapping(value = "/bdmprocess/{processId}", method = RequestMethod.GET)
    public BdmProcess getProcess(@PathVariable String processId) {
        return bpm.getProcess(processId);
    }

    @RequestMapping(value = "/bdmprocess/{processId}/steps/{stepId}/next", method = RequestMethod.GET)
    public Step getNextStep(@PathVariable String processId, @PathVariable String stepId) {
        return bpm.getProcess(processId).getNextStep(stepId);
    }

    @RequestMapping(value = "/bdmprocess/{processId}/steps/{stepId}", method = RequestMethod.GET)
    public Step getStep(@PathVariable String processId, @PathVariable String stepId) {
        return bpm.getProcess(processId).getStep(stepId);
    }

    @RequestMapping(value = "/bdmprocess/{processId}/steps/", method = RequestMethod.GET)
    public Step getStepByType(@PathVariable String processId, @RequestParam String stepType) {
        return bpm.getProcess(processId).getStepByType(stepType);
    }

    @RequestMapping(value = "/bdmprocess/{processId}/context/", method = RequestMethod.PUT)
    public void setContext(@PathVariable String processId, @RequestBody Bag context) throws BdmExeption {
        bpm.setContext(processId, context);
    }

    @RequestMapping(value = "/bdmprocess/{processId}/steps/", method = RequestMethod.POST)
    public String addStep(@PathVariable String processId, @RequestBody Bag parameters) throws IllegalStepStateException, ProcessWorkFlowException, StorageException, BdmExeption {
        return bpm.addStep(processId, (String) parameters.get("stepDescription"), (Step.StepLogic) parameters.get("stepLogic"), (String) parameters.get("stepType"), (List<Step.StepLogic>) parameters.get("allowedStepLogic"));
    }
    
    @RequestMapping(value = "/bdmprocess/{processId}/steps/{stepId}", method = RequestMethod.DELETE)
    public void removeStep(@PathVariable String processId, @PathVariable String stepId) throws BdmExeption {
        bpm.removeStep(stepId, processId);
    }
    
    @RequestMapping(value = "/bdmprocess/{processId}/stepon", method = RequestMethod.PUT)
    public String stepOnProcess(@PathVariable String processId, @RequestBody Bag parameters) throws IllegalStepStateException, ProcessWorkFlowException, StorageException {
        return bpm.stepOnProcess(processId, parameters).toString();
    }

    @RequestMapping(value = "/bdmprocess/{processId}/stepto/{stepId}", method = RequestMethod.PUT)
    public String stepToStep(@PathVariable String processId, @PathVariable String stepId, @RequestBody Bag parameters) throws IllegalStepStateException, ProcessWorkFlowException, StorageException {
        return bpm.stepToStep(processId, stepId, parameters);
    }

    @RequestMapping(value = "/bdmprocess/{processId}/context/patch", method = RequestMethod.PUT)
    public void addInContext(@PathVariable String processId, @RequestBody Bag values) throws IllegalStepStateException, ProcessWorkFlowException, StorageException, BdmExeption {
        bpm.addInContext(processId, values);
    }

    @RequestMapping(value = "/bdmprocess/{processId}/steps/{stepId}", method = RequestMethod.PUT)
    public void setStepLogic(@PathVariable String processId, @PathVariable String stepId, @RequestBody StepLogic stepLogic) throws IllegalStepStateException, ProcessWorkFlowException, StorageException, BdmExeption {
        bpm.setStepLogic(processId, stepId, stepLogic);
    }

    @RequestMapping(value = "/bdmprocess/{processId}/steps/{stepId}/tasks/", method = RequestMethod.POST)
    public String addTask(@PathVariable String processId, @PathVariable String stepId, @RequestBody Bag parameters) throws IllegalStepStateException, ProcessWorkFlowException, StorageException, BdmExeption {
        return bpm.addTask((String) parameters.get("taskType"), (Bag) parameters.get("taskParameters"), processId, stepId);
    }
    
    @RequestMapping(value = "/bdmprocess/{processId}/steps/{stepId}/tasks/{taskId}", method = RequestMethod.DELETE)
    public void removeTask(@PathVariable String processId, @PathVariable String stepId, @PathVariable String taskId) throws BdmExeption {
        bpm.removeTask(taskId, processId, stepId);
    }
    
    @RequestMapping(value = "/bdmprocess/{processId}/steps/{stepId}/tasks/remove/", consumes = "application/json" ,method = RequestMethod.POST)
    public void removeTasks(@PathVariable String processId, @PathVariable String stepId, @RequestBody List<String> taskIdList) throws BdmExeption {
        for (String taskId : taskIdList) {
            bpm.removeTask(taskId, processId, stepId);
        }
    }

    @RequestMapping(value = "/bdmprocess/", method = RequestMethod.POST)
    public BdmProcess addProcess(@RequestBody Bag parameters) throws BdmExeption {
        return bpm.addProcess(parameters);
    }
    
    @RequestMapping(value = "/bdmprocess/{processId}", method = RequestMethod.DELETE)
    public void deleteProcess(@PathVariable String processId) throws BdmExeption {
        bpm.deleteProcess(processId);
    }

    @RequestMapping(value = "/bdmprocess/{processId}/abort", method = RequestMethod.PUT)
    public void abortProcess(@PathVariable String processId) throws BdmExeption {
        bpm.abortProcess(processId);
    }
    
    @RequestMapping(value = "/bdmprocess/", method = RequestMethod.GET)
    public List<String> listProcess() throws StorageException {
        return bpm.getProcessIdList();
    }

}
