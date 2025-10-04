package com.example.leakage.service;

import com.example.leakage.dto.CreateRequestDto;
import com.example.leakage.dto.LeakageRequestDto;
import com.example.leakage.model.Action;
import com.example.leakage.model.Request;
import com.example.leakage.model.RequestStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class RequestService {

	private final Map<Long, Request> storage = new ConcurrentHashMap<>();
	private final AtomicLong seq = new AtomicLong(1);

	public Request create(CreateRequestDto dto, String makerId) {
		Request r = new Request();
		r.setId(seq.getAndIncrement());
		r.setTitle(dto.getTitle());
		r.setDescription(dto.getDescription());
		r.setMakerId(makerId);
		r.setStatus(RequestStatus.PENDING_APPROVER);
		r.setCreatedAt(Instant.now());
		r.getActions().add(new Action(Instant.now(), makerId, "CREATE", null));
		storage.put(r.getId(), r);
		return r;
	}

	public List<Request> listPendingForRole(String role) {
		List<Request> list = new ArrayList<>();
		for (Request r : storage.values()) {
			if (role.equals("approver") && r.getStatus() == RequestStatus.PENDING_APPROVER) list.add(r);
			if (role.equals("super_approver") && r.getStatus() == RequestStatus.PENDING_SUPER) list.add(r);
		}
		list.sort((a,b) -> Long.compare(a.getId(), b.getId()));
		return list;
	}

	public List<Request> historyForUser(String userId, String role) {
		List<Request> list = new ArrayList<>();
		for (Request r : storage.values()) {
			boolean isMaker = userId.equals(r.getMakerId());
			boolean actedOn = false;
			for (Action a : r.getActions()) {
				if (userId.equals(a.getActorId())) { actedOn = true; break; }
			}
			if (isMaker || actedOn) list.add(r);
		}
		list.sort((a,b) -> Long.compare(a.getId(), b.getId()));
		return list;
	}

	public Request approve(long id, String actorId, String role) {
		Request r = mustFind(id);
		if (role.equals("approver") && r.getStatus() == RequestStatus.PENDING_APPROVER) {
			r.setStatus(RequestStatus.APPROVED);
			r.setApproverId(actorId);
			r.getActions().add(new Action(Instant.now(), actorId, "APPROVE", null));
			return r;
		}
		if (role.equals("super_approver") && r.getStatus() == RequestStatus.PENDING_SUPER) {
			r.setStatus(RequestStatus.APPROVED);
			r.setSuperApproverId(actorId);
			r.getActions().add(new Action(Instant.now(), actorId, "APPROVE", null));
			return r;
		}
		throw new IllegalStateException("Not allowed to approve this request at its current status");
	}

	public Request reject(long id, String actorId, String role) {
		Request r = mustFind(id);
		if ((role.equals("approver") && r.getStatus() == RequestStatus.PENDING_APPROVER) ||
			(role.equals("super_approver") && r.getStatus() == RequestStatus.PENDING_SUPER)) {
			r.setStatus(RequestStatus.REJECTED);
			r.getActions().add(new Action(Instant.now(), actorId, "REJECT", null));
			return r;
		}
		throw new IllegalStateException("Not allowed to reject this request at its current status");
	}

	public Request escalate(long id, String actorId, String role) {
		Request r = mustFind(id);
		if (role.equals("approver") && r.getStatus() == RequestStatus.PENDING_APPROVER) {
			r.setStatus(RequestStatus.PENDING_SUPER);
			r.getActions().add(new Action(Instant.now(), actorId, "ESCALATE", null));
			return r;
		}
		throw new IllegalStateException("Only approver can escalate from approver to super approver queue");
	}

	public Request createLeakageRequest(LeakageRequestDto dto, String makerId) {
		Request r = new Request();
		r.setId(seq.getAndIncrement());
		r.setTitle("Leakage Request - CA ID: " + dto.getCaId());
		r.setDescription(String.format(
			"Leakage Request for %s %s (CA: %s)\n" +
			"Current Loan: %.2f at rates %.2f%%, %.2f%%, %.2f%%\n" +
			"New Loan: %.2f at rates %.2f%%, %.2f%%, %.2f%%",
			dto.getUserInfo().getFirstName(),
			dto.getUserInfo().getLastName(),
			dto.getCaId(),
			dto.getUserInfo().getLoanAmount(),
			dto.getUserInfo().getCurrentLoanRate1st(),
			dto.getUserInfo().getCurrentLoanRate2nd(),
			dto.getUserInfo().getCurrentLoanRate3rd(),
			dto.getNewLoanAmount(),
			dto.getRequestNewLoanRate1st(),
			dto.getRequestNewLoanRate2nd(),
			dto.getRequestNewLoanRate3rd()
		));
		r.setMakerId(makerId);

		// Auto-approve logic
		boolean autoApprove = dto.getRequestNewLoanRate1st() >= dto.getUserInfo().getCurrentLoanRate1st() - 0.5 &&
			dto.getRequestNewLoanRate2nd() >= dto.getUserInfo().getCurrentLoanRate2nd() - 0.5 &&
			dto.getRequestNewLoanRate3rd() >= dto.getUserInfo().getCurrentLoanRate3rd() - 0.5;

		if (autoApprove) {
			r.setStatus(RequestStatus.APPROVED);
			r.setCreatedAt(Instant.now());
			r.getActions().add(new Action(Instant.now(), makerId, "CREATE_LEAKAGE", "Auto-approved: all new rates < current - 0.5%"));
			r.getActions().add(new Action(Instant.now(), makerId, "APPROVE", "Auto-approved by system"));
		} else {
			r.setStatus(RequestStatus.PENDING_APPROVER);
			r.setCreatedAt(Instant.now());
			r.getActions().add(new Action(Instant.now(), makerId, "CREATE_LEAKAGE", null));
		}
		storage.put(r.getId(), r);
		return r;
	}

	public Request sendEmailToOperation(long id, String actorId, String role) {
		Request r = mustFind(id);
		if (!"maker".equals(role)) {
			throw new IllegalStateException("Only maker can send email");
		}
		if (r.getStatus() != RequestStatus.APPROVED) {
			throw new IllegalStateException("Can only send email for approved requests");
		}
		boolean alreadySent = r.getActions().stream().anyMatch(a -> "SEND_EMAIL".equals(a.getType()));
		if (alreadySent) {
			throw new IllegalStateException("Email already sent for this request");
		}
		// Mock sending email to mock.operationteam.leakage@xxxxx.com
		String note = "Mock email sent to mock.operationteam.leakage@xxxxx.com";
		r.getActions().add(new Action(java.time.Instant.now(), actorId, "SEND_EMAIL", note));
		return r;
	}

	private Request mustFind(long id) {
		Request r = storage.get(id);
		if (r == null) throw new IllegalArgumentException("Request not found: " + id);
		return r;
	}
} 