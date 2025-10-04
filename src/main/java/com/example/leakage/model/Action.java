package com.example.leakage.model;

import java.time.Instant;

public class Action {
	private Instant at;
	private String actorId;
	private String type;
	private String note;

	public Action() {}

	public Action(Instant at, String actorId, String type, String note) {
		this.at = at;
		this.actorId = actorId;
		this.type = type;
		this.note = note;
	}

	public Instant getAt() { return at; }
	public void setAt(Instant at) { this.at = at; }
	public String getActorId() { return actorId; }
	public void setActorId(String actorId) { this.actorId = actorId; }
	public String getType() { return type; }
	public void setType(String type) { this.type = type; }
	public String getNote() { return note; }
	public void setNote(String note) { this.note = note; }
} 