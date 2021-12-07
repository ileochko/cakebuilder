package ru.sfedu.cakebuilder.models;


import ru.sfedu.cakebuilder.models.enums.Status;

import java.util.Date;
import java.util.Objects;

public class HistoryContent {
    private String className;
    private Date createdDate;
    private String actor;
    private String methodName;
    private Object object;
    private Status status;

    public HistoryContent() {
    }

    public HistoryContent(String className, Date createdDate, String actor) {
        this.className = className;
        this.createdDate = createdDate;
        this.actor = actor;
    }

    public HistoryContent(String className, Date createdDate, String actor, String methodName, Object object, Status status) {
        this.className = className;
        this.createdDate = createdDate;
        this.actor = actor;
        this.methodName = methodName;
        this.object = object;
        this.status = status;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HistoryContent that = (HistoryContent) o;
        return Objects.equals(className, that.className) && Objects.equals(createdDate, that.createdDate) && Objects.equals(actor, that.actor) && Objects.equals(methodName, that.methodName) && Objects.equals(object, that.object) && status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(className, createdDate, actor, methodName, object, status);
    }

    @Override
    public String toString() {
        return "HistoryContent{" +
                "className='" + className + '\'' +
                ", createdDate=" + createdDate +
                ", actor='" + actor + '\'' +
                ", methodName='" + methodName + '\'' +
                ", object=" + object +
                ", status=" + status +
                '}';
    }
}