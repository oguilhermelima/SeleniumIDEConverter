package models;

import java.util.List;

public class Command {

    private String id;
    private String comment;
    private String command;
    private String target;
    private List<List<String>> targets;
    private String value;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public List<List<String>> getTargets() {
        return targets;
    }

    public void setTargets(List<List<String>> targets) {
        this.targets = targets;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
