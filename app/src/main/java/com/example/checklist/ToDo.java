package com.example.checklist;

public class ToDo {
    String Id;
    String Activity;
    boolean Done;

    public ToDo(String id, String activity, boolean done) {
        this.Id = id;
        this.Activity = activity;
        this.Done = done;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getActivity() {
        return Activity;
    }

    public void setActivity(String activity) {
        Activity = activity;
    }

    public boolean isDone() {
        return Done;
    }

    public void setDone(boolean done) {
        Done = done;
    }
}
