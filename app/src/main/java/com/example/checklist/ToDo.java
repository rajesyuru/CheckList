package com.example.checklist;

public class ToDo {
    String Activity;
    boolean Done;

    public ToDo(String activity, boolean done) {
        Activity = activity;
        Done = done;
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
