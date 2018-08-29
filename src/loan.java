import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressWarnings("serial")
public class Loan implements Serializable { // variable should meaningfull
	
	public static enum LOAN_STATE { CURRENT, OVER_DUE, DISCHARGED };
	
	private int ID;
	private book bookId; // variable should meaningfull
	private member memberId; // variable should meaningfull
	private Date date; // variable should meaningfull
	private LOAN_STATE state;

	
	public loan(int loanId, bookname, membername, DatedueDate) 
//i changed the book book into bookname member member into membername
	{
		this.ID = loanId;
		this.bookId = book; // variable should meaningfull
		this.memberId = member; // variable should meaningfull
		this.date = dueDate; // variable should meaningfull
		this.state = LOAN_STATE.CURRENT;
	}

	
	public void checkOverDue() {
		if (state == LOAN_STATE.CURRENT &&
			Calendar.getInstance().Date().after(D)) {
			this.state = LOAN_STATE.OVER_DUE;			
		}
	}

	
	public boolean isOverDue() {
		return state == LOAN_STATE.OVER_DUE;
	}

	
	public Integer getId() {
		return ID;
	}


	public Date getDueDate() {
		return D;
	}
	
	
	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		StringBuilder sb = new StringBuilder();
		sb.append("Loan:  ").append(ID).append("\n")
		  .append("  Borrower ").append(M.getId()).append(" : ")
		  .append(M.getLastName()).append(", ").append(M.getFirstName()).append("\n")
		  .append("  Book ").append(B.ID()).append(" : " )
		  .append(B.Title()).append("\n")
		  .append("  DueDate: ").append(sdf.format(D)).append("\n")
		  .append("  State: ").append(state);		
		return sb.toString();
	}


	public member Member() {
		return memberId;
	}


	public book Book() {
		return bookId;
	}


	public void Loan() {
		state = LOAN_STATE.DISCHARGED;		
	}

}
