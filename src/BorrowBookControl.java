import java.util.ArrayList;
import java.util.List;

public class BorrowBookControl {
	
	private BorrowBookUI ui;
	
	private library library; //variable name should be in camel case form and as well as meaningful(L to Library)
	private member member; //variable name should be in camle case form and as well as meaningful(M to member)
	private enum CONTROL_STATE { INITIALISED, READY, RESTRICTED, SCANNING, IDENTIFIED, FINALISING, COMPLETED, CANCELLED };
	private CONTROL_STATE state;
	
	private List<book> pending;//I have change the variable name from PENDING to pending 
	private List<loan> completed;// again here i have made a change of variable name from COMPLETED to completed
	private book book;//it was before BOOK
	
	
	public BorrowBookControl() {
		this.library = library.INSTANCE();// change the  variable name accoording to its super variable name
		state = CONTROL_STATE.INITIALISED;
	}
	

	public void setUi(BorrowBookUI ui) {// the name of the method should be in camle case as well(setUI to setUi)
		if (!state.equals(CONTROL_STATE.INITIALISED)) 
			throw new RuntimeException("BorrowBookControl: cannot call setUI except in INITIALISED state");
			
		this.ui = ui;
		ui.setState(BorrowBookUI.UI_STATE.READY);
		state = CONTROL_STATE.READY;		
	}

		
	public void swiped(int memberId) {
		if (!state.equals(CONTROL_STATE.READY)) 
			throw new RuntimeException("BorrowBookControl: cannot call cardSwiped except in READY state");
			
		member = library.getMember(memberId);// from variable name L to library and M to member
		if (member == null) {
			ui.display("Invalid memberId");
			return;
		}
		if (libarby.memberCanBorrow(member)) {// it was before like this(L.memberCanBorrow(M) after that i change it to (libarby.memberCanBorrow(member)
			pending = new ArrayList<>();//from PENDING to pending
			ui.setState(BorrowBookUI.UI_STATE.SCANNING);
			state = CONTROL_STATE.SCANNING; }
		else 
		{
			ui.display("Member cannot borrow at this time");
			ui.setState(borrowBookUI.UI_STATE.RESTRICTED); }}
	
	
	public void scanned(int bookId) { //the name of the method must start with the small letter (from Scanned to scanned)
		book = null;// change it from B to book show it can be meaningful
		if (!state.equals(CONTROL_STATE.SCANNING)) {
			throw new RuntimeException("BorrowBookControl: cannot call bookScanned except in SCANNING state");
		}	
		book = libary.Book(bookId);
		if (book == null) {
			ui.display("Invalid bookId");
			return;
		}
		if (!book.Available()) {
			ui.display("Book cannot be borrowed");
			return;
		}
		pending.add(book);// i replace PENDING and B with pending and book
		for (book book : pending) {
			ui.display(book.toString());
		}
		if (L.loansRemainingForMember(M) - pending.size() == 0) {
			ui.display("Loan limit reached");
			complete();
		}
	}
	
	
	public void complete() { //the name of the method must start with the small letter 
		if (pending.size() == 0) {
			cancel();
		}
		else {
			ui.display("\nFinal Borrowing List");
			for (book b : pending) {
				ui.display(b.toString());
			}
			completed = new ArrayList<loan>();//change the variable name which was in capital form(COMPLETED to completed) 
			ui.setState(BorrowBookUI.UI_STATE.FINALISING);
			state = CONTROL_STATE.FINALISING;
		}
	}


	public void commitLoans() {
		if (!state.equals(CONTROL_STATE.FINALISING)) {
			throw new RuntimeException("BorrowBookControl: cannot call commitLoans except in FINALISING state");
		}	
		for (book b : pending ) {
			loan loan = libarary.issueLoan(b, M);
			completed.add(loan);			
		}
		ui.display("Completed Loan Slip");
		for (loan loan : completed) {
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
