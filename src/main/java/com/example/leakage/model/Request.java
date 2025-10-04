package com.example.leakage.model;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class Request {
	private long id;
	private String title;
	private String description;
	private String makerId;
	private RequestStatus status;
	private String approverId;
	private String superApproverId;
	private Instant createdAt;
	private List<Action> actions = new ArrayList<>();

	public long getId() { return id; }
	public void setId(long id) { this.id = id; }
	public String getTitle() { return title; }
	public void setTitle(String title) { this.title = title; }
	public String getDescription() { return description; }
	public void setDescription(String description) { this.description = description; }
	public String getMakerId() { return makerId; }
	public void setMakerId(String makerId) { this.makerId = makerId; }
	public RequestStatus getStatus() { return status; }
	public void setStatus(RequestStatus status) { this.status = status; }
	public String getApproverId() { return approverId; }
	public void setApproverId(String approverId) { this.approverId = approverId; }
	public String getSuperApproverId() { return superApproverId; }
	public void setSuperApproverId(String superApproverId) { this.superApproverId = superApproverId; }
	public Instant getCreatedAt() { return createdAt; }
	public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
	public List<Action> getActions() { return actions; }
	public void setActions(List<Action> actions) { this.actions = actions; }
} 