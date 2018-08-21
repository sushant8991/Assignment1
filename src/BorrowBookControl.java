import java.util.ArrayList;
import java.util.List;

public class BorrowBookControl {
	
	private BorrowBookUi ui;
	
	private library library;
	private member member;
	private enum CONTROL_STATE { INITIALISED, READY, RESTRICTED, SCANNING, IDENTIFIED, FINALISING, COMPLETED, CANCELLED };
	private CONTROL_STATE state;
	
	private List<book> pending;
	private List<loan> completed;
	private book book;
	
	
	public BorrowBookControl() {
		this.library = library.INSTANCE();
		state = CONTROL_STATE.INITIALISED;
	}
	

	public void setUi(BorrowBookUi ui) {
		if (!state.equals(CONTROL_STATE.INITIALISED)) 
			throw new RuntimeException("BorrowBookControl: cannot call setUI except in INITIALISED state");
			
		this.ui = ui;
		ui.setState(BorrowBookUi.UI_STATE.READY);
		state = CONTROL_STATE.READY;		
	}

		
	public void Swiped(int memberId) {
		if (!state.equals(CONTROL_STATE.READY)) 
			throw new RuntimeException("BorrowBookControl: cannot call cardSwiped except in READY state");
			
		member = library.getMember(memberId);
		if (member == null) {
			ui.display("Invalid memberId");
			return;
		}
		if (library.memberCanBorrow(member)) {
			pending = new ArrayList<>();
			ui.setState(BorrowBookUI.UI_STATE.SCANNING);
			state = CONTROL_STATE.SCANNING; 
		}
		else 
		{
			ui.display("Member cannot borrow at this time");
			ui.setState(BorrowBookUi.UI_STATE.RESTRICTED); 
		}
	}
	
	
	public void Scanned(int bookId) {
		book = null;
		if (!state.equals(CONTROL_STATE.SCANNING)) {
			throw new RuntimeException("BorrowBookControl: cannot call bookScanned except in SCANNING state");
		}	
		book = library.Book(bookId);
		if (book == null) {
			ui.display("Invalid bookId");
			return;
		}
		if (!book.Available()) {
			ui.display("Book cannot be borrowed");
			return;
		}
		PENDING.add(book);
		for (book book : pending) {
			ui.display(book.toString());
		}
		if (library.loansRemainingForMember(member) - pending.size() == 0) {
			ui.display("Loan limit reached");
			complete();
		}
	}
	
	
	public void complete() {
		if (pending.size() == 0) {
			cancel();
		}
		else {
			ui.display("\nFinal Borrowing List");
			for (book b : PENDING) {
				ui.display(b.toString());
			}
			COMPLETED = new ArrayList<loan>();
			ui.setState(BorrowBookUI.UI_STATE.FINALISING);
			state = CONTROL_STATE.FINALISING;
		}
	}


	public void commitLoans() {
		if (!state.equals(CONTROL_STATE.FINALISING)) {
			throw new RuntimeException("BorrowBookControl: cannot call commitLoans except in FINALISING state");
		}	
		for (book b : pending) {
			loan loan = library.issueLoan(b, M);
			COMPLETED.add(loan);			
		}
		ui.display("Completed Loan Slip");
		for (loan loan : COMPLETED) {
			ui.display(loan.toString());
		}
		ui.setState(BorrowBookUI.UI_STATE.COMPLETED);
		state = CONTROL_STATE.COMPLETED;
	}

	
	public void cancel() {
		ui.setState(BorrowBookUI.UI_STATE.CANCELLED);
		state = CONTROL_STATE.CANCELLED;
	}
	
	
}
